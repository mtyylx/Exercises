package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/10/6.
 * Sort a linked list in o(nlogn) time and o(1) space.
 *
 * Function Signature:
 * public ListNode sort(ListNode head) {...}
 *
 * <Tags>
 * - Merge Sort: 链表的归并与数组归并不同。
 *
 */
public class M148_Merge_Sort_Linked_List {
    public static void main(String[] args) {
        sort(ListNode.Generator(new int[] {4, 5, 3, 2, 1, 0})).print();
        sort(ListNode.Generator(new int[] {4, 5, 3, 2, 1, 0})).print();
    }

    /** 思路分析：Time - o(nlogn), Space - o(1) 的链表排序为什么必须用归并排序？ */
    // Space - o(1)意味着不能将链表转化为数组排序，也不考虑Counting Sort / Bucket Sort / QuickSort这些需要额外空间的排序算法
    // Time - o(nlogn)意味着不考虑Insertion Sort / Selection Sort这些需要o(n^2)时间的排序算法
    // 再加上单链表的单向访问特性，用Heap Sort将会遍历链表很多次来构造最大堆
    // 最后让我们再来看看Merge Sort。众所周知，归并排序分为两部分：分解和合并。通常认为合并部分的操作需要额外的空间才能完成。
    // 但实际上，上面的结论仅仅适用于数组排序。
    // 按顺序合并两个数组，如果想要在o(n)时间内完成，就一定需要o(n)的额外空间。
    // 按顺序合并两个链表，如果想要在o(n)时间内完成，却只需要o(1)的额外空间。
    // 尽管链表有很多弊端，但是在归并这件事上，归并链表比归并数组要更简单。

    /** 递归解法实现链表归并排序（而且还是原位归并）使用while循环 + 快慢指针确定链表中点，并斩断当前链表 */
    // 对于奇数个节点的链表，slow就指向中间的节点
    // 对于偶数个节点的链表，slow指向中间两节点之中的右边那个（相比之下数组首尾相加除二得到的是两节点中的左边那个）
    // 所以综上所述，只需要在slow所指节点之前斩断链表即可，
    // 即[start, mid - 1][mid, end]，而非数组(start + end)/2所得的[start, mid][mid + 1, end]，但本质一样。
    // 示例：{9, 7, 5, 6}
    // 9 -> 7 -> 5 -> 6 -> null (divide)
    // 9 -> 7 -> null (divide)
    // 9 -> null, 7 -> null (need merge)
    // 7 -> 9 -> null (sorted)
    // 5 -> 6 -> null (divide)
    // 5 -> null, 6 -> null (need merge)
    // 5 -> 6 -> null (sorted)
    // 7 -> 9 -> null, 5 -> 6 -> null (need merge)
    // 5 -> 6 -> 7 -> 9 -> null (sorted)

    static ListNode sort(ListNode head) {
        // 链表专用的递归终止条件写法：如果链表只剩下一个节点，那么肯定已排序，应该终止递归，直接返回当前链表的表头。
        if (head == null || head.next == null) return head;
        // 确定链表中点
        ListNode slow = head;
        ListNode fast = head;
        ListNode mid = head;
        while (fast != null && fast.next != null) {
            mid = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 斩断链表
        mid.next = null;
        head = sort(head);
        slow = sort(slow);
        return merge(head, slow);
    }

    // 用于链表的特殊merge方法，可以轻松做到原位合并链表：
    // 只需不断的把两个链表中元素小的那个接到新链表上，只占用一个dummy表头的空间，而对于数组就没有这种好事了。
    static ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (left != null && right != null) {
            current.next = (left.val < right.val) ? left : right;
            current = current.next;
            if (left.val < right.val)   left = left.next;
            else                        right = right.next;
        }
        current.next = (left != null) ? left : right;
        return dummy.next;
    }

}
