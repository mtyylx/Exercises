package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/8.
 * Reverse a singly linked list.
 *
 * Hint:
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 *
 * Function Signature:
 * public ListNode reverse(ListNode head) {...}
 *
 * <Tags>
 * - Two Pointers: 双指针反转 + 第三指针缓存。
 * - 递归：逆序递归 / 正序递归。
 *
 */
public class E206_Reverse_Linked_List {
    public static void main(String[] args) {
        reverse(ListNode.Generator(new int[] {1, 2, 3, 4, 5})).print();
        reverse_Recursive(ListNode.Generator(new int[] {1, 2, 3, 4, 5})).print();
        reverse2(ListNode.Generator(new int[] {1, 2, 3, 4, 5})).print();
        reverse3(ListNode.Generator(new int[] {1, 2, 3, 4, 5})).print();
    }

    /** 标准迭代解法：双指针反转 + 第三指针缓存。最佳解法。Time - o(n), Space - o(1). */
    // 链表的反转关键在于<确定循环体要处理的区域>，从最简化模型入手：
    // null    1  ->  2  ->  ...
    //  ↑      ↑      ↑
    // prev   curr   next
    // 节点反转是两个节点的事情，因此设置两个同步移动的指针：prev和curr，目标是让 prev <- curr
    // 但是反转之前，我们首先需要缓存curr原本指向的下一个节点，否则链表就断开了。这里缓存节点可以算是第三个指针。
    // 针对头节点这种特殊情况，我们通过设置prev的初始值为null来解决。
    // null      1  ->  2  ->  ...
    //  ↑        ↑      ↑
    // prev     curr   next
    //
    // null  <-  1      2  ->  3  ->  ...
    //           ↑      ↑      ↑
    //          prev   curr   next
    //   之前的 (curr) (next)
    // null  <-  1  <-  2      3  ->  4  ->  ...
    //                  ↑      ↑      ↑
    //                 prev   curr   next
    //         之前的  (curr) (next)
    // 可以看到缓存next的作用就是让双指针反转完当前节点之后，能够顺利的移到下一个位置。
    static ListNode reverse(ListNode head) {
        ListNode prev = null;               // prev起始状态为null，方便将头节点直接变为为尾节点（指向null）
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;      // 缓存curr原本的下个节点
            curr.next = prev;               // 修改curr的下个节点
            prev = curr;                    // 双指针右移
            curr = next;                    // 双指针右移：用上之前缓存的节点
        }
        return prev;                        // curr是null的时候，prev就是头节点
    }

    /** 标准递归解法：逆序递归 Work from backward。Time - o(n), Space - o(n). */
    // 思路是先递归至最后一个节点，然后依次反转每个节点。
    //   1  ->  2  ->  3  ->  4  ->  null
    // 由于不想用helper方法，因此只能凑合用返回值返回链表的新头节点。
    // 显然新头节点一定是最后一个节点，因此递归终止条件就是：只要该节点是最后一个节点（head.next == null），就返回该节点
    // 不过需要先考虑空链表的情况，才能进入递归终止条件，否则会报错。
    // 然后就开始递归了，递归返回值先缓存，然后让下个节点指向当前节点，再把当前节点的下个节点清空。
    //           1  ->  2  ->  3  ->  4  ->  null
    //               null  <-  3  <-  4
    //        null  <-  2  <-  3
    //  null <-  1  <-  2
    // 可以看到每次都清理head.next十分重要：
    // 对于中间节点，head.next虽然在本层递归中被清理，但是会在上一层递归的head.next.next中被重新赋值
    // 对于头节点，head.next则会保持null，这也是尾节点所必须做到的。
    // 本层递归处理完之后，才返回新的头节点（其实一直都没有变，只不过要传递至递归最顶层）
    static ListNode reverse_Recursive(ListNode head) {
        if (head == null || head.next == null) return head;     // 递归终止条件：需要先单独处理空链表情况（head==null），然后才是终止条件。
        ListNode ret = reverse_Recursive(head.next);
        head.next.next = head;              // Point next node to curr node.
        head.next = null;                   // 每次都清理head.next
        return ret;
    }

    /** 递归解法2：逆序递归 + 2入参的helper。*/
    // 难点1：递归至最深处后如何将新链表头返回至最上层
    // 难点2：如何分解每个递归体的动作
    // 难点3：如何隐式包含链表头的处理（让head指向null）
    static ListNode reverse2(ListNode head) {
        return reverse_Recursive2(null, head);
    }

    // 逆序递归
    // 先递归：将当前节点指针以及下一个节点指针传入，这样后面的节点能够指向前面的节点，同时返回新链表头（即终止递归的链表尾）
    // 再处理当前节点：因为这时候已经可以确保current右侧的所有点都reverse完毕了，所以只需要把current的next指向上一个节点即可。
    // 不断向上传递新链表头
    private static ListNode reverse_Recursive2(ListNode prev, ListNode current) {
        if (current == null) return prev;
        ListNode newhead = reverse_Recursive2(current, current.next);
        current.next = prev;
        return newhead;
    }

    /** 递归解法3：正序递归 + 2入参的helper。*/
    static ListNode reverse3(ListNode head) {
        return reverse_Recursive3(null, head);
    }

    // 正序递归
    // 区别于逆序递归的地方是先处理当前节点，再递归后面节点，处理方法和迭代方法类似，具有尾递归特性。
    private static ListNode reverse_Recursive3(ListNode prev, ListNode current) {
        if (current == null) return prev;
        ListNode next = current.next;               // 和迭代法一样，先缓存下一个节点
        current.next = prev;
        return reverse_Recursive3(current, next);
    }

}
