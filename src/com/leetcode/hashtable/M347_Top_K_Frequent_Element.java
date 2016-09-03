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
        topKFrequent(a, 1);
    }

    static List<Integer> topKFrequent(int[] a, int k) {
        // 先统计各元素出现次数，使用HashMap
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }

        // 后遍历HashMap，将Value作为Index访问，并反存Value（出现频率）对应的Key（元素本身）
        // 不使用二重ArrayList而使用ArrayList数组的缘故：外层用数组的话，中括号就可以访问和修改，相比ArrayList的get和set可以少些很多字，省事
        // 内层使用ArrayList的原因，是考虑到只需要简单的添加出现次数相同的元素，相比之下，外层需要更多的是通过索引访问并修改，用数组更方便。
        // 不能使用ArrayList替换数组的实现
//        List<List<Integer>> bucket = new ArrayList<>(a.length + 1);
//        for (int element : map.keySet()) {
//            int freq = map.get(element);
//            if (bucket.get(freq) == null) bucket.set(freq, new ArrayList<>());
//            bucket.get(freq).add(element);
//        }

        List<Integer>[] bucket = new List[a.length + 1];
        for (int element : map.keySet()) {
            int freq = map.get(element);
            if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
            bucket[freq].add(element);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = bucket.length - 1; i >= 0; i++) {
            if (bucket[i] != null) result.addAll(bucket[i]);
            if (result.size() == k) break;
        }

        return result;
    }
}
