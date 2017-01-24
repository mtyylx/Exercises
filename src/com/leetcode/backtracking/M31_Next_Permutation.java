package com.leetcode.backtracking;

import java.util.Arrays;

/**
 * Created by Michael on 2017/1/23.
 *
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
 * Note: The replacement must be in-place, do not allocate extra memory.
 *
 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
 * Since the total permutations for {1, 2, 3} are:
 *  ↓ 1 2 3 ←
 *  ↓ 1 3 2  |
 *  ↓ 2 1 3  |
 *  ↓ 2 3 1  |
 *  ↓ 3 1 2  |
 *  ↓ 3 2 1 →
 *
 * Thus,
 * 1,2,3 next → 1,3,2
 * 3,2,1 next → 1,2,3
 * 1,1,5 next → 1,5,1
 *
 * Function Signature:
 * public void nextPermutation(int[] a) {...}
 *
 * <Tags>
 * - 利用已排序数组的性质，即使是部分已排序的数组也很有用。
 *
 */
public class M31_Next_Permutation {
    public static void main(String[] args) {
        int[] a = {5, 1, 1};
        for (int i = 0; i < 24; i++) {
            nextPermutation(a);
            System.out.println(Arrays.toString(a));
        }
    }

    /** 解法2：找规律 (以右为上) + 局部交换 + 局部反转。Time - o(n), Space - o(1). */
    // 首先要搞清楚Lexicographic顺序排列的特征是<以右为上>。即尽可能从最右端开始交换元素。
    // 例如{1,2,3}的下一个排列是{1,3,2}，而不是{2,1,3}
    // 例如{3,7,2,5}的下一个排列是{3,7,5,2}，而不是{7,3,2,5}
    // 然后针对三种情况进行分析：
    // Case#1 如果数组是<完全降序>排列的（相等元素也算降序），例如{7,4,2}，说明当前状态已经是最后一个Permutation，需要回到第一个Permutation
    // Case#2 如果数组是<完全升序>排列的，例如{3,4,6,9}，那么只需要交换最后两个元素即可得到下一个Permutation。
    // Case#3 如果数组是<部分降序>排列的，需要分成三步运算：
    /** 第一步：定位。找到从右向左<首次违反降序排列>的元素位置i。*/
    // 例：{6 8 | 9 7 4 1} a[1] = 8 是第一个违反降序的元素
    //    {1 4 2 8 5 | 7} a[4] = 5 是第一个违反降序的元素
    //    {8 | 9 8 6 4 2} a[0] = 8 是第一个违反降序的元素
    /** 第二步：交换。从最右边向左找到<第一个比i大的元素j>，将i与j的值交换。*/
    // 这么做本质是要找到i右侧数组中比i大的最小元素，由于i右侧都是降序排列的，因此只要从右向左扫描，遇到的第一个比i大的元素就一定是比i大的最小元素。
    // 交换元素后，i右侧区间内的元素一定依然是完全降序排列的，因为把i与第一个比i大的元素交换，本质上就是把i插入了右侧降序数组中并确保整个数组依然降序排列的操作。
    // 例：{6 8 | 9 7 4 1} -> {6 9 | 8 7 4 1}
    //    {1 4 2 8 5 | 7} -> {1 4 2 8 7 | 5}
    //    {8 | 9 8 6 4 2} -> {9 | 8 8 6 4 2}
    /** 第三步：反转。将i右侧区间的<所有元素按轴对称反转>。*/
    // 这么做其实是为了让i的右侧区间恢复升序排列，以得到下一个Permutation。由于上面已经知道交换元素之后右侧区间是完全降序排列的，因此并不需要真正的排序，只需要翻转即可。
    // 例：{6 9 | 8 7 4 1} -> {6 9 | 1 4 7 8}
    //    {1 4 2 8 7 | 5} -> {1 4 2 8 7 | 5}
    //    {9 | 8 8 6 4 2} -> {9 | 2 4 6 8 8}
    static void nextPermutation(int[] a) {
        if (a == null || a.length < 2) return;
        int i = a.length - 2;                       // 让i从倒数第二个元素开始向前扫描，这样只有数组是完全降序排列时，i才会停在-1
        int j = a.length - 1;
        while (i >= 0 && a[i] >= a[i + 1]) i--;     // 第一步：定位i。只有当a[i]小于a[i+1]时i才会停下来。
        if (i >= 0) {
            while (a[j] <= a[i]) j--;               // 第二步：定位j。
            swap(a, i, j);                          // 第二步：交换i和j。
        }
        reverse(a, i + 1, a.length - 1);        // 第三步：翻转i的右侧区间。
    }

    private static void reverse(int[] a, int i, int j) {
        while (i < j) swap(a, i++, j--);
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /** 解法1：Value-As-Index + Backtracking + 匹配。 Time - o(n!). */
    // 最简单的思路是根据数组的元素生成全排列，然后将数组于全排列每一个比较，找到自己在全排列中的位置，返回下一个排列即可，
    // 但是这样的空间复杂度和实践复杂度都很高。因为全排列的个数于数组长度之间是阶乘的关系，比指数上升还要快。
}
