package com.leetcode.string;

/**
 * Created by Michael on 2016/9/22.
 * Compare two version numbers version1 and version2.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 * You may assume that the version strings are non-empty and contain only digits and the . character.
 * The . character does not represent a decimal point and is used to separate number sequences.
 * For instance, 2.5 is not "two and a half" or "half way to version three",
 * it is the fifth second-level revision of the second first-level revision.
 *
 * For Example:
 * 0.1 < 1.1 < 1.2 < 13.37
 *
 * Function Signature:
 * public int compareVersion(String ver1, String ver2) {...}
 * */
public class E165_Compare_Version_Number {
    public static void main(String[] args) {
        String a = "2147483648.543245.343001";
        String b = "122345.543245.3224525";
        String[] x = a.split("\\.");
        int z = Integer.parseInt(x[0]);
        System.out.println(compareVersion(a, b));
    }

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
}
