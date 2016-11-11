package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by LYuan on 2016/11/11.
 * Given a collection of distinct numbers, return all possible permutations.
 *
 * For example,
 * [1,2,3] have the following permutations:
 * [
 *  [1,2,3],
 *  [1,3,2],
 *  [2,1,3],
 *  [2,3,1],
 *  [3,1,2],
 *  [3,2,1]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> permutation(int[] a) {...}
 *
 */
public class M46_Permutations {
    public static void main(String[] args) {
        List<List<Integer>> result = permutation3(new int[] {1, 2, 3});
    }

    // 这类涉及到穷举的题目最需要小心的就是在判定解无效后，如何后退。
    // 思路有两条：一个是判定解无效，然后删除当前更新的组合结果的最后一位。（很容易出现复原不完全，得到的结果不对的情况。）
    // 还有就是根本不让无效解算出来，只计算有效的。（比如这里的DP解法）

    /** DP解法，迭代写法，找到每轮之间的递推关系（状态转移），非常优美的解法。*/
    // 我们可以通过列举长度从1到n每轮的所有状态，发现当前轮的每个状态都是上一轮中的状态的变体。
    // n = 0:         [ ]
    // n = 1:         (1)                                                将1插入上一轮的所有状态（就一个，就是空数组）
    //            ↑          ↑
    // n = 2:   2 (1)      (1) 2                                         将2插入上一轮的所有状态的所有可能位置。
    //       ↑   ↑   ↑    ↑   ↑   ↑
    // n = 3:  3 (2 1) | (2 3 1) | (2 1) 3 | 3 (1 2) | 1 3 2 | (1 2) 3   将3插入上一轮的所有状态的所有可能位置。
    // DP用起来爽爽的。
    // 每轮都计算出一个result，然后为下一轮所用，不断更新，所以理论上来讲Memoization用的dp应该是一个List<List<List<Integer>>> (三重list)
    // 然后我们发现，上面的递推过程中，每一轮的所有状态都可以由上一轮的所有状态所完全决定！
    // 这么来说我们根本不需要存所有轮（三重list），而是只需要两个互为备份的List<List<Integer>>即可。
    // 所以下面外面定义一个用来返回的dp（上一轮解空间，即所有状态），里面又定义了一个newdp（当前轮解空间），
    // 用来临时存储当前轮按dp逐条更新的结果，然后每轮结束之后再把newdp给dp，newdp再指向一个新的空间。
    static List<List<Integer>> permutation3(int[] a) {
        List<List<Integer>> dp = new ArrayList<>();
        if (a == null || a.length == 0) return dp;
        List<Integer> init = new ArrayList<>();     // 开始循环前得先往dp中放一个空path，要不然启动不了。
        dp.add(init);
        for (int i = 0; i < a.length; i++) {        // 外循环：i表示轮数，第0轮相当于是从无到有的过程。[] -> [1]
            List<List<Integer>> newdp = new ArrayList<>();          // 申请一个新空间，用来缓存从上一轮的结果更新的条目。
            for (List<Integer> path : dp) {                         // 中循环：遍历上一轮的所有状态
                for (int ins = 0; ins < path.size() + 1; ins++) {   // 内循环：ins表示插入索引位置，注意范围要+1，因为植树原理：0个元素则有1个插入位置，1个元素则有2个插入位置。
                    List<Integer> entry = new ArrayList<>(path);    // 需要申请新的List内存空间拷贝path值，确保原有的path完好无损。
                    entry.add(ins, a[i]);                           // 在path的拷贝上的所有位置插入a[i]，
                    newdp.add(entry);                               // 存入新的dp解空间中了。
                }
            }
            dp = newdp;     // 把当前轮的解空间存回dp，供下轮用。
        }
        return dp;
    }


    /** 回溯法，通过判断待添加值是否已经在path中来剪枝，Time - o(n^2), Space - o(n) */
    // 使用ArrayList作为缓存，并且排除当前path中出现的所有元素。
    // 1    1   X
    //      2   3
    //      3   2
    //
    // 2    1   3
    //      2   X
    //      3   1
    //
    // 3    1   2
    //      2   1
    //      3   X
    static List<List<Integer>> permutation2(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        permute_your_ass(result, new ArrayList<>(), a);
        return result;
    }
    // 在生成当前path的最后，必须要把path存成一个新的ArrayList，否则相当于是重复的修改同一个ArrayList，最后得到的结果是重复的。
    static void permute_your_ass(List<List<Integer>> result, List<Integer> path, int[] a) {
        if (path.size() == a.length) {
            result.add(new ArrayList<>(path)); // 易错点：写成result.add(current)就错了。因为result里面只存了current的引用。
            return;                            // 一旦current被修改，那么result里的结果也被一同修改了。
        }
        for (int i = 0; i < a.length; i++) {
            if (!path.contains(a[i])) {        // 是的你没看错，ArrayList也是可以用contains()这个方法的，虽然复杂度是o(n).
                path.add(a[i]);
                permute_your_ass(result, path, a);
                path.remove(path.size() - 1);
            }
        }
    }

    /** 回溯法，和上面的解法相同，只不过用的是HashSet，带来的问题是需要不断的new临时的新HashSet，所以整体速度反而更慢。 */
    // 一开始非要走火入魔的用HashSet来硬解。
    // 结果是十分郁闷的，因为HashSet的遍历比较讨厌，不能够在遍历的同时修改HashSet的内容。
    static List<List<Integer>> permutation(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int x : a) set.add(x);
        permute_fucking_stupid_recursive(set, new ArrayList<>(), result);
        return result;
    }

    static void permute_fucking_stupid_recursive(Set<Integer> set, List<Integer> current, List<List<Integer>> result) {
        if (set.isEmpty()) {
            result.add(current);
            return;
        }
        for (int x : set) {
            Set<Integer> newSet = new HashSet<>(set);
            List<Integer> newCurrent = new ArrayList<>(current);
            newCurrent.add(x);
            newSet.remove(x);
            permute_fucking_stupid_recursive(newSet, newCurrent, result);
        }
    }

}
