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
 * - Array
 *
 */
public class M54_Spiral_Matrix {
    public static void main(String[] args) {

    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List res = new ArrayList();
        if (matrix.length == 0) return res;
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) {
            for (int c = c1; c <= c2; c++) res.add(matrix[r1][c]);
            for (int r = r1 + 1; r <= r2; r++) res.add(matrix[r][c2]);
            if (r1 < r2 && c1 < c2) {
                for (int c = c2 - 1; c > c1; c--) res.add(matrix[r2][c]);
                for (int r = r2; r > r1; r--) res.add(matrix[r][c1]);
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return res;
    }

    public List<Integer> spiralOrder2(int[][] matrix) {
        List res = new ArrayList();
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
