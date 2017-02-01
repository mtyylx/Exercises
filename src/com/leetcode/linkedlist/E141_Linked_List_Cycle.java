package com.leetcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/8.
 * Given a linked list, determine if it has a cycle in it.
 * 判断链表是否是循环链表，比较的是节点（内存地址）是否重复，而不只是值是否重复
 *
 * Notes:
 * Can you solve it without using extra space?
 *
 * Function Signature:
 * public boolean hasCycle(ListNode head) {...}
 *
 * <链表循环检测 系列问题>
 * - E141 Linked List Cycle  : 给定一个单链表，判断链表是否存在循环。
 * - E142 Linked List Cycle 2: 给定一个单链表，返回链表循环的起始节点，如果没有循环则返回null。
 * - M287 Find Duplicate     : 给定一个长度为n的数组，取值范围为n-1，只包含一个重复元素，求这个重复元素。
 *
 * <Tags>
 * - Two Pointers: 快慢指针用于循环检测。慢指针每次移动一个节点，快指针每次移动两个节点。[slow → * 1 ... fast → * 2 ... ]
 * - Floyd Loop Detection: 本质是使用双指针实现。
 * - HashSet: 判重。这里所存元素是节点的内存地址。
 *
 */
public class E141_Linked_List_Cycle {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4});
        x.next.next.next.next = x;      // Make it loop back to the first node.
        System.out.println(hasCycle(x));
        System.out.println(hasCycle2(x));

        // 即使每个元素内容都一样，一样可以正确判断。因为比较的是两个节点是否一样，而不是两个节点的val是否一样。
        ListNode y = ListNode.Generator(new int[] {1, 1, 1, 1});
        x.next.next.next.next = x;
        System.out.println(hasCycle(x));
        System.out.println(hasCycle2(x));
    }

    /** 解法2：双指针（快慢指针）。优势是空间复杂度低。Time - o(n), Space - o(1). */
    // 快慢指针同向扫描的一大功效：循环检测。
    // 慢指针采用1倍步长，快指针采用2倍步长，有定理可以证明如果循环存在则两个指针一定会相遇。
    // 1 -> 2 -> 3 -> 4
    //      ↑_________|
    // 指针运动示例：
    // 1 -> 2 -> 3 -> 4
    //      ↑    ↑
    //      slow fast
    // 1 -> 2 -> 3 -> 4
    //      ↑    ↑
    //      fast slow
    // 1 -> 2 -> 3 -> 4
    //               ↑ ↑
    //            fast,slow
    //
    static boolean hasCycle2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {       // 循环逻辑：得寸进尺，确定fast不为null才检查fast.next
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;                // 这里是内存地址比较，即identity compare
        }
        return false;
    }

    /** 解法1：HashSet判重。需要使用额外空间。Time - o(n), Space - o(n). */
    // 最直观的解法就是用哈希表存储每个节点对象的内存地址，如果链表存在循环则一定会第二次访问同一个节点。
    static boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode curr = head;
        while (curr != null) {
            if (!set.add(curr)) return true;
            curr = curr.next;
        }
        return false;
    }
}
