package com.leetcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/9/12.
 * Given a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 *
 * Note: Do not modify the linked list.
 * Can you solve it without using extra space?
 *
 * Function Signature:
 * public ListNode detectCycle(ListNode head) {...}
 *
 * <链表循环检测 系列问题>
 * - E141 Linked List Cycle  : 给定一个单链表，判断链表是否存在循环。
 * - E142 Linked List Cycle 2: 给定一个单链表，返回链表循环的起始节点，如果没有循环则返回null。
 *
 * <Tags>
 * - Two Pointers: 快慢指针用于循环检测。慢指针每次移动一个节点，快指针每次移动两个节点。[slow → * 1 ... fast → * 2 ... ]
 * - Floyd Loop Detection: 本质是使用双指针实现。
 * - HashSet: 判重。这里所存元素是节点的内存地址。
 *
 */
public class E142_Linked_List_Cycle_2 {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4});
        x.next.next.next.next = x;                      // Make it loop back to the first node.
        System.out.println(getCycleStart(x).val);
        System.out.println(getCycleStart2(x).val);
    }

    /** <存在循环的链表所具有的特性>
     *  1. 该链表将没有结尾。
     *  2. 该链表将具有一个长度为0-N的起始非循环区域（prefix），当N等于链表长度时，链表不具有循环。
     *  3. 该链表的非循环长度一定等于快慢指针相遇位置距离下个循环起点的长度。（推导过程见下面分析）
     */

    /** 解法2：双指针（快慢指针循环检测）。无需额外空间。Time - o(n), Space - o(1). */
    // {9, 9, 9, 1, 2, 3, 4, 5, 6} 的展开形式为 9, 9, 9, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 ...
    //           ↑______________|
    //
    //            |←————partA————→|←partB→|
    // |←-prefix-→|←————————loop—————————→|
    //  9   9   9   1   2   3   4   5   6   1   2   3   4   5   6 ...
    //  ↑1  ↑2  ↑3  ↑4  ↑5  ↑6  ↑7
    // |←———————Length.slow——————→|←partB→|
    // ↑↑1     ↑↑2     ↑↑3     ↑↑4     ↑↑5     ↑↑6     ↑↑7
    // |←—————————————————Length.fast————————————————————→|
    //
    // 根据两个指针所走路程的成分分解可以得到
    /** <线索1> */
    // loop = partA + partB
    // Length.slow = prefix + partA
    // Length.fast = prefix + loop + partA
    // 由于快指针速度是慢指针的两倍，因此相等时间走过的路程长度一定也是两倍，可以得到
    /** <线索2> */
    // Length.slow * 2 = Length.fast
    // 结合<线索1>和<线索2>，可以将等式转化为：
    // (prefix + partA) * 2 = prefix + partA + loop
    // i.e.  prefix + partA = loop
    // i.e.          prefix = loop - partA
    // i.e.          prefix = partB
    // 最终结论就是：prefix的长度刚好等于partB的长度
    // 因此当slow已经和fast相遇时，slow从相遇位置移动到下一个循环起点的长度（partB），就等于从链表头移动到该循环起点的长度（prefix）
    // 所以，我们首先让slow和fast相遇，在让slow和head相遇，这个相遇节点就一定是循环起点。
    //    9   9   9   1   2   3   4   5   6   1   2   3   4   5   6 ...
    //                |       slow ->  ->  -> |
    // head ->  ->  ->|
    static ListNode getCycleStart2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {                 // 首先让快慢指针相遇
                while (head != slow) {          // 然后让慢指针与头指针相遇
                    head = head.next;
                    slow = slow.next;
                }
                return head;
            }
        }
        return null;
    }

    /** 解法1：使用HashSet判重。优点是思路直接，缺点是需要额外空间。Time - o(n), Space - o(1). */
    static ListNode getCycleStart(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) return head;
            head = head.next;
        }
        return null;
    }
}
