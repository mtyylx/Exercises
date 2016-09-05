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
 *
 * Function Signature:
 * public List<Integer> topKFrequent(int[] nums, int k) {...}
 */
public class M347_Top_K_Frequent_Element {
    public static void main(String[] args) {
        int[] a = {1, 1, 1, 3, 4, 1, 4, 23, 3};
        topKFrequent2(a, 1);

    }

    static List<Integer> topKFrequent2(int[] a, int k) {
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
            if (result.size() > k) break;
        }
        return new ArrayList<>();
    }

    static List<Integer> topKFrequent(int[] a, int k) {
        // 先统计各元素出现次数，使用HashMap
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }

        // 使用ArrayList<Integer>数组，也就是该数组每个元素都各自是一个ArrayList
        // 由于Java禁止泛型类数组的定义，因此这里new的是一个ArrayList数组，也就是这每一个ArrayList里面存的不是Integer，而是Object
        // 这种用法会被提示Unchecked，不过如果只是在程序内部处理使用是没问题的，如果要暴露给别人用就会有ClassCastException的风险。
        List<Integer>[] bucket = new List[a.length + 1];
        for (int element : map.keySet()) {
            int freq = map.get(element);
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
            bucket[freq].add(element);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = bucket.length - 1; i > 0; i--) {
            if (bucket[i] != null) result.addAll(bucket[i]);
            if (result.size() == k) break;
        }

        return result;
    }
}
