package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/8.
 * Reverse a singly linked list.
 *
 * Hint:
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 *
 * Function Signature:
 * public ListNode reverse(ListNode head) {...}
 * */
public class E206_Reverse_Linked_List {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4, 5});
        ListNode y = reverse(x);
    }

    // 迭代解法，
    // 难点1：如何定义循环体的最小操作
    // 难点2：几个指针够用，怎么移动，怎么缓存
    // 难点3：如何处理头节点和尾节点
    // 用三个指针，起始阶段prev = null，head就是头节点，next先不赋值
    // 停止条件：当head移动到尾节点指向的null节点时
    // 情况1：空链表，return null
    // 情况2：单节点，return prev (head)
    // 情况3：多节点，当head为null的时候，prev一定是链表的尾节点
    static ListNode reverse2(ListNode head) {
        ListNode prev = null;
        ListNode next;
        while (head != null) {
            next = head.next;   // 缓存当前节点（head）的下一个节点（next）
            head.next = prev;   // 修改当前节点（head）的下一节点指向为上一个节点（prev）
            prev = head;        // 当前节点操作完成，上一节（prev）点变为当前节点（head）
            head = next;        // 当前节点（head）变为下一节点（next）
        }
        return prev;            // 当前节点（head）指向null的时候，上一节点（prev）一定是尾节点
    }

    // 下面这个解法是最初想出来的解法，缺点是单独处理头节点，没有都统一到循环体中，其实只要让prev指向null就可以解决头节点指向null的要求
    static ListNode reverse(ListNode head) {
        if (head == null) return head;
        ListNode prev = head;
        ListNode current = head.next;
        ListNode next;
        head.next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    // 递归解法，
    // 难点1：递归至最深处后如何将新链表头返回至最上层

}
