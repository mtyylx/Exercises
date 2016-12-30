package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/8/19.
 *
 * Rotate an array of n elements to the right by k steps. (Shift right for K steps)
 *
 * For example,
 * with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 *
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
 * Could you do it in-place with o(1) extra space?
 *
 * Function Signature:
 * public void rotateArray(int[] a, int k) {...}
 *
 * <Tags>
 * - Divide & Conquer
 * - Reverse: Two Pointers
 * - Shifting by swapping
 *
 */
public class E189_Rotate_Array {
    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateArray(a, 3);
        System.out.println(Arrays.toString(a));
        int[] b = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateArray2(b, 3);
        System.out.println(Arrays.toString(b));
        int[] c = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateArray3(c, 3);
        System.out.println(Arrays.toString(c));
        int[] d = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateArray4(d, 3);
        System.out.println(Arrays.toString(d));
    }

    /** 解法4：分治法，非常牛逼。Time - o(n), Space - o(1). */
    // 难点在于用心观察到这里面是存在不断缩小的问题规模这一特征的，以及如何确定每次递归时K的位置。
    // k = 3, split = 4.
    // [1  2  3  4  5  6  7]
    //           ↓        ↓
    // [1  2  3  7  5  6  4]
    //        ↓        ↓
    // [1  2  6  7  5  3  4]
    //     ↓        ↓
    // [1  5  6  7  2  3  4]
    //  ↓        ↓
    // [7  5  6  1  2  3  4], 此时 [1 2 3 4] 已经就位，这部分任务完成。
    // [7  5  6] k = 3 - 4 % 3 = 2
    //  ↓     ↓
    // [6  5  7], 此时[ 7 ] 已经就位
    // [6  5] k = 2 - 1 % 3 = 1
    //  ↓  ↓
    // [5  6], 此时[ 6 ] 已经就位，剩下的元素只剩下1个，无需继续。
    static void rotateArray4(int[] a, int k) {
        if (a.length == 0) return;
        int len = a.length;
        while ((k %= len) > 0 && len > 1) {
            int split = len - k;
            for (int i = 1; i <= split; i++) {
                int val = a[len - i];
                a[len - i] = a[len - i - k];
                a[len - i - k] = val;
            }
            len = k;
            k = len - (split % k);
        }
    }

    /** 解法3：数组翻转。前三种方法中综合性能最佳。Time - o(n), Space - o(1). */
    // 非常有意思的性质：数组右移k位等效于分别对[0, len - k - 1] [len - k, len]两段进行翻转，再翻转整个数组的结果。
    // 类似于矩阵旋转，旋转90度等效于先转置后逆序行或列。
    // 同样的，数组左移n位是先翻转整个再各自翻转。
    // [1  2  3  |  4  5]  [1  2  |  3  4  5]
    //     ↓          ↓       ↓         ↓
    // [3  2  1  |  5  4]  [2  1  |  5  4  3]
    //        ↓                   ↓
    // [4  5  1  2  3]     [3  4  5  1  2]
    static void rotateArray3(int[] a, int k) {
        if (a == null || a.length == 0 || k % a.length == 0) return;     // Avoid divide by zero.
        int split = a.length - k % a.length;
        reverse(a, 0, split - 1);
        reverse(a, split, a.length - 1);
        reverse(a, 0, a.length - 1);
    }

    /** 翻转数组任意区间 解法1：双指针法，推荐解法，不需要计算中点，代码最简洁。 */
    private static void reverse(int[] a, int left, int right) {
        while (left < right) {
            int temp = a[left];
            a[left++] = a[right];   // 自增自减结合在运算过程中，互不影响。爽爽的。
            a[right--] = temp;
        }
    }

    /** 翻转数组任意区间 解法2：双指针法 + XOR。好处是无需临时变量，速度快。 */
    private static void reverse2(int[] a, int left, int right) {
        while (left < right) {
            a[left] ^= a[right];
            a[right] ^= a[left];
            a[left] ^= a[right];
            left++;
            right--;
        }
    }

    /** 翻转数组任意区间 解法3：围绕中点扫描交换 */
    private static void reverse3(int[] a, int start, int stop) {
        int center = (stop - start) / 2;
        for (int i = 0; i <= center; i++) {
            int temp = a[start + i];
            a[start + i] = a[stop - i];
            a[stop - i] = temp;
        }
    }

    /** 解法2：1次平移解法，需要额外空间缓存。Time - o(n), Space - o(k % len). */
    // 解法1和2本质上是同一个解法在空间和时间复杂度上的权衡。
    // [1 2 3 4 5 6 7] k = 3, split = 4
    //       ↘ ↘ ↘ ↘
    // [1 2 3 1 2 3 4]
    // temp = [5 6 7]
    //         ↓ ↓ ↓
    //        [5 6 7 1 2 3 4]
    static void rotateArray2(int[] a, int k) {
        if (a == null || a.length == 0 || k % a.length == 0) return;
        k %= a.length;
        int split = a.length - k;
        int[] temp = new int[k];
        for (int i = split; i < a.length; i++)  temp[i - split] = a[i];
        for (int i = split - 1; i >= 0; i--)    a[i + k] = a[i];           // 需要逆序覆盖
        for (int i = 0; i < temp.length; i++)   a[i] = temp[i];
    }

    /** 解法1：k次平移法，类似于插入排序。Time - o(nk), Space - o(1) */
    // 本质是为了将空间复杂度降至o(1)而做出的牺牲，时间复杂度上升至o(nk)。原本是可以一口气全平移过来了，但是需要额外空间o(k)
    // k = 3
    // [1, 2, 3, 4, 5, 6, 7] 缓存7，其他元素右移一位
    // [7, 1, 2, 3, 4, 5, 6] 缓存6，其他元素右移一位
    // [6, 7, 1, 2, 3, 4, 5] 缓存5，其他元素右移一位
    // [5, 6, 7, 1, 2, 3, 4] 大功告成
    static void rotateArray(int[] a, int k) {
        if (a == null || a.length == 0) return;
        k %= a.length;
        for (int i = 0; i < k; i++) {
            int temp = a[a.length - 1];
            for (int j = a.length - 2; j >= 0; j--)
                a[j + 1] = a[j];
            a[0] = temp;
        }
    }
}
