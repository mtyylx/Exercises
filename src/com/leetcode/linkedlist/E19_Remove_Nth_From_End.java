package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/11.
 * Given a linked list, remove the nth node from the end of list and return its head.
 *
 * For example,
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 *
 * Note:
 * Given n will always be valid.
 * Try to do this in one pass.
 *
 * Function Signature:
 * public ListNode removeNthFromEnd(ListNode head, int x) {...}
 * */
public class E19_Remove_Nth_From_End {
    public static void main(String[] args) {

    }

    // 由于是从链表尾端计算，不扫描完一遍链表不可能知道到底第几个
    // 对于这种情况，如果要只扫描一次就结束战斗，
    // 如果用迭代的方法，一定会用到双指针，在扫描的过程中就记录着可能的节点
    // 由于递归天生具有顺序递归、逆序返回的特性，因此使用递归是最直觉的想法。
}
