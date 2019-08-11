package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/10/17.
 * Implement the following operations of a stack using queues.
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 *
 * Notes:
 * You must use only standard operations of a queue,
 * which means only push to back, peek/pop from front, size, and is empty operations are valid.
 * Depending on your language, queue may not be supported natively.
 * You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
 * You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).
 *
 * Function Signature:
 * public void push(int x) {...}
 * public void pop() {...}
 * public int top() {...}
 * public boolean empty() {...}
 *
 */
public class E225_Implement_Stack_Using_Queue {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.push(3);
        System.out.println(stack.top());
    }
}

/** 单Queue循环出入队解法 */
class MyStack {
    private Deque<Integer> queue = new ArrayDeque<>();

    // Push: time - o(n)
    // 先入队，然后循环出队进队 size - 1 次
    public void push(int x) {
        int size = queue.size();
        queue.add(x);
        for (int i = 0; i < size - 1; i++)      // 注意只重复 size - 1 次，如果是 size 次相当于又回到开始状态了
            queue.add(queue.remove());
    }

    // Pop: time - o(1)
    public void pop() {
        queue.remove();
    }

    // Peek: time - o(1)
    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}

/** 双Queue，push的时候，空的那个用来先push进新元素，然后把full的所有元素出队存入空的里面。等效于单Queue的循环出入队解法。 */
class MyStack2 {
    private Deque<Integer> queue1 = new ArrayDeque<>();
    private Deque<Integer> queue2 = new ArrayDeque<>();
    private Deque<Integer> empty = queue1;
    private Deque<Integer> full = queue2;

    public void push(int x) {
        empty.add(x);
        while (full.size() > 0) {
            empty.add(full.remove());
        }
        Deque<Integer> temp = empty;
        empty = full;
        full = temp;
    }

    public void pop() {
        full.remove();
    }

    public int top() {
        return full.peek();
    }

    public boolean empty() {
        return full.isEmpty();
    }
}

/** 双Queue解法，其中一个Queue用来中转缓存用，巨傻。 */
class MyStack3 {
    private Deque<Integer> queue1 = new ArrayDeque<>();
    private Deque<Integer> queue2 = new ArrayDeque<>();
    private int top = 0;

    // Push: time - o(1)
    public void push(int x) {
        queue1.add(x);
    }

    // Pop: time - o(2n)
    public void pop() {
        while (queue1.size() > 1) {
            queue2.add(queue1.remove());
        }
        top = queue1.remove();
        while (queue2.size() > 0) {
            queue1.add(queue2.remove());
        }
    }

    // Peek: time - o(2n)
    public int top() {
        this.pop();
        queue1.add(top);
        return top;
    }

    public boolean empty() {
        return queue1.size() == 0;
    }
}
