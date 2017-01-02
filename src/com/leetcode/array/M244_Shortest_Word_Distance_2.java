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
 * Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * Function Signature:
 * public int shortestWordDistance(String a, String b) {...}
 *
 * <系列问题>
 * E243 Shortest Word Distance 1: 给定一个字符串数组，以及该数组中的两个字符串（不重复），求它们的最短距离。
 * M244 Shortest Word Distance 2: 给定一个字符串数组，反复给出该数组中的任意两个的字符串（不重复），求它们的最短距离。
 * M245 Shortest Word Distance 3: 给定一个字符串数组，以及该数组中的两个字符串（可能重复），求它们的最短距离。
 *
 * <Tags>
 * - HashTable
 * - Scan Two Sorted Arrays
 * - Two Pointers: i → → → ... j → → →
 *
 */
public class M244_Shortest_Word_Distance_2 {
    public static void main(String[] args) {
        WordDistance wd = new WordDistance(new String[]{"a", "b", "c", "a", "d", "b", "c", "b", "b", "a"});
        System.out.println(wd.ShortestDistance2("a", "b"));
    }
}

/** 解法1：哈希表记录同一字符串的所有出现位置 + 双指针扫描. 与E243的思路完全不同。
 *  Constructor Time - o(n), Space - o(n)
 *  Method Time - o(m + n), Space - o(n). 其中m和n分别为两个字符串的出现次数。
 */
class WordDistance {
    Map<String, List<Integer>> map = new HashMap<>();

    // 为了在之后的方法中快速的识别任何字符串的出现位置，需要在构造器里就对整个字符串列表进行扫描
    // 并使用HashMap保存每一个字符串出现的所有位置索引，这些索引存成一个List
    public WordDistance(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (!map.containsKey(words[i])) map.put(words[i], new ArrayList<>());   // 只有之前没遇到过，才需要创建新List
            map.get(words[i]).add(i);                                               // 简化代码，不管之前是否遇到，都要添加当前的索引
        }
    }

    /** 双指针扫描 + 利用已排序数组的性质 */
    // 由于每个字符串的位置索引列表一定是已排序的（因为是按顺序扫描的）
    // 因此可以利用这个性质来移动两个指针中的一个，避免无用的结果
    // idx1 = [0   20   25   40]
    //         i →                  此时由于j所指元素大于i所指元素，如果右移j而不是i，
    //         j →                  那么差值一定会进一步增大，因此为了让差值最小，合理的选择是移动i。
    // idx2 = [10  12   24   30   32]
    public int ShortestDistance2(String a, String b) {
        List<Integer> idx1 = map.get(a);
        List<Integer> idx2 = map.get(b);
        int i = 0, j = 0, min = Integer.MAX_VALUE;
        while (i < idx1.size() && j < idx2.size()) {               // 因为我们求得是差值，因此并不需要完整扫描两个数组，只要一个扫完了另一根本不用继续
            min = Math.min(min, Math.abs(idx1.get(i) - idx2.get(j)));
            if (min == 1) return 1;                     // early exit
            if (idx1.get(i) < idx2.get(j)) i++;         // 利用已排序数组的性质
            else j++;
        }
        return min;
    }

    /** 双循环解法：没有根据元素大小关系进行有选择的遍历。Time - o(n * m)  */
    public int ShortestDistance(String a, String b) {
        List<Integer> idx1 = map.get(a);
        List<Integer> idx2 = map.get(b);
        int min = Integer.MAX_VALUE;
        for (int x : idx1)
            for (int y : idx2)
                min = Math.min(min, Math.abs(x - y));
        return min;
    }
}
