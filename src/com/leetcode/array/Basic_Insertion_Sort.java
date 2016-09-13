package com.leetcode.array;

/**
 * Created by LYuan on 2016/9/13.
 *
 * Basic Algorithm: Insertion Sort
 * Time - o(n^2)
 * Space - o(1)
 */
public class Basic_Insertion_Sort {
    public static void main(String[] args) {
        int[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        InsertionSort(a);
    }

    // 易错点1：由于内循环可能会覆盖a[i]的值，因此进入内循环之前需要缓存为current，
    // 且内循环比对a[i]和a[j]时由于a[i]可能已经被覆盖了因此，应该比对的是current和a[j]
    // 易错点2：由于for循环会在idx已经变化的前提下才退出，因此for循环退出时其索引已经变化，需要补偿回来
    // （对于i++就用i - 1补偿，对于j--就用j + 1补偿）
    static void InsertionSort(int[] a) {
        int i, j, current;
        for (i = 0; i < a.length; i++){
            current = a[i];
            for (j = i - 1; j >= 0; j--) {
                if (current < a[j])
                    a[j + 1] = a[j];
            }
            a[j + 1] = current;
        }
    }
}
