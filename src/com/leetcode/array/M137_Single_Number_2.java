package com.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/12/27.
 * Given an array of integers, every element appears three times except for one. Find that single one.
 *
 * Note: Your algorithm should have a linear runtime complexity. i.e. Time - o(n).
 * Could you implement it without using extra memory? i.e. Space - o(1).
 *
 * Function Signature:
 * public int singleNumber(int[] a) {...}
 *
 * <系列问题>
 * E136 Single Number1: 给定一个数组，元素值成双出现，只有一个值出现了一次，找到这个元素。
 * M137 Single Number2: 给定一个数组，元素值成仨出现，只有一个值出现了一次，找到这个元素。
 * M260 Single Number3: 给定一个数组，元素值成双出现，只有两个值出现了一次，找到这两个元素。
 *
 * <Tags>
 * - Bit Manipulation: 未完全消化。
 *
 */
public class M137_Single_Number_2 {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[] {2, 3, 2, 3, 2, 3, 4}));
        System.out.println(singleNumber2(new int[] {1, 3, 5, 1, 3, 5, 1, 3, 5, 9}));
        System.out.println(singleNumber3(new int[] {1, 3, 5, 1, 3, 5, 1, 3, 5, 9}));
    }

    /** 比特翻转法。Time - o(n), Space - o(1). */
    // 出现1次的数字通过异或合并在once中
    // 出现2次的数字通过异或合并在twice中，并且从once中去掉
    // 出现3次的字符会从twice中去掉
    // 最后once中剩的那个数，就是唯一出现一次的数字。
    static int singleNumber2(int[] a) {
        int once = 0, twice = 0;
        for (int i = 0; i < a.length; i++) {
            once  = (a[i] ^ once)  & ~twice;
            twice = (a[i] ^ twice) & ~once;
            System.out.println("a[i]: " + a[i] + ", once: " + once + ", twice: " + twice);
        }
        return once;
    }

    // 另一种写法。
    static int singleNumber3(int[] a) {
        int ones = 0, twos = 0, threes;
        for (int i = 0; i < a.length; i++) {
            twos = twos | ones & a[i];      // twos holds the values that appears twice
            ones = ones ^ a[i];             // ones holds the values that appears once
            threes = ones & twos;           // threes holds the values that appears three times
            ones = ones & ~threes;          // if a[i] appears three times, this will clear ones and twos
            twos = ones & ~threes;
        }
        return ones;
    }

    /** 最简单的HashMap解法，统计出现频率。Time - o(n)，Space - o(n). */
    static int singleNumber(int[] a) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }

        for (int x : map.keySet())
            if (map.get(x) == 1) return x;
        return 0;
    }
}
