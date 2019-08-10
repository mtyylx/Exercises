#include <iostream>
#include <algorithm>
#include <vector>

// Memo 记忆的是外循环过程中，每个时期的LIS长度
int LIS(std::vector<int>& a) {
  int len = a.size();
  if (len == 0) return 0;
  std::vector<int> dp(len, 1);      // Init as all-one array.
  int res = 1;                      // LIS is at least 1 for non-empty array.
  for (int i = 1; i < len; i++) {
    for (int j = 0; j < i; j++) {
        if (a[j] < a[i])
            dp[i] = std::max(dp[i], dp[j] + 1);
    }
    res = std::max(res, dp[i]);
  }
  return res;
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
