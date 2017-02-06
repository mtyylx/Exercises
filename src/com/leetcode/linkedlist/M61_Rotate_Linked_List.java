package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/14.
 * Given a list, rotate the list to the right by k places, where k is non-negative.
 * For example:
 * Given 1->2->3->4->5->NULL and k = 2,
 * return 4->5->1->2->3->NULL.
 *
 * Function Signature:
 * public ListNode rotateList(ListNode head, int step) {...}
 *
 * <Tags>
 * - <成环再拆环>：链表的独特用法。
 * - <切断再接续>：链表的传统反折方式。
 * - Two Pointers: 快慢指针等距扫描。[slow → → → ... fast → → → ... ]
 *
 */
public class M61_Rotate_Linked_List {
    public static void main(String[] args) {
        rotateList(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 2).print();
        rotateList2(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 2).print();
    }


    /** 解法2：双指针的创新用法（先成环 再平移 再拆环复原）。Time - o(n), Space - o(1). */
    // 通常链表的问题都以One-Pass为理想目标，但是这道题由于K可以是任意正整数，因此如果想不检查链表长度与k的关系就平移，会做无用功。
    // 这里我们还是需要先得到链表长度，然后将链表最后一个节点与头节点相连
    // 此时再使用双指针平移，最后快慢指针停下来的位置将刚好是新的头节点与尾节点的位置，最后把为尾节点拆掉循环即可。
    // 例如 k = 2，那么实际移动的次数是 5 - 2 = 3次
    // 1 -> 2 -> 3 -> 4 -> 5 -> 1 -> 2 -> 3 -> ...
    // ↑    ↑    ↑    ↑    ↑    ↑    ↑    ↑
    // s    ↑    ↑    ↑    f    ↑    ↑    ↑             起始位置
    //      s    ↑    ↑         f    ↑    ↑             第1次
    //           s    ↑              f    ↑             第2次
    //                s                   f             第3次
    //                4 -> 5 -> 1 -> 2 -> 3 -> null    （链表复原）
    //
    static ListNode rotateList2(ListNode head, int k) {
        if (head == null) return null;
        int count = 1;
        ListNode curr = head;
        for (; curr.next != null; count++) curr = curr.next;        // 提前检测终点，为了修改成环
        if (k % count == 0) return head;                            // Early exit
        curr.next = head;                                           // 链表成环
        k = count - k % count;
        ListNode newHead = head;                                    // 慢指针是newHead，快指针是curr
        for (int i = 0; i < k; i++) {
            curr = curr.next;
            newHead = newHead.next;
        }
        curr.next = null;                                           // 链表复原
        return newHead;
    }


    /** 解法1：传统方法，<链表切断和接续>。Time - o(n), Space - o(1). */
    // 由于k有可能很大，但是依然是链表长度的整数倍，因此完全无需修改链表，因此首先扫描一遍链表得到长度是很有必要的。
    //         1 -> 2 -> 3 -> 4 -> 5 -> null       k = 2, 移动步数需要提前停止，因此实际步数应该为 count - k - 1;
    //           左半部   ↑  |   右半部
    //                停止位置
    static ListNode rotateList(ListNode head, int k) {
        if (head == null) return null;
        int count = 0;
        ListNode curr = head;                               // 1st Scan
        for (; curr != null; count++) curr = curr.next;     // 首先获得链表长度
        if (k % count == 0) return head;                    // 先判断是否需要继续
        k = count - k % count - 1;                          // 计算实际移动步数，注意需要减1，因为需要提前停在左半部最后一个节点才能拆分。

        curr = head;                                        // 2nd Scan
        for (int i = 0; i < k; i++) curr = curr.next;       // 移动至左半部最后一个节点
        ListNode newHead = curr.next;                       // 缓存新的头节点（右半部第一个节点）
        curr.next = null;                                   // 清理左半部终点

        curr = newHead;                                     // 3rd Scan
        while (curr.next != null) curr = curr.next;         // 开始右半部扫描，移动至链表终点
        curr.next = head;                                   // 重新接合左右两部分
        return newHead;
    }
}
