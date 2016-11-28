package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/11/23.
 * Find all possible combinations of k numbers that add up to a number n,
 * given that only numbers from 1 to 9 can be used
 * and each combination should be a unique set of numbers.
 *
 * Example 1:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]] (如果允许多次使用同一个值那么这里应该还有一个解[2,2,3] )
 *
 * Example 2:
 * Input: k = 3, n = 9
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 *
 * Function Signature:
 * public List<List<Integer>> combSum(int k, int n) {...}
 *
 * M39  Combination Sum 1: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可用多次，不考虑重复解，给出所有解内容
 * M40  Combination Sum 2: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate仅用一次，，不考虑重复解，给出所有解内容
 * M216 Combination Sum 3: 给定Candidate数字集合为{1-9}，目标值n，每条结果长度固定为k，每个Candidate仅用一次，不考虑重复解，给出所有解内容
 * M377 Combination Sum 4: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可以用多次，考虑所有重复解，仅返回解的个数
 */
public class M216_Combination_Sum_3 {
    public static void main(String[] args) {
        List<List<Integer>> result = combSum(3, 7);
    }

    static List<List<Integer>> combSum(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(k, n, 1, result, new ArrayList<>());      // start from 1, and iterate to 9.
        return result;
    }

    // 有可能k已经减至0，但是target依然不为0。
    static void backtrack(int k, int target, int start, List<List<Integer>> result, List<Integer> path) {
        if (target < 0) return;                 // 步子太大，路径无效，剪枝。
        if (target == 0 && k != 0) return;      // target已经为0但是路径长度还没有达到要求，路径无效，剪枝。
        if (target == 0) {                      // target == 0 && k == 0，找到符合要求的路径，存储返回。
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < 10; i++) {
            path.add(i);
            backtrack(k - 1, target - i, i + 1, result, path);
            path.remove(path.size() - 1);
        }
    }
}
