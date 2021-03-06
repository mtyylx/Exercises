package com.leetcode.tree;

import java.util.*;

/**
 * Created by LYuan on 2016/10/11.
 * Given a binary tree, determine if it is height-balanced.
 * A height-balanced binary tree is defined as a binary tree in which ->
 * the depth of the two subtrees of every node never differ by more than 1.
 *
 * Function Signature:
 * public boolean isBalanced(TreeNode root) {...}
 */
public class E110_Balanced_Binary_Tree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7});
        System.out.println(isBalanced4(root));
    }

    /** DFS + Stack: 递归解法，逆序递归Bottom-Up（需要按照自底向上的顺序传递有用信息：当前子树高度）*/
    // Time - o(n), Space - o(n)
    // 核心思路：如果一棵树的子树不是平衡二叉树，那么这棵树一定不是平衡二叉树。所以只需不断分解问题直至触底再在上浮的同时计算高度差解决问题。
    // 由于考虑到递归的过程中必须要传递树高度的值，这个值没法做成入参，而原函数返回值类型又是boolean，所以只能另起炉灶。
    // 开门见山首先是递归终止条件：root == null
    // 进入正题，首先递归当前节点的左右子树
    // 如果左右子树发现不满足平衡二叉树的要求，也就是返回高度为-1，
    // 就向上传递这个-1，而不再计算当前树的真实高度（因为以当前节点作为树根的树已经不是平衡二叉树了）
    // 或者虽然左右子树都是平衡二叉树，但是到了当前节点这块两个子树高度的差值已经大于1了，也向上传递-1
    // 只有左右子树都是平衡二叉树，且当前节点两个子树的高度也小于等于1，才说明当前节点为根的树依然是平衡二叉树，这时候才上传当前树的长度。
    // 从树的遍历角度来看，其实这个解法的本质就是后序遍历的递归写法（左、右、根）的变体。很有意思。所以如果要寻找对应的迭代方法，只需后序遍历的迭代解法即可。
    static boolean isBalanced(TreeNode root) {
        return Depth(root) != -1;
    }

    static int Depth(TreeNode root) {
        if (root == null) return 0;
        int left = Depth(root.left);
        int right = Depth(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return 1 + Math.max(left, right);
    }

    /** 另一种递归解法，正序递归Top-Down */
    // Time - o(n^2)
    // 注意这种解法并不是BFS，因为它的Depth2方法依然是需要递归到最底端才能获得结果的，而不是一层一层的计算
    // 从这个解法我们可以看到，递归的算法并不一定就是DFS或BFS中的一种，有可能都不是。
    // 这个解法的主要问题是无用的运算太多。在计算根节点的时候先获取两个子树的高度，然后判断是否平衡。然后再判断两个子树是否是平衡二叉树。
    // 所以实际过程就是重复下降N次判断每个节点是否平衡，但是实际上计算两个子树高度的时候应该就顺带着把子树是否平衡就算出来了，而不是重复很多次。
    // 因此，这就体现出来在这道题上，虽然TopDown和BottomUp都可以使用，但是最本质的办法还是BottomUp。
    static boolean isBalanced2(TreeNode root) {
        if (root == null) return true;
        int left = Depth2(root.left);
        int right = Depth2(root.right);
        return (Math.abs(left - right) <= 1) && isBalanced2(root.left) && isBalanced2(root.right);
    }

    static int Depth2(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return 1 + Math.max(Depth2(root.left), Depth2(root.right));
    }

    /** 由于任何二叉树的问题都或多或少的需要遍历这个二叉树，也就转变成为了采用某种二叉树遍历方式并顺便在遍历的过程中做一些事情的问题了 */
    // 这道题由于需要先检查子节点是否是平衡的，再检查父节点，因此特别符合后序遍历算法的顺序，所以可以用后序遍历的迭代解法实现。
    // 另：其实解法1本质上就是后序遍历的递归实现。

    /** 迭代写法：使用DFS + Stack + HashMap: 使用HashMap把节点和节点所在高度一一对应了起来 */
    // 逻辑分析：
    // 每个循环开始首先观察栈顶节点，可以看到进入if的前提可以是这几种：
    // 1. 根本没有左右节点
    // 2. 只有一个子节点，且该子节点已经出现在哈希表中（已访问）
    // 3. 两个子节点都有，且都已经出现在哈希表中
    // 所以进入else的前提就是这几种：
    // 1. 只有一个子节点，且该子节点没有访问过
    // 2. 两个子节点都有，且至少有一个没有访问过
    // 于是就进入else详细检查，如果有左节点且左节点未出现在哈希表中，就把左节点压入，等待下轮循环访问。
    // 如果没有左节点（但一定有右节点）就把右节点压入，如果有左节点但左节点已经访问过，也要把右节点压入以供访问。
    // 进入下一循环。
    // 只要进入了大if，我们就可以确保current的子节点要不然不存在（高度0）要不然存在且已经记录高度，然后判定current这个数是否是平衡的
    // 如果是平衡的，就把current节点也记录在哈希表中，同时把current所在高度也记录进去。
    static boolean isBalanced4(TreeNode root) {
        if (root == null) return true;
        Deque<TreeNode> stack = new ArrayDeque<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.peek();
            if (current.left == null && current.right == null ||
                current.left == null && map.containsKey(current.right) ||
                current.right == null && map.containsKey(current.left) ||
                map.containsKey(current.left) && map.containsKey(current.right)) {
                int left = (current.left == null) ? 0 : map.get(current.left);
                int right = (current.right == null) ? 0 : map.get(current.right);
                if (Math.abs(left - right) > 1) return false;
                else map.put(current, 1 + Math.max(left, right));
                stack.pop();
            }
            else {
                if (current.left != null && !map.containsKey(current.left)) stack.push(current.left);
                else stack.push(current.right);
            }
        }
        return true;
    }
}
