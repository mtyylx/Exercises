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
 * - 矩阵相关：固定四定点，遍历四条边（用不上什么独特的方法）
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


    /** 解法1：固定矩形的四个顶点，依次扫描四条边，再收缩四个顶点，循环往复。 Time - O(N * M) */
    //             上    边                  <特殊情况1>          <特殊情况2>
    //       1  -  2  -  3  -  4          1   2   3   4             1
    //   左                    |  右
    //      10  - 11  - 12     5                                    2
    //   边  |                 |  边
    //       9  -  8  -  7  -  6                                    3
    //             下    边
    //
    // 基本逻辑
    // 1. 上边扫描范围：行索引始终为 row_start，列索引遍历 [col_start, col_stop]
    // 2. 右边扫描范围：列索引始终为 col_stop，行索引遍历 [row_start + 1, row_stop]
    // 3. 下边扫描范围：行索引始终为 row_stop，列索引遍历 [col_stop - 1, col_start + 1]
    // 4. 左边扫描范围：列索引始终为 col_start，行索引遍历 [row_stop, row_start + 1]
    // 需要注意
    // 1. 在完成向右向下扫描之后，需要判断是否剩下的部分还能构成一个矩阵，否则相当于重复扫描同一区域了
    // 2. 在每条边扫描的时候，指针的起始和终止位置设置比较考验细节。
    static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix.length == 0) return res;
        int row_start = 0;
        int row_stop = matrix.length - 1;
        int col_start = 0;
        int col_stop = matrix[0].length - 1;
        while (row_start <= row_stop && col_start <= col_stop) {
            for (int col = col_start; col <= col_stop; col++) res.add(matrix[row_start][col]);         // 上边
            for (int row = row_start + 1; row <= row_stop; row++) res.add(matrix[row][col_stop]);      // 右边
            // 仅在区域是真的矩阵时才继续剩下两个边的扫描，对于列向量 (col_start == col_stop) 和行向量 (row_start == row_stop) 都不再扫描
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

    /** 解法2：借助于辅助矩阵对已访问元素进行标注，这样遍历写法会简单的多。 */
    // 非常聪明的解法，首先用额外的seen矩阵专门用来指示元素的访问情况
    // 然后用 dr 和 dc 的组合搭配，实现在矩阵上的上下左右移动，很有意思。
    // <运动表>
    //      dr    0      1     0    -1
    //      dc    1      0    -1     0
    // 对应行为   右移   下移   左移   上移
    // di 则是上表行为的索引，只要遇到遍历指针到头或者遇到已经访问过的节点，就移动di索引
    static List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix.length == 0) return res;
        int R = matrix.length;
        int C = matrix[0].length;
        boolean[][] seen = new boolean[R][C];
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        int di = 0;
        int r = 0, c = 0;
        for (int i = 0; i < R * C; i++) {
            res.add(matrix[r][c]);
            seen[r][c] = true;      // 标记已访问
            int cr = r + dr[di];
            int cc = c + dc[di];
            if (0 <= cr && cr < R && 0 <= cc && cc < C && !seen[cr][cc]){
                r = cr;
                c = cc;
            } else {
                di = (di + 1) % 4;  // 循环移动di的索引 0, 1, 2, 3, 0, 1, 2, 3 ...
                r += dr[di];
                c += dc[di];
            }
        }
        return res;
    }
}
