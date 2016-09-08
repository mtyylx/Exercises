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
