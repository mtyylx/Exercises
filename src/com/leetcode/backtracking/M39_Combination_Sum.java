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
 *
 * M39  Combination Sum 1: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可用多次，不考虑重复解，给出所有解内容
 * M40  Combination Sum 2: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate仅用一次，，不考虑重复解，给出所有解内容
 * M216 Combination Sum 3: 给定Candidate数字集合为{1-9}，目标值n，每条结果长度固定为k，每个Candidate仅用一次，不考虑重复解，给出所有解内容
 * M377 Combination Sum 4: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可以用多次，考虑所有重复解，仅返回解的个数
 */
public class M39_Combination_Sum {
    public static void main(String[] args) {
        List<List<Integer>> result = combSum2(new int[]{2, 3, 6, 7}, 7);
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
    // 还米有想出来。


    /** 优化穷举法，先排序后干活，通过禁止回退来避免重复。 */
    // 避免重复路径 技巧1：
    // 通过观察分析重复路径的共同特征就是出现了回退：{2, 2, 3} {2, 3, 2} {3, 2, 2}
    // 以x为分支后，后面不能再跟小于x的分解值：例如走到了2 -> 3 -> ?这条路，那么接下来不允许2 -> 3 -> 2出现就不会记录重复路径。
    // 同理，如果走到了3 -> ?这条路，那么接下来不允许出现3 -> 2这条路径。
    // 这样的规则下，我们可以确保只记录下来2 -> 2 -> 3这条路径。从而完全避免了重复路径的记录。
    // 避免重复路径 技巧2：
    // 如果数组a中包含重复元素，虽然很坑爹，但是为了避免生成重复路径，需要跳过这些重复的元素。
    // 由于candidate已经排序，所以只需要检查相邻元素是否相等即可。
    static List<List<Integer>> combSum2(int[] a, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);     // 确保Candidate按顺序排号，避免记录重复路径（技巧1 + 技巧2）
        backtrack(a, target, result, new ArrayList<>(), 0);
        return result;
    }

    static void backtrack(int[] a, int target, List<List<Integer>> result, List<Integer> current, int start) {
        // 递归终止条件：要不然无解，要不然找到完整解立即返回，要不然接着递归。
        if (target < 0) return;
        if (target == 0) {result.add(new ArrayList<>(current)); return;}    // 拷贝当前路径的值存入result，而不是直接添加。
        for (int i = start; i < a.length; i++) {                    // 避免重复：不从0开始尝试，而是从上一层访问位置的右侧开始尝试
            current.add(a[i]);                                      // 依然是先添后删
            backtrack(a, target - a[i], result, current, i);
            current.remove(current.size() - 1);
            while (i + 1 < a.length && a[i + 1] == a[i]) i++;       // 避免Candidate中有重复元素。
        }
    }

    /** 穷举法（其实并没有什么回溯），完全的递归每一个可能的分支。用HashSet来避免存储重复路径。效率奇低。
     * Time - o(n^m) n为candidate的个数，m为层高。*/
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
            Collections.sort(current_path);     // 先调整顺序
            result.add(current_path);           // 再添加。添加的过程会隐式的比较ArrayList是否相等。
            return;
        }
        for (int x : a) {
            current_path.add(x);                                        // 先添加上
            combSum_Recursive(a, target - x, result, current_path);
            current_path.remove(current_path.size() - 1);               // 递归完把path恢复到之前的状态，以供下个循环尝试添加。
        }
    }
}
