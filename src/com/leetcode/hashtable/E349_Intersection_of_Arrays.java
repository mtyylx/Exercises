package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by Michael on 2016/8/30.
 * Given two arrays, write a function to compute their intersection.
 * 说的更清楚些，就是求两个数组的公用元素列表。
 * 一个元素如果既出现在a数组也出现在b数组中，就算是结果之一。
 *
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
 *
 * Note:
 * Each element in the result must be unique.
 * The result can be in any order.
 *
 * Function Signature:
 * public int[] intersection(int[] a, int[] b) {...}
 * */
public class E349_Intersection_of_Arrays {
    public static void main(String[] args) {
        int[] a = {1, 4, 7, 29, 30, 59, 67};
        int[] b = {2, 5, 6, 30, 59, 55};
        int[] result = intersection3(a, b);
    }

    // 二分查找，两数组
    // 当然也可以只让一个数组有序，然后遍历另一个数组的每个元素，边遍历边与已排序数组进行二分查找比对
    // 所以计算量依然是n次的logn二分查找。
    static int[] intersection3(int[] a, int[] b) {
        int[] small = a.length < b.length ? a : b;
        int[] big = a.length >= b.length ? a : b;
        Arrays.sort(big);
        Set<Integer> set = new HashSet<>();
        int i, j, mid;
        for (int x = 0; x < small.length; x++) {
            // 每扫描完一个元素，必须重置首尾指针哦
            i = 0;
            j = big.length - 1;
            while (i <= j) {
                mid = (i + j) / 2;
                if      (big[mid] < small[x]) i = mid + 1;
                else if (big[mid] > small[x]) j = mid - 1;
                else    {
                    set.add(small[x]);
                    break;      // 找到后得立刻退出循环
                }
            }
        }
        int[] result = new int[set.size()];
        int idx = 0;
        for (int ele : set) {
            result[idx++] = ele;
        }
        return result;
    }

    // 先把两个数组都排序后用双指针扫描解法，因为有排序的复杂度在那，所以是o(nlogn)
    static int[] intersection2(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0;
        int j = 0;
        Set<Integer> set = new HashSet<>();
        while (i < a.length && j < b.length) {
            if      (a[i] > b[j]) j++;
            else if (a[i] < b[j]) i++;
            else {
                set.add(a[i]);
                i++;
                j++;
            }
        }
        int[] result = new int[set.size()];
        int x = 0;
        for (int z : set)
            result[x++] = z;
        return result;
    }

    // 常规解法，o(n)
    // 先统计数组a中独特元素都是哪些，存在HashSet中
    // 再统计数组b中有哪些元素是HashSet中有的，存在ArrayList中
    // 最后把ArrayList转化成为int数组返回
    static int[] intersection(int[] a, int[] b) {
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (int x : a)
            set.add(x);
        for (int x : b) {
            if (set.contains(x)) {
                list.add(x);
                set.remove(x);  // 避免重复记录
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }
}
