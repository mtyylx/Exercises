package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/15.
 * Given a singly linked list, group all odd nodes together followed by the even nodes.
 * Please note here we are talking about the node number and not the value in the nodes.
 * You should try to do it in place.
 * The program should run in O(1) space complexity and O(nodes) time complexity.
 *
 * Example:
 * Given 1->2->3->4->5->NULL,
 * return 1->3->5->2->4->NULL.
 *
 * Note:
 * The relative order inside both the even and odd groups should remain as it was in the input.
 * The first node is considered odd, the second node even and so on ...
 *
 * Function Signature:
 * public ListNode splitOddEven(ListNode head) {...}
 *
 * */
public class M328_Odd_Even_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7});
        ListNode newhead = splitOddEven2(head);
    }

    // 简化写法，因为实际上没有必要分别判断奇偶节点的指针是否为null，偶数总在奇数的右边
    // even总会在odd之前遇到null，而且不判断even.next.next的好处是even会自动衔接到null上，不需要额外的收尾处理了
    static ListNode splitOddEven2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode odd = head;
        ListNode even = head.next;
        ListNode connect = head.next;
        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            odd = odd.next;
            even.next = even.next.next;
            even = even.next;
        }
        odd.next = connect;
        return head;
    }

    // 典型的双指针解法，time - o(n), space - o(1)
    // 一个指针指向奇数节点，一个指针指向偶数节点，互为缓存，这样就可以让奇数链接奇数的而不失去偶数的节点引用。
    static ListNode splitOddEven(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode odd = head;
        ListNode even = head.next;
        ListNode connect = head.next;
        while (true) {
            if (odd.next != null && odd.next.next != null) {
                odd.next = odd.next.next;
                odd = odd.next;
            }
            else break;
            if (even.next != null && even.next.next != null) {
                even.next = even.next.next;
                even = even.next;
            }
            else break;
        }
        odd.next = connect;
        even.next = null;
        return head;
    }
}
