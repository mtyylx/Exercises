package com.leetcode.linkedlist;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2017/2/10.
 *
 * You are given two non-empty linked lists representing two non-negative integers.
 * The most significant digit comes first and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Follow up: What if you CANNOT modify the input lists? In other words, reversing the lists is not allowed.
 *
 * Example:
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 8 -> 0 -> 7
 *
 * Function Signature:
 * public ListNode addTwo(ListNode head1, ListNode head2) {...}
 *
 * <链表加法 系列问题>
 * E2   Add Two Numbers 1    : 给定表示两个数字的两个链表，低位在前，求和链表。
 * M445 Add Two Numbers 2    : 给定表示两个数字的两个链表，高位在前，求和链表。
 * M369 Plus One Linked List : 给定表示一个数字的一个链表，高位在前，求加一链表。
 *
 * <Tags>
 * - <链表尾对齐>的方法
 *      1. <迭代 + 栈>：以逆序访问链表
 *      2. <迭代 + 链表反转>：以逆序访问链表
 *      3. <递归>：以逆序访问链表
 *      4. <循环检测>：将链表相互连接扫描lenA + lenB。
 * - Two Pointers: 双指针同步扫描两个链表。 [p → → → ... ] [q → → → ...]
 * - <Sentinel 卫兵>：用于同步扫描两个链表。
 * - Dummy节点：动态链表头。根据是否有进位决定最终是否使用dummy作为表头。
 *
 */
public class M445_Add_Two_Numbers_2 {
    public static void main(String[] args) {
        addTwo(ListNode.Generator(new int[] {9, 9, 9}), ListNode.Generator(new int[] {9, 9, 9})).print();
        addTwo2(ListNode.Generator(new int[] {9, 9, 9}), ListNode.Generator(new int[] {9, 9, 9})).print();

    }

    /** 解法1：<迭代 + 栈>. Time - o(n), Space - o(n). */
    // 其实这个问题关键的地方就在于如何将两个链表尾对齐。
    // 对于不同长度的链表，如果想要迭代的方式逆序访问链表，只有两种方式：一种就是压栈（和递归本质一样），一种就是显式的将链表反转。
    // 相比于递归，迭代 + 栈的解法好处在于很轻松就可以开始愉快的从尾部往回扫描。
    static ListNode addTwo(ListNode head1, ListNode head2) {
        Deque<ListNode> stack1 = new ArrayDeque<>();
        Deque<ListNode> stack2 = new ArrayDeque<>();
        while (head1 != null) {                         // 将链表1节点全部压栈
            stack1.push(head1);
            head1 = head1.next;
        }
        while (head2 != null) {                         // 将链表2节点全部压栈
            stack2.push(head2);
            head2 = head2.next;
        }
        ListNode dummy = new ListNode(1);             // 使用动态链表头，假设会有进位，至于用不用最后再说
        int carry = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {            // 结合卫兵使用，只有当两个栈都掏空之后才退出循环
            int val1 = stack1.isEmpty() ? 0 : stack1.pop().val;     // 卫兵
            int val2 = stack2.isEmpty() ? 0 : stack2.pop().val;     // 卫兵
            int sum = val1 + val2 + carry;
            ListNode next = dummy.next;
            dummy.next = new ListNode(sum % 10);                  // 不断将新节点插入
            dummy.next.next = next;
            carry = sum / 10;
        }
        return carry == 1 ? dummy : dummy.next;          // 根据是否有进位决定是否使用dummy节点
    }

    /** 解法2：<迭代 + 链表反转>。不需要额外空间，但会修改链表结构。Time - o(n), Space - o(1). */
    // 通过链表反转将问题直接转化成为E2.
    static ListNode addTwo2(ListNode head1, ListNode head2) {
        head1 = reverse(head1);                                             // 先反转两个链表，再顺序扫描即可
        head2 = reverse(head2);
        ListNode dummy = new ListNode(1);
        int carry = 0;
        while (head1 != null || head2 != null || carry != 0) {
            int sum = carry;
            if (head1 != null) { sum += head1.val; head1 = head1.next; }
            if (head2 != null) { sum += head2.val; head2 = head2.next; }
            carry = sum / 10;
            ListNode temp = dummy.next;                                     // 采用在表头不断插入的方式构建结果，省的再次反转链表
            dummy.next = new ListNode(sum % 10);
            dummy.next.next = temp;
        }
        return dummy.next;
    }

    static ListNode reverse(ListNode curr) {
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /** 解法3：递归，需要提前知道长度差值。 */

    /** 解法4：使用循环检测的思路使得两个链表尾对齐。 */

}
