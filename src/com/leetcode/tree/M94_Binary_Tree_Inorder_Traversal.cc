#include "../common.h"
#include <stack>
using namespace std;

// trick #1: no seed in stack before while loop
// trick #2: loop condition
// trick #3: go straight to left bottom, make curr reach nullptr. (stack should not contain nullptr, but curr can.)
vector<int> inorder(TreeNode* root) {
    vector<int> result;
    if (root == nullptr) return result;
    stack<TreeNode*> stack;
    TreeNode* curr = root;
    while (curr != nullptr || !stack.empty()) {
        if (curr == nullptr) {
            curr = stack.top();             // guarantee to be curr's parent or predecessor.
            stack.pop();
            result.push_back(curr->val);    // visit parent
            curr = curr->right;             // go right
        }
        else {
            stack.push(curr);
            curr = curr->left;  // go left even if left is nullptr
        }
    }
    return result;
}

// use while instead of if-else
vector<int> inorder2(TreeNode* root) {
    vector<int> result;
    if (root == nullptr) return result;
    stack<TreeNode*> stack;
    TreeNode* curr = root;
    while (curr != nullptr || !stack.empty()) {
        while (curr != nullptr) {
            stack.push(curr);
            curr = curr->left;
        }
        curr = stack.top();             // gurantee to be curr's parent
        stack.pop();
        result.push_back(curr->val);    // visit parent
        curr = curr->right;             // go right
    }
    return result;
}

// 错误：过早检查
vector<int> inorder_bad(TreeNode* root) {
    vector<int> result;
    if (root == nullptr) return result;
    stack<TreeNode*> stack;
    TreeNode* curr = root;
    while (curr != nullptr || !stack.empty()) {
        if (curr->left == nullptr) {        // check too early
            result.push_back(curr->val);    // cause infinite loop
            curr = stack.top();
            stack.pop();
        }
        else {
            stack.push(curr);   // push 
            curr = curr->left;  // go left
        }
    }
    return result;
}

vector<int> preorder(TreeNode* root) {
    vector<int> result;
    if (root == nullptr) return result;
    stack<TreeNode*> stack;
    stack.push(root);
    while (!stack.empty()) {
        TreeNode* curr = stack.top();
        stack.pop();
        result.push_back(curr->val);
        if (curr->right != nullptr) stack.push(curr->right);
        if (curr->left != nullptr) stack.push(curr->left);
    }
    return result;
}

int main() {

    TreeNode* root = new TreeNode(1);
    root->left = new TreeNode(2);
    root->right = new TreeNode(3);
    root->left->left = new TreeNode(4);
    root->left->right = new TreeNode(5);
    root->right->right = new TreeNode(6);
    vector<int> res = preorder(root);
    vector<int> res2 = inorder(root);
    print(res);
    print(res2);
}


/*
   
        a
      /  \      a, 压栈 a, b, 压栈 b, X1, 出栈 b (b 是 X1 的 parent)
     b   c
   /  \
  X1  X2

  
*/