package com.leetcode.sort;

import com.leetcode.linkedlist.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/13.
 *
 * Basic Algorithm: Insertion Sort
 *
 * <Core Mechanism>
 * Scan Unsorted part + Insert each unsorted element into the correct position of Sorted part.
 *
 * - Time - o(n^2)
 * - Space - o(1)
 *
 * <Tags>
 * - Two Pointers: <双指针中心扩散扫描>。外循环扫描未排序元素，内循环扫描已排序元素。
 *                 [ ... ← ← ← sorted | unsorted → → → ... ]
 *                 [ ... ← ← ← unsorted | sorted → → → ... ]
 * - Two Pointers: <双指针同向扫描>。
 *                 Sorted LinkedList: [ sorted → → → ... ]    Unsorted LinkedList: [ unsorted → → → ... ]
 * - Dummy节点：<Dummy节点迁移法>。将原链表元素依次迁移至Dummy节点引领的新已排序链表。
 * - Early Detection：为了检测并及时提前插入节点，需要检测curr.next而不是curr。
 *
 */
public class Basic_Insertion_Sort extends SortMethod {
    public static void main(String[] args) {
        // For Array
        int[] a = {0, 0, 1, 0};
        InsertionSort1(a);
        System.out.println(Arrays.toString(a));
        int[] b = {4, 5, 3, 2, 1, 0};
        InsertionSort2(b);
        System.out.println(Arrays.toString(b));

        // For Linked List
        InsertionSort_LinkedList(ListNode.Generator(new int[] {4, 5, 3, 2, 1, 0})).print();

        // For ArrayList
        List<Integer> list = new ArrayList<>(Arrays.asList(4, 5, 3, 2, 1, 0));
        InsertionSort_ArrayList(list);
        System.out.println(list);

        // Bulk Test
        SortUtility.VerifySortAlgorithm("insertion");
    }

    /** 插入排序的思想：外循环扫描整个数组，内循环扫描已排序区间并对相邻元素进行平移，最后将未排序元素插入已排序区间的合适位置。*/
    // 作为对比，选择排序的思想是选出未排序区间的最大或最小元素，将其与已排序区间的头或尾元素交换。
    // 插入排序需要双指针实现，
    // 对于<数组>的插入排序，两个指针的运动方向向背（中心扩散方式）：原因是数组无法原位插入元素，但可以依次反向平移。
    // 对于<链表>的插入排序，两个指针的运动方向相同（同向扫描方式）：原因是链表可以原位插入节点，且链表无法反向扫描。


    /** 数组解法1：双指针（中心扩散扫描）。外循环正序遍历数组，已排序区域在左侧，内循环逆序遍历已排序区域 */
    //  sorted | unsorted
    // ← 内循环   外循环 →
    // 易错点1：内循环条件应该比较a[j]和curr之间的大小关系，而不是a[j]与a[i]之间的，因为a[i]此时可能已经被修改。
    // 易错点2：需要缓存外循环索引对应的元素值，因为平移会导致该值暂时被覆盖。
    // 易错点3：需要提前定义内循环指针，因为内循环结束之后，还需要该指针将被覆盖的元素恢复。
    static void InsertionSort1(int[] a) {
        for (int i = 1; i < a.length; i++) {                /* 外循环：<正序扫描整个数组> */
            int j;                                          // 需要提前定义内循环指针，这样退出内循环后才可以将当前元素值复原。
            int curr = a[i];                                // 需要缓存当前元素值，因为内循环将会覆盖当前元素
            for (j = i - 1; j >= 0 && a[j] > curr; j--)     /* 内循环：<逆序扫描已排序区间> */
                a[j + 1] = a[j];                            // 统一<向右平移>拷贝元素，直至j < 0 或 a[j] <= curr
            a[j + 1] = curr;                                // 将curr复原至数组合适位置
        }
    }

    /** 数组解法2：双指针（中心扩散扫描）。外循环逆序遍历数组，已排序区域在右侧，内循环正序遍历已排序区域 */
    //  unsorted | sorted
    //   ← 外循环   内循环 →
    static void InsertionSort2(int[] a) {
        for (int i = a.length - 2; i >= 0; i--) {                 /* 外循环：<逆序扫描整个数组> */
            int j;                                                // 需要提前定义内循环指针
            int curr = a[i];                                      // 需要缓存当前元素值
            for (j = i + 1; j < a.length && a[j] < curr; j++)     /* 内循环：<逆序扫描已排序区间> */
                a[j - 1] = a[j];                                  // 统一<向左平移>拷贝元素，直至j > a.length 或 a[j] >= curr
            a[j - 1] = curr;                                      // 将curr复原至数组合适位置
        }
    }

    /** 实例分析 */
    //  sorted | unsorted                         unsorted | sorted
    // ← 内循环   外循环 →                           ← 外循环   内循环 →
    //                          示例 4 5 3 2
    // curr = 5      4 | 5 3 2                curr = 3       4 5 3 | 2
    // curr = 3      4 5 | 3 2                                     ↙
    //                   ↘                                   4 5 2 | 2
    //               4 5 | 5 2                Recover curr   4 5 2 | 3
    //                ↘                       curr = 5       4 5 | 2 3
    //               4 4 | 5 2                                   ↙
    // Recover curr  3 4 | 5 2                               4 2 | 2 3
    // curr = 2      3 4 5 | 2                                      ↙
    //                     ↘                                 4 2 | 3 3
    //               3 4 5 | 5                Recover curr   4 2 | 3 5
    //                  ↘                     curr = 4       4 | 2 3 5
    //               3 4 4 | 5                                 ↙
    //                ↘                                      2 | 2 3 5
    //               3 3 4 | 5                               2 | 3 3 5
    // Recover curr  2 3 4 | 5                Recover curr   2 | 3 4 5
    // Sorted        2 3 4 5                  Sorted         2 3 4 5

    /** 为什么数组插入排序的双指针是反向的？这是因为想要往数组中插入元素，只能通过<依次平移数组元素>来达到。*/
    // 之所以插入排序使用的是<中心远离方式>的双指针，是因为这么扫描对于数组平移最方便。
    // 使用<同向扫描方式>双指针不是不可以，但是用代码实现时就会发现，由于数组插入元素只能靠平移，因此需要多一层while循环，代码不够简洁。
    //      中心远离方式                     同向扫描方式
    //  sorted | unsorted               sorted | unsorted
    // ← 内循环   外循环 →                内循环 →  外循环 →
    static void InsertionSort_SameDirectionScan(int[] a) {
        for (int i = 1; i < a.length; i++) {        // 外循环正向扫描
            int j = 0;
            for (; j < i && a[j] < a[i]; j++);      // 内循环也是正向扫描，但是需要首先确定插入位置
            int temp = a[i];
            int k = i - 1;
            for (; k >= j - 1; k--)                 // 然后还是需要反向依次平移，最后才插入。
                a[k + 1] = a[k];
            a[k + 1] = temp;
        }
    }

    /** 链表解法：双指针（同向扫描） + Dummy节点（迁移法） + 链表节点插入。Time - o(n^2), Space - o(1). */
    // 链表的插入排序与数组差别较大。
    // 区别1：数组插入排序的全程都是在数组内部进行的，而链表插入排序则灵活的多，整个过程是未排序链表（原链表）不断变短，同时已排序链表不断变长的过程。
    // 区别2：数组插入排序由于无法将节点直接插入，需要靠平移，因此需要让双指针反向扫描。而链表则可以直接将一个节点插入已排序链表中，因此可以同向扫描。
    // 具体逻辑分析：
    // 外循环：依次扫描未排序链表（原链表）的每个节点，并准备将该节点从原链表中剥离，加入到已排序的新链表之中的合适位置。
    // 内循环：每次都从已排序链表（Dummy节点引领）的表头开始扫描，将指针停在最后一个小于待插入节点值的节点上，将带插入节点插入到该节点的后面。
    // 示例分析：
    //   4 -> 7 -> 6 -> 9 -> null      Dummy -> null
    //   ↑                               ↑
    // curr                             sort
    //        7 -> 6 -> 9 -> null      Dummy -> 4 -> null
    //        ↑                                 ↑
    //       curr                              sort
    //             6 -> 9 -> null      Dummy -> 4 -> 7 -> null
    //             ↑                            ↑
    //            curr                         sort
    //                  9 -> null      Dummy -> 4 -> 6 -> 7 -> null
    //                  ↑                                 ↑
    //                 curr                             sort
    //                        null     Dummy -> 4 -> 6 -> 7 -> 9 -> null
    //                         ↑
    //                        curr
    static ListNode InsertionSort_LinkedList(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode curr = head;
        while (curr != null) {                                      // 外循环正序扫描整个未排序链表
            ListNode sort = dummy;                                  // 内循环指针每次都要从头扫描已排序链表
            ListNode next = curr.next;                              // 缓存外循环指针的下个节点，因为当前节点即将脱离大部队
            while (sort.next != null && curr.val > sort.next.val)   // 内循环指针停留在最后一个小于外循环节点的节点上
                sort = sort.next;
            curr.next = sort.next;                                  // 将外循环节点（未排序）插入内循环节点之后，使链表依然有序
            sort.next = curr;
            curr = next;                                            // 外循环指针移至缓存的下一个节点
        }
        return dummy.next;
    }

    /** ArrayList解法：操作与链表类似。 */
    static void InsertionSort_ArrayList(List<Integer> a) {
        for (int i = 1; i < a.size(); i++) {
            int temp = a.get(i);
            int j;
            for (j = i - 1; j >= 0 && a.get(j) > temp; j--);
            if (j != i - 1) {
                a.remove(i);
                a.add(j + 1, temp);
            }
        }
    }

    @Override
    public void sort(int[] a) {
        InsertionSort1(a);
    }

}
