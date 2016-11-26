package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/11/24.
 *
 * The n-queens puzzle is the problem of placing n queens
 * on an n×n chessboard such that no two queens attack each other.
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * Each solution contains a distinct board configuration of the n-queens' placement,
 * where 'Q' and '.' both indicate a queen and an empty space respectively.
 * 这里使用字符串数组来表示，每个字符串表示一行，字符串的每一个字符代表棋盘上的一个位置。
 *
 * For example, There exist two distinct solutions to the 4-queens puzzle:
 * [
 *  [".Q..",  // Solution 1
 *  "...Q",
 *  "Q...",
 *  "..Q."],
 *
 *  ["..Q.",  // Solution 2
 *  "Q...",
 *  "...Q",
 *  ".Q.."]
 * ]
 *
 * Function Signature:
 * public List<List<String>> nQueens(int n) {...}
 */
public class H51_N_Queens {
    public static void main(String[] args) {
        List<List<String>> result = nQueens(8);
        List<List<String>> result2 = nQueens2(8);
        List<List<String>> result3 = nQueens3(8);
    }

    // 科普时间：
    // 1. Queen可以说是象棋中攻击力最高的兵种。可以攻击360度八方向任意距离的兵。
    // 2. 八皇后的解集分为两种，一种叫Distinct Solution Set，只要是不重复的解都算。另一种叫Unique Solution Set，必须是在旋转和镜像都独立的解才算。
    // 也就是说，任何一个解，都可以衍生出<4种旋转解>和<4种镜像解>，只不过这些解可能完全一样（例如解本身就呈轴对称或中心对称）
    // n	        1	2	3	4	5	6	7	8	9	10
    // Unique:  	1	0	0	1	2	1	6	12	46	92
    // Distinct:	1	0	0	2	10	4	40	92	352	724

    /** 关键点1：只需要搜索3个方向 */
    // 虽然每一个Queen都能填满8个方向（左上，上，右上，左，右，左下，下，右下）
    // 但是在实际放置每一个Queen的时候，其实只用考虑八个方向中的三个：左上，上，右上！
    // 因为Queen所在行以及其下面的全部区域都是自由的，只有上方已经填上了其他的Queen，需要检查。

    /** 关键点2：多查少改 */
    // 因为字符串是immutable的，修改很麻烦，应该尽量避免
    // 其实一开始的想法是每放一个Queen，都更新整个矩阵的非法位置，但是这样的性能很低
    // 不如采用每放一个Queen之前都查一下这个位置的所有非法位置是否已经有了其他Queen来的简单。
    // 前者以修改矩阵为主，后者以查阅矩阵为主，当然是后者性能好。

    /** (最初解法) 回溯法，路径增删法，递归写法。*/
    static List<List<String>> nQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        List<String> matrix = new ArrayList<>();
        StringBuilder sb = new StringBuilder();         // 构造空棋盘
        for (int j = 0; j < n; j++) sb.append(".");
        for (int i = 0; i < n; i++) matrix.add(sb.toString());
        backtrack(n, 0, result, matrix);
        return result;
    }

    static void backtrack(int n, int row, List<List<String>> result, List<String> matrix) {
        if (row == n) {
            result.add(new ArrayList<>(matrix));
            return;
        }
        String current = matrix.get(row);                   // 缓存当前层将会修改的那行
        for (int i = 0; i < n; i++) {
            if (isValid(matrix, row, i, n)) {                  // 只有有效位置才继续递归下一层，否则什么都不做。
                updateMatrix(matrix, row, i);               // 修改当前行
                backtrack(n, row + 1, result, matrix);  // 递归下一层
                matrix.set(row, current);                   // 复原当前行
            }
        }
    }

    // 只扫描[↖ ↑ ↗]这三个方向上是否有其他Queen在。
    static boolean isValid(List<String> matrix, int row, int col, int n) {
        for (int i = 0; i < row; i++)                                       // ↑
            if (matrix.get(i).charAt(col) == 'Q') return false;

        for (int x = row - 1, y = col - 1; x >= 0 && y >= 0; x--, y--)      // ↖
            if (matrix.get(x).charAt(y) == 'Q') return false;

        for (int x = row - 1, y = col + 1; x >= 0 && y < n; x--, y++)       // ↗
            if (matrix.get(x).charAt(y) == 'Q') return false;

        return true;
    }

    static void updateMatrix(List<String> matrix, int row, int col) {
        char[] modified = matrix.get(row).toCharArray();
        modified[col] = 'Q';
        matrix.set(row, new String(modified));
    }

    /** (优化后的解法) 依然是回溯法 */
    // 优化1：不在一开始就创建整个棋盘，而是不断的为棋盘添加包含试探解的行。
    // 优化2：找到左右对角线元素坐标值的特性，简化位置判定逻辑。
    // 示例：如以(2,1)为中心向外辐射，可以看到：
    //  左对角线上所有元素横纵坐标的共同特点是row - col的值相同。
    //  右对角线上所有元素横纵坐标的共同特点是row + col的值相同。
    //  用这两个值作为标识，判定对角线元素。
    //  ---  ---  ---  0,3  ---
    //  1,0  ---  1,2  ---  ---
    //  ---  2,1  ---  ---  ---
    //  3,0  ---  3,2  ---  ---
    //  ---  ---  ---  4,3  ---
    static List<List<String>> nQueens2(int n) {
        List<List<String>> result = new ArrayList<>();
        backtrack2(n, 0, result, new ArrayList<>());
        return result;
    }

    static void backtrack2(int n, int row, List<List<String>> result, List<String> matrix) {
        if (row == n) {
            result.add(new ArrayList<>(matrix));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (isValid2(matrix, row, i, n)) {
                char[] current = new char[n];
                Arrays.fill(current, '.');      // 添加全点行，然后添加试探解
                current[i] = 'Q';
                matrix.add(new String(current));
                backtrack2(n, row + 1, result, matrix);
                matrix.remove(matrix.size() - 1);   // 试探结束后恢复棋盘
            }
        }
    }

    // 更简单的位置有效性判定：直接遍历上方所有元素，如果发现元素值为Q且位于同一列或同一左右对角线，就说明位置冲突，退出。
    // j == col 表示该位置与试探位置在同一列
    // row - col == i - j 表示该位置和试探位置在同一条左对角线
    // row + col == i + j 表示该位置和试探位置在同一条右对角线
    static boolean isValid2(List<String> matrix, int row, int col, int n) {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < n; j++)
                if (matrix.get(i).charAt(j) == 'Q' && (j == col || row - col == i - j || row + col == i + j))
                    return false;
        return true;
    }

    /** (最优美的解法) 使用三个boolean数组标记Queen可能出现的区域 */
    // 详细分析见H52. 非常有意思。
    static List<List<String>> nQueens3(int n) {
        List<List<String>> result = new ArrayList<>();
        backtrack3(n, 0, result, new ArrayList<>(), new boolean[n], new boolean[n * 2], new boolean[n * 2]);
        return result;
    }

    static void backtrack3(int n, int row, List<List<String>> result, List<String> matrix,
                           boolean[] column, boolean[] leftdiag, boolean[] rightdiag) {
        if (row == n) {
            result.add(new ArrayList<>(matrix));
            return;
        }
        for (int col = 0; col < n; col++) {
            int left = row - col + n;
            int right = row + col;
            if (column[col] | leftdiag[left] | rightdiag[right]) continue;       // 跳过不合适的位置
            column[col] = true; leftdiag[left] = true; rightdiag[right] = true;  // 更新Queen标记地图
            char[] line = new char[n];
            Arrays.fill(line, '.');
            line[col] = 'Q';
            matrix.add(new String(line));                                         // 构造新的一行
            backtrack3(n, row + 1, result, matrix, column, leftdiag, rightdiag);
            matrix.remove(matrix.size() - 1);                               // 恢复棋盘形态
            column[col] = false; leftdiag[left] = false; rightdiag[right] = false;   // 恢复Queen标记地图
        }
    }

}
