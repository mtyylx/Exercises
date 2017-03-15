package com.leetcode.tree;

/**
 * Created by Michael on 2017/3/13.
 * The basic implementation of <Stack> Data Structure.
 * Busy Working on trip planning and hotel booking these days. Really tired.
 */
public class Basic_Stack {
    public static void main(String[] args) {
        Stack_Array myStack = new Stack_Array();
        myStack.add(1);
        myStack.add(2);
        myStack.add(3);
        myStack.add(4);
        myStack.add(5);
        myStack.add(6);
        myStack.peek();
        myStack.remove();
        myStack.remove();
        myStack.add(7);
        myStack.remove();
        myStack.remove();
        myStack.remove();
        myStack.remove();
        myStack.remove();
        myStack.peek();
    }
}

class Stack_Array {
    private int[] stack;
    private static final int LEN = 5;
    private int ptr = 0;

    public Stack_Array() {
        this.stack = new int[LEN];
    }

    public void add(int x) {
        if (ptr == stack.length) System.out.println("Stack is Full.");
        else stack[ptr++] = x;
    }

    public void remove() {
        if (ptr == 0) System.out.println("Stack is Empty.");
        else stack[--ptr] = 0;
    }

    public int peek() {
        if (ptr != 0) return stack[ptr - 1];
        System.out.println("Stack is Empty.");
        return -1;
    }
}
