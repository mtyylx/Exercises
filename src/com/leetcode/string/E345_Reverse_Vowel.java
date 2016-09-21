package com.leetcode.string;

/**
 * Created by Michael on 2016/9/21.
 * Write a function that takes a string as input and reverse only the vowels of a string.
 *
 * Example 1:
 * Given s = "hello", return "holle".
 * Example 2:
 * Given s = "leetcode", return "leotcede".
 *
 * Note: The vowels does not include the letter "y".
 *
 * Function Signature:
 * public String reverseVowel(String a) {...}
 */
public class E345_Reverse_Vowel {
    public static void main(String[] args) {

    }

    // 双指针 + 字符串as字典解法，
    // 继续使用双while首尾扫描特定特征的字符串并进行操作（交换/比较）
    // 需要注意的是这里没有使用HashSet，但是原理完全是HashSet的特点，好处是不用傻傻的添加10个字母进HashSet了。
    static String reverseVowels(String s) {
        if (s == null || s.length() == 0) return s;
        int i = 0;
        int j = s.length() - 1;
        String vowel = "aeiouAEIOU";
        char[] result = s.toCharArray();
        while (i < j) {
            while (i < j && vowel.indexOf(result[i]) == -1 ) i++;
            while (i < j && vowel.indexOf(result[j]) == -1 ) j--;
            if (i < j) {
                char temp = result[i];
                result[i++] = result[j];
                result[j--] = temp;
            }
        }
        return new String(result);
    }
}
