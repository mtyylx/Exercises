package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/14.
 * Given a sorted linked list, delete all nodes that have duplicate numbers,
 * leaving only distinct numbers from the original list.
 *
 * For example,
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * Given 1->1->1->2->3, return 2->3.
 *
 * Function Signature:
 * public ListNode removeDuplicate(ListNode head) {...}
 * */
public class M82_Remove_Duplicate_Nodes_2 {
    public static void main(String[] args) {
        ListNode head = ListNode.Generator(new int[] {1, 2, 2, 3, 3, 4, 4, 5});
        ListNode newhead = removeDuplicate2(head);
    }


    // 简化的写法：看了半天才基本弄明白，但是如果让我再写，估计写不出来
    // 把current移动到与当前current不再一样的节点上
    // 如果current与next并不相同，那么while循环就不会执行，那么就直接移动prev至下一节点即可
    // 反过来，只要进入了内部的while循环，就说明遇到了重复节点，也就说明prev必须跳过所有的这些节点，即prev.next = current.next
    static ListNode removeDuplicate2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode current = head;
        while (current != null) {
            while (current.next != null && current.next.val == current.val)
                current = current.next;
            if (prev.next == current)
                prev = prev.next;
            else
                prev.next = current.next;
            current = current.next;
        }
        return dummy.next;
    }

    // 最初的解法，没有章法，判断语句太多。
    static ListNode removeDuplicate(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode done = dummy;
        ListNode current = head;
        ListNode next;
        while (current != null) {
            if (current.next != null) next = current.next;
            else {
                done.next = current;
                done = done.next;
                break;
            }
            if (next.val == current.val) {
                while (next != null && next.val == current.val) {
                    next = next.next;
                }
                if (next != null) current = next;
                else {
                    done.next = null;
                    break;
                }
            }
            else {
                done.next = current;
                done = done.next;
                current = current.next;
            }
        }
        done.next = null;
        return dummy.next;
    }
}
