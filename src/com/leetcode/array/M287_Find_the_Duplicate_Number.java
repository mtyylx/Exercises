package com.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2017/2/1.
 *
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
 * prove that at least one duplicate number must exist.
 * Assume that there is only one duplicate number, find the duplicate one.
 *
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 *
 * Function Signature:
 * public int findDuplicate(int[] a) {...}
 *
 * <链表循环检测 系列问题>
 * - E141 Linked List Cycle  : 给定一个单链表，判断链表是否存在循环。
 * - E142 Linked List Cycle 2: 给定一个单链表，返回链表循环的起始节点，如果没有循环则返回null。
 * - M287 Find Duplicate     : 给定一个长度为n的数组，取值范围为n-1，只包含一个重复元素，求这个重复元素。
 *
 * <Tags>
 * - Binary Search (Two Pointers): 左右指针包围扫描。[left → ... ← right] 不过这里左右指针的范围是元素的取值范围，而不是数组的索引位置。
 * - Two Pointers: 快慢指针进行循环检测。
 * - Sort: 先排序在解决问题
 * - HashMap: 判重
 * - Value-As-Index：判重
 *
 */
public class M287_Find_the_Duplicate_Number {
    public static void main(String[] args) {
        System.out.println(findDuplicate(new int[] {1, 1, 2, 3}));
        System.out.println(findDuplicate2(new int[] {1, 1, 2, 3}));
        System.out.println(findDuplicate3(new int[] {1, 1, 2, 3}));
        System.out.println(findDuplicate4(new int[] {1, 2, 2, 3}));
        System.out.println(findDuplicate5(new int[] {1, 2, 2, 3}));
    }

    /** 解法5：双指针（快慢指针循环检测）。最优解法，时间和空间复杂度都是最低的。Time - o(n), Space - o(1). */
    // 非常巧妙的将数组转换成为了链表。元素值接着作为元素索引使用。很牛逼。思路借鉴自E142。
    // [1, 4, 3, 4, 5, 6, 4] 等效于 1 -> 4 -> 5 -> 6 -> 4 -> 5 -> 6 -> ...
    // [3, 1, 3, 2] 等效于 3 -> 2 -> 3 -> 2 -> ...
    static int findDuplicate5(int[] a) {
        if (a == null || a.length == 0) return 0;
        int slow = a[0];            // slow = head.next
        int fast = a[a[0]];         // fast = head.next.next
        while (slow != fast) {
            slow = a[slow];
            fast = a[a[fast]];
        }
        int head = 0;               // 找到相汇点（partA），再向前移动partB距离，就抵达了循环起点元素。
        while (slow != head) {
            slow = a[slow];
            head = a[head];
        }
        return slow;
    }


    /** 解法4：不排序使用Binary Search。优点是不会修改原数组而且空间复杂度也低。Time - o(nlogn), Space - o(1). */
    // 通常用二分查找的前提都是数组已排序，不过这里却很少见的对未排序数组使用Binary Search。
    // 区别于一般的二分查找，这里的左右指针值不是数组的上下索引，而是数组元素取值范围的上下界。
    // 之所以可以这么做的原因是，这里已经知道数组中只有一个重复元素。因此取值的分布个数一定是不均匀的。
    // [3, 1, 3, 2, 4]的实际取值范围一定是区间[1, 4]
    // 1: 1
    // 2: 1
    // 3: 2
    // 4: 1
    // 可以看到区间[1, 4]的右侧区间个数多，因此下个取值区间应该在右侧。
    static int findDuplicate4(int[] a) {
        int i = 1;                              // 左右指针的初始位置是元素取值范围的上下界（而不是索引上下界）
        int j = a.length - 1;
        while (i <= j) {                        // 标准的Binary Search Routine.
            int mid = i + (j - i) / 2;
            int count = 0;
            for (int x : a)                     // 每个循环都要遍历整个数组，但每个循环都会将取值范围缩小为原来的一般，因此总共时间复杂度为N*logN.
                if (x <= mid) count++;
            if (count <= mid) i = mid + 1;
            else              j = mid - 1;
        }
        return i;
    }

    /** 解法3：Sort + 线性扫描。优点是不使用额外空间，缺点是排序会修改原数组。Time - o(nlogn), Space - o(1). */
    static int findDuplicate3(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) return a[i];
        }
        return -1;
    }

    /** 解法2：Value-As-Index判重。缺点是会使用额外空间。Time - o(n), Space - o(n). */
    static int findDuplicate2(int[] a) {
        int[] map = new int[a.length + 1];
        for (int x : a) {
            if (map[x] != 0) return x;
            else map[x] = 1;
        }
        return -1;
    }

    /** 解法1：HashSet判重。缺点是会使用额外空间。Time - o(n), Space - o(n). */
    static int findDuplicate(int[] a) {
        Set<Integer> set = new HashSet<>();
        for (int x : a)
            if (!set.add(x)) return x;
        return -1;
    }
}
