package com.leetcode.linkedlist;

import java.util.List;

/**
 * Created by Michael on 2016/9/7.
 * Write a function to delete a node (except the tail) in a singly linked list,
 * given only access to that node. <---- 这个限制条件很关键，你并不知道链表头指针。所以不可能回溯。
 * Supposed the linked list is 1 -> 2 -> 3 -> 4,
 * and you are given the third node with value 3,
 * the linked list should become 1 -> 2 -> 4 after calling your function.
 *
 * Function Signature:
 * public void deleteNode(ListNode x) {...}
 * */
public class E237_Delete_Node {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        deleteNode(head.next);
        deleteNode(head, 8);
    }

    // 特殊约束：单链表，但是只给你需要删除的这个节点的指针，
    // 所以你无论如何都不可能把当前结点删掉，因为上一个节点的next始终的存着当前结点的内存地址。
    // 所以只能复制下一个节点，然后把下一个节点删掉。
    static void deleteNode(ListNode x) {
        x.val = x.next.val;
        x.next = x.next.next;
    }

    // 通常情况下删除链表元素的方法，以current.next.val而不是current.val来检查，以直接跳过下一个节点
    static void deleteNode(ListNode head, int content) {
        while (head != null && head.next != null) {
            if (head.next.val == content)
                head.next = head.next.next;
            head = head.next;
        }
    }
}
