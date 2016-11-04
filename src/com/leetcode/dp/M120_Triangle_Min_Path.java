package com.leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/11/4.
 * Given a triangle, find the minimum path sum from top to bottom.
 * Each step you may move to adjacent numbers on the row below.
 *
 * For example, given the following triangle
 * [
 *   [2],
 *   [3,4],
 *   [6,5,7],
 *   [4,1,8,3]
 * ]
 *
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 * Note: Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
 *
 * Function Signature:
 * public int minPathTriangle(List<List<Integer>> triangle) {...}
 */
public class M120_Triangle_Min_Path {
    public static void main(String[] args) {
        List<Integer> level1 = new ArrayList<>();
        level1.add(1);
        List<Integer> level2 = new ArrayList<>();
        level2.add(2);
        level2.add(3);
        List<List<Integer>> tri = new ArrayList<>();
        tri.add(level1);
        tri.add(level2);
        System.out.println(minPathTriangle3(tri));
    }

    /** DP解法1，Top-Down，Iterative，Memoization (in-place), Time - o(n), Space - o(1), n = number of rows. */
    // 修改了原有三角形的数据
    static int minPathTriangle(List<List<Integer>> a) {
        if (a == null || a.size() == 0) return 0;
        int n = a.size();

        for (int level = 0; level < n - 1; level++) {
            List<Integer> cur = a.get(level);
            List<Integer> next = a.get(level + 1);
            for (int i = 0; i <= cur.size(); i++) {
                if      (i - 1 < 0)         next.set(i, next.get(i) + cur.get(i));            // left-most element
                else if (i == cur.size())   next.set(i, next.get(i) + cur.get(i - 1));        // right-most element
                else    next.set(i, next.get(i) + Math.min(cur.get(i - 1), cur.get(i)));      // middle element
            }
        }
        // Find the min of list in the bottom and return.
        int min = a.get(n - 1).get(0);
        for (int i = 0; i < a.get(n - 1).size(); i++)
            min = Math.min(min, a.get(n - 1).get(i));
        return min;
    }

    /** DP解法2，Bottom-Up，Memoization (In-place), Time - o(n), Space - o(1) */
    // 修改了原有三角形数据。
    //    [2],
    //   [3,4],
    //  [6,5,7],
    // [4,1,8,3]
    // 通过观察，我们可以看到这个三角形自下而上看的话非常符合树的结构，而且针对每个小三角形都是一个最优子结构。
    /** 问题1：为什么说每个小三角形都是最优子结构？ */
    // 对于下面的三角形
    //     6
    //   4   1
    //  ...  ...
    // 对于根节点6来说，只要我们知道了其左右叶子分支4和1谁的<路径和>更小，我们就可以肯定知道根节点6的<最小路径和>。
    // 也就是说，根节点问题（路径和）的最优解（最小路径和）完全由其子问题（两个叶子节点分支各自的路径和）的最优解（各自的最小路径和）决定。
    // 然后我们实际上要做的就是不断自底向上更新最优解，直至塔顶。
    /** 问题2：为什么都已经是最优子结构了，还不能用贪心来解？ */
    // 虽然每个小三角形都是最优子结构，但是每个节点都需要全部子问题的解，也就是说，不能够只找每一层的最小值，然后组合在一起就OK了。
    static int minPathTriangle2(List<List<Integer>> a) {
        if (a == null || a.size() == 0) return 0;
        int n = a.size();
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                a.get(i - 1).set(j, a.get(i - 1).get(j) + Math.min(a.get(i).get(j), a.get(i).get(j + 1)));
            }
        }
        return a.get(0).get(0);
    }

    /** DP解法3，Bottom-Up，不修改原有三角形数据，使用额外空间。Time - o(n), Space - o(n) */
    //    [2],
    //   [3,4],
    //  [6,5,7],
    // [4,1,8,3]
    // 从最底部这行开始，由于没有子节点，因此dp数组的每个元素就是原数据本身。
    // 紧接着第二行，dp数组应该更新为该行原数据加上之前dp数组相邻元素中小的那个。实时更新dp[]数组的每个元素刚好不会相互影响。
    static int minPathTriangle3(List<List<Integer>> a) {
        if (a == null || a.size() == 0) return 0;
        int n = a.size();
        int[] dp = new int[n + 1];          // 最底行需要访问n+1，因为有一个虚拟的n+1全零行。
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = a.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0];
    }
}
