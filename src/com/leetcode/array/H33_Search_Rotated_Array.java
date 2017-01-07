package com.leetcode.array;

/**
 * Created by Michael on 2017/1/6.
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * You are given a target value to search.
 * If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
 *
 * Function Signature:
 * public int search(int[] a, int target) {...}
 *
 * <系列问题>
 * - M153 Find Min in Rotated Sorted Array 1: 给定一个被折断的有序数组，找到折断的起点（即最小值），该数组<无重复元素>。
 * - H154 Find Min in Rotated Sorted Array 2: 给定一个被折断的有序数组，找到折断的起点（即最小值），该数组<有重复元素>。
 * - H33  Search in Rotated Sorted Array 1:   给定一个被折断的有序数组和一个目标值，如果目标值在数组中就返回所在索引，如果不在就返回-1，该数组<无重复元素>。
 * - M81  Search in Rotated Sorted Array 2:   给定一个被折断的有序数组和一个目标值，判断目标值是否存在于数组中，该数组<有重复元素>。
 *
 * <Tags>
 * - Binary Search
 * - Two Pointers: 左右指针首尾包围 [left → → → ... ← ← ← right]
 * - Rotated Array: 折断数组由两个已排序数组构成，最小值通过比较中点与右指针之间的关系得到。
 *
 */
public class H33_Search_Rotated_Array {
    public static void main(String[] args) {
        System.out.println(search(new int[] {1, 2, 3}, 0));
        System.out.println(search2(new int[] {3, 1, 2}, 3));
        System.out.println(search2(new int[] {1, 2, 3}, 3));
        System.out.println(search2(new int[] {2, 3, 1}, 3));
    }

    /** 折断的有序数组的性质分析 */
    // Rotated Sorted Array的最大性质：虽然整个数组已经不再是有序数组，但是该数组一定是由<至多2个完全排序数组连接而成>的。
    // [1, 2, 3, 4] 是一个完整已排序数组，但也可以看作一个长度为0的已排序数组[]加[1, 2, 3, 4]构成。
    // [3, 4, 1, 2] 是一个折断的已排序数组，可以看作由数组[3, 4]和数组[1, 2]连接而成，而这两个子数组都是完整已排序的。
    // [4, 1, 2, 3] 同样是一个折断的额已排序数组，可以看作[4]和[1, 2, 3]连接而成，也是分别已排序的。


    /** 解法2：同样的思路，但是更为精简。Time - o(logN).
      * 二分法查找目标值: 根据 a[mid] 与 target 的大小关系，决定移动左还是右指针
      * 二分法查找最小值: 根据 a[mid] 与 a[right] 的大小关系，决定移动左还是右指针
      */
    // 需要把上面这两个方法结合起来才能解决问题，但是该先用哪个后用哪个呢？
    // 试一下就知道了。就跟M11一样，我们肯定要选择<能产生稳定趋势>的那个。
    // 如果我们先比较a[mid]与target，那么就会发现，由于此时我们根本无法确定mid在整个数组中的相对位置，
    // 例如对于同样target = 4，[4, 5, 1, 2, 3]和[5, 1, 2, 4]中，a[mid]都是小于target的，但是target实际位置却一个在左一个在右，无法预测。
    // 因此此时我们无法找到一个可靠的有稳定趋势的移动左右指针的方法，也就无法使用二分法。
    // 但是如果我们反过来，先比较a[mid]与a[right]，我们就可以确定a[mid]在整个[left,right]区间范围内的相对位置关系了，
    // 例如[4, 5, 1, 2, 3]和[5, 1, 2, 4]中，a[mid]始终在右侧部分，这时候我们再检查target是否在[mid, right]区间内，并选择移动左右指针中的一个
    // 例如[3, 4, 5, 1, 2]和[2, 3, 4, 1]中，a[mid]始终在左侧部分，这时候我们再检查target是否在[left, mid]区间内，并选择移动左右指针中的一个
    // 为了尽快匹配target，在比较a[mid]和a[right]之前，可以检查mid是否就是target，以尽快返回。同时以绝后患。
    static int search2(int[] a, int target) {
        if (a == null || a.length == 0) return -1;
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            if (a[mid] == target) return mid;                           // Early exit
            if (a[mid] < a[j]) {                                        // a[mid] < a[j]: mid 在右侧，且确定(mid, j]区间完全已排序
                if (target <= a[j] && target > a[mid]) i = mid + 1;     // target in (mid, j]
                else j = mid - 1;                                       // target in [i, mid)
            }
            else {                                                      // a[mid] >= a[j]: mid 在左侧，且确定[i, mid)区间完全已排序
                if (target >= a[i] && target < a[mid]) j = mid - 1;     // target in [i, mid)
                else i = mid + 1;                                       // target in (mid, j]
            }
        }
        return a[i] == target ? i : -1;         // 最后再检查下i和j重合的地方是否真的符合要求。
    }


    /** 解法1：结合了M153方法和标准Binary Search的解法。Time - o(logn) */
    // 思路是首先用M153的方法确定数组的最小值和最大值，然后用标准的二分查找在左或右区间内寻找target。
    // 比较讨厌的是处理边界情况，例如只有一个元素的数组，或者数组根本就是完好全排序的。
    // 因此需要先判断数组真的折断，如果真的折断，才分段调用二分查找。
    // 例如 [4 7 9 0 1 2 3] target = 7
    // 那么首先我们用findMinRotated找到最小值是0，最大值是9，起始值是4，因此target一定在左半部分，对[4 7 9]进行二分查找即可。
    static int search(int[] a, int target) {
        if (a == null || a.length == 0) return -1;
        int min = findMinRotated(a);
        if (min == 0) return binarySearch(a, 0, a.length - 1, target);  // 如果最小值就是第一个元素，就直接对整个数组进行二分查找。
        return target >= a[0] ? binarySearch(a, 0, min - 1, target) : binarySearch(a, min, a.length - 1, target);
    }

    // M153解法，利用Rotated Array的性质：寻找最小值就是比较中点与右指针之间的关系
    static int findMinRotated(int[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            if (a[mid] > a[j]) i = mid + 1;
            else               j = mid;
        }
        return i;
    }

    static int binarySearch(int[] a, int i, int j, int target) {
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (a[mid] > target) j = mid - 1;
            else if (a[mid] < target) i = mid + 1;
            else return mid;
        }
        return -1;
    }


}
