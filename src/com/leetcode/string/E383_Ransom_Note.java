package com.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/9/21.
 *  Given  an  arbitrary  ransom  note  string  and  another  string  containing  letters from  all  the  magazines, 
 * write  a  function  that  will  return  true  if  the  ransom  note  can  be  constructed  from  the  magazines;  
 * otherwise,  it  will  return  false.   
 * Each  letter  in  the  magazine  string  can  only  be  used  once  in  your  ransom  note.
 *
 * Note: You may assume that both strings contain only lowercase letters.
 * canConstruct("a", "b") -> false
 * canConstruct("aa", "ab") -> false
 * canConstruct("aa", "aab") -> true
 *
 * Function Signature:
 * public boolean canConstruct(String ransomNote, String magazine) {...}
 *
 * */
public class E383_Ransom_Note {
    public static void main(String[] args) {
        String ransom = "fucccckingbastardx";
        String magazine = "ccccufbaastrdingkjlksdfja";
        System.out.println(canConstruct(ransom, magazine));
    }

    // 哈希表解法，其实完全应该归类在hash下面
    // 只要确保ransomNote里面有的每一个字符在magazine中都有对应的即可，所以还是先++统计magazine再--检查ransomNote
    static boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (char x : magazine.toCharArray()) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
        }
        for (char y : ransomNote.toCharArray()) {
            if (map.containsKey(y)) map.put(y, map.get(y) - 1);
            else return false;
            if (map.get(y) < 0) return false;
        }
        return true;
    }
}
