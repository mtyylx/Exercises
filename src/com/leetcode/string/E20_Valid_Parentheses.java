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

    // 使用LIFO数据结构，用Deque接口和ArrayDeque实现
    // 为什么不用Stack？原因有三点：
    // 1. 因为Deque的速度比Stack和Queue都要快，远强于java.util.Stack本身
    // 2. 因为Stack是实现而没有接口，假设你的函数返回值类型是Stack，那么使用者将会被绑定在一种具体的类实现上 (虽然这里并没有)
    // 3. 因为Deque提供了丰富的API，功能更强大
    // ArrayDeque的addFirst()和removeFirst()等同于push()和pop()，因为将ArrayDeque用作Stack的时候，栈顶就在队首。
    static boolean isValidParentheses2(String a) {
        Deque<Character> stack = new ArrayDeque<>(a.length());
        for (int i = 0; i < a.length(); i++) {
            char x = a.charAt(i);
            if (x == '(' || x == '[' || x == '{') stack.push(x);
            else if (stack.size() == 0) return false;
            else if (x == ')' && stack.pop() != '(') return false;
            else if (x == ']' && stack.pop() != '[') return false;
            else if (x == '}' && stack.pop() != '{') return false;
        }
        return stack.size() == 0;
    }

    /** 这道题似乎不能使用哈希表来做，因为哈希表不能正确判断“([)]”这种情形*/
    // 哈希表解法，time - o(n), space - o(1)
    // 分析一下到底不合法的括号具备什么样的特性：
    // 0. 字符串长度是奇数，一定不合法
    // 1. 当前字符是右半边，但是哈希表中并没有左半边存储
    // 2. 扫描完之后，哈希表中没有清空，还有没有匹配的左半边括号
    static boolean isValidParentheses(String a) {
        if (a == null || a.length() == 0) return true;
        if (a.length() % 2 != 0) return false;

        Map<Character, Integer> map = new HashMap<>(6);
//        for (int i = 0; i < a.length(); i++) {
//            char x = a.charAt(i);
//            if (x == '(' || x == '[' || x == '{') {
//                if (map.containsKey(x)) map.put(x, map.get(x) + 1);
//                else map.put(x, 1);
//            }
//            else if (x == ')' && !map.containsKey('(') ||
//                     x == ']' && !map.containsKey('[') ||
//                     x == '}' && !map.containsKey('{'))
//                return false;
//        }
        return false;
    }
}
