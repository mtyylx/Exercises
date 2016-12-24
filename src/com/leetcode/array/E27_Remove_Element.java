package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/8/18.
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 *
 * Example:
 * Given input array nums = [3,2,2,3], val = 3
 * our function should return length = 2, with the first two elements of nums being 2.
 *
 * Hint:
 * Try two pointers.
 * Did you use the property of "the order of elements CAN BE changed"?
 * What happens when the elements to remove are rare?
 *
 * Solution Signature:
 * public int removeElement(int[] nums, int val) {...}
 *
 */
public class E27_Remove_Element {
    public static void main(String[] args) {
        int[] a = {1, 2, 2, 3, 3};
        System.out.println("The New Length is " + removeElement3(a, 2));
        System.out.println(Arrays.toString(a));
    }

    /** 解法3：双指针同向扫描，逆序扫描，结果乱序。Time - o(n), Space - o(1). */
    // 逆序扫描解法：o(n) 优势是如果要删的元素少，则运算量也小，覆盖次数等效于要删除的元素个数。
    // 发现要删除的元素后，将该元素用数组最后一个元素覆盖，并缩小数组长度。这样可以确保缩小数组长度的同时用有用元素替换无用元素。
    static int removeElement3(int[] a, int target) {
        if (a == null || a.length == 0) return 0;
        int len = a.length;
        for (int i = len - 1; i >= 0; i--) {
            if (a[i] == target) a[i] = a[(len--) - 1];
        }
        return len;
    }


    // 双指针解法：o(n)
    // 注意这道题里告诉你：The order of elements can be changed. It doesn't matter what you leave beyond the new length
    static int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0;
        int i = 0;
        // 如果当前元素不是要删除的元素，那么i和j都自增，而且是j覆盖i元素
        // 如果当前元素需要被删除，那么只有j自增，这样可以确保j走的快，i走的慢，i只有在处于不需要删除的元素时才会向前走。
        // i就等于留下来的数组长度
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }

    // 依次平移解法：o(n^2) 优势是可以确保没删除的元素相对位置不变。
    // 要想清楚的是虽然不可能做到不创建新数组而删除数组元素，但是可以通过改变元素顺序，再把想要去掉的元素放在最后，再给出新长度就可以
    // it is impossible to remove an element from the array without making a copy of the array.
    // Remove given val in-place and return the new length.
    static int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0;
        int new_length = nums.length;
        for (int i = nums.length -  1; i >= 0; i--) {
            if (nums[i] == val) {
                for (int j = i; j < new_length - 1; j++) {
                    nums[j] = nums[j + 1];
                }
                new_length--;
            }
        }
        return new_length;
    }
}
