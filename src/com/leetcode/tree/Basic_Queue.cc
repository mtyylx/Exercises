#include "../common.h"

/**
 * Created by Michael on 2019/8/10.
 * 
 * Cyclic Queue, implement using array or linked list.
 * 基本功能：add，remove，peek
 * 队列vs栈：先进先出vs先进后出。
 * 循环队列：很常见的一种队列类型，关键在于存储长度受限。
 * 
 * <循环队列的实现>
 * 0. 内存申请
 *      - 与栈边用边申请边扩充的方式不同，循环队列的内存空间是在初始阶段一次性生成好的。
 * 1. 队首指针与队尾指针的规则：
 *      - LinkedList 用<指针>，Vector 用<索引值>
 *      - 队首 head 指向 <队首元素> 
 *      - 队尾 tail 指向 <队尾元素的下一个元素>（而不是队尾元素）
 *      - 添加元素：在队尾添加，移动 tail
 *      - 删除元素：移除队头，移动 head
 * 2. 队列判空与队列判满的规则：
 *      - 队列最多只能放 n - 1 个元素，n 是内存申请空间。这么做是为了区分<队列空>和<队列满>这两种状态所做的牺牲。
 *      - 队列判空：队首指针 head 与队尾指针 tail 相同
 *      - 队列判满：
 *          - LinkedList方式：队尾指针 tail 的下一个元素 tail->next 与队首指针 head 相同
 *          - Vector方式：队首和队尾用索引值访问，索引取值范围是 0 - length，(tail + 1) % length == head 作为判断依据
 * 
 */

class CyclicQueueLL {

private:
  ListNode* head = nullptr;
  ListNode* tail = nullptr;
  int size = 0;

public:
  // Init a empty queue with given capacity.
  CyclicQueueLL(int capacity) {
    std::vector<int> data(capacity + 1);    // The actual length should + 1.
    bool loop = true;
    head = ListNode::create(data, loop);    // Allocate memory on heap, and make it loop.
    tail = head;                            // Empty Queue Initialized.
  }

  // Add element to the tail of this queue.
  ListNode* add(int val) {
    // Check if full first
    if (tail->next == head) {
      std::cout << "Queue is full. Cannot add element." << std::endl;
        return nullptr;
    }
    tail->val = val;                      // Update val.
    tail = tail->next;                    // Move tail to new tail.
    size ++;
  }

  // Remove element from the head of this queue.
  int remove() {
    // Check if empty first
    if (head == tail) {
      std::cout << "Queue is empty. Cannot remove element." << std::endl;
      return -1;
    }
    int res = head->val;
    head->val = 0;                       // Clean val.
    head = head->next;                   // Move head to new head.
    size --;
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
  
  CyclicQueueLL* q = new CyclicQueueLL(3);
  q->add(2);
  q->add(5);
  q->add(7);
  q->add(10);
  q->remove();
  q->remove();
  std::cout << q->peek() << std::endl;
  q->remove();
  q->remove();
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