package com.leetcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/12.
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 *
 * For example, the following two linked lists:
 * A:       a1 → a2
 *                  ↘
 *                  c1 → c2 → c3
 *                 ↗
 * B:  b1 → b2 → b3
 * begin to intersect at node c1.
 *
 * Notes:
 * 1. If the two linked lists have no intersection at all, return null.
 * 2. The linked lists must retain their original structure after the function returns.
 * 3. You may assume there are no cycles anywhere in the entire linked structure.
 * 4. Your code should preferably run in O(n) time and use only O(1) memory.
 *
 * Function Signature:
 * public ListNode hasIntersection(ListNode a, ListNode b) {...}
 *
 * */
public class E160_Intersection_of_Linked_List {
    public static void main(String[] args) {
        ListNode a = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7});
        ListNode b = ListNode.Generator(new int[] {9, 99, 999});
        b.next.next.next = a. next.next.next;
        ListNode cross = hasIntersection3(a, b);
    }

    // Floyd Cycle Detection解法思路：time - o(m + n), space - o(1), one pass.
    // 将两个链表互相连接，则长度一定相等
    // a a a a c c - b b c c
    // b b c c - a a a a c c
    // 若两链表长度相等，则会同时为null退出循环
    // 若两链表长度不等，则会先后为null，因此先后进入对方链表，但最后一定同时为null退出循环。
    static ListNode hasIntersection3(ListNode a, ListNode b) {
        if (a == null || b == null) return null;
        ListNode head1 = a;
        ListNode head2 = b;
        while (a != null || b != null) {
            if (a == null) a = head2;
            if (b == null) b = head1;
            if (a == b) return a;
            a = a.next;
            b = b.next;
        }
        return null;
    }

    // 找齐解法，time - o(n), space - o(1), two-pass
    // 其实难点只在于两个链表长度不同的时候，从头开始扫描一定会相互错过，
    // 只要把两个链表尾部对齐，并且补偿长度差，那么同时移动指针一定能够找到共同节点。
    static ListNode hasIntersection2(ListNode a, ListNode b) {
        int l1 = 0;
        int l2 = 0;
        ListNode node = a;
        while (node != null) {
            l1++;
            node = node.next;
        }
        node = b;
        while (node != null) {
            l2++;
            node = node.next;
        }
        ListNode fast = (l1 >= l2) ? a : b;
        ListNode slow = (l1 < l2) ? a : b;
        for (int i = 0; i < Math.abs(l1 - l2); i++) {
            fast = fast.next;
        }
        while (fast != null) {
            if (fast == slow) return fast;
            else {
                fast = fast.next;
                slow = slow.next;
            }
        }
        return null;
    }

    // 哈希表解法，time - o(n), space - o(n), one-pass
    // 最简单的解法，以节点指针（内存地址）作为特征值，分别维护两个set，看两个链表是否有相互重叠的节点
    static ListNode hasIntersection(ListNode a, ListNode b) {
        Set<ListNode> setA = new HashSet<>();
        Set<ListNode> setB = new HashSet<>();
        while (a != null || b != null) {
            if (a != null) {
                if (!setB.contains(a)) setA.add(a);
                else return a;
                a = a.next;
            }
            if (b != null) {
                if (!setA.contains(b)) setB.add(b);
                else return b;
                b = b.next;
            }
        }
        return null;
    }
}

