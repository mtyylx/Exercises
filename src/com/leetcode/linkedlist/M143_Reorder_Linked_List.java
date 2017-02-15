package com.leetcode.linkedlist;

/**
 * Created by Michael on 2017/2/15.
 *
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln, reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 * You must do this in-place without altering the nodes' values.
 *
 * For example,
 * Given {1, 2, 3, 4}, reorder it to {1, 4, 2, 3}.
 * Given {1, 2, 3}, reorder it to {1, 3, 2}.
 *
 * Function Signature:
 * public void reorderList(ListNode head) {...}
 *
 */
public class M143_Reorder_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5});
        reorderList(head);
        head.print();
    }

    // Partial Reverse + Merge
    static void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = slow.next;
        slow.next = null;
        right = reverse(right);
        ListNode left = head;
        ListNode dummy = new ListNode(0);
        while (right != null) {
            dummy.next = left;
            left = left.next;
            dummy = dummy.next;
            dummy.next = right;
            right = right.next;
            dummy = dummy.next;
        }
        if (left != null) dummy.next = left;
    }

    static ListNode reverse(ListNode curr) {
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
