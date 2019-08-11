#include "../common.h"
#include <deque>
#include <queue>
#include <stack>
/**
 * Created by Michael on 2019/8/10.
 * 
 * Implement the following operations of a stack using queues.
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 * 
 * 关键：每次入队后，把队列的元素重新出队再入队 (n - 1) 次，目的是把刚入队的元素暴露在队首，随时可以正确 pop 出来。
 * 复杂度：push ~ O(n), pop ~ O(1).
 * 
 */

class StackUsingQueue {

private:
  std::queue<int> q;  // using queue internally.

public:
  StackUsingQueue() {}

  // push: Time ~ O(n)
  // Add element to queue, then "pop & push" for (size-1) times.
  void push(int x) {
    q.push(x);
    for (int i = 0; i < q.size() - 1; i++) {
      q.push(q.front());
      q.pop();
    }
  }

  // pop: Time ~ O(1)
  int pop() {
    int top = q.front();
    q.pop();
    return top;
  }

  int top() {
    return q.front();
  }

  bool empty() {
    return q.empty();
  }
};

/*
  front   <------>    back
  1,
  -----------------------------------------------
  1, 2                    push 2 to queue, size = 2
     2, 1                 pop & push for 1 time
  -----------------------------------------------
     2, 1, 3              push 3 to queue, size = 3   
        1, 3, 2
           3, 2, 1        pop & push for 2 times
 */

int main() {

  StackUsingQueue* st = new StackUsingQueue();
  st->push(22);
  st->push(33);
  std::cout << "top = " << st->top() << std::endl;
  std::cout << "pop = " << st->pop() << std::endl;


  // Basic Usage of STL Queue
  std::deque<int> v{2, 3, 5};     // 底层数据结构
  std::queue<int> q(v);           // 拷贝构造函数
  std::stack<int> s(v);
  while (!q.empty()) {
    std::cout << q.front() << ", ";
    q.pop();
  }
  std::cout << std::endl;

  while (!s.empty()) {
    std::cout << s.top() << ", ";
    s.pop();
  }
  std::cout << std::endl;
}