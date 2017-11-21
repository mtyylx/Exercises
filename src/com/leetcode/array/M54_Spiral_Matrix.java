package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2017/11/5.
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 * For example, given the following matrix:
 * [
 *     [ 1, 2, 3 ],
 *     [ 4, 5, 6 ],
 *     [ 7, 8, 9 ]
 * ]
 *
 * You should return [1,2,3,6,9,8,7,4,5].
 *
 * Function Signature:
 * public List<Integer> spiralOrder(int[][] matrix) {...}
 *
 * <系列问题>
 * - M54 Spiral Matrix
 * - M59 Spiral Matrix 2
 *
 * <Tags>
 * - Two Pointers: 对向双指针扫描
 *
 */
public class M54_Spiral_Matrix {
    public static void main(String[] args) {
        int[] a = new int[]{ 1,  2,  3,  4};
        int[] b = new int[]{10, 11, 12,  5};
        int[] c = new int[]{ 9,  8,  7,  6};
        int[][] matrix = new int[][]{a, b, c};
        System.out.println(spiralOrder(matrix));
        System.out.println(spiralOrder2(matrix));
    }


    // 双指针扫描
    //  1  -  2  -  3  -  4
    //                    |
    // 10  - 11  - 12     5
    //  |                 |
    //  9  -  8  -  7  -  6
    //
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix.length == 0) return res;
        int row_start = 0;
        int row_stop = matrix.length - 1;
        int col_start = 0;
        int col_stop = matrix[0].length - 1;
        while (row_start <= row_stop && col_start <= col_stop) {
            for (int col = col_start; col <= col_stop; col++) res.add(matrix[row_start][col]);         // 上边
            for (int row = row_start + 1; row <= row_stop; row++) res.add(matrix[row][col_stop]);      // 右边
            if (row_start < row_stop && col_start < col_stop) {
                for (int col = col_stop - 1; col > col_start; col--) res.add(matrix[row_stop][col]);   // 下边
                for (int row = row_stop; row > row_start; row--) res.add(matrix[row][col_start]);      // 左边
            }
            row_start++;
            row_stop--;
            col_start++;
            col_stop--;
        }
        return res;
    }

    public static List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix.length == 0) return res;
        int R = matrix.length;
        int C = matrix[0].length;
        boolean[][] seen = new boolean[R][C];
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        int r = 0, c = 0, di = 0;
        for (int i = 0; i < R * C; i++) {
            res.add(matrix[r][c]);
            seen[r][c] = true;
            int cr = r + dr[di];
            int cc = c + dc[di];
            if (0 <= cr && cr < R && 0 <= cc && cc < C && !seen[cr][cc]){
                r = cr;
                c = cc;
            } else {
                di = (di + 1) % 4;
                r += dr[di];
                c += dc[di];
            }
        }
        return res;
    }
}
