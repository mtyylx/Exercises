package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by LYuan on 2016/11/18.
 * Given a set of candidate numbers (C) and a target number (T),
 * find all unique combinations in C where the candidate numbers sums to T.
 * The same repeated number may be chosen from C unlimited number of times.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 *
 * For example, given candidate set [2, 3, 6, 7] and target 7,
 * A solution set is:
 * [
 *  [7],
 *  [2, 2, 3]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> combSum(int[] a, int target) {...}
 */
public class M39_Combination_Sum {
    public static void main(String[] args) {
        List<List<Integer>> result = combSum(new int[]{2, 3, 6, 7}, 7);
    }

    /** DP解法，Top-Down，递归方式，Memoization避免重复计算。 */
    // 其实就是一个多叉树。例如target = 4，a = {1, 2, 3}
    //                       4
    //                  /    |    \
    //                3      2     1            <- 可以看到target = 2和1的情况出现了多次，如果穷举的话要重复计算很多次。
    //              / | \
    //            2   1  0 {{1,1,1}{2,1}}
    //          / |
    //         1  0 {{1,1}{2}}
    //        /
    //       0 {{1}}
    static Map<Integer, Set<List<Integer>>> dp = new HashMap<>();
    static List<List<Integer>> combinationSum(int[] a, int target) {

    }

    static Set<List<Integer>> dfs(int[] a, int target, Set<List<Integer>> result) {
        Set<List<Integer>> current_result = new HashSet<>(result);

    }


    /** 穷举法（其实并没有什么回溯），完全的递归每一个可能的分支。Time - o(n^m) n为candidate的个数，m为层高。*/
    // 为了避免重复，例如 {2, 2, 3} {2, 3, 2} {3, 2, 2}
    // 可以先把所有找到的path都存到Set里面，最后再把Set导出为ArrayList返回。
    static List<List<Integer>> combSum(int[] a, int target) {
        Set<List<Integer>> result = new HashSet<>();
        if (a == null || a.length == 0) return new ArrayList<>(result);
        combSum_Recursive(a, target, result, new ArrayList<>());
        return new ArrayList<>(result);
    }
    // 特别要注意递归中来回传递当前访问路径必须拷贝一份，而不是直接使用传入的引用，这样会导致你添加进result的路径在之后会被修改。
    static void combSum_Recursive(int[] a, int target, Set<List<Integer>> result, List<Integer> path) {
        List<Integer> current_path = new ArrayList<>(path);     // 完全拷贝，避免原path被修改。
        if (target < 0) return;
        if (target == 0) {
            Collections.sort(current_path);
            result.add(current_path);
            return;
        }
        for (int x : a) {
            current_path.add(x);
            combSum_Recursive(a, target - x, result, current_path);
            current_path.remove(current_path.size() - 1);
        }
    }
}
