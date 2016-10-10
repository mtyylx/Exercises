package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/10/10.
 * Invert a binary tree.
 *         4
 *       /   \
 *      2     7
 *     / \   / \
 *    1   3 6   9
 *    to
 *          4
 *        /   \
 *       7     2
 *      / \   / \
 *     9   6 3   1
 * Trivia: This problem was inspired by this original tweet by Max Howell:
 * Google: 90% of our engineers use the software you wrote (Homebrew),
 * but you can’t invert a binary tree on a whiteboard so fuck off.
 *
 * Function Signature:
 * public TreeNode invertTree(TreeNode root) {...}
 */
public class E226_Invert_Binary_Tree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        root = invertTree2(root);
    }

    // 由于题目只涉及到交换节点的左右子节点，因此其实任何顺序遍历树的节点都是可以的
    // 这就是使用DFS和BFS都可以的前提。

    /** DFS + Stack: Recursive */
    // 逆序递归（前序遍历root-left-right）、正序递归（后序遍历left-right-root）都可以
    // 先对子树进行树反转，等两个子树都反转完了，再反转当前节点的左右节点。
    static TreeNode invertTree(TreeNode root) {
        if (root == null) return root;
        //
        invertTree(root.left);
        invertTree(root.right);

        TreeNode temp = root.left;
        root.left = root.right;3
        root.right = temp;

        return root;
    }

    /** DFS + Stack: Iterative */
    // 其实任何树的问题都是某种形式的遍历，这就是为什么先拿下前序、中序、后续、层序遍历的递归和迭代解法这么重要的原因。
    // 比如交换节点的这道题，其实本质上就是前序遍历，先访问当前节点，然后再访问其左右子树，
    // 额外做的只是在访问当前节点的同时把这个节点的左右指针地址交换即可。
    static TreeNode invertTree2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root == null) return root;
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }
        return root;
    }

    /** BFS + Queue: Iterative */
    // 使用queue可以确保出队的顺序是按照树的高度一层一层的扫描的，
    // 不过其实对于交换左右子树的问题上，你是按层扫描还是胡乱扫描，根本无所谓。
    static TreeNode invertTree3(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root == null) return root;
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        return root;
    }
}
