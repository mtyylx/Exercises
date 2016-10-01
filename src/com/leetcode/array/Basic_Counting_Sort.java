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
        int[] a = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        CountingSort(a);
        System.out.println(Arrays.toString(a));
        //bulkTest();
    }

    /** 计数排序的最大软肋是对未排序数组的元素取值范围有要求。*/
    // 基本思想：Value-as-Index，将原数组的元素值作为新数组的索引，原数组元素值出现的个数作为新数组的元素值。
    // 虽然计数排序不属于比较排序，因此不存在比较排序时间复杂度o(nlogn)的下限，理论上可以比任何比较排序的速度都要快。
    // 但是，只要其元素取值的最大值和最小值差值很大，即使未排序数组的元素个数很少，其排序速度依然将会非常慢（比较类排序则完全不会有这种问题）
    // 特别的，计数排序对于取值范围超过Integer.MAX_VALUE的数组是无法排序的。
    // 例如一个只有两个元素的数组[2147483647, -2147483648]的取值范围是4294967296，而由于Java数组的索引值必须是整型，因此索引会整型溢出，索引值将会是负值
    static void CountingSort(int[] a) {
        if (a == null || a.length < 2) return;
        int max = a[0];
        int min = a[0];
        for (int x : a) {
            if (x > max) max = x;
            else if (x < min) min = x;
        }
        int[] count = new int[max - min + 1];
        for (int x : a) {
            count[x - min]++;
        }
        int current = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                a[current] = i + min;
                current++;
                count[i]--;
            }
        }
    }

    static void CountingSortX(int[] a) {

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
