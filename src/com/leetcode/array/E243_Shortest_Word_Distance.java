package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/19.
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 *
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * Given word1 = "coding", word2 = "practice", return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 *
 * Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * Function Signature:
 * public int shortestWordDistance(String[] list, String a, String b) {...}
 *
 * 可以扩展为任意类型的数组，给定数组中的两个元素，求他们的最近距离。
 *
 * <Tags>
 * - Two Pointers: i → → → ... j → → →
 * - Sentinel: Use <-1> as protect index.
 *
 */
public class E243_Shortest_Word_Distance {
    public static void main(String[] args) {
        System.out.println(wordDistance(new String[] {"a", "b", "c", "a"}, "a", "c"));
    }

    /** 解法1：双指针同向扫描，一个指针管一个值。Time - o(n), Space - o(1). */
    // 使用双指针解法题目的共性：需要同时跟踪两个对象（这里是两个单词）的出现，直到遍历完整个数组。
    // 由于题目确保两个单词一定在数组中，因此我们只需要在发现两个单词之中的一个的时候，更新距离最小值即可。
    // 一个技巧是两个指针初始默认值的设计，为了确保两个单词都找到才比较，我们需要能够判断是否已经找到该单词，
    // 因此负数的索引可以很清晰的表示该指针代表的单词还未找到
    static int wordDistance(String[] list, String a, String b) {
        int min = Integer.MAX_VALUE;
        int ai = -1, bi = -1;
        for (int i = 0; i < list.length; i++) {
            if      (list[i].equals(a)) ai = i;
            else if (list[i].equals(b)) bi = i;
            else continue;                                  // 如果当前单词谁都不是就跳过计算距离
            if (ai >= 0 && bi >= 0)
                min = Math.min(min, Math.abs(ai - bi));     // 如果当前单词是两者之一，就尝试更新最小距离。
        }
        return min;
    }
}
