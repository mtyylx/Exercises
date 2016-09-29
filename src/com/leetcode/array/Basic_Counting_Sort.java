package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/9/29.
 *
 * Basic Algorithm: Counting Sort.
 *
 * Time - o(n)
 * Space - o(n)
 *
 */
public class Basic_Counting_Sort {
    public static void main(String[] args) {
        int[] a = {1, 1, 4, 2, 4, 2, 1, 4, 2, 2, 1, 5, 65, 4, 1, 2, 4};
        CountingSort(a);
        System.out.println(Arrays.toString(a));
        bulkTest();
    }

    // 思想就是Value-as-index解法，用元素值作为计数数组的索引值，在计数数组里面存索引值出现次数
    // 再从小到大顺序扫描计数数组，根据出现次数原位修改原数组即可。
    static void CountingSort(int[] a) {
        int max = a[0];
        for (int x : a) {
            if (x > max) max = x;
        }
        int[] count = new int[max + 1];
        for (int x : a) {
            count[x]++;
        }
        int idx = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] != 0) {
                a[idx] = i;
                count[i]--;
                idx++;
            }
        }
    }

    public static void bulkTest() {
        for (int j = 2; j < 1000; j += 2) {
            for (int i = 1; i < 100; i++) {
                int[] x = randGen(i, j);
                System.out.println("Original: " + Arrays.toString(x));
                CountingSort(x);
                if (!isSorted(x)) {
                    System.out.println("Failed at: " + Arrays.toString(x));
                    return;
                }
                else System.out.println("Passed at: " + Arrays.toString(x));
            }
        }
        System.out.println("Passed.");
    }

    public static boolean isSorted(int[] a) {
        if (a == null || a.length < 2) return true;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }

    private static int[] randGen(int len, int range) {
        int[] a = new int[len];
        for (int i = 0; i < a.length; i++)
            a[i] = (int) (range * Math.random());
        return a;
    }

}
