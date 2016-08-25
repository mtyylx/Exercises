package com.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/8/24.
 *
 * Given an array of integers, find if the array contains any duplicates.
 * Your function should return true if any value appears at least twice in the array,
 * and it should return false if every element is distinct.
 *
 * Function Signature:
 * public boolean containsDuplicate(int[] a) {...}
 */
public class E217_Contains_Duplicate {
    public static void main(String[] args) {
        System.out.println(containsDuplicate2(new int[]{1, 2, 3, 4, 5, 6, 7, 7}));
    }

    // 哈希表解法，o(n)
    // 这里使用的是HashSet，因为只需要用到是否重复的特性，HashMap的键值对没有什么用。
    static boolean containsDuplicate(int[] a) {
        Set<Integer> set = new HashSet<>(a.length);
        for (int x : a) {
            if (!set.add(x)) return true;
        }
        return false;
    }

    // 先排序后扫描解法，
    // 排序了的数组具备了非常多方便的特性，是的运算处理起来都大大简化。所以说排序很神奇，因为它真的很有用。
    // 我发现基本上所有的数组类题目都可以使用这种思路解题，
    // 虽然使用自带的Arrays.sort()是个偷懒的行为，而且通常并不是性能最优的解法
    // 只要涉及到了排序，时间复杂度就至少是o(nlogn)，相比之下最优的解法一般顶多是o(n)，少数例如二分法能达到o(logn)，更有甚者则是o(1).
    static boolean containsDuplicate2(int[] a) {
        Arrays.sort(a);
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) return true;
        }
        return false;
    }
}
