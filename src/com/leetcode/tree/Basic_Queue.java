package com.leetcode.tree;

import com.leetcode.linkedlist.ListNode;


/**
 * Created by Michael on 2017/3/9.
 * The basic implementation of <Queue> Data Structure.
 *
 * <Tags>
 * - Implement Queue using Linkedlist.
 * - Two Pointers: front (head) and rear (tail).
 *
 */
public class Basic_Queue {

    /** 队首和队尾指针的定义*/
    // 队首指针 head：指向队首元素
    // 队尾指针 tail：指向队尾元素的下一个元素（而不是队尾元素本身）
    // 为了避免无法区分队列空和队列满这两种情况，需要额外的机制：
    // 运行方式1：设置一个flag指示队列当前是否为空。队列空和队列满的时候都有head == tail，但是可以通过flag状态区分是队列空还是队列满。
    // 运行方式2：将队列最大长度限制为实际容量减1，使得队列空时head == tail，队列满时tail.next == head。
    private ListNode queue;
    private ListNode head;
    private ListNode tail;
    private ListNode start;
    private final int DEFAULT_LEN = 5;

    // Default constructor with default queue size.
    public Basic_Queue() {
        queue = ListNode.Generator(new int[DEFAULT_LEN]);
        start = queue;
        head = queue;
        tail = queue;
        while (queue.next != null) queue = queue.next;      // Create loop
        queue.next = head;
    }

    public Basic_Queue(int capacity) {
        queue = ListNode.Generator(new int[capacity]);
        head = queue;
        tail = queue;
        while (queue.next != null) queue = queue.next;      // Create loop
        queue.next = head;
    }

    public void add(int x) {
        tail.val = x;
        tail = tail.next;
        start.print();
    }

    public int remove() {
        if (head == tail) return -1;
        int ret = head.val;
        head.val = 0;
        head = head.next;
        start.print();
        return ret;
    }

    public int peek() {
        if (head == tail) return -1;
        start.print();
        return head.val;
    }


    /** 循环队列：入队和出队的时间复杂度为o(1). */
    //  Capacity = 5
    //  0 -> 0 -> 0 -> 0 -> 0 -> (loop)
    //  ↑
    //  head,tail
    //
    //  1 -> 2 -> 3 -> 4 -> 0 -> (loop)
    //  ↑                   ↑
    //  head                tail
    public static void main(String[] args) {
        Basic_Queue queue = new Basic_Queue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.remove();
        queue.add(4);
        queue.remove();
        queue.remove();
        queue.add(5);
        queue.add(6);
        queue.add(7);
        queue.remove();
        queue.remove();
        queue.remove();
        System.out.println(queue.peek());
    }
}
