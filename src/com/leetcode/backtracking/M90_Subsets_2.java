package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by LYuan on 2016/11/21.
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets.
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * For example,
 * If nums = [1,2,2], a solution is:
 * [
 *  [2],
 *  [1],
 *  [1,2,2],
 *  [2,2],
 *  [1,2],
 *  []
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> subsetDup(int[] a) {...}
 *
 * 与M39/M40的写法几乎完全一样。
 * 与M78的唯一区别在于Candidate数组含有重复元素，因此需要先Sort后去重。
 */
public class M90_Subsets_2 {
    public static void main(String[] args) {
        List<List<Integer>> result = subsetsDup(new int[] {1, 2, 2, 3});
        List<List<Integer>> result2 = subsetsDup2(new int[] {1, 2, 2, 3});
    }

    /** 相应的DP解法，需要在内循环前判定跳过重复元素。*/
    static List<List<Integer>> subsetsDup2(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        Arrays.sort(a);
        int size = 0;
        for (int i = 0; i < a.length; i++) {
            int start = 0;
            if (i > 0 && a[i] == a[i - 1]) start = size;    // 内循环扫描范围要从不重复的开始添加。
            size = result.size();           // 注意易错点：如果不缓存result.size，那么由于内循环在不断扩容，因此会形成无限循环。
            for (int j = start; j < size; j++) {
                List<Integer> temp = new ArrayList<>(result.get(j));    // 注意易错点：必须拷贝至新list，避免修改现有path。
                temp.add(a[i]);
                result.add(temp);
            }
        }
        return result;
    }

    /** 回溯法，路径增删，和M78基本一样，只是增加了排序去重。 */
    public static List<List<Integer>> subsetsDup(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);     // 需要先排序
        subset_recursive2(a, result, new ArrayList<>(), 0);
        return result;
    }

    static void subset_recursive2(int[] a, List<List<Integer>> result, List<Integer> current, int idx) {
        result.add(new ArrayList<>(current));
        while (idx < a.length) {
            current.add(a[idx]);
            subset_recursive2(a, result, current, idx + 1);
            current.remove(current.size() - 1);
            idx++;
            while (idx < a.length && a[idx] == a[idx - 1]) idx++;   // 然后跳过重复的candidate
        }
    }
}
