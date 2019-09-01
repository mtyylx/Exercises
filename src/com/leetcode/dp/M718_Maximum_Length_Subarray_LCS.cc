#include "../common.h"
using namespace std;

/**
 * Created by Michael on 2019/9/1.
 * 
 * LCS - 经典 DP 问题
 * 
 * Given two integer arrays A and B, 
 * return the maximum length of an subarray that appears in both arrays.
 * 
 * Example 1:
 * 
 * Input:
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * Output: 3
 * Explanation: 
 * The repeated subarray with maximum length is [3, 2, 1].
 * 
 * Note:
 * 1 <= len(A), len(B) <= 1000
 * 0 <= A[i], B[i] < 100
 * 
 * 相似问题
 *      - M1143 Longest Common Subsequence (LCS)：二维 DP
 *      - M718 Longest Common Substring (LCS)：二维 DP
 *      - M300 Longest Increasing Subsequence (LIS)：一维 DP
 * 
 * 
 */

int findLength(vector<int>& A, vector<int>& B) {
    int rows = A.size() + 1;
    int cols = B.size() + 1;
    vector<vector<int>> dp(rows, vector<int>(cols, 0));
    int max = 0;
    for (int r = 1; r < rows; r++) {
        for (int c = 1; c < cols; c++) {
            if (A[r - 1] == B[c - 1]) dp[r][c] = 1 + dp[r - 1][c - 1];
            max = std::max(max, dp[r][c]);
        }
    }
    return max;
}