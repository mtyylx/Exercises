package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by LYuan on 2016/11/8.
 * Given a digit string, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below.
 * 1 = ""
 * 2 = a, b, c
 * 3 = d, e, f
 * 4 = g, h, i
 * 5 = j, k, l
 * 6 = m, n, o
 * 7 = p, q, r, s
 * 8 = t, u, v
 * 9 = w, x, y, z
 * 0 = " "
 * Input: Digit string "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * Note: Although the above answer is in lexicographical order, your answer could be in any order you want.
 *
 * Function Signature:
 * public List<String> letterComb(String s) {...}
 */
public class M17_Letter_Combinations_Of_Phone_Number {
    public static void main(String[] args) {
        List<String> result = letterComb("21319");
    }

    /** 回溯法（DFS），递归方式，Top-Down，Time - o(n), Space - o(n) */
    // 由于问题本质上就是要求穷举，因此用回溯法递归的实现从左到右的扫描。
    static List<String> letterComb(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0) return result;

        // 用数组索引访问对应值，注意0只对应空格，而1对应空字符串。
        String[] map = new String[] {" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        letterComb_Recursive(s, 0, result, "", map);
        return result;
    }
    // 递归方法1：用String记录当前所在路径
    private static void letterComb_Recursive(String s, int level, List<String> result, String path, String[] map) {
        if (level == s.length()) {          // 递归终止，存储结果。
            result.add(path);
            return;
        }
        int key = s.charAt(level) - '0';
        if (key == 1)
            letterComb_Recursive(s, level + 1, result, path, map);      // 对1特殊处理，不更新path，直接继续递归下个数字。
        else
            for (char c : map[key].toCharArray())                       // 对其他数字的字符集，更新path，并递归下个数字。
                letterComb_Recursive(s, level + 1, result, path + c, map);
    }

    // 递归方法2：用StringBuilder记录当前所在路ing
    // 同样是递归解法，path的记录从String换成了StringBuilder。
    // 由于上面的解法中，递归时传入的是path + c，也就是一个新的字符串，所以递归结束返回时path的值还是原值
    // 但是对于StringBuilder来说不同，因为StringBuilder是引用传值，因此实际对象的值在递归进入时已经修改，递归结束后返回时sb的值需要恢复。
    private static void letterComb_Recursive2(String s, int level, List<String> result, StringBuilder sb, String[] map) {
        if (level == s.length()) {          // 抵达最后一个数字的后面，递归终止，存储结果。
            result.add(sb.toString());
            return;
        }
        int key = s.charAt(level) - '0';
        if (key == 1)
            letterComb_Recursive2(s, level + 1, result, sb, map);      // 对1特殊处理，不更新path，直接继续递归下个数字。
        else {
            for (char c : map[key].toCharArray()) {                    // 对其他数字的字符集，更新path，并递归下个数字。
                sb.append(c);                                          // 追加内容
                letterComb_Recursive2(s, level + 1, result, sb, map);
                sb.deleteCharAt(sb.length() - 1);                      // 递归结束回来时要恢复原sb值。
            }
        }
    }

    // 用回溯法穷举所有解。思想和DFS一样。
    // 输入的字符串的每一个字符都是树的一层。字符串的最后一个字符就是树的叶子节点所在层
    // 首先深度优先方式累加至叶子节点层的某个节点，因为已经是叶子节点，因此将这条路存入结果中，返回至父节点，再深入、再返回。



//        // Key HashMap, for fast retrieval. 其实没必要，因为直接用数组index也可以做到o(1)访问性能。
//        Map<Character, Character[]> map = new HashMap<>();
//        map.put('1', new Character[] {});
//        map.put('2', new Character[] {'a', 'b', 'c'});
//        map.put('3', new Character[] {'d', 'e', 'f'});
//        map.put('4', new Character[] {'g', 'h', 'i'});
//        map.put('5', new Character[] {'j', 'k', 'l'});
//        map.put('6', new Character[] {'m', 'n', 'o'});
//        map.put('7', new Character[] {'p', 'q', 'r', 's'});
//        map.put('8', new Character[] {'t', 'u', 'v'});
//        map.put('9', new Character[] {'w', 'x', 'y', 'z'});
//        map.put('0', new Character[] {' '});
}
