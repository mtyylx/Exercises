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
 *
 * <系列问题>
 * E169 Majority Element : 给定一个数组，确定存在一个出现次数超过 n / 2 的元素，找到这个元素。
 * M229 Majority Element2: 给定一个数组，不确定存在出现次数超过 n / 3 的元素，找到所有元素。（可能的元素个数：0, 1, 2）
 *
 * <Tags>
 * - Moore Voting: Two Counters.
 * - HashMap
 *
 */
public class M229_Majority_Element_2 {
    public static void main(String[] args) {
        System.out.println(majorityElement2(new int[] {1, 9, 5, 9, 1, 4, 1, 9}));
        System.out.println(majorityElement(new int[] {1, 9, 5, 9, 1, 4, 1, 9}));
    }

    /** 解法2：升级版的Moore Voting。Time - o(n), Space - o(1) */
    // 首先用扩展版的投票法，获得出现频率最高的两个值。
    // 然后再扫描一次，确定两个值出现次数都超过了n / 3。
    static List<Integer> majorityElement2(int[] a) {
        List<Integer> result = new ArrayList<>(2);
        if (a == null || a.length == 0) return result;
        int count1 = 0;
        int count2 = 0;
        int major1 = a[0];
        int major2 = a[0];
        for (int x : a) {
            if      (x == major1) count1++;                     // 等于major1
            else if (x == major2) count2++;                     // 等于major2
            else if (count1 == 0) { major1 = x; count1++; }     // 都不是，且此时count1已经为0 -> 更新major1
            else if (count2 == 0) { major2 = x; count2++; }     // 都不是，且count1不是0，但count2是0 -> 更新major2
            else                  { count1--; count2--; }       // 都不是，且count1和count2都没降到0 -> count1和count2都降
        }

        count1 = 0;
        count2 = 0;
        for(int x : a) {
            if (x == major1) count1++;
            else if (x == major2) count2++;             // 使用else if可以避免count1和count2重复记录，当major1=major2的时候。
        }
        if (count1 > a.length / 3) result.add(major1);
        if (count2 > a.length / 3) result.add(major2);  // 无需检查major1是否等于major2
        return result;
    }

    /** 解法1：传统哈希表，统计次数。Time - o(n), Space - o(n) */
    // 哈希表解法，时间复杂度o(n)，空间复杂度o(n)
    // 用HashMap记录每个独特值的出现次数
    // 用HashSet去重
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
