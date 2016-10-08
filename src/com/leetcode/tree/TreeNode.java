package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/10/8.
 *
 * Defined for all BinaryTree questions.
 *
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }

    // For the given array, generate a tree in level order.
    public static TreeNode Generator(int[] a) {
        if (a == null || a.length == 0) return null;
        TreeNode root = new TreeNode(a[0]);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 0;
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            if (++i < a.length) current.left = new TreeNode(a[i]);
            else break;
            if (++i < a.length) current.right = new TreeNode(a[i]);
            else break;
            queue.add(current.left);
            queue.add(current.right);
        }
        return root;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode root = TreeNode.Generator(a);
    }
}
