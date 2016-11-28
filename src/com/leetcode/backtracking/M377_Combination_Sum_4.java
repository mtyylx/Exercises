package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by Michael on 2016/11/28.
 *
 * Given an integer array with all positive numbers and no duplicates,
 * find the number of possible combinations that add up to a positive integer target.
 *
 * Example:
 * nums = [1, 2, 3]
 * target = 4
 *
 * The possible combination ways are:
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * Note that different sequences are counted as different combinations.
 * Therefore the output is 7.
 *
 * Follow up:
 * What if negative numbers are allowed in the given array?
 * How does it change the problem?
 * What limitation we need to add to the question to allow negative numbers?
 *
 * Function Signature:
 * public int combSum(int[] a, int target) {...}
 *
 * M39  Combination Sum 1: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可用多次，不计算重复解
 * M40  Combination Sum 2: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate仅用一次，，不计算重复解
 * M216 Combination Sum 3: 给定Candidate数字集合为{1-9}，目标值n，每条结果长度固定为k，每个Candidate仅用一次，不计算重复解
 * M377 Combination Sum 4: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可以用多次，计算所有重复解，仅返回解的个数。
 */
public class M377_Combination_Sum_4 {
    public static void main(String[] args) {
        System.out.println(combSum(new int[] {1, 2, 3}, 8));
        System.out.println(combSum2(new int[] {1, 2, 3}, 8));
    }

    /** 回溯法，为了包括重复解，每次递归都从扫描整个candidate，计算复杂度较高 */
    // 区别于同系列其他三道题目的地方在于需要计算每个解排列组合顺序的个数。而且不需要计算解本身。
    // 针对上面这两个特点，可以将原有的算法进行简化，不需要传递result解集，也不需要传递当前已经走的路径，也不需要传递上一层访问到第几个candidate
    // 唯一需要记录的就是现在的target值减到多少了，如果减到0以下就停止，如果刚好减到0解个数就自增。
    static int combSum(int[] a, int target) {
        return backtrack(a, target, 0);
    }

    static int backtrack(int[] a, int target, int count) {
        if (target < 0) return count;
        if (target == 0) return count + 1;
        for (int i = 0; i < a.length; i++)          // 每次都从0开始扫描，因为需要计算所有重复解（即内容相同但顺序不同）
            count += backtrack(a, target - a[i], 0);
        return count;
    }

    /** 回溯法，依然沿袭之前题目的策略，每次递归从上个元素的位置开始扫描，然后通过解的内容推测出该解会有多少个重复解。降低复杂度。*/
    // 例如[1, 2, 3] 4
    // 按照之前题目的解应该只有：[1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2]
    // 但是因为这里要求计算重复解，因此上面的每个解可能对应多于1个的解，因此可以扩展出：
    // [1, 1, 2] = [1, 1, 2] [1, 2, 1] [2, 1, 1]
    // [1, 3] = [1, 3] [3, 1]
    // 所以我其实只要依旧使用之前的方法，求出每一个解的具体构成，然后分析这个解可以衍生出多少种排列组合即可。
    // 排列组合的个数与独特元素个数之间的关系举例：
    // [1,2,3,4] 对应4!个排列组合 = 24个解
    // [1,1,2,3] 对应4!/2!个排列组合 = 12个解
    // [1,1,1,2] 对应4!/3!个排列组合 = 4个解
    // [1,1,1,1] 对应4!/4!个排列组合 = 1个解

    // 因为题目中已经确定candidate中不会有重复元素，因此无需排序去重。
    static int combSum2(int[] a, int target) {
        return backtrack2(a, target, new ArrayList<>(), 0, 0);
    }

    static int backtrack2(int[] a, int target, List<Integer> path, int start, int count) {
        if (target < 0) return count;
        if (target == 0) return getNumPerm(path);
        for (int i = start; i < a.length; i++) {         // 每次都从0开始扫描，因为需要计算所有重复解（即内容相同但顺序不同）
            path.add(a[i]);
            count += backtrack2(a, target - a[i], path, i, 0);
            path.remove(path.size() - 1);
        }
        return count;
    }

    // 计算方法有误：[1,1,1,1,2] [1,1,1,2,2] 虽然都只有2个不同元素，但是组合数却是不同的。
    static int getNumPermutation(List<Integer> path) {
        Set<Integer> uniq = new HashSet<>(path);
        int n = path.size();
        int k = uniq.size();
        int count = 1;
        while (k > 1) {
            count *= n;
            n--;
            k--;
        }
        return count;
    }

    // 直接使用M47的方法来求解给定path的排列组合个数。但是依然很慢。
    static int getNumPerm(List<Integer> current) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        result.add(new ArrayList<>());
        for (int round : current) {                                       // Add one element in each round
            int size = result.size();
            for (int i = 0; i < size; i++) {                        // previous path number: from 0 to size - 1.
                List<Integer> path = result.remove();
                for (int pos = 0; pos <= path.size(); pos++) {      // insert position: from 0 to path.size() [INCLUSIVE]
                    if (pos > 0 && path.get(pos - 1) == round) break;  // 避免重复的关键，比较难写对。
                    List<Integer> newpath = new ArrayList<>(path);
                    newpath.add(pos, round);
                    result.add(newpath);
                }
            }
        }
        return result.size();
    }
}
