package com.leetcode.array;

import java.util.*;

/**
 * Created by LYuan on 2016/9/2.
 * Design and implement a TwoSum class. It should support the following operations: add and find.
 * add - Add the number to an internal data structure.
 * find - Find if there exists any pair of numbers which sum is equal to the value.
 *
 * For example,
 * add(1); add(3); add(5);
 * find(4) -> true
 * find(7) -> false
 *
 * Function Signature:
 * public boolean find(int a) {...}
 *
 * <K-Sum系列问题>
 *    E1 2-Sum: 给定一个整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *   M15 3-Sum: 给定一个整形数组a和目标值0，求解所有相加等于0的三元组，不可重复。（解个数随机）
 *   M18 4-Sum: 给定一个整型数组a和目标值k，求解所有相加等于0的四元组，不可重复。（解个数随机）
 *  M167 2-Sum Sorted: 给定一个已排序的整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *  E170 2-Sum Data Structure: 给定一系列整型数值和目标值，提供添加元素和寻找内部数据库中是否存在和等于目标值的两个元素的功能。
 *   M16 3-Sum Closest: 给定一个整型数组a和目标值k，求解距离k最近的一个三元组之和。（有且仅有一个解）
 *  M259 3-Sum Smaller: 给定一个整型数组a和目标值k，求解和小于target的三元组个数，可以重复。
 *
 * <Tags>
 * - HashMap 维护统计数据
 *
 */
public class E170_Two_Sum_3_Data_Structure {
    public static void main(String[] args) {
        TwoSum ex = new TwoSum();
        TwoSum2 ex2 = new TwoSum2();
        ex2.add(1);
        ex2.add(1);
        ex2.add(2);
        ex2.add(8);
        System.out.println(ex2.find(2));
        System.out.println(ex2.find(3));
        System.out.println(ex2.find(4));
    }
}


/** 解法2：性能更好，在添加每个元素的时候就统计元素个数，可以重复利用统计数据。 */
// 由于始终维护一个HashMap，统计了添加的所有元素的分布情况和出现次数。
// 因此在反复调用find的时候，不需要每次都另立门户o(n)的搜索整个解集，而是可以直接用这个HashMap来做到o(1)的查询。
class TwoSum2 {
    private List<Integer> list = new ArrayList<>();
    private Map<Integer, Integer> map = new HashMap<>();

    public void add(int number) {
        list.add(number);
        if (map.containsKey(number))
            map.put(number, map.get(number) + 1);
        else
            map.put(number, 1);
    }

    public boolean find(int value) {
        for (int i = 0; i < list.size(); i++) {
            int num1 = list.get(i);
            int num2 = value - num1;
            // 如果两个元素相同，就检查该元素个数是否大于1，如果两个元素不同，就检查是否都存在
            if ((num1 == num2 && map.get(num1) > 1) || (num1 != num2 && map.containsKey(num2)))
                return true;
        }
        return false;
    }
}

/** 解法1：HashMap，与 2Sum 完全一样的解法。 */
// 为了避免重复计算，例如反复find同一个值时无需每次都真正搜索，第一次找到以后就应该记录下来（就像DP一样备忘）
// 因此设计一个单独的HashSet，用来存储那些返回true的target。因为这里并没有delete方法，因此不存在调用同一个find的中间删除元素的可能。
class TwoSum {
    private List<Integer> list = new ArrayList<>();
    private Set<Integer> dp = new HashSet<>();
    public void add(int val) {
        list.add(val);
    }

    public boolean find(int target) {
        if (dp.contains(target)) return true;
        Map<Integer, Integer> map = new HashMap<>();        // 每次都从头扫描起，这个HashMap没有做到重复利用
        for (int i = 0; i < list.size(); i++) {
            if (map.containsKey(list.get(i))) { dp.add(target); return true; }
            else map.put(target - list.get(i), i);
        }
        return false;
    }
}
