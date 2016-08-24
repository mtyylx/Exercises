package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/8/24.
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 *
 * For example,
 * Given nums = [0, 1, 3] return 2.
 * Your algorithm should run in linear runtime complexity.
 * Could you implement it using only constant extra space complexity?
 *
 * Function Signature:
 * public int missingNumber(int[] a) {...}
 */
public class M268_Missing_Number {
    public static void main(String[] args) {
        int[] a = {0, 2, 3};
        System.out.println(missingNumber3(a));
    }

    // 法3：先排序后找，o(nlogn)
    // 利用数组索引与元素内容的相同的特性迅速找到
    static int missingNumber3(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++)
            if (i != a[i]) return i;
        return a.length;
    }

    // 法2：Bit Manipulation解法：使用XOR特性， o(n)
    // 虽然，比特反转法比较难想，不如一般直觉的算法好理解，
    // 但是他绝对的优势在于他会<避免出现各种溢出风险>，而且运算速度是所有操作中最快的
    // 这道题的具有的一个特点是，不管数组是什么顺序，数组的元素取值范围和数组索引的范围一定是一样的
    // 因此把所有索引和所有元素异或在一起，最后的结果一定是0，缺的那个元素则会留到最后
    // {0, 2, 3} = (0 ^ 0 ^ 1 ^ 2 ^ 2 ^ 3) ^ 3 = 1
    static int missingNumber2(int[] a){
        int result = a.length;
        for (int i = 0; i < a.length; i++)
            result = result ^ i ^ a[i];
        return result;
    }

    // 法1：求和公式解法，o(n). 缺陷在于如果数组长度超过int类型长度，则会溢出
    // 数组长度是切入点
    // 数组少一个元素，因此可以直接从数组长度求出该数组如果不少那个元素的时候的和
    // sum = (0 + len) * (len + 1) / 2 中，算上了少的元素，所以首项是0，末项是len - 1 + 1，长度是len + 1.
    // 如果想通过在扫描过程中找到最大值来判断数组长度的话，
    // 会碰到{0, 1, 2} 和 {1, 2, 3} 这两种情况需要区分，就不够简洁优美了
    static int missingNumber(int[] a) {
        int len = a.length;
        int sum = (0 + len) * (len + 1) / 2;
        for (int x : a)
            sum -= x;
        return sum;
    }
}
