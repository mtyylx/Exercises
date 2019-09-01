#include <string>
#include <algorithm>
#include "../common.h"
using namespace std;

/**
 * Created by Michael on 2019/8/25.
 * 
 * 经典 DP 问题 - LCS
 * 
 * Given two strings text1 and text2, return the length of their longest common subsequence.
 * A subsequence of a string is a new string generated from the original string with some characters (can be none) 
 * deleted without changing the relative order of the remaining characters. 
 * (eg, "ace" is a subsequence of "abcde" while "aec" is not). 
 * A common subsequence of two strings is a subsequence that is common to both strings.
 * If there is no common subsequence, return 0.
 * 
 * Example 1:
 * Input: text1 = "abcde", text2 = "ace" 
 * Output: 3  
 * Explanation: The longest common subsequence is "ace" and its length is 3.
 * 
 * Example 2:
 * Input: text1 = "abc", text2 = "abc"
 * Output: 3
 * Explanation: The longest common subsequence is "abc" and its length is 3.
 * 
 * Example 3:
 * Input: text1 = "abc", text2 = "def"
 * Output: 0
 * Explanation: There is no such common subsequence, so the result is 0.
 * 
 * Constraints:
 * 1 <= text1.length <= 1000
 * 1 <= text2.length <= 1000
 * The input strings consist of lowercase English characters only.
 * 
 * 基本思想
 *      - 字符串消解
 *      - 空字符串的特性作为 Bottom-Up 的起始解
 *      - Top-Down (Reduce and Conquer)
 *      - 二维 DP table
 * 
 * 
 * 相似问题
 *      - M1143 Longest Common Subsequence (LCS)：二维 DP
 *      - M718 Longest Common Substring (LCS)：二维 DP
 *      - M300 Longest Increasing Subsequence (LIS)：一维 DP
 * 
 * 思考方式
 *      - 首先明确，subsequence 问题寻找的是 ordered subset，即保持原数组或字符串顺序的前提下，取满足要求的元素子集。
 *      - 最暴力的方法就是列出每个字符串的所有 subsequence，这是一个组合问题，组合的个数是字符串长度的阶乘，即复杂度 O(n!)，然后再匹配。
 *      - 动态规划的核心，是寻找子问题或子结构，然后对其建模并实体化。
 *      - 动态规划应用在字符串问题上，最常用的思路是：字符串消解（String Decomposing），背后的思想是 Top-Down / Reduce & Conquer
 *      - 在构造动态规划所需的子问题时，比较有助于思考的方法是：把问题实体化为伪代码函数调用，设计函数的接口，然后看看是否呈现递归的特质
 *      - LCS 只需要返回解的长度，而并不需要返回解是什么，这恰恰是 DP 所擅长的。
 * 
 * 动态规划的建模过程：
 *      - 第一步：将问题 formulate 为函数调用：lcs("abcde", "ace")
 *      - 第二步：消解字符串，削减问题规模至可解。关键在于每次消解一位字符时，会遇到两种情况：匹配（已解决） or 不匹配（竞争）。
 *              针对不匹配的情况，存在竞争，不确定应该在哪个字符串上做削减，需要在两个方向（两个字符串）上都进行进一步的探索。
 * 
 *              lcs("a bcde", "a ce")
 *                                     匹配 a，削减问题规模 1 个字符，且匹配长度 + 1
 *              1 + lcs("b cde", "c e")
 *                                     不匹配，需要同时考虑削减 b 或者 c 的最优解（即最大长度），其实这里是削减 str1 还是削减 str2 就是 DP 二维表中向右探索还是向下探索的问题本质。
 *              1 + max( lcs("c de", "c e") , lcs("b cde", "e") ) 
 * 
 *              1 + max{ 1 + lcs("d e", "e"), max[ lcs("cde", "e"), lcs("cde", "") ] }
 *                                                                             终于递归至其中一个字符串为空字符串。由于空字符串与任何字符串的 LCS 都是 0，因此可以终止递归。
 *              1 + max{ 1 + ...            , max[ ...            , 0 ] }
 * 
 * 将上面的问题通过二维DP表的形式呈现出来如下，表头是字符串1，左侧是字符串2
 * 
 *         “”    a    b    c    d    e
 *    “”    -    -    -    -    -    -      表中每个元素的含义：字符串1 的 0-列索引 子字符串 vs 字符串2 的 0-行索引 子字符串的 LCS
 *     a    -    -    x    -    -    -      举例： x 单元格所表示的含义就是 LCS("ab", "a")
 *     c    -    -    -    -    -    -            y 单元格表示的则是 LCS("abcd", "ace")
 *     e    -    -    -    -    y    -           元素的取值含义是 LCS 的长度
 * 
 *         “”    a    b    c    d    e
 *    “”    0    0    0    0    0    0
 *     a    0    -    -    -    -    -      (1) 首先确定左上角两条边界的 LCS 都是 0，因为任何字符串与 "" 的 LCS 都是 0
 *     c    0    -    -    -    -    -  
 *     e    0    -    -    -    -    -  
 * 
 *         “”    a    b    c    d    e
 *    “”    0    0    0    0    0    0
 *     a    0    1    -    -    -    -      (2) 开始遍历剩下的矩阵区域
 *     c    0    -    -    -    -    -      Case #1：如果当前元素的<行索引>（字符串1）与<列索引>（字符串2）的字符相同，说明找到匹配项，此时我需要同时削减这两个字符，才能得到更小规模的问题，
 *     e    0    -    -    -    -    -      因此反过来思考，当前位置的 LCS 就是左上对角相邻元素的 LCS 值 + 1
 *                                          Case #2：如果当前元素投影在两个字符串上的字符并不相同，那么就存在竞争，我要不然砍掉字符串1中的字符（正上方相邻元素）、要不然砍掉字符串2中的字符（正左侧相邻元素）
 *                                          体现为代码，就是 max(正上方LCS，正左方LCS)
 */

// Longest Common Subsequence
// Recursive (Top-down)
int LCS(const string& text1, const string& text2) {
    if (text1.empty() || text2.empty()) return 0;
    if (text1[0] == text2[0]) return 1 + LCS(text1.substr(1), text2.substr(1));
    else return max(LCS(text1.substr(1), text2.substr(0)), LCS(text1.substr(0), text2.substr(1)));
}

// Iterative (Bottom-Up)
// Time ~ O(nm), Space ~ O(nm)
int LCS2(string text1, string text2) {
    int cols = text1.size() + 1;
    int rows = text2.size() + 1;
    vector<vector<int>> dp(rows, vector<int>(cols, 0));
    for (int i = 1; i < rows; i++) {                        // 搜索从坐标 (1, 1) 开始，因为矩阵的左上两条边表示与空字符串的 LCS 值，只会为 0
        for (int j = 1; j < cols; j++) {
            if (text1[j - 1] == text2[i - 1]) dp[i][j] = 1 + dp[i - 1][j - 1];  // 注意这里 i 和 j 的索引用在字符串里面依然要从 0 开始，因此需要减一，这里容易疏忽
            else dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
        }
    }
    return dp[rows - 1][cols - 1];
}

/** Longest Common Substring
 * Time - O(nm)
 * Substring 的要求比 Subsequence 更为严格，如果相等的字符并不相邻，那么就应该 “立即死亡”，即 DP 值为 0，而不像 Subsequence 那样保持之前的 LCS 值
 * 这样本质是把 Substring 问题转化为 Suffix/Prefix 问题
 * DP 矩阵中每个元素的含义从 “当前两个 substring 的 LCS 值” 变为 “当前两个 substring 中是否一个字符串是另外一个字符串的后缀？如果是，返回后缀长度；如果不是，返回零” 
 * 这样我们就可以得到生成 DP 表每个元素的递推公式了。
 * 另外需要考虑到我们在生成 DP 表的时候需要时刻监视目前的最大 LCS 值
 * 
 * 以 “ABAB” 和 “BABA” 为例分析，绘制二维 DP 表
 * 
 *       ""   A    B    A    B
 * ""    0    0    0    0    0
 *  B    0    0    1    0    1      "A" vs "B" = 0 (非后缀), "AB" vs "B" = 1, "ABA" vs "B" = 0（非后缀）, "ABAB" vs "B" = 1
 *  A    0    1    0    2    0      "A" vs "BA" = 1, "AB" vs "BA" = 0（非后缀）, "ABA" vs "BA" = 2, "ABAB" vs "BA" = 0（非后缀）
 *  B    0    0    2    0    3      
 *  A    0    1    0    3    0
 * 
 */
int LCSubstring(string text1, string text2) {
    int cols = text1.size() + 1;
    int rows = text2.size() + 1;
    vector<vector<int>> dp(rows, vector<int>(cols, 0));
    int max = 0;
    for (int r = 1; r < rows; r++) {
        for (int c = 1; c < cols; c++) {
            if (text1[c - 1] == text2[r - 1]) dp[r][c] = 1 + dp[r - 1][c - 1];      // 只有尾部字符相同，才有可能存在共同的 suffix，才有必要从上一阶段问题取答案 + 1，否则就是零（初始值）
            max = std::max(max, dp[r][c]);                                          // 如果需要就更新 max
        }
    }
    return max;
}


int main() {

    string a = "abcde";
    string b = "ace";
    cout << LCS(a, b) << endl;
    cout << LCS("ylqpejqbalahwr", "yrkzavgdmdgtqpg") << endl;
    cout << LCS2("ylqpejqbalahwr", "yrkzavgdmdgtqpg") << endl;
    cout << LCSubstring("ABAB", "BABA") << endl;
}

