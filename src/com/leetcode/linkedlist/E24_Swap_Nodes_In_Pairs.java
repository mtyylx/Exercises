package com.leetcode.linkedlist;

/**
 * Created by LYuan on 2016/9/7.
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * For example,
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 *
 * Notes:
 * Your algorithm should use only constant space.
 * You may not modify the values in the list, only nodes itself can be changed.
 *
 * Function Signature:
 * public ListNode swapNodes(ListNode head) {...}
 *
 * <Tags>
 * - Dummy节点
 * - <三指针>遍历链表：<prev | curr | next>
 * - 递归解法：<把每一个节点都视为链表的表头>，每个节点都开启了它所打头的链表。
 *
 */
public class E24_Swap_Nodes_In_Pairs {
    public static void main(String[] args) {
        swapPairs(ListNode.Generator(new int[] {1, 2, 3, 4})).print();
        swapPairs2(ListNode.Generator(new int[] {1, 2, 3, 4})).print();
        swapPairs3(ListNode.Generator(new int[] {1, 2, 3, 4})).print();
        swapPairs4(ListNode.Generator(new int[] {1, 2, 3, 4})).print();
    }


    /** 解法1：迭代 + 三指针 <prev | curr | next>。Time - o(n), Space - o(1). */
    // Tip 1: 交换一对节点看上去只涉及到两个节点，但是由于这里交换的是链表的节点，因此实际上涉及到三个节点（两个节点修改之后需要连上之前的节点），这就是使用三指针的必要性
    // Tip 2: 与一般双指针的步步平移不同，这里指针需要跳跃式的迁移。
    // Tip 3: 处理空节点的原则是只要一对节点中第一个为空或者第二个为空就结束扫描。例如 d → 1 → null 和 d → null 两种情况都应直接返回。
    // 根据上述三条准则，基本模型如下：
    // -------------------------------------
    //  Phase 1：循环开始：各指针就位
    //   d  →  1  →  2  →  3  →  4  →  ...
    //   ↑     ↑     ↑
    //  prev  curr  next
    // -------------------------------------
    //  Phase 2：节点交换完成
    //   d  →  2  →  1  →  3  →  4  →  ...
    //   ↑     ↑     ↑
    //  prev  next  curr （注意此时next和curr的位置已经交换）
    // -------------------------------------
    //  Phase 3：各指针移动至下一位置
    //   d  →  2  →  1  →  3  →  4  →  ...
    //               ↑     ↑     ↑
    //              prev  curr  next
    // -------------------------------------
    // 下面是初始解法，三个指针全在while循环之外事先定义好初始位置，缺点是针对head本身就是null的情况必须提前处理。
    static ListNode swapPairs(ListNode head) {
        if (head == null) return null;                  // 提前处理head = null的情况
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode next = head.next;                      // 此时已经确定head已经是有next的
        while (curr != null && next != null) {
            prev.next = next;                           // prev从指向curr修改为指向next
            curr.next = next.next;                      // curr从指向next修改为指向next.next
            next.next = curr;                           // next从指向next.next修改为指向curr，至此为止，当前这对节点反转完成。
            prev = curr;                                // prev移动至下一个位置
            curr = curr.next;                           // curr移动至下一位置
            next = (curr == null) ? null : curr.next;   // 针对curr = null的情况进行保护
        }
        return dummy.next;
    }
    // 简化后的代码：第三个指针next在while循环内定义，可以确保先检查curr非空且curr.next也非空的时候，才继续节点翻转的操作（即Tip3）
    // 由于next已经经过判断才赋值，因此无需一开始单独处理，结尾也无需保护
    static ListNode swapPairs2(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            ListNode next = curr.next;                  // 第三指针在检查后才使用，省去边界保护
            prev.next = next;
            curr.next = next.next;
            next.next = curr;
            prev = curr;
            curr = curr.next;
        }
        return dummy.next;
    }
    // 进一步简化代码，直接省略第三指针，但是看上逻辑没有那么清晰。
    static ListNode swapPairs3(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr;
        while (prev.next != null && prev.next.next != null) {
            curr = prev.next;               // 缓存curr节点
            prev.next = curr.next;          // 让prev指向next
            curr.next = curr.next.next;     // 让curr指向next.next
            prev.next.next = curr;          // 让next指向curr
            prev = prev.next.next;          // prev进入下一位置
        }
        return dummy.next;
    }


    /** 解法2：递归写法。Time - o(n), Space - o(n). */
    // 单链表使用递归可谓神器，代码通常会异常的简洁优美，但是缺点是需要额外的空间复杂度。
    // 核心思想：单链表的递归解法的思路就是把<每一个节点都视为链表的表头>，每个节点都开启了它所打头的链表。
    // 因此head就是迭代写法的curr，每层递归的head都是curr，无需prev指针，因为递归结构会隐式存储。
    // 可以看到本质上还是三指针解法，只不过prev节点是递归本身搞定，curr节点就是head，只需要缓存next节点即可。
    // 设计递归终止条件：不存在一对节点时就终止递归。
    // 递归条件之下就可以保证两个节点都存在，放心进行操作。
    static ListNode swapPairs4(ListNode head) {
        if (head == null || head.next == null) return head;     // 递归终止条件：只要curr为空或者next为空就直接返回当前head而无需翻转
        ListNode next = head.next;                              // 缓存next节点，curr节点就是head无需缓存
        head.next = swapPairs4(next.next);                      // head指向下一对节点翻转后的新head
        next.next = head;                                       // next指向head(curr)
        return next;                                            // next就是这一对节点的新head
    }

}
