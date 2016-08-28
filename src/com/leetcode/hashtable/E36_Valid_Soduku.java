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

    }

    static boolean validSoduku(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            Set<Character> row = new HashSet<>(10);
            Set<Character> col = new HashSet<>(10);
            Set<Character> cube = new HashSet<>(10);
            for (int j = 0; j < board[i].length; j++) {
                if (!row.add(board[i][j])) return false;
                if (!col.add(board[j][i])) return false;
            }
        }
        return true;
    }
}
