package com.leetcode.backtracking;

/**
 * Created by Michael on 2016/11/26.
 *
 * Follow up for N-Queens problem.
 * Now, instead outputting board configurations, return the total number of distinct solutions.
 *
 * Function Signature:
 * public int nQueensCount(int n) {...}
 *
 */
public class H52_N_Queens_2 {
    public static void main(String[] args) {
        System.out.println(nQueensCount(8));
        System.out.println(nQueensCount2(8));
    }

    // 对于只计算解个数而不计算具体解是什么，那么算法在哪块儿能够优化呢？
    // 最值得优化的地方是递归函数应该不用再传递当前进行的path了，而是只花精力在尽早排除那些不可能的皇后位置，
    // 只要我们的递归深度达到了n，就自动说明找到了一个完整解，count++即可，无需存入result再恢复path等这类操作。
    /** 回溯法
     * 本质上这三个数组就完全描述了当前状态下可能出现Queen的所有位置，并且告诉你现在再选位置绝对不能在这些位置上。 */
    // 现在我们已经了解到只需要检查<三个方向>即可知道当前位置是否合法。
    // 方向一：当前元素所在列，boolean数组每一个元素表示对应列。
    // 方向二：左对角线，row - col的每一个不同值都代表一条左对角线。这个值会出现负值，因此为了让这个值作为数组索引使用，需要归一化为row - col + n
    // 方向三：右对角线，row + col的每一个不同值都代表一条右对焦线。因为这个值不会出现负值，所以直接用即可。
    // 原矩阵横纵坐标       列矩阵[0-4]  左对角线[1-9]  右对角线[0-8]
    // 00 01 02 03 04    0 1 2 3 4    5 4 3 2 1    0 1 2 3 4
    // 10 11 12 13 14    0 1 2 3 4    6 5 4 3 2    1 2 3 4 5
    // 20 21 22 23 24    0 1 2 3 4    7 6 5 4 3    2 3 4 5 6
    // 30 31 32 33 34    0 1 2 3 4    8 7 6 5 4    3 4 5 6 7
    // 40 41 42 43 44    0 1 2 3 4    9 8 7 6 5    4 5 6 7 8
    // 可以看到，使用boolean数组标识特定列 / 特定左右对角线可以极大的简化运算量。原本需要逐行逐列的标记一个元素是否是Queen，这下只用一个数字就可以标记一整排。
    // 如果rightDiag数组的值是{False, True, False, False, False, True, False, False, False}，则表示：
    // -- Qn -- -- --
    // Qn -- -- -- Qn
    // -- -- -- Qn --   Qn表示有可能出现Queen，并不需要知道具体位置，因为只要会出现，那么整条线都立刻不能放任何其他Queen了。
    // -- -- Qn -- --
    // -- Qn -- -- --
    static int count = 0;       // 省得递归方法再写一个入参了
    static int nQueensCount(int n) {
        backtrack(n, 0, new boolean[n], new boolean[n * 2], new boolean[n * 2]);
        return count;
    }

    static void backtrack(int n, int row, boolean[] column, boolean[] leftDiag, boolean[] rightDiag) {
        if (row == n) count++;          // 可以不写return，因为超过最后一行之后，每一列就都有Queen了，因此一定会退出。
        for (int col = 0; col < n; col++) {
            int left = row - col + n;
            int right = row + col;
            if (column[col] || leftDiag[left] || rightDiag[right]) continue;    // Skip invalid position
            column[col] = true;         // Update Queen Map
            leftDiag[left] = true;
            rightDiag[right] = true;
            backtrack(n, row + 1, column, leftDiag, rightDiag);     // Recurse next row
            column[col] = false;        // Reverse Queen Map
            leftDiag[left] = false;
            rightDiag[right] = false;
        }
    }

    /** 上面方法的细微修改版：把count集成在递归方法中的版本 */
    static int nQueensCount2(int n) {
        return backtrack2(n, 0, new boolean[n], new boolean[n * 2], new boolean[n * 2]);
    }

    static int backtrack2(int n, int row, boolean[] column, boolean[] leftDiag, boolean[] rightDiag) {
        int count = 0;
        if (row == n) return 1;
        for (int col = 0; col < n; col++) {
            int left = row - col + n;
            int right = row + col;
            if (column[col] || leftDiag[left] || rightDiag[right]) continue;    // Skip invalid position
            column[col] = true;         // Update Queen Map
            leftDiag[left] = true;
            rightDiag[right] = true;
            count += backtrack2(n, row + 1, column, leftDiag, rightDiag);     // Recurse next row
            column[col] = false;        // Reverse Queen Map
            leftDiag[left] = false;
            rightDiag[right] = false;
        }
        return count;
    }
}
