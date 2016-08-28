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
        int[] a = {1, 9, 5, 9, 1, 4, 1, 9};
        List<Integer> result = majorityElement2(a);
    }

    static List<Integer> majorityElement2(int[] a) {
        if (a == null || a.length == 0) return new ArrayList<>();
        // 首先扫描找出出现次数最高的前两个元素
        int count1 = 1;
        int count2 = 0;
        int major1 = a[0];
        int major2 = a[0];
        // 难点在于对于5种情况的先后分别处理：
        // 1. 是major1
        // 2. 是major2
        // 3. 既不是major1也不是major2，但count1已经为0
        // 4. 既不是major1也不是major2，count1不为0，但count2为0
        // 5. 既不是major1也不是major2，且count1和count2都不为0
        for (int i = 1; i < a.length; i++) {
            if      (a[i] == major1) count1++;
            else if (a[i] == major2) count2++;
            else if (count1 == 0) {
                major1 = a[i];
                count1++;
            }
            else if (count2 == 0) {
                major2 = a[i];
                count2++;
            }
            // 两个counter都要自减
            else    {
                count1--;
                count2--;
            }
        }

        // 需要再次扫描确认前两名的出现次数是否都超过了n/3次，如果是，则加入最终结果的数组返回
        count1 = 0;
        count2 = 0;
        for(int x : a) {
            if (x == major1) count1++;
            else if (x == major2) count2++;     // 用else if的原因是避免{1, 1}情况下major1和major2都是初始值a[0]的情形。
        }
        List<Integer> result = new ArrayList<>(2);
        if (count1 > a.length / 3) result.add(major1);
        if (count2 > a.length / 3) result.add(major2);
        return result;
    }

    // 哈希表解法，时间复杂度o(n)，空间复杂度o(n)
    // HashMap 记录每个独特值的出现次数
    // HashSet 将独一无二的Majority Element存好
    static List<Integer> majorityElement(int[] a) {
        int limits = a.length / 3;
        Set<Integer> set = new HashSet<>(2);
        Map<Integer, Integer> map = new HashMap<>(a.length);
        for (int x : a) {
            if      (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else    map.put(x, 1);
            if      (map.get(x) > limits) set.add(x);
        }
        return new ArrayList<>(set);
    }
}
