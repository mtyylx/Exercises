package com.leetcode.tree;

import sun.reflect.generics.tree.Tree;

import java.util.*;

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
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5});
        System.out.println(preOrderTraversal(root));
        List<List<Integer>> result2 = preOrderPathSum(root, 8);

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
    static List<List<Integer>> preOrderPathSum(TreeNode root, int target) {
        List<List<Integer>> result = new ArrayList<>();
        preOrderPathSum(root, target, result, new ArrayList<>());
        return result;
    }

    static void preOrderPathSum(TreeNode node, int target, List<List<Integer>> result, List<Integer> current) {
        if (node == null) return;
        current.add(node.val);
        target -= node.val;
        if (node.left == null && node.right == null && target == 0) result.add(new ArrayList<>(current));
        preOrderPathSum(node.left, target, result, current);
        preOrderPathSum(node.right, target, result, current);
        current.remove(current.size() - 1);
    }

    static List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preOrderTraversal(root, result);
        return result;
    }

    static void preOrderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.val);
            preOrderTraversal(node.left, result);
            preOrderTraversal(node.right, result);
        }
    }

    /** 关于 <实体树结构的问题> 和 <逻辑上具有树结构特征的问题> 在处理上的不同 */
    // “实体树结构”是指要解决的问题给的直接就是树状数据结构对象，每个节点都具有值/左右子树引用。
    // “逻辑上具有树结构特征”是指要解决的问题在逻辑上是一个树的扩展形态，例如Combination Sum系列中给定candidate数组，每选定一个candidate，就相当与开了一个分支节点。
    // 虽然这两类问题都涉及到树的结构，但是处理上并不完全一样。实体树结构的问题往往需要更小心的处理，以避免出现回退跳跃的情况。
    // 回退跳跃：指使用堆栈

    // 如果不是真实的树结构（即只是逻辑上的树，而不是已经实体化为树节点的树），那么可以放心的以步长为1的增和删节点，因为回溯不会出现跳跃。
    // 但是如果是真实的树结构，那么将会出现跳跃的情况，因为你不知道下次取出来的节点离上个节点多远。需要检查path的最后一个节点是否是当前节点的父节点。

    /** 按理说任何树的问题都应该可以用遍历的四种方式之一来解决。
     * 这里用Preorder合适，但难点是如何伸缩当前记录的path，使得既保证不同分支间的path是互不影响的，又保证结果和path之间不相互影响。 */
    // 只要检测到当前的节点是叶子节点，不管匹配不匹配target，都在结束的时候把路径缩短1.
    // 遍历树的全过程中只使用一个path，path保存的是节点对象
    // 只有遇到叶子节点的时候，才首先检查是否target达到了0
    // 然后再peek栈顶的节点（即下一个即将出栈的节点），然后不断的删除path，直至path的最后一个节点是下一个即将出栈节点的父节点。



    /** DFS + Stack，迭代写法。用了三个栈，存访问节点 + 当前路径结果 + target值（和递归解法根本没区别了，没有意义）*/
    static List<List<Integer>> pathSum2(TreeNode root, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<List<Integer>> path = new ArrayDeque<>();
        Deque<Integer> sum = new ArrayDeque<>();
        path.push(new ArrayList<>(Arrays.asList(root.val)));
        stack.push(root);
        sum.push(target - root.val);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            List<Integer> current = new ArrayList<>(path.pop());
            int new_target = sum.pop();
            if (node.left == null && node.right == null && new_target == 0)
                result.add(new ArrayList<>(current));
            if (node.left != null) {
                current.add(node.left.val);
                stack.push(node.left);
                path.push(new ArrayList<>(current));
                sum.push(new_target - node.left.val);
                current.remove(current.size() - 1);
            }
            if (node.right != null) {
                current.add(node.right.val);
                stack.push(node.right);
                sum.push(new_target - node.right.val);
                path.push(new ArrayList<>(current));
            }
        }
        return result;
    }
}
