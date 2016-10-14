package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by LYuan on 2016/10/13.
 * Given a binary tree, return all root-to-leaf paths.
 *
 * For example, given the following binary tree:
 *              1
 *            /   \
 *           2    3
 *           \
 *           5
 * All root-to-leaf paths are:
 * ["1->2->5", "1->3"]
 *
 * Function Signature:
 * public List<String> path(TreeNode root) {...}
 *
 * 关于二叉树的Path专题：核心要点就是<判断是否是叶子节点>，且对于只有一个子节点的节点应该特殊处理。
 * <E104 Maximum Depth>
 * <E111 Minimum Depth>
 * <E112 Path Sum>
 * <E257 Path>
 *
 */
public class E257_Binary_Tree_Path {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        List<String> result = treePaths5(root);
    }

    /** DFS + Stack: 递归 TopDown方式，path字符串自顶向下生长 */
    // 难点在于需要两个额外的变量，一个是存储最终结果的容器，一个是从根节点到当前节点的路径字符串。
    // 至于为什么入参是引用变量，但是依然压栈出栈不会相互影响呢？
    // 这是因为每次递归调用的时候，传入的都不是同一个字符串的引用，(path + "->" + root.left.val)隐式构造了一个新的字符串，
    // 因此实际压栈的时候，这个入参的地址和原来的path并不是一个地址，所以相互不会干扰。
    static List<String> result = new ArrayList<>();
    static List<String> treePaths(TreeNode root) {
        if (root == null) return result;
        paths(root, root.val + "");
        return result;
    }

    static void paths(TreeNode root, String path) {
        if (root == null) return;
        if (root.left == null && root.right == null) result.add(path);
        if (root.left != null) paths(root.left, path + "->" + root.left.val);
        if (root.right != null) paths(root.right, path + "->" + root.right.val);
    }

    /** DFS + Stack: 递归 BottomUp方式，path字符串自底向上生长 */
    // 缺点在于每次递归都会new新的数组。
    static List<String> treePaths4(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        if (root.left == null && root.right == null) {
            result.add(root.val + "");
            return result;
        }
        for (String path : treePaths4(root.left)) result.add(root.val + "->" + path);
        for (String path : treePaths4(root.right)) result.add(root.val + "->" + path);
        return result;
    }

    /** DFS + Stack: 递归 + StringBuilder解法 */
    // 难点在于如何恢复sb的状态，以避免清理不干净的结果。
    // 清理情况有两种：
    // 1. 遇到叶子节点并把path添加至result后，需要清理回退sb
    // 2. 在当前节点左右子树全部递归完毕后退出前，必须还原sb至添加当前节点之前的状态。
    static List<String> treePaths5(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        StringBuilder sb = new StringBuilder();
        getPath(root, result, sb);
        return result;
    }

    static void getPath(TreeNode root, List<String> result, StringBuilder sb) {
        if (root == null) return;
        int pos = sb.length();
        if (root.left == null && root.right == null) {
            sb.append(root.val);
            result.add(sb.toString());
            sb.delete(pos, sb.length());    // 清理：遇到叶子节点后要清理。
        }
        sb.append(root.val + "->");
        getPath(root.left, result, sb);
        getPath(root.right, result, sb);
        sb.delete(pos, sb.length());        // 清理：左右子树都记录完毕后要清理。
    }

    /** DFS + Stack: 迭代解法，双栈 */
    // 一个栈装节点，一个栈同步的装这个节点目前已有的path字符串。
    // 特别需要注意的地方是，因为把节点压栈的目的是为了下一个循环出栈，因此同步压入该节点对应的完整path时，就应该在字符串上同步添加上这个节点本身。
    // 而不是出栈的时候再在字符串中添加这个节点本身，否则第一个节点会重复输出。
    // 难点在于有些情况需要回退一个节点，有些情况则需要回退多个节点，这种情况下想要仅手动收缩StringBuilder是无法做到的。只能靠栈。
    //           1
    //          / \
    //         2   3
    //        / \   \
    //       4   5   6
    // [1 -> [3 2        -> [3 5 4                   -> [3    -> [6       -> [
    // [1 -> [1->3, 1->2 -> [1->3, 1->2->5, 1->2->4  -> [1->3 -> [1->3->6 -> [
    static List<String> treePaths2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<String> path = new ArrayDeque<>();
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        stack.push(root);
        path.push(Integer.toString(root.val));
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            String current_path = path.pop();
            if (current.left == null && current.right == null)
                result.add(current_path);
            if (current.right != null) {
                stack.push(current.right);
                path.push(current_path + "->" + current.right.val);
            }
            if (current.left != null) {
                stack.push(current.left);
                path.push(current_path + "->" + current.left.val);
            }
        }
        return result;
    }

    /** BFS + Queue: 迭代解法 */
    //           1
    //          / \
    //         2   3
    //        / \   \
    //       4   5   6
    // [1 -> [2 3        -> [3 4 5                   -> [4 5 6                      -> [
    // [1 -> [1->2, 1->3 -> [1->3, 1->2->4, 1->2->5  -> [1->2->5, 1->2->4, 1->3->6  -> [
    static List<String> treePaths3(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        Deque<String> path = new ArrayDeque<>();
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        queue.add(root);
        path.add(Integer.toString(root.val));
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            String current_path = path.remove();
            if (current.left == null && current.right == null)
                result.add(current_path);
            if (current.left != null) {
                queue.add(current.left);
                path.add(current_path + "->" + current.left.val);
            }
            if (current.right != null) {
                queue.add(current.right);
                path.add(current_path + "->" + current.right.val);
            }
        }
        return result;
    }
}
