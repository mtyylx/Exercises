package com.leetcode.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by LYuan on 2016/10/24.
 * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
 *
 * Examples:
 * s = "leetcode", return 0.
 * s = "loveleetcode", return 2.
 *
 * Note: You may assume the string contain only lowercase letters.
 *
 * Function Signature:
 * public int firstUniqueChar(String str) {...}
 */
public class E387_First_Unique_Char_In_String {
    public static void main(String[] args) {
        System.out.println(firstUniqueChar3("aabbc"));
    }

    // 做到一次扫描的难点（而双指针可以化腐朽为神奇，特别适合解决“找第一个XXX”这种类型的问题）
    // 1. 要求是按顺序的，必须返回第一个独特的字母
    // 2. 如何处理那些多次出现重复的字母

    /** Two Pointer 一次扫描解法 */
    // time - o(n) 只需要一次扫描，比下面的两种解法性能稍好
    // 其实双指针有和队列相似的性质，可以在快指针发现特性的时候，慢指针再向前移动（相当于之前指向的元素出队了）
    // fast负责在扫描的过程中更新每个字符出现的次数表
    // 当fast所指字符刚好是slow所指字符时，说明当前slow所指字符不再是unique的，
    // 因此这时候需要移动slow，边移动边查询map中当前字符是否是unique的，直至slow超过fast，或者slow找到了一个unique的字符。
    // 易疏忽点：需要检查slow是否已经越界，越界说明一定没有unique字符。
    static int firstUniqueChar3(String s) {
        int slow = 0;
        int fast = 0;
        int[] map = new int[128];
        for (fast = 0; fast < s.length(); fast++) {
            map[s.charAt(fast)]++;
            if (s.charAt(fast) == s.charAt(slow)) {
                while (slow <= fast && map[s.charAt(slow)] > 1) slow++;
                if (slow == s.length()) return -1;
            }
        }
        return s.length() < 1 ? -1 : slow;
    }

    /** Value-As-Index 同样是两次扫描解法：因为提示了可以只考虑小写的26个字母 */
    // time - o(2n)
    static int firstUniqueChar2(String s) {
        int[] map = new int[128];
        for (char c : s.toCharArray())
            map[c]++;
        for (int i = 0; i < s.length(); i++) {
            if (map[s.charAt(i)] == 1) return i;
        }
        return -1;
    }

    /** HashMap 两次扫描解法 */
    // time - o(2n)
    static int firstUniqueChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) map.put(c, map.get(c) + 1);
            else map.put(c, 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) return i;
        }
        return -1;
    }
}
