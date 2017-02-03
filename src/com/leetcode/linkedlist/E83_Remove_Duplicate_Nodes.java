package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/8.
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * For example,
 * Given 1->1->2, return 1->2.
 * Given 1->1->2->3->3, return 1->2->3.
 *
 * Function Signature:
 * public ListNode removeDuplicate(ListNode head) {...}
 *
 * <链表重复节点删除 系列问题>
 * E83 Remove Duplicate Nodes   : 给定一个已排序单链表，删除多余的重复节点。
 * M82 Remove Duplicate Nodes 2 : 给定一个已排序单链表，只要节点值出现重复就删除。
 * E203 Remove Element          : 给定一个未排序单链表和一个值k，删除链表中所有值等于k的节点。
 * E237 Delete Node             : 给定一个链表的节点指针，删除该节点。
 *
 * <Tags>
 * - Two Pointers：快慢指针同向扫描，快指针始终移动，慢指针仅在值不相同时才移动。[ slow → → → ... fast → → → ... ]
 * - 递归解法：反向递归，在当前节点和子链表之间做出选择。
 *
 */
public class E83_Remove_Duplicate_Nodes {
    public static void main(String[] args) {
        removeDuplicate(ListNode.Generator(new int[] {1, 2, 2, 3, 3, 3, 3})).print();
        removeDuplicate2(ListNode.Generator(new int[] {1, 2, 2, 3, 3, 3, 3})).print();
        removeDuplicate3(ListNode.Generator(new int[] {1, 2, 2, 3, 3, 3, 3})).print();
    }


    /** 解法3：递归写法（反向递归）。Time - o(n), Space - o(1). */
    // 这里head就是当前节点指针（curr）
    // 首先设置递归终止条件：当前链表为空（实际是原链表的子链表）
    // 调用递归函数，该函数可以确保返回的子链表中已经没有重复元素，因此head的next一定是指向返回节点的。
    // 接下来就是判断当前节点和子链表的表头是否相等，如果相等，就应该抛弃当前节点，而直接返回子链表头节点，否则就返回当前节点。
    static ListNode removeDuplicate3(ListNode head) {
        if (head == null) return null;
        head.next = removeDuplicate3(head.next);
        if (head.next != null && head.val == head.next.val) return head.next;
        else return head;
    }

    /** 解法2：单指针解法。Time - o(n), Space - o(1). */
    // 很少见单指针的解法比双指针简单。
    // 不断的检查当前节点与下个节点的值是否相同，如果相同才移动至下个节点，否则跳至下下个节点。
    static ListNode removeDuplicate2(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            if (curr.next != null && curr.val == curr.next.val) curr.next = curr.next.next;
            else curr = curr.next;      // 意思就是只有在(curr.next == null || curr.val != curr.next.val)的时候才会直接移动至下一节点
        }
        return head;
    }

    /** 解法1：双指针解法（快慢指针）。Time - o(n), Space - o(1). */
    // 快指针负责寻找和慢指针不同的节点，如果相同，就只移动快指针，如果不同，就让慢指针的next指向快指针，并且同时移动快指针和慢指针。
    // 特例处理1：需要单独处理最后全是相同节点的情况
    // 1  ->  2 ->  3  ->  null
    //              ↑      ↑
    //              slow   fast   正常情况：while循环结束后fast抵达null，slow抵达尾节点。
    //
    // 1  ->  2 ->  2  ->  null
    //        ↑            ↑
    //        slow         fast   特殊情况：while循环结束后还有重复节点没有处理。
    //
    // 特例处理2：需要单独处理空链表的情形
    static ListNode removeDuplicate(ListNode head) {
        if (head == null) return null;      // 需要单独处理空链表
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            if (fast.val != slow.val) {     // 只有在快慢指针的节点值不相同时，慢指针才有必要移动
                slow.next = fast;           // 首先让慢节点跳过所有重复节点，直接与快指针相连
                slow = slow.next;           // 然后移动慢指针
            }
            fast = fast.next;               // 每一次无论如何都会移动快指针
        }
        slow.next = null;                   // 为了处理链表尾端全是相同元素的情况（例如 {1, 2, 2, 2, 2} 最后快指针会抵达null，但慢指针不会）
        return head;
    }
}
