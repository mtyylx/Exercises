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
 */
public class E219_Contains_Duplicate {
    public static void main(String[] args) {
        int[] a = {1, 0, 1, 1};
        System.out.println("Contains Duplicate within K steps: " + containsDuplicate2(a, 1));
    }

    // HashSet滑动窗解法，o(n)
    // 思考角度转变为：只检查当前滑动窗内是否出现重复元素，出现就返回true
    static boolean containsDuplicate2(int[] a, int k) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            if (i > k) set.remove(a[i - k - 1]);    // i > k时窗口开始移动，则从HashSet中删除刚出窗口的元素。
            if (!set.add(a[i])) return true;        // 在窗口内找到了重复元素，返回true
        }
        return false;
    }

    // 哈希表解法，o(n)
    // 关键在于给哈希表的键值对赋予含义：将元素内容存为键，将元素索引存为值
    // 虽然一个元素可以无限次多的出现在一个数组中，但是我们只关心它的距离是否小于某个值，
    // 也就是只需要确保重复出现的元素相邻的距离就可以了，例如{9,0,0,9,0,9}中，我们只需关心相邻的9，不需要关系第一个9和最后一个9
    static boolean containsDuplicate(int[] a, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (!map.containsKey(a[i])) map.put(a[i], i);   // Add to HashMap if new
            else if (i - map.get(a[i]) <= k) return true;   // map.get(a[i])表示最近一次出现时的索引值 只要满足距离小于K就立刻结束搜索
            else map.put(a[i], i);  // 记得处理这种情况：找到了重复出现的元素，但是距离大于K。必须也要更新哈希表中键对应的值
        }
        return false;
    }
}
