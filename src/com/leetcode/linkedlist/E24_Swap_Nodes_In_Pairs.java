package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/7.
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * For example,
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 *
 * Notes:
 * Your algorithm should use only constant space.
 * You may not modify the values in the list, only nodes itself can be changed.
 *
 * Function Signature:
 * public ListNode swapNodes(ListNode head) {...}
 * */
public class E24_Swap_Nodes_In_Pairs {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        ListNode x = swapNodes(head);
    }

    // 递归解法，o(n)
    // 关键将链表分隔成两两节点进行操作
    /** 难点1：在于交换完两节点之后，如何与前面的节点衔接上 */
    // 最简单的办法就是从链表尾端反向顺序来操作，而用递归恰恰能很方便实现
    // 观察到可以将问题归纳为以下几种情形：
    // 1. --> Null
    // 2. --> node --> Null
    // 3. --> node --> node --> Null
    // 4. --> node --> node --> node --> node --> Null
    // 前两种情况直接返回原始head即可，因为没有可交换的元素
    // 后两种情况本质上是一样的，一个是一对节点后遇到null，一个是两对节点后遇到null
    // 因此递归的逻辑应该写成：
    // 1. 首先验证终止递归的条件，满足终止条件直接返回当前head，无需交换
    // 2. 不满足递归条件，则先递归交换下一对节点，并返回交换完成后的新head
    // 3. 再对当前这对节点进行真正的交换
    // 4. 最后返回当前这对节点交换完成后的新head
    /** 难点2：在于用什么次序先后交换当前的两个节点的两个指针。
     * 根本技巧：修改指针前需要把现在被指向的对象缓存 */
    // head --> node1 --> node2 --> nextHead
    // 缓存新的nextHead：指向交换完的下一对的链表头
    // 缓存新的newHead：指向node2
    // 把node2的next指向node1：newHead.next = head;
    // 把node1的next指向nextHead：head.next = nextHead;
    static ListNode swapNodes(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode nextHead = swapNodes(head.next.next);
        ListNode newHead = head.next;
        newHead.next = head;
        head.next = nextHead;
        return newHead;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }

    // Generate a LinkedList from a given array
    static ListNode Generator(int[] array) {
        ListNode head = new ListNode(array[0]);
        ListNode pointer = head;
        for (int i = 1; i < array.length; i++) {
            pointer.next = new ListNode(array[i]);
            pointer = pointer.next;
        }
        return head;
    }
}