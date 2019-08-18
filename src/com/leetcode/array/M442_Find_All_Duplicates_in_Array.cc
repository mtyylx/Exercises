#include "../common.h"
using namespace std;

/**
 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 * Find all the elements that appear twice in this array.
 * Could you do it without extra space and in O(n) runtime?
 * 
 * Example:
 * Input: [4,3,2,7,8,2,3,1]
 * Output: [2,3]
 * 
 * 基本思想
 *      - 正整数数组使用 value-as-index
 *      - 正负翻转：Coin Flipping
 * 
 */


// 题目的限定条件非常多，才能用 value as idx 的方法来解
// 限定1：元素取值恒正，可以做为 idx 使用
// 限定2：元素取值范围不会超过元素个数
// 限定3：重复只会成对出现，不会超过两个
// mark by negation
// if you ever come across a value that is positive after negating, you know you've seen it before!
//  1   2   3   4  5  6   7   8
//  4,  3,  2, -7, 8, 2,  3,  1, 
//  4,  3, -2, -7, 8, 2,  3,  1, 
//  4, -3, -2, -7, 8, 2,  3,  1, 
//  4, -3, -2, -7, 8, 2, -3,  1, 
//  4, -3, -2, -7, 8, 2, -3, -1, 
//  4,  3, -2, -7, 8, 2, -3, -1,   [2, ]
//  4,  3,  2, -7, 8, 2, -3, -1,   [2, 3, ]
// -4,  3,  2, -7, 8, 2, -3, -1,   [2, 3, ]
vector<int> findDuplicates(vector<int>& nums) {
    vector<int> result;
    for (int i = 0; i < nums.size(); i++) {
        int val_as_idx = abs(nums[i]) - 1;
        nums[val_as_idx] = - nums[val_as_idx];
        if(nums[val_as_idx] > 0) 
            result.push_back(abs(nums[i]));
        print(nums);
        print(result);
    }
    return result;
}

int main() {
    vector<int> x = {4, 3, 2, 7, 8, 2, 3, 1};
    findDuplicates(x);
}