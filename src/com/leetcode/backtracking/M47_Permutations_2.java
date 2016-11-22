package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/11/22.
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * For example, [1,1,2] have the following unique permutations:
 * [
 *  [1,1,2],
 *  [1,2,1],
 *  [2,1,1]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> permute(int[] a) {...}
 */
public class M47_Permutations_2 {
    public static void main(String[] args) {
        List<List<Integer>> result = permute(new int[] {1, 1, 2});
    }

    /** 回溯法，路径增删法，特点在于路径中存的不是实际值，而是访问元素的索引。 */
    // 难点在于如何让重复的元素该记录的时候记录，不该记录的时候不记录
    // [1, 1, 2]
    // 1 - 1 - 2
    //   - 2 - 1
    // 1 - dup
    //   - dup
    // 2 - 1 - 1
    //   - 1 - dup
    // 可以看到，需要避免的重复只会出现在同层的for循环中。
    // 第一层的两个1互相重复，当第一层取2的时候，第二层的两个1互相重复。
    // 因此需要避免在每一层的for循环中对值相同的元素进行下一层递归。
    // 与Subset这类题不同，每到新的一层，扫描空间必须从0开始，而不是限制为上一个搜索位置的右侧。
    // 与Permuatation1这道题不同，不能排除path中已经出现的元素值，而应该只排除那些已经访问过的元素<位置>，毕竟这里元素是可以重复的，但访问位置不重复即可。
    // 所以，在这里记录在path中的数据并不是candidate数组中的元素值，而是元素索引。
    // 相应的，一旦path记满了，需要存入result的时候，只需再遍历一遍path，并且把对应的元素值添加进真的path中返回即可。
    static List<List<Integer>> permute(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        Arrays.sort(a);
        backtrack(a, result, new ArrayList<>());
        return result;
    }

    static void backtrack(int[] a, List<List<Integer>> result, List<Integer> path) {
        if (path.size() == a.length) {
            List<Integer> real = new ArrayList<>();
            for (int idx : path) real.add(a[idx]);
            result.add(real);
            return;
        }
        // 由于for循环的i会自增，因此需要试探的是i + 1是否和i重复，如果重复，就在循环内手动自增i，
        // 直到以下两种情况之一就结束本轮循环：
        // 情况1：i + 1是最后一个元素，
        // 情况2：i + 1是不同于i的值
        // 结束本轮循环后，for循环就会对i再次进行自增，那么要不然循环彻底结束，要不然开始遍历不同的元素。
        // 总之可以确保每一层的搜索中不会选取相同值的元素递归。
        for (int i = 0; i < a.length; i++) {
            if (!path.contains(i)) {
                path.add(i);
                backtrack(a, result, path);
                path.remove(path.size() - 1);
                while (i + 1 < a.length && a[i + 1] == a[i]) i++;
            }
        }
//        int i = 0;
//        while (i < a.length) {
//            if (path.contains(i)) i++;
//            else {
//                path.add(i);
//                backtrack(a, result, path);
//                path.remove(path.size() - 1);
//                i++;
//                while (i < a.length && a[i] == a[i - 1]) i++;
//            }
//        }
    }
}
