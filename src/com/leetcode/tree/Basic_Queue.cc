#include "../common.h"
#include <vector>
#include <list>
#include <algorithm>
using namespace std;

/**
 * Created by Michael on 2019/8/10.
 * 
 * 循环队列 Circular Queue 使用数组或循环链表实现。
 * 基本功能：add，remove，peek
 * 队列vs栈：先进先出vs先进后出。
 * 
 * <循环队列的实现>
 * 0. 内存申请
 *      - 与栈边用边申请边扩充的方式不同，循环队列具有固定的容量大小
 *      - 如果使用数组，正常申请即可。如果不使用 length，则申请量必须等于需求量 + 1
 *      - 如果使用链表，则需要初始化好整个链表的节点，并且使其成环。如果不使用 length，则节点数量必须等于需求量 + 1
 * 
 * 1. 队首指针与队尾指针的规则：
 *      - 队首 head 指向 <队首元素> 
 *      - 队尾 tail 指向 <队尾元素的下一个有效位置>（并不是队尾元素本身），tail 可能小于 head
 *      - 添加元素：先将数据写到 tail 所在位置，再右移 tail，最后修正 tail 至有效取值范围
 *      - 删除元素：先将数据从 head 所在位置导出，再右移 head，最后修正 head 至有效取值范围
 * 
 * 2. 队列判空与队列判满的规则：
 *      - 如果使用 length 记录队列的有效元素个数，那么判空和判满只需要看 length 的值即可，非常简单
 *      - 如果非不用 length，则需要空出一个位置，如下操作：
 *      - 如果申请内存空间的长度为 n，则队列最多只能放 n - 1 个元素。这么做是为了区分<队列空>和<队列满>这两种状态。
 *      - 队列判空：head == tail
 *      - 队列判满：
 *          - 数组实现：(tail + 1) % capacity == head
 *          - 链表实现：tail->next == head
 * 
 */

class ArrayQueue {
private:
  vector<int> data;
  size_t head = 0;
  size_t tail = 0;
  size_t length = 0;

public:
  ArrayQueue(size_t capacity) {
    data.resize(capacity);
  }

  bool enqueue(int x) {
    if (length == data.size()) return false;
    data[tail++] = x;       // 先赋值，再扩张指针
    tail %= data.size();    // 修正指针的位置，使其返回 0 - (capacity-1) 取值区间
    length++;
    return true;
  }

  // 入队指定长度的数据，返回成功入队的数据长度
  size_t enqueue(int* seq, size_t len) {
    size_t valid = min(data.size() - length, len);
    for (int i = 0; i < valid; i++) {
      data[tail++] = seq[i];    // 先赋值，后扩张指针
      tail %= data.size();      // 修正指针位置
    }
    length += valid;
    return valid;
  }

  int dequeue() {
    if (length == 0) return -1;
    int result = data[head++];    // 先缓存，后收缩指针
    head %= data.size();          // 同样需要修正指针位置
    length--;
    return result;
  }

  // 出队指定长度的数据，返回成功出队的数据长度
  size_t dequeue(int* seq, size_t len) {
    size_t valid = min(length, len);
    for (int i = 0; i < valid; i++) {
      seq[i] = data[head++];      // 先导出数组，后收缩指针
      head %= data.size();        // 最后修正指针位置
    }
    length -= valid;
    return valid;
  }
};



class CircularQueueLL {

private:
  ListNode* head = nullptr;
  ListNode* tail = nullptr;

public:
  // 需求量为 capacity，但需要创建 + 1 个节点
  CircularQueueLL(int capacity) {
    head = new ListNode(0);
    tail = head;
    ListNode* curr = head;
    for (int i = 0; i < capacity; i++) {
      curr->next = new ListNode(0);
      curr = curr->next;
    }
    curr->next = head;    // Make it loop
  }

  // Add element to the tail of this queue.
  ListNode* enqueue(int val) {
    if (tail->next == head) {             // Full Check
      std::cout << "Queue is full. Cannot add element." << std::endl;
        return nullptr;
    }
    tail->val = val;                      // Update val.
    tail = tail->next;                    // Move tail to new tail.
  }

  // Remove element from the head of this queue.
  int dequeue() {
    if (head == tail) {                 // Empty Check
      std::cout << "Queue is empty. Cannot remove element." << std::endl;
      return -1;
    }
    int res = head->val;
    head->val = 0;                       // Clean val.
    head = head->next;                   // Move head to new head.
    return res;
  }

  int peek() {
    // Check if empty first
    if (head == tail) {
      std::cout << "Queue is empty. Cannot remove element." << std::endl;
      return -1;
    }
    return head->val;
  }
};

int main() {
  
  ArrayQueue* aq = new ArrayQueue(5);
  aq->enqueue(99);
  aq->enqueue(88);
  aq->dequeue();
  vector<int> d = {1, 2, 3, 4};
  aq->enqueue(d.data(), d.size());
  vector<int> dd(5);
  aq->dequeue(dd.data(), 10);
  print(dd);


  CircularQueueLL* q = new CircularQueueLL(3);
  q->enqueue(2);
  q->enqueue(5);
  q->enqueue(7);
  q->enqueue(10);
  q->dequeue();
  q->dequeue();
  std::cout << q->peek() << std::endl;
  q->dequeue();
  q->dequeue();
}

/* 数组实现循环队列示意图：队尾与队首的位置不确定，但是可以通过求模解决 (tail + 1) % length == head
  Capacity = 3, Actual Size = 4
  ------------------------------------------------------------------------------------------
  head                  tail
   ↓                     ↓           (3 + 1) % 4 == 0  -> full
   0       1       2     3
   22     33      44     0
  ------------------------------------------------------------------------------------------
          tail   head
           ↓      ↓                  (1 + 1) % 4 == 2 -> full
    0      1      2      3
   67      0     55     84
  ------------------------------------------------------------------------------------------

 */