package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/19.
 *
 * Rotate an array of n elements to the right by k steps.
 *
 * For example,
 * with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 *
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 *
 * Function Signature:
 * public void rotateArray(int[] a, int k) {...}
 */
public class E189_Rotate_Array {
    public static void main(String[] args) {
        int[] test = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        print(test);
        rotateArray(test, 3);
    }

    // time: o(n). space: o(k % a.length). 需要另外建一个辅助数组。
    static void rotateArray2(int[] a, int k) {
        //将划分点右侧的部分拷贝到新数组暂存
        int step = k % a.length;
        int split = a.length - step;
        int[] temp = new int[step];
        for (int i = split; i < a.length; i++) {
            temp[i - split] = a[i];
        }
        //将左侧部分平移至数组尽头
        for (int i = a.length -1; i >= step; i--) {
            a[i] = a[i - step];
        }
        //将暂存部分拷贝至数组开头
        for (int i = 0; i < step; i++) {
            a[i] = temp[i];
        }
        print(a);
    }

    // time: o(n). space: o(1). 优点是只需占用常数存储空间。
    // 找规律（类似于矩阵旋转：旋转90度等效于先转置后逆序行或列）
    // 数组右移n位等效于先<将按n划分的两个子数组自行翻转>再<反翻转整个数组>
    // 同样的，数组左移n位是先翻转整个再各自翻转。
    static void rotateArray(int[] a, int k) {
        int split = a.length - k % a.length;
        reverse3(a, 0, split - 1);
        reverse3(a, split, a.length - 1);
        reverse3(a, 0, a.length - 1);
    }

    // 翻转数组任意区间1：找中点扫描解法（单指针）
    private static void reverse(int[] a, int start, int stop) {
        int center = (stop - start) / 2;
        for (int i = 0; i <= center; i++) {
            int temp = a[start + i];
            a[start + i] = a[stop - i];
            a[stop - i] = temp;
        }
        print(a);
    }

    // 翻转数组任意区间2：双指针法
    private static void reverse2(int[] a, int start, int stop) {
        int temp = 0;
        while (start < stop) {
            temp = a[start];
            a[start] = a[stop];
            a[stop] = temp;
            start++;
            stop--;
        }
        print(a);
    }

    // 翻转数组任意区间3：双指针法 + 异或运算符交换变量内容，无需临时变量
    private static void reverse3(int[] a, int start, int stop) {
        while (start < stop) {
            a[start] ^= a[stop];
            a[stop] ^= a[start];
            a[start] ^= a[stop];
            start++;
            stop--;
        }
        print(a);
    }

    private static void print(int[] a) {
        for (int x : a) {
            System.out.print(x + ",");
        }
        System.out.println("");
    }
}
