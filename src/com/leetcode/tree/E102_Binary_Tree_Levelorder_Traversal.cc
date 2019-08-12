#include "../common.h"
#include <queue>
#include <stack>

/**
 * Created by Michael on 2019/8/12.
 * 
 * Binary Tree Level Order Traversal: From Left to Right, Top to Bottom.
 * 
 *          1
 *         / \
 *        2  3
 *       / \  \
 *      4  5  6
 * 
 * Output as [1, 2, 3, 4, 5, 6] is easy
 * 
 * Output as [[1], [2, 3], [4, 5, 6]] requires tactics.
 * 
 */

// BFS + Queue + Iterative
// 涉及到队列，一定要善用队列长度这个特性。
// 难点：如何把不同层的数据放到不同的容器中？
// 解决方法1：双循环，外循环遍历层数，内循环遍历层内所有元素。内循环连续出队一层元素，并同时连续入队下一层所有元素。利用特性：队列的长度就是这层元素的个数。
// 解决方法2：再定义一个队列记录每个节点的深度，两个队列同时出入队。缺点是空间复杂度加倍。
std::vector<std::vector<int>> level_order (TreeNode* root) {
  std::vector<std::vector<int>> result;
  std::queue<TreeNode*> queue;
  if (root == nullptr) return result;
  queue.push(root);
  while (!queue.empty()) {                                      // 外循环遍历每一个level
    int num_nodes_per_level = queue.size();                     // 外循环开始时，队列的长度就是这一层所含的节点个数
    std::vector<int> data;                                      // 本层数据容器
    for (int i = 0; i < num_nodes_per_level; i++) {             // 内循环持续出队+入队操作，出队出的是本层节点，入队入的是下层节点。
      TreeNode* curr = queue.front();
      queue.pop();
      data.push_back(curr->val);
      if (curr->left != nullptr) queue.push(curr->left);        // 入队顺序是先左后右，与 DFS Preorder 先右后左不同
      if (curr->right != nullptr) queue.push(curr->right);
    }
    result.push_back(data);
  }
  return result;
}

// DFS + Stack + Iterative
// 这是一种拓展思路的方法，虽然复杂度不是最优，但是和 BFS + Queue 是个对偶的问题。
// 用深度优先的方法去解广度优先的问题，看上去很矛盾，实际上思路很简单：虽然我是跳层访问节点，不是按照一层一层的方式顺序访问，但是只要我知道我在哪层，我访问节点的时候就<知道该把节点值存到哪层的容器>里。
// 考虑到 DFS 天然就会在遍历树的过程中频繁的上下移动，这导致很难随时都知道当前节点的所在深度，因此需要额外的数据结构来跟踪深度信息，确保不同层的数据能够保存在不同容器中。
// 实现起来就是双栈，一个栈放节点，一个栈放节点所在深度。这个 BFS + Queue 用双队列是一样的。
std::vector<std::vector<int>> level_order2 (TreeNode* root) {
  std::vector<std::vector<int>> result;
  std::stack<TreeNode*> node;
  std::stack<int> depth;
  if (root == nullptr) return result;
  node.push(root);
  depth.push(0);
  while (!node.empty()) {
    TreeNode* curr = node.top();
    int level = depth.top();
    node.pop();
    depth.pop();
    if (result.size() <= level)                      // 无法预知树的深度，需要随时扩充容器，容器元素会自动初始化。
      result.resize(level + 1);
    result.at(level).push_back(curr->val);           // 把节点值保存到<对应深度>的容器中
    if (curr->right != nullptr) {                    // 正常 DFS Preorder，先右后左
      node.push(curr->right);
      depth.push(level + 1);                         // 双栈同时压栈
    }
    if (curr->left != nullptr) {
      node.push(curr->left);
      depth.push(level + 1);
    }
  }
  return result;
}

