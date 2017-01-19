package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by LYuan on 2016/9/2.
 * Given a non-empty array of integers, return the k most frequent elements.
 *
 * For example,
 * Given [1,1,1,2,2,3] and k = 2, return [1,2].
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size
 * 此题默认数组中元素的出现次数都是不同的。如果有相同次数的元素，那么返回结果的长度将会大于k值。
 *
 * Function Signature:
 * public List<Integer> topKFrequent(int[] nums, int k) {...}
 *
 * <出现频率分布统计 系列问题>
 * - M347 Top K Frequent Element      : 给定一个整型数组和一个值K，按照元素出现次数从大到小的顺序返回前K个元素值。
 * - M451 Sort Characters By Frequency: 给定一个字符串，按照字符出现次数从大到小的顺序返回字符串。
 *
 * <Tags>
 * - Bucket: 桶归类的思想，可用于反向映射哈希表。数组索引可以用作反向索引。
 * - HashMap: 正向和反向映射。元素值-出现次数映射 → 出现次数-元素值映射。
 * - getOrDefault(key, defaultValue)：可以用一行就完成HashMap统计词频的工作。
 * - List<>[] bucket = new List[size]：泛型数组的定义和使用，里面存的是List对象。
 *
 */
public class M347_Top_K_Frequent_Element {
    public static void main(String[] args) {
        int[] a = {2, 2, 2, 2, 3, 3, 3, 4, 4, 1};
        System.out.println(topKFrequent1(a, 2));
        System.out.println(topKFrequent2(a, 2));
        System.out.println(topKFrequent3(a, 2));
    }

    /** 解法2：HashMap + 泛型数组，正向和反向映射。Time - o(n), Space - o(n). */
    // 简化写法：使用泛型数组，数组索引表示出现次数，利用数组索引自然有序的特性，省去了解法1中对HashMap键的排序工作。
    // 关键在于这句话：List<Integer> bucket = new List[size];
    // 这里定义了元素类型为List<Integer>的bucket数组。但是要清楚，所用的构造器决定了该数组本质上存的都是List对象，是硬被cast成为List<Integer>类型的。
    // 泛型数组最好只在内部逻辑中使用，因为数组中存储的本质是Object对象，如果开放给别人用，可能会导致ClassCastException的风险
    // 这里由于我们只是利用了数组索引已排序的特性逆向扫描即可，没有什么风险。
    static List<Integer> topKFrequent2(int[] a, int k) {
        Map<Integer, Integer> map = new HashMap<>();                            // 正向映射
        for (int x : a)
            map.put(x, map.getOrDefault(x, 0) + 1);                  // 简化写法：一行搞定频率分布
        List<Integer>[] bucket = new List[a.length + 1];                        // 反向映射：使用泛型数组，索引即为元素出现次数
        for (int key : map.keySet()) {
            int freq = map.get(key);
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();         // 空bucket则需要首先构造好ArrayList
            bucket[freq].add(key);                                              // 添加元素
        }
        List<Integer> result = new ArrayList<>();
        for (int i = bucket.length - 1; i > 0; i--) {                           // 逆向扫描bucketID
            if (bucket[i] != null) result.addAll(bucket[i]);
            if (result.size() == k) break;
        }
        return result;
    }

    /** 解法1：双HashMap正向和反向映射。Time - o(n), Space - o(n). */
    // 首先我们需要获得原数组每个元素出现次数的分布：根据原数组，使用第一个HashMap存储“元素值-出现次数”键值对。
    // 然后我们需要按照出现次数对元素值进行归类（桶归类）：根据第一个HashMap，使用第二个HashMap存储“出现次数-元素值集合”键值对。
    // 最后我们需要按照出现次数从大到小，构造新数组返回
    // 这里比较麻烦的地方在于HashMap的KVP是无序排列的，因此需要首先对Key进行排序，然后才能按顺序访问到对一个的元素值集合存入新数组。
    static List<Integer> topKFrequent1(int[] a, int k) {
        Map<Integer, Integer> map = new HashMap<>();                        // 正向映射：元素值做Key，出现次数做Value
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }
        Map<Integer, List<Integer>> reverseMap = new HashMap<>();           // 反向映射：出现次数做Key，元素值的集合做Value
        for (int x : map.keySet()) {
            if (reverseMap.containsKey(map.get(x))) reverseMap.get(map.get(x)).add(x);
            else reverseMap.put(map.get(x), new ArrayList<>(Arrays.asList(x)));
        }
        List<Integer> keys = new ArrayList<>(reverseMap.keySet());
        Collections.sort(keys);                                             // 对反向映射的KeySet进行排序
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {                             // 从大到小遍历Key值对应的元素值集合，个数达到K就返回
            result.addAll(reverseMap.get(keys.get(keys.size() - i - 1)));
            if (result.size() == k) break;
        }
        return result;
    }

    // 使用List<List<Integer>>
    // 问题在于List变长，不是自动获得所有长度，因此无法直接用索引访问，需要先手动初始化所有元素之后才能访问。
    static List<Integer> topKFrequent3(int[] a, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }
        // 不使用二重ArrayList而使用ArrayList数组的缘故：
        // 内层使用ArrayList，是考虑到只需要简单的添加出现次数相同的元素，相比之下，
        // 外层则必须在没有添加任何元素的情况下就直接访问指定索引的元素，而这一点ArrayList是无法做到的，因此外层只能使用数组。
        // 下面的实现方式是错误的：因为bucket在仅初始化(size=0)的前提下，是不能直接调用bucket.set(index, value)的，会抛数组越界异常。
//        List<List<Integer>> bucket = new ArrayList<>(a.length + 1);
//        for (int element : map.keySet()) {
//            int freq = map.get(element);
//            if (bucket.get(freq) == null) bucket.set(freq, new ArrayList<>());
//            bucket.get(freq).add(element);
//        }
        // 如果真的要用List实现外层，就必须初始化后填满长度。
        List<List<Integer>> bucket = new ArrayList<>(a.length + 1);
        for (int i = 0; i < a.length + 1; i++)
            bucket.add(new ArrayList<>());
        // 填满后可以任意get和set了
        for (int element : map.keySet()) {
            int freq = map.get(element);
            bucket.get(freq).add(element);
        }
        // 按K来取出排名靠前的K个，从最大的索引（出现次数）开始找，只找那些有元素的list
        List<Integer> result = new ArrayList<>();
        for (int i = a.length; i >= 0; i--) {
            if (bucket.get(i).size() != 0) result.addAll(bucket.get(i));
            if (result.size() == k) break;
        }
        return result;
    }
}
