package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 2016/11/20.
 * Given a set of distinct integers, nums, return all possible subsets.
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * For example,
 * If nums = [1,2,3], a solution is:
 * [
 *  [3],
 *  [1],
 *  [2],
 *  [1,2,3],
 *  [1,3],
 *  [2,3],
 *  [1,2],
 *  []
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> subset(int[] a) {...}
 *
 * 与M39/M40的写法几乎完全一样。
 */
public class M78_Subsets {
    public static void main(String[] args) {
        List<List<Integer>> result = subset(new int[] {1, 2, 3});
    }





    /** 回溯法，随时记录当前路径，显示搜索范围以避免重复路径。*/
    // 这类题目的关键在于要设计传递索引i。
    static List<List<Integer>> subset(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);
        subset_recursive(a, result, new ArrayList<>(), 0);
        return result;
    }

    static void subset_recursive(int[] a, List<List<Integer>> result, List<Integer> current, int i) {
        while (i < a.length) {
            current.add(a[i]);                                  // 添加路径节点
            result.add(new ArrayList<>(current));               // 随时记录（区别于M39/M40的地方）
            if (i < a.length - 1)                               // 直接避免出现越界情况，而不是等下一层递归方法的开始再处理，避免重复记录路径。
                subset_recursive(a, result, current, i + 1);    // 递归，并限定搜索范围
            current.remove(current.size() - 1);                 // 恢复路径节点
            i++;
            while (i < a.length && a[i] == a[i - 1]) i++;       // 避免重复元素
        }
    }

    // 上面解法的简化写法，在每次扫描搜索范围之前（即for循环开始前）就直接把当前路径添加到结果中。这样可以省略判断抵达边界的语句。
    static List<List<Integer>> subset2(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);
        subset_recursive2(a, result, new ArrayList<>(), 0);
        return result;
    }

    static void subset_recursive2(int[] a, List<List<Integer>> result, List<Integer> current, int idx) {
        result.add(new ArrayList<>(current));
        for (int i = idx; i < a.length; i++) {
            current.add(a[i]);
            subset_recursive2(a, result, current, i + 1);
            current.remove(current.size() - 1);
        }
    }
}
