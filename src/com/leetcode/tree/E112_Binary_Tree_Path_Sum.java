package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/10/13.
 * Given a binary tree and a sum,
 * determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
 *
 * For example: Given the below binary tree and sum = 22,
 *                  5
 *                 / \
 *                4   8
 *               /   / \
 *              11  13  4
 *             /  \      \
 *            7    2      1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 *
 * Function Signature:
 * public boolean hasPathSum(TreeNode root, int sum) {...}
 *
 * 系列问题：
 * E112 Path Sum 1: 给定树和目标值，<判断是否存在>树根一直到叶子节点之和为目标值的路径。
 * M113 Path Sum 2: 给定树和目标值，求解树根一直到叶子节点之和等于目标值的<所有路径>。
 * E437 Path Sum 3: xxxxxxxxxxx
 *
 */
public class E112_Binary_Tree_Path_Sum {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        System.out.println(hasPathSum2(root, 15));
    }

    /** DFS + Stack: 递归解法 */
    // 主要思路是不断把当前节点的值从sum中减掉，然后看减到了任何一个叶子节点时，sum是否为0。只要找到一个满足的就返回true。
    // 这里对于Path的理解很关键，想通的话很好写代码。Path的终点必须是没有任何子树的节点。
    // 易疏忽点1：是必须计算从根到叶子节点全程（Path）的和，而不是只要减到0了就算找到。所以必须递归至任意叶子节点。
    // 易疏忽点2：如果给空树和0的话，应该返回false而不是true。因为真正应该返回true的应该是零值单节点二叉树，而不是空树。
    // 易疏忽点3：对于缺一个子树的节点来说，不能因为它缺一个子树，就把它本身看作Path的终点。Path的终点，必须是没有任何子树的节点。
    //           1
    //          / \
    //         2   3       <-- 如果给定sum = 4，不能认为1 + 3算一个Path，虽然节点3的左节点是null。这也刚好符合处理空树的原则。
    //        / \   \
    //       4   5   6
    // 所以递归方法中首先需要处理空树： if (root == null) return false;
    // 然后设立递归终止条件是抵达叶子节点：if (root.left == null && root.right == null) return sum == root.val;
    // 直接不管有没有左右节点就递归进入左右节点，如果左右节点不存在就和处理空树一样的原理返回false。
    static boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return sum == root.val;
//        if      (root.left == null) return hasPathSum(root.right, sum - root.val);    // 并不需要专门避开空节点。
//        else if (root.right == null) return hasPathSum(root.left, sum - root.val);
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    /** DFS + Stack: 迭代解法，双栈 */
    // 因为递归解法每次都传入一个TreeNode和一个整型，因此需要创建两个栈，分别装节点和当前sum值。
    // 需要注意的是这里的不能因为当前path不满足就提前退出，只能在找到满足的才提前退出返回true，只有在所有path都找过后才在最后返回false
    static boolean hasPathSum2(TreeNode root, int sum) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> diff = new ArrayDeque<>();
        if (root == null) return false;
        stack.push(root);
        diff.push(sum);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            int d = diff.pop();
            if (current.left == null && current.right == null && d == current.val) return true;     // Early Exit
            if (current.right != null) {
                stack.push(current.right);
                diff.push(d - current.val);
            }
            if (current.left != null) {
                stack.push(current.left);
                diff.push(d - current.val);
            }
        }
        return false;
    }

    /** BFS + Queue: 迭代解法 */
    // 由于队列的性质，导致节点的扫描顺序与使用栈的解法略有不同，但是都同样需要检测是否是叶子节点且与sum相等。
    static boolean hasPathSum3(TreeNode root, int sum) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        Deque<Integer> diff = new ArrayDeque<>();
        if (root == null) return false;
        queue.add(root);
        diff.add(sum);
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            int d = diff.remove();
            if (current.left == null && current.right == null && d == current.val) return true;     // Early Exit
            if (current.right != null) {
                queue.add(current.right);
                diff.add(d - current.val);
            }
            if (current.left != null) {
                queue.add(current.left);
                diff.add(d - current.val);
            }
        }
        return false;
    }

    // 对比DFS和BFS的节点扫描处理过程
    //                  5
    //                 / \
    //                4   8
    //               /   / \
    //              11  13  9
    //             /  \      \
    //            7    2      1
    // DFS + Stack: [5 → [8 4 → [8 11 → [8 2 7 → [9 13 → [9 → [1 → [
    // BFS + Queue: [5 → [8 4 → [4 9 13 → [9 13 11 → [13 11 1 → [11 1 → [11 → [2 7 → [7 → [
    // 由于BFS一定是一层一层的计算当前节点的sum，因此对于一个比较大的树，需要扫描很久才能到叶子节点的部分，所以也许DFS能够比BFS更早exit。
}
