package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Michael on 2016/10/9.
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 * Function Signature:
 * public int depth(TreeNode root) {...}
 */
public class E104_Binary_Tree_Maximum_Depth {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        System.out.println(depth(root));
    }

    // DFS + Stack: Recursive
    static int depth(TreeNode root) {
        return (root == null) ? 0 : 1 + Math.max(depth(root.left), depth(root.right));
    }

    // BFS + Queue: Iterative.
    // 跟广度优先的Level Traversal解法一样，只是不用记录节点而已。但是解法看上去很臃肿。
    static int depth2(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        int level = 0;
        if (root == null) return level;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.remove();
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
        }
        return level;
    }


    // 一不小心写成了计算二叉树节点个数的方法
    static int count = 0;
    static int count(TreeNode root) {
        if (root == null) return count;
        count++;
        count(root.left);
        count(root.right);
        return count;
    }
}
