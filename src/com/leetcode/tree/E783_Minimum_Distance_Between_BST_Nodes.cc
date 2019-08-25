#include "../common.h"
#include <stack>
#include <climits>
#include <algorithm>
using namespace std;

/**
 * Given a Binary Search Tree (BST) with the root node root, 
 * return the minimum difference between the values of any two different nodes in the tree.
 * 
 * Example :
 * Input: root = [4,2,6,1,3,null,null], Output: 1
 * Explanation:
 * Note that root is a TreeNode object, not an array.
 * The given tree [4,2,6,1,3,null,null] is represented by the following diagram:

          4
        /   \
      2      6
     / \    
    1   3  

 * while the minimum difference in this tree is 1, it occurs between node 1 and node 2, also between node 3 and node 2.
 * The size of the BST will be between 2 and 100.
 * The BST is always valid, each node's value is an integer, and each node's value is different. 
 * 
 * 基本思想
 *      - 利用 BST 的中序遍历已排序特性
 *      - 缓存上一个访问的节点，而不是缓存之前访问的所有节点，节省空间
 *      - min 初始化为 INT_MAX
 * 
 */

// 仅缓存上一个节点，由于是中序遍历，可以确保下个节点的值一定大于上个节点。
int minDiffInBST(TreeNode* root) {
    stack<TreeNode*> stack;
    TreeNode* curr = root;
    int prev = -1;
    int min = INT_MAX;      // 最小值初始化为类型最大值
    while (curr != nullptr || !stack.empty()) {
        if (curr != nullptr) {
            stack.push(curr);
            curr = curr->left;
        }
        else {
            curr = stack.top();
            stack.pop();
            if (prev != -1)     // 第一个访问的元素跳过对比，直接保存为 prev，之后的所有元素都与上一个元素比较。
                min = std::min(min, curr->val - prev);
            prev = curr->val;   // 缓存为上一个元素，用于下一次迭代比较
            curr = curr->right;
        }
    }
    return min;
}

// 中序遍历并保存为已排序数组，然后顺序扫描并比较相邻元素即可。
// Space ~ O(n) 栈需要 N，数组也需要 N
// 我们可以观察到，在 result 数组增长的过程中，其元素始终是有序追加进来的
// 也就是说，我们其实是可以边扫描边比较的，只需要保存上一个元素即可，并不需要保存所有之前的元素。因此有上面的更节约内存的方式
int minDiffInBST_as_array(TreeNode* root) {
    vector<int> result;
    stack<TreeNode*> stack;
    TreeNode* curr = root;
    int min = INT_MAX;      // 最小值初始化为类型最大值
    while (curr != nullptr || !stack.empty()) {
        if (curr != nullptr) {
            stack.push(curr);
            curr = curr->left;
        }
        else {
            curr = stack.top();
            stack.pop();
            result.push_back(curr->val);
            print(result);
            curr = curr->right;
        }
    }
    for (int i = 1; i < result.size(); i++)
        min = std::min(min, result[i] - result[i - 1]);
    return min;
}

int main() {
    TreeNode* root = new TreeNode(4);
    root->left = new TreeNode(2);
    root->right = new TreeNode(6);
    root->left->left = new TreeNode(1);
    root->left->right = new TreeNode(3);
    cout << minDiffInBST_as_array(root) << endl;
    cout << minDiffInBST(root) << endl;
}