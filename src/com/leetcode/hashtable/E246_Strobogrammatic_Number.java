package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/8/31.
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Write a function to determine if a number is strobogrammatic. The number is represented as a string.
 *
 * For example,
 * the numbers "69", "88", and "818" are all strobogrammatic.
 *
 * Function Signature:
 * public boolean isStrobogrammatic(String a) {...}
 * */
public class E246_Strobogrammatic_Number {
    public static void main(String[] args) {
        System.out.println(isStrobogrammatic("168906891"));
    }

    // 哈希表 + 双指针, o(n)
    // 因为本质上这就是一个一对一的映射关系: 0 - 0, 1 - 1, 8 - 8, 6 - 9, 9 - 6
    // 所以使用一个哈希表存储映射关系，再用双指针首尾扫描结合键值对来比对是否镜像
    // 当然如果直接比对具体的值也可以，本质上是一样的。
    static boolean isStrobogrammatic(String a) {
        Map<Character, Character> map = new HashMap<>();
        map.put('6', '9');
        map.put('9', '6');
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');
        int i = 0;
        int j = a.length() - 1;
        while (i <= j) {
            if (!map.containsKey(a.charAt(i)) || map.get(a.charAt(i)) != a.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
