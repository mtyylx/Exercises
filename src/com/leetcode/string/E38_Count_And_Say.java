package com.leetcode.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 *  2.     <11>
 *  3.     <21>
 *  4.     <12><11>
 *  5.     <11><12><21>
 *  6.     <31><22><11>
 *  7.     <13><11><22><21>
 *  8.     <11><13><21><32><11>
 *  9.     <31><13><12><11><13><12><21>
 *  10.    <13><21><13><11><12><31><13><11><22><11>
 *
 * Function Signature:
 * public String generate(int x) {...}
 */
public class E38_Count_And_Say {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            System.out.println(generate(i));
        }
    }

    // 并没有什么优美的解法，
    // 只能够用最简单的办法，顺序生成新的序列，循环往复
    // 这种看上去没什么规律的东西，用程序生成却很简单，
    // 逻辑就是比较当前字符是否与之前的一样，一样的话就计数加一，不一样就重新计数，每次重新计数前都记录在StringBuilder里面
    static String generate(int x) {
        String seed = "1";
        // 外循环负责顺序生成第i个序列
        for (int j = 1; j < x; j++) {
            StringBuilder sb = new StringBuilder();
            char c = seed.charAt(0);
            int count = 1;
            // 内循环负责扫描当前序列并生成下一个序列
            for (int i = 1; i < seed.length(); i++){
                if (seed.charAt(i) == c) count++;
                else {
                    sb.append(count);
                    sb.append(c);
                    c = seed.charAt(i);
                    count = 1;
                }
            }
            sb.append(count);
            sb.append(c);
            seed = sb.toString();
        }
        return seed;
    }
}
