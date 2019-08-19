#include "../common.h"
#include <stack>
#include <algorithm>
using namespace std;

/**
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), 
 * find all unique combinations in candidates where the candidate numbers sums to target.
 * The same repeated number may be chosen from candidates unlimited number of times.
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * 
 * Example 1:
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 *  [7],
 *  [2,2,3]
 * ]
 * 
 * Example 2:
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 *  [2,2,2,2],
 *  [2,3,3],
 *  [3,5]
 * ]
 * 
 * 关键点
 *      - candidate 是 <集合> 的意义而不是数组的意义。集合，说明元素之间没有顺序的概念，因此元素的组合也就没有顺序的概念，{a, b} 与 {b, a} 是一样的，因此也就不能有重复的组合。
 *      - 集合中抽取元素的限制：无限制，允许无限次重复抽取。
 *      - 所有元素都是正整数。这确保了组合有限，即搜索路径的长度有限，只要路径超过 target 就无需继续。不会出现正负两个元素无限次组合抵消的情况。
 * 
 * 上手难点
 *      - 该如何遍历并缓存不同路径同时相互不干扰？
 *          - 答案：对于递归解法，其实任意时刻只有一条路径，只是这条路径一直被原位修改。
 *      - 对于组合结果，如何避免重复？
 *          - 答案：可以先让 candidate 有序。这样虽然每个元素可以无限次取用，但是我们可以限定，在当前路径下抽取新元素的时候，只从当前元素位置开始<向后抽取>，当前元素前面的不再作为 candidate。这样可以巧妙的避开重复组合的产生。
 * 
 */

// 每次递归需要缓存两个内容
// 1. 当前 path，用于前进或者回头
// 2. 结果容器，用于把成功的 path 保存下来
// 每一条探索路径，都是一次尝试，遇到失败终止条件（target < 0）或者成功终止条件（target == 0）都会回退一步，并尝试新的路径。
// 每尝试前进一步，尝试的候选元素都来自整个集合，即搜索空间都是整个集合。
// 有两种
// 1. 失败终止 target < 0：当前路径已经是绝路，没有再继续的必要，直接回退一步
// 2. 成功终止 target == 0：当前路径就是解，保存解到最终结果，没有再继续的必要，直接回退一步
// 递归终止的三种情况：递归终止就意味着当前 path 已经探索完成，必须回退一步。
// 1. 失败终止
// 2. 成功终止
// 3. 当前深度遍历完成
class Solution {
private:
    vector<vector<int>> results = {};
    vector<int> path = {};    
public:
    void helper(vector<int>& candidates, int start_idx, int target) {
        if (target == 0) {                                         // Exploration Succeeded!
            results.push_back(path);
        } 
        else if (target > 0) {                                     // Continue Exploration
            for (int i = start_idx; i < candidates.size(); i++) {  // 搜索起点不从0开始，而是从当前深度的元素位置开始（包含当前元素，这样可以重复抽取当前元素）
                path.push_back(candidates[i]);                     // path 向前一步
                helper(candidates, i, target - candidates[i]);     // 增加深度，对扩增后的 path 进一步扩增，搜索位置从当前candidate元素开始，target越来越小，离目标（==0）越来越近
                path.pop_back();                                   // path 回退一步（下轮继续向前探索）
            }
        }                                                          // if (target < 0) Dead end! 直接终止，回到上一层回退 path
    }

    vector<vector<int>> combinationSum(vector<int>& candidates, int target) {
        results.clear();                                // results 作为成员变量，每次必须清理干净再开始，否则还会有上次的计算结果。
        path.clear();                                   // path 任何时候只有一份，并不需要作为入参压栈和出栈，所以也同样作为成员变量，以简化代码
        sort(candidates.begin(), candidates.end());     // 首先让 candidate 有序
        helper(candidates, 0, target);                  // 对所有 candidate 开始组合搜索，start_idx = 0, target 就是起始 target
        return results;
    }
};

int main() {
    vector<int> x = {2, 3, 6, 7};
    vector<int> x2 = {2, 3, 5};
    Solution s;
    vector<vector<int>> res = s.combinationSum(x, 7);
    print2D(res);
    vector<vector<int>> res2 = s.combinationSum(x2, 8);
    print2D(res2);
}

/*

[2, 3, 6, 7]    ->    [2, 3, 6, 7]    ->     [2, 3, 6, 7]       ->     [2, 3, 6, 7]
{2} t = 5             {2, 2} t = 3           {2, 2, 2} t = 1           {2, 2, 2, 2} t = -1 终止

                                             [2, 3, 6, 7]       <-     回退一步
                                             {2, 2, 2} t = 1 
                                                                
                                             [2, 3, 6, 7]       ->     [3, 6, 7]
                                             {2, 2, 2} t = 1           {2, 2, 2, 3} t = -2 终止
重复以上过程。

*/