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
 *
 * <链表重复节点删除 系列问题>
 * E83 Remove Duplicate Nodes   : 给定一个已排序单链表，删除多余的重复节点。
 * M82 Remove Duplicate Nodes 2 : 给定一个已排序单链表，只要节点值出现重复就删除。
 *
 * <Tags>
 * - Two Pointers: 快慢指针同向扫描. [slow → → → ... fast → → → ...]
 * - Dummy节点：由于链表头节点有可能会被删除。
 *
 */
public class M82_Remove_Duplicate_Nodes_2 {
    public static void main(String[] args) {
        removeDuplicate(ListNode.Generator(new int[] {1, 1, 2, 2, 3, 4, 4, 4, 5, 5, 5})).print();
        removeDuplicate2(ListNode.Generator(new int[] {1, 1, 2, 2, 3, 4, 4, 4, 5, 5, 5})).print();
    }


    /** 解法1：双指针（快慢指针）+ Dummy节点. Time - o(n), Space - o(1). */
    // 由于涉及到head被删除的情况，所以肯定要用dummy节点。
    // slow从dummy节点出发，fast从head出发，可以自动处理head为空节点的情况。
    // 关键在于slow和fast的移动逻辑：
    // slow所指节点及其左侧都是完成扫描的节点，已经跳过了出现重复的节点
    // fast冲锋陷阵不断踩雷，比较fast和fast.next是否值相等，出现相等就记录到标志位上
    // 如果fast和fast.next相等就不断后移，直到fast和fast.next不相等或fast.next是空节点停下来
    // 如果标志位是true，就说明slow之后、fast之前（包括fast）的节点都需要被跳过，
    // 但是此时并不能急于将slow移动至fast.next，因为此时我们还不清楚fast.next是否就符合要求，例如 2 2 3 3 4 4 这种就不符合要求，
    // 因此应该做的是先更新slow.next的指针。
    // 如果标志为是false，就说明可以移动slow指针了。
    // dummy -> 1 -> 1 -> 2 -> 3 -> 3       初始状态
    //   ↑      ↑
    //   slow   fast
    // dummy -> 1 -> 1 -> 2 -> 3 -> 3       while循环结束，fast停在不同节点之前
    //   ↑           ↑    ↑
    //   slow        fast fast.next
    // dummy -> 2 -> 3 -> 3                 更新slow.next
    //   ↑      ↑    ↑
    //   slow   fast fast.next
    // dummy -> 2 -> 3 -> 3                 更新slow
    //          ↑    ↑
    //        slow   fast
    static ListNode removeDuplicate(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = head;
        while (fast != null) {
            boolean duplicate = false;                                  // 标志fast和fast.next是否重复
            while (fast.next != null && fast.val == fast.next.val) {    // 确保退出while循环时要不然fast.next == null或者fast.next值与fast不等
                fast = fast.next;
                duplicate = true;
            }
            if (!duplicate) slow = slow.next;               // count = 0, 则节点值唯一，slow直接移动
            else            slow.next = fast.next;          // count > 0, 则节点值有重复，slow不移动，先更新slow.next（有可能更新多次才移动）
            fast = fast.next;                               // fast继续冲锋陷阵
        }
        return dummy.next;
    }

    // 简化写法：省略定义标志位。初始状态下，slow.next == fast，以后每个while循环结束之后，由于slow就会移动至fast的上一个节点，
    // 因此，如果fast与fast.next不同的话，fast也就不会移动，因此slow.next也就一定是fast
    // 因此判断是否出现重复节点不需要单设置一个标志位，而是直接判断slow.next是否等于fast即可
    static ListNode removeDuplicate2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = head;
        while (fast != null) {
            while (fast.next != null && fast.next.val == fast.val)
                fast = fast.next;
            if (slow.next == fast)              // 没有重复发生，直接移动slow节点
                slow = slow.next;
            else
                slow.next = fast.next;          // 有重复发生，更新slow.next节点
            fast = fast.next;
        }
        return dummy.next;
    }

    // 最初的解法，没有章法，判断语句太多。
    static ListNode removeDuplicatex(ListNode head) {
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
