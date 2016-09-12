package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/11.
 * Given a linked list, remove the nth node from the end of list and return its head.
 *
 * For example,
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 *
 * Note:
 * Given n will always be valid.
 * Try to do this in one pass.
 *
 * Function Signature:
 * public ListNode removeNthFromEnd(ListNode head, int x) {...}
 * */
public class E19_Remove_Nth_From_End {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6});
        ListNode newHead = removeNthFromEnd0(head, 3);
    }

    // 思路分析：
    // 由于是从链表尾端计算，不扫描完一遍链表不可能知道“倒数第几个”到底是哪个节点，因此有下面这几种思路：
    // 法1：扫描两遍链表，第一遍得到链表长度，第二遍去除节点
    // 法2：只扫描一次就结束战斗，迭代的方法，用双指针在扫描的过程中让快指针和慢指针始终相距为x+1，直到快指针抵达链表尾。
    // 法3：只扫描一次就结束战斗，递归的方法，在递归结束一层层退出的时候删除节点
    // 由于递归天生具有顺序递归、逆序返回的特性（类似E234）

    // 递归解法，时间和空间复杂度都是o(n)，并不美好。

    // 迭代解法，双指针
    // 另一种解法，直接让快指针先移动到第x + 1个节点的位置，然后再同步移动快慢指针
    static ListNode removeNthFromEnd2(ListNode head, int x) {
        ListNode dummy = new ListNode(0);
        ListNode slow = dummy;
        ListNode fast = dummy;
        dummy.next = head;
        for (int i = 0; i < x + 1; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    // 迭代解法，双指针
    // 指针current扫描整个链表
    // 指针remove在count已经大于x的情况下开始移动（视current指针指向的节点为最后一个节点，不断更新remove节点，直到遇到真正的尾节点）
    // 为了覆盖链表头被删除的情况，需要用dummy节点，最后返回dummy.next
    static ListNode removeNthFromEnd(ListNode head, int x) {
        int count = 0;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;
        ListNode remove = dummy;
        while (current != null){
            if (count - x > 0)
                remove = remove.next;
            count++;
            current = current.next;
        }
        remove.next = remove.next.next;
        return dummy.next;
    }

    // 扫描两遍解法，最简单
    static ListNode removeNthFromEnd0(ListNode head, int x) {
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        node = dummy;
        while (length - x != 0) {
            node = node.next;
            length--;
        }
        node.next = node.next.next;
        return dummy.next;
    }
}
