#include <iostream>
#include <vector>

/** 
 * Common data structure used in leetcode.
 * Created by Michael on 2019/8/9.
 *
 */

/** 
 * <LinkedList>
 * Pass by pointer.
 * If a ListNode ptr is nullptr, then it is a empty linkedlist.
 * If a ListNode ptr is not nullptr, *ptr is the Node itself.
 * - ptr->val / (*ptr).val is the actual value of the current node.
 * - ptr->next / (*ptr).next is the ptr of the next node.
 *
 */
struct ListNode {
  int val;
  ListNode *next;
  ListNode(int x) {  // Constructor
    val = x;
    next = nullptr;
  }

  // Print current ListNode and all its descendents.
  void print() {
    ListNode *curr = this;
    while(curr != nullptr) {
      std::cout << curr->val << " -> ";
      curr = curr->next;
    }
    std::cout << "null" << std::endl;
  }

  // Print current ListNode and its K descendents.
  void print_k_nodes(int k) {
    ListNode *curr = this;
    for (int i = 0; i < k; i++) {
      if (curr != nullptr) {
        std::cout << curr->val << " -> ";
        curr = curr->next;
      }
    }
    std::cout << "null" << std::endl;
  }

  // Create a new LinkedList from given array and return its head.
  static ListNode* create(std::vector<int>& data, bool loop = false) {
    ListNode* dummy = new ListNode(-1);
    ListNode* curr = dummy;
    for (int i = 0; i < data.size(); i++) {
      curr->next = new ListNode(data[i]);
      curr = curr->next;
    }
    std::cout << "LinkedList Created: ";
    // Make the linked list loop or not.
    if (loop) {
      curr->next = dummy->next;
      dummy->next->print_k_nodes(data.size());
    } else {
      dummy->next->print();
    }
    return dummy->next;
  }
};


/**
 * <Tree>
 * 
 */
struct TreeNode {
  int val;
  TreeNode *left;
  TreeNode *right;
  TreeNode(int x) {  // Constructor
    val = x;
    left = nullptr;
    right = nullptr;
  }
};

void print(std::vector<int>& vec) {
  for (auto x : vec) 
      std::cout << x << ", ";
  std::cout << std::endl;
}

void print2D(std::vector<std::vector<int>>& vec) {
  std::cout << "2D Matrix: \n";
  for (auto row : vec) 
    print(row);
  std::cout << std::endl;
}