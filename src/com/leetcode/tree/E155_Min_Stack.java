package com.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Michael on 2016/10/23.
 * Design a stack that supports push, pop, top, and retrieving the minimum element <in constant time>.
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * Example:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> Returns -3.
 * minStack.pop();
 * minStack.top();      --> Returns 0.
 * minStack.getMin();   --> Returns -2.
 *
 */
public class E155_Min_Stack {
    public static void main(String[] args) {
        MinStack3 minStack = new MinStack3();
        minStack.push(19);
        minStack.push(12);
        minStack.push(8);
        minStack.push(99);
        minStack.push(67);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());

    }
}

/** 基本解法：pop()有一定概率是o(n)而不是o(1). */
// 其实找到最小值并不难，难的是在最小值出栈后如何依旧能够o(1)的找到新的最小值。
// 下面的解法中，如果pop出的刚好是最小值，就需要重新扫描一遍整个栈的所有值，这种情况下的pop()操作将不是o(1)而是o(n)的。
class MinStack {
    // 如果构造器并不需要传递进来任何数据，那么成员变量的初始化位置写在类中和写在构造器中，是没有任何区别的。
    // 其实这时候写不写构造器都无所谓。不写的话，系统也会自动实现。
    private int min = Integer.MAX_VALUE;
    private Deque<Integer> stack = new ArrayDeque<>();
    public MinStack() {
//        min = Integer.MAX_VALUE;
//        stack = new ArrayDeque<>();
    }

    public void push(int x) {
        stack.push(x);
        if (stack.size() == 0) min = x;
        else min = Math.min(x, min);
    }

    // 出栈元素就是最小值时，时间复杂度是o(n).
    // 需要遍历整个stack才能确定新的最小值。这不够好。
    public void pop() {
        int top = stack.peek();
        stack.pop();
        if (top == min) {
            min = Integer.MAX_VALUE;
            if (stack.size() != 0) {
                for (int x : stack) {
                    min = Math.min(x, min);
                }
            }
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}

/** 双栈解法：可以确保所有操作都是o(1)。同步出入栈，通过<辅栈>记录<主栈>任意高度的最小值。 */
// 相当于是空间换时间，需要o(2n)的空间。
class MinStack2 {
    private Deque<Integer> stack = new ArrayDeque<>();
    private Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int x) {
        stack.push(x);
        if (minStack.size() == 0) minStack.push(x);
        else minStack.push(Math.min(minStack.peek(), x));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}

/** 单栈解法1：根据情况连续push/pop两次。 */
// 如果发现即将压入的值小于等于当前最小值，就先把当前最小值压入栈中，再压入这个值。
// 出栈时，如果栈顶元素就是当前最小值，那么就意味着出栈之后栈最小值会变化，所以pop两次取出并更新最小值。
class MinStack3 {
    private int min = Integer.MAX_VALUE;
    private Deque<Integer> stack = new ArrayDeque<>();

    // 易错点：即使x等于最小值，也要push两次。因为对应的pop()里面需要比较是否等于最小值决定是否pop两次的。
    // 如果写成(x < min)才push两次的话，会出现多pop的情况。
    // 例如输入序列为0 1 0，
    // 如果写成(x <= min)则栈为[0 0 1 0 0，再调用pop()的时候，因为peek到的值就是min而pop两次，变成[0 0 1
    // 如果写成(x < min)则栈为[0 0 1 0，再调用pop()，结果是[0 0。把实际元素给pop出去了。
    public void push(int x) {
        if (x <= min) {
            stack.push(min);
            min = x;
        }
        stack.push(x);
    }

    public void pop() {
        if (stack.peek() == min) {
            stack.pop();
            min = stack.pop();
        }
        else stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}

/** 单栈解法2：栈中放的不是原始值，而是放与当前栈最小值的差值。 */
// 例如压入序列为：7, 5, 8, 2, 4, 1
// 则栈实际为：[2147483640, -2, 3, -3, 2, -1
//            min = 7     5   5  2   2  1
class MinStack4 {
    long min = Integer.MAX_VALUE;
    Deque<Long> stack = new ArrayDeque<>();

    public void push(int x) {
        stack.push((long) x - min);
        min = Math.min(x, min);
    }

    // 这里出栈并不要求返回栈顶元素，因此只更新栈最小值即可
    public void pop() {
        min = Math.max(min - stack.pop(), min);
    }

    // 复原
    public int top() {
        return (int) Math.max(stack.peek() + min, min);
    }

    public int getMin() {
        return (int) min;
    }
}
