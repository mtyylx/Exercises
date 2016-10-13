package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/10/13.
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 *
 * Function Signature:
 * public int minDepth(TreeNode root) {...}
 */
public class E111_Binary_Tree_Minimum_Depth {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
        System.out.println(minDepth(root));
        System.out.println(minDepth2(root));
    }

    /** DFS + Stack: 递归解法 */
    // 对比求最大深度和最小深度的解法，可以看到求最小深度需要避开一个坑，在E112中也要避开的坑：
    // 就是如果当前节点有一个子节点不存在，那么不能简单的返回两个子节点深度的最小值。因为仅仅缺少一个子节点，并不能够让当前节点成为叶子节点。
    // 话说回来还是那个道理，就是minimum depth = shortest path = path必须以叶子节点结尾。
    static int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return 1 + minDepth(root.right);     // 自动包含右节点也为null即叶子节点情况，此时高度为1.
        if (root.right == null) return 1 + minDepth(root.left);
        return 1 + Math.min(minDepth(root.left), minDepth(root.right)); // 走到这步已经排除了任何一个节点不存在的可能，所以可以安全求最小值了。
    }

    static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /** DFS + Stack: 迭代解法，使用双栈。 */
    static int minDepth2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> height = new ArrayDeque<>();
        if (root == null) return 0;
        stack.push(root);
        height.push(1);
        int min = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            int depth = height.pop();
            if (current.left == null && current.right == null) min = Math.min(depth, min);      // 仅在真的是叶子节点时才计算path长度。
            if (current.left != null) {
                stack.push(current.left);
                height.push(depth + 1);
            }
            if (current.right != null) {
                stack.push(current.right);
                height.push(depth + 1);
            }
        }
        return min;
    }

    /** BFS + Queue: 迭代解法，使用两个队列。 利用Level Order Traversal的优势。 */
    // 看上去似乎和DFS加Stack没什么两样，但是实际上却有优化的空间。
    // 由于是要找到最小的高度，因此BFS一层一层从上到下的过程特别适合做这个，
    // 因为BFS能够确保他找到的第一个叶子节点一定就是位置最高的那个叶子节点（同时也就是长度最短的一个path）
    // 所以当DFS在遇到了current.left == null && current.right == null这种叶子节点判定时，必须全都遍历一遍，因为深度优先导致他的顺序是乱序的
    // 但是对于BFS就不一样了，只要找到了叶子节点，直接return当前高度就OK了。
    // 所以说BFS好还是DFS好，关键要看问题的本质能够用上这两者之一的哪个特性，用哪个一定就快。
    static int minDepth3(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        Deque<Integer> height = new ArrayDeque<>();
        if (root == null) return 0;
        queue.add(root);
        height.add(1);
        int min = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            int depth = height.remove();
            if (current.left == null && current.right == null)
                return depth;                       // 直接返回结果，因为Level Order的话第一个叶子节点一定是最高位的。
                // min = Math.min(depth, min);      // 仅在真的是叶子节点时才计算path长度。
            if (current.left != null) {
                queue.add(current.left);
                height.add(depth + 1);
            }
            if (current.right != null) {
                queue.add(current.right);
                height.add(depth + 1);
            }
        }
        return min;
    }


}
