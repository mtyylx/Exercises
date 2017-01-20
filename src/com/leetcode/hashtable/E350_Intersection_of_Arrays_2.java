package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by Michael on 2016/8/31.
 * Given two arrays, write a function to compute their intersection.
 *
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 *
 * Note:
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 *
 * Follow up:
 * 1. What if the given array is already sorted? How would you optimize your algorithm?
 *    Answer: Use Two Pointers can achieve Time - o(min(m, n)).
 * 2. What if nums1's size is small compared to nums2's size? Which algorithm is better?
 *    Answer: 使用小递归。
 * 3. What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
 *    Answer: 如果有一个数组特别长，那么可以使用<分治法>。每次只读取长数组的一部分，然后与短数组一起使用同样的算法，生成临时结果，最后合并所有临时结果即可。
 *
 * Function Signature:
 * public int[] intersection(int[] a, int[] b) {...}
 *
 * <Intersection 系列问题>
 * E349 Intersection of Arrays：给定两个数组，找出它们共同拥有的元素，<不含重复>。
 * E350 Intersection of Arrays：给定两个数组，找出它们共同拥有的元素，<包含重复>。
 *
 * <Tags>
 * - Sort + Two Pointers: [i → → → ... ] [j → → → ... ]
 * - 小递归：在方法起始添加一个递归语句，优化性能。
 * - HashMap: Key → 元素值，Value → 出现次数。map.put(x, map.getOrDefault(x, 0) + 1)一行统计分布频率。
 *
 */
public class E350_Intersection_of_Arrays_2 {
    public static void main(String[] args) {
        int[] a = {1, 4, 2, 4, 5, 4};
        int[] b = {2, 6, 4, 3, 4, 2, 9};
        System.out.println(Arrays.toString(intersection3(a, b)));
        System.out.println(Arrays.toString(intersection2(a, b)));
        System.out.println(Arrays.toString(intersection1(a, b)));
    }

    /** 解法3：单HashMap解法，解法2的简化版。Time - o(n), Space - o(n). */
    // 只对其中短的数组进行HashMap统计，然后长的数组遇到重复元素就抵消该元素的次数一次。
    static int[] intersection3(int[] a, int[] b) {
        if (a.length > b.length) return intersection3(b, a);
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : a) map.put(x, map.getOrDefault(x, 0) + 1);     // 统计比较短的那个数组的元素分布频率情况
        for (int y : b) {
            if (map.containsKey(y) && map.get(y) > 0) {
                result.add(y);
                map.put(y, map.get(y) - 1);             // 每查到重复元素就减1，直到为0.
            }
        }
        int[] res = new int[result.size()];
        int idx = 0;
        for (int x : result) res[idx++] = x;
        return res;
    }

    /** 解法2：双HashMap解法。Time - o(n), Space - o(n). */
    // 由于要记录重复元素，我们可以发现两个数组中元素相同的个数一定由出现次数小的那个数组决定。
    // 例如[1,2,3,3,3][3,1,3]中3出现了3次和2次，但是显然只能输出两个3，因为要满足两个数组中都出现的原则。
    // 于是我们分别扫描两个数组，获取其元素频率分布在HashMap1和2中，
    // 然后我们扫描小的HashMap，如果元素在两个HashMap中都有，那么就取出该元素值在两个数组中的出现次数，然后取出现次数少的次数，加到result中
    // 针对数组a和b长度可能十分悬殊的情况，为了优化算法性能，可以在开头加入一个小递归，以确保最后遍历的是长度小的那个数组。
    static int[] intersection2(int[] a, int[] b) {
        if (a.length > b.length) return intersection2(b, a);        // 小递归，最后处理的是短的数组。
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        for (int x : a) map1.put(x, map1.getOrDefault(x, 0) + 1);
        for (int y : b) map2.put(y, map2.getOrDefault(y, 0) + 1);
        List<Integer> result = new ArrayList<>();
        int count = 0;
        for (int x : map1.keySet()) {
            if (map2.containsKey(x)) {
                count = Math.min(map1.get(x), map2.get(x));
                while (count-- > 0) result.add(x);
            }
        }
        int[] res = new int[result.size()];
        int idx = 0;
        for (int x : result) res[idx++] = x;
        return res;
    }

    /** 解法1：Sort + Two Pointers。与E349解法完全一样。Time - o(nlogn) */
    // 先排序，然后利用已排序数组的性质使用双指针同步扫描。
    // 唯一的区别就是不再使用HashSet去重，而是直接记录在ArrayList中最后输出。
    static int[] intersection1(int[] a, int[] b) {
        if (a == null || b == null) return null;
        List<Integer> result = new ArrayList<>();
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if      (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else {
                result.add(a[i]);
                i++; j++;
            }
        }
        int[] res = new int[result.size()];
        int idx = 0;
        for (int x : result) res[idx++] = x;
        return res;
    }
}
