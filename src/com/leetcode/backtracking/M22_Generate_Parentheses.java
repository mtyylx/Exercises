package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by LYuan on 2016/11/11.
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * For example, given n = 3, a solution set is:
 * [
 *  "((()))",
 *  "(()())",
 *  "(())()",
 *  "()(())",
 *  "()()()"
 *  ]
 *
 * Function Signature:
 * public List<String> generate(int n) {...}
 */
public class M22_Generate_Parentheses {
    public static void main(String[] args) {
        List<String> result = generate(3);
    }

    /** 回溯法，递归解法，关键在定义剪枝条件。Time - o(n), Space - o(n). */
    // 挺牛逼的，n = 14的时候就有二百多万个有效组合。n = 15的时候直接堆栈溢出了。
    //                 (
    //         /              \
    //        (                )
    //      /      \         /  \
    //     (        )       (   X
    //    / \      / \      / \
    //   X   )    (   )    (   )
    //      / \   /\  /\  / \  /\
    //     X  )  X ) ( X  X )  ( X
    //        |    | |      |  |
    //        )    ) )      )  )
    // Rule #1: left和right总量都等于n。任何一方超过n都无效。
    // Rule #2: left必须永远大于等于right。任何时刻只要right大于left就无效。
    // 分支无效则递归结束。
    static List<String> generate(int n) {
        List<String> result = new ArrayList<>();
        if (n < 1) return result;
        generate_recursive(n, 0, 0, result, "");
        return result;
    }

    static void generate_recursive(int n, int left, int right, List<String> result, String str) {
        if (right > left || left > n || right > n) return;          // Early terminate.
        if (right == n && right == left) {                          // Store result and terminate.
            result.add(str);
            return;
        }
        generate_recursive(n, left + 1, right, result, str + "(");
        generate_recursive(n, left, right + 1, result, str + ")");
    }

    /** 回溯法，更快的递归写法。在一开始给出可用资源 */
    // 这里的left和right与上面解法的含义不同，上面解法中表示已经用了多少个，这里表示还剩多少个
    // ( ( ( ) ) )
    //     |
    // ( ( ) ( ) ) i = 2 修正
    //       |
    // ( ( ) ) ( ) i = 3 修正
    //   |
    // ( ) ( ( ) ) i = 1 修正
    //       |
    // ( ) ( ) ( ) i = 3 修正
    static List<String> generate3(int n) {
        List<String> result = new ArrayList<>();
        char[] path = new char[n * 2];
        generate_recursive2(n, n, path, 0, result);    // 限定left和right分别的资源上限为n。
        return result;
    }
    // 这里使用字符数组char[]的原因是数组是mutable的，而字符串修改比较麻烦。
    static void generate_recursive2(int left, int right, char[] path, int i, List<String> result) {
        // 填满长度就存入结果并返回
        if (i == path.length) {
            result.add(new String(path));
            return;
        }
        // 如果left还有资源且right剩的多于left，填left
        if (left > 0 && right >= left) {
            path[i] = '(';
            generate_recursive2(left - 1, right, path, i + 1, result);
        }
        // 如果right有资源，填right（有可能left已经没有资源，也有可能有）
        if (right > 0) {
            path[i] = ')';
            generate_recursive2(left, right - 1, path, i + 1, result);
        }
    }


    /** 穷举遍历法，搭配HashSet去重。 */
    // 对上一轮得到的每一个有效组合都进行扫描，将括号插入每个索引值，HashSet负责去重。
    // 这个解法的问题是重复尝试太多，没有及时的剪枝以节省不必要分支的计算。
    // n = 2       ( )
    //            ↑ ↑ ↑
    //        ()() (()) ()()
    // n = 3      ( ) ( )    ( ( ) )
    //           ↑ ↑ ↑ ↑ ↑  ↑ ↑ ↑ ↑ ↑
    //        ()()() (())() ()(()) ((())) (()())
    public List<String> generate2(int n) {
        List<String> result = new LinkedList<>();
        result.add("()");
        for (int i = 1; i < n; ++i) {
            Set<String> dp = new HashSet<>();   // Handle duplicate automatically.
            for (int j = 0; j < result.size(); ++j) {
                String str = result.get(j);
                for (int k = 0; k < str.length(); ++k) {
                    dp.add(str.substring(0, k) + "()" + str.substring(k, str.length())); // 将一对括号插入上一轮的每个结果的每个位置上。
                }
            }
            result.clear();         // 更新结果
            result.addAll(dp);
        }
        return result;
    }
}
