package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by LYuan on 2016/10/8.
 * Given a binary tree, return the IN-order traversal of its nodes' values. <二叉树的中序遍历>
 * For example: Given binary tree as below,
 *         4
 *       /  \
 *     2     5
 *    / \     \
 *   1  3      6
 * Inorder traversal should return [1,2,3,4,5,6].
 *
 * Function Signature:
 * public List<Integer> inorder(TreeNode root) {...}
 */
public class M94_Binary_Tree_Inorder_Traversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        List<Integer> result;
        result = inorder2(root);
        for (int x : result) System.out.print(x + ", ");
        result = inorder3(root);
        for (int x : result) System.out.print(x + ", ");
    }

    // 递归解法
    static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            result.addAll(inorder(root.left));
            result.add(root.val);
            result.addAll(inorder(root.right));
        }
        return result;
    }

    // 迭代解法
    /**  难点1：如何做到遍历完左节点才存当前结点？ */
    // 比前序遍历难的地方在于：root必须得在存入left节点之后才能存入。
    // 因此，并不能像前序遍历的while循环体那样，逐层记录并向下扩展，
    // 而是上来就一口气戳到左侧的最底部（while (current != null) {...}）
    // 直达左侧最底端节点为null的左子叶上（注意：不仅只到左侧最底端节点本身），向下移动的同时缓存当前节点（所以左侧最低端节点也会被压入栈中）
    // 之所以这里并不是current.left != null而是current != null，
    // 是因为这样可以确保while循环结束后出栈时得到的节点一定是当前分支最左侧的节点，就自动的覆盖了下面两种布局情况的处理：
    //               left-most                       left-most
    //               /       \                       /       \
    // current -> null       null       current -> null      right
    /** 难点2：如何确保循环持续运行并遍历所有节点？ */
    // 前序遍历只需要不断的先从栈中领任务，然后再往栈里压入新任务，然后循环往复直至栈空即可，因此循环条件很简单，只需要!stack.isEmpty()
    // 中序遍历的循环体中要做的不仅仅是从栈中领任务，而是需要先让current自由的向left移动，边移动边压栈，
    // 触底之后才从栈中取任务（根节点），把节点存入输出结果，再向right移动一步，然后就进入下一个循环的向left移动至触底，出栈，导出，向右，循环往复
    // 因此维持循环的条件不能仅仅看栈是否空了，还要看current是否null了，因为即使栈空了树可能依然没有扫完，考虑下面的情况
    //         0
    //           \
    //            4
    //              \
    //               9
    // 将0出栈后导出并将current指向4的时候，此时stack已经空了，但是仍然应该继续进入下一循环，不能终止
    // 这就是为什么外循环的循环条件是current != null || !stack.isEmpty()的原因。
    static List<Integer> inorder2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }
        return result;
    }

    // 上面写法的变体，将不断向左下移动的内嵌while循环改为if...else结构，打散由外循环while代替实现
    // 逻辑也相应的解释为：
    // 如果当前结点不为空，说明还能继续向左下移动，所以将当前结点压栈，并移动至其左子树（即使其左子树是null也移动）
    // 如果当前结点是空，说明触底，需要从栈中领取新的节点（这个节点可能是当前结点的父节点），导出新节点并访问其右子树
    static List<Integer> inorder3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            }
            else {
                current = stack.pop();
                result.add(current.val);
                current = current.right;
            }
        }
        return result;
    }
}

