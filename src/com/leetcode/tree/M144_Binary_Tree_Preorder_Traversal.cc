#include "../common.h"
#include <stack>

/**
 * Created by Michael on 2019/8/11.
 * 
 * Binary Tree Pre-order Traversal
 *      - Depth-First
 *      - root -> left -> right
 * 
 *         1
 *       /  \
 *     2     5
 *    / \     \
 *   3  4      6
 * 
 * Output as: 1, 2, 3, 4, 5, 6
 * 
 */

// Iterative + Stack 解法一
// 循环的设计：从栈中取节点，访问值，压右儿子，压左儿子。
// 
// 循环前将种子压栈：在循环开始前，把种子（root）压入栈中，循环内部根据情况把节点指针压栈或出栈，循环在栈空后终止。这样可以精简循环条件和循环内部的逻辑的设计。
// 迭代的终止条件：栈空。此时所有的有效节点都已经遍历。
// 压栈前 null-check：总是在压栈前做 null-check，包括在一开始检查 root 是否为空，省去在循环内检查，确保每次迭代开始出栈的元素都是有效的。
std::vector<int> preorder_iter(TreeNode* root) {
  std::vector<int> result;
  std::stack<TreeNode*> stack;
  if (root == nullptr) return result;
  stack.push(root);
  while (!stack.empty()) {
    TreeNode* curr = stack.top();     // 完善的 null-check 可以确保栈内元素一定有效，可以放心出栈。curr 是循环体内部的临时缓存，每次循环记忆都会清空。
    stack.pop();
    result.push_back(curr->val);      // root 1st. 这句话才是每次循环对结果的真正贡献。下面的压栈操作都是给后面循环做的准备而已，功能等效于函数调用栈。
    if (curr->right != nullptr) 
      stack.push(curr->right);        // right 3rd
    if (curr->left != nullptr) 
      stack.push(curr->left);         // left 2nd. 注意这里应该压栈，而不是直接让 curr = curr->left，那样会与循环体开始的出栈操作冲突。
  }
  return result;
}

// Iterative + Stack 解法二
// 循环的设计：如果节点不为空，就访问值，压右儿子，然后跳转至左儿子（至于左儿子是否存在不管）；如果节点为空，就出栈取新节点等下一轮。
//
// 循环前没有将种子压栈，这使得循环体内部不能以出栈开头。需要分情况讨论。
// 迭代的终止条件有两重：当前节点为空且栈也为空时。
std::vector<int> preorder_iter2(TreeNode* root) {
  std::vector<int> result;
  std::stack<TreeNode*> stack;
  while (root != nullptr || !stack.empty()) {
    if (root != nullptr) {
      result.push_back(root->val);
      if (root->right != nullptr) stack.push(root->right);
      root = root->left;
    } else {
      root = stack.top();
      stack.pop();
    }
  }
  return result;
}


// Recursion with global vector (or member variable if define class).
// Time ~ O(n), Space ~ O(n)
std::vector<int> vec;
std::vector<int> preorder_recurr (TreeNode* root) {
  if (root != nullptr) {      // only perform null check for current node, not its siblings.
    vec.push_back(root->val);
    preorder_recurr(root->left);
    preorder_recurr(root->right);
  }
  return vec;
}

// Recursion with helper func.
void preorder_helper(TreeNode* root, std::vector<int>& result) {
  if (root == nullptr) return;
  result.push_back(root->val);
  preorder_helper(root->left, result);
  preorder_helper(root->right, result);
}
std::vector<int> preorder_recurr2 (TreeNode* root) {
  std::vector<int> result;
  preorder_helper(root, result);
  return result;
}

int main() {
  TreeNode* root = new TreeNode(1);
  root->left = new TreeNode(2);
  root->right = new TreeNode(5);
  root->left->left = new TreeNode(3);
  root->left->right = new TreeNode(4);
  root->right->right = new TreeNode(6);
  
  std::vector<int> result = preorder_recurr(root);
  for (auto x : result) 
    std::cout << x << ", ";
  std::cout << std::endl;

  std::vector<int> result2 = preorder_recurr2(root);
  for (auto x : result2) 
    std::cout << x << ", ";
  std::cout << std::endl;

  std::vector<int> result3 = preorder_iter(root);
  for (auto x : result3) 
    std::cout << x << ", ";
  std::cout << std::endl;

  std::vector<int> result4 = preorder_iter2(root);
  for (auto x : result4) 
    std::cout << x << ", ";
  std::cout << std::endl;
}