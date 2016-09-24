package com.leetcode.string;

/**
 * Created by Michael on 2016/9/24.
 * The count-and-say sequence is the sequence of integers beginning as follows:
 * 1, 11, 21, 1211, 111221, ...
 *
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 * Given an integer n, generate the nth sequence.
 * Note: The sequence of integers will be represented as a string.
 *
 * 感觉这个题的逻辑很任性，并不好递推，下面是正确的递推结果：
 *  1.     1
 *  2.     11
 *  3.     21
 *  4.     1211
 *  5.     111221
 *  6.     312211
 *  7.     13112221
 *  8.     1113213211
 *  9.     31131211131221
 *  10.    13211311123113112211
 *
 * Function Signature:
 * public String generate(int x) {...}
 */
public class E38_Count_And_Say {
    public static void main(String[] args) {

    }

    static String generate(int x) {

    }
}
