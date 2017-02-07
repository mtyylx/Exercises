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
        insertionSort(ListNode.Generator(new int[] {4, 5, 3, 2, 1, 0})).print();
        insertionSort2(ListNode.Generator(new int[] {6, 5, 4, 3})).print();
    }

    /**
     * 在使用各种数据结构的时候，<扬长避短>是很重要的思路。
     * <数组的优点>在于可以以任意方式随便访问任意数组元素，正序逆序怎么着访问复杂度都是o(1)
     * <数组的缺点>在于插入和删除元素的时候，操作复杂度一定是o(n)
     * 链表与数组的优缺点恰恰相反：
     * <链表的优点>在于插入和删除元素的复杂度是o(1)
     * <链表的缺点>在于访问元素只能通过遍历，即复杂度为o(n)，特别的，对于单链表是无法逆序遍历的。
     */


    // 迭代解法，time - o(n^2), space - o(1)
    // 由于传统数组的插入排序是逆序执行的（逆序平移，插入至停止点），因此想要将数组插入排序的算法用在链表上会卡在如何逆序移动的问题上，
    // 但是只要考虑清楚<链表的优点>在于任何地方插入节点的运算量都是o(1)，完全不用考虑逆序移动的问题，直接插入即可
    // 因此只要保证顺序扫描的时候保持双指针，检测到待插入节点比prev大比current小就可以插入在current的位置上了
    // dummy和prev是已排序数组，current是待插入元素
    // 下面算法设计的一个巧妙的地方在于让dummy和当前链表完全独立开，两者并不连在一起，dummy一开始指向null，然后不断把节点插入dummy链表
    // 相比于所有节点都在一个链表上，这么做的好处是可以省去判断已排序节点的终点位置
    static ListNode insertionSortt(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;   // 缓存current.next节点，因为current有可能被修改引用插入到前面
            // current与已排序链表（从prev.next开始）一一比较大小，
            // 如果扫描的的是第一个节点，此时dummy.next = null，已排序链表为null(prev.next = null)，就把current插入dummy与null之间
            // 如果已排序小，就继续扫描已排序，
            // 如果已排序大，就把current插入到prev与prev.next之间
            while (prev.next != null && prev.next.val <= curr.val)
                prev = prev.next;
            curr.next = prev.next;   // current后接prev.next
            prev.next = curr;        // prev后接current
            prev = dummy;               // 插入完成后重置已排序链表表头
            curr = next;             // current指针平移
        }
        return dummy.next;
    }


    /** 解法3：递归解法，基于数组插入排序的解法2（外循环利用逆序递归实现逆序扫描链表，内循环正序扫描） Time - o(n^2), Space - o(n). */
    //  unsorted | sorted
    //   ← 外循环   内循环 →
    // 外循环的实体就是递归的这个过程本身。首先递归至最后一个实体节点，并将这个节点视作已排序区域。
    // 内循环的实体就是递归的每一层返回操作。每向上返回一层，链表的已排序区域就会增长一个节点。
    // 难点在于插入节点的条件如何分离：
    // 1. curr.val <= curr.next.val 说明当前节点呆的位置刚好，不需要和curr.next打头的已排序链表交换位置。因此直接返回退出。
    // 2. curr.val > sort.val && sort.next == null 说明curr应该插入到sort的下个节点
    // 3. curr.val > sort.val && curr.val <= sort.val 说明curr应该插入到sort的下个节点
    // 4. curr.val > sort.val && sort.next != null && curr.val > sort.val 说明curr比sort和sort的下个节点都大，应该继续扫描已排序区域进一步确定插入位置。
    // 总结下来，
    // #1可以提前处理并返回，
    // #2+#3刚好与#4互斥，因此最后可以一句话做到处理三种情况：
    // while (curr.val > sort.val && sort.next != null && curr.val > sort.next.val) sort = sort.next;
    // 其中，sort.next != null是检查sort.next.val的先觉条件，如果sort.next已经是null了，那么就轮不到检查sort.next.val就可以退出了，避免异常。
    static ListNode insertionSort3(ListNode curr) {
        if (curr == null || curr.next == null) return curr;         // 递归至最后一个非空节点停止
        curr.next = insertionSort3(curr.next);                      // 返回的是后续链表经过排序后的新头节点（可能变了也可能没变），更新为当前节点的下个节点
        if (curr.val <= curr.next.val) return curr;                 // 开始检查当前节点与后续链表头的大小关系，如果小于的话就不用动直接返回当前节点
        ListNode sort = curr.next;                                  // 定义已排序区域的扫描指针
        ListNode newHead = curr.next;                               // 由于此时已经确定当前节点应该插入后续链表之中，因此新的头节点一定不是当前节点，需要记录下来
        while (curr.val > sort.val && sort.next != null && curr.val > sort.next.val) sort = sort.next;
        curr.next = sort.next;                                      // 将当前节点插入已排序区域指针的下个位置
        sort.next = curr;
        return newHead;
    }

    /** 解法2：类似于<链表反转的插入法>。与数组的插入排序思想有一定区别。*/
    // 显然对于链表来讲，逆序遍历是最需要避免的
    // 由于插入的过程头节点有可能会被修改，因此需要使用dummy节点。
    static ListNode insertionSort2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null) {
            ListNode sort = dummy;
            while (sort.next != curr && sort.next.val < curr.val) sort = sort.next;
            prev.next = curr.next;
            curr.next = sort.next;
            sort.next = curr;
            curr = prev.next;
        }
        return dummy.next;
    }

    /** 参考算法：数组插入排序的两种方式 */
    // 解法1：外循环正序遍历数组，已排序区域在左侧，内循环逆序遍历已排序区域
    //  sorted | unsorted
    // ← 内循环   外循环 →
    static void sort1(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int j, temp = a[i];
            for (j = i - 1; j >= 0 && a[j] > temp; j--)
                a[j + 1] = a[j];
            a[j + 1] = temp;
        }
    }
    // 解法2：外循环逆序遍历数组，已排序区域在右侧，内循环正序遍历已排序区域。
    //  unsorted | sorted
    //   ← 外循环   内循环 →
    static void sort2(int[] a) {
        for (int i = a.length - 2; i >= 0; i--) {
            int j, temp = a[i];
            for (j = i + 1; j < a.length && a[j] < temp; j++)
                a[j - 1] = a[j];
            a[j - 1] = temp;
        }
    }

    /** 解法1：转换为数组排序问题。执行速度最快，但是需要额外空间。Time - o(nlogn), Space - o(n). */
    // 说白了，对链表进行排序其实是个很傻的事情，完全可以拷贝到数组里排序，最后再拷贝回来。
    // 看上去复杂，但是实际上执行速度却最快。
    static ListNode insertionSort(ListNode head) {
        ListNode node = head;
        int length = 0;
        for (; node != null; length++) node = node.next;    // 1st Scan - Get length
        int[] a = new int[length];
        node = head;
        for (int i = 0; i < length; i++) {                  // 2nd Scan - Update array
            a[i] = node.val;
            node = node.next;
        }
        Arrays.sort(a);                                     // Cheating...
        node = head;
        for (int x : a) {                                   // 3rd Scan - Update linked list
            node.val = x;
            node = node.next;
        }
        return head;
    }
}
