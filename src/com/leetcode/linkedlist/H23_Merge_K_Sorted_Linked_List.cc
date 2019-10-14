#include "../common.h"
#include <climits>
using namespace std;

/**
 * Created by Michael on 2019/9/5.
 * 
 * Merge k sorted linked lists and return it as one sorted list. 
 * Analyze and describe its complexity.
 * 
 * Example:
 * Input:
 * [
 *      1->4->5,
 *      1->3->4,
 *      2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 * 
 * Tag
 *      - Merge Two Linked List
 *      - CUDA Reduction Sum
 *      - Binary (Divide and Conquer)
 * 
 * 本质是 Merge Two Sorted Linked List 的拓展通用形式
 * 
 * 思维陷阱：刚拿到题目，脑海里想到有 k 个链表列成一排的样子时，第一反应肯定是一次遍历 k 的链表的表头，但是这么做效率很低，因为每次扫描所有链表的表头后只能确定最小的那个（问题规模减一），并不能确定其他表头和非表头的大小排列关系
 * 正确的思维方式，是重复利用合并两个链表的思路 + 归并排序 + 分治法的思想。（1）两两合并链表 → （2）链表数量减半 → 重复步骤（1）直至只剩一个链表。
 * 
 */

/**
 * 复杂度分析
 * 首先我们简化问题，如果我们只有两个有序链表，长度分别为 n 和 m，那么合并链表操作的时间和空间复杂度是 O(n + m) 和 O(1)
 * 如果我们把任意两个链表的长度之和表示为 N，那么对于 K 个有序链表，我们会砍 log K 次，而每一次都对应于 O(N) 复杂度的两个链表合并操作
 * 因此整体时间复杂度是 Time - O(NlogK)，其中 N 是合并过程中遇到的最大 n + m，其实也就是所有链表的长度总和。
 */

//---------------------------------------------------
// 纯递归解法：两个链表的合并用的是递归，K个链表的划分和两两合并也是递归。速度也不慢。
ListNode* mergeTwoList_recursive(ListNode* l1, ListNode* l2) {
    if (l1 == nullptr) return l2;
    if (l2 == nullptr) return l1;
    if (l1->val <= l2->val) {
        l1->next = mergeTwoList_recursive(l1->next, l2);    // 左指针更小，更新左指针的 next
        return l1;
    }
    else {
        l2->next = mergeTwoList_recursive(l1, l2->next);    // 右指针更小，更新右指针的 next
        return l2;
    }
}

// vector 中的 idx 就是要合并的链表编号
// 持续二分，经过 logK 次二分，左右指针的序号相同，即只剩一个链表，开始结束递归，开始上浮，上浮的过程中对二分的两个链表调用 E21 的代码进行合并，返回新链表头
ListNode* mergeKLists(vector<ListNode*>& lists, int left, int right) {
    if (left == right) return lists[left];
    int mid = left + (right - left) / 2;
    return mergeTwoList_recursive(mergeKLists(lists, left, mid), mergeKLists(lists, mid + 1, right));
}

ListNode* mergeKLists(vector<ListNode*>& lists) {
    if (lists.empty()) return nullptr;
    return mergeKLists(lists, 0, lists.size() - 1);
}

//---------------------------------------------------
// 纯迭代解法：两个链表的合并用的是递归，K个链表的划分用的是不断除 2 直至 1 的 reduction sum 的套路，没想到 CUDA 的经典问题在这还能看到，精彩。
ListNode* mergeTwoLists_iterative(ListNode* l1, ListNode* l2) {
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

// 0 1 2 3 4 5 6
// | | |   | | |
// 0 1 2 3
// | | | |
// 0 1
// | |
// 0
// 处理奇数长度的技巧：不直接除二（那样会在中心出现落单的元素），而是始终把长度加一后，再除二
// 注意1：不要上来就加一，那样会越界，要在第一次完成后再除二
// 注意2：奇数长度并不一定出现在最开始，这取决于长度的质数分解，例如 14 是偶数，但是 14 / 2 = 7，依然是奇数
// 注意3：虽然每次折半时都会事先加一，感觉好像会重复合并链表，但是实际上并不会。因为遇上奇数长度加一除二可以带上轴心，而遇上偶数长度加一除二与不加一样，没有影响。很牛逼的特性。
ListNode* mergeKLists_iterative(vector<ListNode*>& lists) {
    if (lists.empty()) return nullptr;
    int k = lists.size();
    while (k > 1) {
        for (int i = 0; i < k / 2; i++) {
            lists[i] = mergeTwoLists_iterative(lists[i], lists[k - 1 - i]);
            // lists[k - 1 - i] = nullptr;     // 并不需要人为去重
        }
        k = (k + 1) / 2;    // 精髓：奇数加一是偶数，偶数加一是奇数，但是偶数加一除二后和不加没有区别。
    }
    return lists[0];
}

int main() {

    ListNode* head1 = new ListNode(1);
    head1->next = new ListNode(3);
    head1->next->next = new ListNode(9);

    ListNode* head2 = new ListNode(1);
    head1->next = new ListNode(3);
    head1->next->next = new ListNode(9);

    ListNode* head3 = new ListNode(1);
    head1->next = new ListNode(3);
    head1->next->next = new ListNode(9);

}