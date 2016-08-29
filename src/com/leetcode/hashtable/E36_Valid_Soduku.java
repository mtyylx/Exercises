package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/8/28.
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 * Rule #1. Each row must have the numbers 1-9 occurring just once.
 * Rule #2. Each column must have the numbers 1-9 occurring just once.
 * Rule #3. Each cube (3*3) must have the numbers 1-9 occurring just once.
 *
 * Note:
 * A valid Sudoku board (partially filled) is not necessarily solvable.
 * Only the filled cells need to be validated.
 *
 * Function Signature:
 * public boolean validSoduku(char[][] board) {...}
 */
public class E36_Valid_Soduku {
    public static void main(String[] args) {
        char[][] matrix = new char[9][9];
        matrix[0] = new char[]{'1', '2', '.', '.', '.', '.', '.', '.', '.'};
        matrix[1] = new char[]{'.', '.', '9', '.', '.', '.', '.', '7', '.'};
        matrix[2] = new char[]{'8', '.', '.', '.', '.', '5', '.', '.', '.'};
        matrix[3] = new char[]{'.', '.', '1', '.', '.', '.', '.', '.', '.'};
        matrix[4] = new char[]{'.', '.', '.', '7', '.', '.', '2', '.', '.'};
        matrix[5] = new char[]{'.', '.', '.', '.', '.', '.', '4', '3', '.'};
        matrix[6] = new char[]{'7', '.', '8', '.', '.', '.', '.', '.', '.'};
        matrix[7] = new char[]{'.', '.', '5', '.', '6', '.', '.', '2', '.'};
        matrix[8] = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.'};

        System.out.println(validSoduku(matrix));
    }

    // o(n^2)
    // 同样也是哈希的解法，但是把功能解耦，看上去简单多了
    // 将问题提炼为：给定一个二维数组的某个区域范围（可以是正方形也可以是长方形区域），判断里面的元素是否重复
    // 专门写一个处理通用二维矩阵扫描的函数
    static boolean isMatrixValid(char[][] board, int istart, int iend, int jstart, int jend) {
        Set<Character> set = new HashSet<>(9);
        for (int i = istart; i < iend; i++) {
            for (int j = jstart; j < jend; j++) {
                if (board[i][j] != '.' && !set.add(board[i][j])) return false;
            }
        }
        return true;
    }

    static boolean validSoduku2(char[][] board) {
        // check Rule #1 and Rule #2
        for (int i = 0; i < board.length; i++) {
            if (!isMatrixValid(board, i, i, 0, 9)) return false;    // 数独表的格式是固定的，一定是9*9的尺寸（针对1-9）
            if (!isMatrixValid(board, 0, 9, i, i)) return false;
            int x = (i % 3) * 3;
            int y = (i / 3) * 3;
            // 关键在于首的索引如何定，只要首定了，加3就是尾
            // 找规律，发现余数和商之间的关系符合这个排列组合的特性
            // i = 0, 1, 2
            // 起始位置 >> 起位置 >> 起位置除3 >> 可以看到，左侧这列表现的是i除以3之后的余数，右侧列则是i除以3之后的商
            // 0, 3, 0, 3 >> 0, 0 >> 0, 0
            // 3, 6, 0, 3 >> 3, 0 >> 1, 0
            // 6, 9, 0, 3 >> 6, 0 >> 2, 0

            // 0, 3, 3, 6 >> 0, 3 >> 0, 1
            // 3, 6, 3, 6 >> 3, 3 >> 1, 1
            // 6, 9, 3, 6 >> 6, 3 >> 2, 1

            // 0, 3, 6, 9 >> 0, 6 >> 0, 2
            // 3, 6, 6, 9 >> 3, 6 >> 1, 2
            // 6, 9, 6, 9 >> 6, 6 >> 2, 2
            if (!isMatrixValid(board, x, x + 3, y, y + 3)) return false;
        }

        // 可以简化的部分
//        for (int i = 0; i < board.length / 3; i++) {
//            for (int j = 0; j < board[0].length / 3; j++) {
//                if (!isMatrixValid(board, 3*i + 0, 3*i + 2, 3*j + 0, 3*j + 2)) return false;
//            }
//        }

        return true;
    }

    // 哈希表解法
    // 难点主要是如何达成Rule#3，即对二维数组进行九宫格内的元素扫描
    static boolean validSoduku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            Set<Character> row = new HashSet<>(9);
            Set<Character> col = new HashSet<>(9);
            Set<Character> cube = new HashSet<>(9);
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.' && !row.add(board[i][j])) return false;
                if (board[j][i] != '.' && !col.add(board[j][i])) return false;
                int x = (i / 3) * 3 + j / 3;
                int y = (i % 3) * 3 + j % 3;
                if(board[x][y] != '.' && !cube.add(board[x][y])) return false;
            }
        }
        return true;
    }
}
