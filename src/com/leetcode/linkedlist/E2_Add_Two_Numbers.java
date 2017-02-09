package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/7.
 * You are given two linked lists representing two non-negative numbers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 *
 * Example:
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 *
 * Function Signature:
 * public ListNode addTwoNums(ListNode l1, ListNode l2) {...}
 *
 * <Tags>
 * - Dummy节点：动态链表头。自动handle空链表的情形。要知道，链表的很多问题，不用dummy节点是解不出来的。这道题就是。
 * - Sentinel卫兵：在单个while循环内完成两个链表的扫描，精简代码。
 * - Math: Carry
 *
 */
public class E2_Add_Two_Numbers {
    public static void main(String[] args) {
        ListNode l1 = ListNode.Generator(new int[] {9, 5, 9});
        ListNode l2 = ListNode.Generator(new int[] {3, 6, 9, 9});
        addTwo(l1, l2).print();
        addTwo2(l1, l2).print();
    }

    /** 关于Dummy节点的使用 */
    // 1. Dummy节点可以确保你赢在起点，可进可退。返回链表头指针十分轻松。
    // 2. Dummy节点可以自动处理空链表的情况，dummy -> null。无需操心。

    /** <链表节点扩展>的注意事项：先实例化下个节点，再后移指针
     * 例如当前链表只有一个节点：ListNode head = new ListNode(99);
     *
     * <正确扩展方式>
     * head         →   head                        → head.next 和 node 都指向下一个新节点
     * [99 | null]      [99 | ref] → [0 | null]
     * 先实例化下个节点，同时把下个节点的引用填到了head.next里，再让node指向head.next所指向的新节点
     * head.next = new ListNode(0); node = head.next
     *
     * <错误扩展方式>
     * head         →   node       →   node          → head.next仍指向null，node指向新节点，head.next和node之间完全无关了。
     * [99 | null]      null           [0 | null]
     * 先让node指向head.next所指向的（此时仍然是null），再让node指向一个全新的节点（此时node已经和head.next分道扬镳了）
     * node = head.next; node = new ListNode(0);
     */

    /** 解法1：dummy节点 + Sentinel + 双指针同步扫描。Time - o(n), Space - o(1). */
    // 使用dummy节点的好处：给自己留了退路（退一步海阔天空），能自动处理空链表的情况，无需专门处理。
    static ListNode addTwo(ListNode a, ListNode b) {
        int carry = 0;
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        while (a != null || b != null) {
            int x = (a != null) ? a.val : 0;
            int y = (b != null) ? b.val : 0;
            int sum = carry + x + y;
            node.next = new ListNode(sum % 10);
            carry = (sum > 9) ? 1 : 0;
            node = node.next;
            if (a != null) a = a.next;
            if (b != null) b = b.next;
        }
        if (carry == 1) node.next = new ListNode(1);    // while循环结束后要检查是否需要新增进位节点。
        return dummy.next;
    }

    /** 简化写法 */
    // while循环设置成3个条件，这样当两个链表都抵达尾端的时候，还可以继续增加一个值为1的进位节点再退出，无需循环外再判断一次，代码更简洁
    // 另外进位的值可以用过(sum / 10)来获得，因为进位只可能是0或者1.
    static ListNode addTwo2(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        int carry = 0;
        while (a != null || b != null || carry != 0) {
            int x = (a != null) ? a.val : 0;
            int y = (b != null) ? b.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            node.next = new ListNode(sum % 10);
            node = node.next;
            if (a != null) a = a.next;
            if (b != null) b = b.next;
        }
        return dummy.next;
    }



}
