package com.leetcode.array;

import java.util.*;

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
 * - Two Pointers: Scan Two Sorted Arrays
 * - HashTable: Use <ArrayList> as Values
 * - HashTable: Use <String Concat> as Keys (Store 'Triplets')
 * - String: Use CompareTo() to determine order.
 *
 */
public class M244_Shortest_Word_Distance_2 {
    public static void main(String[] args) {
        WordDistance wd = new WordDistance(new String[]{"a", "b", "c", "a", "d", "b", "c", "b", "b", "a"});
        System.out.println(wd.ShortestDistance2("a", "b"));
        WordDistanceII wd2 = new WordDistanceII(new String[] {"practice", "makes", "perfect", "coding", "makes"});
        System.out.println(wd2.shortestDistance("coding", "makes"));
        WordDistanceIII wd3 = new WordDistanceIII(new String[] {"practice", "makes", "perfect", "coding", "makes"});
        System.out.println(wd3.shortestDistance("makes", "coding"));
    }
}

/** 解法1：哈希表记录同一字符串的所有出现位置 + 双指针扫描. 与E243的思路完全不同。
 *  Constructor Time - o(n), Space - o(n)
 *  Method Time - o(m + n), Space - o(n). 其中m和n分别为两个字符串的出现次数。
 *  巧妙的使用ArrayList作为HashMap键值对的值类型
 *  相当于是在构造器里面整理出来一个半成品的统计模型（每个字符串的出现位置列表），然后每次调用Shortest时会用这个半成品计算出最后的结果。
 *  构造半成品只需要o(words.length)，之后每次调用Shortest方法需要o(n+m)，注意这里n和m只是字符串出现的次数，一定是远小于字符串数组的长度的。
 *  因此这种方法比E243更好，因为E243中调用Shortest方法的时间复杂度每次都是o(words.length)。
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

    /** 双指针扫描 + 利用已排序数组的性质。Time - o(n + m) */
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

/** 如果题目明确表示，<调用shortest方法的次数>会远远大于<字符串数组的长度>，那么效率更高的方法有两种：
 *
 *  方法一：在构造器中完成所有字符串之间的距离计算，Time - o(n^2)，之后调用shortest方法只是查询结果而已，Time - o(1)
 *  这个方法的问题在于，如果shortest方法只使用了全集中一部分，那么构造器中的运算其实是多余的。因此虽然思路清晰，但是并不是最好的方法。
 *
 *  方法二：每调用一次shortest方法就把结果存下来，类似于DP的备忘
 *  问题在于，如何设计一个访问速度很快的三元组数据结构，把两个字符串和他们之间的距离存起来，以供之后在o(1)内查到。
 *  由于HashMap只能做到存储二元组，因此我们不得不把两个字符串合并在一起，作为一个字符串的Key
 *  这里又涉及到顺序问题，我们肯定希望调用("aaa","bbb")和("bbb", "aaa")时找同一个记录而不是两个
 *  因此需要用到字符串的顺序比较工具：compareTo，固定下来一个先后顺序，只要反了就小递归一下。
 *
 *  经过上面的问题，我们可以看到，<其实所谓的三元组是伪命题>。如果想要用字典的方式查询一一对应关系，那么必须是二元组。
 *  这里唯一的区别是：这个二元组的Key值是两个字符串，表达的是两个字符串之间的关系。因此最简单的办法就是把两个字符串合并。
 */

/** 解法2：使用第二个HashMap存储已计算结果。*/
class WordDistanceIII {
    private Map<String, Integer> distance = new HashMap<>();
    private Map<String, List<Integer>> map = new HashMap<>();

    public WordDistanceIII(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (!map.containsKey(words[i])) map.put(words[i], new ArrayList<>());
            map.get(words[i]).add(i);
        }
    }

    // 沿用解法一的双指针解法。控制顺序，将"a + b"作为key。
    public int shortestDistance(String a, String b) {
        if (a.compareTo(b) > 0) return shortestDistance(b, a);      // 用compareTo确定两个字符串字典顺序的大小，如果顺序反了就递归
        String key = a + "+" + b;                                   // 将两个字符串合并为一个作为Key
        if (distance.containsKey(key)) return distance.get(key);
        List<Integer> idx1 = map.get(a);
        List<Integer> idx2 = map.get(b);
        int i = 0, j = 0, min = Integer.MAX_VALUE;
        while (i < idx1.size() && j < idx2.size()) {
            min = Math.min(min, Math.abs(idx1.get(i) - idx2.get(j)));
            if (idx1.get(i) < idx2.get(j)) i++;
            else j++;
        }
        distance.put(key, min);
        return min;
    }
}

/** 对比上面的解法，这里使用E243的解法。可以看到时间复杂度明显增大。 */
class WordDistanceII {
    private String[] words;
    private Set<String> set;
    private Map<String, Integer> distance = new HashMap<>();    // 记住每次计算的结果：Key为两个字符串的唯一序号构成的字符串，Value为最小距离。
    private Map<String, Integer> map = new HashMap<>();         // 用于快速匹配字符串查到唯一序号
    public WordDistanceII(String[] words) {
        this.words = words;
        set = new HashSet<>(Arrays.asList(words));
        List<String> list = new ArrayList<>(set);
        int idx = 0;
        for (String x : list) map.put(x, idx++);
    }

    // 退化成E243的解法，时间复杂度增大：每次调用都是o(words.length)，而不是o(n + m)，words.length远大于n + m
    private int wordDistance(String a, String b) {
        int min = Integer.MAX_VALUE;
        int ai = -1, bi = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(a)) ai = i;
            else if (words[i].equals(b)) bi = i;
            else continue;
            if (ai != -1 && bi != -1) min = Math.min(min, Math.abs(ai - bi));
        }
        return min;
    }

    // 备忘
    public int shortestDistance(String a, String b) {
        int i = map.get(a);
        int j = map.get(b);
        String key = String.valueOf(Math.min(i, j)) + "+" + String.valueOf(Math.max(i, j));
        if (!distance.containsKey(key))
            distance.put(key, wordDistance(a, b));
        return distance.get(key);
    }
}
