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
 * Note:
 * You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * Solution Signature:
 * public int shortestWordDistance(String[] list, String a, String b) {...}
 */
public class E243_Shortest_Word_Distance {
    public static void main(String[] args) {
        String[] list = {"a", "b", "c", "a", "b", "c"};
        String a = "a";
        String b = "c";
        System.out.println("The shortest word distance is " + shortestWordDistance(list, a, b));
    }

    // 双指针解法：o(n)
    // 从这种题中可以看到使用双指针解法的场景所具备的一些共性：都是对数组操作，而且一般是要在遍历整个数组的同时比较两个东西。
    // 只扫描一次，且有一定几率扫描到中间直接退出
    // 扫描数组，两个指针只有在匹配到单词时才会更新，且只要两个单词都匹配到就开始每个循环都比较两指针距离
    static int shortestWordDistance(String[] list, String a, String b) {
        int i = -1; // 需要区分已找到和未找到两种状态，必须两个单词都找到（不等于-1）才能开始比较计算距离
        int j = -1;
        int diff = Integer.MAX_VALUE; // 必须是一个极大值，大于所有可能出现的差值，才能找到最小的距离
        for (int x = 0; x < list.length; x++) {
            if (list[x].equals(a)) i = x;
            if (list[x].equals(b)) j = x;
            if (i != -1 && j != -1) diff = Math.min(diff, Math.abs(i - j));
            if (diff == 1) return 1; //如果距离已经为1，则不会有更近的距离出现，因此没必要继续扫描下去。
        }
        return diff;
    }
}
