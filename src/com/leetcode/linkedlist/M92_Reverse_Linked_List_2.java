package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/13.
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 *
 * For example:
 * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
 * return 1->4->3->2->5->NULL.
 *
 * Note:
 * Given m, n satisfy the following condition:
 * 1 ≤ m ≤ n ≤ length of list.
 *
 * Function Signature:
 * public ListNode reverse(ListNode head, int m, int n) {...}
 * */
public class M92_Reverse_Linked_List_2 {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        //ListNode reverseRange = reverseRange(head, 3, 6);
        ListNode reversed = reverseStd2(head);
    }

    // 参考下面两种链表完全反转的解法，可以看到：
    // 如果使用简单的调头解法，那么处理这个问题反而会比较复杂，因为调转之后首尾节点衔接需要很多额外处理
    // 区别1：如果m = 1，则head等于prev，如果m > 1，则head等于现在的head
    // 区别2：需要缓存指向第m个节点的指针，因为最后需要让原来的链表头指向第n+1个节点，
    // 区别3：需要缓存指向第m-1个节点的指针，因为最后要让第m-1个节点指向原来的第n个节点。
    // 如果使用看似比较没有人性的插入反转法，则有奇效，几乎不需要怎么额外处理，反转区域外的节点就自动都衔接好了。
    static ListNode reverseRange(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        for (int i = 0; i < m - 1; i++)
            prev = prev.next;
        ListNode start = prev.next;
        ListNode next = start.next;

        // 需要反转的元素一共有(n - m + 1)个，但是只需要操作(n - m)次，因为链表头已经自动成为最后一个元素，无需反转
        // 所以只需要循环 (n - m - 1) - 0 + 1 = (n - m)次
        for (int i = 0; i < n - m; i++) {
            start.next = next.next;
            next.next = prev.next;
            prev.next = next;
            next = start.next;
        }
        return dummy.next;
    }

    // 另一种反转链表的写法：不断把当前处理的节点插入到dummy.next，每插入一次，head.next的元素就会自动变为接下来要被插入的元素。
    // dummy → 1 → 2 → 3 → 4 → end    先让1指向3，然后让2指向1，然后让dummy指向2
    // dummy → (2) → 1 → 3 → 4 → end
    // dummy → (3) → 2 → 1 → 4 → end
    // dummy → (4) → 3 → 2 → 1 → end
    static ListNode reverseStd2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode next = head.next;      // 从第2个节点开始，不断把当前节点插入到dummy和head之间。
        while (next != null) {
            head.next = next.next;
            next.next = dummy.next;
            dummy.next = next;
            next = head.next;
        }
        return dummy.next;
    }

    // 最简单的链表反转写法：表头变表尾，让相邻节点指向反向
    static ListNode reverseStd(ListNode head) {
        ListNode prev = null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
