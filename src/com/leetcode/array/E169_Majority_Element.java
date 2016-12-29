package com.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/8/19.
 *
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 * You may assume that the array is non-empty and the majority element always exist in the array.
 *
 * Functions Signature:
 * public int majorityElement(int[] a){...}
 *
 * <Tags>
 * - Moore Voting Algorithm: Find majority.
 * - HashMap: Collect Frequency Statistics
 * - Sort
 *
 */
public class E169_Majority_Element {
    public static void main(String[] args) {
        System.out.println("The Majority Element is = " + majorityElement(new int[]{1, 2, 3, 2, 2}));
        System.out.println("The Majority Element is = " + majorityElement2(new int[]{1, 2, 3, 2, 2}));
        System.out.println("The Majority Element is = " + majorityElement3(new int[]{1, 1, 2, 2, 3, 3}));
        System.out.println("The Majority Element is = " + majorityElement4(new int[]{1, 1, 2, 2, 3, 3}));

        System.out.println("The Majority Element is = " + majorityElementX(new int[]{1, 2, 3, 2, 1}));
        System.out.println("The Majority Element is = " + majorityElementX(new int[]{1, 2, 3, 2, 2}));
    }

    /** 解法3：最潮解法 - Moore Voting，模拟投票抵消的过程。Time - o(n), Space - o(1) */
    // 在时间和空间复杂度上的性能最优，算是处理majority element的至尊大宝剑。
    // 原理是通过抵消不同元素的投票值来计算谁笑到最后。
    // 0 0 0 9 9 9 9 --- 前三个0和前三个9抵消，所以轮到第四个9的时候，9就胜出了，虽然此时count只有1
    // 0 9 0 9 0 9 9 --- 前三对0和9抵消，最后一个9还是会胜出。
    // 上面这两个数组虽然元素顺序不同，但是如果使用相互抵消的角度看，其实是一样的
    static int majorityElement3(int[] a) {
        int count = 0;
        int major = a[0];
        for (int x : a) {
            if (count == 0) major = x;  // 隐式的避免了count小于0的情况出现，省去了手动重置count。
            if (major == x) count++;
            else            count--;
        }
        return major;
    }

    // 另一种写法，可以确保返回值是最后一个出现频率最高的元素。对于[1, 1, 2, 2, 3, 3]会返回3。
    static int majorityElement4(int[] a) {
        int majority = a[0];
        int count = 1;
        for (int x : a) {
            if (x == majority) count++;
            else count--;
            if (count < 1) { majority = x; count = 1; }     // 由于count有可能小于1甚至变成负的，因此需要手动重置count。
        }
        return majority;
    }

    /** <问题的引申解法> 如果数组中不保证存在多数席位，那么使用投票法需要<扫描两轮>。 */
    //  例如 a = [1, 2, 3, 2, 1]，这里面没有一个元素出现次数超过3次，因此并不存在Majority Element。
    //  如果还像上面只扫描一次的话，那么返回的结果将是出现频率最高的元素之一。
    static int majorityElementX(int[] a) {
        int major = a[0];
        int count = 1;
        for (int x : a) {
            if (count == 0) major = x;
            if (major == x) count++;
            else count--;
        }
        count = 0;
        for (int x : a) if (x == major) count++;
        return (count > a.length / 2) ? major : -1;     // 扫描第二轮确定major是真的多数席位，否则返回-1。
    }


    /** 解法2：哈希表，统计出现次数。Time - o(n), Space - o(n) */
    // 为了o(n)的时间复杂度，付出了o(n)的空间复杂度。
    static int majorityElement2(int[] a) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) map.put(x, map.get(x) + 1);
            else map.put(x, 1);
            if (map.get(x) > a.length / 2) return x;    // Early Exit
        }
        return -1;

    }

    /** 解法1：先排序后取中点，写法最简单。Time - o(nlogn), Space - o(1) */
    // 为了o(1)的空间复杂度，付出了o(nlogn)的时间复杂度。
    // 之所以个数需要大于一半元素个数，而不是大于等于，是因为等于一半元素个数会导致出现两个majority元素。
    static int majorityElement(int[] a) {
        Arrays.sort(a);
        return a[a.length / 2];
    }
}
