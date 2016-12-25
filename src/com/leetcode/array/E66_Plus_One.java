package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/8/18.
 *
 * Given a non-negative number represented as an array of digits, plus one to the number.
 * The digits are stored such that the most significant digit is at the head of the list.
 *
 * Solution Signature:
 * public int[] plusOne(int[] nums) {...}
 *
 * <Tags>
 * - Math: Carry
 *
 */
public class E66_Plus_One {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(plusOne(new int[] {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9})));
        System.out.println(Arrays.toString(plusOne2(new int[] {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9})));
    }

    /** 最佳解法：利用性质提前结束（Early Exit），无需每次都扫描整个数组。 */
    // 最初的解法没有认识到一个性质，就是只要当前位的carry为0，那么再往前的元素就不会变化了，因此应该可以Early Exit。
    // 任何一位只要不是9，那么不管低位发生了多少次进位，都一定会停止在这一位，不用再更新比这一位更高的位了
    // 由低位到高位扫描，遇见元素为9就更新为0，只要遇见第一个不是9的位就结束扫描并返回更新完的数组，
    // 如果第一位就不是9，那么相当于只把第一位加1返回
    // 如果for循环都执行完了，也就是整个数组都扫描一遍了，那就说明这个数组一定是全9数组，
    // 这就需要构造一个比当前全9数组长度大1的全0数组，把打头的元素置为1即可。
    static int[] plusOne2(int[] a) {
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == 9)
                a[i] = 0;
            else {
                a[i]++;         // 如果这一位不是9，那么只自增该位就可以返回结果了。因为不是9的话，即使自增了，上一位也不会得到carry，也就不会再有更新了。
                return a;
            }
        }
        int[] b = new int[a.length + 1];        // 如果走到这说明已经扫描了整个数组，需要扩容。
        b[0] = 1;
        return b;
    }

    /** 初始解法：使用carry来记录进位。如果全9则新建数组。 */
    // 特性1：只有全9数组才会需要扩容长度。例如999+1=1000，989+1=990
    // 特性2：扩容长度后的新数组应该是一个1，剩下全是0的数组。
    static int[] plusOne(int[] a) {
        if (a == null || a.length == 0) return a;
        int carry = 1;                                  // 只需要把carry初始值设为1就可以不需要单独处理个位数自增的问题了。
        for (int i = a.length - 1; i >= 0; i--) {
            a[i] += carry;
            if (a[i] > 9) a[i] = 0;
            carry = (a[i] == 0) ? 1 : 0;
        }
        if (carry == 0) return a;
        int[] b = new int[a.length + 1];    // 注意此时a的所有位一定全为0。因此并不需要把a拷贝至新数组b中。只需要把第0个元素置为1即可。
        b[0] = 1;
        return b;
    }
}
