package com.leetcode.math;

/**
 * Created by Michael on 2016/10/15.
 * Determine whether an integer is a palindrome.
 * Do this without extra space.
 *
 * Function Signature:
 * public boolean isPalindromeNumber(int a) {...}
 */
public class E9_Palindrome_Number {
    public static void main(String[] args) {
        System.out.println(isPalindromeNumber0(1234321));
    }

    /** 数值反转解法，经典 */
    // 关键点1：避免溢出。循环条件设为org > rev，相当于逆序值只构造到一半就可以判断了，这样可以有效的避免完全生成逆序数值潜在的溢出风险。
    // 关键点2：构造逆序数值，对一般的数值每一位分解法进行发扬光大，将提取的最低位作为构造逆序数值的基数。
    // 一般的数值位分解是digit = x % 10; x /= 10;
    // 而这里则是直接把这个digit同时构造回去。
    // 12321
    // rev = 0, org = 12321
    // rev = 1, org = 1232
    // rev = 12, org = 123
    // rev = 123, org = 12 -> rev / 10 == org
    // 1991
    // rev = 0, org = 1991
    // rev = 1, org = 199
    // rev = 19, org = 19 -> rev == org
    // 关键点3：上面的规律有不符合的特例，就是结尾为0的数值
    // 100
    // rev = 0, org = 100
    // rev = 0, org = 10
    // rev = 0, org = 1
    // rev = 1, org = 0 -> rev / 10 == org 但并不是回文
    // 因此需要专门把这种情况筛选掉，反正结尾是0的数值除了0本身意外一定不可能是回文。
    static boolean isPalindromeNumber3(int a) {
        if (a < 0) return false;
        if (a != 0 && a % 10 == 0) return false;
        int org = a;
        int rev = 0;
        while (org > rev) {
            rev = rev * 10 + org % 10;  // 取原数值当前最后一位，同时累加构造逆序数值。
            org /= 10;
        }
        return rev == org || rev / 10 == org;
    }

    /** 纯粹提取每一位对比 */
    // 问题是需要首先扫描一遍确定该整型数据的长度。
    static boolean isPalindromeNumber2(int a) {
        if (a < 0) return false;
        // Get width
        int temp = a;
        int width = 0;
        while (temp > 0) {
            temp /= 10;
            width++;
        }
        int left = 0, right = 0;
        int k = (int) Math.pow(10, width - 1);
        while (a > 0) {
            left = a / k;
            right = a % 10;
            if (left != right) return false;
            a = a - right;
            a = a - left * k;
            a /= 10;
            k /= 100;
        }
        return true;
    }


    /** 转化为字符串问题 */
    // Time - o(n), Space - o(n) 只要转化为字符串解决就一定需要额外空间。
    // 易疏忽点1：负数不会是回文，因为有负号。这个要考虑到。
    static boolean isPalindromeNumber(int a) {
        if (a < 0) return false;
        StringBuilder sb = new StringBuilder();
        while (a > 0) {
            sb.append(a % 10);
            a /= 10;
        }
        return sb.toString().equals(sb.reverse().toString());
    }

    /** 转化为字符串解法，利用String.valueOf这个API直接获取数字对应的字符串 */
    static boolean isPalindromeNumber0(int a) {
        char[] str = String.valueOf(a).toCharArray();
        for (int i = 0; i < str.length / 2; i++)
            if (str[i] != str[str.length - i - 1]) return false;
        return true;
    }
}
