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
 * Function Signature:
 * public List<Integer> postorder(TreeNode root) {...}
 */
public class H145_Binary_Tree_Postorder_Traversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        List<Integer> result = postOrderx(root);
    }

    // 递归解法
    static List<Integer> postOrder0(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            result.addAll(postOrder0(root.left));
            result.addAll(postOrder0(root.right));
            result.add(root.val);
        }
        return result;
    }

    /** 解法1.1：真正使用后序遍历的逻辑 */
    // 虽然解法234写起来很简单，但因为并没有使用真正意义上的后序顺序遍历节点，因此在实际情况中很有可能不能被使用。
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
    static List<Integer> postOrder(TreeNode root) {
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

    /** 解法1.2：基于1.1进行简化，推荐的解法 */
    // 上面的解法重复的部分是每次要向左下移动的while循环，可以将while循环拆成if...else混合在大的while循环中
    //                     1              |    [1 2 4 <-    output 4, prev 4
    //                   /   \            |    [1 2 <-      current = peek.right = 5
    //                  2     3           |    [1 2 5 <-    output 5, prev 5
    //                /   \     \         |    [1 2 <-      peek.right == prev output 2, prev 2
    //               4     5     6        |    [1 <-        current = peek.right = 3
    //                                    |    [1 3 <-      current = peek.right = 6
    //                                    |    [1 3 6 <-    output 6, prev = 6
    //                                    |    [1 3 <-      peek.right == prev output 3, prev 3
    //                                    |    [1 <-        peek.right == prev output 1, prev 1, stack empty, current is null. Exit.
    /** 难点1：需要使用额外的prev和peek指针 */
    // 对比中序遍历的if...else解法可以看到其实代码整体结构都差不多，区别在于定义了两个新的节点指针prev和peek.
    /** prev的用途 */
    // 是辨识当前节点的右子树是否已经探索过，如果已经探索过，就可以直接输出当前节点，并将prev更新为指向当前节点。
    // prev赋给左侧节点的时候不会起作用，因为peek.right不可能访问到左侧节点
    /** peek的用途 */
    // 是避免每次while循环一开始都进入if区块向左下移动。不使用peek而是全用current会怎么样：
    // 当current已经触底，将peek到的栈顶元素赋给current时，如果这个节点没有右节点或者已经访问过右节点进入下一循环时，
    // 因为current已经不是null了，因此current又会再次向左下移动到底，就死循环了。所以这种情况必须跳过if。
    // 反过来说就是：if块的功能（向左一直到底）只有在当前节点还未探索过左侧分支时才进行，如果已经探索过左侧分支，那么仅会在向右移动一步后探索左侧分支的时候才再用到。
    static List<Integer> postOrderx(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode prev = null;
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            }
            else {
                TreeNode peek = stack.peek();
                if (peek.right == null || peek.right == prev) {     // 不更新current，current依然是null，这样不会进入if块
                    result.add(peek.val);
                    prev = stack.pop();
                }
                else current = peek.right;                          // 更新current，向右下移动一步，再进入下个循环的if块向左一直到底
            }
        }
        return result;
    }

    /** 解法3.1：前序遍历的变体 + 反转 */
    // 由于后序遍历单凭栈实现起来比较困难，而后续遍历(left -> right -> root)和前序遍历(root -> left ->right)的顺序刚好基本上相反
    // 虽然这种方法很巧妙，但是标准的后续遍历实现依然是主流
    static List<Integer> postOrder31(TreeNode root) {
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

    /** 解法3.2：前序遍历 + 两个栈 */
    // 第一个栈负责前序遍历并把元素导出至第二个栈，再对第二个栈出栈就可以得到后序遍历的顺序了，和前序遍历反转的方法思路类似。
    // 可以看到其实第整个结构就是前序遍历，只不过原来直接add进result的结果先被压入了第二个stack中，最后一步的出栈其实跟Collections.reverse功能一模一样
    //                     1
    //                   /   \
    //                  2     3
    //                 / \      \
    //               4     5     6
    // stack 1: [1 -> [2 3 -> [2 6 -> [2     -> [4 5     -> [
    // stack 2: [  -> [1   -> [1 3 -> [1 3 6 -> [1 3 6 2 -> [1 3 6 2 5 4 -> 出栈：4 5 2 6 3 1
    static List<Integer> postOrder32(TreeNode root) {
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

    /** 解法3.3：前序遍历 + LinkedList */
    // 同样使用前序遍历反转，借助于LinkedList用o(1)插入表头可以无需调用reverse或出栈。
    // 这时候就体现出接口的灵活性了，因为接口只要求List，所以内部实现用ArrayList还是LinkedList完全随便用。
    static List<Integer> postOrder33(TreeNode root) {
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
