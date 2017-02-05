package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/9.
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Notes: Could you do it in O(n) time and O(1) space?
 *
 * Function Signature:
 * public boolean isPalindrome(ListNode head) {...}
 *
 * <Tags>
 * - 递归：利用逆序递归的特性，达到反向扫描链表的目的。当然代价是空间复杂度为o(n)。
 * - 双指针：快慢指针获得链表中点位置。[slow → → → ... fast → → → ... ]
 * - 双指针：中心扩散扫描。    [ ... ← ← ← left | right → → → ... ]
 * - 链表反转：两段法
 * - 链表反转：插入法
 *
 */
public class E234_Palindrome_Linked_List {
    public static void main(String[] args) {
        System.out.println(isPalindrome(ListNode.Generator(new int[] {1, 2, 3, 2, 1})));
        System.out.println(isPalindrome(ListNode.Generator(new int[] {1, 2, 3, 3, 2, 1})));
        System.out.println(isPalindrome(ListNode.Generator(new int[] {1, 2, 4, 3, 2, 1})));
        System.out.println(isPalindrome2(ListNode.Generator(new int[] {1, 2, 3, 2, 1})));
        System.out.println(isPalindrome2(ListNode.Generator(new int[] {1, 2, 3, 3, 2, 1})));
        System.out.println(isPalindrome2(ListNode.Generator(new int[] {1, 2, 4, 3, 2, 1})));
        System.out.println(isPalindrome3(ListNode.Generator(new int[] {1, 2, 3, 2, 1})));
        System.out.println(isPalindrome3(ListNode.Generator(new int[] {1, 2, 3, 3, 2, 1})));
        System.out.println(isPalindrome3(ListNode.Generator(new int[] {1, 2, 4, 3, 2, 1})));
        System.out.println(isPalindrome4(ListNode.Generator(new int[] {1, 2, 3, 2, 1})));
        System.out.println(isPalindrome4(ListNode.Generator(new int[] {1, 2, 3, 3, 2, 1})));
        System.out.println(isPalindrome4(ListNode.Generator(new int[] {1, 2, 4, 3, 2, 1})));
    }


    /** 解法3：双指针（快慢指针）寻找链表中点 + 前半段链表反转（两段法）。空间复杂度最优解。Time - o(n), Space - o(1). */
    // 首先用快慢指针定位链表中点，然后根据链表长度的奇偶情况分别处理。
    //  奇数长度  1 -> 2 -> 3 -> 2 -> 1
    //           ← ← ←| IGNORE |→ → →
    //  偶数长度  1 -> 2 -> 3 -> 3 -> 2 -> 1
    //                ← ← ←|   |→ → →
    // 由于我们只需要反转链表的前半段，因此我们在寻找中点的时候必须要确保指针不要过站，因为过了这个村就没有回头路了。
    // 因此循环终止条件应该是fast.next.next都不是null，这样可以确保在偶数长度情况下，slow指针会停在中间位置靠左（而不是靠右的那个）
    // Odd               Even                  Odd                       Even
    // 1 -> null         1 -> 2 -> null        1 -> 2 -> 3 -> null       1 -> 2 -> 3 -> 4 -> null
    // ↑                 ↑                          ↑    ↑                    ↑    ↑
    // slow & fast       slow & fast              slow  fast                slow  fast
    // 可以看到通过这种方式我都不用计数就可以知道链表长度到底是奇数还是偶数。只需要在循环结束之后看fast.next为空还是fast.next.next为空即可。
    // Odd                 Even                       Odd                              Even
    // null <- 1  null     null <- 1   2 -> null      null <- 1 <- 2   3 -> null       null <- 1 <- 2   3 -> 4 -> null
    // ↑           ↑        ← ← ←  ↑   ↑  → → →        ← ← ←  ↑        ↑  → → →              ← ← ←  ↑   ↑  → → →
    // left       right          left  right                 left     right                       left  right
    static boolean isPalindrome3(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        boolean odd = (fast.next == null);              // 提前记录链表是奇数长度还是偶数长度（因为对于长度为2的链表反转后fast的判断依据将失效）
        ListNode right = slow.next;                     // 右半部的起始节点
        ListNode prev = null;
        while (head != right) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        ListNode left = odd ? prev.next : prev;         // 左半部的起始节点，如果是奇数长度则需要跳过中心节点
        while (left != null) {                          // 双指针中心扩散，同步扫描
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }

    /** 解法4：简化写法，半程扫描和链表反转同时完成 + 前半段链表反转（插入法）。Time - o(n), Space - o(1). */
    // 似乎分段反转法会有一些问题，于是这里使用插入反转。
    // dummy -> 1 -> 2 -> 3 -> 4 -> 4 -> 3 -> 2 -> 1 -> null
    //          ↑
    //        slow (pivot)
    // dummy -> 2 -> 1 -> 3 -> 4 -> 4 -> 3 -> 2 -> 1 -> null
    // dummy -> 3 -> 2 -> 1 -> 4 -> 4 -> 3 -> 2 -> 1 -> null
    // dummy -> 4 -> 3 -> 2 -> 1 -> 4 -> 3 -> 2 -> 1 -> null
    //          ↑              ↑    ↑
    //         left          slow  right
    static boolean isPalindrome4(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            ListNode next = slow.next;
            slow.next = next.next;
            next.next = dummy.next;
            dummy.next = next;
        }
        ListNode left = (fast.next == null) ? dummy.next.next : dummy.next;     // 左半部起始节点，如果奇数则需要跳过第一个节点
        ListNode right = slow.next;                                             // 右半部起始节点
        while (right != null) {                                                 // 以右指针结束为循环条件
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }

    /** 解法2：递归（逆序递归）。Time - o(n), Space - o(n). */
    // 轴对称判定要求你必须要同时正序和逆序扫描，这对于链表来说是做不到的，但是如果用递归的话，可以曲线达到逆序扫描的目的。
    // 逆序递归的关键就是先递归，递归完了才开始干实事。<递归本身的唯一目的>就是先抵达最后一个节点并触发递归终止条件而已。
    // 一旦触发了递归终止条件，并开始向上返回，那么我们就做到了逆序扫描链表这件事，此时我们需要一个脱离于递归函数的指针同时进行正序扫描。
    private static ListNode ptr;                        // 正序扫描指针，脱离于递归函数存在，只要递归函数修改它就不会变化
    static boolean isPalindrome2(ListNode head) {
        ptr = head;
        return recursiveScan(head);
    }

    static boolean recursiveScan(ListNode head) {
        if (head == null) return true;                  // 递归终止条件
        boolean result = recursiveScan(head.next);      // 先递归，直达最后一个节点，触发递归终止条件。
        result = result && (ptr.val == head.val);       // ptr是正序扫描指针，head是逆序扫描指针
        ptr = ptr.next;                                 // 移动正序扫描指针
        return result;
    }


    /** 解法1：双指针构造反转链表。Time - o(n), Space - o(n). */
    // 在正序扫描链表的同时，拷贝每个节点的值，构造一个顺序相反的链表。然后再从头同步扫描两个链表。
    static boolean isPalindrome(ListNode head) {
        ListNode curr = head;                           // 双指针 curr
        ListNode prev = null;                           // 双指针 prev
        while (curr != null) {
            ListNode node = new ListNode(curr.val);     // 初始化新的节点，因此整体需要o(n)的额外空间
            node.next = prev;
            prev = node;
            curr = curr.next;
        }
        while (head != null) {
            if (head.val != prev.val) return false;
            head = head.next;
            prev = prev.next;
        }
        return true;
    }
}
