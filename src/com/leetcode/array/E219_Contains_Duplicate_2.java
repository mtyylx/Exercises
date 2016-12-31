package com.leetcode.array;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by LYuan on 2016/8/22.
 *
 * Given an array of integers and an integer k,
 * find out whether there are two distinct indices i and j in the array,
 * such that nums[i] = nums[j] and the difference between i and j is at most k.
 *
 * Function Signature:
 * public boolean containsDuplicate(int[] a, int k) {...}
 *
 * <系列问题>
 * E217 Contains Duplicate 1: 给定一个数组，如果相等元素存在就返回true，                           ，否则返回false。
 * E219 Contains Duplicate 2: 给定一个数组，如果相等元素的索引值之差等于k就返回true                  ，否则返回false。
 * M220 Contains Duplicate 3: 给定一个数组，如果元素的索引值之差小于等于k且元素值之差小于等于t就返回true，否则返回false。
 *
 * <Tags>
 * - Hash Table: HashSet / HashMap
 * - Sliding Window
 *
 */
public class E219_Contains_Duplicate_2 {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 1};
        System.out.println("Contains Duplicate within K steps: " + containsDuplicate2(a, 2));
        System.out.println("Contains Duplicate within K steps: " + containsDuplicate(a, 2));
    }

    /** 解法1：HashSet + 滑动窗。Time - o(n), Space - o(n).*/
    // 滑动窗中的所有元素存在HashSet中，随着滑动窗向右移动，移出的元素需要从HashSet中删掉，移入的被添加，或发现重复。
    static boolean containsDuplicate2(int[] a, int k) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            if (!set.add(a[i])) return true;    // 发现重复就返回结果
            if (i >= k) set.remove(a[i - k]);   // 为下一个循环清除即将被移出滑动窗的元素
        }
        return false;
    }

    /** 解法2：HashMap。Time - o(n), Space - o(n). */
    // Key存数组元素值，Value存数组元素的索引，用于检查是否在滑动窗范围内。
    // 如果出现重复，那么还需要检查最近的重复和当前位置相距是否超过k
    // 如果超过k，还要记得更新这个Key的Value，确保存的是最近出现的位置。
    // 例如 a = [9 0 0 9 0 9], k = 2，第二个9距离第一个9超过了K，但是第三个9距离第二个9等于k。
    static boolean containsDuplicate(int[] a, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (!map.containsKey(a[i])) map.put(a[i], i);
            else if (i - map.get(a[i]) <= k) return true;
            else map.put(a[i], i);                          // 重复值相距大于k，记得更新索引位置。
        }
        return false;
    }
}
