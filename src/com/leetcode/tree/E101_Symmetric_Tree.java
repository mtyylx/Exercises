package com.leetcode.tree;

import java.util.*;

/**
 * Created by LYuan on 2016/10/12.
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *          1
 *         / \
 *        2   2
 *       / \ / \
 *      3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 *          1
 *         / \
 *        2   2
 *        \   \
 *        3    3
 * Note: Bonus points if you could solve it both recursively and iteratively.
 *
 * Function Signature:
 * public boolean isSymmetric(TreeNode root) {...}
 */
public class E101_Symmetric_Tree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.Generator(new int[] {1, 2, 2, 3, 4, 4, 3});
        System.out.println(isSymmetric5(root));
    }

    // 另一个思路是先E226反转二叉树，然后再E100判同树。不过要扫描两遍。还需要额外的树空间。

    /** (1) DFS + Stack: 递归解法 */
    // 难点在于需要构造一个递归函数可以比对任何两个节点是否对称，而不是仅仅比较同一个节点下面的两个子节点（那样无法验证镜像特性）
    static boolean isSymmetric2(TreeNode root) {
        if (root == null) return true;
        return compare(root.left, root.right);
    }

    static boolean compare(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;        // 筛选掉两者同为空的情况
        if (a == null || b == null) return false;       // 筛选掉两者之一为空的情况
        return a.val == b.val && compare(a.left, b.right) && compare(a.right, b.left);  // 两者只可能不为空
    }

    /** (2) DFS + Stack: 迭代解法，需要两个栈，每个栈存一个分支节点，使用ArrayDeque实现Stack，不允许压入null值 */
    // 之所以需要两个栈，是因为先写出来的递归解法入参有两个，因此每次递归调用时，都会压入这两个参数值进函数栈。
    // 所以我们只需要把他们手动实现就行了
    // 之所以需要写这么多，是因为使用ArrayDeque实现Stack是不允许null值被压入的，因此就多了很多判空保护。
    // 所以如果非要用Deque接口实现的话，那么Deque旗下的LinkedList也是可以实现Stack功能的，只不过调用的API名字比较怪而已。见解法3.
    static boolean isSymmetric3(TreeNode root) {
        Deque<TreeNode> left = new ArrayDeque<>();
        Deque<TreeNode> right = new ArrayDeque<>();
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;
        left.push(root.left);
        right.push(root.right);
        while (!left.isEmpty() || !right.isEmpty()) {
            TreeNode l = left.pop();
            TreeNode r = right.pop();
            if (l.val != r.val) return false;
            if (l.left == null && r.right == null);
            else if (l.left == null || r.right == null) return false;
            else {
                left.push(l.left);
                right.push(r.right);
            }
            if (l.right == null && r.left == null);
            else if (l.right == null || r.left == null) return false;
            else {
                left.push(l.right);
                right.push(r.left);
            }
        }
        return left.size() == right.size();
    }

    /** (3) DFS + Stack: 迭代解法，单栈简化版，使用LinkedList实现Stack以允许压入null值 */
    // 上面解法的简化版。
    // 简化点1：不需要双栈，其实同时入栈同时出栈的双栈完全等效于一个每次入栈两次出栈两次的单栈。真正需要双栈的场合是要压入元素的类型不同时。例如一个是TreeNode一个是Integer。
    // 简化点2：省略判空，由于ArrayDeque的任何API都不允许null值，因此如果还想用Deque接口实现的话，只能用同门的LinkedList实现。
    // 因为LinkedList的所有方法都允许null元素，用起来很清爽，但是要知道ArrayDeque才是性能之王。
    // 其实背后的原理本质是将本不在一起的元素交换顺序压入栈中以供后续访问的。
    //              1
    //             / \
    //            2   3
    //           / \ / \
    //          4  5 6  7
    //         /\ /\ /\ /\
    //       8 9 1 2 3 4 5 6
    // 我们需要比对的，是2和3，4和7，5和6，而这些元素原本并不在同一个根节点的两个子树上，无法通过left和right同时访问到，但是通过交换压栈顺序就可以做到。
    // [2 3 -> [4 7 5 6 -> [4 7 1 4 2 3 -> [4 7 1 4 -> [4 7 -> [8 6 9 5 -> [8 6 -> [
    // 每个循环处理一对，并最多增加两对，这样一来成对的元素虽然不属于一个树，但是的确是我们想要比对的两个节点。很简洁优美。
    static boolean isSymmetric4(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        if (root == null) return true;
        stack.push(root.left);
        stack.push(root.right);
        while (!stack.isEmpty()) {
            TreeNode l = stack.pop();
            TreeNode r = stack.pop();
            if (l == null && r == null) continue;
            if (l == null || r == null) return false;
            if (l.val != r.val) return false;
            stack.push(l.left);
            stack.push(r.right);
            stack.push(l.right);
            stack.push(r.left);
        }
        return true;
    }

    /** BFS + Queue: 简化版，使用LinkedList实现队列，可以随便压入null值。*/
    // 和上面DFS + Stack的简化版一样，背后的原理也是将本不在一起的元素交换顺序压入队列中以供后续访问的，很优美。
    //              1
    //             / \
    //            2   3
    //           / \ / \
    //          4  5 6  7
    //         /\ /\ /\ /\
    //       8 9 1 2 3 4 5 6
    // [2 3 -> [4 7 5 6 -> [5 6 8 6 9 5 -> [8 6 9 5 1 4 2 3 -> [9 5 1 4 2 3 -> [1 4 2 3 -> [1 4 -> [
    // 每个循环处理一对，和栈不一样的地方在于它是处理完一层以后才会接着处理下一层。
    static boolean isSymmetric5(TreeNode root) {
        Deque<TreeNode> queue = new LinkedList<>();
        if (root == null) return true;
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            TreeNode l = queue.remove();
            TreeNode r = queue.remove();
            if (l == null && r == null) continue;
            if (l == null || r == null) return false;
            if (l.val != r.val) return false;
            queue.add(l.left);
            queue.add(r.right);
            queue.add(l.right);
            queue.add(r.left);
        }
        return true;
    }

    /** BFS + Queue: Level Order Traversal 迭代解法 */
    // 最初的解法，比较臃肿。想法是转变成为Level Order Traversal问题，只需要确保每一层都是对称的即可。
    // 难点：在于如何处理缺位的节点。因为ArrayDeque不能接受null元素。
    // 如果不处理，那么将会误判：例如有一层如果原本应有4个节点，但是实际是null,2,null,2，如果简化成[2, 2]则会以为是对称的。
    // 这里要搞清楚，Deque这个接口提供了多种方法，其本身并不是完全禁止null元素的，真正其决定性作用的是到底用什么实现。
    // 如果用ArrayDeque实现，那么必须不能有null元素添加。
    // 如果用LinkedList实现，那么就可以。
    static boolean isSymmetric(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root == null) return true;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.remove();
                if (current.val != Integer.MIN_VALUE) level.add(current.val);
                else {
                    level.add(Integer.MIN_VALUE);
                    continue;   // 及时终止补位节点的扩展
                }
                if (current.left != null) queue.add(current.left);
                else queue.add(new TreeNode(Integer.MIN_VALUE));
                if (current.right != null) queue.add(current.right);
                else queue.add(new TreeNode(Integer.MIN_VALUE));
            }
            for (int i = 0; i < level.size() / 2; i++)
                if ((int) level.get(i) != (int) level.get(level.size() - i - 1))    // 需要主动cast为基础类型才能用==比对。
                    return false;
        }
        return true;
    }
}
