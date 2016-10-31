package com.leetcode.dp;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/10/31.
 * Given a non negative integer number num.
 * For every numbers i in the range 0 ≤ i ≤ num,
 * calculate the number of 1's in their binary representation and return them as an array.
 *
 * Example: For num = 5 you should return [0,1,1,2,1,2].
 *
 * Follow up:
 * It is very easy to come up with a solution with run time O(n*sizeof(integer)).
 * But can you do it in linear time O(n) /possibly in a single pass? Space complexity should be O(n).
 * Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
 *
 * Hint:
 * 1. You should make use of what you have produced already.
 * 2. Divide the numbers in ranges like [2-3], [4-7], [8-15] and so on. And try to generate new range from previous.
 * 3. Or does the odd/even status of the number help you in calculating the number of 1s?
 *
 * Function Signature:
 * public int[] countBits(int n) {...}
 */
public class M338_Counting_Bits {
    public static void main(String[] args) {
        for(int i = -200; i < 200; i++) {
            int x = i >> 1;
            System.out.println(x + ", " + i / 2);
            if (x != i / 2) System.out.println(false);
        }
        System.out.println(Arrays.toString(countBits2(16)));
    }

    /** DP解法，Iterative，Time - o(n) */
    // 状态的转移体现在循环周期的变化上，range长度从1开始，以2的幂次扩张，而元素内容则一定是同位置上一range的值加1。
    // 0 # 1
    // 0 1 # 1 2
    // 0 1 1 2 # 1 2 2 3
    // 0 1 1 2 1 2 2 3 # 1 2 2 3 2 3 3 4
    // 外循环扫描用来生成新的range，内循环用来将上个range内的所有元素加一拷贝至索引所在元素。
    static int[] countBits(int n) {
        int[] result = new int[n + 1];
        int range = 1;
        int i = 1;
        while (i <= n) {
            for (int j = 0; j < range && i <= n; j++, i++)
                result[i] = result[j] + 1;
            range *= 2;
        }
        return result;
    }

    /** 利用除2和模2在二进制数值上的特性 */
    // 性质一：对于正数，i / 2 == i >> 1
    // 1000 / 2 = 0100，1001 / 2 = 0100
    // 因此任何比特位的1都等效于平移至最低位的1的个数
    // 1000 = 0100 = 0010 = 0001
    // 性质二：i % 2 == i的最低位值
    // 1011 % 2 = 1，1010 % 2 = 0
    // 同时由于任何数模2只会有0和1的可能，回复被除2过程忽视掉的最低为1
    // 除2会消除原数值的最低位1：例如011/2后等效于001，但是实际上应该比001多一个1，这个1在除的过程中被右移清理了。
    // 结合除2和模2的特性，就可以得到任何数值所含的1bit个数了。
    static int[] countBits2(int n) {
        int[] result = new int[n + 1];
        for(int i = 1; i <= n; i++){
            result[i] = result[i / 2];
            if(i % 2 == 1) result[i]++;
        }
        return result;
    }

    /** Bit Manipulation，使用比特反转法达到上面解法同样的效果 */
    // i >>> 1 等效于 i / 2
    // i & 1   等效于 i % 2
    static int[] countBits3(int n) {
        int[] result = new int[n + 1];
        for (int i = 1; i <= n; i++)
            result[i] = result[i >>> 1] + (i & 1);
        return result;
    }
}
