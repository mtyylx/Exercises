package com.leetcode.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LYuan on 2016/9/6.
 *
 * This is a follow up of Shortest Word Distance.
 * The only difference is now you are given the list of words,
 * and your method will be called repeatedly many times with different parameters.
 * How would you optimize it?
 * Design a class which receives a list of words in the constructor,
 * and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list.
 *
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * Given word1 = “coding”, word2 = “practice”, return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 *
 * Note:
 * You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * Function Signature:
 * public int shortestWordDistance(String a, String b) {...}
 *
 * */
public class M244_Shortest_Word_Distance_2 {
    public static void main(String[] args) {
        WordDistance wd = new WordDistance(new String[]{"a", "b", "c", "a", "d", "b", "c", "b", "b", "a"});
        System.out.println(wd.ShortestDistance2("a", "b"));
    }
}

class WordDistance {
    Map<String, List<Integer>> map = new HashMap<>();
    public WordDistance(String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (map.containsKey(list[i])) map.get(list[i]).add(i);
            else {
                List<Integer> row = new ArrayList<>();
                row.add(i);
                map.put(list[i], row);
            }
        }
    }

    // 双指针解法，o(m + n)
    // a: 0, 3, 9
    // b: 1, 5, 7, 8
    // [0, 1]  [3, 1]  [3, 5]  [9, 5]  [9, 7]  [9, 8]
    // 我们不需要剩下的6种组合，只会比这6种差值更大。
    public int ShortestDistance2(String a, String b) {
        List<Integer> idx1 = map.get(a);
        List<Integer> idx2 = map.get(b);
        int result = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        while (i < idx1.size() && j < idx2.size()) {
            result = Math.min(result, Math.abs(idx1.get(i) - idx2.get(j)));
            if      (idx1.get(i) < idx2.get(j)) i++;
            else    j++;
        }
        return result;
    }

    // 双循环解法，o(m * n)
    // 但是实际上双循环遍历的时候，很多组合是没有意义的，我们只想要要两者最小的结果
    public int ShortestDistance(String a, String b) {
        List<Integer> idx1 = map.get(a);
        List<Integer> idx2 = map.get(b);
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < idx1.size(); i++) {
            for (int j = 0; j < idx2.size(); j++) {
                result = Math.min(result, Math.abs(idx1.get(i) - idx2.get(j)));
            }
        }
        return result;
    }
}
