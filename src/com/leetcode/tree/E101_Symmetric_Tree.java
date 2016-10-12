package com.leetcode.tree;



import java.util.*;

/**
 * Created by LYuan on 2016/10/12.
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *          1
 *         / \
 *        2   2
 *       / \ / \
 *      3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 *          1
 *         / \
 *        2   2
 *        \   \
 *        3    3
 * Note: Bonus points if you could solve it both recursively and iteratively.
 *
 * Function Signature:
 * public boolean isSymmetric(TreeNode root) {...}
 */
public class E101_Symmetric_Tree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 2, 3, 4, 4, 3, 5});
        System.out.println(isSymmetric3(root));
    }

    // 另一个思路是先E226反转二叉树，然后再E100判同树。不过要扫描两遍。还需要额外的树空间。

    /** DFS + Stack: 递归解法 */
    // 难点在于需要构造一个递归函数可以比对任何两个节点是否对称，而不是仅仅比较同一个节点下面的两个子节点（那样无法验证镜像特性）
    static boolean isSymmetric2(TreeNode root) {
        if (root == null) return true;
        return compare(root.left, root.right);
    }

    static boolean compare(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;        // 筛选掉两者同为空的情况
        if (a == null || b == null) return false;       // 筛选掉两者之一为空的情况
        return a.val == b.val && compare(a.left, b.right) && compare(a.right, b.left);  // 两者只可能不为空
    }

    /** DFS + Stack: 迭代解法，需要两个栈，每个栈存一个分支节点 */
    // 之所以需要两个栈，是因为先写出来的递归解法入参有两个，因此每次递归调用时，都会压入这两个参数值进函数栈。
    // 所以我们只需要把他们手动实现就行了
    static boolean isSymmetric3(TreeNode root) {
        Deque<TreeNode> left = new ArrayDeque<>();
        Deque<TreeNode> right = new ArrayDeque<>();
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;
        left.push(root.left);
        right.push(root.right);
        while (!left.isEmpty() || !right.isEmpty()) {
            TreeNode l = left.pop();
            TreeNode r = right.pop();
            if (l.val != r.val) return false;
            if (l.left == null && r.right == null);
            else if (l.left == null || r.right == null) return false;
            else {
                left.push(l.left);
                right.push(r.right);
            }
            if (l.right == null && r.left == null);
            else if (l.right == null || r.left == null) return false;
            else {
                left.push(l.right);
                right.push(r.left);
            }
        }
        return left.size() == right.size();
    }

    /** BFS + Queue: Level Order Traversal 迭代解法 */
    // 本质是Level Order Traversal问题，只需要确保每一层都是对称的即可。
    // 难点：在于如何处理缺位的节点
    // 如果不处理，那么将会误判：例如有一层如果原本应有4个节点，但是实际是null,2,null,2，如果简化成[2, 2]则会以为是对称的。
    // 这里要注意，Deque是不接受null元素的，而List则可以接受null元素。
    static boolean isSymmetric(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root == null) return true;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.remove();
                if (current.val != Integer.MIN_VALUE) level.add(current.val);
                else {
                    level.add(Integer.MIN_VALUE);
                    continue;   // 及时终止补位节点的扩展
                }
                if (current.left != null) queue.add(current.left);
                else queue.add(new TreeNode(Integer.MIN_VALUE));
                if (current.right != null) queue.add(current.right);
                else queue.add(new TreeNode(Integer.MIN_VALUE));
            }
            for (int i = 0; i < level.size() / 2; i++)
                if ((int) level.get(i) != (int) level.get(level.size() - i - 1))    // 需要主动cast为基础类型才能用==比对。
                    return false;
        }
        return true;
    }
}
