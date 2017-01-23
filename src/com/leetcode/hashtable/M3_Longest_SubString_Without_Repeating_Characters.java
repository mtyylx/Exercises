package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/9/5.
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Examples:
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * Given "bbbbb", the answer is "b", with the length of 1.
 * Given "pwwkew", the answer is "wke", with the length of 3.
 *
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Function Signature:
 * public int substring(String a) {...}
 *
 * <Tags>
 * - Two Pointers: 快慢指针同向扫描 [ slow → → → ... fast → → → ... ]
 * - HashMap: Key → 字符，Value → 出现索引位置
 * - 字符串扫描：关键是增量扫描，而不是每次都重起炉灶。
 *
 */
public class M3_Longest_SubString_Without_Repeating_Characters {
    public static void main(String[] args) {
        System.out.println(substring3("abbxyza"));
        System.out.println(substring2("abbxyza"));
        System.out.println(substring("abbxyza"));
    }

    /** 解法1：HashMap + Two Pointers (快慢指针)。Time - o(n), Space - o(n). */
    // 慢指针指向目标子字符串起始位置，快指针指向子字符串的终止位置，fast - slow + 1就是子字符串的长度。
    // 指针位置移动的关键逻辑在于：慢指针只有在快指针遇到重复字符且这个字符最近出现位置比慢指针还靠右时才更新，否则只移动慢指针。
    // 为什么采用这个逻辑：
    // 示例1："abcbd" slow = 0, fast = 3 时，已经出现了重复元素，因此当前的字符串不再符合要求，
    // 此时，为了继续向右扫描并寻找符合要求的子字符串，我们只能放弃掉第一个"b"，放弃的方式就是把slow指向第一个"b"的下一个字符，即slow = 2, fast = 3
    // 同时还要更新最后一次出现"b"的位置，以确保HashMap记录的是字符最近出现的位置
    // 否则对于"abcbzb"，如果不更新"b"的位置，那么遇到第三个"b"的时候slow跳跃到的位置还是第一个"b"
    // 示例2："abbxyza"，虽然出现了第二个a，但是由于已经先遇到了第二个b，因此slow的位置已经比第一个a的位置更靠右了，因此无需更新slow的位置。
    static int substring3(String s) {
        int slow = 0;
        int max = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int fast = 0; fast < s.length(); fast++) {
            char c = s.charAt(fast);
            if (map.containsKey(c) && map.get(c) + 1 > slow) {
                slow = map.get(c) + 1;          // 慢指针更新至重复元素的下一个位置开始
                map.put(c, fast);               // 记得更新每个字符最近的出现位置（而不是第一次出现位置）
                continue;
            }
            else map.put(c, fast);
            max = Math.max(max, fast - slow + 1);
        }
        return max;
    }

    // 解法1的简化写法
    static int substring2(String s) {
        if (s == null || s.length() == 0) return 0;
        int max = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int slow = 0, fast = 0; fast < s.length(); fast++){
            if (map.containsKey(s.charAt(fast)))                    // 只要快指针遇到重复元素，就准备更新慢指针
                slow = Math.max(slow, map.get(s.charAt(fast)) + 1); // 如果慢指针更靠右就不更新，如果重复元素的位置更靠右就更新
            map.put(s.charAt(fast), fast);                          // 更新元素为最近出现位置
            max = Math.max(max, fast - slow + 1);                   // 求最大值
        }
        return max;
    }

    // 最初解法，没有利用增量，而是每次都重起炉灶，因此复杂度高。
    // 这个解法思路其实和上面是相似的，但是区别在于遇到重复元素以后怎么处理。
    // 这里选择把整个哈希表都清空，然后指针位置从上次出现重复元素的位置开始，重新扫描添加，所以会效率很低。
    static int substring(String s) {
        if (s == null || s.length() == 0) return 0;
        int i = 0;
        int j = i + 1;
        int result = 1;
        Map<Character, Integer> map = new HashMap<>();
        map.put(s.charAt(0), 0);
        while (j < s.length()) {
            if (!map.containsKey(s.charAt(j))) {
                map.put(s.charAt(j), j);
                j++;
                result = Math.max(result, j - i);
            }
            else {
                result = Math.max(result, j - i);
                i = map.get(s.charAt(j)) + 1;
                j = i + 1;
                map.clear();
                map.put(s.charAt(i), i);
            }
        }
        return result;
    }
}
