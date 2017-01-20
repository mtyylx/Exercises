package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by Michael on 2016/8/30.
 * Given two arrays, write a function to compute their intersection.
 * 求两个数组的公用元素列表。一个元素如果既出现在a数组也出现在b数组中，就算是结果之一。
 *
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
 *
 * Note:
 * Each element in the result must be unique.
 * The result can be in any order.
 *
 * Function Signature:
 * public int[] intersection(int[] a, int[] b) {...}
 *
 * <Tags>
 * - Sort + Two Pointers: [i → → → ... ] [j → → → ... ]
 * - Sort + Binary Search: [i → → → ... mid ... ← ← ← j]
 * - HashSet去重
 *
 */
public class E349_Intersection_of_Arrays {
    public static void main(String[] args) {
        int[] a = {1, 4, 2, 8, 5, 7};
        int[] b = {2, 6, 4, 3, 4, 2, 9};
        System.out.println(Arrays.toString(intersection3(a, b)));
        System.out.println(Arrays.toString(intersection2(a, b)));
        System.out.println(Arrays.toString(intersection1(a, b)));
    }


    /** 解法3：双HashSet。Time - o(n), Space - o(n). */
    // 先对第一个数组用HashSet去重，然后遍历第二个数组，判断元素是否在第一个数组出现。
    static int[] intersection3(int[] a, int[] b) {
        if (a == null || b == null) return null;
        Set<Integer> set = new HashSet<>();
        for (int x : a) set.add(x);
        Set<Integer> inter = new HashSet<>();
        for (int x : b)
            if (set.contains(x)) inter.add(x);
        int[] result = new int[inter.size()];
        int idx = 0;
        for (int x : inter) result[idx++] = x;
        return result;
    }

    /** 解法2：Sort + Two Pointer。Time - o(nlogn), Space - o(n). */
    // 对两个数组都进行排序，然后双指针同步扫描，根据大小关系选择移动哪个指针。
    static int[] intersection2(int[] a, int[] b) {
        if (a == null || b == null) return null;
        List<Integer> result = new ArrayList<>();
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {      // 双指针同步扫描
            if      (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else {
                result.add(a[i]);
                i++; j++;
            }
        }
        Set<Integer> set = new HashSet<>(result);
        int[] res = new int[set.size()];
        int idx = 0;
        for (int x : set) res[idx++] = x;
        return res;
    }

    /** 解法1：Sort + Binary Search. Time - o(nlogn), Space - o(n). */
    // 对两个数组中的其中一个进行排序，然后对另外一个数组的每一个元素使用Binary Search。
    // 为了避免结果中出现重复元素，需要存入HashSet中再输出到int[]数组之中。
    static int[] intersection1(int[] a, int[] b) {
        if (a == null || b == null) return null;
        if (a.length > b.length) intersection1(b, a);   // 小递归：为的是让两个数组中长的那个排序，短的那个遍历
        Set<Integer> set = new HashSet<>();             // 自动去重，避免记录重复元素
        Arrays.sort(b);                                 // Time - o(max(n,m) * log(max(n,m)))
        for (int x : a) {                               // 标准Binary Search, Time - o(min(n,m) * log(max(n,m)))
            int i = 0;
            int j = b.length - 1;
            while (i <= j) {
                int mid = i + (j - i) / 2;
                if      (b[mid] < x) i = mid + 1;
                else if (b[mid] > x) j = mid - 1;
                else    { set.add(x); break; }          // 找到就立即结束while循环
            }
        }
        int idx = 0;
        int[] res = new int[set.size()];
        for (int x : set) res[idx++] = x;               // 集合类转数组
        return res;
    }

}
