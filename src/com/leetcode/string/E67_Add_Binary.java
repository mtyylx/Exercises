package com.leetcode.string;

/**
 * Created by LYuan on 2016/9/21.
 * Given two binary strings, return their sum (also a binary string).
 *
 * For example,
 * a = "11"
 * b = "1"
 * Return "100"
 *
 * Function Signature:
 * public String addBinary(String a, String b) {...}
 * */
public class E67_Add_Binary {
    public static void main(String[] args) {
        String a = "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101";
        String b = "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011";
        System.out.println(addBinary2(a, b));
    }


    // 标准双指针解法
    // 只要两个字符串有一个没扫描完，就扫，扫描完的自动补0即可。
    // 需要小心的是在while循环结束后，要记得检查carry，如果为1则需要把进位加上。
    // 可以提高效率的地方：
    // 1. 使用append(char)而不是insert(int, char)，因为insert操作很浪费资源，每插入一次都要平移所有元素，这比reverse的开销要大的多。
    // 2. 使用StringBuilder.reverse()
    // 3. 使用Character.getNumericValue(char)直接把0-9的字符转为int，省去了a.charAt(i) - '0'的写法。
    static String addBinary2(String a, String b) {
        if (a == null) return b;
        if (b == null) return a;
        int i = a.length() - 1;
        int j = b.length() - 1;
        int x, y, sum, carry = 0;
        StringBuilder sb = new StringBuilder();
        while (i >= 0 || j >= 0) {
            x = i >= 0 ? Character.getNumericValue(a.charAt(i)) : 0;
            y = j >= 0 ? Character.getNumericValue(b.charAt(j)) : 0;
            sum = x + y + carry;
            if (sum % 2 == 0) sb.append('0');
            else sb.append('1');
            carry = sum > 1 ? 1 : 0;
            i--;
            j--;
        }
        if (carry == 1) sb.append('1');
        return sb.reverse().toString();
    }

    // 首先想到的是通过乘2叠加把字符串转换为int类型再计算
    // 但是这个方法最大的问题就是它会overflow，没法处理比较长一些的字符串。所以要谨慎使用。
    static String addBinary(String a, String b) {
        if (a == null) return b;
        if (b == null) return a;
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < a.length(); i++) sum1 = sum1 * 2 + (a.charAt(i) - '0');
        for (int i = 0; i < b.length(); i++) sum2 = sum2 * 2 + (b.charAt(i) - '0');
        int result = sum2 + sum1;
        StringBuilder sb = new StringBuilder();
        while (result > 0) {
            if (result % 2 == 1) sb.insert(0, '1');
            else sb.insert(0, '0');
            result /= 2;
        }
        return sb.toString();
    }
}
