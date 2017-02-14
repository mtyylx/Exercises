package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/10/6.
 * Sort a linked list in o(nlogn) time and o(1) space.
 *
 * Function Signature:
 * public ListNode mergeSort(ListNode head) {...}
 *
 * <Tags>
 * - Merge Sort: 链表的归并可以做到Space - o(1)，但数组的归并通常只能做到Space - o(n).
 * - Two Pointers: 快慢指针（倍速双指针）同向扫描定位链表中点。[ slow → ... fast → → ... ]
 * - 链表拆分（切断）：在修改链表结构时常用的方法，用于避免在修改链表结构时成环或丢失节点。
 * - 利用Dummy节点进行链表迁移：用以按顺序重新组装链表的节点。
 *
 */
public class M148_Merge_Sort_Linked_List {
    public static void main(String[] args) {
        mergeSort(ListNode.Generator(new int[] {4, 5, 3, 2, 1, 0})).print();
        mergeSort(ListNode.Generator(new int[] {4, 5, 3, 2, 1, 0})).print();
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


    /** 解法1：双指针（快慢指针）进行链表分解切断 + 链表合并。Time - o(nlogn), Space - o(1). */
    // 链表的归并排序和数组一样分为<分解>和<合并>两个过程。但链表和数组在这两个过程的具体操作上是完全不同的。
    /** <链表分解>核心思路：利用快慢指针（倍速）寻找链表中点，当fast抵达右侧边界时，slow所在位置就是中点。*/
    /** 关键点1：奇偶长度下，中点应该停在什么位置。*/
    // 奇数长度的链表，slow应该停在链表中间节点位置。
    // 偶数长度的链表，slow应该停在链表中间两个节点的左侧节点。例如对于3 -> 2 -> null，slow应该停在节点3上。
    // 为了达到上面这两条，需要将循环条件设为fast.next != null && fast.next.next != null
    // 奇数长度示例
    //        3 -> 2 -> 1 -> null
    // slow   ↑    ↑               slow停在中间节点(2)
    // fast   ↑         ↑
    // 偶数长度示例
    //        3 -> 2 -> null
    // slow   ↑                    slow停在中间两个节点的左侧节点(3)
    // fast   ↑
    /** 关键点2：每次分解都需要从中点将链表直接切断。*/
    // 我在一开始试图用归并对链表进行排序的时候，没有意识到应该在分解的同时把链表的左右两部分直接切断。
    // 这样的后果就是，即使递归的下一层的merge方法已经将链表局部排序，但是由于上一层split的区间还是原来节点的位置，就会导致有一些节点直接丢失。
    // 不断的分解加切断链表本质就是将链表拆散为一个一个的节点，最后组装起来的过程。这与数组归并排序的迭代解法是完全一样的。
    // 奇数长度示例
    //        3 -> 2 -> 1 -> null  分解为   3 -> 2 -> null    +     1 -> null
    // slow        ↑
    // 偶数长度示例
    //        3 -> 2 -> null       分解为   3 -> null    +     1 -> null
    // slow   ↑
    /** <链表合并>核心思路：利用Dummy节点的链表迁移法，将节点按顺序组装至Dummy节点所引领的新链表。 */
    // 整体过程实例分析
    // 9 -> 8 -> 7 -> 6 -> 5
    // 9 -> 8 -> 7 -> null      6 -> 5 -> null
    // 9 -> 8 -> null           7 -> null           6 -> 5 -> null
    // 9 -> null                8 -> null           7 -> null            6 -> 5 -> null
    // 8 -> 9 -> null           7 -> null           6 -> 5 -> null
    // 7 -> 8 -> 9 -> null      6 -> 5 -> null
    // 7 -> 8 -> 9 -> null      6 -> null           5 -> null
    // 7 -> 8 -> 9 -> null      5 -> 6 -> null
    // 5 -> 6 -> 7 -> 8 -> 9 -> null
    static ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) return head;   // 递归终止条件：如果当前链表为空或只有一个节点，则无需再分解。
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) { // 倍速双指针：确保slow会提前停在中点（奇数）或中点左侧（偶数）
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = slow.next;                           // 缓存右半部起点
        slow.next = null;                                     // 将链表从中点位置拆分。
        head = mergeSort(head);                               // 递归左半部，并记录返回的新表头
        right = mergeSort(right);                             // 递归右半部，并记录返回的新表头
        return merge(head, right);                            // 根据新表头进行合并
    }

    // 将两个链表按大小顺序一个一个的迁移至Dummy节点引领的新链表上（原位操作），这也是区别于数组归并排序的关键之一。
    static ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (left != null && right != null) {                                      // 只需将其中一个链表扫描完成即可，无需两个链表全扫完
            if (left.val < right.val) { curr.next = left; left = left.next; }
            else                      { curr.next = right; right = right.next; }
            curr = curr.next;
        }
        if (left == null) curr.next = right;            // 收尾工作：如果其中一个链表尚未扫完，就直接把curr.next接在这个链表上即可
        else              curr.next = left;
        return dummy.next;
    }

}
