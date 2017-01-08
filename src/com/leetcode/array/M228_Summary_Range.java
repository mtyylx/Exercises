package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/10/22.
 * Given a sorted integer array without duplicates, return the summary of its ranges.
 *
 * For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
 *
 * Function Signature:
 * public List<String> summaryRange(int[] a) {...}
 *
 * <Tags>
 * - Integer to String Conversion.
 * - Embed While inside For loops for faster traversal.
 *
 */
public class M228_Summary_Range {
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 4, 5, 7, 9, 10, 11, 13};
        System.out.println(summaryRange(a));
        System.out.println(summaryRange2(a));
    }

    /** 解法1：双指针扫描。一个指针及记录起点，一个指针记录终点。Time - o(n) */
    // 简单分析后可以发现只有两种情况下，需要记录range：
    // Case #1：当前元素是最后一个元素 i + 1 == a.length
    // Case #2：当前元素与下一个元素不连续 a[i] + 1 != a[i + 1]
    // 另外考察了int -> String转换的方法：
    // 方法1：加上空字符串就自动整体转为字符串了
    // 方法2：使用String.valueOf(int)或Integer.toString(int)方法
    static List<String> summaryRange(int[] a) {
        List<String> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        int start = a[0];
        for (int i = 0; i < a.length; i++) {
            if (i + 1 == a.length || a[i + 1] != a[i] + 1) {            // Case #1 + #2
                if (start == a[i]) result.add(String.valueOf(start));
                else result.add(start + "->" + a[i]);
                if (i < a.length - 1) start = a[i + 1];
            }
        }
        return result;
    }

    /** 解法2：while嵌套在for循环中，加速移动。Time - o(n). */
    // 相比于解法1，写法更为简洁。
    static List<String> summaryRange2(int[] a) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            int x = a[i];
            while (i + 1 < a.length && a[i] + 1 == a[i + 1]) i++;   // 快速移动指针
            if (x == a[i]) result.add(x + "");
            else result.add(x + "->" + a[i]);
        }
        return result;
    }
}
