package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/8/23.
 * Given an index k, return the kth row of the Pascal's triangle.
 *
 * For example, given k = 3,
 * Return [1,3,3,1].
 * Could you optimize your algorithm to use only O(k) extra space?
 *
 * Function Signature:
 * public List<Integer> pascalTriangle(int nums) {...}
 */
public class E119_Pascal_Triangle_2 {
    public static void main(String[] args) {
        List<Integer> result = pascalTriangle(10);
        int[] result2 = pascalTriangle2(10);
        for (int x : result) {
            System.out.print(x + ",");
        }
        System.out.println("");
        for (int x : result2) {
            System.out.print(x + ",");
        }
    }

    // 和E118相比更简单，连二维ArrayList都不用保存了
    // 依然使用逆序扫描，这样可以确保元素是追加而不是插入平移。
    // 需要明白的是，这里返回值不用int[]而用List<Integer>是有充分原因的
    // 因为Integer[]和int[]这两个类型之间是不能cast的，只有对数组元素遍历的同时cast才可能。
    static List<Integer> pascalTriangle(int nums) {
        List<Integer> row = new ArrayList<>(nums+1);
        for (int i = 0; i <= nums; i++) {
            row.add(1);
            for (int j = row.size() - 2; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }
        return row;
    }

    // 由于这个题的返回数组长度并不是自增的，而是只要给出行号就已经长度完全确定，所以使用基础类型数组是完全一样的
    // 如果说有区别的话，就是这种解法没法返回List<Integer>类型，返回的是int[]
    static int[] pascalTriangle2(int nums) {
        int[] row = new int[nums + 1];
        int length = 0;
        for (int i = 0; i <= nums; i++) {
            row[length++] = 1;
            for (int j = length - 2; j > 0; j--) {
                row[j] += row[j - 1];
            }
        }
        return row;
    }
}
