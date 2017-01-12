package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/1.
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Function Signature:
 * public int countingPrime(n) {...}
 *
 * <Tags>
 * - HashSet 判重
 * - Boolean数组作为标志位：读写速度比HashSet更快
 * - Math: 质数的个数要通过总个数减合数个数得到。
 *
 */
public class E204_Counting_Prime {
    public static void main(String[] args) {
        System.out.println(countPrime(9999999));        // 4946 ms
        System.out.println(countPrime2(9999999));       // 94 ms
        System.out.println(countPrime3(9999999));       // 62 ms
    }

    /** 解法3：解法2的升级版，两次扫描，内循环指针j的取值范围不断缩小。
     *  该算法的学名是Sieve of Eratosthenes，是一个很古老的算法，由古希腊亚里士多德之子尼可马库斯记录。*/
    // i和j不再是乘数1和乘数2。i表示基底，j表示以i为底的结果: i * (i + x) → i*i, i*i + i, i*i + i + i, i*i + i + i + i, ...
    // i的取值范围：[2, sqrt(n)] 线性扫描，不超过根号n。
    // j的取值规律：[i^2, i^2 + i, i^2 + 2i, i^2 + 3i, ... n]，多项式扫描，每一项都比前面一项增加i，不超过n。
    // 由于j的不用每次都从2开始扫描，而是从平方值才开始扫描，因此范围越大，省略的运算越多，效果越好。
    // 例如n = 100，则i的取值范围是[2, 49]，j的取值范围为[2, 100/i]
    // 2 → [4, 98] {4,6,8,10,12,14,16,18,20,22,24,26,28,30...
    // 3 → [9, 99] {9,12,15,18,21,24,27,30...
    // 4 已有
    // 5 → [25, 95] {25,30...
    // ...
    // 不过相比解法2可以直接一次就统计完毕，这里如果在第一个for循环中统计，将统计不全，必须单开一个重新统计。
    static int countPrime3(int n) {
        long start = System.nanoTime();
        boolean[] map = new boolean[n];
        for (int i = 2; i * i < n; i++) {           // 写成 i * i < n 是因为sqrt运算量较大
            if (map[i]) continue;                   // 一旦已经判定过是合数就不再继续
            for (int j = i * i; j < n; j += i)      // j就是
                map[j] = true;
        }
        int count = 0;
        for (int i = 2; i < n; i++) if (!map[i]) count++;       // 必须从2开始统计，因为第0和第1个都不算。
        System.out.println((System.nanoTime() - start)/1000000 + " ms");
        return count;
    }

    /** 解法2：boolean数组去重（标志位），一次扫描，两个指针的取值范围都从2开始。*/
    // 对boolean数组的操作要快于对HashSet的插入。解法2是解法1速度的10倍。
    // 一开始数组所有元素都是false，只要构造出一个合数，就把相应索引的元素置为true。
    // 同样外循环是乘数1，但扫描范围是[i, n)，由于是顺序扫描，因此只要发现false，就认为这是质数，并且在之后把这个质数对应的所有合数都置为true，
    // 因此这里count记录的是质数的个数。
    // 例如n = 100，则i的取值范围是[2, 99]，j的取值范围为[2, 100/i]
    // 2 * [2, 49] {4,6,8,10,12,14,16,18,20,22,24,26,28,30...
    // 3 * [2, 33] {6,9,12,15,18,21,24,27,30...
    // 4 已有
    // 5 * [2, 19] {10,15,20,25,30...
    // ...
    static int countPrime2(int n) {
        long start = System.nanoTime();
        boolean[] map = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {           // 2 to n
            if (map[i]) continue;
            count++;
            for (int j = 2; i * j < n; j++)     // 2 to sqrt n
                map[i * j] = true;
        }
        System.out.println((System.nanoTime() - start)/1000000 + " ms");
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
    // 4 已有
    // 5 * [5, 19] 共19-5+1=15 {25,30,35...
    // 6 已有
    // 7 * [7, 14] 共14-7+1=8  {49,56,63...
    // 8 已有
    // 9 已有
    // 不过这里计算出的合数依然会出现重复，例如2*6=3*4=12, 2*9=3*6=18
    // 只能利用HashSet来做到自动去重。最后HashSet的大小就是给定范围内的所有合数的个数。剩下的就是质数的个数了。
    static int countPrime(int n) {
        long start = System.nanoTime();
        if (n < 2) return 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 2; i * i < n; i++) {           // 选择乘数1：从2开始，遍历至根号n
            if (set.contains(i)) continue;          // 如果乘数1已经是合数，则无需再计算。
            for (int j = i; i * j < n; j++)         // 选择乘数2：从i开始，扩展到相乘等于n
                set.add(i * j);
        }
        System.out.println((System.nanoTime() - start)/1000000 + " ms");
        return n - set.size() - 2;
    }
}
