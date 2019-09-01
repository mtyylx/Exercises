#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
/**
 * Created by Michael on 2019/8/10.
 * 
 * LIS - 经典 DP
 * 
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 * 
 * Example:
 * Input: [10,9,2,5,3,7,101,18]
 * Output: 4 
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
 * 
 * Note:
 * There may be more than one LIS combination, it is only necessary for you to return the length.
 * Your algorithm should run in O(n2) complexity.
 * Follow up: Could you improve it to O(n log n) time complexity?
 * 
 * 基本思想
 *      - 明确 Subsequence 是 Ordered Subset，并不要求连续
 *      - 对 sub-problem 建模，当前问题如何与上一级问题联系起来
 *      - 对最小问题建模，任何非空数组的 LIS 至少是 1，其下界情况出现在严格降序排列的数组上
 * 
 * 相似问题
 *      - M1143 Longest Common Subsequence (LCS)：二维 DP
 *      - M718 Longest Common Substring (LCS)：二维 DP
 *      - M300 Longest Increasing Subsequence (LIS)：一维 DP
 * 
 */


// Time ~ O(n^2) 双循环 DP 解法
// [10]
// [10, 9]：        i = 1，由于 a[i] 并不大于 a[j]，因此不可能构造更长的 LIS，跳过 
// [10, 9, 2]：     i = 2，由于 a[i] 比所有 a[j] 都小，因此依然不可能构造更长的 LIS，跳过 
// [10, 9, 2, 15]： i = 3，由于 a[i] 比所有 a[j] 都大，因此 15 可以分别和 10 / 9 / 2 构成更长的 LIS，需要在这个过程中让 dp[i] 更大，即 max(dp[i], dp[j] + 1)
int LIS(std::vector<int>& a) {
    if (a.empty()) return 0;
    vector<int> dp(a.size(), 1);            // 初始化为全一状态，因为每个 sub-problem 的解至少是 1
    int lis = 1;                            // 初始化一个全局状态记录变量
    for (int i = 1; i < a.size(); i++) {    // 从第二个元素开始解决问题，i 是当前正解决的 sub-problem 规模的编号
        for (int j = 0; j < i; j++) {       // j 负责扫描比当前问题规模小一级的数组区域中的所有元素，即 0 to (i - 1)
            if (a[j] < a[i]) 
                dp[i] = max(dp[i], dp[j] + 1);      // 如果 i 元素大于 j 元素，说明 i 可以插入到 j 元素后面，构成一个更长的 LIS，其长度是 dp[j] + 1，同时要比当前问题已知的最长 LIS（即 dp[i]）更长才行
        }
        lis = std::max(lis, dp[i]);                 // 此时已经扫描完把 i 插入 (i - 1) Sub-problem 的所有有效位置，并且得到了局部 LIS，即 dp[i]，如果有必要就更新全局 LIS。
    }
    return lis;
}

// 二分查找的主要用途是可以让 O(n) 的操作变为 O(logn)，但是使用前提是数组已排序
// 原数组是未排序的，显然不能直接用二分查找，但是外循环扫描过程中产生的 LIS 本身是已排序的，因此内循环可以利用二分查找降低复杂度
// 因此整体复杂度从 O(n * n) 变为 O(n * logn)
// 这里 Memo 记忆的不再是扫描过程中每个时期的LIS长度，而是一个LIS的变体，最后得到的并不能确保是一个有效的 LIS。这是一个问题。
int LIS_BinarySearch(std::vector<int>& a) {
  std::vector<int> curr_lis;
  for (int i = 0; i < a.size(); i++) {
      auto iter = std::lower_bound(curr_lis.begin(), curr_lis.end(), a[i]);
      if (iter == curr_lis.end())
          curr_lis.push_back(a[i]);  // Expand LIS
      else
          *iter = a[i];              // Update element in LIS
      
      for (auto x : curr_lis) std::cout << x << ", ";
      std::cout << std::endl;
  }
  return curr_lis.size();
}


int main() {
    // std::vector<int> data = {10, 9, 2, 5, 3, 7, 101, 18};
    std::vector<int> data = {10, 9, 2, 55, 333, 777, 101, 28};
    int res = LIS(data);
    int res2 = LIS_BinarySearch(data);
    std::cout << "Longest Increasing Subsequence = " << res << std::endl;
    std::cout << "Longest Increasing Subsequence = " << res2 << std::endl;
}

// 问题的规模随着数组的长度不断增加，而且由于只需要LIS的长度，因此最优解允许存在多个
// idx = 0 ~ i 范围的所有解(IS)一定都是 idx = 0 ~ (i + 1) 范围的解
// idx = 0 ~ i 范围的最优解(LIS)一定都是 idx = 0 ~ (i + 1) 范围的最优解的一部分：即存在最优子结构

// for {10, 9, 2, 5, 3, 7, 101, 18}
// i = 0 : {10}
// IS = {10}
// LIS = {10}

// i = 1 : {10, 9}
// IS = {10}, {9}
// LIS = {10}, {9}

// i = 2 : {10, 9, 2}
// IS = {10}, {9}, {2}
// LIS = {10}, {9}, {2}

// i = 3 : {10, 9, 2, 5}
// IS = {10}, {9}, {2}, {5}, {2, 5}
// LIS = {2, 5}

// i = 4 : {10, 9, 2, 5, 3}
// IS = {10}, {9}, {2}, {5}, {2, 5}, {3}, {2, 3}
// LIS = {2, 5}, {2, 3}

// i = 5 : {10, 9, 2, 5, 3, 7}
// IS = {10}, {9}, {2}, {5}, {2, 5}, {3}, {2, 3}, {7}, {2, 7}, {5, 7}, {2, 5, 7}, {3, 7}, {2, 3, 7}
// LIS = {2, 5, 7}, {2, 3, 7}

// i = 6 : {10, 9, 2, 5, 3, 7, 101}
// IS = {10}, {9}, {2}, {5}, {2, 5}, {3}, {2, 3}, {7}, {2, 7}, {5, 7}, {2, 5, 7}, {3, 7}, {2, 3, 7}, {101}, all add 101
// LIS = {2, 5, 7, 101}, {2, 3, 7, 101}

// i = 7 : {10, 9, 2, 5, 3, 7, 101, 18}
// IS = {10}, {9}, {2}, {5}, {2, 5}, {3}, {2, 3}, {7}, {2, 7}, {5, 7}, {2, 5, 7}, {3, 7}, {2, 3, 7}, {101}, all add 101, add 18
// LIS = {2, 5, 7, 101}, {2, 3, 7, 101}, {2, 5, 7, 18}, {2, 3, 7, 18}
