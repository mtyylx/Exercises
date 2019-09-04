#include "../common.h"
#include <algorithm>
using namespace std;

/**
 * Created by Michael on 2019/9/4.
 * 
 * Given an array nums of n integers, 
 * are there elements a, b, c in nums such that a + b + c = 0? 
 * Find all unique triplets in the array which gives the sum of zero.
 * 
 * Note:
 * The solution set must not contain duplicate triplets.
 * 
 * Example:
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 * A solution set is:
 * [
 *      [-1, 0, 1],
 *      [-1, -1, 2]
 * ]
 * 
 */

/**
 * 首先，沿用 2sum 的思路，用 target - 元素值，得到 2sum 的新 target
 * 然后，利用已排序数组的性质，用双指针确定剩下两个元素的组合
 *
 * 问题一：为什么双指针选择首尾对向扫描 [→ ←]，而不是起始位置同向扫描 [→ →] ？
 * 思考过程：双指针该按什么顺序扫描，要看哪种扫描方式能够带来<可划分的确定趋势>
 * 对于已排序数组，左右指针同向扫描，没有什么区分度，右移左指针和右移右指针并不会带来截然不同的后果，因此我们无法形成一个移动指针的逻辑，也就不能这么做
 * 而如果是左右指针分别从左右边界扫描，那么右移左指针一定会使 2sum 变大，而左移右指针一定会让 2sum 变小，因此我们就得到了一个移动指针的逻辑，可以实现为代码
 * 
 * 问题二：如何避免重复
 * 思考过程：重复 triplets 的产生，唯一的可能来自于重复的元素，因此我只要跳过这些重复的元素，就一定可以避免重复的三元组。
 * 对于已排序数组，重复元素一定是相邻的，因此可以通过指针比较相邻元素来快速飘过重复元素。
 * 
 */

// Time - O(N^2)
// 外循环确定三元组的第一个元素
// 内循环用双指针确定剩下两个元素
vector<vector<int>> threeSum(vector<int>& nums) {
    vector<vector<int>> results;
    if (nums.size() < 3) return results;
    std::sort(nums.begin(), nums.end());                 // 先排序，后利用已排序数组的特性
    for (int i = 0; i < nums.size() - 2; i++) {          // 给后面两个指针留两个空位
        if (i > 0 && nums[i] == nums[i - 1]) continue;   // 去重：首先要跳过重复元素
        int target = 0 - nums[i];
        int left = i + 1;
        int right = nums.size() - 1;
        while (left < right) {
            if      (nums[left] + nums[right] < target) left++;     // 变得更大
            else if (nums[left] + nums[right] > target) right--;    // 变得更小
            else {
                results.push_back({nums[i], nums[left], nums[right]});
                left++;     // 继续寻找
                right--;    // 继续寻找
                while (left < right && nums[left] == nums[left - 1]) left++;        // 去重
                while (left < right && nums[right] == nums[right + 1]) right--;     // 去重
            }
        }
    }
    return results;
}

int main() {
    vector<int> v = {-1, 0, 1, 2, -1, -4};
    vector<vector<int>> r = threeSum(v);
    print2D(r);
}