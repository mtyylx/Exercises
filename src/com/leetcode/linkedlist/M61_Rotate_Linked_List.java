package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/14.
 * Given a list, rotate the list to the right by k places, where k is non-negative.
 * For example:
 * Given 1->2->3->4->5->NULL and k = 2,
 * return 4->5->1->2->3->NULL.
 *
 * Function Signature:
 * public ListNode rotateList(ListNode head, int step) {...}
 */
public class M61_Rotate_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5});
        ListNode newhead = rotateList2(head, 0);
    }

    // 双指针解法，找到新位置后直接拼接。
    static ListNode rotateList2(ListNode head, int step) {
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode tail = dummy;  // tail
        ListNode newhead = dummy;  // new head

        int i;
        for (i = 0; tail.next != null; i++)     //Get the length and tail
            tail = tail.next;

        for (int j = i - step % i; j > 0; j--)  //Get the newhead
            newhead = newhead.next;

        tail.next = dummy.next;
        dummy.next = newhead.next;
        newhead.next = null;

        return dummy.next;
    }

    // 一般链表题目算法都以one pass加不计算长度为荣，但是这道题不一样，因为k可以很大，如果为了one pass不计算长度，那么会反而白白遍历无数次。
    // 获得链表长度后就知道了平移的距离，这时候把链表首尾相连，平移至新head，再把链表拆开。
    static ListNode rotateList(ListNode head, int step) {
        // Get the length first
        if (head == null) return head;
        ListNode current = head;
        ListNode tail;
        int length = 1;
        while (current.next != null) {
            current = current.next;
            length++;
        }
        tail = current;
        current.next = head;
        current = head;
        step = length - step % length;
        for (int i = 0; i < step; i++) {
            if (i == step - 1) tail = current;
            current = current.next;
        }
        tail.next = null;
        return current;
    }
}
