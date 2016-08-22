package com.leetcode.array;

import java.util.HashMap;

/**
 * Created by Michael on 2016/8/22.
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution.
 *
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * Function Signature:
 * public int[] twoSum(int[] a, int target) {...}
 */
public class E1_Two_Sum {
    public static void main(String[] args) {
        int[] a = new int[] {0, 1, 2, 7, 10};
        int[] result = twoSum(a, 8);
        for (int x : result) {
            System.out.print(x + ",");
        }
    }

    static int[] twoSum(int[] a, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (!map.containsKey(a[i])) map.put(target - a[i], i);
            else return new int[] {map.get(a[i]), i};
        }
        return new int[] {-1};
    }
}
