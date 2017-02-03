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
 *
 * <链表重复节点删除 系列问题>
 * E83 Remove Duplicate Nodes   : 给定一个已排序单链表，删除多余的重复节点。
 * M82 Remove Duplicate Nodes 2 : 给定一个已排序单链表，只要节点值出现重复就删除。
 * E203 Remove Element          : 给定一个未排序单链表和一个值k，删除链表中所有值等于k的节点。
 * E237 Delete Node             : 给定一个链表的节点指针，删除该节点。
 *
 * <Tags>
 * - Dummy节点：因为头节点有可能被删除
 * - 递归：逆序递归，先递归，后处理。
 * - 单链表节点的删除：使用(curr.next != null)做循环条件提前判断并删除。
 *
 */
public class E203_Remove_Element {
    public static void main(String[] args) {
        removeElement(ListNode.Generator(new int[] {1,1,1,2,1,1,3}), 1).print();
        removeElement2(ListNode.Generator(new int[] {1,1,1,2,1,1,3}), 1).print();
        removeElement3(ListNode.Generator(new int[] {1,1,1,2,1,1,3}), 1).print();
    }

    /** 解法3：递归写法（逆序递归）。Time - o(n), Space - o(n). */
    // 先递归至最后一个节点，递归返回的节点之后的所有节点一定已经经过了底下一层递归的检验。因此需要更新head.next指向返回的节点。
    static ListNode removeElement3(ListNode head, int val) {
        if (head == null) return null;                  // 递归终止条件
        head.next = removeElement3(head.next, val);     // 先递归，递归完成后所返回节点引领的链表一定是已经完全符合要求的链表。
        return (head.val == val) ? head.next : head;    // 再检查当前节点，决定是否跳过当前节点。
    }

    /** 解法2：不使用Dummy节点。专门处理。 */
    static ListNode removeElement2(ListNode head, int val) {
        while (head != null && head.val == val)                 // 用while专门处理首节点。
            head = head.next;
        ListNode curr = head;
        while (curr != null && curr.next != null)
            if (curr.next.val == val) curr.next = curr.next.next;
            else curr = curr.next;
        return head;
    }

    /** 解法1：Dummy节点 + 迭代写法。Time - o(n), Space - o(1). */
    // 由于头节点也有可能被删除，因此最简单的方法肯定是使用dummy节点，然后始终检查curr.next的情况。
    static ListNode removeElement(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;
        while (curr.next != null) {
            if (curr.next.val == val) curr.next = curr.next.next;       // 跳过
            else curr = curr.next;                                      // 移动
        }
        return dummy.next;
    }
}
