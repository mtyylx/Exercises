package com.leetcode.hashtable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        int[] result = intersection(a, b);
    }

    // 常规解法，o(n)
    // 先统计数组a中独特元素都是哪些，存在HashSet中
    // 再统计数组b中有哪些元素是HashSet中有的，存在ArrayList中
    // 最后把ArrayList转化成为int数组返回
    static int[] intersection(int[] a, int[] b) {
        Set<Integer> set = new HashSet<>();
        for (int x : a)
            set.add(x);
        List<Integer> list = new ArrayList<>();
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
