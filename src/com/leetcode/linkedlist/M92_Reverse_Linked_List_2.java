package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/13.
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 *
 * For example:
 * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
 * return 1->4->3->2->5->NULL.
 *
 * Note:
 * Given m, n satisfy the following condition:
 * 1 ≤ m ≤ n ≤ length of list.
 *
 * Function Signature:
 * public ListNode reverse(ListNode head, int m, int n) {...}
 *
 * <链表反转 系列问题>
 * E206 Reverse Linked List  : 反转整个链表
 * M92  Reverse Linked List 2: 反转链表的指定区域。
 *
 * <Tags>
 * - 链表反转 之 <插入法>
 *
 */
public class M92_Reverse_Linked_List_2 {
    public static void main(String[] args) {
        reverseRange(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 2, 4).print();
        reverseRange2(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 2, 4).print();
    }

    /** 解法1：插入法链表反转。Time - o(n), Space - o(1). */
    // 虽然链表反转有两种办法，但是这道题的情形下，使用插入法反转链表更容易处理。因为它可以自动衔接反转部分与非反转部分。
    //          0 -> 1 --> 2 -> 3 -> 4 --> 5 -> 6
    //        |---------|----反转区域----|---------|
    //          0 -> 1 --> 4 -> 3 -> 2 --> 5 -> 6
    // 由于插入法的起始位置既可以是dummy节点，也可以是任意位置的节点，因此特别适合这种场景：
    // 核心思路：设计两个并行移动的指针prev和pivot，在抵达left之前同时平移，在抵达left之后，prev固定不动，不断将pivot之后的节点插入prev之后。
    // Case #1: 如果left = 1，也就是说反转从链表头开始，那么起始位置就是dummy节点，轴心是dummy.next
    //    dummy -> 1 -> 2 -> 3 ...  =>  dummy -> 2 -> 1 -> 3 ... =>  dummy -> 3 -> 2 -> 1 ...
    //      ↑      ↑                                  ↑                                 ↑
    //  指向头节点  轴心                               轴心                               轴心
    // Case #2: 如果left > 1，那么只需要先移动到非反转区域的最后一个节点位置作为起始位置，以反转区域的第一个节点作为轴心
    //    ... -> 2 ---> 3 -> 4 -> 5 ...    ... -> 2 ---> 4 -> 3 -> 5 ...    ... -> 2 ---> 5 -> 4 -> 3 ...
    //           ↑      ↑                                     ↑                                     ↑
    //       指向头节点  轴心                                   轴心                                  轴心
    static ListNode reverseRange(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        for (int i = 0; i < left - 1; i++) prev = prev.next;    // 提前结束循环
        ListNode pivot = prev.next;                             // pivot是轴心：反转区域的第一个节点
        for (int i = left; i < right; i++) {
            ListNode next = pivot.next;
            pivot.next = next.next;
            next.next = prev.next;                              // prev.next是反转区域的链表头，不断被更新。
            prev.next = next;
        }
        return dummy.next;
    }
    // 全写在一个循环内的解法
    static ListNode reverseRange2(ListNode curr, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = curr;
        ListNode pivot = null;
        ListNode prev = dummy;
        for (int i = 1; i <= right; i++) {
            if (i < left) {
                prev = curr;
                curr = curr.next;
            } else if (i == left) {
                pivot = curr;
            } else {
                prev.next = pivot.next;
                pivot.next = prev.next;
                prev.next = curr;
                curr = prev.next;
            }
        }
        return dummy.next;
    }

    /** 解法2：两段法反转链表。处理首尾衔接比较复杂。 */
    /** 解法3：也许可以尝试递归解决。*/
}
