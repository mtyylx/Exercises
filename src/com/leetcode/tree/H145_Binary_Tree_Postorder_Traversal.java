package com.leetcode.tree;

import java.util.*;

/**
 * Created by LYuan on 2016/10/9.
 * Given a binary tree, return the POST-order traversal of its nodes' values. <二叉树的后序遍历>
 * For example: Given binary tree as below,
 *         6
 *       /  \
 *     3     5
 *    / \     \
 *   1  2      4
 * Postorder traversal should return [1,2,3,4,5,6].
 * 
 * 澄清：“后”序遍历，说的是 root 在最“后”，至于两个儿子，始终是先左后右的顺序。
 *
 * Function Signature:
 * public List<Integer> postorder(TreeNode root) {...}
 */
public class H145_Binary_Tree_Postorder_Traversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        System.out.println(postOrder_recursive(root));
        System.out.println(postOrder_recursive2(root));

        System.out.println(postOrder_iterative(root));
        System.out.println(postOrder_iterative2(root));
        System.out.println(postOrder_iterative3(root));
        System.out.println(postOrder_iterative4(root));

        System.out.println(postOrder_reversePreOrder(root));
        System.out.println(postOrder_reversePreOrder2(root));
        System.out.println(postOrder_reversePreOrder3(root));
    }

    /** 递归解法1：每个递归都定义一个result容器 */
    static List<Integer> postOrder_recursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            result.addAll(postOrder_recursive(root.left));
            result.addAll(postOrder_recursive(root.right));
            result.add(root.val);
        }
        return result;
    }

    /** 递归解法2：将result在递归方法之间传递，而不是每个递归方法都创建自己的最后再合并。 */
    static List<Integer> postOrder_recursive2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postOrder_recursive2(root, result);
        return result;
    }

    static void postOrder_recursive2(TreeNode root, List<Integer> result) {
        if (root == null) return;
        postOrder_recursive2(root.left, result);
        postOrder_recursive2(root.right, result);
        result.add(root.val);
    }



    /** 迭代解法1 */
    // 真正意义上的后序顺序遍历节点，即left -> right -> root.
    //                     1
    //                   /   \
    //                  2     3
    //                 / \      \
    //               4     5     6
    /** 难点1：需要先Peek然后判定Peek到的元素的右节点是否已经访问过，如果访问过了就可以出栈并缓存，如果没有访问过就得先访问*/
    // 先不能着急让root出栈，因为不能确定root.right是否已经访问过，如果没访问的话root还得压栈，用peek就可以省略先pop后push的麻烦
    // 首先边压栈边向左下深入直至最左侧叶子节点的并不存在的左儿子。 [1 2 4
    // 此时进入大的while循环，先看一下栈顶元素（此时是最左侧叶子节点4本身）：由于4没有右子树，因此可以直接导出4，并让prev指向这个被弹出的节点4 [1 2
    // 进入下一个while循环，看栈顶元素（此时是节点2）：由于2有右子树，且右子树并不是prev（4），所以向下移动并压栈 [1 2 5
    // 进入下一个while循环，看栈顶元素（此时是prev的父节点2的右子树5）：由于5没有右子树，因此可以直接导出5，并让prev指向这个被弹出的节点5 [1 2
    // 进入下一个while循环，看栈顶元素（此时又回到了节点2）：由于2有右子树，但是右子树已经访问过了，所以可以直接导出2，并让prev指向这个被弹出的节点2 [1
    static List<Integer> postOrder_iterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode prev = null;
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        while (!stack.isEmpty()) {
            current = stack.peek();
            if (current.right == null || current.right == prev) {
                result.add(current.val);
                prev = stack.pop();
            }
            else {
                current = current.right;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
        }
        return result;
    }

    /** 迭代解法2：基于上面简化，推荐解法。 */
    // 使用current / top / prevTop 三个指针。
    // current负责持续访问节点的左分支。
    // top用来判断栈顶元素的右分支是否有必要访问。
    // prevTop用来判断栈顶元素的右分支是否已经访问过。
    /** 整体逻辑 */
    // 如果node存在：则先缓存node，然后去node.left
    // 如果node不存在：
    //      如果top.right也不存在：说明top是叶子节点，可以直接访问，并出栈。出栈前缓存top节点为prevTop，以用来判断下一个top和当前top的关系。
    //      如果top.right存在：
    //              如果top.right就是prevTop节点，说明目前处于<向上访问>过程，top.right已经访问过，就是刚刚出栈的prevTop。
    //              如果top.right不是prevTop节点，说明目前处于<向下访问>过程，top.right还没有访问过，让node指向top.right继续访问。
    //
    //               1
    //             /   \
    //           2       3
    //          / \     / \
    //       X1   4     5   X6
    //           /\     /\
    //         X2 X3   X4 X5
    //
    // node -> 1    stack: [ 1
    // node -> 2    stack: [ 1 2
    // node -> X1   top -> 2, 因为 top.right -> 4 && prev -> null,     所以 node = top.right -> 4                        top(2)右子树未访问
    // node -> 4    stack: [ 1 2 4
    // node -> X2   top -> 4, 因为 top.right -> X3 == null,            所以 访问top.val, prevTop -> 4, stack: [ 1 2       top(4)没有右子树
    // node -> X2   top -> 2, 因为 top.right -> 4 && prev -> 4,        所以 访问top.val, prevTop -> 2, stack: [ 1         top(2)右子树已经访问
    // node -> X2   top -> 1, 因为 top.right -> 3 && prev -> 2,        所以 node = top.right -> 3   , stack: [ 1         top(1)右子树未访问
    // node -> 3    stack: [ 1 3
    // node -> 5    stack: [ 1 3 5
    // node -> X4   top -> 5, 因为 top.right -> X5,                    所以 访问top.val, prevTop -> 5, stack: [ 1 3       top(5)没有右子树
    // node -> X4   top -> 3, 因为 top.right -> X6,                    所以 访问top.val, prevTop -> 3, stack: [ 1         top(3)没有右子树
    // node -> X4   top -> 1, 因为 top.right -> 3 && prev -> 3,        所以 访问top.val, prevTop -> 1, stack: [ ]         top(1)右子树已经访问
    // exit.

    // if-else 结构
    static List<Integer> postOrder_iterative2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode prevTop = null;
        while (current != null || !stack.isEmpty()) {       // 因为没有在一开始压入种子节点，为了进入循环，就需要扩大循环条件。
            if (current != null) {
                stack.push(current);
                current = current.left;
            }
            else {
                TreeNode top = stack.peek();                         // 判断栈顶元素状况：
                if (top.right == null || top.right == prevTop) {     // 如果栈顶元素没有右分支，或右分支已经访问过，就可以将栈顶元素出栈。
                    result.add(top.val);
                    prevTop = stack.pop();      // 缓存当前栈顶元素，以便判断扫描方向。
                }
                else current = top.right;                            // 如果栈顶元素有右分支，就访问右分支。
            }
        }
        return result;
    }
    // while 结构
    static List<Integer> postOrder_iterative3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode top = null;
        TreeNode prevTop = null;
        while (current != null || !stack.isEmpty()) {       // 因为没有在一开始压入种子节点，为了进入循环，就需要扩大循环条件。
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            top = stack.peek();                         // 判断栈顶元素状况：
            if (top.right == null || top.right == prevTop) {     // 如果栈顶元素没有右分支，或右分支已经访问过，就可以将栈顶元素出栈。
                result.add(top.val);
                prevTop = stack.pop();      // 缓存当前栈顶元素，以便判断扫描方向。
            }
            else current = top.right;                            // 如果栈顶元素有右分支，就访问右分支。
        }
        return result;
    }

    /** 迭代解法3：也是真正的后序遍历的迭代写法，但是借助了额外的<HashSet>作为判断是否已访问的工具 */
    // 由于哈希表最终需要记录所有节点，所以空间复杂度是Space - o(2n)
    // 基本逻辑是：如果当前节点没有子节点，或者所有子节点都访问过，就记录当前节点在哈希表中，并同时输出该元素。
    // 如果当前节点的子节点还没有访问过，那么就得先压栈等待访问。
    // 灵感来源于E110判断是否balanced的迭代解法。
    static List<Integer> postOrder_iterative4(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        Set<TreeNode> set = new HashSet<>();
        if (root == null) return result;
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.peek();
            if (current.left == null && current.right == null ||
                current.left == null && set.contains(current.right) ||
                current.right == null && set.contains(current.left) ||
                set.contains(current.left) && set.contains(current.right))
            {
                set.add(current);
                result.add(current.val);
                stack.pop();
            }
            else {
                if (current.left != null && !set.contains(current.left))
                     stack.push(current.left);
                else stack.push(current.right);
            }
        }
        return result;
    }

    /** 解法2.1：前序遍历的变体 + 反转 */
    // 由于后序遍历单凭栈实现起来比较困难，而后续遍历(left -> right -> root)和前序遍历(root -> left ->right)的顺序刚好基本上相反
    // 虽然这种方法很巧妙，但是标准的后续遍历实现依然是主流
    static List<Integer> postOrder_reversePreOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root == null) return result;
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val);
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }
        Collections.reverse(result);
        return result;
    }

    /** 解法2.2：前序遍历 + 两个栈 */
    // 第一个栈负责前序遍历并把元素导出至第二个栈，再对第二个栈出栈就可以得到后序遍历的顺序了，和前序遍历反转的方法思路类似。
    // 可以看到其实第整个结构就是前序遍历，只不过原来直接add进result的结果先被压入了第二个stack中，最后一步的出栈其实跟Collections.reverse功能一模一样
    //                     1
    //                   /   \
    //                  2     3
    //                 / \      \
    //               4     5     6
    // stack 1: [1 -> [2 3 -> [2 6 -> [2     -> [4 5     -> [
    // stack 2: [  -> [1   -> [1 3 -> [1 3 6 -> [1 3 6 2 -> [1 3 6 2 5 4 -> 出栈：4 5 2 6 3 1
    static List<Integer> postOrder_reversePreOrder2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        if (root == null) return result;
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode current = stack1.pop();
            stack2.push(current);
            if (current.left != null) stack1.push(current.left);
            if (current.right != null) stack1.push(current.right);
        }
        while (!stack2.isEmpty()) result.add(stack2.pop().val);
        return result;
    }

    /** 解法2.3：前序遍历 + LinkedList */
    // 同样使用前序遍历反转，借助于LinkedList用o(1)插入表头可以无需调用reverse或出栈。
    // 这时候就体现出接口的灵活性了，因为接口只要求List，所以内部实现用ArrayList还是LinkedList完全随便用。
    static List<Integer> postOrder_reversePreOrder3(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root == null) return result;
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(0, current.val);     // Insert at head.
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }
        return result;
    }
}
