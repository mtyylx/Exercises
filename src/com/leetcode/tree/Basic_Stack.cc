#include <iostream>
#include <vector>
#include <list>
#include <algorithm>
#include "../common.h"
using namespace std;

/**
 * Created by Michael on 2019/8/9.
 * 
 * 使用数组或链表实现栈的基本功能。
 */
 
/**
 * Stack, implemented using Array
 * 实现起来很简单。
 * 核心思想：
 *    - 数组的最后一个有效元素就是栈顶
 *    - 入栈就是后移尾指针
 *    - 出栈就是前移尾指针
 * 
 * 栈底               栈顶     最大容量
 *    ----------------------------
 *    | 99 | 88 | 77 |   |   |   | 
 *    ----------------------------
 * 
 */

class ArrayStack {
  private:
    vector<int> data;
    size_t tail = 0;    // tail 作为索引值，指向栈顶元素的下一个位置，其取值本身就等于栈内有效元素的个数

  public:
    ArrayStack (size_t capacity) {
      data.resize(capacity);
    }

    // 压入单个元素，返回操作成功与否
    bool push(int x) {
      if (tail == data.size()) return false;  // Stack is full, cannot push.
      data[tail] = x;
      tail++;             // 先写入数据，后移动栈顶指针
      return true;
    }

    // 压入指定长度的数据序列，返回实际压入的有效长度
    size_t push(int* seq, size_t len) {
      size_t valid = min(data.size() - tail, len);
      for (int i = 0; i < valid; i++)
        data[tail++] = seq[i];    // 先写入数据，后移动栈顶指针
      return valid;
    }

    // 弹出单个元素，返回元素值
    int pop() {
      if (tail == 0) return -1;   // Stack is empty, cannot pop.
      tail--;                     // 先收缩栈顶指针，再输出元素
      return data[tail];
    }

    // 弹出指定长度的元素至指定地址
    size_t pop(int* seq, size_t len) {
      size_t valid = min(tail, len);
      for (int i = 0; i < valid; i++)
        seq[i] = data[--tail];    // 先收缩栈顶指针，再导出数据
      return valid;
    }
};


/**
 * Stack, implemented using LinkedList.
 * 基本功能：push, pop, peek, size
 * 核心思想：
 *    - 链表的<表头>就是<栈顶>。
 *    - 入栈就是在表头起始位置插入元素。
 *    - 出栈就是断开表头节点，用第二个节点做新表头。
 * 
 */
class LLStack {
private:
  // 维护表头节点指针
  ListNode* head = nullptr;
  int length = 0;

public:
  LLStack() {}

  int peek() {
    if (head == nullptr) {
      std::cout << "Empty stack." << std::endl;
      return -1;
    }
    return head->val;
  }

  // 入栈：新元素插入表头
  void push(int val) {
    ListNode* new_head = new ListNode(val);
    new_head->next = head;
    head = new_head;
    length++;
    std::cout << "Pushed " << val << std::endl;
  }

  // 出栈：删除表头
  int pop() {
    if (head == nullptr)
      throw "Empty stack. Nothing to pop.";
    int ret = head->val;
    head = head->next;
    length--;
    return ret;
  }

  int size() {
    return length;
  }
};

class ListStack {
private:
  list<int> data;

public:
  ListStack() {}

  void push(int x) {
    data.push_front(x);
  }

  int pop() {
    int result = data.front();
    data.pop_front();
    return result;
  }
};


int main() {
  ArrayStack* astack = new ArrayStack(5);
  astack->push(999);
  cout << astack->pop() << endl;
  vector<int> in = {1, 2, 3, 4, 5};
  astack->push(in.data(), in.size());
  vector<int> out(5, 0);
  astack->pop(out.data(), 5);
  print(out);


  LLStack* stack = new LLStack();
  std::cout << "Current Size = " << stack->size() << std::endl;
  stack->push(2);
  stack->push(99);
  std::cout << "Current Size = " << stack->size() << std::endl;
  std::cout << "Peek value = " << stack->peek() << std::endl;
  std::cout << "Pop " << stack->pop() << std::endl;
  std::cout << "Pop " << stack->pop() << std::endl;
  std::cout << "Peek value = " << stack->peek() << std::endl;

  ListStack* lstack = new ListStack();
  lstack->push(999);
  lstack->push(888);
  cout << lstack->pop() << endl;
}


void test_api() {

  ListNode* root = new ListNode(3);
  root->next = new ListNode(4);
  root->next->next = new ListNode(5);
  // Loop: root->next->next->next = root;
  root->print();

  std::vector<int> data = {2, 3, 5, 7, 11, 13};
  ListNode* head = ListNode::create(data);
}
