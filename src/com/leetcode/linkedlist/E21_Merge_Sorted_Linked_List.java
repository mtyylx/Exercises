package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/12.
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 * 注意：这里强调是将现有链表的节点混合(Splice Together)，而不是拷贝值另建链表。
 *
 * Function Signature:
 * public ListNode merge(ListNode a, ListNode b) {...}
 *
 * <Tags>
 * - Dummy节点：自动处理链表头节点不确定的情况。
 * - Sentinel卫兵：单个While循环内完成两个长短不一的链表的扫描。
 * - 递归：利用链表每个节点的<自相似性>，正向递归。
 *
 */
public class E21_Merge_Sorted_Linked_List {
    public static void main(String[] args) {
        merge(ListNode.Generator(new int[] {1, 2, 3}), ListNode.Generator(new int[] {4, 5, 6})).print();
        merge2(ListNode.Generator(new int[] {1, 2, 3}), ListNode.Generator(new int[] {4, 5, 6})).print();
        merge3(ListNode.Generator(new int[] {1, 2, 3}), ListNode.Generator(new int[] {4, 5, 6})).print();
    }

    /** 解法3：递归（正向递归）。Time - o(n), Space - o(n). */
    // 优点是代码简洁，缺点是空间复杂度略高。
    // 首先上来就要设计递归的终止条件，两个链表中至少一个已经是空链表时，就返回另外一个表头。（这也包括了两个链表都是空链表时的情况，此时返回的是null，正合我意）
    // 链表的特性，使得链表的每个节点具有自相似性，因此每个节点都可以看作是他自己作为表头的链表，
    // 利用这个特性，我们每层递归只选定一个节点作为当前深度的表头，将落选的那个节点与当前表头的下一个元素进行递归，然后把表头返回给上一层。
    //        1 -> 2 -> 3        2 -> 3              3                             null
    // 表头为1 |           表头为2 |            表头为3 |             表头为4（递归终止） |
    //        4 -> 5 -> 6        4 -> 5 -> 6         4 -> 5 -> 6                   4 -> 5 -> 6
    static ListNode merge3(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;
        if (a.val < b.val) {                        // 执行到这，两个链表头一定都非空，可以直接比较val
            a.next = merge2(a.next, b);             // a做表头，递归解决a.next和b谁更小
            return a;                               // 递归完后，返回a
        }
        else {
            b.next = merge2(a, b.next);             // b做表头，递归解决a和b.next谁更小
            return b;                               // 递归完后，返回
        }
    }

    /** 解法2：迭代 + Dummy节点 + Sentinel卫兵。Time - o(n), Space - o(1). */
    // 使用卫兵的好处是可以在一个while循环中完成所有事情，不需要两个循环。
    // 因为每次只会挑选值更小的节点，因此对于已经是null的节点，只要设置成整型最大值即可。(不过如果节点值为最大整型值会出问题)
    // 另外由于结束while循环的条件一定是两个链表中更长的那个的最后一个节点也被收编，而该节点的下一个元素一定是null，因此无需清理。
    static ListNode merge2(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        while (a != null || b != null) {
            int x = (a != null) ? a.val : Integer.MAX_VALUE;
            int y = (b != null) ? b.val : Integer.MAX_VALUE;
            node.next = (x < y) ? a : b;
            node = node.next;
            if (x < y) a = a.next;
            else b = b.next;
        }
        return dummy.next;
    }

    /** 解法1：迭代 + Dummy节点 + 不使用Sentinel卫兵。Time - o(n), Space - o(1). */
    // 这里while循环结束之后，只需要把剩下未扫描的链表头连上即可，无需扫描到终点。
    static ListNode merge(ListNode a, ListNode b) {
        ListNode head = new ListNode(0);
        ListNode current = head;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                current.next = a;
                a = a.next;
            }
            else {
                current.next = b;
                b = b.next;
            }
            current = current.next;
        }
        if (a == null) current.next = b;        // concatenate the rest of the linked list.
        if (b == null) current.next = a;
        return head.next;
    }
}
