#include <stack>
#include "../common.h"

/**
 * Created by Michael on 2019/8/11.
 * 
 * Implement Queue using Stacks.
 * push(x) -- Push element x to the back of queue.
 * pop() -- Removes the element from in front of queue.
 * peek() -- Get the front element.
 * empty() -- Return whether the queue is empty.
 * 
 * 关键：用两个栈（输入栈，输出栈） + pop & push 翻转元素顺序 + 清空栈应该用 while 循环
 * 复杂度：<很有意思！考察平摊分析！>
 *  - push ~ O(1)
 *  - pop ~ Amortized O(1), O(n) worst
 * 
 */

class QueueUsingStacks {

private:
  std::stack<int> st1;
  std::stack<int> st2;
  int length = 0;

public:
  QueueUsingStacks() {}

  // 所有元素都首先 push 到 st1 中
  void push(int x) {
    st1.push(x);
    length++;
  }

  // 重用 peek 逻辑
  int pop() {
    int val = this->peek();  // 注意 this 是指针，因此需要用 "->" 而不是 "." 访问成员函数。
    st2.pop();
    length--;
    return val;
  }

  // 在 pop 或者 peek 之前，首先看 st2 是否为空
  // 如果为空，则用 push & pop 方式将 st1 清空，把元素全都逆序转移到 st2 中
  // 不论如何，最后都会返回 st2 的栈顶元素
  int peek() {
    if (st2.empty()) {
      while (!st1.empty()){
        st2.push(st1.top());
        st1.pop();
      }
    }
    return st2.top();
  }

  // 清空栈不建议用 for 循环（易错且冗余），而应该用 while 循环
  int peek_bad() {
    if (st2.empty()) {
      // 极易疏忽的点：清空栈的for循环，其终止条件必须先缓存固定，否则会提前终止，清不干净
      int size = st1.size();
      for(int i = 0; i < size; i++) {
        st2.push(st1.top());
        st1.pop();
      }
    }
    return st2.top();
  }

  bool empty() {
    return length == 0;
  }
};


 /* 
  <平摊分析 Amortized Analysis>
  - 在 st2 不为空的情况下，pop 操作的时间复杂度是 O(1)
  - 在 st2 为空的情况下，pop 需要转移全部 st1 的元素后再在 st2 上 pop，也就是 2n+1 次操作，时间复杂度为 O(n)
  那么该如何计算 pop 的平均时间复杂度呢？
  仔细想一下就会发现，pop 不是无限制的，每个 pop 必然对应与一个 push 操作
  
  举例分析：push 和 pop 分别重复进行 n 次
  push, push, push, ..., pop, pop, pop, pop, ...
    1    1     1    ...   2n    1    1    1  ...
  
  平均每个操作的时间复杂度（包括 push 和 pop）：
  O[(n + 2n + n - 1) / 2n] = O[(2n - 1)/2n] ~ O(1)

  其实 Vector 的动态扩容也是这么分析的。

*/


int main() {

  QueueUsingStacks* q = new QueueUsingStacks;
  q->push(1);
  q->push(2);
  std::cout << q->peek() << std::endl;
  std::cout << q->pop() << std::endl;
}

