#include "../common.h"

/**
 * Created by Michael on 2019/8/10.
 *
 * 链表反转问题是典型的一类巧解问题，这类问题的特点是：
 * 如果思考角度对，那么实现起来会很轻松，最后的实现也很简洁优美;
 * 如果思考角度稍有偏差，虽然最终也可以做出来，但是过程会变得非常痛苦，容易出错，而且最后的实现存在冗余。
 * 
 * 对比下面的两种解法，思路本质是一样的，但是解法一明显更清晰简洁。
 * 
 * 解法一的根本优势在于认识到三点：
 * 1. 主线双指针选择 prev 和 curr
 * 2. next 仅作为循环体内部的临时缓存
 * 3. 每次循环的任务限定为让双指针同步右移一个节点，并反转一个节点，不多做
 * 
 * 解法二的弱势原因：
 * 1. 主线不清晰，用了三个指针 prev / curr / next，而在循环外面初始化 next 不仅是冗余的，还会导致需要额外的 check。
 * 2. 由于用了三个指针出现冗余，导致每次循环的任务不再是反转一个节点，而是反转两个。
 * 
 */



// Solution #1: 简洁清晰。
// 双指针，prev 和 curr 同步平移，next 作为缓存。
ListNode* reverse_linked_list_neat(ListNode* head) {
  ListNode* prev = nullptr;  // left ptr
  ListNode* curr = head;     // right ptr
  ListNode* next;            // buffer for each loop
  while (curr != nullptr) {
    next = curr->next;    // Buffer next node
    curr->next = prev;    // Reverse curr node direction, buffer prev node
    prev = curr;          // Shift left ptr, buffer curr node
    curr = next;          // Shift right ptr, buffer next node
  }                       // After each loop ends, left and right ptr are shifted one node at the same pace.
  return prev;            // prev ptr is always the "current" ptr for "next loop", until loop ends.
}

// Solution #2: 冗余易错
ListNode* reverse_linked_list_redundant(ListNode* head) {
  if (head == nullptr) return head;  // Must check head, because we access head->next later.

  ListNode* prev = nullptr;
  ListNode* curr = head;
  ListNode* next = head->next;  // Init next outside loop is a bad idea, make code redundant.
  while (next != nullptr) {     // Check next instead of curr
    curr->next = prev;          // next node is already stored, so revert current node direction.
    prev = curr;                // Shift
    curr = next;                // Shift
    next = next->next;          // Shift again, equal to the first line inside loop of Solution #1.
    curr->next = prev;          // Duplicated.
  }
  return curr;
}

int main() {

  std::vector<int> data = {1, 2, 3, 4, 5};
  ListNode* head1 = ListNode::create(data);
  ListNode* head2 = ListNode::create(data);
  // Solution #1
  ListNode* r1 = reverse_linked_list_neat(head1);
  r1->print();
  // Solution #2
  ListNode* r2 = reverse_linked_list_redundant(head2);
  r2->print();
}

/* 示意图

---------------------------------------
 prev     curr
  ↓        ↓                                 (initial state)
 null      1  ->  2  ->  3
---------------------------------------
 prev     curr   next
  ↓        ↓      ↓                          next = curr->next; (I)
 null      1  ->  2  ->  3
---------------------------------------
 prev     curr   next
  ↓        ↓      ↓                          curr->next = prev; (II)
 null  <-  1      2  ->  3
---------------------------------------
       prev,curr next
           ↓      ↓                          prev = curr;       (III)
 null  <-  1      2  ->  3
---------------------------------------
          prev curr,next
           ↓      ↓                          curr = next;       (IV)
 null  <-  1      2  ->  3
---------------------------------------

At this point, prev at 1, curr at 2. Both shifted one node. Repeat (I)-(IV).

*/ 
