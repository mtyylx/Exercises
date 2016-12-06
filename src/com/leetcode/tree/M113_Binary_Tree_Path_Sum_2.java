package com.leetcode.tree;

import org.omg.CORBA.INTERNAL;

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
        List<List<Integer>> result = preOrderPathSum(root, 8);
        System.out.println(postOrderTraversal(root));
        List<List<Integer>> result2 = postOrderPathSum(root, 8);
    }

    /** 关于 <实体树结构的问题> 和 <逻辑上具有树结构特征的问题> 在处理上是相同的！ */
    // “实体树结构”是指要解决的问题给的直接就是树状数据结构对象，每个节点都具有值/左右子树引用。
    // “逻辑上具有树结构特征”是指要解决的问题在逻辑上是一个树的扩展形态，例如Combination Sum系列中给定candidate数组，每选定一个candidate，就相当与开了一个分支节点。
    // 一开始我以为这两类问题不能用相同解法解决，现在上面的递归解法证明其实是可以的。
    // 反而<真正的难点>在于如何用迭代的写法解决这两类问题（实际是同一类问题）：
    // 因为递归的时候，函数栈的内容可以自动帮你打理好你在每一个节点时所匹配的这些context，例如当前结点下target的剩余值，以及路径状态（走了哪些点）
    // 但是迭代的时候，就没有人帮你在背后记录这些东西了，你只能靠自己定义的栈来获知当前节点的context，
    // 因为身在树林中的你是很难获悉自己在这个树中的确切位置的（指在代价很小的情况下获悉，代价很大的话当然就可以做到，但是算法本身效率就低了）

    // 下面是两种解法：Preorder + Recursive (Backtracking) / Postorder + Iterative

    /** 解法1：前序遍历（==回溯法），递归方式
     *  对于实体树结构的问题，<回溯法>和<前序遍历>是同一件事。 */
    // 相比于一般的逻辑上具有树结构的回溯路径增删法，这里需要小心处理叶子节点（即递归终止条件）原因如下：
    //      1     target = 1 应该没有解才对。这点要小心。因为1并不算叶子节点，只有抵达真正的叶子节点才能决定是否输出结果。
    //     /
    //    2
    // 因此应该一上来就检查当前节点是否为null。
    // 然后把当前结点就加入path中，并对其左右子节点的情况就事论事：
    // 1. 如果当前节点是叶子节点，那么检查target减当前结点值是否为0，是就存储result退出。
    // 2. 如果当前节点不是叶子节点，那么直接递归左右节点（即使没有也没关系，因为下一层递归方法一开始就会退出）
    // 然后把本层递归方法在一开始添加上的节点从path中移除。结束本层递归。
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

    // 上面的解法是<Backtracking>，结构形式与下面的标准前序遍历递归写法<Pre-order Traversal>完全一样。
    // 严格来说这两个算法的着眼点是不同的，回溯强调的是“我可以无限的前进和后退以至穷举所有可能”，而前序遍历强调的是“我要以先root后子节点的方式遍历树”。
    // 但是两者的作用是不约而同的。
    static List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preOrderTraversal(root, result);
        return result;
    }

    static void preOrderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preOrderTraversal(node.left, result);
        preOrderTraversal(node.right, result);
    }



    /** 解法2：后序遍历，迭代方式。 */
    // 在一开始试图用前序遍历的迭代写法解决，但是发现似乎并不能handle节点一次向上跳跃多次的情况。
    // 后序遍历的迭代解法之所以可以胜任，是因为它的逻辑分支可以区分出什么时候该往path里面添加节点，什么时候该从path里删除节点，完全可以处理跳跃的情况。
    // 什么时候往path里添加节点：只要current不为null，就先往path里添加。
    // 什么时候往path里删除节点：栈顶元素没有右分支，或者右分支已经访问过的时候，就可以删除path的尾端。
    static List<List<Integer>> postOrderPathSum(TreeNode root, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode prevTop = null;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                path.add(current.val);      // 更新路径
                target -= current.val;      // 更新target
                current = current.left;
            }
            TreeNode top = stack.peek();
            if (top.right == null || top.right == prevTop) {       // top要不然没有右分支，要不然右分支已经访问过。可以准备回头了。
                if (top.left == null && top.right == null && target == 0)   // top是叶子节点且target为0，说明找到一条path。
                    result.add(new ArrayList<>(path));
                // 不管top有没有左节点，target是不是0，都要回退，target要恢复，path也要回退。
                target += top.val;
                path.remove(path.size() - 1);
                prevTop = stack.pop();                             // 和后序遍历一样，缓存当前栈顶。
            }
            else current = top.right;                              // 接着访问top的右分支。
        }
        return result;
    }

    // 上面的解法使用的思路是后序遍历，参照下面标准后续遍历写法，可以看到两者的结构形式完全一样。
    static List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode prevTop = null;
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            }
            else {
                TreeNode top = stack.peek();
                if (top.right == null || top.right == prevTop) {
                    result.add(top.val);
                    prevTop = stack.pop();
                }
                else current = top.right;
            }
        }
        return result;
    }


    /** 解法3：DFS + Stack，迭代写法。用了三个栈，存访问节点 + 当前路径结果 + target值（和回溯递归解法根本没区别了，意义不大）*/
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
