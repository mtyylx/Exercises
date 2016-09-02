package com.leetcode.hashtable;

/**
 * Created by LYuan on 2016/9/1.
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Function Signature:
 * public int countingPrime(n) {...}
 */
public class E204_Counting_Prime {
    public static void main(String[] args) {
        System.out.println(countingPrime2(100));
    }

    // 写法2：扫描至i * i < n，再扫描一遍
    static int countingPrime2(int n) {
        // 一开始全认为是Prime
        boolean[] notPrime = new boolean[n];
        int count = 0;
        // 从2开始标注，因为0和1都不是质数
        // 一开始所有的数都是质数，都可以被用来生成自己的合数
        // 为了避免生成重复的合数，例如2*6=12,3*4=12,4*3=12,6*2=12中前两个没法避免，但后两个可以，只要让i * [i ~ n]范围即可。
        // 为了进一步避免生成重复的合数，i的范围应该控制在[2 ~ sqrt(n)]之间，因为超过了sqrt(n)的i * j一定会大于n，根本不会有结果。
        // 又因为sqrt(n)复杂度高，所以写成了i * i < n。
        for (int i = 2; i * i < n; i++)
            if (!notPrime[i])
                for (int j = 2; i * j < n; j++)
                    notPrime[i * j] = true;

        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
                System.out.println(i + ": Prime");
            } else System.out.println(i);
        }
        return count;
    }

    // 写法1：扫描一遍，边扫描边计数
    // 一开始全认为是Prime
    // 从2开始计算质数，并且同时找出这些质数为底的所有合数，即i*j，j范围则是从i到n，因为从0到i的部分会被前辈搞定。
    static int countingPrime(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
                for (int j = 2; i * j < n; j++)
                    notPrime[i * j] = true;
            }
        }
        return count;
    }
}
