#include "../common.h"
#include <stack>
#include <algorithm>
using namespace std;

/**
 * Given a collection of candidate numbers (candidates) and a target number (target), 
 * find all unique combinations in candidates where the candidate numbers sums to target.
 * Each number in candidates may only be used once in the combination.
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * 
 * Example 1:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 * [
 *  [1, 7],
 *  [1, 2, 5],
 *  [2, 6],
 *  [1, 1, 6]
 * ]
 * 
 * Example 2:
 * Input: candidates = [2,5,2,1,2], target = 5,
 * A solution set is:
 * [
 *  [1,2,2],
 *  [5]
 * ]
 * 
 * M39 与 M40 的区别
 *      - M39：candidates 集合元素无重复，每个元素可以重复抽取
 *      - M40：candidates 集合元素有重复，每个元素至多抽取一次
 * 
 * 需要注意的点
 *      - M40 不允许重复抽取元素，看上去只需要把下一层的 start_idx 设为 i + 1 即可，但是并没有那么简单
 *      - 因为需要考虑 [1, 1, 6, 7] target = 8 的情况，即要允许两个 1 都被抽取，与 6 构成 8，又要避免两个 1 分别与 7 构成 8 的重复组合情况，需要小心设计条件规避。
 * 
 */

class Solution {
private:
    vector<vector<int>> result;
    vector<int> path;
    
public:
    void helper(vector<int>& candidates, int target, int start_idx) {
        if (target == 0) {
            result.push_back(path);
        }
        else if (target > 0) {
            for (int i = start_idx; i < candidates.size(); i++) {
                if (i != 0 && candidates[i] == candidates[i - 1] && i > start_idx) continue;     // 跳过非开头元素且非连续起始元素且与前面元素取值相等的 candidate
                path.push_back(candidates[i]);
                helper(candidates, target - candidates[i], i + 1);      // start_idx = i + 1，下一层避免使用重复元素
                path.pop_back();
            }
        }
    }
    
    vector<vector<int>> combinationSum2(vector<int>& candidates, int target) {
        result.clear();
        path.clear();
        sort(candidates.begin(), candidates.end());
        helper(candidates, target, 0);
        return result;
    }
};

int main() {
    vector<int> x = {1, 1, 6, 7};
    Solution s;
    vector<vector<int>> res = s.combinationSum2(x, 8);
    print2D(res);
}
