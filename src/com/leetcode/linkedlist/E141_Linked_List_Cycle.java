package com.leetcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/8.
 * Given a linked list, determine if it has a cycle in it. <-- 判断链表是否是循环链表，比较的是节点（内存地址）是否重复，而不只是值是否重复
 *
 * Notes:
 * Can you solve it without using extra space?
 *
 * Function Signature:
 * public boolean hasCycle(ListNode head) {...}
 * */
public class E141_Linked_List_Cycle {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4});
        x.next.next.next.next = x;      // Make it loop back to the first node.
        System.out.println(hasCycle(x));

        // 即使每个元素内容都一样，一样可以正确判断。因为比较的是两个节点是否一样，而不是两个节点的val是否一样。
        ListNode y = ListNode.Generator(new int[] {1, 1, 1, 1});
        x.next.next.next.next = x;
        System.out.println(hasCycle(x));
    }

    // 双指针解法，fast/slow pointer. time - o(n), space - o(1)
    // 简单的来说就是判断链表是否是循环链表，而不是判断链表中的元素内容是否周期循环。
    static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        // 终止条件很容易疏忽：假设没有cycle，那么fast一定永远跑在slow的前面，因此只要fast没有遇到null那么slow一定不会遇到null
        // 假设真的有cycle，那么就更不可能遇到null
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // 哈希表解法，time - o(n), space - o(n).
    static boolean hasCycle2(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) return true;
            else set.add(head);
            head = head.next;
        }
        return false;
    }
}
