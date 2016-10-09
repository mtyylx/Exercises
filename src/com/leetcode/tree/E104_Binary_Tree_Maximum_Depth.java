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
        System.out.println(depth2(root));
    }

    // DFS + Stack: Recursive
    static int depth(TreeNode root) {
        return (root == null) ? 0 : 1 + Math.max(depth(root.left), depth(root.right));
    }

    // DFS + Stack: Iterative
    // 使用两个栈，一个栈存节点，一个栈存这个节点对应的高度，
    // 然后确保两个栈永远同时入栈和出栈，就可以做到保持节点和其高度一直一一对应
    // 实际上这个过程就等效于手动实现了DFS递归写法函数栈的功能。
    static int depth2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> depth = new ArrayDeque<>();
        if (root == null) return 0;
        stack.push(root);
        depth.push(1);
        int max = 0;
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();     // 取节点
            int level = depth.pop();            // 取节点的高度
            max = Math.max(max, level);         // 更新全局最大值
            if (current.right != null) {
                stack.push(current.right);      // 记录右节点
                depth.push(level + 1);          // 同时记录其高度
            }
            if (current.left != null) {
                stack.push(current.left);       // 记录左节点
                depth.push(level + 1);          // 同时记录其高度
            }
        }
        return max;
    }

    // BFS + Queue: Iterative.
    // 跟广度优先的Level Traversal解法一样，只是不用记录节点而已。比DFS的迭代方法效率要高
    static int depth3(TreeNode root) {
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
