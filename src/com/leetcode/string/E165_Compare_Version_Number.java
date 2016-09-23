package com.leetcode.string;

/**
 * Created by Michael on 2016/9/22.
 * Compare two version numbers version1 and version2.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 * You may assume that the version strings are non-empty and contain only digits and the . character.
 * The . character does not represent a decimal point and is used to separate number sequences.
 * For instance, 2.5 is not "two and a half" or "half way to version three",
 * it is the fifth second-level revision of the second first-level revision.
 * 需要明确的一点是，版本号并不一定只有两段（即一个点），而是理论上可以有无限多个点，所以算法需要设计成应对这种情况。
 * 另外版本号的数字是可以补很多首0的例如1.00.0001。因此只比较长度是绝对不够的。
 *
 * For Example:
 * 0.1 < 1.1 < 1.2 < 13.37
 *
 * Function Signature:
 * public int compareVersion(String ver1, String ver2) {...}
 * */
public class E165_Compare_Version_Number {
    public static void main(String[] args) {
        String a = "2147483647.543245.343001";
        String b = "122345.543245.3224525";
        String[] x = a.split("\\.");
        int z = Integer.parseInt(x[0]);
        System.out.println(compareVersion(a, b));
        System.out.println("001".compareTo("1"));
        System.out.println(stringCompare("1", "00011"));
    }

    // 技巧0：必须从高位向低位扫描比较。
    // 技巧1：使用简单的正则表达式"\\."匹配字符串中出现的所有分隔符点，之所以不用"."而用"\\."是因为dot在正则表达式中表示匹配任何字符。
    // 技巧2：使用Integer.parseInt()可以简单的完成字符串转整型的工作，而不需要自己写一位位的比对或者转化为int来计算
    // 虽然说转成整型再比较有一定溢出的风险，但是能把版本号出到2147483647的软件估计已经成精了（也许那会儿人类已经不存在了）
    static int compareVersion(String ver1, String ver2) {
        String[] str1 = ver1.split("\\.");
        String[] str2 = ver2.split("\\.");
        int i = 0;
        int a, b;
        while (i < str1.length || i < str2.length) {
            if (i < str1.length) a = Integer.parseInt(str1[i]);
            else a = 0;
            if (i < str2.length) b = Integer.parseInt(str2[i]);
            else b = 0;
            if (a > b) return 1;
            else if (a < b) return -1;
            i++;
        }
        return 0;
    }

    // 解法2：思路相同，区别在于这种解法避免使用字符串转整型的方法（因为有可能溢出），
    // 相当于是手动实现了任意两个字符串之间的逐位比较，关键是可以正确补0.
    static int compareVersion2(String ver1, String ver2) {
        String[] s1 = ver1.split("\\.");
        String[] s2 = ver2.split("\\.");
        int i = 0;
        String a, b;
        while (i < s1.length || i < s2.length) {        // 等效于for (int i = 0; i < Math.max(s1.length, s2.length); i++)
            a = b = "0";
            if (i < s1.length) a = s1[i];
            if (i < s2.length) b = s2[i];
            int result = stringCompare(a, b);
            if (result != 0) return result;
            i++;
        }
        return 0;
    }

    // 补0的思路是：让长度短的那个字符串使用offset来访问，offset之后的索引如果非法，就说明需要补零。
    // 所以扫描每个字符串的时候会有三种情况：
    // 1. 该字符串不是短字符串，因此不需要offset，直接拿到当前索引的字符：if (a.length() >= length) x = a.charAt(i)
    // 2. 该字符串是短字符串，且i - offset是合理范围，拿到偏移后的索引对应的字符：else if (i - offset >= 0) x = a.charAt(i - offset);
    // 3. 该字符串是短字符串，且i - offset不在合理范围内，说明需要补0，直接赋成'0'：else x = '0';
    // 举例1： a = "100", b = "1" 则i = 0和1的时候，b的索引一直是负的，因此赋值'0'，等效于"100"和"001"之间从高位开始一一比较。
    // 举例2： a = "2", b = "002" 则比较的是"002"和"002".
    private static int stringCompare(String a, String b) {
        int length = Math.max(a.length(), b.length());
        int offset = Math.abs(a.length() - b.length());
        char x, y;
        for (int i = 0; i < length; i++) {
            if (a.length() >= length) x = a.charAt(i);
            else if (i - offset >= 0) x = a.charAt(i - offset);
            else x = '0';
            if (b.length() >= length) y = b.charAt(i);
            else if (i - offset >= 0) y = b.charAt(i - offset);
            else y = '0';
            if      (x - y > 0) return 1;
            else if (x - y < 0) return -1;
        }
        return 0;
    }
}
