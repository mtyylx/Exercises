package com.leetcode.tree;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayDeque;
import java.util.Deque;

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
 * 注意这里只记录左叶子节点，也就是说，首先，他得是叶子节点，其次，在要在左边。
 *
 * Function Signature:
 * public int sumLeftLeaves(TreeNode root) {...}
 */
public class E404_Sum_of_Left_Leaves {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5});
        System.out.println(sumLeftLeaves(root));
        System.out.println(sumLeftLeaves2(root));
        System.out.println(sumLeftLeaves3(root));
    }

    /** 迭代 + 栈 */
    // 逻辑完全一样，只不过压栈前需要判空。
    static int sumLeftLeaves3(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        int sum = 0;
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            if (current.left != null && current.left.left == null && current.left.right == null) sum += current.left.val;
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }
        return sum;
    }

    /** 递归解法2，直接解决 */
    // 由于已经有递归终止条件的保护，因此我们可以专门寻找左节点存在且左节点是叶子节点的情况，记录左节点的值。
    static int sumLeftLeaves2(TreeNode root) {
        if (root == null) return 0;
        int sum = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) sum += root.left.val;
        sum += sumLeftLeaves2(root.left) + sumLeftLeaves2(root.right);
        return sum;
    }

    /** 递归解法1，需要辅助方法 */
    // 判断是否要记录节点值的依据是：该节点没有左右孩子 + 该节点在左分支上
    // 所以最简单的思路就是直接在递归的时候告诉下面的节点自己处于左分支还是右分支。
    // 递归终止条件：节点本身为空。
    static int sumLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        return sumLeftRecursive(root.left, true) + sumLeftRecursive(root.right, false);
    }

    static int sumLeftRecursive(TreeNode root, boolean left) {
        if (root == null) return 0;
        int sum = sumLeftRecursive(root.left, true) + sumLeftRecursive(root.right, false);
        if (root.left == null && root.right == null && left) return sum + root.val;         // 只有在当前结点是左叶子时才累加
        return sum;
    }
}
