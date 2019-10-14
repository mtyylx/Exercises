#include "../common.h"
#include <climits>
using namespace std;

/**
 * Created by Michael on 2019/9/5.
 * 
 * Merge two sorted linked lists and return it as a new list. 
 * The new list should be made by splicing together the nodes of the first two lists.
 * 
 * Example:
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 * 
 * Tags
 *      - Two Ptr: Parallel Traversal + Sentinel
 *      - Dummy Node.
 * 
 */

// Time - O(n + m), Space - O(1)
ListNode* mergeTwoLists(ListNode* l1, ListNode* l2) {
    ListNode* dummy = new ListNode(0);
    ListNode* curr = dummy;
    while (l1 != nullptr || l2 != nullptr) {
        int l = (l1 != nullptr) ? l1->val : INT_MAX;
        int r = (l2 != nullptr) ? l2->val : INT_MAX;
        if (l <= r) {
            curr->next = l1;
            l1 = l1->next;
        }
        else {
            curr->next = l2;
            l2 = l2->next;
        }
        curr = curr->next;
    }
    return dummy->next;
}