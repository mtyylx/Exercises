package com.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/8/24.
 *
 * Given an array of integers, find if the array contains any duplicates.
 * Your function should return true if any value <appears at least twice> in the array,
 * and it should return false if every element is distinct.
 *
 * Function Signature:
 * public boolean containsDuplicate(int[] a) {...}
 *
 * <系列问题>
 * E217 Contains Duplicate 1: 给定一个数组，如果相等元素存在就返回true，                           ，否则返回false。
 * E219 Contains Duplicate 2: 给定一个数组，如果相等元素的索引值之差等于k就返回true                  ，否则返回false。
 * M220 Contains Duplicate 3: 给定一个数组，如果元素的索引值之差小于等于k且元素值之差小于等于t就返回true，否则返回false。
 *
 * <Tags>
 * - Hash Table
 * - Sort: 相等元素一定相邻
 *
 */
public class E217_Contains_Duplicate {
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 4, 5, 6, 7, 7}));
        System.out.println(containsDuplicate2(new int[]{1, 2, 3, 4, 5, 6, 7, 7}));
    }

    /** 看到重复元素的另一个直觉解法就是使用Bit Manipulation，如果成功，就可以做到Space - o(1)。
     *  Single Number 2 中就是使用Bit Manipulation找到不重复的元素。但是这里是要找到重复的元素，看上去问题很相似，但是却不好应用。
     *  因为对于不同的元素，异或的结果的值的字面意思是随机的，例如 1 ^ 2 ^ 3 等于 0。
     */

    /** 解法1：哈希表，判重的最直觉解法。Time - o(n), Space - o(n) */
    // 需要额外空间。使用HashSet判重，无需HashMap。
    // set.add()方法会返回boolean值，告知是否成功添加，没成功添加就说明出现重复。
    // 利用set的性质，还有另一种类似的解法，就是把数组存入set中直接比较长度是否缩短即可。但是这个解法的前提是数组必须得是Integer[]，int[]不行。
    static boolean containsDuplicate(int[] a) {
        Set<Integer> set = new HashSet<>(a.length);
        for (int x : a)
            if (!set.add(x)) return true;
        return false;
    }

    /** 解法2：排序 + 利用已排序数组中相等元素一定相邻的特性. Time - o(nlogn), Space - o(1) */
    // 如果要求不使用额外空间，就可以先排序后解决问题，虽然这样时间复杂度就增加到至少o(nlogn)
    // 已排序数组具备很多方便的特性，使运算处理都大大简化。这也体现了排序的价值。排序通常不是主要目的，而是达到目的的称心工具。
    // 只要涉及到了排序，时间复杂度就至少是o(nlogn)，相比之下最优的解法一般顶多是o(n)，少数例如二分法能达到o(logn)，更有甚者则是o(1).
    static boolean containsDuplicate2(int[] a) {
        Arrays.sort(a);
        for (int i = 1; i < a.length; i++)      // 如果数组只有一个元素就跳过
            if (a[i] == a[i - 1]) return true;
        return false;
    }

}
