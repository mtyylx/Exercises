package com.leetcode.hashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 2017/1/15.
 *
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 *
 * Example 1:
 * Input: s: "cbaebabacd" p: "abc"
 * Output: [0, 6]
 *
 * Example 2:
 * Input: s: "abab" p: "ab"
 * Output: [0, 1, 2]
 *
 * Function Signature:
 * public List<Integer> findAnagram(String a, String b) {...}
 *
 * <Anagram系列问题>
 * E242 Valid Anagram: 给定字符串a和字符串b，判断a和b是否是Anagram。
 * M49  Group Anagram: 给定一个字符串数组，按Anagram分组返回。
 * E438 Find Anagram : 给定字符串a和字符串b，返回a中所有出现b的Anagram的起始位置。
 *
 * <Tags>
 * - Slide Window: 双指针扫描
 * - Two Pointers: 用于实现滑动窗
 * - Value-As-Index: 模拟HashMap统计词频和分布
 *
 */
public class E438_Find_Anagram_In_String {
    public static void main(String[] args) {
        System.out.println(findAnagram3("cbaebabacd", "abc").toString());
        System.out.println(findAnagram2("cbaebabacd", "abc").toString());
        System.out.println(findAnagram("cbaebabacd", "abc").toString());
    }

    /** 解法3：滑动窗（双指针扫描） + Value-As-Index + 计数统计。Time - o(n), Space - o(1) */
    // 在解法2的基础上修改检测Anagram的机制，省略了每次都要遍历数组以对比map和pattern数组的过程，转而直接统计出现在pattern的字符个数变化
    // 不过实际上与解法2的运算复杂度差别不大，都属于同一个数量级。
    // 这里的while循环等效与把for循环从半截开始运行的过程，指针的移动在中间发生，而不是循环起止时。
    // 这里需要区分count和随便一个字符的统计个数，count只可能大于等于0，但字符的统计个数可以为负值，负值表示该字符在target中并不存在。
    static List<Integer> findAnagram3(String a, String b) {
        List<Integer> result = new ArrayList<>();
        int[] pattern = new int[128];
        for (int i = 0; i < b.length(); i++) pattern[b.charAt(i)]++;        // 收集字符出现分布和频率
        int count = b.length();                                             // 目标的总体字符个数
        int left = 0, right = 0;                                            // 滑动窗的左右指针
        while (right < a.length()) {                                        // 使用while而不是for循环，能更灵活的控制循环
            if (pattern[a.charAt(right)] > 0) count--;          // 如果数组中的字符在pattern中存在，才让count自减
            pattern[a.charAt(right)]--;                         // 不管pattern中是否存在，都自减该字符的统计个数，但count不受影响
            right++;                                            // 移动右指针
            if (count == 0) result.add(left);                   // 如果count为0，说明匹配Anagram，需要记录当前的左指针位置
            if (right - left == b.length()) {                   // 如果当前左右指针之差已经抵达滑动窗宽度，就需要缩减
                if (pattern[a.charAt(left)] >= 0) count++;      // 如果数组中的字符在pattern中存在，才让count自增回血
                pattern[a.charAt(left)]++;                      // 不管pattern中是否存在，都自增恢复该字符个数，同时count不受影响
                left++;                                         // 移动左指针
            }
        }
        return result;
    }

    /** 解法2：滑动窗（双指针扫描） + Value-As-Index + 暴力扫描。Time - o(26n), Space - o(1) */
    // 虽然这里时间复杂度写的是“26n”，但是实际就是n，在n很大的时候可以忽略
    // 使用两个数组作为Value-As-Index用途的HashMap。窗口每移动一次，就比较一次两个数组（也可以使用一个数组，然后++/--，判断是否全0.）
    // 关键在于识别右指针是否已经抵达滑动窗的最大宽度的边界。最大宽度在这里就是目标字符串b的长度。
    // 使用Arrays.equals()方法来比较两个数组的内容是否完全匹配。
    static List<Integer> findAnagram2(String a, String b) {
        List<Integer> result = new ArrayList<>();
        int[] pattern = new int[26];
        for (char c : b.toCharArray()) pattern[c - 'a']++;          // 收集目标字符串b的字符分布和频率
        int[] map = new int[26];                                    // 源字符串a的字符分布和频率
        int start = 0;                                              // 循环外定义滑动窗的<左指针>
        for (int stop = 0; stop < a.length(); stop++) {             // 循环变量为滑动窗的<右指针>
            map[a.charAt(stop) - 'a']++;                            // 上来先添加当前字符的统计
            if (stop - start + 1 == b.length()) {                   // 如果当前元素（右指针）是饱满的滑动窗的最右侧元素，就:
                if (Arrays.equals(map, pattern)) result.add(start); // 先添加左指针位置
                map[a.charAt(start) - 'a']--;                       // 再去掉左指针元素
                start++;                                            // 再移动左指针
            }
        }
        return result;
    }


    /** 解法1：Sort + HashMap。Time - o(n * m). Space - o(n). */
    // 最初的想法，只是一味的遍历字符串a，并比较每个长度等于b的子字符串是否与b相等。
    // 算法复杂度高的原因在于相邻的扫描有很大一部分是重复的，可以使用滑动窗来增量的修改比较，而不是每次都完整比较。
    //   a = [c b a d e g f c], b = [a b c d e f g]
    // i = 0  ↑------------
    //        a b c d e f g         可以看到，i = 0 和 i = 1这两个子字符串中，
    // i = 1    ↑------------       中间部分[b a d e g f]是完全重合的，没有必要扫描。对于i = 1，只需要去掉打头的c，加上新来的c即可。
    //          a b c d e f g
    static List<Integer> findAnagram(String a, String b) {
        List<Integer> result = new ArrayList<>();
        String pattern = getPattern2(b);
        int len = a.length() - b.length();
        int plen = b.length();
        for (int i = 0; i <= len; i++) {
            if (pattern.equals(getPattern2(a.substring(i, i + plen))))
                result.add(i);
        }
        return result;
    }

    // Value-As-Index，比较出现分布和频次
    static String getPattern2(String a) {
        int[] map = new int[26];
        for (int i = 0; i < a.length(); i++)
            map[a.charAt(i) - 'a']++;
        return Arrays.toString(map);
    }
    // 先排序后比较
    static String getPattern(String a) {
        char[] c = a.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }
}
