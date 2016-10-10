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

    /** BFS + Queue: Iterative. */
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

    /** 对比以下两个解法，可以发现，递归方法中设计多少个入参，相应的迭代方法就需要自定义多少栈 */
    // 每次递归都会传入当前结点以及当前结点高度这两个数据，所以看debug的函数堆栈信息可以看到这两个隐式存在的栈，
    // 因此使用迭代的话，就需要把这两个数据以实体栈的形式建立起来。

    /** DFS + Stack (Implicit): Recursive. 隐式使用函数堆栈。 */
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

    /** DFS + Stack (Explicit): Iterative. 双栈同进同出。*/
    // 显式定义两个栈，代替函数堆栈，一个栈负责缓存还没来得及的节点，一个栈负责同步缓存节点的深度
    // 比递归要难写。因为是深度优先，因此遍历过程中会先下后上好几次，而不是一层一层的访问节点，
    // 如果不单独维护每个节点的高度信息，那么当从栈中取节点时，你是没法知道这个节点是在几层的，
    // 因此能够做的就是使用两个栈，然后确保这两个栈总是同时压栈同时出栈的，
    // 由于压栈的时候，你一定是知道所压节点的高度的，所以出栈时就可以根据同时出栈的高度把节点信息导出至高度对应的list中。
    // 受Maximum Depth题的启发。
    static List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> level = new ArrayDeque<>();
        if (root == null) return result;
        stack.push(root);
        level.push(0);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            int height = level.pop();
            if (result.size() <= height)
                result.add(new ArrayList<>());
            result.get(height).add(current.val);
            if (current.right != null) {
                stack.push(current.right);
                level.push(height + 1);
            }
            if (current.left != null) {
                stack.push(current.left);
                level.push(height + 1);
            }
        }
        return result;
    }
}
