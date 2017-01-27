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

    /** 解法1：双指针解法（快慢指针）。Time - o(n), Space - o(1). */
    // 快指针负责寻找和慢指针不同的节点，如果相同，就只移动快指针，如果不同，就让慢指针的next指向快指针，并且同时移动快指针和慢指针。
    // 需要单独处理最后全是相同节点的情况
    // 需要单独处理空链表的情形
    static ListNode removeDuplicate(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            if (fast.val != slow.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        slow.next = null;
        return head;
    }
}
