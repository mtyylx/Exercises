package com.leetcode.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/9/18.
 * You are playing the following Flip Game with your friend:
 * Given a string that contains only these two characters: + and -,
 * you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.
 * Write a function to compute all possible states of the string after one valid move.
 *
 * For example, given s = "++++", after one move, it may become one of the following states:
 * [
 * "--++",
 * "+--+",
 * "++--"
 * ]
 * If there is no valid move, return an empty list [].
 *
 * Function Signature:
 * public List<String> flip(String a) {...}
 * */
public class E293_Flip_Game {
    public static void main(String[] args) {
        String a = "+++-+++";
        List<String> result = flip2(a);
    }

    // 转换为数组的解法
    // 只新建一个char array，每次flip完存入result后再复原。
    static List<String> flip2(String s) {
        List<String> result = new ArrayList<>();
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length - 1; i++) {
            if (c[i] == '+' && c[i + 1] == '+') {
                c[i] = '+';
                c[i + 1] = '+';
                result.add(new String(c));
                c[i] = '-';
                c[i + 1] = '-';
            }
        }
        return result;
    }

    // 每次都新建一个新的char Array然后更改，缺点是空间复杂度高
    static List<String> flip(String s) {
        if (s == null || s.length() < 2) return new ArrayList<>();
        int l = s.length();
        List<String> result = new ArrayList<>();
        for (int i = 1; i < l; i++) {
            char[] c = s.toCharArray();
            if (c[i - 1] == '+' && c[i] == '+') {
                c[i - 1] = '-';
                c[i] = '-';
                result.add(new String(c));
            }
        }
        return result;
    }
}
