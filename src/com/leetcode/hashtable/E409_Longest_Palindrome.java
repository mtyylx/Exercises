package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/10/8.
 *
 * Given a string which consists of lowercase or uppercase letters,
 * find the length of the longest palindromes that can be built with those letters.
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 *
 * Note: Assume the length of given string will not exceed 1,010.
 *
 * Example:
 * Input: "abccccdd"
 * Output: 7
 *
 * Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.
 *
 * Function Signature:
 * public int longestPalindrome(String s) {...}
 */
public class E409_Longest_Palindrome {
    public static void main(String[] args) {
        String a = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
        System.out.println(longestPalindrome2(a));
    }

    // 翻硬币解法 (+/-), 优点是可以只扫描一次就完成任务。
    // 由于这道题我们只关心字符出现的个数，而不关心是什么字符，因此可以使用翻硬币的解法，不使用HashMap就完成任务，
    // ASCII码表中大写字母在小写字母之前，且中间还夹杂着标点符号，这点不要疏忽。
    static int longestPalindrome2(String s) {
        int[] map = new int[60];
        int count = 0;
        for (char c : s.toCharArray()) {
            int i = c - 'A';
            if (map[i] == 0) map[i]++;
            else {
                map[i]--;
                count += 2;
            }
        }

        // 不用再扫描map了，只要count没有等于整个字符串的长度，就说明有的字符还处在奇数状态，所以直接加1即可，因为不关心是哪个字符是奇数。
        return count < s.length() ? (count + 1) : count;
    }

    // 哈希表解法，time - o(n), space - o(1) 因为最多只有26*2个字母
    // 这道题特别容易疏忽的地方在于：因为目的是达到尽可能长的回文，因此对于出现了奇数次的字符而言，并不应该完全舍弃，而是退而求其次，把出现的奇数次减一即可。
    // 然后最后检查下是否出现了奇数次的字符，如果有的话，回文最大可以允许一个奇数次的字符，因此也就是把原有减一的奇数补偿上一即可。
    static int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char x : s.toCharArray()) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }

        int odd = 0;
        int even = 0;
        for (char x : map.keySet()) {
            int count = map.get(x);
            if (count % 2 == 0) even += count;
            else {
                odd = 1;
                even += count - 1;
            }
        }
        return even + odd;
    }
}
