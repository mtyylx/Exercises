package com.leetcode.tree;

import com.leetcode.linkedlist.ListNode;

/**
 * Created by Michael on 2017/3/13.
 * The basic implementation of <Stack> Data Structure.
 * Busy Working on trip planning and hotel booking these days. Really tired.
 */
public class Basic_Stack {

    /** 栈的两种实现媒介：<数组>和<链表> */

    public static void main(String[] args) {
        Stack_Array myStack = new Stack_Array();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        myStack.push(5);
        myStack.push(6);
        myStack.peek();
        myStack.pop();
        myStack.pop();
        myStack.push(7);
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.peek();

        Stack_LinkedList myStack2 = new Stack_LinkedList();
        myStack2.push(1);
        myStack2.push(2);
        myStack2.push(3);
        myStack2.push(4);
        myStack2.push(5);
        System.out.println(myStack2.peek());
        myStack2.pop();
        myStack2.pop();
        myStack2.pop();
        myStack2.pop();
        myStack2.pop();
        System.out.println(myStack2.peek());
        myStack2.pop();
    }
}

/** <数组>实现栈 */
class Stack_Array {
    private int[] stack;
    private static final int LEN = 5;
    private int ptr = 0;

    public Stack_Array() {
        this.stack = new int[LEN];
    }

    public void push(int x) {
        if (ptr == stack.length) System.out.println("Stack is Full.");
        else stack[ptr++] = x;
    }

    public void pop() {
        if (ptr == 0) System.out.println("Stack is Empty.");
        else stack[--ptr] = 0;
    }

    public int peek() {
        if (ptr != 0) return stack[ptr - 1];
        System.out.println("Stack is Empty.");
        return -1;
    }
}

/** <链表>实现栈 */
// 随着压栈的操作，链表的生长方式是不断的将节点插入头节点之前。区别于数组实现方式的追加，链表实现方式用的是插入。
class Stack_LinkedList {
    private ListNode head;
    private int length;

    public Stack_LinkedList() {
        head = new ListNode(0);
        length = 0;
    }

    public void push(int x) {
        ListNode node = new ListNode(x);
        node.next = head.next;
        head.next = node;
        length++;
    }

    public int pop() {
        if (head.next == null) {
            System.out.println("Stack is Empty.");
            return -1;
        }
        int ret = head.next.val;
        head.next = head.next.next;
        length--;
        return ret;
    }

    public int peek() {
        if (head.next == null) {
            System.out.println("Stack is Empty");
            return -1;
        }
        return head.next.val;
    }

    public int getLength() {
        return length;
    }
}