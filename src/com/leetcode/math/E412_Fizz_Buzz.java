package com.leetcode.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/12/14.
 *
 * Write a program that outputs the string representation of numbers from 1 to n.
 * But for multiples of 3 it should output “Fizz” instead of the number,
 * and for the multiples of 5 output “Buzz”.
 * For numbers which are multiples of both 3 and 5 output “FizzBuzz”.
 *
 * Function Signature:
 * public List<String> fizzBuzz(int n) {...}
 *
 */
public class E412_Fizz_Buzz {
    public static void main(String[] args) {
        List<String> result = fizzBuzz(15);
        List<String> result2 = fizzBuzz2(15);
    }

    /** 解法2：避免求余运算，用两个累加变量来正向检测3和5的倍数。*/
    static List<String> fizzBuzz2(int n) {
        List<String> result = new ArrayList<>();
        int three = 3;
        int five = 5;
        for (int i = 1; i <= n; i++) {
            if (i == three && i == five) {
                result.add("FizzBuzz");
                three += 3;
                five += 5;
            }
            else if (i == three) {
                result.add("Fizz");
                three += 3;
            }
            else if (i == five) {
                result.add("Buzz");
                five += 5;
            }
            else result.add(String.valueOf(i));
        }
        return result;
    }

    /** 解法1：最简单解法，每个值都检查是否可以被5或3整除。*/
    static List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 != 0 && i % 5 != 0) result.add(String.valueOf(i));
            else if (i % 3 != 0) result.add("Buzz");
            else if (i % 5 != 0) result.add("Fizz");
            else result.add("FizzBuzz");
        }
        return result;
    }
}
