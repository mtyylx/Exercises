package com.leetcode.array;

/**
 * Created by Michael on 2017/1/1.
 *
 * This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 * word1 and word2 may be the same and they represent two individual words in the list.
 *
 * For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * Given word1 = “makes”, word2 = “coding”, return 1.
 * Given word1 = "makes", word2 = "makes", return 3.
 *
 * Note: You may assume word1 and word2 are both in the list.
 *
 * Function Signature:
 * public int wordDistance(String[] words, String a, String b) {...}
 *
 * <系列问题>
 * E243 Shortest Word Distance 1: 给定一个字符串数组，以及该数组中的两个字符串（不重复），求它们的最短距离。
 * M244 Shortest Word Distance 2: 给定一个字符串数组，反复给出该数组中的任意两个的字符串（不重复），求它们的最短距离。
 * M245 Shortest Word Distance 3: 给定一个字符串数组，以及该数组中的两个字符串（可能重复），求它们的最短距离。
 *
 * <Tags>
 * - Two Pointers: i → → → ... j → → →
 *
 */
public class M245_Shortest_Word_Distance_3 {
    public static void main(String[] args) {
        System.out.println(wordDistance(new String[] {"a", "b", "c", "a"}, "a", "c"));
        System.out.println(wordDistance2(new String[] {"a", "b", "c", "a"}, "a", "a"));
    }


    /** 解法2：干脆两种情况分别处理 */
    static int wordDistance2(String[] words, String a, String b) {
        int min = Integer.MAX_VALUE;
        if (a.equals(b)) {
            int prev = -1;
            for (int i = 0; i < words.length; i++) {
                if (!words[i].equals(a)) continue;
                if (prev != -1) min = Math.min(min, Math.abs(i - prev));  // 如果匹配且不是第一次匹配，就更新距离
                prev = i;
            }
        }
        else {
            int prevA = -1, prevB = -1;
            for (int i = 0; i < words.length; i++) {
                if      (words[i].equals(a)) prevA = i;
                else if (words[i].equals(b)) prevB = i;
                else continue;
                if (prevA != -1 && prevB != -1) min = Math.min(min, Math.abs(prevA - prevB));
            }
        }
        return min;
    }

    /** 解法1：基于E243的方法。当两个单词不同时，用双指针 ai / bi；当两个单词相同时，用单指针 prev。*/
    static int wordDistance(String[] words, String a, String b) {
        int min = Integer.MAX_VALUE;
        int ai = -1, bi = -1, prev = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(a) && words[i].equals(b)) { ai = i; bi = i; }   // 如果a与b相同，则双指针永远同时更新
            else if (words[i].equals(a)) ai = i;
            else if (words[i].equals(b)) bi = i;
            else continue;
            if (ai != -1 && ai == bi) {                                     // 如果指针相同就说明两个单词相同
                if (prev != -1) min = Math.min(min, Math.abs(ai - prev));   // 如果prev已经遇到过，就更新距离
                prev = ai;                                                  // 更新prev
            }
            else if (ai != -1 && bi != -1) min = Math.min(min, Math.abs(ai - bi));  // 如果指针不同就退化成为E243的解法。
        }
        return min;
    }
}
