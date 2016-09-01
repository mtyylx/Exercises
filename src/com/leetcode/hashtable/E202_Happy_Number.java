package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/1.
 * Write an algorithm to determine if a number is "happy".
 * A happy number is a number defined by the following process: Starting with any positive integer,
 * replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay),
 * or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy numbers.
 *
 * Example: 19 is a happy number
 *
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 *
 * Function Signature:
 * public boolean isHappy(int a) {...}
 *
 * */
public class E202_Happy_Number {
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            if (isHappy3(i)) System.out.println(i);
        }
    }

    // 法3：使用Floyd循环检测算法 - 快慢指针
    // 相比于哈希表的解法，这种算法空间复杂度是o(1).
    // 只要出现了重复元素就退出计算，并检查是不是1
    static boolean isHappy3(int a) {
        int slow = a;
        int fast = a;
        while (true) {
            slow = calculate(slow);
            fast = calculate(fast);
            fast = calculate(fast);
            if (slow == fast) break;
        }
        return slow == 1;
    }

    static int calculate(int a) {
        int temp = 0;
        while (a > 0) {
            temp += (a % 10) * (a % 10);
            a /= 10;
        }
        return temp;
    }

    // 法2：哈希表解法，只要出现重复元素，且不是1，那么一定是死循环，退出。
    static boolean isHappy2(int a) {
        Set<Integer> set = new HashSet<>();
        while (set.add(a)) {
            int temp = 0;
            while (a > 0) {
                temp += (a % 10) * (a % 10);
                a /= 10;
            }
            a = temp;
        }

        return a == 1;
    }

    // 1 - 1
    // 2 - <4> - 16 - 37 - 58 - 89 - 145 - 42 - 20 - 4
    // 3 - 9 - 81 - 65 - 61 - <37> - 58 - 89 - 145 - 42 - 20 - 4
    // <4> - 16 - 37 - 58 - 89 - 145 - 42 - 20 - 4
    // 5 - 25 - 39 - 85 - <89> - 145 - 42 - 20 - 4
    // 6 - 36 - 45 - 41 - 17 - 50 - <25> - 39 - 85 - <89> - 145 - 42 - 20 - 4
    // 7 - 49 - 97 - 130 - 10 - 1
    // 8 - 64 - 52 - 29 - <85> - <89> - 145 - 42 - 20 - 4
    // 9 - 81 - 65 - 61 - <37> - 58 - 89 - 145 - 42 - 20 - 4
    // 10 - 1
    // 11 - 2
    // 12 - 5
    // 13 - 10
    // 14 - 17
    // 15 - 26 - 20 - 4
    // 16
    // 17

    // 法1：找数学规律解法
    // 只有两种走向：一种是循环最后到1，一种是无限变化循环。
    // 所以解题的关键在于找到能够停止循环的特征。
    // 如果循环中出现了什么值，就一定不是Happy Number，作为停止循环的条件。
    // 所以通过计算1到9的规律，发现出现无限循环的序列中都有4.
    static boolean isHappy(int a) {
        int temp = 0;
        while (true) {
            while (a > 0) {
                temp += (a % 10) * (a % 10);
                a /= 10;
            }
            if (temp == 1) return true;
            if (temp == 4) return false;
            a = temp;
            temp = 0;
        }
    }
}
