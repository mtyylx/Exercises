package com.leetcode.tree;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by LYuan on 2016/10/8.
 * Given a binary tree, return the preorder traversal of its nodes' values. <二叉树的前序遍历>
 * For example: Given binary tree as below,
 *         1
 *       /  \
 *     2     5
 *    / \     \
 *   3  4      6
 * Preorder traversal should return [1,2,3,4,5,6].
 *
 * Function Signature:
 * public List<Integer> preorder(TreeNode root) {...}
 */
public class M144_Binary_Tree_Preorder_Traversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        List<Integer> preorder = preOrder4(root);
    }

    /** 二叉树的前、中、后序遍历都是针对root而言的。
     *  <前序>：先访问root
     *  <中序>：先访问左儿子，再访问root
     *  <后序>：先访问左儿子，再访问右儿子，再访问root */

    /** 二叉树不是线性存储结构，因此如果想要线性的串行（而非并行多线程）遍历二叉树，必然会在遍历的过程中缓存一部分节点。
     *  对于<递归方式>的遍历，节点缓存的工作被隐式的通过函数调用栈完成。
     *  对于<迭代方式>的遍历，则必须构造额外的栈或队列来辅助才能完成。*/

    // 递归解法1：每次递归都创建新List，返回时用addAll()方法将新List合并入最后的结果中。
    static List<Integer> preOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            result.add(root.val);
            result.addAll(preOrder(root.left));
            result.addAll(preOrder(root.right));
        }
        return result;
    }

    // 递归解法2：设立一个类的成员变量，游离于递归方法之外，避免每次递归都创建新List。
    static List<Integer> result = new ArrayList<>();
    static List<Integer> preOrder2(TreeNode root) {
        if (root != null) {
            result.add(root.val);
            preOrder2(root.left);
            preOrder2(root.right);
        }
        return result;
    }

    // 迭代解法：使用栈辅助
    // 注意：栈不能接受null元素。因此所有push前都要先做null check。
    static List<Integer> preOrder3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root == null) return result;
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val);    // 前序：访问子节点前先把根输出。
            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
        return result;
    }

    // 这是类似于Inorder和Postorder解法的if...else结构的Preorder版本，但是并没有上面的解法直观。
    static List<Integer> preOrder4(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                result.add(current.val);
                if (current.right != null) stack.push(current.right);
                current = current.left;
            }
            else  current = stack.pop();
        }
        return result;
    }
}
