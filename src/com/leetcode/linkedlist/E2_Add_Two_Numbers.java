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
 * */
public class E2_Add_Two_Numbers {
    public static void main(String[] args) {
        ListNode l1 = ListNode.Generator(new int[] {9, 5, 3, 4});
        ListNode l2 = ListNode.Generator(new int[] {3, 6, 2, 9});
        ListNode sum = addTwoNums(l1, l2);
    }

    // 使用dummy节点，可以确保返回链表头指针
    // 先定义好next节点，再把指针后移，否则无效：下面这两句是完全不同的，第一句可以成功扩展链表，第二句则不能。
    // CORRECT: current.next = new ListNode(val); current = current.next;
    // WRONG: current = current.next; current = new ListNode(val);
    // 需要考虑到最高位如果出现进位的话，必须增加一个值为1的新节点
    static ListNode addTwoNums(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        Boolean carry = false;
        while (l1 != null || l2 != null) {
            int temp = 0;
            if (l1 != null) {
                temp += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                temp += l2.val;
                l2 = l2.next;
            }
            if (carry) temp++;
            carry = (temp > 9);
            current.next = new ListNode(temp % 10);
            current = current.next;
        }
        if (carry) current.next = new ListNode(1);
        return dummy.next;
    }
}
