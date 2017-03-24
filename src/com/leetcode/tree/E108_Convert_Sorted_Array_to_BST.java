package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Michael on 2017/3/23.
 *
 * Given an array where elements are sorted in ascending order,
 * Convert it to a height balanced BST.
 *
 * Function Signature:
 * public TreeNode arr2BST(int[] a) {...}
 *
 */
public class E108_Convert_Sorted_Array_to_BST {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        TreeNode res = array2BST(a);

        TreeNode res2 = array2BST2(a);
    }

    /** 解法1：递归解法。Time - o(n), Space - o(n). */
    // Binary Search的思想：定位当前区间内位于中心的元素作为根，递归搜索该元素左右区间内的中点作为左右子树。
    // 注意这里的中间节点位置靠左，因此生成的树都是有右子树但没有左子树的
    // [1, 2, 3, 4, 5]        中间节点位置偏左           中间节点位置偏右
    //                              3                      3
    //                            /   \                  /   \
    //                           1     4                2     5
    //                            \     \              /     /
    //                             2     5            1     4
    // 可以看到左子树分支是只有右儿子没有左儿子的。
    static TreeNode array2BST(int[] a) {
        if (a == null || a.length == 0) return null;
        return array2BST2(a, 0, a.length - 1);       // Inclusive
    }

    // 中间节点位置偏左，生成树偏右
    static TreeNode array2BST(int[] a, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(a[mid]);
        root.left = array2BST(a, left, mid - 1);
        root.right = array2BST(a, mid + 1, right);
        return root;
    }

    // 中间节点位置偏右，生成树偏左（更符合二叉树的一般形式）
    // 只需要额外处理区间长度是偶数的中点向右偏移一位即可
    static TreeNode array2BST2(int[] a, int left, int right) {
        if (left > right) return null;
        int mid = (right - left) % 2 == 0 ? left + (right - left) / 2 : left + (right - left) / 2 + 1;
        TreeNode root = new TreeNode(a[mid]);
        root.left = array2BST(a, left, mid - 1);
        root.right = array2BST(a, mid + 1, right);
        return root;
    }

    /** 解法2：迭代解法 + 栈。Time - o(n), Space - o(logn) */
    // 本质上完全等效于递归解法，这里只不过将递归的函数栈显式的定义出来称为一个对象压入栈中而已。
    static TreeNode array2BST2(int[] a) {
        if (a == null || a.length == 0) return null;
        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        BinaryTreeNode root = new BinaryTreeNode(0, a.length - 1, new TreeNode(0));
        stack.push(root);
        while (!stack.isEmpty()) {
            BinaryTreeNode curr = stack.pop();
            int mid = curr.lower + (curr.upper - curr.lower) / 2;
            curr.root.val = a[mid];
            if (curr.lower < mid) {
                TreeNode x = new TreeNode(0);
                curr.root.left = x;
                stack.push(new BinaryTreeNode(curr.lower, mid - 1, x));
            }
            if (curr.upper > mid) {
                TreeNode y = new TreeNode(0);
                curr.root.right = y;
                stack.push(new BinaryTreeNode(mid + 1, curr.upper, y));
            }
        }
        return root.root;
    }
}

class BinaryTreeNode {
    int lower;
    int upper;
    TreeNode root;
    public BinaryTreeNode(int l, int r, TreeNode n) {
        lower = l;
        upper = r;
        root = n;
    }
}
