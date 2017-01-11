package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/1.
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Function Signature:
 * public int countingPrime(n) {...}
 */
public class E204_Counting_Prime {
    public static void main(String[] args) {
        System.out.println(countPrime(999999));
        System.out.println(countingPrime2(100));
    }

    // 写法2：扫描至i * i < n，再扫描一遍
    static int countingPrime2(int n) {
        // 一开始全认为是Prime
        boolean[] map = new boolean[n];
        int count = 0;
        // 从2开始标注，因为0和1都不是质数
        // 一开始所有的数都是质数，都可以被用来生成自己的合数
        // 为了避免生成重复的合数，例如2*6=12,3*4=12,4*3=12,6*2=12中前两个没法避免，但后两个可以，只要让i * [i ~ n]范围即可。
        // 为了进一步避免生成重复的合数，i的范围应该控制在[2 ~ sqrt(n)]之间，因为超过了sqrt(n)的i * j一定会大于n，根本不会有结果。
        // 又因为sqrt(n)复杂度高，所以写成了i * i < n。
        for (int i = 2; i * i < n; i++)
            if (!map[i])
                for (int j = 2; i * j < n; j++)
                    map[i * j] = true;

        for (int i = 2; i < n; i++)
            if (!map[i]) count++;

        return count;
    }

    // 写法1：扫描一遍，边扫描边计数
    // 一开始全认为是Prime
    // 从2开始计算质数，并且同时找出这些质数为底的所有合数，即i*j，j范围则是从i到n，因为从0到i的部分会被前辈搞定。
    static int countingPrime(int n) {
        boolean[] map = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (map[i]) continue;
            count++;
            for (int j = 2; i * j < n; j++) map[i * j] = true;
        }
        return count;
    }

    /** 解法1：HashSet去重  */
    // 曲线救国：虽然问的是<质数>的个数，但是显然<非质数>更容易得到，因为只要把任何两个不等于1的正数相乘，得到的一定就是合数。
    // 因此可以从2开始扫描直至给定上限，得到范围内的所有合数，质数的个数自然就是总个数减2（因为不包括1和上限n本身）再减合数的个数。
    // 然后使用HashSet记录所有非质数，重复计算的不能重复计数。
    // 外循环选择第一个乘数：取值从2到根号n
    // 内循环选择第二个乘数：取值从第一个乘数的值开始（避免重复计算），直至两个乘数的积抵达n。
    // 例如n = 100，则i的取值范围为[2,9]，j的取值范围为[i,100/i]
    // 2 * [2, 49] 共49-2+1=48 {4,6,8,10,12,14,16,18...
    // 3 * [3, 33] 共33-3+1=31 {9,12,15,18,21,24,27...
    // 5 * [5, 19] 共19-5+1=15 {25,30,35...
    // 7 * [7, 14] 共14-7+1=8  {49,56,63...
    // 不过这里计算出的合数依然会出现重复，例如2*6=3*4=12, 2*9=3*6=18
    // 只能利用HashSet来做到自动去重。最后HashSet的大小就是给定范围内的所有合数的个数。剩下的就是质数的个数了。
    static int countPrime(int n) {
        if (n < 2) return 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 2; i * i < n; i++) {           // 选择乘数1：从2开始，遍历至根号n
            if (set.contains(i)) continue;          // 如果乘数1已经是合数，则无需再计算。
            for (int j = i; i * j < n; j++)         // 选择乘数2：从i开始，扩展到相乘等于n
                set.add(i * j);
        }
        return n - set.size() - 2;
    }
}
