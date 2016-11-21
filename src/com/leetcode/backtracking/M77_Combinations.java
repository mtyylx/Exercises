package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/11/21.
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 *
 * For example,
 * If n = 4 and k = 2, a solution is:
 * [
 *  [2,4],
 *  [3,4],
 *  [2,3],
 *  [1,2],
 *  [1,3],
 *  [1,4],
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> combination(int n, int k) {...}
 */
public class M77_Combinations {
    public static void main(String[] args) {
        List<List<Integer>> result = combination(20, 16);
        List<List<Integer>> result2 = combination2(20, 16);
        List<List<Integer>> result3 = combination3(20, 16);
    }

    /** DP解法，Bottom-Up（积少成多），不知道为什么性能反而没有回溯法快。 */
    // 最外层遍历k
    // 中层遍历上一轮的所有解
    // 内层按照每个解的最后一个元素值确定遍历起始位置x。
    // [1, 2, 3, 4]
    // dp[1]: [1] [2] [3] [4]
    // dp[2]: [1] -> [1,2] [1,3] [1,4]
    //        [2] -> [2,3] [2,4]
    //        [3] -> [3,4]
    //        [4] ->  X
    static List<List<Integer>> combination2(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int i = 0; i < k; i++) {                           // iterate depth
            List<List<Integer>> temp = new ArrayList<>();
            for (List<Integer> set : result) {                  // iterate previous result set
                List<Integer> path = new ArrayList<>(set);
                int start = path.size() == 0 ? 1 : path.get(path.size() - 1) + 1;   // 确定扫描解空间的起始位置：start
                for (int x = start; x <= n; x++) {              // iterate 解空间
                    path.add(x);
                    temp.add(new ArrayList<>(path));
                    path.remove(path.size() - 1);
                }
            }
            result = temp;
        }
        return result;
    }

    // 同样是DP解法，与上面的写法稍有不同。节省了一步new新对象的操作，同时也避免了反复增删元素。
    static List<List<Integer>> combination3(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int i = 0; i < k; i++) {                               // iterate depth
            List<List<Integer>> temp = new ArrayList<>();
            for (List<Integer> path : result) {                     // iterate previous result set
                int start = path.size() == 0 ? 1 : path.get(path.size() - 1) + 1;   // 确定扫描解空间的起始位置：start
                for (int x = start; x <= n; x++) {                  // iterate 解空间
                    List<Integer> newPath = new ArrayList<>(path);  // 必须拷贝至新对象中才能修改，否则会导致ConcurrentModification错误。
                    newPath.add(x);
                    temp.add(newPath);
                }
            }
            result = temp;
        }
        return result;
    }


    /** 回溯法，增删当前路径，并记录扫描空间起点。*/
    // [1, 2, 3, 4] 每次递归深入都限制搜索起点为下一个元素。
    // [1 - 2, 3, 4 : [1,2] [1,3] [1,4]
    // [2 - 3,4 : [2,3] [2,4]
    // [3 - 4 : [3,4]
    // [4 - 直接返回，因为start已经大于n
    // 另外每次递归时都减少k（深度），直到减小到0就把路径缓存。
    static List<List<Integer>> combination(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(n, k, 1, result, new ArrayList<>());
        return result;
    }

    static void backtrack(int n, int k, int start, List<List<Integer>> result, List<Integer> current) {
        if (k == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i <= n; i++) {          // 自动处理了当start已经超过了n范围时的情况。
            current.add(i);
            backtrack(n, k - 1, i + 1, result, current);
            current.remove(current.size() - 1);
        }
    }
}
