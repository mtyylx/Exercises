package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/15.
 * Given a singly linked list, group all odd nodes together followed by the even nodes.
 * Please note here we are talking about the node number and not the value in the nodes.
 * You should try to do it in place.
 * The program should run in O(1) space complexity and O(nodes) time complexity.
 *
 * Example:
 * Given 1->2->3->4->5->NULL,
 * return 1->3->5->2->4->NULL.
 *
 * Note:
 * The relative order inside both the even and odd groups should remain as it was in the input.
 * The first node is considered odd, the second node even and so on ...
 *
 * Function Signature:
 * public ListNode splitOddEven(ListNode head) {...}
 *
 * <Tags>
 * - Two Pointers: 同向等速扫描。[slow → → → ... fast → → → ... ]
 * - 利用Dummy节点进行链表迁移：Dummy节点的特殊用法。并不在一开始就把Dummy节点于链表头相连，而是不断的把当前链表的节点迁移至Dummy节点打头的新链表上。
 *
 */
public class M328_Odd_Even_Linked_List {
    public static void main(String[] args) {
        splitOddEven(ListNode.Generator(new int[] {1, 2, 3})).print();
        splitOddEven(ListNode.Generator(new int[] {1, 2, 3, 4})).print();
        splitOddEven2(ListNode.Generator(new int[] {1, 2, 3})).print();
        splitOddEven2(ListNode.Generator(new int[] {1, 2, 3, 4})).print();
    }

    /** 解法1：双指针（同向等速扫描）。Time - o(n), Space - o(1). */
    // 需要将任意长度的链表归纳为两种一般的情况：
    // 奇数长度链表终止情况： ... -> odd -> null          即even == null (或odd.next == null也一样）
    // 偶数长度链表终止情况： ... -> odd -> even -> null  即even.next == null (或odd.next.next == null也一样）
    // 循环继续的前提是没有出现上述两种终止情况，因此循环条件应该设置为(even != null && even.next != null)
    static ListNode splitOddEven(ListNode head) {
        if (head == null || head.next == null) return head;     // 直接跳过空链表和只有一个节点的链表
        ListNode odd = head;                                    // 奇指针
        ListNode even = head.next;                              // 偶指针
        ListNode evenHead = head.next;                          // 缓存偶链表的头指针
        while (even != null && even.next != null) {
            odd.next = odd.next.next;                           // 跳过当前even节点
            odd = odd.next;                                     // 移动至下一个odd节点
            even.next = even.next.next;                         // 跳过下一个odd节点
            even = even.next;                                   // 移动至下一个even节点
        }
        odd.next = evenHead;                                    // 将奇偶链表相连接
        return head;
    }

    /** 解法2：原链表迁移至Dummy节点的解法。Time - o(n), Space - o(1). */
    // 这里的Dummy节点并没有以上就和链表头相连，而是独立的存在着的
    // 循环的作用是不断的把奇偶节点从当前链表拆掉，并放置到两个dummy节点引领的奇偶链表上，最后再把两个链表相连即可。
    // 这种解法思路虽然很独特，但是用在这里并不简洁。
    static ListNode splitOddEven2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy1 = new ListNode(0);      // 只放奇数节点的链表
        ListNode dummy2 = new ListNode(0);      // 只放偶数节点的链表
        ListNode curr1 = dummy1;
        ListNode curr2 = dummy2;
        ListNode odd = head;
        ListNode even = head.next;
        while (even != null || odd != null) {
            ListNode nextOdd = (even == null) ? null : even.next;
            ListNode nextEven = (nextOdd == null) ? null : nextOdd.next;
            curr1.next = odd;
            curr1 = curr1.next;
            curr2.next = even;
            curr2 = curr2.next;
            odd = nextOdd;
            even = nextEven;
        }
        curr1.next = dummy2.next;
        return dummy1.next;
    }
}
