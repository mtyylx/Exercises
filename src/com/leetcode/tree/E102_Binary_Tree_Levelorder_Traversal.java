package com.leetcode.tree;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by LYuan on 2016/10/9.
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 *
 * For example: Given binary tree [3,9,20,null,null,15,7],
 *          3
 *         / \
 *        9  20
 *       / \
 *     15  7
 * return its level order traversal as:
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> levelOrder(TreeNode root) {...}
 */
public class E102_Binary_Tree_Levelorder_Traversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        List<List<Integer>> result = levelOrder2(root, 0);
    }

    // 广度优先BFS：迭代 + 自定义Queue辅助
    // 难点在如何分层的导出元素，因为队列是在每个循环中都会同时进出元素，
    // 解决方案是进入内循环前先搞清楚这一层有多少节点，都出去了这一层就可以结束了。
    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.remove();
                level.add(current.val);
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
            result.add(level);
        }
        return result;
    }

    // 深度优先DFS：递归解法（隐式使用函数堆栈）
    // 由于会不断深入，因此每次写入元素将不会在同一层，所以需要通过记录当前循环所处理节点的高度来访问对应索引的ArrayList。
    // 难点在于如何设计递归传入的参数和返回值：起码需要传递要处理的节点，以及这个节点所处的层高度。
    // 只要节点不为空，就先查当前的level是否已经有对应的存储空间创建了，如果没有就new一个，然后把节点值append
    // 由于是严格的按照先左后右的方式访问，所以虽然每一层的元素不是一次性填好的，但是顺序是没问题的。
    static List<List<Integer>> result = new ArrayList<>();
    static List<List<Integer>> levelOrder2(TreeNode root, int level) {
        if (root == null) return null;
        if (result.size() <= level) result.add(new ArrayList<>());
        result.get(level).add(root.val);
        levelOrder2(root.left, level + 1);
        levelOrder2(root.right, level + 1);
        return result;
    }

    // 尝试使用DFS的迭代写法加自己定义的栈，但是发现level不太好判断，因为会上下运动，不好判断level的增减。
}
