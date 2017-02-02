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
 * <Tags>
 * - Two Pointers: 并行同向扫描。  [i → → → ... ] && [j → → → ... ]
 * - 循环检测：任何两个长度不同的数组（或链表），只要相互首尾相连，总长度一定相等。
 * - 尾对齐
 * - HashSet：判重。
 *
 */
public class E160_Intersection_of_Linked_List {
    public static void main(String[] args) {
        ListNode a = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7});
        ListNode b = ListNode.Generator(new int[] {9, 99, 999});
        b.next.next.next = a. next.next.next;
        getIntersection(a, b).print();
        getIntersection2(a, b).print();
        getIntersection3(a, b).print();
    }

    /** 解法1：双指针（并行扫描）+ 循环检测。Time - o(n + m), Space - o(1). */
    // 题目的关键在于两个链表进入重合点之前的部分长度不等，如何找齐。
    // 这里利用了循环检测的原理，将任意两个数组串联起来，其总长度一定相等（本质上是交换律）：如下图所示
    // |---------lenA-------|-----lenB-----|
    // |-----lenB-----|-------lenA---------|
    // Case#1                    |---重合---| 如果存在重合部分，那么在后半部分可以看到其尾部已经找齐，一定可以找到重合起点。
    // Case#2                              | 如果不存在重合部分，也就是重合部分是null，那么重合起点就是null本身。
    static ListNode getIntersection(ListNode a, ListNode b) {
        if (a == null || b == null) return null;            // 必须单独处理其中之一为空链表的情况。否则会错误返回链表起始节点。
        ListNode i = a;
        ListNode j = b;
        while (a != null || b != null) {                    // 同时为null就退出（说明两个链表没有重合部分）
            if (a == null) a = j;       // 两个链表首尾相连
            if (b == null) b = i;       // 两个链表首尾相连
            if (a == b) return a;       // 节点内存地址比较
            a = a.next;                 // 先比较后移动
            b = b.next;
        }
        return null;
    }

    /** 解法2：手动尾对齐解法。Time - o(n + m), Space - o(1). 虽然看上去没有双指针简洁，但是实际性能却快于解法1，比较诡异。 */
    // 首先遍历两个链表，获得各自的长度，然后根据长度差，先遍历长的那个链表，然后同步遍历。
    // |---------lenA--------|    transform      |------|--lenA--------|
    // |-----lenB-----|-diff-|       to...       |-diff-|-----lenB-----|
    static ListNode getIntersection2(ListNode a, ListNode b) {
        if (a == null || b == null) return null;
        int diff = 0;
        ListNode curr = a;
        while (curr != null) {          // 累加
            curr = curr.next;
            diff++;
        }
        curr = b;
        while (curr != null) {          // 累减
            curr = curr.next;
            diff--;
        }
        if (diff > 0) while (diff-- > 0) a = a.next;        // 根据正负选择先遍历长的那个链表
        else while (diff++ < 0) b = b.next;
        while (a != null) {                                 // 此时已经做到了尾对齐，可以同步遍历
            if (a == b) return a;
            a = a.next;
            b = b.next;
        }
        return null;
    }

    /** 解法3：HashSet判重。Time - o(n), Space - o(n). 思路简单，但是需要额外空间。 */
    static ListNode getIntersection3(ListNode a, ListNode b) {
        Set<ListNode> set = new HashSet<>();
        ListNode curr = a;
        while (curr != null) {
            set.add(curr);
            curr = curr.next;
        }
        curr = b;
        while (curr != null) {
            if (!set.add(curr)) return curr;
            curr = curr.next;
        }
        return null;
    }
}

