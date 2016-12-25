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
 * What happens when the elements     to remove are rare? 解法3更好：只交换目标元素。
 * What happens when the elements NOT to remove are rare? 解法2更好：只交换非目标元素。
 * What happens when the order need to remain the same?   解法1,2更好：可确保顺序不变。
 *
 * Solution Signature:
 * public int removeElement(int[] nums, int val) {...}
 *
 * <Tags>
 * - Two Pointers: [slow → ... fast → → → ... ]
 * - Two Pointers: [ ... ← ← ← fast ... ← slow ]
 *
 */
public class E27_Remove_Element {
    public static void main(String[] args) {
        int[] a = {2, 3, 1, 4, 2, 7, 2};
        System.out.println("The New Length is " + removeElement3(a, 3));
        System.out.println(Arrays.toString(a));
        int[] b = {2, 3, 1, 4, 2, 7, 2};
        System.out.println("The New Length is " + removeElement2(b, 3));
        System.out.println(Arrays.toString(b));
        int[] c = {2, 3, 1, 4, 2, 7, 2};
        System.out.println("The New Length is " + removeElement(c, 3));
        System.out.println(Arrays.toString(c));
    }

    /** 解法3：双指针同向扫描（快慢指针），逆序扫描，不维持原有排列顺序。Time - o(n), Space - o(1). */
    // [ ... ← ← ← fast ... ← slow ]
    // 利用结果无需保持原有排列顺序的特点，使用<一次交换>而不是<多次平移>来删除目标元素。
    // 交换（覆盖）次数等于要删除的元素个数。因此适合于目标值零星出现在数组中的情况。
    // 发现要删除的元素后，将该元素用有效长度数组最后一个元素覆盖，并缩小数组长度。
    // len = slow, i = fast
    static int removeElement3(int[] a, int target) {
        if (a == null || a.length == 0) return 0;
        int len = a.length;
        for (int i = len - 1; i >= 0; i--) {
            if (a[i] == target) a[i] = a[(len--) - 1];
        }
        return len;
    }


    /** 解法2：双指针同向扫描（快慢指针），正序扫描，可以维持原有排列顺序。Time - o(n), Space - o(1) */
    // [slow → ... fast → → → ... ]
    // 同样使用<一次交换>而不是<多次平移>来删除目标元素，相比于解法3，可以确保不改变原来的排列顺序。
    // 此法的交换（覆盖）次数等于无需被删除的元素的个数。适合处理目标值在数组中出现次数远多于其他值的情况。
    // slow指示数组未扫描区域的起始点，如果fast不需要删除，就用fast所指元素覆盖该起始点，并向前移动。如果fast需要删除，则只移动fast。
    static int removeElement2(int[] a, int target) {
        if (a == null || a.length == 0) return 0;
        int slow = 0;
        for (int fast = 0; fast < a.length; fast++) {
            if (a[fast] != target) a[slow++] = a[fast];
        }
        return slow;
    }


    /** 解法1：双指针依次平移解法，类似于插入排序的思路，逆序扫描。可以保持原有排列顺序不变。Time - o(n^2), Space - o(1) */
    // [ ... ← ← ← fast ... ← slow ]
    // len = slow, i = fast
    // 如果当前元素就是目标元素，即a[i] == target，则依次将第(i + 1)个元素至第len - 1个元素拷贝至前一个元素。
    static int removeElement(int[] a, int target) {
        if (a == null || a.length == 0) return 0;
        int len = a.length;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == target) {
                len--;
                for (int j = i; j < len; j++) a[j] = a[j + 1];      // 依次平移
            }
        }
        return len;
    }
}
