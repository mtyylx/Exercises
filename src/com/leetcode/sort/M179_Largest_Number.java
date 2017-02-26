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
 * <Tags>
 * - 基于比较的排序的本质
 * - 避免整型溢出：转换为字符串比较问题。
 * - 字符串比较：比较每个字符的ASCII值的大小（Lexicographic Order）
 * - int2String类型转换：String.valueOf(int x)
 *
 */
public class M179_Largest_Number {
    public static void main(String[] args) {
        int[] a = {99, 88, 77, 66, 5, 50};
        int[] b = {0, 0};

        System.out.println(largestNumber(a));
        System.out.println(largestNumber2(a));
    }

    /** 解法2：重写Arrays.sort所使用的compare方法，自定义比较逻辑. */
    // 由于仅仅修改了元素大小的概念，排序算法用的还是库函数，因此可以确保性能最优（用的应该是快速排序的变体）
    // 关键点1：对比机制的自定义
    // 关键点2：排序算法
    // 关键点3：特例边界情况处理
    static String largestNumber(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++)                          // 全部转换为字符串数组操作，以避免重复转换
            s[i] = String.valueOf(a[i]);

        Comparator<String> comparator = new Comparator<String>() {  // 定义匿名类，重写compare方法
            @Override
            public int compare(String str1, String str2) {
                String comb1 = str1 + str2;
                String comb2 = str2 + str1;
                return comb2.compareTo(comb1);                      // compareTo方法比较两个字符串按字典排序的先后顺序
            }
        };

        Arrays.sort(s, comparator);                                 // Arrays.sort指定排序机制的用法，可以指定自定义的大小关系
        if(s[0].charAt(0) == '0') return "0";                       // 需要考虑数组全为0的特例，特殊处理
        StringBuilder sb = new StringBuilder();
        for (String i : s) sb.append(i);
        return sb.toString();
    }

    /** 解法1：插入排序 + 字符串比较。Time - o(n^2), Space - o(n) */
    // 数组a的元素本身可以直接比较，但是拼接以后的数值不一定在int范围之内。
    // 最稳妥、永远不会发生溢出情况的比较方法是按照字母顺序比较字符串大小。
    static String largestNumber2(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int j = i - 1;
            int temp = a[i];
            for (; j >= 0 && larger(a[j], temp); j--)
                a[j + 1] = a[j];
            a[j + 1] = temp;
        }
        if (a[a.length - 1] == 0) return "0";
        StringBuilder sb = new StringBuilder();
        for (int i = a.length - 1; i >= 0; i--)
            sb.append(String.valueOf(a[i]));
        return sb.toString();
    }

    // 比较两个数字正反组合后哪个更大，作为我们希望的顺序进行排列
    // 可能会出现数值大的元素放在前面组合的值更大的情况
    // 例如 10 和 50，如果按照一般的数值大小排序，那么显然应该是10在50之前
    // 但是为了让两个数值组合后的值更大，由于5010大于1050，所以应该让50在10之前排列
    static boolean larger(int a, int b) {
        String x = String.valueOf(a);
        String y = String.valueOf(b);
        String ab = x + y;
        String ba = y + x;
        return strcmp(ab, ba);
    }

    static boolean strcmp(String a, String b) {
        if (a.length() > b.length()) return true;
        if (a.length() < b.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) > b.charAt(i)) return true;
            if (a.charAt(i) < b.charAt(i)) return false;
        }
        return true;
    }
}
