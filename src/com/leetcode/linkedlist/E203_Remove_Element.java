package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/8.
 * Remove all elements from a linked list of integers that have value val.
 *
 * Example:
 * Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
 * Return: 1 --> 2 --> 3 --> 4 --> 5
 *
 * Function Signature:
 * public ListNode removeElement(ListNode head, int val) {...}
 * */
public class E203_Remove_Element {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1,1,1,2,1,1,3});
        ListNode x = removeElement(head, 1);
        ListNode y = removeElement2(head, 1);
        ListNode z = removeElement3(head, 1);
    }

    // 递归解法，
    // 终止条件：head已经指向了空节点
    // 先递归：把删完val节点的链表头返回作为当前head的next
    // 再处理：可以确保head右侧的所有节点都删光了val，只要确保head本身不等于val即可，如果等于，则返回下一个元素作为衔接。
    static ListNode removeElement3(ListNode head, int val) {
        if (head == null) return head;
        head.next = removeElement3(head.next, val);
        return (head.val == val) ? head.next : head;
    }

    // 不使用dummy节点，单独处理第一个节点就是需要删除的情况
    static ListNode removeElement2(ListNode head, int val) {
        // 一直删直到遇到一个不等于val的节点
        while (head != null && head.val == val)
            head = head.next;
        ListNode curr = head;
        while (curr != null && curr.next != null)
            if (curr.next.val == val) curr.next = curr.next.next;
            else curr = curr.next;
        return head;
    }

    // 使用dummy节点，处理第一个节点就需要删除的情况
    static ListNode removeElement(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode node = dummy;
        while (node.next != null) {
            if (node.next.val == val) node.next = node.next.next;
            else node = node.next;
        }
        return dummy.next;
    }
}
