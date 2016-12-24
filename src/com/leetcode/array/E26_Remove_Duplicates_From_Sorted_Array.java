package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/8/19.
 * Given a sorted array, remove the duplicates <in place> such that each element appear only once and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 *
 * For example,
 * Given input array nums = [1,1,2],
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 * It doesn't matter what you leave beyond the new length.
 *
 * Function Signature:
 * public int removeDuplicate(int[] a) {...}
 *
 * <Tags>
 * - Two Pointers: [slow → ... fast → → → ... ]
 *
 */
public class E26_Remove_Duplicates_From_Sorted_Array {
    public static void main(String[] args) {
        int[] a = {1, 1, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 100, 100, 111, 111, 111};
        System.out.println("The New Length is " + removeDuplicate2(a));
        System.out.println(Arrays.toString(a));
        int[] b = {1, 1, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 100, 100, 111, 111, 111};
        System.out.println("The New Length is " + removeDuplicate(b));
        System.out.println(Arrays.toString(b));
    }

    /** 双指针同向扫描（快慢指针），正序扫描。Time - o(n), Space - o(1). */
    // 定义两个指针：
    // <快指针>完整扫描整个数组，
    // <慢指针>则始终指向已去重数组区域边界的下一个插入位置，也表示着已去重部分的长度。即slow指向的元素的<左侧>所有元素都可以保证无重复。
    // 由于第0个元素一定是独特的，因此slow可以从第1个元素开始，这样也省的判断a[fast - 1]是否越界。
    // 边界情况：数组a为空 / 数组a长度为0 / 数组a长度为1
    static int removeDuplicate2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int slow = 1;
        for (int fast = 1; fast < a.length; fast++) {
            if (a[fast] != a[fast - 1])
                a[slow++] = a[fast];
        }
        return slow;
    }

    // 另一种写法：
    // 一般if语句用continue都是因为不希望if下面的语句嵌套太深。使用continue的好处是else和else的大括号不需要写了。对于else里面语句较多的情况比较合适。
    static int removeDuplicate(int[] a) {
        if (a == null || a.length == 0) return 0;
        int slow = 0;
        for (int fast = 1; fast < a.length; fast++) {
            if (a[fast] == a[fast - 1]) continue;
            a[(slow++) + 1] = a[fast];
        }
        return slow + 1;
    }
}
