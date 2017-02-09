package com.leetcode.linkedlist;

/**
 * Created by Michael on 2016/9/11.
 * Given a linked list, remove the nth node from the end of list and return its head.
 * 简而言之就是，<删除单链表的倒数第n个节点并返回新链表头指针>。
 *
 * For example,
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 *
 * Note:
 * Given n will always be valid.
 * Try to do this in one pass.
 *
 * Function Signature:
 * public ListNode removeNthFromEnd(ListNode head, int x) {...}
 *
 * <Tags>
 * - Dummy节点：动态链表头。自动处理表头节点被删除的情况。
 * - Two Pointers：等距快慢指针，特别适合处理<倒数xxx问题>。[ slow → → → ... fast → → → ... ]
 * - Recursion：利用顺序递归、逆序返回的特性。
 *
 */
public class E19_Remove_Nth_From_End {
    public static void main(String[] args) {
        reverseRemove3(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 5).print();
        reverseRemove2(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 5).print();
        reverseRemove(ListNode.Generator(new int[] {1, 2, 3, 4, 5}), 5).print();
    }

    /** <单链表生存手册>
     *  对于单链表来说，最大的软肋就是不能反向扫描。指针只要走过站，就没有反悔的余地。
     *  不过道高一尺魔高一丈，我们有两个应对方案：
     *  1号应对方案 - 使用<Dummy节点>。好处是空间复杂度为 o(1)
     *  2号应对方案 - 使用<递归>。利用顺序递归，逆序返回的性质，空间复杂度是 o(n).
     */

    // 思路1：最直觉的方法，扫描两次链表，第一次获得长度，第二次删除节点。
    // 思路2：快慢指针，指针距离固定为n，快指针抵达null的时候，慢指针指向的就是要删除的节点。
    // 思路3：递归写法。


    /** 解法3：通过递归反向定位节点并删除。缺点是函数堆栈占用额外空间。Time - o(n), Space - o(n). */
    // 由于需要在返回的过程中计数，必须用整型返回值，因此需要设计helper方法。
    // 另外由于同样需要处理dummy节点，因此也必须用双层调用。
    static ListNode reverseRemove3(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        recursiveRemove(dummy, n);      // helper方法只管修改链表内部结构，而外部方法只管返回dummy.next，因为dummy.next一定是头节点
        return dummy.next;
    }

    static int recursiveRemove(ListNode node, int n) {
        if (node == null) return -1;                        // 递归终止条件：设为-1是为了累加到n的时候总距离是 n+1（待删除节点的上一个节点）
        int count = recursiveRemove(node.next, n) + 1;      // 逆序返回时累加
        if (count == n) node.next = node.next.next;         // 累加至n即走过了n+1个节点，可以删除下一个节点了
        return count;                                       // 返回累加值
    }


    /** 解法2：双指针<等距快慢指针>。Time - o(n), Space - o(1). */
    // 关键点1：由于我们想要扫描一次链表就搞定，因此必须使用双指针（其实本质上双指针是另一种形态的两次扫描）。
    //        快慢指针的移动逻辑是：慢指针先不动，快指针先走n步，走满n步之后，快慢指针开始同时移动，直至快指针抵达null节点。
    // 关键点2：由于单链表要想删除节点必须指针在待删除节点的前面，因此为了让慢指针落在待删除节点之前，快慢指针之间的距离应该是(n+1)而不仅仅是n。
    // 关键点3：由于单链表的表头节点也有可能被删除（当n等于链表长度时），因此为了能返回新表头节点，必须使用dummy节点，并让快慢指针都从dummy节点出发。
    static ListNode reverseRemove2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;      // start from dummy
        ListNode fast = dummy;      // start from dummy
        while (fast != null) {
            fast = fast.next;                   // fast always move
            if (n == -1) slow = slow.next;      // only move slow if <N+1> distance is reached.
            else n--;
        }
        slow.next = slow.next.next; // delete node
        return dummy.next;          // return new head
    }
    // 老写法，不够简洁。但逻辑足够清楚。
    static ListNode reverseRemove21(ListNode head, int x) {
        ListNode dummy = new ListNode(0);
        ListNode slow = dummy;
        ListNode fast = dummy;
        dummy.next = head;
        for (int i = 0; i < x + 1; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    /** 解法1：单指针两次链表扫描。Time - o(n), Space - o(1). */
    // 最直觉的解法，虽然解法2看上去更酷，但是实际上解法1和解法2的算法复杂度差别并不大，o(2n) vs o(n)，毕竟都是同一个数量级的差别。
    static ListNode reverseRemove(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode node = head;
        int count = 0;
        while (node != null) {                  // 1st Scan: get node count
            count++;
            node = node.next;
        }
        node = dummy;
        for (int i = 0; i < count - n; i++)     // 2nd Scan: get right node
            node = node.next;
        node.next = node.next.next;             // Delete node
        return dummy.next;
    }


}
