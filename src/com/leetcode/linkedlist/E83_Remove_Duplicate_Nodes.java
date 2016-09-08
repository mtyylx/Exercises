package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/8.
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * For example,
 * Given 1->1->2, return 1->2.
 * Given 1->1->2->3->3, return 1->2->3.
 *
 * Function Signature:
 * public ListNode removeDuplicate(ListNode head) {...}
 * */
public class E83_Remove_Duplicate_Nodes {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 1, 1, 2, 3, 3, 3, 4, 4, 4});
        removeDuplicate3(head);
    }

    // 递归解法，space - o(n)，有可能overflow
    // 先递归调用head.next的removeDuplicate，确保返回的节点指针开始后面所有的元素都已没有重复元素
    // 然后判断head和返回的节点是否重复，如果是，就让head.next指向返回节点的下一个节点（因为这时已经可以保证后面的节点不会重复）
    // 然后返回head节点（其实只是为了返回整个链表的头指针）
    static ListNode removeDuplicate3(ListNode head) {
        if (head == null || head.next == null) return head;
        removeDuplicate3(head.next);
        if (head.val == head.next.val) head.next = head.next.next;
        return head;
    }

    // 单指针解法，time - o(n), space - o(1)
    // 判断node.val和node.next.val，如果相同就跳过next节点，如果不同就扫描下一个node
    static ListNode removeDuplicate2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode node = head;
        while (node != null) {
            if (node.next == null) break;
            if (node.val == node.next.val) node.next = node.next.next;
            else node = node.next;
        }
        return head;
    }

    // 双指针解法，但似乎并不简约，time - o(n), space - o(1)
    // 快指针一旦找到和慢指针值不同的节点，就让慢指针节点的next指向这个节点，然后向前移动慢指针
    static ListNode removeDuplicate(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode current = head;
        ListNode next = head.next;
        while (next != null) {
            if (next.val != current.val) {
                current.next = next;
                current = current.next;
                next = current;
            }
            next = next.next;
        }
        current.next = next;
        return head;
    }
}
