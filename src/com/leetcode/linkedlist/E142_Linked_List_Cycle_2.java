package com.leetcode.linkedlist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
 *
 * <链表循环检测 系列问题>
 * - E141 Linked List Cycle  : 给定一个单链表，判断链表是否存在循环。
 * - E142 Linked List Cycle 2: 给定一个单链表，返回链表循环的起始节点，如果没有循环则返回null。
 *
 * <Tags>
 * - Two Pointers: 快慢指针用于循环检测。慢指针每次移动一个节点，快指针每次移动两个节点。[slow → * 1 ... fast → * 2 ... ]
 * - Floyd Loop Detection: 本质是使用双指针实现。
 * - HashSet: 判重。这里所存元素是节点的内存地址。
 *
 */
public class E142_Linked_List_Cycle_2 {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4});
        x.next.next.next.next = x;      // Make it loop back to the first node.
        System.out.println(getCycleStart(x).val);
        System.out.println(getCycleStart2(x).val);
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
    static ListNode getCycleStart2(ListNode head) {
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

    /** 解法1：使用HashSet判重。优点是思路直接，缺点是需要额外空间。Time - o(n), Space - o(1). */
    static ListNode getCycleStart(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) return head;
            head = head.next;
        }
        return null;
    }
}
