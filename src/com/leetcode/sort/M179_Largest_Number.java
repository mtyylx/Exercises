package com.leetcode.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Michael on 2016/10/7.
 * Given a list of non negative integers, arrange them such that they form the largest number.
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * Note: The result may be very large, so you need to return a string instead of an integer.
 *
 * Function Signature:
 * public String largestNumber(int[] a) {...}
 *
 * */
public class M179_Largest_Number {
    public static void main(String[] args) {
        int[] a = {99, 88, 77, 66, 5, 50};
        System.out.println(largestNumber(a));
    }

    // 相当机智的解法，利用了库函数的Arrays.sort，但是使用了自己的比较逻辑：只要两者正反相连哪个大就返回哪个小。
    static String largestNumber(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++)
            s[i] = String.valueOf(a[i]);
        // 匿名方法
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                String comb1 = str1 + str2;
                String comb2 = str2 + str1;
                return comb2.compareTo(comb1);    // 调用Comparable接口的方法，可以确保降序排列字符串数组
            }
        };
        // 使用重写的comparator
        Arrays.sort(s, comparator);
        if(s[0].charAt(0) == '0') return "0";
        StringBuilder sb = new StringBuilder();
        for (String i : s) sb.append(i);
        return sb.toString();
    }
}
