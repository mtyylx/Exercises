package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/7.
 *
 * Defined for all LinkedList questions.
 *
 */
public class ListNode {
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
