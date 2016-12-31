package com.leetcode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/12/31.
 * Given an array of integers, find out whether there are two distinct indices i and j in the array
 * such that the difference between nums[i] and nums[j] is at most t
 * and the difference between i and j is at most k.
 *
 * Function Signature:
 * public boolean containsDuplicate(int[] a, int k , int t) {...}
 *
 * <系列问题>
 * E217 Contains Duplicate 1: 给定一个数组，如果相等元素存在就返回true，                           ，否则返回false。
 * E219 Contains Duplicate 2: 给定一个数组，如果相等元素的索引值之差等于k就返回true                  ，否则返回false。
 * M220 Contains Duplicate 3: 给定一个数组，如果元素的索引值之差小于等于k且元素值之差小于等于t就返回true，否则返回false。
 *
 * <Tags>
 * - Sliding Window
 *
 */
public class M220_Contains_Duplicate_3 {
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[] {1, 4, 2, 8, 5, 7}, 2, 1));
    }

    /** 解法1：线性滑动窗（双指针实现）遍历。Time - o(n*min(n,k)), Space - o(1). */
    // 维持一个宽度为k的滑动窗，每个新加入的元素都要与滑动窗内的所有元素进行比较，看差值是否小于等于t
    // 初始状态滑动窗宽度为0，以双指针作为滑动窗的起点left和终点right。随着滑动窗宽度不断增长，直到宽度为k的时候开始移动左侧指针。
    static boolean containsDuplicate(int[] a, int k, int t) {
        if (k < 0 || t < 0) return false;
        int left = 0;
        for (int right = 0; right < a.length; right++) {
            if (right > k) left++;                  // 滑动窗增长到宽度k以后，才开始移动起点指针，以保持滑动窗的宽度恒定。
            for (int i = left; i < right; i++) {    // 每个新加入滑动窗的元素a[right]都要与窗内的其他所有元素比较
                long diff = a[right] - a[i];        // 由于差值可能会大于整型取值区间，因此要用long，再用Math.abs()，否则取绝对值会整型溢出。
                if (Math.abs(diff) <= t) return true;
            }
        }
        return false;
    }
}
