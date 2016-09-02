package com.leetcode.hashtable;

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
 * */
public class E170_Two_Sum_3_Data_Structure {
    public static void main(String[] args) {
        TwoSum2 ex = new TwoSum2();
        ex.add(1);
        ex.add(1);
        ex.add(2);
        ex.add(8);
        System.out.println(ex.find(2));
        System.out.println(ex.find(3));
        System.out.println(ex.find(4));
    }
}

// 使用HashSet，只要给的数与数组依次元素的差也在set里就返回true
// 缺点是每调用find一次，都要重新生成一个set，不能重复使用。
// 如果想要add时就统计，会出现不能cover的问题，因为set不能存重复元素，如果数组是{1, 3, 4} find(2)的话，会错误的使用1两次。无法规避。
class TwoSum {
    private List<Integer> list = new ArrayList<>();
    // No Explicit Constructor Needed. public TwoSum() {...}

    public void add(int a) {
        list.add(a);
    }

    public boolean find(int a) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            if (set.contains(a - list.get(i))) return true;
            else set.add(list.get(i));
        }
        return false;
    }
}

// 使用HashMap，在添加元素的时候就统计了每个值都有多少个，
// 因此不论find多少次，都可以重复利用一个统计好的HashMap
// 而且使用value存每个值出现的次数，可以统计对于重复元素是否有足够元素。
// 例如{1, 1, 3} find(2)可以正确返回true，因为键1对应的值是2.
class TwoSum2 {
    private List<Integer> list = new ArrayList<>();
    private Map<Integer, Integer> map = new HashMap<>();

    // 与上面方法的区别在于他会统计每个值出现的次数
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
            // 如果是由相等元素构成，就查是否该元素个数大于1，如果由不等元素构成，就差是否存在
            if ((num1 == num2 && map.get(num1) > 1) || (num1 != num2 && map.containsKey(num2)))
                return true;
        }
        return false;
    }
}
