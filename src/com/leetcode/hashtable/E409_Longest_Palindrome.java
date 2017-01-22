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
 *
 * <Palindrome系列问题>
 * E9   Palindrome Number
 * E125 Palindrome String
 * E234 Palindrome LinkedList
 * E266 Palindrome Permutation
 * M267 Palindrome Permutation II
 * E409 Longest Palindrome
 *
 * <Tags>
 * - HashMap: Key → 字符，Value → 出现次数。
 * - Value-As-Index模拟HashMap统计字符频率分布。
 * - 硬币翻转加减法（Sum +/-）：特别适合处理奇偶数的相关问题。
 *
 */
public class E409_Longest_Palindrome {
    public static void main(String[] args) {
        String a = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
        System.out.println(longestPalindrome3(a));
        System.out.println(longestPalindrome2(a));
        System.out.println(longestPalindrome(a));
    }

    /** Palindrome就是镜像对称的字符串，其特征是： */
    // 奇数长度的字符串：只能有1个字符出现奇数次，其他必须全部偶数次
    // 偶数长度的字符串：所有字符都必须出现偶数次（等效于出现0次奇数次字符）

    /** 解法3：硬币翻转加减法（Sum +/-）。Time - o(n), Space - o(1). */
    // 让我们从解法1和2对奇偶分别讨论的思路中跳出来，不分开讨论奇偶，而是将问题直接转化为记录任何字符<成对出现>的次数（即奇数也都转化成偶数了）
    // 不统计总次数，而是硬币每翻过来一次（++）再翻回去一次（--）我们就直接将计数器加2，表明找到了一对。
    // 然后最后用和解法2一样的方法，判断是否需要加1补偿即可
    // 该解法的优势是可以只扫描一次就完成任务，无需使用HashMap
    static int longestPalindrome3(String s) {
        int[] map = new int[128];
        int count = 0;
        for (char c : s.toCharArray()) {
            if (map[c] == 0) map[c]++;      // 硬币翻过来一次
            else {
                map[c]--;                   // 硬币翻回去一次
                count += 2;                 // 一来一回，算两个有效字符
            }
        }
        return count < s.length() ? (count + 1) : count;
    }

    /** 解法2：Value-As-Index解法模拟HashMap。Time - o(n), Space - o(1).
     * 虽然复杂度与解法1一样，但是由于数组数据结构比哈希表要简单，因此实际性能要高于解法1不少。*/
    // 解法1的简化写法，由于字符集已经限定为52个字符，因此使用数组操作起来更为简单。速度也有显著提升。
    // 另外也省略了判断是否存在奇数次字符的布尔值，因为如果字符串有出现奇数次的字符，那么由于减一效果，最后累计的长度一定会小于字符串原本的长度
    // 只有字符串中全是偶数次字符才会等长，因此在最后返回时只要判断累加值是否等于字符串长度即可。很简洁清晰。
    static int longestPalindrome2(String s) {
        int max = 0;
        int[] map = new int[128];
        for (char c : s.toCharArray()) map[c]++;
        for (int x : map) {
            if (x % 2 == 0) max += x;
            else max += x - 1;
        }
        return max < s.length() ? (max + 1) : max;
    }

    /** 解法1：传统HashMap统计词频。Time - o(n), Space - o(1) since only 52 characters allowed. */
    // 先扫描整个字符串，获得字符次数分布，然后扫描所有key的value，累加得到总长度
    // 这道题上手简单，但容易疏忽出现奇数次的字符的计数处理。
    // 因为目的是让Palindrome尽可能的长，因此奇数次的字符并不应该完全舍弃，而是退而求其次，将奇数次的减一，转化成为偶数次即可。
    // 同时需要在扫描的过程中标记该字符串是否真的存在奇数次的字符，如果出现，那么为了追求最大，可以在最后结果上加一。
    static int longestPalindrome(String s) {
        int max = 0;
        boolean hasOdd = false;
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
        for (int x : map.values()) {        // 直接遍历HashMap的Values，无需通过Key找Value，因为这里我们只关心Value。
            if (x % 2 == 0) max += x;
            else {
                hasOdd = true;              // 标记字符串中有出现奇数次的字符，供最后使用。
                max += x - 1;
            }
        }
        return hasOdd ? (max + 1) : max;    // 根据flag决定是否加1补偿
    }


}
