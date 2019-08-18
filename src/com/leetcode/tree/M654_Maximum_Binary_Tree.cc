#include "../common.h"
#include <algorithm>
#include <stack>
using namespace std;

/**
 * Created by Michael on 2019/8/18.
 * 
 * Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:
 * 
 * 1. The root is the maximum number in the array.
 * 2. The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
 * 3. The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
 * Construct the maximum tree by the given array and output the root node of this tree.
 * 
 * Example 1:
 * Input: [3,2,1,6,0,5]
 * Output: return the tree root node representing the following tree:
 *
 *      6
 *   /     \
 *  3       5
 *   \      / 
 *    2    0   
 *     \
 *      1
 *
 * Notes: The size of the given array will be in the range [1,1000].
 * 
 * 
 * 题目特点
 *      - 建树：能够看到建树的规则具有明确的递归特性
 *      - 限定为无重复、无序、二叉树
 *      - 注释里面明确提到这个数组的长度小于 1000，这其实是在<暗示你>可以放心使用递归来解题，不会爆栈。因为默认栈空间在 1-2 MB。
 * 
 * 基本思路
 *      - 如果按照数组来解是很困难的
 *      - 如果上来就要迭代解法也是很困难的
 *      - 但是观察到题目中给出的建树规则具有<非常典型的递归特性>，因此为了快速打开局面，可以首先写出来递归的解法，再根据递归解法的 helper 函数接口改写成迭代写法。
 *      - 利用 C++ 的迭代器，同时获得 idx 和 val。
 * 
 * Tag
 *      - Recursion
 *      - DFS + Stack
 * 
 */

// Recursion
// 每一次递归只负责处理 [start, end) 区间。
// 每一次递归首先设定终止条件：只要 start 与 end 重合就结束（等效为先压栈，后检查）
// 特别注意是左闭右开的，end 被排除在外。
TreeNode* pick_max_return_root(vector<int>& nums, vector<int>::iterator start, vector<int>::iterator end) {
    if (start >= end) return nullptr;
    auto max_pos = max_element(start, end);
    int max_val = *max_pos;
    TreeNode* root = new TreeNode(max_val);
    root->left = pick_max_return_root(nums, start, max_pos);        // 直接压栈，至于是否区间是否有效，由下个深度的递归终止条件决定。
    root->right = pick_max_return_root(nums, max_pos + 1, end);     // 直接压栈
    return root;
}

TreeNode* constructMaximumBinaryTree(vector<int>& nums) {
    return pick_max_return_root(nums, nums.begin(), nums.end());
}

// Iterative: Stack + 利用相邻元素大小顺序与树结构的对应特性
// 降序数组，对应的树结构刚好全是右分支，且每个元素都是后面元素的根节点
// 升序数组，对应的树结构刚好全是左分支，且每个元素都是后面元素的左儿子
// 栈内元素并不是数组的全部元素，有一些会中途删除，感觉自己写不出来这种解法。
TreeNode* constructMaximumBinaryTree_Hard(vector<int>& nums) {
    stack<TreeNode*> stack;
    for (int i = 0; i < nums.size(); ++i)
    {
        TreeNode* cur = new TreeNode(nums[i]);
        while (!stack.empty() && stack.top()->val < nums[i])       // 升序：前面节点做当前节点的左儿子
        {
            cur->left = stack.top();
            stack.pop();
        }
        if (!stack.empty())
            stack.top()->right = cur;                               // 降序：当前节点做前面节点的右儿子
        stack.push(cur);
    }
    while (stack.size() > 1) {
        stack.pop();                                        // 栈内元素顺序为严格降序，栈底元素是 root
    }
    return stack.top();
}

// Iterative: DFS + Stack
// 如果上来就让你写迭代写法，你可能很难一上来就想到这个 stack 里应该一次压两个值，分别是扫描区间的首指针和尾指针
// 但是如果你写好了递归的写法，这时候再写迭代就非常容易了，本质上递归的解法是隐式的用函数栈，所以只需要看 helper 函数的接口用了哪些变量，这些变量就是每次压栈的目标。
// 原本以为会很简单，但是发现有返回值不太好弄。
// TreeNode* constructMaximumBinaryTree_iterative(vector<int>& nums) {
//     if (nums.empty()) return nullptr;
//     TreeNode* root = nullptr;
//     stack<pair<vector<int>::iterator, vector<int>::iterator>> stack;    // 把处理区间的起点和终点作为压栈元素
//     stack.push({nums.begin(), nums.end()});
//     while (!stack.empty()) {
//         auto x = stack.top();
//         stack.pop();
//         if (x.first >= x.second) 
//     }
// }

int main() {
    vector<int> x = {3, 2, 1, 6, 0, 5};
    constructMaximumBinaryTree_Hard(x);
}