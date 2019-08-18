#include "../common.h";
using namespace std;

/** 
 * You are given two non-empty linked lists representing two non-negative integers. 
 * The digits are stored in reverse order and each of their nodes contain a single digit. 
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * 
 * Example:
 * 
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 * 
 * 基本思路
 *      - Dummy Node：进退自如
 *      - Math Carry：除号和求余
 *      - 双指针同向同速扫描
 *      - 边界情况处理：尾节点有 carry 时需要创建新节点
 * 
 */

ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
    ListNode* dummy = new ListNode(-1);
    ListNode* curr = dummy;
    int carry = 0;
    while (l1 != nullptr || l2 != nullptr) {
        int sum = carry;
        if (l1 != nullptr) {
            sum += l1->val;
            l1 = l1->next;
        }
        if (l2 != nullptr) {
            sum += l2->val;
            l2 = l2->next;
        }
        carry = sum / 10;
        sum = sum % 10;
        curr->next = new ListNode(sum);
        curr = curr->next;
    }
    if (carry != 0) curr->next = new ListNode(carry);       // 非常容易忘记这种情况。如果两个链表等长，且最后元素存在进位，则必须创建新节点。
    return dummy->next;
}