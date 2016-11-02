package com.leetcode.dp;

/**
 * Created by LYuan on 2016/11/2.
 * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
 *
 * For example, Given n = 3, there are a total of 5 unique BST's.
 *          1         3     3      2      1
 *          \       /     /      / \      \
 *           3     2     1      1   3      2
 *         /     /       \                 \
 *       2     1         2                 3
 *
 * Function Signature:
 * public int uniqueBST(int n) {...}
 */
public class M96_Unique_Binary_Search_Tree {
    public static void main(String[] args) {
        System.out.println(uniqueBST2(4));
    }

    // BST的基本性质：任何一个节点的左子树中的所有子节点都小于这个节点，任何一个节点的右子树上的所有节点都大于这个节点。

    // DP解法最关键的是如何找到状态转移的规律。一开始我在手动列出各种BST时，想到的是下一轮用到的是上一轮中的树形态的扩展（即在头或尾添加）
    // 但是这么考虑需要判断到底某一个值添加到现有树结构的首或尾是否合法（因为必须保证添加之后依然满足BST的基本性质），所以不好下手。
    // 研究了那么多树的问题，其实应该想到树区别于其他数据结构的一个最大特色就是树的根节点和子节点之间的自相似性，特别适合递归。
    // 因此寻找状态转移的规律上，应该在子树上入手。如果子树的特性符合之前计算过的n的特性，那么就可以直接加以利用。

    /** DP解法，Memoization，Iterative，Time - o(n), Space - o(n)
     *  空间复杂度为o(n)因为当前轮与之前所有轮都有关系。 */
    // 从下面的列举中，可以看到，其实i在不断增长的过程中，每一轮的状态可能性都是之前所有轮的状态的组合（左子树状态可能性乘以右子树状态可能性）
    // 因此只要维护一个数组，步步为营的以初始状态值（n=0和n=1）推导出每一轮的所有可能状态组合，并继续被后面的轮所复用。依然是Memoization。
    // 先从0开始列举，找规律：
    // n = 0: 按理说应该是0，但是后面频繁需要用到左子树或右子树为空的情况，即dp[0]，为了相乘不为0，需要把dp[0]强制赋值为1.
    // n = 1:      1                                                (1)
    //
    // n = 2:
    // if root = 1, left = x (0) right = 2 (1) -> dp[0] * dp[1]
    // if root = 2, left = 1 (1) right = x (0) -> dp[1] * dp[0]
    //             1                    1                           (2) = 1 + 1
    //              \                  /
    //              {2}              {2}
    //             (n=1)            (n=1)
    //
    // n = 3:
    // if root = 1, left = x (0), right = 2, 3 (2) -> dp[0] * dp[2]
    // if root = 2, left = 1 (1), right = 3 (1)    -> dp[1] * dp[1]
    // if root = 3, left = 1, 2 (2) right = x (0)  -> dp[2] * dp[0]
    //
    //             1                 2                3             (5) = 2 + 1 + 2 = (1 + 1) + 1 + (1 + 1)
    //              \               / \              /
    //               {2, 3}       {1} {3}      {1, 2}
    //               (n=2)      (n=1) (n=1)     (n=2)
    //         1         1           2               3     3
    //          \         \         / \             /     /
    //           2         3       1   3           2     1
    //            \       /                       /       \
    //             3     2                       1         2
    //
    // n = 4:                                                       (14) = 1 * 5 + 1 * 2 + 2 * 1 + 5 * 1
    // if root = 1, left = x (0) right = 2, 3, 4 (3) -> dp[0] * dp[3]
    // if root = 2, left = 1 (1) right = 3, 4 (2)    -> dp[1] * dp[2]
    // if root = 3, left = 1, 2 (2) right = 4 (1)    -> dp[2] * dp[1]
    // if root = 4, left = 1, 2, 3 (3) right = x (0) -> dp[3] * dp[0]
    static int uniqueBST(int n) {
        if (n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - 1 - j];     // j从0开始计算，表明没有子树的分支，但是另一分支需要记得减一，因为root本身还占一个。
            }
        }
        return dp[n];
    }

    /** Catalan: 使用一种叫做Catalan数的特性解决问题, Time - o(n), Space - o(1) */
    // 这个数广泛用于组合数学中，刚好也能用在二叉树的形状上。很神奇。
    // Catalan数的递推公式是Cn+1 = Cn * (4n + 2) / (n + 2)
    static int uniqueBST2(int n) {
        long x = 1;
        for (int i = 1; i < n; i++) {
            x = x * (4 * i + 2) / (i + 2);
        }
        return (int) x;
    }
}
