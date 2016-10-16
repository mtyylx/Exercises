package com.leetcode.math;

/**
 * Created by Michael on 2016/10/16.
 * Related to question Excel Sheet Column Title
 * Given a column title as appear in an Excel sheet, return its corresponding column number.
 *
 * For example:
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 *
 * Function Signature:
 * public int columnToNumber(String str) {...}
 */
public class E171_Excel_Column_Number {
    public static void main(String[] args) {
        System.out.println(columnToNumber2("AB"));
    }

    /** 进制转换：乘26叠加法 */
    // 需要补偿1，因为这里的26进制没有0。
    static int columnToNumber(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum = sum * 26 + (str.charAt(i) - 'A' + 1);
        }
        return sum;
    }

    /** 递归解法 One-liner */
    // 递归终止条件：字符串长度为0
    // 每次取当前字符串最后一位，然后把字符串砍掉最后一位继续递归，正序递归。
    // 不过一行的代码真的调试起来很不方便，调试的最大好处就是你能看到运算过程，结果one-liner终结了这个最大的好处。
    // 因为你看不到过程，很多事情一行里都做了。所以说，实际使用中，没事别老试图一行写完。
    static int columnToNumber2(String str) {
        return str.length() < 1 ? 0 : columnToNumber2(str.substring(0, str.length() - 1)) * 26 + str.charAt(str.length() - 1) - 'A' + 1;
    }
}
