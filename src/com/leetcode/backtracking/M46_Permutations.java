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
        List<List<Integer>> result = permutation2(new int[] {1, 2, 3});
    }

    /** 回溯法，通过判断待添加值是否已经在path中来剪枝 */
    // 使用ArrayList作为缓存，并且排除当前path中出现的所有元素。
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
