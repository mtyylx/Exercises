package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 2016/11/20.
 * Given a set of distinct integers, nums, return all possible subsets.
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * For example,
 * If nums = [1,2,3], a solution is:
 * [
 *  [3],
 *  [1],
 *  [2],
 *  [1,2,3],
 *  [1,3],
 *  [2,3],
 *  [1,2],
 *  []
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> subset(int[] a) {...}
 *
 * 与M39/M40的写法几乎完全一样。
 */
public class M78_Subsets {
    public static void main(String[] args) {
        List<List<Integer>> result = subset(new int[] {1, 2, 3});
        List<List<Integer>> result2 = subset2(new int[] {1, 2, 3});
        List<List<Integer>> result3 = subset3(new int[] {1, 2, 3});
        List<List<Integer>> result4 = subset4(new int[] {1, 2, 3});
        List<List<Integer>> result5 = subset5(new int[] {1, 2, 3});
    }

    /** 比特翻转法 */
    // subset的问题可以转化为candidate数组（即数组a）中每个元素是否include的问题。
    // a中有n个元素，那么就有2^n中组合
    // 0 0 0 = {}
    // 0 0 1 = {3}
    // 0 1 0 = {2}
    // 0 1 1 = {2, 3}
    // 1 0 0 = {1}
    // 1 0 1 = {1, 3}
    // 1 1 0 = {1, 2}
    // 1 1 1 = {1, 2, 3}
    static List<List<Integer>> subset4(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        int size = 1 << a.length;   // Get the value of 2^n.
        for (int i = 0; i < size; i++) {
            int x = i;
            List<Integer> path = new ArrayList<>();
            for (int idx = 0; x > 0; idx++, x /= 2) {       // 利用int转str的求余除2法。本质就是将每个数都二进制化。
                int res = x % 2;
                if (res > 0) path.add(a[idx]);
            }
            result.add(path);
        }
        return result;
    }

    // 同样是比特翻转法，但是将内循环逻辑优化成为使用bitmask求与来add元素。
    static List<List<Integer>> subset5(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        int size = 1 << a.length;   // Get the value of 2^n.
        for (int i = 0; i < size; i++) {
            int x = i;
            List<Integer> path = new ArrayList<>();
            for (int mask = 0; mask < a.length; mask++)    // mask = 0, 1, 2 则 1 << mask = 001, 010, 100
                if ((i & (1 << mask)) != 0)                // 检查每一位是否是为0，不是0就add。
                    path.add(a[mask]);
            result.add(path);
        }
        return result;
    }


    /** DP解法，Bottom-Up（积少成多），迭代写法，十分优美。 */
    // a = {}时，新增result = {{}}
    // a = {1}时，新增result = {{1}}
    // a = {1,2}时，新增result = {{1,2}, {2}}
    // a = {1,2,3}时，新增result = {{1,2,3}, {2,3}, {1,3}, {3}}
    // 从上面的递推过程中可以发现，每一轮的新增状态都是之前所有轮新增状态append当前轮新增元素而构成。
    // 而“之前所有轮新增状态”其实就是result本身所存储的。所以result就是dp memoization的实体。
    // 同时我们也发现，只要题目已经强调不会出现重复元素，那么其实这类题完全不需要事先对a进行排序。因为使用的逻辑已经隐式的确保了不会遗失。
    static List<List<Integer>> subset3(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int i = 0; i < a.length; i++) {
            int size = result.size();           // 注意易错点：如果不缓存result.size，那么由于内循环在不断扩容，因此会形成无限循环。
            for (int j = 0; j < size; j++) {
                List<Integer> temp = new ArrayList<>(result.get(j));    // 注意易错点：必须拷贝至新list，避免修改现有path。
                temp.add(a[i]);
                result.add(temp);
            }
        }
        return result;
    }

    /** 回溯法，随时记录当前路径，显示搜索范围以避免重复路径。*/
    // 这类题目的关键在于要设计传递索引i。
    static List<List<Integer>> subset(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        if (a == null || a.length == 0) return result;
        // Arrays.sort(a);
        subset_recursive(a, result, new ArrayList<>(), 0);
        return result;
    }

    static void subset_recursive(int[] a, List<List<Integer>> result, List<Integer> current, int i) {
        while (i < a.length) {
            current.add(a[i]);                                  // 添加路径节点
            result.add(new ArrayList<>(current));               // 随时记录（区别于M39/M40的地方）
            if (i < a.length - 1)                               // 直接避免出现越界情况，而不是等下一层递归方法的开始再处理，避免重复记录路径。
                subset_recursive(a, result, current, i + 1);    // 递归，并限定搜索范围
            current.remove(current.size() - 1);                 // 恢复路径节点
            i++;                                                // 由于题目里已经表明a不会有重复元素，所以无需检查。
        }
    }

    // 上面解法的简化写法，在每次扫描搜索范围之前（即for循环开始前）就直接把当前路径添加到结果中。这样可以省略判断抵达边界的语句。
    static List<List<Integer>> subset2(int[] a) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length == 0) return result;
        // Arrays.sort(a);
        subset_recursive2(a, result, new ArrayList<>(), 0);
        return result;
    }

    static void subset_recursive2(int[] a, List<List<Integer>> result, List<Integer> current, int idx) {
        result.add(new ArrayList<>(current));
        for (int i = idx; i < a.length; i++) {
            current.add(a[i]);
            subset_recursive2(a, result, current, i + 1);
            current.remove(current.size() - 1);
        }
    }
}
