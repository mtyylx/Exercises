package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/7.
 *
 * Defined for all LinkedList questions.
 *
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int x) { val = x; }

    // 根据给定数组生成链表，并同时打印出来。
    public static ListNode Generator(int[] a) {
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        for (int x : a) {
            node.next = new ListNode(x);
            node = node.next;
        }
        if (dummy.next == null) System.out.println("null");
        else dummy.next.print();
        return dummy.next;
    }

    // 打印任何非空ListNode节点及其子节点。增加对循环链表的打印支持。
    public void print() {
        StringBuilder sb = new StringBuilder();
        ListNode head = this;
        ListNode current = this;
        while (current != null) {
            sb.append(current.val).append(" -> ");
            current = current.next;
            if (current == head) break;
        }
        if (current != head) sb.append("null");
        else sb.append("(loop)");
        System.out.println(sb.toString());
    }
}
