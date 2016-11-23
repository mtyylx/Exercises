package com.leetcode.backtracking;
import java.util.*;
/**
 * Created by Michael on 2016/11/19.
 *
 * Given a collection of candidate numbers (C) and a target number (T),
 * find all unique combinations in C where the candidate numbers sums to T.
 * Each number in C may only be used once in the combination.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 *
 * For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
 * A solution set is:
 * [
 *  [1, 7],
 *  [1, 2, 5],
 *  [2, 6],
 *  [1, 1, 6]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> combSum(int[] a, int target) {...}
 *
 * M39  Combination Sum 1: 给定Candidate数字集合a，以及目标值target，每个Candidate可用多次。
 * M40  Combination Sum 2: 给定Candidate数字集合a，以及目标值target，每个Candidate仅用一次。
 * M216 Combination Sum 3: 给定Candidate数字集合为{1-9}，以及目标值n和个数k，每个Candidate仅用一次。
 */
public class M40_Combination_Sum_2 {
    public static void main(String[] args) {
        List<List<Integer>> result = combinationSum2(new int[]{2, 2, 3, 6, 7}, 7);
    }

    /** 回溯法，记录总路径和当前路径，每次递归结束后回退一步。通过限制访问candidate的区域达到避免记录重复路径的目的。 */
    static List<List<Integer>> combinationSum2(int[] a, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);
        backtrack(a, target, result, new ArrayList<>(), 0);
        return result;
    }

    static void backtrack(int[] a, int target, List<List<Integer>> result, List<Integer> current, int i) {
        if (target < 0) return;
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        while (i < a.length) {
            current.add(a[i]);
            backtrack(a, target - a[i], result, current, i + 1);  // 唯一的区别就是这里传给下一层递归的起始索引是i+1而不是i了。
            current.remove(current.size() - 1);
            i++;
            while (i < a.length && a[i] == a[i - 1]) i++;
        }
    }

    // 用for循环实现同样的逻辑。
    static void backtrack2(int[] a, int target, List<List<Integer>> result, List<Integer> current, int start) {
        if (target < 0) return;
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < a.length; i++) {
            current.add(a[i]);
            backtrack2(a, target - a[i], result, current, i + 1);  // 唯一的区别就是这里传给下一层递归的起始索引是i+1而不是i了。
            current.remove(current.size() - 1);
            while (i + 1 < a.length && a[i + 1] == a[i]) i++;
        }
    }

    // 用continue循环实现同样的逻辑。
    static void backtrack3(int[] a, int target, List<List<Integer>> result, List<Integer> current, int start) {
        if (target < 0) return;
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < a.length; i++) {
            if (i > 0 && a[i] == a[i - 1]) continue;
            current.add(a[i]);
            backtrack3(a, target - a[i], result, current, i + 1);  // 唯一的区别就是这里传给下一层递归的起始索引是i+1而不是i了。
            current.remove(current.size() - 1);
        }
    }
}
