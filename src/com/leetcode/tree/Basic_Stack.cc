#include <iostream>
#include <vector>
#include "../common.h"

/**
 * Created by Michael on 2019/8/9.
 *
 * Stack, implemented using LinkedList.
 * 基本功能：push, pop, peek, size
 * 核心思想：链表的<表头>就是<栈顶>。
 * 入栈就是在表头起始位置插入元素。
 * 出栈就是断开表头节点，用第二个节点做新表头。
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


int main() {
  LLStack* stack = new LLStack();
  std::cout << "Current Size = " << stack->size() << std::endl;
  stack->push(2);
  stack->push(99);
  std::cout << "Current Size = " << stack->size() << std::endl;
  std::cout << "Peek value = " << stack->peek() << std::endl;
  std::cout << "Pop " << stack->pop() << std::endl;
  std::cout << "Pop " << stack->pop() << std::endl;
  std::cout << "Peek value = " << stack->peek() << std::endl;
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
