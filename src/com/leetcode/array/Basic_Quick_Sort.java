package com.leetcode.array;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import java.util.Arrays;

/**
 * Created by Michael on 2016/9/28.
 *
 * Basic Algorithm: Quick Sort
 * Time - o(n * log n)
 * Space - o(？)
 */
public class Basic_Quick_Sort {
    public static void main(String[] args) {
        for (int j = 1; j < 1000; j += 2) {
            for (int i = 1; i < 100; i++) {
                int[] x = randGen(i, j);
                QuickSort(x, 0, x.length - 1);
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

    // 基本思路是Top-down，不断的分解问题，在分解的过程中原位的对元素顺序进行调换，
    // 分解完成时（即递归到头时），整个数组已经完全有序，任务也就完成了，如果用递归解法，显然也是正序递归。
    // 先选基准元素，然后双指针同时首尾扫描，互换位置不对的元素，
    // 直至双指针相遇，这时候start至end区域就被划分成了两个部分，左侧的全小于pivot，右侧的全大于pivot
    // 再对划分后的元素进行继续划分，直至待分区数组长度为1。
    // 一开始卡在了一定要让pivot元素本身处于边界上的思维定势，发现怎么交换或插入元素代码都不好写
    // 后来意识到其实pivot直接被缓存，因此他本身可以湮灭一阵子。
    // 遇到了一个难点：如何处理left和right重合这种情况的移动，如何确保在任何情况都能结束递归
    // 如果刚好left和right在相互交换内容且相向移动之后，进入下一循环开始时是重合的，那么会有下面三种可能：
    // Case 1: 重合点元素小于pivot
    // 5 4 3 -> 3 4 5 (此时left和right都指向4)
    // 进入新的循环，将4与pivot(5)比较，left因为满足移动至5，right则依然指向4，因此start至right（也可以说是left - 1）都是小于pivot的，right + 1（也可以说是left）至end都是大于pivot的
    // Case 2: 重合点元素等于pivot
    // 5 5 3 -> 3 5 5 (此时left和right都指向4)
    // 进入新的循环，left和right都因为不满足而继续指向4，因此start至right
    static void QuickSort(int[] a, int start, int end) {
        if (start == end) return;
        int left = start;
        int right = end;
        int pivot = a[start];
        while (true) {
            while (a[left] < pivot) left++;
            while (a[right] > pivot) right--;
            if (left < right) {
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
            else break;
        }
        if (start < left - 1) QuickSort(a, start, left - 1);
        if (right + 1 < end) QuickSort(a, right + 1, end);
    }

    // 依然是递归写法，只不过分为两个独立的方法
    static void QuickSort2(int[] a, int start, int end) {

    }
}
