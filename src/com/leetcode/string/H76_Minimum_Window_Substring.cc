#include <string>
#include <iostream>
#include <vector>
#include <unordered_map>
#include <climits>
using namespace std;

/**
 * Created by Michael on 2019/8/21.
 * 
 * Given a string S and a string T, 
 * find the minimum window in S which will contain all the characters in T in complexity O(n).
 * 
 * Example:
 * Input: S = "ADOBECODEBANC", T = "ABC"
 * Output: "BANC"
 * 
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 * 
 * 基本观察
 *      - 求的是 Substring，来自于 S
 *      - 求的是 Minimum 而不是 Maximum
 *      - 需要返回具体 Substring 是什么，而不仅仅是长度
 *      - 虽然涉及两个字符串，但是这两个字符串并不平等，T 是字符集，S 才是 Substring 的出处，因此需要遍历 S
 *      - 没有限定 T 中的字符唯一，也就是说 T 中允许出现重复字符，这就是在暗示我们要用 HashMap 统计 T 的词频而不是简单的用 HashSet。
 * 
 * 滑动窗方法
 *      - 关键：如何选择滑动窗左右边界的移动规则。滑动窗本质就是一个一维的 BBox 而已，只有两个参数：左边界和右边界。
 *      - 一般思路：首先以找到解为目标，然后再对解进行优化，得到最终的答案。
 *          - 第一步：找到解
 *          - 第二步：优化解，尝试找到更短的解
 * 
 * 基本思想
 *      - 滑动窗：同向双指针
 *      - 词频表：Hashtable or ASCII-as-index.
 * 
 */

// 用 freq 统计目标字符集 t 的词频情况
// 然后顺序扫描原字符串 s，如果遇到 freq 中存在的字符，就对其计数减 1，
// 当 freq 中的某个字符的计数降至 0 的时候，说明这个字符已经在扫描窗口内完全出现了，如果已经小于 0 则说明当前窗口的 subarray 已经超量达成该字符的目标
// 当 freq 中所有字符的计数都降至 0 的时候，说明当前扫描窗口的字符串满足要求。可以作为一个合法 candidate，但不一定是最短的那个 candidate。
// 为了快速判断 hashmap 中的所有 kvp 的 v 都为 0，可以单独用一个 counter 来计数，这样就避免了反复扫描 hashmap 的低效操作。很显然，这个 counter 的初始值就是 t 的长度。

// 如何在高压情况下<自然而然>的解决字符串搜索中<指针移动的决策问题>，例如我该移动右指针还是左指针这种决策问题。分析如下：
// s = "a z j s k z", t = "s z"
// 首先，初始化左右指针指向 s 的首元素。
// 一开始的目标是不断移动右指针来<扩张窗口>，直至找到一个满足要求的 substring。为什么不移动左指针？因为移动左指针意味着<收缩窗口>，而这时候你连一个解都没有，收缩窗口只会让问题更无解，所以不能移动左指针。
// a → az → azj → azjs 从不满足到满足条件！i = 0，j = 3
// 当找到第一个满足要求的 substring 时，不再无脑移动右指针，转而根据当前情况选择该移动左指针（收缩）还是移动右指针（扩张）。
// 为什么不再无脑移动右指针？因为右指针只会<扩张窗口>，在当前窗口已经满足要求的前提下，不断扩张窗口，只会让我们距离 Minimum Window 越来越远，所以不能在当前已经窗口满足条件的情况下再移动右指针。
// azjs → zjs → jz 从满足条件到不满足条件！i = 2，j = 3
// 此时我们发现，当前的 window 已经不再满足条件，也就是说，如果我们再继续移动左指针，那么<一定更不可能>满足条件，此时唯有移动右指针，才有希望！
// jz → jsk → jskz 从不满足条件到满足条件！i = 2，j = 5
// 此时我们发现，当前的 window 再次满足条件，也就是说，如果我们再继续移动右指针，那么<一定不可能>得到更短的有效window，此时唯有移动左指针，才有希望！
// jskz → skz → kz 从满足条件到不满足条件！i = 4，j = 5
// 此时我们发现，当前的 window 再次不满足条件，也就是说，轮到移动右指针了，但是右指针再移动就越界了，所以搜索终止。

// 经过上面的分析，我们可以清晰的总结出，移动左右指针分别的原则是什么：
//      - 当前 window 不满足 → 移动右指针（扩张才有希望满足）
//      - 当前 window 满足 → 移动左指针（收缩才有希望更小）
// 所以，其实我们只需要上面这两条原则就行了，并不需要严格的分为第一阶段（找到解）和第二阶段（优化解），因为后面这种分阶段的思维定式会导致代码实现起来更冗长
string minWindow(string s, string t) {
    vector<int> freq(128);
    for (auto ch : t) freq[ch]++;
    int left = 0, right = 0;
    int len = 0, min_len = s.size();
    string result;
    while (right < s.size()) {
        freq[s[right]]--;                               // 当前字符更新到词频表，注意这里用的是--，目的是使词频更接近零或负数，也就是让窗口更有机会满足条件
        if (freq[s[right]] >= 0) len++;                 // 如果右指针所指字符在自减之后非负，说明右指针的字符一定是 t 中的有效字符，因此应该相应的增加有效字符数
        while (len == t.size()) {                       // 如果窗口内的有效字符数已经达到 t，说明已经找到满足要求的 substring
            if (right - left + 1 <= min_len) {          // 此时首先判断该 substring 是不是比现有的解更短，如果是就更新
                min_len = right - left + 1;
                result = s.substr(left, min_len);
            }
            freq[s[left]]++;                            // 尝试移动左指针，注意这里词频更新是++，是让词频更难接近零或负数，即让窗口更难以满足条件
            if (freq[s[left]] > 0) len--;               // 如果左指针所指字符在自增之后大于零，而再此之前等于零，说明移动左指针导致窗口不在满足 t 的条件，有效长度下降
            left++;
        }
        right++;
    }
    return result;
}



// 用于暴力搜索验证每一个 window 内的子字符串能否让词频全部归零
bool isValid(string s, vector<int> map, int count) {
    for (auto c : s) {
        if (map[c] > 0) {
            map[c]--;
            count--;
        }
    }
    if (count > 0) return false;
    else return true;
}

// Time - O(N!)，时间复杂度很高，因为是全组合
// 暴力搜索所有可能的 window，判断是否合法，并统计最小合法 window 长度
// s = "bba", t = "ab"
// i = 0: "b", "bb", "bba"
// i = 1: "b", "ba"
// i = 2: "a"
string minWindow_Brute(string s, string t) {
    vector<int> map(128);
    int count = t.size();
    int min = INT_MAX;
    int begin = 0, end = 0;
    for (auto c : t) map[c]++;
    for (int i = 0; i < s.size(); i++) {            // 双循环遍历所有位置和大小的 window substring
        for (int j = i; j < s.size(); j++) {
            int len = j - i + 1;
            if (isValid(s.substr(i, len), map, count) && len < min) {
                min = len;
                begin = i;
                end = j;
            }
        }
    }
    if (min == INT_MAX) return "";
    return s.substr(begin, end - begin + 1);
}


int main() {

    string s = "ADOBECODEBANC";
    string t = "ABC";
    // cout << minWindow_Brute("bba", "ab") << endl;
    // cout << minWindowSubstring("bba", "ab") << endl;
    cout << minWindowSubstring("acbbaca", "aba") << endl;
    cout << minWindow("a", "a") << endl;
}