#include "../common.h"
using namespace std;

/**
 * Created by Michael on 2019/8/25.
 * 
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 * 
 * Example:
 * Input: 3
 * Output: 5
 * Explanation: Given n = 3, there are a total of 5 unique BST's:
 *
 *   1         3     3      2      1
 *    \       /     /      / \      \
 *     3     2     1      1   3      2
 *    /     /       \                 \
 *   2     1         2                 3
 *
 * 基本思想
 *      - 考察什么是 BST：空树、或者满足左子树所有节点都小于根节点、右子树所有节点都大于根节点、左右子树也都是 BST。
 *      - Bottom-Up DP：从最小规模的问题开始解，确定递推公式，一直推广至问题规模为 N 的情况
 *      - 递推的思考角度：子树视为上一级问题。
 *      - Catalan 数：递推公式满足 Cn + 1 = \sigma_{i = 0}^{N} C(n - i) C(i)，则可以用阶乘公式直接计算（本质与 DP 一样）
 * 
 * Catalan 数的应用
 *      - 从 1 到 n 的元素，问可以构造出多少个<取值不同>且<结构不同>的<BST>
 *      - n 个节点，问可以构造出多少个<结构不同>的<一般二叉树>，不考虑结构相同但取值不同的情况
 *      - 2n + 1 个节点，问可以构造出多少个<结构不同>的<满二叉树>
 *      - 从 1 到 n 的元素，问可以有多少种不同的入栈和出栈的顺序
 * 
 */

// 关键在于确定递推公式的思考方式：
// 1. <当前问题>与<小一级问题>之间的联系：当前问题规模的两个<子树>，分别是小一号问题规模的解
// 2. 两个子树如何组合：相乘（组合）
// 3. 起始特例：dp[0] = 1, dp[1] = 1

int numTrees(int n) {
    vector<int> dp(n);    // zero init
    dp[0] = 1;            // empty tree has only one unique structure
    dp[1] = 1;            // single node tree has only one unique structure
    for (int N = 2; N <= n; N++) {               // N 为问题规模，从 2 开始计算，因为 N = 0 或 1 已经在上面确定
        for (int i = 1; i <= N; i++) {           // i 为子树的排列组合特性
            dp[N] += dp[N - i] * dp[i - 1];      // dp[N] = \sigma_{i = 1}^N {dp[N - i] * dp[i - 1]}
        }
    }
    return dp[n];
}

/*

      图解递推公式：每个子树是上一个级别的问题。当前问题的解，是两个子树解的个数的乘积。

      dp[2], {1, 2}             1               2             
                              /  \            /   \
                             x   [2]        [1]   x
                          dp[0] dp[1]     dp[1]  dp[0]    =   dp[0] * dp[1] + dp[1] * dp[0]

      dp[3], {1, 2, 3}          1                2                 3
                              /   \            /   \              / \
                            x  [2, 3]      [ 1 ] [ 2 ]       [1, 2]  x
                          dp[0]  dp[2]     dp[1] dp[1]       dp[2]  dp[0]    =   dp[0] * dp[2] + dp[1] * dp[1] + dp[2] * dp[0]
                                                                             
      dp[4] = dp[0] * dp[3] + dp[1] * dp[2] + dp[2] * dp[1] + dp[3] * dp[0] = dp[4 - 4] * dp[4 - 1] + dp[4 - 3] * dp[3 - 1] + dp[4 - 2] * dp[2 - 1] + dp[4 - 1] * dp[1 - 1]

 */

int main() {

    cout << numTrees(3) << endl;
}