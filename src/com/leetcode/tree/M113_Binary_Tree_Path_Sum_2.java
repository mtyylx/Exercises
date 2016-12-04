package com.leetcode.tree;

import sun.reflect.generics.tree.Tree;

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
        List<List<Integer>> result2 = preOrderPathSum(root, 8);

    }

    /** DFS, 递归写法，感觉很像回溯法常用的路径增删法。让我试一下。 */
    // 相比普通的回溯路径增删法，这里需要小心处理叶子节点（即递归终止条件）原因如下：
    //      1     target = 1 应该没有解才对。这点要小心。因为1并不算叶子节点，只有抵达真正的叶子节点才能决定是否输出结果。
    //     /
    //    2
    // 所以递归方法中的原则是：
    // 一上来就检查当前节点是否为null。
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

    /** 关于回溯法与前序遍历法的对比分析：上面的解法是<Backtracking>，下面的解法则是递归写法的标准<Pre-order Traversal> */
    // 可以看到，两个算法的形式是极为相似的，虽然他们俩解决的具体问题不完全一样，但是思路是完全一样的。
    // 所以说，其实Backtracking解法使用在实体树问题上就是前序遍历！！！
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

    /** 关于 <实体树结构的问题> 和 <逻辑上具有树结构特征的问题> 在处理上是相同的！ */
    // “实体树结构”是指要解决的问题给的直接就是树状数据结构对象，每个节点都具有值/左右子树引用。
    // “逻辑上具有树结构特征”是指要解决的问题在逻辑上是一个树的扩展形态，例如Combination Sum系列中给定candidate数组，每选定一个candidate，就相当与开了一个分支节点。
    // 一开始我以为这两类问题不能用相同解法解决，现在上面的递归解法证明其实是可以的。
    // 反而<真正的难点>在于如何用迭代的写法解决这两类问题（实际是同一类问题）：
    // 因为递归的时候，函数栈的内容可以自动帮你打理好你在每一个节点时所匹配的这些context，例如当前结点下target的剩余值，以及路径状态（走了哪些点）
    // 但是迭代的时候，就没有人帮你在背后记录这些东西了，你只能靠自己定义的栈来获知当前节点的context，
    // 因为身在树林中的你是很难获悉自己在这个树中的确切位置的（指在代价很小的情况下获悉，代价很大的话当然就可以做到，但是算法本身效率就低了）

    /** Post-order Traversal */
    static List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode prev = null;
        TreeNode current = root;

        while (current != null || stack.isEmpty()) {    // 双循环条件：要不然一开始都进不来循环，没有初始的种子压栈。
            if (current != null) {
                stack.push(current);        // 不管右节点
                current = current.left;     // 直接深入左节点
            }
            else {
                TreeNode peek = stack.peek();
                if (peek.right == null || peek.right == prev) {
                    result.add(peek.val);
                    prev = stack.pop();
                }
                else current = peek.right;
            }
        }
        return result;
    }

    /** Case 1 */
    //       1
    //      / \
    //  null   null
    //    ↑
    //  current目前位于节点1的左子叶节点（并不存在）。节点1是一个纯粹的叶子节点。
    //  [应对策略] 将节点1出栈，并访问节点1。
    //
    /** Case 2 */
    //       1
    //      / \
    //  null   2
    //    ↑
    //  current目前位于节点1的左子叶节点（并不存在）。节点1存在右叶子节点。
    //  节点1本身不是叶子节点，因此左右子树都必须访问。
    //  [应对策略] 将节点1出栈，先检查节点1是否有右子树。如果有，就继续访问右子树，如果没有，等效于Case 1. 可以直接Visit节点1了。
    //
    /** Case 3 */
    //       1
    //      / \
    //  null   2
    //         ↑
    //         current目前位于节点1的右子叶节点（存在）。
    //  节点2的情况和Case 1完全一样。因此是先压栈2，然后检测到2是纯粹的叶子节点后，就直接把2出栈并访问了。此时缓存了prev就是节点2。
    //  然后因为此时current一直是null，因此会一直的peek栈顶元素，并检查yua


//    if (current != null) {
//      current = stack.peek();         // 因为要后序遍历，最后才能访问根节点。
//      stack.push(current.right);      // 右分支也压进去，先一边凉快着。
//      current = current.left;         // 不断深入左分支
//    }
//    else {
//      TreeNode backup = stack.pop();
//    }



    /** 按理说任何树的问题都应该可以用遍历的四种方式之一来解决。
     * 这里用Preorder合适，但难点是如何伸缩当前记录的path，使得既保证不同分支间的path是互不影响的，又保证结果和path之间不相互影响。 */
    // 只要检测到当前的节点是叶子节点，不管匹配不匹配target，都在结束的时候把路径缩短1.
    // 遍历树的全过程中只使用一个path，path保存的是节点对象
    // 只有遇到叶子节点的时候，才首先检查是否target达到了0
    // 然后再peek栈顶的节点（即下一个即将出栈的节点），然后不断的删除path，直至path的最后一个节点是下一个即将出栈节点的父节点。



    /** DFS + Stack，迭代写法。用了三个栈，存访问节点 + 当前路径结果 + target值（和递归解法根本没区别了，没有意义）*/
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
