package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/10/6.
 * Sort a linked list in o(nlogn) time and o(1) space.
 *
 * Function Signature:
 * public ListNode sort(ListNode head) {...}
 */
public class M148_Merge_Sort_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[]{9, 7, 5, 6});
        head = sort(head);
        while (head != null) {
            System.out.print(head.val + ", ");
            head = head.next;
        }
    }

    // 由于要求Space - o(1)，因此不考虑将链表转化为数组排序，不考虑使用Counting Sort / Bucket Sort / QuickSort
    // 由于要求Time - o(nlogn)，因此不考虑使用Insertion Sort，Selection Sort
    // 由于单链表的单向访问特性，使用Heap Sort将需要遍历链表非常多次来构造最大堆，使用QuickSort双指针相向移动实现起来很复杂
    // 因此最可能成功的方法只剩下了Merge Sort. 可是Merge Sort由于需要额外的空间进行merge，所以空间复杂度也是o(n)啊？
    // 看了别人的解法以后恍然大悟，没想到链表虽然有很多不方便的地方，但是在归并两个已排序链表的问题上，却比归并两个已排序数组的操作简单的多！
    // 这也是为什么虽然Merge Sort用在数组上很难做到o(1)空间复杂度，但是如果用在链表上，竟然就很容易的原因。
    // 疑问：如果相邻的两个节点的值顺序相反，与其交换两个节点位置，为什么不直接交换两个节点的内容呢？这不是更简单些么？
    // 这么做是可以，但是这就相当于将链表最重要的性质弃之不顾了，没有利用上链表的长项。

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
