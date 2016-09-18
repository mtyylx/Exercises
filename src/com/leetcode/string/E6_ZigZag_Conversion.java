package com.leetcode.string;

/**
 * Created by LYuan on 2016/9/18.
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
 * (you may want to display this pattern in a fixed font for better legibility)

 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 * Write the code that will take a string and make this conversion given a number of rows:
 * string convert(string text, int nRows);
 * convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR".
 *
 * Function Signature:
 * public String convertZigZag(String a, int n) {...}
 */
public class E6_ZigZag_Conversion {
    public static void main(String[] args) {
        String a = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(convertZigZag2(a, 3));
    }

    // 顺序扫描字符串，存入不同的StringBuilder，再按StringBuilder顺序输出
    // 相比于第一种解法的好处是不需要找挑选每行每个元素的索引规律，更人性化些
    //　第一个for循环负责把V字形的左侧加中点存入sb，第二个for循环负责把V字形的右侧刨除两个端点存入sb，刨除的原因是为了循环不重复记录元素。
    static String convertZigZag2(String str, int n) {
        StringBuilder[] sblist = new StringBuilder[n];
        char[] a = str.toCharArray();
        for (int i = 0; i < n; i++) sblist[i] = new StringBuilder();

        int i = 0;
        while (i < a.length) {
            for (int row = 0; row < n && i < a.length; row++) sblist[row].append(a[i++]);
            for (int row = n - 2; row >= 1 && i < a.length; row--) sblist[row].append(a[i++]);
        }
        for (int x = 1; x < n; x++)
            sblist[0].append(sblist[x].toString());
        return sblist[0].toString();
    }


    // 找规律解法，
    // 一开始没有头绪，可以先把每一行的处理方法写出来，多写几行后，发现了行与行之间的规律，就知道怎么精简成为循环了。
    /** 可以把ZigZag抻长为轴对称的元素排列，可以分析出以下规律：
     *  1. 总行数为n时，第0行的周期cycle = 2n - 2
     *  2. 每0行的起点元素索引等于行号
     *  3. 每下降一行，同一个V字形中的两个元素的距离就会比第0行的cycle减小2，
     *     所以对于任意一行的V字形右侧元素就应该是V字形左边元素索引加cycle减2倍行号：i + cycle - 2 * j
     *  4. 第0行(j == 0)和最后一行(shift == 0)没有V字形右侧元素，需要特殊处理
     *
     *      0              6                   12                      18
     *        1         5     7            11      13              17
     *          2    4           8     10              14      16
     *            3                 9                      15
     */
    static String convertZigZag(String str, int n) {
        if (n == 1) return str;
        char[] a = str.toCharArray();       // 为了精简代码，避免用charAt，所以先转成数组
        int l = a.length;
        StringBuilder sb = new StringBuilder();
        int cycle = 2 * n - 2;
        for (int j = 0; j < n; j++ ) {
            for (int i = j; i < l; i += cycle) {            // 每个内循环结束后索引都会向前移动一个周期长度。
                sb.append(a[i]);
                int shift = cycle - 2 * j;                  // 计算V字形右侧元素索引相对于左侧元素的偏移量
                if (j != 0 && shift > 0 && i + shift < l)   // 在第0行或最后一行的时候避免计算右侧元素，同时避免不完整V字链导致数组越界
                    sb.append(a[i + shift]);
            }
        }
        return sb.toString();
    }
}
