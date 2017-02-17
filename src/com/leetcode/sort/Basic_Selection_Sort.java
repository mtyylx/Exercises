package com.leetcode.sort;

import com.leetcode.linkedlist.ListNode;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/9/27.
 *
 * Basic Algorithm: Selection Sort
 *
 * <Core Mechanism>
 * Scan unsorted + Find min index + Swap with the first element in the unsorted part.
 *
 * Time - o(n^2)
 * Space - o(1)
 *
 * <Tags>
 * - Two Pointers: 双指针同向扫描。[ i → → → ... j → → → ... ].
 * - Dummy节点：动态链表头。原链表的表头节点有可能被删除。
 * - Dummy节点：迁移法。将节点迁移至Dummy节点引领的新链表上。
 * - Early Detection：为确保链表节点插入和删除成功，必须让指针停留在侦察位置之前。
 *
 */
public class Basic_Selection_Sort extends SortMethod {
    public static void main(String[] args) {
        // For Arrays
        int[] a = new int[] {9, 7, 6, 5, 4, 2, 3, 8, 1};
        SelectionSort(a);
        System.out.println(Arrays.toString(a));

        // For LinkedList
        SelectionSort_LinkedList(ListNode.Generator(new int[] {4, 5, 3, 2, 1})).print();

        // Bulk Test
        //SortUtility.VerifySortAlgorithm("selection");
    }

    /** 数组解法：双指针同向扫描。Time - o(n^2), Space - o(1). */
    // 外循环指针用来限定未排序数组的起始位置，并随着元素的排序，该区间逐渐收缩
    // 内循环正序扫描以外循环指针为起点的未排序数组区间，并记录该区间中数值最小的元素索引
    // 内循环结束后，将该区间内的最小元素与外循环指针元素交换，并将外循环指针后移（已排序区间壮大，未排序区间缩小）
    // |  4  5  3  2  1  0
    //    ↑              ↑
    //    i             min
    // 0  |  5  3  2  1  4
    //       ↑        ↑
    //       i       min
    // 0  1  |  3  2  5  4
    //          ↑  ↑
    //          i min
    // 0  1  2  |  3  5  4
    //             ↑
    //            i,min
    // 0  1  2  3  |  5  4
    //                ↑  ↑
    //                i min
    // 0  1  2  3  4  |  5
    //                   ↑
    //                  i,min
    // 0  1  2  3  4  5  |
    static void SelectionSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i; j < a.length; j++)
                if (a[j] < a[min]) min = j;
            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }

    /** 链表解法：Dummy节点迁移法及动态链表头法 + Early Detection + 链表节点插入 + 链表节点删除。Time - o(n), Space - o(1). */
    // 为了避免修改链表结构导致链表断开或节点丢失，需要使用Dummy节点的迁移法，将已排序链表和未排序链表独立来开，省去不断接续的麻烦。
    // 未排序链表就是原链表本身，外循环每次都会完整的扫描一次，并记录具有最小值的节点的上一个位置（Early Detection），
    // 然后将该节点迁移至新的已排序链表：既要确保该节点接续在新链表尾部，还要确保原链表在删除该节点之后，依然能够接续。
    // dummy -> 4 -> 5 -> 3 -> 2 -> null      newHead -> null
    //                    ↑                             ↑
    //                   min                           sort
    // dummy -> 4 -> 5 -> 3 -> null           newHead -> 2 -> null
    //                    ↑                                     ↑
    //                   min                                   sort
    // dummy -> 4 -> 5 -> null                newHead -> 2 -> 3 -> null
    //   ↑                                                           ↑
    //  min                                                         sort
    // dummy -> 5 -> null                     newHead -> 2 -> 3 -> 4 -> null
    //   ↑                                                                ↑
    //  min                                                              sort
    // dummy -> null                          newHead -> 2 -> 3 -> 4 -> 5 -> null
    static ListNode SelectionSort_LinkedList(ListNode head) {
        ListNode dummy = new ListNode(0);                       // 原链表由于表头有可能被迁移，因此需要使用动态链表头
        ListNode newHead = new ListNode(0);                     // 新链表由于会接受迁移的节点，因此需要使用迁移的Dummy表头
        dummy.next = head;
        ListNode sort = newHead;                                  // sort指向已排序链表的尾端
        while (dummy.next != null) {
            ListNode curr = dummy;                                // curr用于遍历整个未排序链表
            ListNode min = curr;                                  // min指向未排序链表中值最小节点的上个节点
            while (curr.next != null) {
                if (curr.next.val < min.next.val) min = curr;
                curr = curr.next;
            }
            sort.next = min.next;                                 // 首先将最小节点接入新链表
            min.next = min.next.next;                             // 然后将最小节点从未排序链表中移除
            sort = sort.next;                                     // 同时保持sort指向已排序链表尾端
        }
        return newHead.next;
    }

    @Override
    public void sort(int[] a) {
        SelectionSort(a);
    }
}
