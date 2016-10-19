package com.leetcode.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LYuan on 2016/10/19.
 * Intro-level Exercises for Regex.
 */
public class Basic_Grammar {
    public static void main(String[] args) {
        quantifier_Test();
    }

    /** Selection：选择符 */
    public static void or_Test() {
        find("F(u|i)ck", "Fack Fuck Fick");     // 等效于或运算。
    }

    /** Quantification: 数量限定符，限定前面出现的单独这个字符<允许出现的次数>。只有出现次数在规定范围内才算匹配。*/
    public static void quantifier_Test() {
        /** Greedy 贪婪模式（默认）：<尽可能多>的匹配 */
        // 不加: 出现次数 == 1 (1)  如果字符后面没有跟数量限定符，那么就说明这个字符有且仅能出现1次。
        // *: 出现次数 >= 0 (0, 1, n)
        // +: 出现次数 > 0 (1, n)
        // ?: 出现次数 < 2 (0, 1)
        // {n}: 出现次数 == n
        // {n,}: 出现次数 >= n
        // {n, m}: 出现次数 n <= x <= m
        find("Fuck", "FuckFucker");             // 每个字符都要出现，且只能出现一次，且必须按给定顺序出现。
        find("Fu*ck", "Fck Fuck Fuuuuck");      // u可以出现：0次，1次，n次
        find("Fu+ck", "Fck Fuck Fuuuuck");      // u可以出现：1次，n次
        find("Fu?ck", "Fck Fuck Fuuuuck");      // u可以出现：0次，1次
        find("Fu{3}ck", "Fck Fuck Fuuuck");     // u可以出现：3次
        find("Fu{2,}ck", "Fuck Fuuck Fuuuck");  // u可以出现：大于等于2次
        find("Fu{2,3}ck", "Fuck Fuuck Fuuuck"); // u可以出现：2次，3次

        /** Lazy 懒惰模式（数量限定符后添加?号）：<尽可能少>的匹配 */
        // 注意正则表达式扫描matcher字符串只会扫描一次，在扫描的过程中判断是否匹配，已经扫描且匹配的区间不会成为一个更大的匹配结果的一部分。
        // 例如用正则表达式a.*?扫描"abaab"，第一个ab符合最小匹配，接着第二个aab也符合匹配，此时已经扫描完毕。
        // 虽然整体abaab也符合匹配要求，但是由于不会重复扫描，因此不会讲abaab也算作匹配结果。
        // *?: >= 0，尽可能少的匹配
        // +?: > 0，尽可能少的匹配
        // ??: 0, 1，尽可能少的匹配
        // {n,}?: >= n，尽可能少的匹配
        // {n, m}?: m>=x>=n，尽可能少的匹配
        find("a.*b", "abaab");          // 贪婪，从整个字符串开始匹配，不断缩小，因此匹配结果是：abaab at (0, 5)
        find("a.*?b", "abaab");         // 懒惰，从字符串开始扫描，只要达到匹配最低标准就算匹配，因此匹配结果有两个：ab 和 aab (没有abaab)
        find("F*", "AFF");              // 贪婪，因此只会返回一个匹配结果：AFF at (0, 4)
        find("F*?", "AFF");             // 懒惰，因为*?最少可以匹配0次，因此匹配结果是：5个空字符串"". 位置分别在(0,0)(1,1)(2,2)(3,3)
        find("(Fuck)+", "FuckFuck");    // 贪婪，因此只会返回一个匹配结果：FuckFucker at (0, 8)
        find("(Fuck)+?", "FuckFuck");   // 懒惰，只要找到一个满足的就返回，因此有两个匹配结果：Fuck at (0,4) (4, 8) (没有FuckFuck)
    }

    /** 括号：定义操作符的范围和优先顺序 */
    public static void grouping_Test() {
        find("gr(a|e)y (gray|grey)", "grey gray");      // 或运算被限制于括号内。
        find("(Shit)*X", "ShitShitX");                  // 括号内作为一个字符看待，*作用于括号内的这个整体。
    }

    public static void metacharacter_Test() {
        /** Meta-Character：元字符，即正则表达式专用的转义字符，用来匹配特定的字符 */
        // 注意：区别java自己的转义字符\n,\t，以及正则表达式的转义字符\w,\b。后者为了放在字符串中，必须再加一个转义符\，形如"\\w", "\\b"
        // 点号: 代表任意字符，除了换行符"\n"
        // \w: 代表字母、数字、下划线、汉字
        // \s: 代表空白符（空格，中文全角空格，换行"\n"，制表符"\t"）
        // \d: 代表数字
        // \b: 代表位置 -> 单词的开始和结束
        // ^: 代表位置 -> 整个字符串的开始
        // $: 代表位置 -> 整个字符串的结束
        print(".*", "a;lsdkfj");                            // .*表示匹配任意长度的任意字符串，只要这个字符串内没有换行符就匹配。
        print("\\w \\w \\w", "1 c  ");                      // 表示专门匹配三个非空白符的字符，中间用空格相连。例如"1 2 3"
        print("\\s*Shit", "    \t   Shit");                 // \s*表示任意多个空白符。
        print("Mobile: \\d{11}", "Mobile: 13901234567");    // \d{11}表示匹配十一位连续出现的数字，即手机号。
        print("\\bFuck\\b", "Fuckish");                     // 只匹配Fuck这个单词，不匹配
        find("(Shit)*", "ShitShitShit");
    }

    public static void stringMatchesAPI_Test() {
        // Or
        print("F(u|i)ck", "Fack");

        // Quantifier
        print("Fuck", "Fuck");
        print("Fu*ck", "Fuuuuuuck");
        print("Fu+ck", "Fck");
        print("Fu?ck", "Fuuck");
        print("Fu{3}ck", "Fuuuck");
        print("Fu{2,}ck", "Fuuuck");
        print("Fu{2,4}ck", "Fuuuck");

        // Grouping
        print("(Shit)*X", "ShitShitX");

        // Metacharacters
        print(".*", "a;lsdkfj");                            // .*表示匹配任意长度的任意字符串，只要这个字符串内没有换行符就匹配。
        print("\\w \\w \\w", "1 c  ");                      // 表示专门匹配三个非空白符的字符，中间用空格相连。例如"1 2 3"
        print("\\s*Shit", "    \t   Shit");                 // \s*表示任意多个空白符。
        print("Mobile: \\d{11}", "Mobile: 13901234567");    // \d{11}表示匹配十一位连续出现的数字，即手机号。
        print("\\bFuck\\b", "Fuckish");                     // 只匹配Fuck这个单词，不匹配
        find("(Shit)*", "ShitShitShit");
    }

    /** 使用 String.matches(String regex): 判断<整个字符串>是否匹配正则表达式 */
    // 要特别注意，Java提供的String类库的matches()方法返回true的条件是<整个字符串>都必须匹配
    // 也就是说，不管你定义的正则表达式regex是什么内容，这个API实际处理的都是^regex$，即强制从字符串的开头开始匹配，并要一直匹配到结尾。
    private static void print(String regex, String str) {
        boolean match = str.matches(regex);
        System.out.println("[" + regex + "] -> " + "\"" + str + "\"" + " -> " + match);
    }

    /** 使用 java.util.regex 类库：可以判断正则表达式是否能够匹配所给字符串的局部或全部，如果可以匹配，则给出所有匹配的位置区间 */
    // regex类库是专门进行正则匹配的库，功能很全面和强大。
    // 需要使用Pattern的静态工厂方法构造正则表达式的匹配器(pattern)，
    // 然后再由待匹配字符串定义一个matcher对象，最后使用find()来搜索。
    private static void find(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean find = false;
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("@ Regex: [" + regex + "] | @ Matcher: [" + str + "]");
        while (matcher.find()) {
            find = true;
            System.out.println("=> Found \"" + matcher.group() + "\" at (" + matcher.start() + ", " + matcher.end() + ").");
        }
        if (!find) System.out.println("=> No Match Found.");
    }
}