package com.leetcode.array;

/**
 * Created by Michael on 2017/1/6.
 *
 * Follow up for "Find Minimum in Rotated Sorted Array": What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * The array may contain duplicates.
 *
 * Example:
 * - [3, 3, 3, 1, 3], Min = 1
 * - [3, 1, 3, 3, 3], Min = 1
 *
 * Function Signature:
 * public int findMin(int[] a) {...}
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
public class H154_Find_Min_Rotate_Sorted_Array_2 {
    public static void main(String[] args) {
        System.out.println(findMin(new int[] {2, 3, 3, 1, 1}));
        System.out.println(findMin2(new int[] {3, 1, 3, 3, 3}));
    }

    // 其实本身题目很简单，只不过如果想用Binary Search的话，就会比较棘手，需要小心处理边界情况。

    /** 解法2：Binary Search（双指针包围）。Time Avg - o(logn), Time Worst - o(n). */
    // 一言以蔽之：It's all about the relationship between a[mid] and a[right].
    // 与M153的唯一区别就是数组可能会有重复元素，而恰恰是这一点，使得二分搜索会在某些情况下失效。
    // 例如这两种情况：a=[3, 3, 3, 1, 3] b=[3, 1, 3, 3, 3],
    // 虽然两个数组的a[mid]都是3，都等于a[j]，但是数组a应该向后找min，而数组b却应该向前找min
    // 因此可以说，在这种特殊情况下，二分查找的准则本质上失效了。如何解决呢？
    // 由于o(logn)已经失效，因此我们应该做的是退一步海阔天空，退而求线性时间复杂度，不断收缩右指针，直至右指针和中点不再相同。
    static int findMin2(int[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            if      (a[mid] > a[j]) i = mid + 1;    // min位于mid右侧（不包括mid）
            else if (a[mid] < a[j]) j = mid;        // min位于mid左侧（包括mid）
            else j--;                               // 专门单独处理这种情况：一旦出现，就退化成为线性的时间复杂度，不断收缩范围，直至两者不再相等。
        }
        return a[i];
    }

    /** 解法1：单指针顺序扫描，100% Brain Friendly. Time - o(n) */
    static int findMin(int[] a) {
        if (a == null || a.length == 0) return -1;
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) return a[i];
        return a[0];
    }
}
