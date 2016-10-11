package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/10/11.
 * Given two binary trees, write a function to check if they are equal or not.
 * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
 *
 * Function Signature:
 * public boolean isSameTree(TreeNode p, TreeNode q) {...}
 */
public class E100_Same_Tree {
    public static void main(String[] args) {
        TreeNode a = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6});
        TreeNode b = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 7});
        System.out.println(isSameTree2(a, b));
    }

    // DFS + Stack: 递归解法（正序递归，当前节点一致才有必要继续往下）
    // 注意如何一层层的筛选两个节点的状态：
    // p == null && q == null -> 筛选掉两者同时为null的情况，只剩下同时不为null，两者之一为null这两种情况
    // p == null || q == null -> 筛选掉两者之一为null的情况，只剩下同时不为null。
    // p != null && q != null -> 可以确保都不为null，直接访问val
    static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // DFS + Stack：迭代解法
    // 省略，本质上和下面的BFS + Queue的思路完全一样，只是把Queue换成Stack而已。

    // BFS + Queue: 迭代解法
    // 因为递归方法每次都传入两个节点的指针，因此迭代解法需要把这两个指针实体化为两个独立的队列。
    // 比较过程需要首先判定出队的两个元素值是否一样，如果一样，是否<同时没有>左右节点或<同时有>左右节点
    // 针对这种"同时怎么怎么地"的情况使用异或"^"运算是最为简洁的。
    static boolean isSameTree2(TreeNode p, TreeNode q) {
        Deque<TreeNode> queue1 = new ArrayDeque<>();
        Deque<TreeNode> queue2 = new ArrayDeque<>();
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        queue1.add(p);
        queue2.add(q);
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            TreeNode a = queue1.remove();
            TreeNode b = queue2.remove();
            if (a.val != b.val) return false;
            if (a.left == null ^ b.left == null) return false;      // XOR
            if (a.right == null ^ b.right == null) return false;    // XOR
            if (a.left != null) queue1.add(a.left);
            if (a.right != null) queue1.add(a.right);
            if (b.left != null) queue2.add(b.left);
            if (b.right != null) queue2.add(b.right);
        }
        return queue1.size() == queue2.size();
    }
}
