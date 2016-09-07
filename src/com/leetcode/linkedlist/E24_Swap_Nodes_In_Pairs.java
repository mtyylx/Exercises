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
        ListNode x = swapNodes2(head);
        x = swapNodes2(null);
    }

    // 另一种类似的迭代解法，只是省略了定义第二个节点。
    static ListNode swapNodes3(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        ListNode first;
        dummy.next = head;
        while (current.next != null && current.next.next != null) {
            first = current.next;           // 缓存第1个节点
            current.next = first.next;      // 第0个节点指向第2个节点
            first.next = first.next.next;   // 第1个节点指向第3个节点
            current.next.next = first;      // 第2个节点指向第1个节点
            current = current.next.next;    // current从第0个节点转移到第2个节点
        }
        return dummy.next;
    }

    // 迭代解法，time - o(n), space - o(1)
    // 难点：需要设计三个指针，current / Node1 / Node2，以确保两两节点衔接处能够及时更新
    // current 前的所有节点已经完成交换
    // Node1 是一对节点的左侧那个节点
    // Node2 是一对节点的右侧那个节点
    /** <dummy> --> <node1> --> <node2> --> <node3> --> <node4> --> NULL */
    static ListNode swapNodes2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;           // 从第零个节点开始，一是可以保存head，二是可以保证衔接部分更新
        ListNode n1;
        ListNode n2;
        while (current.next != null && current.next.next != null) {
            n1 = current.next;              // 缓存第一个节点
            n2 = current.next.next;         // 缓存第二个节点
            n1.next = n2.next;              // 第一个节点改为指向第三个元素
            current.next = n2;              // 第零个节点改为指向第二个元素
            current.next.next = n1;         // 第二个节点改为指向第一个元素
            current = current.next.next;    // 起始点从第零个节点改为指向第二个节点
        }
        return dummy.next;
    }

    // 递归解法，time - o(n), space - o(n) 递归的优点在于代码简洁优美，缺点在于需要压栈，无法做到常数空间复杂度
    // 关键将链表分隔成两两节点进行操作
    /** 难点1：在于交换完两节点之后，如何与前面的节点衔接上 */
    // 最简单的办法就是从链表尾端反向顺序来操作，而用递归恰恰能很方便实现
    // 观察到可以将问题归纳为以下几种情形：
    // 1. --> NULL
    // 2. --> <node1> --> NULL
    // 3. --> <node1> --> <node2> --> NULL
    // 4. --> <node1> --> <node2> --> <node3> --> <node4> --> NULL
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