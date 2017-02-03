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
 * E203 Remove Element          : 给定一个未排序单链表和一个值k，删除链表中所有值等于k的节点。
 * E237 Delete Node             : 给定一个链表的节点指针，删除该节点。
 *
 * <Tags>
 * - Two Pointers: 快慢指针同向扫描. [slow → → → ... fast → → → ...]
 * - Dummy节点：由于链表头节点有可能会被删除。
 * - 递归：正序递归。
 *
 */
public class M82_Remove_Duplicate_Nodes_2 {
    public static void main(String[] args) {
        removeDuplicate(ListNode.Generator(new int[] {1, 1, 2, 2, 3, 4, 4, 4, 5, 5, 5})).print();
        removeDuplicate2(ListNode.Generator(new int[] {1, 1, 2, 2, 3, 4, 4, 4, 5, 5, 5})).print();
        removeDuplicate3(ListNode.Generator(new int[] {1, 1, 2, 2, 3, 4, 4, 4, 5, 5, 5})).print();
    }


    /** 解法1：双指针（快慢指针）+ Dummy节点.（迭代写法） Time - o(n), Space - o(1). */
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



    /** 解法2：递归写法（正向递归）. Time - o(n), Space - o(n). */
    // 虽然链表问题通常不建议用递归解法，因为当链表很长时将会很轻松的堆栈溢出。但是用递归解链表问题确实是一种思维训练，很考验对递归的理解。
    // 在这道题中，我最初试图用E83的反向递归思路来解决，但是由于E83和M82的细微不同，反向递归处理起来并不好弄。
    // 因为这里你需要在了解这个节点值已经重复的情况下抛弃所有拥有该节点值的节点，链表的接续将会很困难。
    // 而如果使用正向递归，则一切都豁然开朗。
    // 由于正向递归是从表头一直扫描至表尾的，因此它能够<最早的检测到出现相等值的相邻节点>，并且直接跳过所有同类。
    // 1 -> 2 -> 2 -> 2 -> 3
    //      ↑
    //      检测到重复节点，跳过所有等于2的节点
    // 而且正向递归的逻辑可以确保只要当前节点与下个节点值不相等，那么当前节点就一定是独一无二的。
    // 1 -> 2 -> 2 -> 2 -> 3 -> 4
    //                     ↑
    //                     当前节点与下个节点不同，保留当前节点，对下个节点打头的子链表进行递归
    static ListNode removeDuplicate3(ListNode head) {
        if (head == null) return null;                                  // 递归终止条件
        if (head.next != null && head.val == head.next.val) {
            while (head.next != null && head.val == head.next.val)
                head = head.next;
            return removeDuplicate3(head.next);                         // 跳过所有相同节点，递归后续链表
        }
        else head.next = removeDuplicate3(head.next);                   // 保留当前节点，递归后续链表
        return head;
    }


}
