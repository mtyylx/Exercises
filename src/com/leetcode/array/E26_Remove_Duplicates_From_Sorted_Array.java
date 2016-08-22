package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/19.
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 *
 * For example,
 * Given input array nums = [1,1,2],
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 * It doesn't matter what you leave beyond the new length.
 *
 * Function Signature:
 * public int removeDuplicate(int[] a) {...}
 */
public class E26_Remove_Duplicates_From_Sorted_Array {
    public static void main(String[] args) {
        int[] a = {1, 1, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 100, 100, 111, 111, 111};
        System.out.println("The New Length is " + removeDuplicate2(a));
        for (int x : a) System.out.print(x + ",");
    }

    // 正序扫描解法，可以保证去重后数组依然有序
    // 这两种解法都节省了一个指针，即当前数组的有效长度。因为这个长度本身的自增可以通过判断相邻元素是否相同而决定。
    static int removeDuplicate2(int[] a) {
        if (a.length < 2) return a.length;
        int tail = 1;   //从数组的第二个元素（idx = 1）开始判断，此时有效长度为1
        for (int i = 1; i < a.length; i++) {
            // 只有相邻元素不同时，才会把当前元素拷贝至当前数组的最后一个元素，并扩展当前数组长度。
            if (a[i] != a[i - 1]) {
                a[tail] = a[i];
                tail++;
            }
        }
        return tail;
    }

    // 逆序扫描解法，不足是去重之后的数组会乱序
    // 思路是从尾部缩小数组有效长度，因此判断相邻元素是否相同。
    // 上面的算法则是从头部扩增有效长度，因此判断的是相邻元素是否不同。
    static int removeDuplicate(int[] a) {
        int newlength = a.length;
        int i = newlength - 1;
        // 只有当上一个元素与当前元素相同时，才把当前元素与当前长度最后一个元素交换，并缩小当前数组长度
        while (i > 0) {
            if (a[i - 1] == a[i]) {
                a[i] = a[newlength - 1];
                newlength--;
            }
            i--;
        }
        return newlength;
    }
}
