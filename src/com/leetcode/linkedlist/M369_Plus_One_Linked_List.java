package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/14.
 * Given a non-negative number represented as a singly linked list of digits, plus one to the number.
 * The digits are stored such that the most significant digit is at the head of the list.
 * Example:
 * Input: 1->2->3
 * Output: 1->2->4
 *
 * Function Signature:
 * public ListNode plusOne(ListNode head) {...}
 * */
public class M369_Plus_One_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 9, 9, 9});
        ListNode newHead = plusOne2(head);
    }

    // Two Pointer: Boring!

    // 递归解法，需要单独处理首节点出现进位的情况。
    static ListNode plusOne2(ListNode head) {
        if (recursivePlusOne(head) == 0) return head;
        ListNode dummy = new ListNode(1);
        dummy.next = head;
        return dummy;
    }
    // 将进位值返回，对于最高位来说，如果返回的不是0，就说明没有进位。
    // 递归函数只负责原位修改链表节点的每个值，并且将进位（如果有的话）传递给上一个节点。
    // 比较巧妙的是可以把递归终止条件返回1，等效于加一
    static int recursivePlusOne(ListNode head) {
        if (head == null) return 1;
        int val = head.val + recursivePlusOne(head.next);
        head.val = val % 10;
        return val / 10;
    }

    // 先反转，加一，再反转回来的解法
    // 对于链表来说，最不舒服的情况就是让你逆向操作，所以完全可以先反转一下正向操作，再反转回去。
    static ListNode plusOne(ListNode head) {
        head = reverse(head);
        ListNode current = head;
        int carry = 0;
        current.val++;
        while (current != null) {
            current.val += carry;
            if (current.val > 9) {
                current.val -= 10;
                carry = 1;
            }
            else carry = 0;
            if (carry == 0) break;
            current = current.next;
        }
        head = reverse(head);
        if (carry == 1) {
            ListNode dummy = new ListNode(1);
            dummy.next = head;
            head = dummy;
        }
        return head;
    }

    static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
