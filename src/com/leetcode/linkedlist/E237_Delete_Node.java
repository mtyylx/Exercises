package com.leetcode.linkedlist;


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
 *
 * <链表重复节点删除 系列问题>
 * E83 Remove Duplicate Nodes   : 给定一个已排序单链表，删除多余的重复节点。
 * M82 Remove Duplicate Nodes 2 : 给定一个已排序单链表，只要节点值出现重复就删除。
 * E203 Remove Element          : 给定一个未排序单链表和一个值k，删除链表中所有值等于k的节点。
 * E237 Delete Node             : 给定一个链表的节点指针，删除该节点。
 *
 * <Tags>
 * - 单链表节点的删除：使用(curr.next != null)做循环条件提前判断并删除。
 *
 */
public class E237_Delete_Node {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        deleteNode(head.next);
        deleteNode(head, 8);
    }

    /** 解法1：修改节点。先拷贝，后跳过。 */
    // 要明确一点：对于一个单链表来说，如果你只有它中间某个节点的指针，那么不管链表本身实际有多长，对于你来说，它的长度只有给定指针开始的链表部分。
    // 当前节点之前的全部内容，对于你来说都是未知的、不存在的。更不用提去修改它们了。
    // 所以这个问题的情况下，你只能在当前节点的右边做文章，左边是不可能了。所以只有把下个节点考过来，然后把下个节点跳过的办法了。
    static void deleteNode(ListNode x) {
        x.val = x.next.val;
        x.next = x.next.next;
    }

    /** <单链表节点删除的标准做法>：如果给定链表头节点和要删除的节点，可以用(curr.next != null)作为循环条件以提前判定下个节点是否需要删除。 */
    static void deleteNode(ListNode head, int val) {
        while (head != null && head.next != null) {
            if (head.next.val == val) head.next = head.next.next;
            head = head.next;
        }
    }
}
