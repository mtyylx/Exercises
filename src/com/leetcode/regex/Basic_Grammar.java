package com.leetcode.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LYuan on 2016/10/19.
 * Intro-level Exercises for Regex.
 */
public class Basic_Grammar {
    public static void main(String[] args) {
        boundaryMatcher_Test();
    }

    /** Selection：选择符 */
    static void or_Test() {
        find("F(u|i)ck", "Fack Fuck Fick");     // 等效于或运算。
    }

    /** Quantification: 数量限定符，限定前面出现的单独这个字符<允许出现的次数>。只有出现次数在规定范围内才算匹配。*/
    static void quantifier_Test() {
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

        /** Reluctant(Lazy) 懒惰模式（数量限定符后添加?号）：<尽可能少>的匹配 */
        // 注意正则表达式扫描matcher字符串只会扫描一次，在扫描的过程中判断是否匹配，已经扫描且匹配的区间不会成为一个更大的匹配结果的一部分。
        // 例如用正则表达式a.*?扫描"abaab"，第一个ab符合最小匹配，接着第二个aab也符合匹配，此时已经扫描完毕。
        // 虽然整体abaab也符合匹配要求，但是由于不会重复扫描，因此不会讲abaab也算作匹配结果。
        // *?: >= 0，尽可能少的匹配
        // +?: > 0，尽可能少的匹配
        // ??: 0, 1，尽可能少的匹配
        // {n,}?: >= n，尽可能少的匹配
        // {n, m}?: m>=x>=n，尽可能少的匹配
        find("a.*b", "abaab");          // 贪婪，在扫描过程中寻找到最大匹配，因此匹配结果是：abaab at (0, 5)
        find("a.*?b", "abaab");         // 懒惰，从字符串开始扫描，只要达到匹配最低标准就算匹配，因此匹配结果有两个：ab 和 aab (没有abaab)
        find("F*", "AFF");              // 贪婪，因此只会返回一个匹配结果：AFF at (0, 4)
        find("F*?", "AFF");             // 懒惰，因为*?最少可以匹配0次，因此匹配结果是：5个空字符串"". 位置分别在(0,0)(1,1)(2,2)(3,3)
        find("(Fuck)+", "FuckFuck");    // 贪婪，因此只会返回一个匹配结果：FuckFucker at (0, 8)
        find("(Fuck)+?", "FuckFuck");   // 懒惰，只要找到一个满足的就返回，因此有两个匹配结果：Fuck at (0,4) (4, 8) (没有FuckFuck)
    }

    /** 括号：定义操作符的范围和优先顺序 */
    static void grouping_Test() {
        find("gr(a|e)y (gray|grey)", "grey gray");      // 或运算被限制于括号内。
        find("(Shit)*X", "ShitShitX");                  // 括号内作为一个字符看待，*作用于括号内的这个整体。
    }

    /** 使用<匹配特定字符内容>的元字符 */
    // Meta-Character：元字符，即正则表达式专用的转义字符，有的可以用来匹配特定字符内容，有的则可以用来匹配特定位置
    // 注意：区别java自己的转义字符（\n,\t...）和正则表达式专用的转义字符（\w,\b...）。后者放在字符串中必须再加一个转义符\，形如"\\w", "\\b".
    static void contentMatcher_Test() {
        // . : 代表任意字符，除了换行符"\n"
        // \w: 代表任意非空白字符（字母、数字、下划线、汉字）
        // \s: 代表任意空白字符（空格，中文全角空格，换行"\n"，制表符"\t"）
        // \d: 代表任意数字。

        /**  . - 匹配：任意字符 */
        // .*表示任意长度的任意字符串，只要遇到换行符就算匹配结束。
        // 因此下面语句返回匹配结果如下：
        // => "abc" at (0, 3)
        // => "" at (3, 3)
        // => "12345 at (4, 9)
        // => "" at (9, 9)
        // => "" at (10, 10)
        // 为什么有这么多空字符串？让我们来分析一下。首先分解原字符串：
        //    0   1   2    3   4   5   6   7   8    9     (字符串索引）
        //    ↑   ↑   ↑    ↑   ↑   ↑   ↑   ↑   ↑    ↑
        //    a   b   c   \n   1   2   3   4   5   \n
        //  ↑   ↑   ↑   ↑    ↑   ↑   ↑   ↑   ↑   ↑    ↑
        //  0   1   2   3    4   5   6   7   8   9    10  (匹配位置索引)
        // 从左向右扫描，寻找最大匹配（即只有遇到换行符才结束匹配）
        // 匹配1: 0 - 3 = "abc"
        // 匹配2: 3 - 3 = ""
        // 匹配3: 4 - 9 = "12345"
        // 匹配4: 9 - 9 = ""
        // 匹配5: 10 - 10 = "" (容易忽视，最后一个字符结尾处也算一个索引。)
        find(".*", "abc\n12345\n");

        /**  \w - 匹配：任意非空白字符 */
        // \\w\\w\\w表示三个非空白字符相连。
        // 由于先发现的是"abc 123"，已经匹配，这里的"123"已经使用（扫描过），不会重复与"456"再组合匹配。
        find("\\w\\w\\w \\w\\w\\w", "abc 123 456");

        /**  \s - 匹配：任意空白字符 */
        // \\s{2,}表示连续出现大于等于2两个空白字符。因此可以匹配到两个结果："Mi   el" at (0,7)，以及"Mi \t el" at (14, 21)
        find("Mi\\s{2,}el", "Mi   el Mi el Mi \t el");

        /**  \d - 匹配：任意数字字符 */
        find("\\d{4,}", "123443215678");    // 贪婪：匹配最长的，即只有一个匹配结果"123443215678"
        find("\\d{4,}?", "123443215678");   // 懒惰：匹配最短的，扫描过程中有四个结果："1234", "4321", "5678".
        find("\\d{4,}", "123456 12345");    // 贪婪：要明白，贪婪匹配的前提，是首先能够符合匹配条件。
                                            // 因此扫描至空格时第一个匹配就结束了，这也是当前能匹配的最长字符串了。从下一个元素开始新的匹配。
                                            // 最后的匹配结果有两个："123456" at (0, 6), "12345" at (7, 12).
        find("\\d{4,}?", "123456 12345");   // 懒惰：匹配的结果也是两个："1234" at (0, 4), "1234" at (7, 11).
    }

    /** 使用<匹配特定位置>的元字符，这类元字符并不匹配具体内容，而是限定了具体位置。 */
    static void boundaryMatcher_Test() {
        // \b: 匹配任意单词的开始和结束位置
        // ^: 匹配整个字符串的开始位置
        // $: 匹配整个字符串的结束位置

        /**  \b - 匹配任意单词的开始和结尾位置 */
        find("\\bfuck\\b", "fuck fucker fuckish fuckfuck");
    }

    static void stringMatchesAPI_Test() {
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
    // 然后再由待匹配字符串定义一个matcher对象，使用find()来搜索。
    // 最后用start() / end()显示匹配的起始位置和终止位置，
    // 要注意的是，这里的位置索引与数组的索引不同，如下图所示：
    // 数组索引：    [____0____] [____1____] [____2____] [____3____] [____4____]
    // 匹配索引：   <0>        <1>         <2>         <3>         <4>        <5>
    // 所以即使字符串索引只有0-4，匹配索引取值范围也可以达到0-5.
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
