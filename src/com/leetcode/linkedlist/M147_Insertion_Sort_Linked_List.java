package com.leetcode.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/13.
 * Sort a linked list using insertion sort.
 *
 * Function Signature:
 * public ListNode insertionSort(ListNode head) {...}
 */
public class M147_Insertion_Sort_Linked_List {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 4, 2, 8, 5, 7, 3, 9, 7, 5, 6});
        ListNode sorted = insertionSort2(head);
    }

    // 其实对链表进行排序是个很傻的事情，完全可以拷贝到数组里排序再拷贝回来...
    // 第一遍扫描获得长度
    // 第二遍扫描拷贝数组
    // 第三遍扫描覆盖链表
    static ListNode insertionSort3(ListNode head) {
        ListNode node = head;
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }
        int[] array = new int[length];
        node = head;
        for (int i = 0; i < length; i++) {
            array[i] = node.val;
            node = node.next;
        }
        Arrays.sort(array);
        node = head;
        for (int x : array) {
            node.val = x;
            node = node.next;
        }
        return head;
    }

    // 在使用各种数据结构的时候扬长避短是很重要的思路：
    // 数组的优点在于可以以任意方式随便访问任意数组元素，正序逆序怎么着访问复杂度都是o(1)
    // 数组的缺点在于插入和删除元素的时候，操作复杂度一定是o(n)
    // 链表与数组的优缺点恰恰相反：
    // 链表的优点在于插入和删除元素的复杂度是o(1)
    // 链表的缺点在于访问元素只能通过遍历，即复杂度为o(n)，特别的，对于单链表是无法逆序遍历的。

    // 迭代解法，time - o(n^2), space - o(1)
    // 由于传统数组的插入排序是逆序执行的（逆序平移），因此想要将数组插入排序的算法用在链表上会卡在如何逆序移动的问题上，
    // 但是只要考虑清楚<链表的优点>在于任何地方插入节点的运算量都是o(1)，完全不用考虑逆序移动的问题
    // 因此只要保证顺序扫描的时候保持双指针，检测到待插入节点比prev大比current小就可以插入在current的位置上了
    // 下面的解法中，dummy和prev是已排序数组，current是待插入元素
    static ListNode insertionSort2(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;   // 缓存current.next节点，因为current有可能被修改引用插入到前面
            // current与已排序链表（从prev.next开始）一一比较大小，
            // 如果已排序小就继续扫描已排序，如果current小就把current插入到prev与prev.next之间
            while (prev.next != null && prev.next.val <= current.val)
                prev = prev.next;
            current.next = prev.next;   // current后接prev.next
            prev.next = current;        // prev后接current
            prev = dummy;               // 插入完成后重置已排序链表表头
            current = next;             // current指针平移
        }
        return dummy.next;
    }

    // 递归解法（逆序递归），time - o(n^n), space - o(n)
    // 假设返回的newhead已经有序，那么只需要把当前的head插入至newHead作为表头的链表中即可
    // 使用dummy节点指向newHead表头，来处理当前节点就是表头的情况
    static ListNode insertionSort(ListNode head) {
        if (head == null) return head;
        ListNode newHead = insertionSort(head.next);
        ListNode dummy = new ListNode(0);
        dummy.next = newHead;
        ListNode current = dummy;
        while (current.next != null) {
            if (head.val <= current.next.val) {
                head.next = current.next;
                current.next = head;
                return dummy.next;
            }
            else current = current.next;
        }
        current.next = head;
        head.next = null;   // 针对head是最后一个元素的情况下，由于head.next原本有值，因此要记得清理，避免成环。
        return dummy.next;
    }
}
