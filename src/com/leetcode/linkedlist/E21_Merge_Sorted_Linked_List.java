package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/12.
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 * 注意：这里强调是将现有链表的节点混合(Splice Together)，而不是拷贝值另建链表。
 *
 * Function Signature:
 * public ListNode merge(ListNode a, ListNode b) {...}
 * */
public class E21_Merge_Sorted_Linked_List {
    public static void main(String[] args) {
        ListNode a = ListNode.Generator(new int[] {1, 3, 4, 5, 6});
        ListNode b = ListNode.Generator(new int[] {2, 4, 6, 8, 10});
        ListNode newhead = merge2(a, b);
    }

    // 递归解法，正向递归（先做当前节点的操作，再递归进入下一节点），每个head都是子链表的表头，不断分解链表，直至其中一个链表抵达尾部返回。
    // space - o(n), time - o(n).
    static ListNode merge2(ListNode a, ListNode b) {
        ListNode head;
        if      (a == null) head = b;
        else if (b == null) head = a;
        else if (a.val <= b.val) {
            head = a;
            head.next = merge2(a.next, b);
        }
        else {
            head = b;
            head.next = merge2(a, b.next);
        }
        return head;
    }

    // 迭代解法，双指针，time - o(n), space - o(1)
    static ListNode merge(ListNode a, ListNode b) {
        ListNode head = new ListNode(0);
        ListNode current = head;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                current.next = a;
                a = a.next;
            }
            else {
                current.next = b;
                b = b.next;
            }
            current = current.next;
        }
        if (a == null) current.next = b;
        if (b == null) current.next = a;
        return head.next;
    }
}
