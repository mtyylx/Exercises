package com.leetcode.tree;

/**
 * Created by LYuan on 2016/10/12.
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 * According to the definition of LCA on Wikipedia:
 * “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that
 * has both v and w as descendants (where we allow a node to be a descendant of itself).”
 * BST: 二叉搜索树的性质是，任何节点的值都大于其左子树的任何元素，都小于其右子树的任何元素，且没有任何重复元素。
 *              _______6______
 *             /              \
 *         ___2__          ___8__
 *        /      \        /      \
 *       0       4       7       9
 *     /  \
 *    3   5
 * For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6.
 * Another example is LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
 *
 * Function Signature:
 * public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {...}
 *
 */
public class E235_Lowest_Common_Ancestor_of_BST {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {3, 1, 5, 0, 2, 4, 6});    // Input must be BST.
        TreeNode ancestor = lowestCommonAncestor3(root, new TreeNode(5), new TreeNode(6));
    }

    // 迭代解法: time - o(logn)
    static TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (p.val < root.val && q.val < root.val) root = root.left;
            else if (p.val > root.val && q.val > root.val) root = root.right;
            else return root;
        }
        return null;
    }

    // 正序递归：精简版
    static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) return lowestCommonAncestor2(root.left, p, q);
        if (p.val > root.val && q.val > root.val) return lowestCommonAncestor2(root.right, p, q);
        return root;
    }

    // 正序递归
    static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) return null;
        if (p.val <= root.val && q.val >= root.val || p.val >= root.val && q.val <= root.val) return root;
        if (p.val < root.val && q.val < root.val) return lowestCommonAncestor(root.left, p, q);
        else return lowestCommonAncestor(root.right, p, q);
    }
}
