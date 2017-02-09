package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/14.
 * Given a non-negative number represented as a singly linked list of digits, plus one to the number.
 * The digits are stored such that the most significant digit is at the head of the list.
 * Example:
 * Input: 1->2->3
 * Output: 1->2->4
 *
 * Function Signature:
 * public ListNode plusOne(ListNode head) {...}
 *
 * <Tags>
 * - 递归：利用逆序递归实现链表的反向扫描。
 * - 链表反转：当你想反向扫描链表而不修改结构时，链表反转可以满足你。
 * - Two Pointers: 快慢指针同向扫描。快指针扫描，将慢指针定位于<最后一个满足xxx的位置>。
 *
 */
public class M369_Plus_One_Linked_List {
    public static void main(String[] args) {
        plusOne1(ListNode.Generator(new int[] {})).print();
        plusOne1(ListNode.Generator(new int[] {1, 0, 0})).print();
        plusOne1(ListNode.Generator(new int[] {8, 9, 9})).print();
        plusOne1(ListNode.Generator(new int[] {9, 9, 9})).print();
        plusOne2(ListNode.Generator(new int[] {})).print();
        plusOne2(ListNode.Generator(new int[] {1, 0, 0})).print();
        plusOne2(ListNode.Generator(new int[] {8, 9, 9})).print();
        plusOne2(ListNode.Generator(new int[] {9, 9, 9})).print();
        plusOne3(ListNode.Generator(new int[] {})).print();
        plusOne3(ListNode.Generator(new int[] {1, 0, 0})).print();
        plusOne3(ListNode.Generator(new int[] {8, 9, 9})).print();
        plusOne3(ListNode.Generator(new int[] {9, 9, 9})).print();
    }

    /** 解法3：双指针（快慢指针同向扫描）+ Dummy节点。Time - o(n), Space - o(1). */
    // 该解法巧妙的地方在于把任意链表提炼成为下面的一般形式（其中x表示节点值非9），以不变应万变。
    // x -> x -> 9 -> x -> ... -> x -> x -> 9 -> 9 -> null
    //                                 ↑
    //                            最后一个非9节点
    // 我们实际要做的就是从最后一个非9节点开始之后的所有节点都加1.
    // 一般形式的几种特殊情况分析
    // 特殊情况1：全9链表 dummy -> 9 -> 9 -> 9 -> null. slow从dummy开始，将所有节点加1。
    //                 ↑slow
    // 特殊情况2：尾节点非9 dummy -> 1 -> 1 -> 1 -> null. slow指向尾节点，并将尾节点加1。
    //                                      ↑slow
    // 特殊情况3：链表有很多断续的9节点 dummy -> 9 -> 1 -> 9 -> 1 -> 9 -> null. slow将指向最后一个非9节点，并将其后所有节点加1。
    //                                                     ↑slow
    static ListNode plusOne3(ListNode head) {
        ListNode dummy = new ListNode(0);               // 先假设有进位，定义好dummy节点备用。
        dummy.next = head;
        ListNode slow = dummy;                             // 慢指针从dummy出发，以应对全9链表
        ListNode fast = head;                              // 快指针从链表头出发，扫描整个链表
        while (fast != null) {                             // 在扫描过程中用慢指针记录<链表中最后一个非9节点的位置>
            if (fast.val != 9) slow = fast;
            fast = fast.next;
        }
        while (slow != null) {                             // slow之后的节点一定都是等于9的节点（或者slow自己就是最后一个节点）
            slow.val = (slow.val + 1) % 10;                // 将所有这些节点都自增，大于9的置为0.
            slow = slow.next;
        }
        return dummy.val == 1 ? dummy : dummy.next;        // 如果dummy节点收到了进位，就需要保留dummy节点，否则跳过。
    }


    /** 解法2：递归解法。<利用逆序递归>来反向扫描链表。Time - o(n), Space - o(1). */
    // 由于这里给出的链表形式是高位在链表头，如果想要从低位加1，无论如何都需要反向扫描链表，依次修改节点值。
    // 本身并不难，关键在于能否分析出所有的特殊情况并进行合理的处理。
    // <情况1> 如果尾节点不是9，那么唯一需要修改的就是尾节点。              1 -> 0 -> 0 -> null        to   1 -> 0 -> 1 -> null
    // <情况2> 如果尾节点是9，则修改区间是从尾节点到第一个不是9的节点。      1 -> 1 -> 9 -> 9 -> null   to   1 -> 2 -> 0 -> 0 -> null
    // <情况3> 如果链表的所有节点都是9，那么需要在链表头加上一个新的节点。    9 -> 9 -> 9 -> null        to   1 -> 0 -> 0 -> 0 -> null
    // <情况4> 如果是链表，则应该返回一个为1的节点。                      null                       to   1 -> null
    static ListNode plusOne2(ListNode head) {
        ListNode dummy = new ListNode(1);                              // 假设链表有进位，预先设置好一个值为1的dummy节点（但不一定真用）
        dummy.next = head;
        return plusOne_Recursive(dummy.next) == 1 ? dummy : dummy.next;   // 只有当原链表头返回了进位，dummy节点才会被使用。
    }

    static int plusOne_Recursive(ListNode head) {
        if (head == null) return 1;                             // 空链表应该返回进位1，这样dummy节点就能被使用
        int sum = head.val + plusOne_Recursive(head.next);      // 当前节点值 + 下个节点的进位值
        head.val = sum % 10;                                    // 归一化
        return sum / 10;                                        // 将进位值返回给上个节点
    }


    /** 解法1：最直觉的迭代解法。为了反向扫描链表，只能<反转链表>。Time - o(n), Space - o(1). */
    // 由于题目只涉及修改节点值，而不涉及修改链表结构（这对于链表题目还是挺少见的），因此可以反转链表再反转回来。
    static ListNode plusOne1(ListNode head) {
        ListNode newHead = reverseX(head);      // 反转链表
        ListNode curr = newHead;
        int carry = 1;                          // 低位加1
        while (curr != null) {
            int sum = curr.val + carry;
            curr.val = sum % 10;                // 本位
            carry = sum / 10;                   // 进位
            curr = curr.next;
        }
        newHead = reverseX(newHead);            // 再反转回来
        if (carry != 1) return newHead;
        ListNode dummy = new ListNode(1);
        dummy.next = newHead;
        return dummy;
    }

    static ListNode reverseX(ListNode curr) {
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
