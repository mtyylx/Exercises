package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Michael on 2016/10/17.
 * Implement the following operations of a queue using stacks.
 * push(x) -- Push element x to the back of queue.
 * pop() -- Removes the element from in front of queue.
 * peek() -- Get the front element.
 * empty() -- Return whether the queue is empty.
 *
 * Notes:
 * You must use only standard operations of a stack,
 * which means only push to top, peek/pop from top, size, and is empty operations are valid.
 * Depending on your language, stack may not be supported natively.
 * You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
 * You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 *
 * Function Signature:
 *  public void push(int x) {...}
 *  public void pop() {...}
 *  public int peek() {...}
 *  public boolean empty() {...}
 *
 */
public class E232_Implement_Queue_Using_Stack {
    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(5);
        queue.push(9);
        queue.pop();
        queue.push(3);
        System.out.println(queue.peek());
    }
}

/** 双栈解法，一个栈用来存入数据，一个栈用来输出数据 */
// 对于输入，只需要简单的压入输入栈即可
// 对于输出，如果是第一次调用输出（peek或pop），或者输出栈已经在之前的输出命令后清空，那么输出前只需要把输入栈的所有元素都倒腾到输出栈就对了。
// 如果要输出时，输出栈还有元素，那么可以直接输出。
// 关键就是这两个栈的元素不能任意混合，不能像用队列实现栈那样。
// 输出栈为空：[ 1 2 3   [       -> pop() ->   [        [ 3 2
// 输出栈非空：[ 4 5 6   [ 3 2   -> pop() ->   [ 4 5 6  [ 3
class MyQueue {
    private Deque<Integer> stack1 = new ArrayDeque<>();
    private Deque<Integer> stack2 = new ArrayDeque<>();

    public void push(int x) {
        stack1.push(x);
    }

    public void pop() {
        peek();
        stack2.pop();
    }

    public int peek() {
        if (stack2.isEmpty()) {
            while (stack1.size() > 0)
                stack2.push(stack1.pop());
        }
        return stack2.peek();   // Deque standard peek method.
    }

    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
}
