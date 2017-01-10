package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/8/28.
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 * Rule #0. A Sudoku board has a <FIXED size of 9*9>.
 * Rule #1. Each row must have the numbers 1-9 occurring just once.
 * Rule #2. Each column must have the numbers 1-9 occurring just once.
 * Rule #3. Each cube (3*3) must have the numbers 1-9 occurring just once.
 *
 * Note:
 * A valid Sudoku board (partially filled) is not necessarily solvable.
 * Only the filled cells need to be validated.
 *
 * Function Signature:
 * public boolean validSudoku(char[][] board) {...}
 *
 * <Tags>
 * - HashSet: 判重 set.add() return true / false.
 * - Matrix Manipulation: Use '/' to move vertical, Use '%' to move horizontal.
 *
 */
public class E36_Valid_Sudoku {
    public static void main(String[] args) {
        char[][] matrix = new char[9][9];
        matrix[0] = "12.......".toCharArray();
        matrix[1] = "..9....7.".toCharArray();
        matrix[2] = "8....5...".toCharArray();
        matrix[3] = "..1......".toCharArray();
        matrix[4] = "...7..2..".toCharArray();
        matrix[5] = "......43.".toCharArray();
        matrix[6] = "7.8......".toCharArray();
        matrix[7] = "..5.6....".toCharArray();
        matrix[8] = "2........".toCharArray();

        char[][] matrix2 = new char[9][9];
        matrix2[0] = "12.......".toCharArray();
        matrix2[1] = "..9....7.".toCharArray();
        matrix2[2] = "8....5...".toCharArray();
        matrix2[3] = "..1......".toCharArray();
        matrix2[4] = "...7..2..".toCharArray();
        matrix2[5] = "......43.".toCharArray();
        matrix2[6] = "7.8......".toCharArray();
        matrix2[7] = "..5.6....".toCharArray();
        matrix2[8] = "5......7.".toCharArray();

        System.out.println(validSudoku(matrix));
        System.out.println(validSudoku2(matrix));
        System.out.println(validSudoku(matrix2));
        System.out.println(validSudoku2(matrix2));
    }

    /** 问题的扩展：对于N * N尺寸的矩阵，如何按照指定块大小K（N可以被K整除）进行块遍历？
      * 设外循环变量为i，负责遍历块，内循环遍历j，负责遍历每个块内的K * K个元素
      * 行索引 = K * (i / K) + (j / K)
      * 列索引 = K * (i % K) + (j % K)
      */

     /** 运算性质：除法和求余都是将原有连续序列<压缩至0to除数范围>的操作。
      * 除以K：将<连续序列>转变为<阶跃函数>，其取值范围为[0, K] 例 [0, 1, 2, 3, 4, 5, 6, 7, 8] / 3 -> [0, 0, 0, 1, 1, 1, 2, 2, 2]
      * 求模K：将<连续序列>转变为<周期函数>，其周期为K         例 [0, 1, 2, 3, 4, 5, 6, 7, 8] % 3 -> [0, 1, 2, 0, 1, 2, 0, 1, 2]
      *          *
      *         *
      *        *
      *       *
      *      *
      *     *
      *    *                 ***      *  *  *
      *   *               ***        *  *  *
      *  *             ***          *  *  *
      *    <原序列>        <除法>      <求余>
      */

    /** 简化解法：将判断是否合法的功能单独提炼出来，问题转化为给定一个二维数组的范围，检查这个区域内是否存在重复元素。 */
    // 对于行和列的检查，这个范围就是一行或一列的区域
    // 对于块的检查，这个范围就是一个正方形区域
    static boolean validSudoku2(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (!isValid(board, i, i, 0, 9)) return false;             // 对第i行扫描
            if (!isValid(board, 0, 9, i, i)) return false;            // 对第i列扫描
            int x = (i % 3) * 3;
            int y = (i / 3) * 3;
            if (!isValid(board, x, x + 3, y, y + 3)) return false;    // 对第i块进行扫描
        }
        return true;
    }

    static boolean isValid(char[][] board, int rowLeft, int rowRight, int colLeft, int colRight) {
        Set<Character> set = new HashSet<>();
        for (int i = rowLeft; i < rowRight; i++)
            for (int j = colLeft; j < colRight; j++)
                if (board[i][j] != '.' && !set.add(board[i][j])) return false;
        return true;
    }

    /** 解法1：HashSet判重。Time - o(n^2), Space - o(c). */
    // 难点在于矩阵的块扫描：如何将双For循环的取值范围[0-8]映射成一个正方形范围的索引
    /** [第一步]：找到第一个block内9个元素的行列索引映射方式：用'/'做行索引，用'%'做列索引。*/
    //       j = 0  1  2  3  4  5  6  7  8
    //           ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
    //     j/3 = 0  0  0  1  1  1  2  2  2 (缓慢上升) 用于垂直扫描，同一行的元素行号不变，不同行的元素行号递增
    //     j%3 = 0  1  2  0  1  2  0  1  2 (周期上升) 用户水平扫描，同一行的元素列号递增
    //           ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
    // j/3,j%3 = 00 01 02 10 11 12 20 21 22 -> 00 01 02 -> [block 0,0]
    //                                         10 11 12
    //                                         20 21 22
    /** [第二步]：将视角缩小，将每个block都抽象为一个点，找到这9个点（block）的索引方式：依旧用'/'做行索引，用'%'做列索引。*/
    //         i = 0  1  2  3  4  5  6  7  8
    //             ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
    // 3 * (i/3) = 0  0  0  3  3  3  6  6  6
    // 3 * (i%3) = 0  3  6  0  3  6  0  3  6
    //             ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
    //             00 03 06 30 33 36 60 63 66 -> [block 0,0] [block 0,3] [block 0,6] 数值表示对于这个block的索引需要加的偏移值
    //                                           [block 3,0] [block 3,3] [block 3,6]
    //                                           [block 6,0] [block 6,3] [block 6,6]
    /** [第三步]：将前两部结合起来，得到行和列的索引值表达式 - [j/3 + 3*(i/3), j%3 + 3*(i%3)]. */
    //  [block 0,0]  [block 0,3]   [block 0,6]
    // |  00 01 02  |  03 04 05  |  06 07 08  |
    // |  10 11 12  |  13 14 15  |  16 17 18  |
    // |  20 21 22  |  23 24 25  |  26 27 28  |
    //  [block 3,0]  [block 3,3]   [block 3,6]
    // |  30 31 32  |  33 34 35  |  36 37 38  |
    // |  40 41 42  |  43 44 45  |  46 47 48  |
    // |  50 51 52  |  53 54 55  |  56 57 58  |
    //  [block 6,0]  [block 6,3]   [block 6,6]
    // |  60 61 62  |  63 64 65  |  66 67 68  |
    // |  70 71 72  |  73 74 75  |  76 77 78  |
    // |  80 81 82  |  83 84 85  |  86 87 88  |
    // 总之，使用双循环分别遍历block和block内的元素即可。
    static boolean validSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {                                               // i负责遍历9个block
            Set<Character> row = new HashSet<>();
            Set<Character> col = new HashSet<>();
            Set<Character> cube = new HashSet<>();
            for (int j = 0; j < 9; j++) {                                           // j负责遍历1个block内的9的元素
                if (board[i][j] != '.' && !row.add(board[i][j])) return false;      // 检查每一行是否无重复元素
                if (board[j][i] != '.' && !col.add(board[j][i])) return false;      // 检查每一列是否无重复元素
                int x = (i / 3) * 3 + j / 3;
                int y = (i % 3) * 3 + j % 3;
                if(board[x][y] != '.' && !cube.add(board[x][y])) return false;      // 检查每个block内是否无重复元素
            }
        }
        return true;
    }
}
