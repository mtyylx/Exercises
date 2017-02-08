package com.leetcode.sort;

import com.leetcode.linkedlist.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/13.
 *
 * Basic Algorithm: Insertion Sort
 * Time - o(n^2)
 * Space - o(1)
 */
public class Basic_Insertion_Sort {
    public static void main(String[] args) {
        // For Array
        int[] a = {4, 5, 3, 2, 1, 0};
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
    }

    /** 插入排序的思想：外循环扫描整个数组，内循环扫描已排序区间并对相邻元素进行平移，最后将未排序元素插入已排序区间的合适位置。*/
    // 作为对比，选择排序的思想是选出未排序区间的最大或最小元素，将其与已排序区间的头或尾元素交换。
    // 插入排序需要双指针实现，
    // 对于<数组>的插入排序，两个指针的运动方向向背（中心扩散方式）
    // 对于<链表>的插入排序，两个指针则可以同向运动


    /** 数组解法1：外循环正序遍历数组，已排序区域在左侧，内循环逆序遍历已排序区域 */
    //  sorted | unsorted
    // ← 内循环   外循环 →
    static void InsertionSort1(int[] a) {
        for (int i = 1; i < a.length; i++) {                /* 外循环：<正序扫描整个数组> */
            int j;                                          // 需要提前定义内循环指针，这样退出内循环后才可以将当前元素值复原。
            int curr = a[i];                                // 需要缓存当前元素值，因为内循环将会覆盖当前元素
            for (j = i - 1; j >= 0 && a[j] > curr; j--)     /* 内循环：<逆序扫描已排序区间> */
                a[j + 1] = a[j];                            // 统一<向右平移>拷贝元素，直至j < 0 或 a[j] <= curr
            a[j + 1] = curr;                                // 将curr复原至数组合适位置
        }
    }

    /** 数组解法2：外循环逆序遍历数组，已排序区域在右侧，内循环正序遍历已排序区域 */
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

    /** 为什么数组插入排序的双指针是反向的？是否可以让双指针同向扫描。因为数组插入元素的操作只能通过平移来达到。*/
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

    /** 链表解法：为什么链表插入排序的双指针就可以同向扫描？因为链表插入元素可以直接插入无需修改前后节点。*/
    // 链表不需要平移插入，而是可以直接插入。因此可以使用同向的双指针扫描。
    static ListNode InsertionSort_LinkedList(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode curr = head;
        while (curr != null) {                                      // 外循环：curr指针正序扫描整个链表
            ListNode next = curr.next;                              // 缓存curr的下个节点，因为curr即将脱离大部队
            ListNode sort = dummy;                                  // 内循环：sort指针从dummy开始，同样顺序扫描已排序部分
            while (sort.next != null && curr.val > sort.next.val)
                sort = sort.next;
            curr.next = sort.next;                                  // 找到curr的插入位置，将curr插入sort.next
            sort.next = curr;
            curr = next;                                            // curr移动至原链表下一个节点
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

    public static void bulkTest() {
        for (int j = 2; j < 100; j += 2) {
            for (int i = 1; i < 100; i++) {
                int[] x = randGen(i, j);
                System.out.println("Original: " + Arrays.toString(x));

                if (!isSorted(x)) {
                    System.out.println("Failed at: " + Arrays.toString(x));
                    return;
                }
                else System.out.println("Passed at: " + Arrays.toString(x));
            }
        }
        System.out.println("Passed.");
    }

    public static boolean isSorted(int[] a) {
        if (a == null || a.length < 2) return true;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }

    private static int[] randGen(int len, int range) {
        int[] a = new int[len];
        for (int i = 0; i < a.length; i++)
            a[i] = (int) (range * Math.random());
        return a;
    }

}
