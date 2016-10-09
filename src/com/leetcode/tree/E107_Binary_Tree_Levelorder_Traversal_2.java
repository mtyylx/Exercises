package com.leetcode.tree;

import java.util.*;

/**
 * Created by Michael on 2016/10/9.
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values.
 * (ie, from left to right, level by level).
 *
 * For example: Given binary tree [3,9,20,null,null,15,7],
 *          3
 *         / \
 *        9  20
 *       / \
 *     15  7
 * return its level order traversal as:
 * [
 *   [15,7]
 *   [9,20],
 *   [3],
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> reverseLevelOrder(TreeNode root) {...}
 */
public class E107_Binary_Tree_Levelorder_Traversal_2 {
    public static void main(String[] args) {

    }

    // 真的和E102是一样的，如果用ArrayList的话只需要最后reverse一下，如果用LinkedList的话什么都不用改。

    // BFS + Queue
    static List<List<Integer>> reverseLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root == null) return result;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.remove();
                level.add(current.val);
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
            result.add(0, level);       // 只改这一行或最后加一行Collections.reverse(result);
        }
        return result;
    }

    // DFS + Stack (实际是Recursive): 如果不在最后reverse，就需要在递归部分在表头插入添加新list，然后先访问子节点后再求当前level的补值。
    static List<List<Integer>> reverseLevelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        levelOrderRecursive(root, 0, result);
        Collections.reverse(result);
        return result;
    }

    static void levelOrderRecursive(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) return;
        if (result.size() <= level) result.add(0, new ArrayList<>());
        levelOrderRecursive(root.left, level + 1, result);
        levelOrderRecursive(root.right, level + 1, result);
        result.get(result.size() - level - 1).add(root.val);
    }
}
