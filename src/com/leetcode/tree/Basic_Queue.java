package com.leetcode.tree;

import com.leetcode.linkedlist.ListNode;
import java.util.Arrays;


/**
 * Created by Michael on 2017/3/9.
 * The basic implementation of <Queue> Data Structure.
 * 队列的两种实现方式：链表和数组。
 *
 * <Tags>
 * - Implement Queue using Linkedlist.
 * - Implement Queue using Array.
 * - Two Pointers: head and tail [ head -> ... -> ... -> tail -> ]
 *
 */
public class Basic_Queue {

    /** 队列的两种实现媒介：数组和链表 */
    // 方式1：使用数组实现（循环数组）：利用数组的索引值记录队首和队尾的位置。需要靠模运算区分队列是满还是空。
    // 方式2：使用链表实现（循环链表）：利用节点相邻关系判断队列满空。

    /** 队首和队尾指针的定义 */
    // 队首指针 head：指向队首元素。
    // 队尾指针 tail：指向队尾元素的下一个元素（而不是队尾元素本身）。

    /** 实现队列时的核心问题：如何判断队满和队空 */
    // 如果不加任何判断方式，队列满和队列空的表现都是队首指针和队尾指针重合。
    // 有两种方式来区分队满和队空：
    // 方式1：将队列<使用长度>限制为<实际容量>减1，
    //       对于链表，队列空时head == tail，队列满时tail.next == head
    //       对于数组，队列空时head == tail，队列满时(tail + 1) % length == head
    // 方式2：设置一个flag指示队列当前是否为空。队列空和队列满的时候都有head == tail，但是可以通过flag状态区分是队列空还是队列满。
    // 通常使用方式1。

    /** 队列基本操作的时间复杂度 */
    //  1.入队：o(1)
    //  2.出队：o(1)
    //  3.队首：o(1)

    //  Designed Capacity = 5
    //  True Capacity = 6
    //  0 -> 0 -> 0 -> 0 -> 0 -> 0 -> (loop)  head == tail 队列空
    //  ↑
    //  head,tail
    //
    //  1 -> 2 -> 3 -> 4 -> 5 -> 0 -> (loop)  tail.next == head 队列满
    //  ↑                        ↑
    //  head                     tail
    //
    //  1 -> 2 -> 3 -> 4 -> 5 -> 0 -> (loop)  head == tail 队列空
    //                           ↑
    //                           head,tail
    //
    //  7 -> 8 -> 9 -> 10 -> 0 -> 6 -> (loop)  tail.next == head 队列满
    //                       ↑    ↑
    //                       tail head
    public static void main(String[] args) {
        Queue_LinkedList q = new Queue_LinkedList();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        q.add(5);
        q.add(6);
        q.remove();
        q.remove();
        q.remove();
        q.remove();
        q.remove();
        q.peek();
        q.add(6);
        q.add(7);
        q.add(8);
        q.add(9);
        q.add(10);
        System.out.println(q.peek());

        Queue_Array q2 = new Queue_Array();
        q2.add(1);
        q2.add(2);
        q2.add(3);
        q2.add(4);
        q2.add(5);
        q2.add(6);
        q2.remove();
        q2.remove();
        q2.remove();
        q2.remove();
        q2.remove();
        System.out.println(q2.peek());
        q2.add(6);
        q2.add(7);
        q2.add(8);
        q2.add(9);
        q2.add(10);
        q2.add(6);
        System.out.println(q2.peek());
    }
}

/** 使用<链表>实现队列 */
// 判断队列满的条件：tail.next == head
// 判断队列空的条件：head == tail
class Queue_LinkedList {
    private ListNode queue;
    private ListNode head;
    private ListNode tail;
    private ListNode start;
    private final int DEFAULT_LEN = 5;

    // Default constructor with default queue size.
    public Queue_LinkedList() {
        queue = ListNode.Generator(new int[DEFAULT_LEN + 1]);
        start = queue;
        head = queue;
        tail = queue;
        while (queue.next != null) queue = queue.next;      // Create loop
        queue.next = head;
    }

    public Queue_LinkedList(int capacity) {
        queue = ListNode.Generator(new int[capacity + 1]);
        head = queue;
        tail = queue;
        while (queue.next != null) queue = queue.next;      // Create loop
        queue.next = head;
    }

    public boolean add(int x) {
        if (tail.next == head) {
            System.out.println("Error: Queue is full.");
            return false;        // 队列满时，禁止再加入元素
        }
        tail.val = x;
        tail = tail.next;
        start.print();
        return true;
    }

    public int remove() {
        if (head == tail) {
            System.out.println("Error: Queue is empty.");
            return -1;                // 队列空时，禁止再删除元素
        }
        int ret = head.val;
        head.val = 0;
        head = head.next;
        start.print();
        return ret;
    }

    public int peek() {
        if (head == tail) {
            System.out.println("Error: Queue is empty.");
            return -1;                // 队列空时，禁止返回元素
        }
        start.print();
        return head.val;
    }
}

/** 使用<数组>实现队列 */
// 判断队列满的条件：(tail + 1) % len == head 其含义是：tail的下个元素就是head（这里“下个元素”的概念是循环长度的下个元素，因此需要使用模运算实现）
// 判断队列空的条件：head == tail

// 示例：
// 队列满的情况1
// [1, 2, 3, 4, 5, 0]
//  ↑              ↑
//  head           tail    tail的循环下个元素就是head

// 队列满的情况2
// [4, 5, 0, 1, 2, 3]
//        ↑  ↑
//      tail head          tail的下个元素是head
class Queue_Array {
    int[] queue;
    private int head;
    private int tail;
    private final int DEFAULT_LEN = 5;

    public Queue_Array() {
        queue = new int[DEFAULT_LEN + 1];
        head = 0;
        tail = 0;
        System.out.println(Arrays.toString(queue));
    }

    public boolean add(int x) {
        int l = queue.length;
        if ((tail + 1) % l == head) {                       // 判断队列满的条件
            System.out.println("Error: Queue is full.");
            return false;
        }
        queue[tail] = x;
        tail = (tail + 1) % l;
        System.out.println(Arrays.toString(queue));
        return true;
    }

    public int remove() {
        if (head == tail) {                                 // 判断队列空的条件
            System.out.println("Error: Queue is empty.");
            return -1;
        }
        int ret = queue[head];
        queue[head] = 0;
        head = (head + 1) % queue.length;
        System.out.println(Arrays.toString(queue));
        return ret;
    }

    public int peek() {
        if (head == tail) {                                  // 判断队列空的条件
            System.out.println("Error: Queue is empty.");
            return -1;
        }
        return queue[head];
    }

}
