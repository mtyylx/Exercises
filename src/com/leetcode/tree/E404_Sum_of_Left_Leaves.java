package com.leetcode.tree;

/**
 * Created by Michael on 2016/11/9.
 * Find the sum of all left leaves in a given binary tree.
 *
 * Example:
 *
 *      3
 *     / \
 *    9  20
 *      /  \
 *     15   7
 *
 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
 *
 * Function Signature:
 * public int sumLeftLeaves(TreeNode root) {...}
 */
public class E404_Sum_of_Left_Leaves {
    public static void main(String[] args) {

    }


    static int sumLeftLeaves(TreeNode root, boolean left) {
        int sum = 0;
        while (root != null) {
            if (root.left != null) sum += sumLeftLeaves(root.left, true);
            if (root.right != null) sum += sumLeftLeaves(root.right, false);

        }
    }
}
