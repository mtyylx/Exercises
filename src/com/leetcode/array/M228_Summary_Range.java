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
 */
public class M228_Summary_Range {
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 4, 5, 7, 9, 10, 11, 13};
        System.out.println(summaryRange(a));
    }

    // 一般数组问题：采用提前判定，决定什么时候写入result
    // Case 1：当前元素是最后一个元素 i + 1 == a.length
    // Case 2：当前元素不是最后一个元素，但是下一个元素不再连续 a[i] + 1 != a[i + 1]
    static List<String> summaryRange(int[] a) {
        List<String> result = new ArrayList<>();
        String range = "";
        for (int i = 0; i < a.length; i++) {
            if (range.length() == 0) range += a[i];
            if (i + 1 == a.length || a[i] + 1 != a[i + 1]) {
                if (range.equals(a[i] + "")) result.add(range);
                else result.add(range + "->" + a[i]);
                range = "";
            }
        }
        return result;
    }

    // 更简约的解法，缓存当前元素值，用while移动至条件不满足的位置，记录进result。
    static List<String> summaryRange2(int[] a) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            int x = a[i];
            while (i + 1 < a.length && a[i] + 1 == a[i + 1]) i++;
            if (x == a[i]) result.add(x + "");
            else result.add(x + "->" + a[i]);
        }
        return result;
    }
}
