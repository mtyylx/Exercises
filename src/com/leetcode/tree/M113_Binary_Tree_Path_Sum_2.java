package com.leetcode.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Michael on 2016/12/1.
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 *
 * For example:
 * Given the below binary tree and sum = 22,
 *            5
 *          / \
 *         4   8
 *        /   / \
 *       11  13  4
 *      /  \    / \
 *    7    2  5   1
 * return
 * [
 *  [5,4,11,2],
 *  [5,8,4,5]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> pathSum(TreeNode root, int target) {...}
 *
 * 系列问题：
 * E112 Path Sum 1: 给定树和目标值，<判断是否存在>树根一直到叶子节点之和为目标值的路径。
 * M113 Path Sum 2: 给定树和目标值，求解树根一直到叶子节点之和等于目标值的<所有路径>。
 * E437 Path Sum 3: xxxxxxxxxxx
 *
 */
public class M113_Binary_Tree_Path_Sum_2 {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {5, 4, 8, 11, 19, 13, 4, 7, 2, 99, 99, 99, 99, 5, 1});
        List<List<Integer>> result = pathSum(root, 22);
    }

    /** DFS, 递归写法，感觉很像回溯法常用的路径增删法。让我试一下。 */
    // 相比普通的回溯路径增删法，这里需要小心处理叶子节点（即递归终止条件）原因如下：
    //      1     target = 1 应该没有解才对。这点要小心。因为1并不算叶子节点，只有抵达真正的叶子节点才能决定是否输出结果。
    //     /
    //    2
    // 所以处理节点的原则是：
    // 首先在递归方法外确保入口节点是有效的
    // 然后在递归方法内：首尾分别增删当前节点值，在此基础上对当前结点的情况就事论事：
    // 1. 如果当前节点是叶子节点，那么检查target减当前结点值是否为0，是就存储result退出。
    // 2. 如果当前节点不是叶子节点，那么仅对他真正有的儿子进行递归。确保不会递归null节点即可。
    static List<List<Integer>> pathSum(TreeNode root, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        backtrack(root, target, result, new ArrayList<>());
        return result;
    }

    static void backtrack(TreeNode node, int target, List<List<Integer>> result, List<Integer> current) {
        current.add(node.val);                      // 路径增
        // 当前节点为叶子结点
        if (node.left == null && node.right == null)
            if (target - node.val == 0) result.add(new ArrayList<>(current));
        // 当前节点不是叶子节点
        if (node.left != null) backtrack(node.left, target - node.val, result, current);
        if (node.right != null) backtrack(node.right, target - node.val, result, current);
        current.remove(current.size() - 1);   // 路径删
    }
}
