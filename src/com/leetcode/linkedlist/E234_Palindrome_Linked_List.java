package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/9.
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Notes:
 * Could you do it in O(n) time and O(1) space?
 *
 * Function Signature:
 * public boolean isPalindrome(ListNode head) {...}
 * */
public class E234_Palindrome_Linked_List {
    public static void main(String[] args) {
        ListNode x = ListNode.Generator(new int[] {1, 2, 3, 4, 5, 4, 3, 2, 1});
        ListNode y = ListNode.Generator(new int[] {1, 2, 3, 4, 4, 3, 2, 1});
        ListNode z = ListNode.Generator(new int[] {1, 0, 1, 1});
        System.out.println(isPalindrome3(x));
        System.out.println(isPalindrome3(y));
        System.out.println(isPalindrome3(z));
    }

    // 递归解法，time - o(n), space - o(n)，优势在于不需要修改反转链表。
    // 一开始很纳闷，既然需要首尾指针同时移动，怎么可能用递归呢
    // 后来发现可以通过单独设一个指针，游离在递归函数之外独立变化
    // 在这里使用递归的原因，是因为递归可以逆序执行原本只能顺序执行的链表操作
    static boolean isPalindrome4(ListNode head) {

    }

    static boolean recursiveScan(ListNode head) {

    }

    // 构造新的反向链表，time - o(n), space - o(n)
    // 在扫描链表的同时，构造一个反向生长的链表，然后再一起扫描
    static boolean isPalindrome3(ListNode head) {
        ListNode node = head;
        ListNode prev = null;
        while (node != null) {
            ListNode newhead = new ListNode(node.val);
            newhead.next = prev;
            prev = newhead;
            node = node.next;
        }
        while (head != null) {
            if (head.val != prev.val) return false;
            head = head.next;
            prev = prev.next;
        }
        return true;
    }

    // 双指针解法，time & space - o(n)，空间复杂度最优
    // 卡在了如何让指针回退的问题上。为了能让指针回退，只能部分反转链表。
    // 所以需要先扫描一遍链表，获得长度，然后从中点开始向后反转链表，最后首尾边扫描边比对。
    static boolean isPalindrome2(ListNode head) {
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        node = head;
        for (int i = 0; i < length / 2; i++) {
            node = node.next;
        }
        // Reverse the 2nd half
        ListNode prev = null;
        ListNode next;
        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        // Head and Tail Scan & Compare
        for (int i = 0; i < length / 2; i++) {
            if (head.val != prev.val) return false;
            head = head.next;
            prev = prev.next;
        }
        return true;
    }

    // 一开始考虑到轴对称的链表元素一定两两重复，因此刚好可以用异或来解决
    // 但是两两重复并不等效于轴对称，1, 1, 2, 3, 3虽然两两重复，但是并不是镜像的
    // 所以轴对称不能用异或。
    static boolean isPalindrome(ListNode head) {
        int sum = 0;
        int length = 0;
        ListNode node = head;
        while (node != null) {
            sum ^= node.val;
            node = node.next;
            length++;
        }

        if (length % 2 == 0)
            if (sum == 0) return true;
            else return false;
        else {
            node = head;
            for (int i = 0; i < length / 2; i++) node = node.next;
            if (node.val == sum) return true;
            else return false;
        }
    }
}
