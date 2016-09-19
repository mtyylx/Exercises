package com.leetcode.string;

import java.util.*;

/**
 * Created by Michael on 2016/9/19.
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 * The brackets must close in the correct order,
 * For example, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 *
 * Function Signature:
 * public boolean isValidParentheses(String a) {...}
 */
public class E20_Valid_Parentheses {
    public static void main(String[] args) {
        String a = "([)]";
        System.out.println(isValidParentheses2(a));
    }

    // 使用LIFO数据结构，Deque实现
    static boolean isValidParentheses2(String a) {
        Deque<Character> stack = new ArrayDeque<>(a.length());
        for (int i = 0; i < a.length(); i++) {
            char x = a.charAt(i);
            if (x == '(' || x == '[' || x == '{') stack.addFirst(x);
            else if (stack.size() == 0) return false;
            else if (x == ')' && stack.removeFirst() != '(') return false;
            else if (x == ']' && stack.removeFirst() != '[') return false;
            else if (x == '}' && stack.removeFirst() != '{') return false;
        }
        return stack.size() == 0;
    }

    // 哈希表解法，time - o(n), space - o(1)
    // 分析一下到底不合法的括号具备什么样的特性：
    // 0. 字符串长度是奇数，一定不合法
    // 1. 当前字符是右半边，但是哈希表中并没有左半边存储
    // 2. 扫描完之后，哈希表中没有清空，还有没有匹配的左半边括号
    static boolean isValidParentheses(String a) {
        if (a == null || a.length() == 0) return true;
        if (a.length() % 2 != 0) return false;

        Map<Character, Integer> map = new HashMap<>(6);
        for (int i = 0; i < a.length(); i++) {
            char x = a.charAt(i);
            if (x == '(' || x == '[' || x == '{') {
                if (map.containsKey(x)) map.put(x, map.get(x) + 1);
                else map.put(x, 1);
            }
            else if (x == ')' && !map.containsKey('(') ||
                     x == ']' && !map.containsKey('[') ||
                     x == '}' && !map.containsKey('{'))
                return false;
        }
        return false;
    }
}
