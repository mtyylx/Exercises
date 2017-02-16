package com.leetcode.linkedlist;

/**
 * Created by Michael on 2017/2/15.
 *
 * Given a singly linked list L: L0 → L1 → … → Ln-1 → Ln,
 * reorder it to: L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
 * You must do this in-place without altering the nodes' values.
 *
 * For example,
 * Given {1, 2, 3, 4}, reorder it to {1, 4, 2, 3}.
 * Given {1, 2, 3}, reorder it to {1, 3, 2}.
 *
 * Function Signature:
 * public void reorderList(ListNode head) {...}
 *
 * <Tags>
 * - <Two Pointers>：快慢指针同向扫描（倍速双指针）定位链表中点。[slow → ... fast → → ... ]
 * - <链表拆分切断>：将链表拆分为两个独立的链表，为之后的操作提供方便。拆分的好处是可以避免在修改链表结构的过程中相互影响。
 * - <链表反转>：根据题目要求进行局部链表反转。
 * - <链表合并>
 *
 */
public class M143_Reorder_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5});
        reorderList(head);
        head.print();
        ListNode head2 = ListNode.Generator(new int[] {1, 2, 3, 4});
        reorderList(head2);
        head2.print();
    }

    /** 解法1：链表中点 + 链表反转 + 链表合并。Time - o(n), Space - o(1). */
    // 集大成者的一个问题，考察了求解链表中点、链表部分反转、链表拆分与合并。
    // 关键点1：倍速双指针定位链表中点。需要让慢指针停留在中间节点的左侧部分。
    // 例如 1 -> 2 -> 3 -> null，慢指针应该停留在2上
    // 例如 1 -> 2 -> null，慢指针应该停留在1上
    // 关键点2：在决定反转链表之前，应该将链表拆分，切断为两个独立互不影响的链表，这样在后续的合并过程中将会更省事。
    // 关键点3：合并链表的过程中，要考虑到只有可能出现右半部份长度小于左半部分的情况（对于奇数长度链表）
    // 因此循环条件应该是右侧链表抵达尾端。不过由于左侧链表已经切断，所以即使退出while循环，也无需将左边链表收尾。
    static void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;    // Early exit
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {                       // Two Pointers 倍速双指针定位链表中点
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = slow.next;                                                 // 缓存右半部分起点
        slow.next = null;                                                           // 将链表一分为二
        right = reverse(right);                                                     // 反转右半部分链表
        merge(head, right);                                                         // 合并左右链表
    }

    static void merge(ListNode left, ListNode right) {                              // 链表合并（两个缓存指针）
        while (right != null) {
            ListNode nextLeft = left.next;                                          // 分别缓存后边的节点
            ListNode nextRight = right.next;                                        // 分别缓存后边的节点
            left.next = right;                                                      // 接续
            left = nextLeft;                                                        // Move on
            right.next = left;                                                      // 接续
            right = nextRight;                                                      // Move on
        }
    }

    static ListNode reverse(ListNode curr) {                                        // 标准的链表反转（三指针）
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
