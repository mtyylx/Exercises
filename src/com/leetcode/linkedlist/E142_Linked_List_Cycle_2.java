package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/12.
 * Given a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 *
 * Note: Do not modify the linked list.
 * Can you solve it without using extra space?
 *
 * Function Signature:
 * public ListNode detectCycle(ListNode head) {...}
 * */
public class E142_Linked_List_Cycle_2 {
    public static void main(String[] args) {

    }

    // 要想清楚循环链表只有两种形式：（不要忘了还存在第二种形式）
    // 1. 全循环链表：{1, 2, 3, 1, 2, 3, 1, 2, 3, ... } 节点1开始了循环
    // 2. 部分循环链表：{9, 8, 7, 1, 2, 3, 1, 2, 3, ... } 节点1开始了循环
    // 所谓的“the node where cycle begins”指的就是循环开始的第一个节点。
    // Floyd循环检测本质上就是“让快指针套圈慢指针”，快慢指针相会时走过的距离差一定是循环长度

    // 解法的根据在于：
    //     base↘    meet↘   cycle↘    meet↘
    // 9, 9, 9, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6
    // slow指针走过的距离 = base + meet
    // fast指针走过的距离 = base + meet + cycle
    // 同时又有两倍步长特性: (base + meet) * 2 = base + meet + cycle
    // 综上所述，刚好有这个关系：base + meet = cycle
    // 所以从meet开始平移至循环第一个元素的长度一定是cycle - meet，而这个值恰好等于base
    // 而从head平移至循环第一个元素的长度也是base，所以同时从head和meet平移，一定能同时遇到第一个循环元素。
    static ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next;
            fast = fast.next;
            if (fast == slow) {
                // 找到相会点，此时同时从head和相会点出发，遇到的第一个相同节点就是循环开始的第一个节点
                while (head != slow) {
                    head = head.next;
                    slow = slow.next;
                }
                return head;
            }
        }
        return null;
    }
}
