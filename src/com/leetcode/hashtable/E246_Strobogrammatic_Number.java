package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/8/31.
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Write a function to determine if a number is strobogrammatic. The number is represented as a string.
 * Strobogrammatic并非轴对称，而是中心旋转对称。
 *
 * For example,
 * the numbers "69", "88", and "818" are all strobogrammatic.
 *
 * Function Signature:
 * public boolean isStrobogrammatic(String a) {...}
 *
 * <Tags>
 * - HashMap: 键值对
 * - Two Pointers: 左右指针首尾包围扫描。[left → → → ... ← ← ← right]
 *
 */
public class E246_Strobogrammatic_Number {
    public static void main(String[] args) {
        System.out.println(isStrobogrammatic("168906891"));
        System.out.println(isStrobogrammatic2("168906891"));
    }

    /** 解法2：双指针 + Switch语句，没有那么精炼，但也不需要HashMap了。Time - o(n), Space - o(1). */
    static boolean isStrobogrammatic2(String a) {
        int i = 0;
        int j = a.length() - 1;
        while (i <= j) {
            switch (a.charAt(i)) {
                case '0':                                        // 对于 0, 1, 8，采用同样的策略，不写break，直接fall through。
                case '1':
                case '8':
                    if (a.charAt(i) != a.charAt(j)) return false;
                    break;
                case '6':                                        // 6和9的情况需要取反
                    if (a.charAt(j) != '9') return false;
                    break;
                case '9':
                    if (a.charAt(j) != '6') return false;
                    break;
                default:                                         // 其他数字一律返回false
                    return false;
            }
            i++;                                                 // 记得移动指针
            j--;
        }
        return true;
    }

    /** 解法1：双指针 + HashMap。Time - o(n), Space - o(1). */
    // 只有1，6，8，9，0符合中心旋转特性：0 - 0, 1 - 1, 8 - 8, 6 - 9, 9 - 6
    // 构造哈希表存储映射关系，再用双指针首尾扫描比对是否中心对称
    static boolean isStrobogrammatic(String a) {
        Map<Character, Character> map = new HashMap<>();
        map.put('6', '9');
        map.put('9', '6');
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');
        int i = 0;
        int j = a.length() - 1;
        while (i <= j) {            // i和j需要重合再退出，否则对于只有一个字符的字符串会不扫描。
            if (!map.containsKey(a.charAt(i)) || map.get(a.charAt(i)) != a.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
