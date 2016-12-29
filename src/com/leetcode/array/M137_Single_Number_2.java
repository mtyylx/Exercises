package com.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/12/27.
 * Given an array of integers, every element appears three times except for one. Find that single one.
 *
 * Note: Your algorithm should have a linear runtime complexity. i.e. Time - o(n).
 * Could you implement it without using extra memory? i.e. Space - o(1).
 *
 * Function Signature:
 * public int singleNumber(int[] a) {...}
 *
 * <系列问题>
 * E136 Single Number1: 给定一个数组，元素值成双出现，只有一个值出现了一次，找到这个元素。
 * M137 Single Number2: 给定一个数组，元素值成仨出现，只有一个值出现了一次，找到这个元素。
 * M260 Single Number3: 给定一个数组，元素值成双出现，只有两个值出现了一次，找到这两个元素。
 *
 * <Tags>
 * - Bit Manipulation: Set Subtraction, Set Merge (XOR)
 * 未完全消化。
 *
 */
public class M137_Single_Number_2 {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[] {2, 3, 2, 3, 2, 3, 4}));
        System.out.println(singleNumber2(new int[] {1, 3, 5, 1, 3, 5, 1, 3, 5, 9}));
        System.out.println(singleNumber3(new int[] {1, 3, 5, 1, 3, 5, 1, 3, 5, 9}));
    }

    /** 比特翻转法。Time - o(n), Space - o(1). */
    /** 技巧1：A & (~B) 的含义 */
    // 代表集合的减法：A & (~B) = A - B
    // 只不过这里的“减法”并不是数值上的减，而是逻辑包含关系上的减，表示从A中刨除与B交集的成分，说白了，就是从A中去掉B的成分。
    /** 技巧2：A ^ B 的深层含义 */
    // 两个集合的异或运算，本质上是提取他们不同的地方。
    // 但是进一步来看，异或等效于将A和B的融合在了一起，就像金属溶解在王水中一样，只有用另一种金属才能置换还原出来。
    // 把整个数组都异或在一个数上时，这个数就不再是一个个体，而是融合了整个数组特征的一个集合（Set）
    // 这时候对于这个数的处理虽然并不是那么直观，但是只要想象成对集合的操作，刨除什么、联合什么就都说的通了。

    // 现在回到题目，我们知道这里的整个数组除了一个元素落单之外，其他所有元素都会恰好出现三次。
    // 如果我们把整个数组的所有元素都异或在一起，存到one和two这两个数中，那么one和two就相当于是融合了整个数组特征的两个相同的集合。
    // 为了区分one和two的功能，让one表示扫描到目前为止，只出现了1次的所有元素的“融合体”。
    // 对于那些已经出现3次的元素们，由于异或已经自动成对去重，因此等效于还是只出现了1次，因此都算是融合在了one这个特征集合中。
    // 相比之下，让two表示扫描到目前为止，只出现了2次的所有元素的融合体。
    // (one ^ x) & (~ two) 就表示去掉那些已经达到2次的元素的特征
    // (two ^ x) & (~ one) 就表示去掉那些已经达到3次的元素的特征

    static int singleNumber2(int[] a) {
        int one = 0, two = 0;
        for (int x : a) {
            one = (one ^ x) & (~ two);  // 把每个元素都异或融入one中，并且刨掉two中已有的“成分”
            two = (two ^ x) & (~ one);  // 把每个元素都异或融入two中，并且刨掉one中已有的“成分”
        }
        return one;
    }

    // 另一种写法。
    static int singleNumber3(int[] a) {
        int ones = 0, twos = 0, threes;
        for (int i = 0; i < a.length; i++) {
            twos = twos | ones & a[i];      // twos holds the values that appears twice
            ones = ones ^ a[i];             // ones holds the values that appears once
            threes = ones & twos;           // threes holds the values that appears three times
            ones = ones & ~threes;          // if a[i] appears three times, this will clear ones and twos
            twos = ones & ~threes;
        }
        return ones;
    }

    /** 最简单的HashMap解法，统计出现频率。Time - o(n)，Space - o(n). */
    static int singleNumber(int[] a) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }

        for (int x : map.keySet())
            if (map.get(x) == 1) return x;
        return 0;
    }
}
