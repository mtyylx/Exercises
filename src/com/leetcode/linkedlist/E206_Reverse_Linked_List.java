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
 * */
public class E206_Reverse_Linked_List {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4, 5});
        //ListNode y = reverse2(x);
        ListNode z = reverse4(x);
    }

    /** 迭代解法，最佳解法 */
    // 难点1：如何定义循环体的最小操作
    // 难点2：几个指针够用，怎么移动，怎么缓存
    // 难点3：如何处理头节点和尾节点
    // 用三个指针，起始阶段prev = null，head就是头节点，next先不赋值
    // 停止条件：当head移动到尾节点指向的null节点时
    // 情况1：空链表，return null
    // 情况2：单节点，return prev (head)
    // 情况3：多节点，当head为null的时候，prev一定是链表的尾节点
    static ListNode reverse2(ListNode head) {
        ListNode prev = null;
        ListNode next;
        while (head != null) {
            next = head.next;   // 缓存当前节点（head）的下一个节点（next）
            head.next = prev;   // 修改当前节点（head）的下一节点指向为上一个节点（prev）
            prev = head;        // 当前节点操作完成，上一节（prev）点变为当前节点（head）
            head = next;        // 当前节点（head）变为下一节点（next）
        }
        return prev;            // 当前节点（head）指向null的时候，上一节点（prev）一定是尾节点
    }

    // 下面这个解法是最初想出来的解法，简陋不推荐
    // 缺点是单独处理头节点，没有都统一到循环体中，其实只要让prev指向null就可以解决头节点指向null的要求
    static ListNode reverse(ListNode head) {
        if (head == null) return head;
        ListNode prev = head;
        ListNode current = head.next;
        ListNode next;
        head.next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    /** 递归解法1，设计包含2个入参的helper，先reverse后面，再处理当下节点。*/
    // 难点1：递归至最深处后如何将新链表头返回至最上层
    // 难点2：如何分解每个递归体的动作
    // 难点3：如何隐式包含链表头的处理（让head指向null）
    static ListNode reverse3(ListNode head) {
        return recursiveReverse(null, head);
    }

    // 传统递归的顺序，先处理后面，再处理前面
    // * 终止递归条件：当前节点current是null
    // * 先递归：将当前节点指针以及下一个节点指针传入，这样后面的节点能够指向前面的节点，同时返回新链表头（即终止递归的链表尾）
    // * 再处理手头上的：因为这时候已经可以确保current右侧的所有点都reverse完毕了，所以只需要把current的next指向上一个节点即可。
    // 不断向上传递新链表头
    private static ListNode recursiveReverse(ListNode prev, ListNode current) {
        if (current == null) return prev;
        ListNode newhead = recursiveReverse(current, current.next);
        current.next = prev;
        return newhead;
    }

    /** 递归解法2，设计包含两个入参的helper，先处理当下节点，再reverse后面的*/
    static ListNode reverse4(ListNode head) {
        return recursiveReverse2(null, head);
    }

    // 类迭代的递归解法，先处理前面，后处理后面
    // 终止递归条件与法1一样
    // 区别在于这里是先处理当前节点，然后再递归后面节点，处理方法和迭代方法其实完全一致了，
    // 好处是一口气return回来
    private static ListNode recursiveReverse2(ListNode prev, ListNode current) {
        if (current == null) return prev;
        ListNode next = current.next;   // 和迭代法一样，先缓存下一个节点
        current.next = prev;
        return recursiveReverse2(current, next);
    }

    /** 递归解法3，设计只使用一个入参，但是依然需要定义helper，因为一个入参递归无法完成。*/
    // 显然并不简单，因为只有一个入参就无法自动包含头节点这个特殊情况，必须在最后手工把头节点指向null
    static ListNode reverse5(ListNode head) {
        if (head == null) return head;      // Cover corner case: when list is empty.
        ListNode last = head;
        ListNode newhead = recursiveReverse(head);
        last.next = null;
        return newhead;
    }

    private static ListNode recursiveReverse(ListNode prev) {
        if (prev.next == null) return prev;
        ListNode newhead = recursiveReverse(prev.next);
        prev.next.next = prev;
        return newhead;
    }


}
