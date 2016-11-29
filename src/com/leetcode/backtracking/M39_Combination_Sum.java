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
        // DP test
        List<List<Integer>> result = combSumDP_BottomUp_Duplicate(new int[]{1, 2, 3}, 4);
        List<List<Integer>> result2 = combSumDP_BottomUp(new int[]{2, 3, 5}, 8);
        List<List<Integer>> result3 = combSumDP_BottomUp2(new int[]{2, 3, 5}, 8);
        List<List<Integer>> result4 = combSumDP_BottomUp3(new int[]{2, 3, 5}, 8);
        List<List<Integer>> result5 = combSumDP_TopDown(new int[]{2, 3, 5}, 8);

        // BK test
        List<List<Integer>> result11 = combSum_BK2(new int[]{1, 2, 3}, 4);
        List<List<Integer>> result12 = combSum_BK(new int[]{1, 2, 3}, 4);
    }

    /** DP解法，Bottom-Up（依次计算target = 1到n的所有解集），迭代方式，Memoization避免重复计算。思路感觉非常强大！ */
    // 其实就是一个多叉树。例如target = 4，a = {1, 2, 3}
    //              target = 4
    //                  /1   |2   \3
    //        target = 3     2     1            <- 可以看到target = 2和1的情况出现了多次，如果穷举的话要重复计算很多次。
    //              / | \
    //    target = 2  1  0
    //           / |
    // target = 1  0
    //        /
    //       0 {{}}
    // 还米有想出来。
    // UPDATE 11/28: 想粗来鸟！就用M377的思路即可。

    /** 计算重复解的解法（即扫描candidate数组总是从0开始，[1,1,2][1,2,1][2,1,1]视为3个解。*/
    // Bottom-Up的递推公式应该着眼于target，而不是a的长度。即dp数组的索引值含义应该对应于target的值。
    // 如果减去candidate后，分解得到的子问题的索引为负值，说明不应使用这个candidate，跳过。
    // 如果dp[x]尝试用所有candidate分解后都没有可用（索引为正）的子问题，则current为空，依然添加进dp。
    // dp[x]的解集size = 0表明target = x时无解。实例a = [5,] target = 7, 则dp[0]-dp[7]中只有dp[5]的size不为0。
    // 实例分析：a = [1, 2, 3] target = 4
    // dp[0] = {{}}
    // dp[1] = dp[1-1] add 1 = {{1}}
    // dp[2] = dp[2-1] add 1 + dp[2-2] add 2 = dp[1] + dp[0] = {{1,1}, {2}}
    // dp[3] = dp[3-1] add 1 + dp[3-2] add 2 + dp[3-3] add 3 = dp[2] + dp[1] + dp[0] = {{1,1,1},{2,1}} + {{1,2}} + {{3}}
    // dp[4] = dp[4-1] add 1 + dp[4-2] add 2 + dp[4-3] add 3 = {{1,1,1,1},{2,1,1},{1,2,1},{3,1}} + {{1,1,2},{2,1}} + {1,3}
    // 另外这里dp用的是三重List，而不是一个装List<List<Integer>>的数组，这是为了避免出现Generic Array Creation的错误。
    static List<List<Integer>> combSumDP_BottomUp_Duplicate(int[] a, int target) {
        List<List<List<Integer>>> dp = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> seed = new ArrayList<>();
        result.add(seed);
        dp.add(result);
        for (int i = 1; i <= target; i++) {         // 遍历计算解集的集
            List<List<Integer>> current = new ArrayList<>();    // current 表示 target = i时的解集
            for (int candidate : a) {    // 遍历candidate
                int sub = i - candidate;
                if (sub < 0) continue;                                  // 减掉当前candidate后，子问题不存在则跳过试下一个。
                for (List<Integer> path : dp.get(sub)) {                // 剪掉当前candidate后，子问题存在，直接取得自问题的解集(dp[sub])并遍历每个解(path)
                    List<Integer> new_path = new ArrayList<>(path);     // 对于每个解，先拷贝一份
                    new_path.add(candidate);                            // 再向拷贝的解里添加candidate
                    current.add(new_path);                              // 再把扩展的解存入当前解集中，等待最后汇总添加进dp。
                }
            }
            dp.add(current);    // current包含着不同candidate添加的解集。
        }
        return dp.get(target);
    }

    /** 如何不计算重复解：即视[1,1,2] [1,2,1] [2,1,1]为 1个解 */
    // 我们可以通过观察解的构成来避免重复：4 = 1 + 1 + 2 = 1 + 2 + 1 = 2 + 1 + 1，如何只计算这三者之一呢？
    // 这里，顺序起到了很重要的作用。
    // 如果我们只按顺序插入，每次要向path中放candidate之前，先查看旧path的首元素值或尾元素值，和candidate相比之后再决定是否插入，在什么位置插入。
    // 方案一：当前candidate与旧path的首元素相比，小于等于首元素时，把candidate插入旧path头部，整体作为新path，否则跳过。
    // 方案二：当前candidate与旧path的尾元素相比，大于等于尾元素时，把candidate追加在旧path尾部，整体作为xinpath，否则跳过。
    // 上面这两个方案频繁使用在Combination Sum系列的4道题目中。
    // 下面的<解法1和2>用的就是方案一。if(candidate <= path.get(0)) then add candidate, then add old path.
    // 下面的<解法3>用的就是方案二，其他的Recursive解法用的也大都是方案二，每次递归传给下层扫描的起始位置，也是尾端插入。
    // 即要不然是 if (candidate >= path.get(path.size() - 1)) then add old path，then add candidate
    // 要不然是 for (int i = start to n).

    /** 不计算重复解，<解法1>，通过先排序candidate数组，并限制插入位置来避免重复。 */
    // 独特之处：在dp中创建一个初始的种子解集dp[0]，用来递归时方便被其他dp调用。
    // 去重方式为首端插入。
    static List<List<Integer>> combSumDP_BottomUp(int[] a, int target) {
        List<List<List<Integer>>> dp = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> seed = new ArrayList<>();
        result.add(seed);
        dp.add(result);
        Arrays.sort(a);
        for (int i = 1; i <= target; i++) {         // 遍历计算解集的集
            List<List<Integer>> current = new ArrayList<>();    // current 表示 target = i时的解集
            for (int candidate : a) {    // 遍历candidate
                int sub = i - candidate;
                if (sub < 0) break;                                     // 减掉当前candidate后，子问题不存在则直接终止循环（因为candidate数组是升序排列的）
                for (List<Integer> path : dp.get(sub)) {                // 剪掉当前candidate后，子问题存在，直接取得自问题的解集(dp[sub])并遍历每个解(path)
                    List<Integer> new_path = new ArrayList<>();         // 创造一个新的空path
                    if (path.size() == 0 || candidate <= path.get(0)) { // 只在candidate小于path起始元素时才记录path（为了避免重复解）
                        new_path.add(candidate);                        // 先把candidate放入新path的起始位置，
                        new_path.addAll(path);                          // 再把老path的内容拷贝进来，这样可以保证所有dp中的所有path内元素值都是升序排列的。
                        current.add(new_path);                          // 最后把新path存入当前解集中，等待最后汇总添加进dp。
                    }
                }
            }
            dp.add(current);    // current包含着不同candidate添加的解集。
        }
        return dp.get(target);
    }

    /** 不计算重复解，<解法2>，通过先排序candidate数组，并限制插入位置来避免重复。[1,1,2][1,2,1][2,1,1]视为1个解 */
    // 独特之处：没有定义初始种子，因此一旦遇到subproblem = 0的时候，就直接把对应的candidate元素本身封装为一个List添加进当前解集中。
    // 去重方式为首端插入。
    static List<List<Integer>> combSumDP_BottomUp2(int[] a, int target) {
        Arrays.sort(a);
        List<List<List<Integer>>> dp = new ArrayList<>();
        for (int i = 1; i <= target; i++) {
            List<List<Integer>> current = new ArrayList<>();
            for (int candidate : a) {
                int sub = i - candidate;
                if (sub < 0) break;
                if (sub == 0) {                                         // Subproblem = 0 means the path contain only one candidate.
                    current.add(Arrays.asList(candidate));              // create path using this single candidate.
                    continue;
                }
                for (List<Integer> path : dp.get(sub - 1)) {            // index need to shift left by 1
                    if (candidate <= path.get(0)) {                     // 与首元素比较，小于等于才插入candidate至开头位置。
                        List<Integer> new_path = new ArrayList<>();
                        new_path.add(candidate);
                        new_path.addAll(path);
                        current.add(new_path);
                    }
                }
            }
            dp.add(current);
        }
        return dp.get(target - 1);
    }

    /** 不计算重复解，<解法3>，通过先排序candidate数组，并限制插入位置来避免重复。[1,1,2][1,2,1][2,1,1]视为1个解 */
    // 独特之处：去重方式为尾端追加。
    static List<List<Integer>> combSumDP_BottomUp3(int[] a, int target) {
        Arrays.sort(a);
        List<List<List<Integer>>> dp = new ArrayList<>();
        for (int i = 1; i <= target; i++) {
            List<List<Integer>> current = new ArrayList<>();
            for (int candidate : a) {
                int sub = i - candidate;
                if (sub < 0) break;
                if (sub == 0) {                                         // Subproblem = 0 means the path contain only one candidate.
                    current.add(Arrays.asList(candidate));              // create path using this single candidate.
                    continue;
                }
                for (List<Integer> path : dp.get(sub - 1)) {            // index need to shift left by 1
                    if (candidate >= path.get(path.size() - 1)) {       // 与尾元素比较，大于等于才在尾部追加candidate
                        List<Integer> new_path = new ArrayList<>(path);
                        new_path.add(candidate);
                        current.add(new_path);
                    }
                }
            }
            dp.add(current);
        }
        return dp.get(target - 1);
    }

    /** DP解法，Top-Down方式，递归写法。 */
    // 关键点1：递归方法需要返回值传递下一层递归返回的解集。
    // 注意这里的递归每一层含义与回溯的递归每一层含义完全不同。
    // 回溯法中使用的递归，其每一层是在试探解的每一个部分。
    // 例如给定a=[1 2 3],target=4，然后回溯过程中通过递归得到一个试探解（path）为[1 1 2]，则获得过程是第1层和第二层递归都选择1，第三层递归选择2。
    // 而动归解法的Top-down方式使用递归，其每一层是在削减问题的规模，每一层得到的（不管是生算还是查dp）都是这层target值对应的完整解集。
    // 简而言之，回溯的递归层是每个解的一部分，动归的递归层则是一个个解集。
    static List<List<Integer>> combSumDP_TopDown(int[] a, int target) {
        List<List<List<Integer>>> dp = new ArrayList<>(target);
        Arrays.sort(a);
        for (int i = 0; i <= target; i++) dp.add(null);
        return subProblem(a, target, dp);
    }
    // 每层递归首先检查是否已经计算过，target小于0和等于0的情况在上一层就处理。
    static List<List<Integer>> subProblem(int[] a, int target, List<List<List<Integer>>> dp) {
        if (dp.get(target) != null) return dp.get(target);      // 此问题已经计算过，直接返回解集。
        List<List<Integer>> current = new ArrayList<>();        // 此问题还没计算过，需要生成一个空的新解集current，等待下面的添加。
        for (int candidate : a) {
            if (target - candidate < 0) break;      // 子问题不存在，再加上已经排序，所以后面的子问题也不可能存在，所以直接终止循环。
            if (target - candidate == 0) {          // 子问题就是candidate本身，所以生成一个只含candidate的path即可，再添加到current解集中。
                List<Integer> single = new ArrayList<>(Arrays.asList(candidate));
                current.add(single);
                continue;
            }
            List<List<Integer>> old = subProblem(a, target - candidate, dp);    // 子问题存在，递归下一层
            for (List<Integer> path : old) {                                          // 把子问题的解集全都加上candidate即可加入current解集。
                if (candidate > path.get(0)) continue;
                List<Integer> new_path = new ArrayList<>();         // 首端插入，避免重复，和上面的解法都一样。
                new_path.add(candidate);
                new_path.addAll(path);
                current.add(new_path);
            }
        }
        dp.set(target, current);    // 此问题的解集已经算出来了，更新到dp备忘，供以后直接取用。
        return dp.get(target);
    }

    /** 优化穷举法，先排序后干活，通过禁止回退来避免重复。 */
    // 避免重复路径 技巧1：
    // 通过观察分析重复路径的共同特征就是出现了回退：{2, 2, 3} {2, 3, 2} {3, 2, 2}
    // 以x为分支后，后面不能再跟小于x的分解值：例如走到了2 -> 3 -> ?这条路，那么接下来不允许2 -> 3 -> 2出现就不会记录重复路径。
    // 同理，如果走到了3 -> ?这条路，那么接下来不允许出现3 -> 2这条路径。
    // 这样的规则下，我们可以确保只记录下来2 -> 2 -> 3这条路径。从而完全避免了重复路径的记录。
    // 避免重复路径 技巧2：
    // 如果数组a中包含重复元素，虽然很坑爹，但是为了避免生成重复路径，需要跳过这些重复的元素。
    // 由于candidate已经排序，所以只需要检查相邻元素是否相等即可。
    static List<List<Integer>> combSum_BK2(int[] a, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);     // 确保Candidate按顺序排号，避免记录重复路径（技巧1 + 技巧2）
        backtrack2(a, target, result, new ArrayList<>(), 0);
        return result;
    }

    static void backtrack2(int[] a, int target, List<List<Integer>> result, List<Integer> current, int start) {
        // 递归终止条件：要不然无解，要不然找到完整解立即返回，要不然接着递归。
        if (target < 0) return;
        if (target == 0) {result.add(new ArrayList<>(current)); return;}    // 拷贝当前路径的值存入result，而不是直接添加。
        for (int i = start; i < a.length; i++) {                    // 避免重复：不从0开始尝试，而是从上一层访问位置的右侧开始尝试
            current.add(a[i]);                                      // 依然是先添后删
            backtrack2(a, target - a[i], result, current, i);
            current.remove(current.size() - 1);
            while (i + 1 < a.length && a[i + 1] == a[i]) i++;       // 避免Candidate中有重复元素。
        }
    }

    /** 穷举法（其实并没有什么回溯），完全的递归每一个可能的分支。用HashSet来避免存储重复路径。效率奇低。
     * Time - o(n^m) n为candidate的个数，m为层高。*/
    // 为了避免重复，例如 {2, 2, 3} {2, 3, 2} {3, 2, 2}
    // 可以先把所有找到的path都存到Set里面，最后再把Set导出为ArrayList返回。
    static List<List<Integer>> combSum_BK(int[] a, int target) {
        Set<List<Integer>> result = new HashSet<>();
        if (a == null || a.length == 0) return new ArrayList<>(result);
        backtrack(a, target, result, new ArrayList<>());
        return new ArrayList<>(result);
    }
    // 特别要注意递归中来回传递当前访问路径必须拷贝一份，而不是直接使用传入的引用，这样会导致你添加进result的路径在之后会被修改。
    static void backtrack(int[] a, int target, Set<List<Integer>> result, List<Integer> path) {
        List<Integer> current_path = new ArrayList<>(path);     // 完全拷贝，避免原path被修改。
        if (target < 0) return;
        if (target == 0) {
            Collections.sort(current_path);     // 先调整顺序
            result.add(current_path);           // 再添加。添加的过程会隐式的比较ArrayList是否相等。
            return;
        }
        for (int x : a) {
            current_path.add(x);                                        // 先添加上
            backtrack(a, target - x, result, current_path);
            current_path.remove(current_path.size() - 1);               // 递归完把path恢复到之前的状态，以供下个循环尝试添加。
        }
    }
}
