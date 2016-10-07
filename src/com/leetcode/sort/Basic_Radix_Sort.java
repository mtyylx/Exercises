package com.leetcode.sort;

import java.util.Arrays;

/**
 * Created by Michael on 2016/10/6.
 * Basic Algorithm: Radix Sort.
 * Time - o(n)
 * Space - o(n)
 */
public class Basic_Radix_Sort {
    public static void main(String[] args) {
        int[] a = {18, 16};
        RadixSort2(a);
        System.out.println(Arrays.toString(a));
        bulkTest();
    }

    // Radix Sort的核心在于对数值类型元素的每一位进行排序。
    // 需要从低位向高位扫描，且需要知道数组元素最大位数，作为循环条件。
    // Radix Sort中对每一位进行排序的子排序算法必须是稳定的，因为每一位在排序时其实都携带了该元素的其他位作为卫星数据。
    // 子排序算法必须稳定的示例：
    // 对 {18, 16} 进行排序，
    // 在对个位排序后变为 {16, 18}
    // 在对十位排序后，由于十位是一样的，因此应该以个位的排序为准，
    // 但是如果算法是不稳定的，比如计数排序如果没有使用逆序填充，则会得到 {18, 16}，导致排序失败。

    /** 使用Counting Sort作为子排序方法 */
    static void RadixSort2(int[] a) {
        int max = 0;
        for (int x : a) max = Math.max(getLength(x), max);

        for (int c = 0; c < max; c++) {
            int[] b = new int[10];
            int[] d = new int[a.length];
            for (int x : a) b[getDigit(x, c)]++;
            for (int i = 1; i < 10; i++) b[i] += b[i - 1];
            for (int i = a.length - 1; i >= 0; i--) d[--b[getDigit(a[i], c)]] = a[i];   // 必须逆序扫描以确保稳定性。
            for (int i = 0; i < a.length; i++) a[i] = d[i];
        }
    }

    /** 使用Insertion Sort作为子排序方法 */
    static void RadixSort(int[] a) {
        int max = 0;
        for (int x : a) max = Math.max(getLength(x), max);
        // 对每一位进行排序
        for (int c = 0; c < max; c++) {
            int i, j, current;
            for (i = 0; i < a.length; i++) {
                current = a[i];
                for (j = i - 1; j >= 0 && getDigit(a[j], c) > getDigit(current, c); j--) {
                    a[j + 1] = a[j];
                }
                a[j + 1] = current;
            }
        }
    }

    // 4321，定义其第0位是1，第1位是2，以此类推。
    private static int getDigit(int x, int digit) {
        for (int i = 0; i < digit; i++)
            x /= 10;
        return x % 10;
    }

    private static int getLength(int x) {
        int count = 0;
        while (x > 0) {
            x /= 10;
            count++;
        }
        return count;
    }

    public static void bulkTest() {
        for (int j = 2; j < 20; j += 2) {
            for (int i = 1; i < 10; i++) {
                int[] x = randGen(i, j);
                System.out.println("Original: " + Arrays.toString(x));
                RadixSort2(x);
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
