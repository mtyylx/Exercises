package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/18.
 *
 * Given a non-negative number represented as an array of digits, plus one to the number.
 * The digits are stored such that the most significant digit is at the head of the list.
 *
 * Solution Signature:
 * public int[] plusOne(int[] nums) {...}
 */
public class E66_Plus_One {
    public static void main(String[] args) {
        int[] res = plusOne(new int[] {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9});
        for(int x : res) {
            System.out.print(x + ",");
        }
    }

    // 找加法运算时每一位值变化的规律，本质上是数学。
    // 任何一位只要不是9，那么不管低位发生了多少次进位，都一定会停止在这一位，不用再更新比这一位更高的位了
    // 由低位到高位扫描，遇见元素为9就更新为0，只要遇见第一个不是9的位就结束扫描并返回更新完的数组，
    // 如果第一位就不是9，那么相当于只把第一位加1返回
    // 如果for循环都执行完了，也就是整个数组都扫描一遍了，那就说明这个数组一定是全9数组，
    // 这就需要构造一个比当前全9数组长度大1的全0数组， 把打头的元素置为1即可。
    static int[] plusOne(int[] a) {
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == 9)
                a[i] = 0;
            else {
                a[i]++;
                return a;
            }
        }
        int[] b = new int[a.length + 1];
        b[0] = 1; //只需要把第一位置为1即可
        return b;
    }
}
