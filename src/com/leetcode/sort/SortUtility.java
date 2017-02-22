package com.leetcode.sort;

import java.util.Arrays;

/**
 * Created by Michael on 2017/2/17.
 *
 * Provide check method for sorted array.
 */
public class SortUtility {

    private static boolean isSorted(int[] a) {
        if (a == null || a.length < 2) return true;
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) return false;
        return true;
    }

    private static int[] randGen(int len, int range) {
        int[] a = new int[len];
        for (int i = 0; i < a.length; i++)
            a[i] = (int) (range * Math.random());
        return a;
    }

    public static void VerifySortAlgorithm(String methodName) {
        // j 控制取值范围    i 控制序列长度
        for (int range = 2; range < 100; range += 5) {
            for (int len = 1; len < 100; len++) {
                int[] x = randGen(len, range);
                System.out.println("Original: " + Arrays.toString(x));
                SortMethod sm = new SortMethod();
                SortMethod method = sm.getSortMethod(methodName);
                if (method == null) {
                    System.out.println("Invalid Method Name!");
                    return;
                }
                method.sort(x);                                           // 利用多态，动态绑定method的实际对象所具有的方法
                if (!isSorted(x)) {
                    System.out.println("Failed  : " + Arrays.toString(x));
                    return;
                }
                else System.out.println("Passed  : " + Arrays.toString(x));
            }
        }
        System.out.println("--------------------------- All Test Cases Passed! ---------------------------");
    }
}

class SortMethod {

    // Default Constructor
    SortMethod() {}

    // To be override by child method
    public void sort(int[] a) {}

    // Return the ACTUAL SortMethod Object base on given method name.
    SortMethod getSortMethod(String methodName) {
        SortMethod sm;
        switch (methodName) {
            case "insertion": sm = new Basic_Insertion_Sort();
                              break;
            case "selection": sm = new Basic_Selection_Sort();
                              break;
            case "merge"    : sm = new Basic_Merge_Sort();
                              break;
            case "heap"    : sm = new Basic_Heap_Sort();
                              break;
            default: sm = null;
        }
        return sm;
    }
}


