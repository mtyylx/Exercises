#include "../common.h"
#include <stack>
using namespace std;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 * 
 * Assume a BST is defined as follows:
 *  1. The left subtree of a node contains only nodes with keys less than the node's key.
 *  2. The right subtree of a node contains only nodes with keys greater than the node's key.
 *  3. Both the left and right subtrees must also be binary search trees.
 * 
 * Example 1:
 *    2
 *   / \
 *  1   3
 * Output: true
 * 
 * Example 2:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 * 
 * BST 中序遍历有序性：对 BST 做中序遍历得到的数组一定是升序排列的（反之亦然，有序数组一定可以构造为 BST）
 * 因此，判断是否满足 BST 只需要判断中序遍历的结果是否有序即可。
 * 
 * 基本思想
 *      - Inorder Traversal
 *      - DFS
 * 
 */

bool isValidBST(TreeNode* root) {
    vector<int> result;
    stack<TreeNode*> stack;
    TreeNode* curr = root;
    while (curr != nullptr || !stack.empty()) {
        if (curr != nullptr) {
            stack.push(curr);
            curr = curr->left;                // go left even if nullptr
        }
        else {
            curr = stack.top();
            stack.pop();
            if (result.size() != 0 && curr->val <= result.rbegin()[0])  // <当前值>必须大于<上个值>才能继续
                return false;
            result.push_back(curr->val);
            curr = curr->right;               // go right
        }
    }
    return true;
}

int main() {

    TreeNode* root = new TreeNode(10);
    root->left = new TreeNode(5);
    root->right = new TreeNode(15);
    root->left->right = new TreeNode(7);
    root->right->left = new TreeNode(13);
    std::cout << "Valid BST = " << isValidBST(root) << std::endl;
}