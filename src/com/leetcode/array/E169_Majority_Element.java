package com.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by LYuan on 2016/8/19.
 *
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 * You may assume that the array is non-empty and the majority element always exist in the array.
 */
public class E169_Majority_Element {
    public static void main(String[] args) {
        System.out.println("The Majority Element is = " + majorityElement3(new int[]{1, 2, 3, 2, 2}));
    }

    // 排序法
    // 先排序再找中点元素，排序复杂度为o(nlogn)
    static int majorityElement3(int[] a) {
        Arrays.sort(a);
        return a[a.length/2];
    }

    // Moore Voting：投票抵消解法，o(n)
    // 这个算法的原理是通过抵消不同元素的投票值来计算谁笑到最后。
    // 0 0 0 9 9 9 9 --- 前三个0和前三个9抵消，所以轮到第四个9的时候，9就胜出了，虽然此时count只有1
    // 0 9 0 9 0 9 9 --- 前三对0和9抵消，最后一个9还是会胜出。
    // 上面这两个数组虽然元素顺序不同，但是如果使用相互抵消的角度看，其实是一样的
    static int majorityElement2(int[] a) {
        int count = 0;
        int major = a[0];
        for (int i = 0; i < a.length; i++) {
            if (count == 0)     major = a[i];
            if (major == a[i])  count++;
            else                count--;
        }
        return major;
        // 如果数组并不不保证存在多数席位，就需要重新扫描一遍，检查major是不是真的个数多于长度的一半
        // 不能通过检查count等于0来检查，因为对于1, 2, 3, 2, 1这种情况，1与2抵消，3与2抵消，到最后一个1的时候count实际为1
        // 但是此时并不存在一个多数席位。
        //int cnt = 0;
        //for (int x : a) if (x == major) count++;
        //if (cnt > a.length/2 + 1) return major;
    }


    // 哈希表解法：o(n)
    // 使用HashMap的键值对统计出现次数，只要出现一个键的值超过长度的一半就知道结果了。
    static int majorityElement(int[] a) {
        HashMap<Integer, Integer> map = new HashMap();
        for(int x : a) {
            if (map.containsKey(x))
                map.put(x, map.get(x) + 1);
            else
                map.put(x, 1);
            // 并不需要找HashMap中值最大的键，只需要找到一个键的值大于n/2即可
            if (map.get(x) > a.length/2)
                return x;
        }
        return -1;
    }
}
