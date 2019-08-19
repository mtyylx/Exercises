#include "../common.h"
#include <vector>
#include <unordered_map>
/*
 * Created by Michael on 2019/8/16.
 *
 * Given an array of integers, return indices of the two numbers such that they
 * add up to a specific target.
 * 
 * You may assume that each input would have exactly one solution, and you may
 * not use the same element twice.
 * 
 * Example:
 * 
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 * 
 * 基本思想
 *      - 查询 target 残差
 *      - Hashmap KVP：残差做键，idx做值
 * 
 */

std::vector<int> twoSum(std::vector<int>& nums, int target) {
    // KVP = residual -> idx (用残差反查idx，残差相同就是target)
    std::unordered_map<int, int> map;
    for (int i = 0; i < nums.size(); i++) {
        int residue = target - nums[i];
        if (map.find(nums[i]) == map.end())     // 用原值查 map 里面是否已经存在
            map[residue] = i;                   // 如果不存在，就把补值作为 Key 存进去
        else
            return std::vector<int> {map[nums[i]], i};
    }
    return {};
}

// 时间复杂度 O(n^2)
std::vector<int> twoSum_n2(std::vector<int>& nums, int target) {
    std::vector<int> results;
    for (int i = 0; i < nums.size(); i++) 
        for (int j = i + 1; j < nums.size(); j++)
            if (nums[i] + nums[j] == target)
                results = {i, j};
    return results;
}

int main() {
    std::vector<int> x = {2, 7, 11, 15};
    twoSum(x, 9);
}

