package com.leetcode.array;

import java.util.*;

/**
 * Created by Michael on 2016/8/27.
 * Given an integer array of size n,
 * find all elements that appear more than ⌊ n/3 ⌋ times.
 * The algorithm should run in linear time and in O(1) space.
 *
 * Hint:
 * How many majority elements could it possibly have?
 * Answer: 2. That's how o(1) is achieved.
 *
 * Function Signature:
 * public List<Integer> majorityElement(int[] a) {...}
 * */
public class M229_Majority_Element_2 {
    public static void main(String[] args) {
        int[] a = {1, 1, 1, 6, 7, 8, 5, 5, 5, 1, 5};
        List<Integer> result = majorityElement(a);
    }

    // 哈希表解法，时间复杂度o(n)，空间复杂度o(n)
    // HashMap 记录每个独特值的出现次数
    // HashSet 将独一无二的Majority Element存好
    static List<Integer> majorityElement(int[] a) {
        int limits = a.length / 3;
        Set<Integer> set = new HashSet<>(2);
        Map<Integer, Integer> map = new HashMap<>(a.length);
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
            if (map.get(x) > limits) set.add(x);
        }
        return new ArrayList<>(set);
    }
}
