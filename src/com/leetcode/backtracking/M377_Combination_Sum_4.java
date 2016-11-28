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
 * M39  Combination Sum 1: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可用多次，不考虑重复解，给出所有解内容
 * M40  Combination Sum 2: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate仅用一次，，不考虑重复解，给出所有解内容
 * M216 Combination Sum 3: 给定Candidate数字集合为{1-9}，目标值n，每条结果长度固定为k，每个Candidate仅用一次，不考虑重复解，给出所有解内容
 * M377 Combination Sum 4: 给定Candidate数字集合a，目标值target，每条结果长度自由，每个Candidate可以用多次，考虑所有重复解，仅返回解的个数
 */
public class M377_Combination_Sum_4 {
    public static void main(String[] args) {
        System.out.println(combSum(new int[] {1, 2, 3}, 32));
        System.out.println(combSum2(new int[] {1, 2, 3}, 32));
        System.out.println(combSum_BottomUp(new int[] {1, 2, 3}, 32));
    }

    /** 回溯法，为了包括重复解，每次递归都从扫描整个candidate，计算复杂度较高 */
    // 区别于同系列其他三道题目的地方在于需要计算每个解排列组合顺序的个数。而且不需要计算解本身。
    // 针对上面这两个特点，可以将原有的算法进行简化，不需要传递result解集，也不需要传递当前已经走的路径，也不需要传递上一层访问到第几个candidate
    // 唯一需要记录的就是现在的target值减到多少了，如果减到0以下就停止，如果刚好减到0解个数就自增。
    // 虽然这个解法非常简单，但是我们可以注意到，解的寻找速度始终是常数级别的，我们是真真切切的“一个一个的找解”，这样的速度太慢。
    static int combSum(int[] a, int target) {
        return backtrack(a, target);
    }

    static int backtrack(int[] a, int target) {
        int count = 0;
        if (target < 0) return 0;
        if (target == 0) return 1;
        for (int i = 0; i < a.length; i++)          // 每次都从0开始扫描，因为需要计算所有重复解（即内容相同但顺序不同）
            count += backtrack(a, target - a[i]);
        return count;
    }

    /** DP解法，Top-Down顺序，递归方式，Memoization备忘的是子问题的解个数 */
    // 相比于上面的回溯解法，使用DP将会使寻找解的过程成为跳跃式的增长（直接利用之前的计算结果），而不再是一个一个的积累解。
    // 关键在于找到了一种削减问题规模的方式，并且发现在削减问题的过程中存在重复使用计算结果的可能，使用DP非常合适。
    // 本质寻找解的过程就是把target一直减到0的过程，dp[target]表示给定candidate为a的情况下target值对应的解个数。
    // 比较特殊的，如果解为0，则表示candidate数组无法组成为target。例如a =[4 5] target = 2的情况
    // 因此如果要表示该dp值还未计算过，不应该用0，而是-1标识，否则会混淆。
    // 状态转移过程（递推）：dp[n] = dp[n - candidate1] + dp[n - candidate2] + ...
    // 写Top-Down递归的几个要点：
    // 1. 起始种子值：例如这里的dp[0] = 1
    // 2. 备忘数组调用
    // 3. 递归终止条件
    // 4. 问题削减前后的联系：例如这里dp[n]等于之前所有相关子问题解之和。
    //
    // 运行过程分析：a = [1, 2, 3] target = 4
    // dp[4] = dp[3] + dp[2] + dp[1] = ------------------→ 4 + 2 + 1 = 7
    //           ↓                                         ↑   ↑   ↑
    //         dp[3] = dp[2] + dp[1] + dp[0] = 2 + 1 + 1 = 4   |   |
    //                   ↓                     ↑               |   |
    //                 dp[2] = dp[1] + dp[0] = 1 + 1 = 2_______|   |
    //                           ↓             ↑                   |
    //                         dp[1] = dp[0] = 1___________________|
    static int combSum2(int[] a, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return combSum_TopDown2(a, target, dp);
    }

    static int combSum_TopDown(int[] a, int target, int[] dp) {
        if (target == 0) return dp[0];
        dp[target] = 0;     // 需要将待计算dp恢复为0再开始累加。
        for (int i = 0; i < a.length; i++) {
            int subproblem = target - a[i];
            if (subproblem >= 0)
                dp[target] += (dp[subproblem] == -1 ? combSum_TopDown(a, subproblem, dp) : dp[subproblem]); //进入递归前就检查是否已经计算
        }
        return dp[target];
    }

    // 或写为下面，区别在于下面的递归方法是在进入下一层递归后才检查是否已经计算，写法更优美些。
    static int combSum_TopDown2(int[] a, int target, int[] dp) {
        if (dp[target] != -1) return dp[target];    // 进入递归后再检查是否已经计算
        dp[target] = 0;
        for (int i = 0; i < a.length; i++) {
            if (target - a[i] >= 0)
                dp[target] += combSum_TopDown2(a, target - a[i], dp);
        }
        return dp[target];
    }

    /** DP解法，Bottom-Up（从头做起），Iterative，Memoization */
    static int combSum_BottomUp(int[] a, int target) {
        int[] dp = new int[target + 1];         // 无需标记所有未计算的dp值为-1，因为不存在提前访问的可能。
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < a.length; j++) {
                if (i - a[j] >= 0)
                    dp[i] += dp[i - a[j]];      // 无需检查是否计算过，因为是Bottom-Up，之前的所有值一定都计算过。
            }
        }
        return dp[target];
    }

    /** 回溯法，依然沿袭之前题目的策略，每次递归从上个元素的位置开始扫描，然后通过解的内容推测出该解会有多少个重复解。
     * 由于“计算一个给定path的所有不同排列数量”这件事本身就是一个需要backtracking的问题，因此实际复杂度范围更高，不推荐。*/
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
    static int combSum3(int[] a, int target) {
        return backtrack3(a, target, new ArrayList<>(), 0, 0);
    }

    static int backtrack3(int[] a, int target, List<Integer> path, int start, int count) {
        if (target < 0) return count;
        if (target == 0) return getNumPerm(path);
        for (int i = start; i < a.length; i++) {         // 每次都从0开始扫描，因为需要计算所有重复解（即内容相同但顺序不同）
            path.add(a[i]);
            count += backtrack3(a, target - a[i], path, i, 0);
            path.remove(path.size() - 1);
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
