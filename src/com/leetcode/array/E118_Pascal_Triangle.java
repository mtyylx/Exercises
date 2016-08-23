package com.leetcode.array;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/8/23.
 * Given numRows, generate the first numRows of Pascal's triangle.
 *
 * For example, given numRows = 5, Return
 * [
 *      [1],
 *      [1,1],
 *      [1,2,1],
 *      [1,3,3,1],
 *      [1,4,6,4,1]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> pascalTriangle(int numRows) {...}
 * 之所以返回类型不是int[][]，是因为这个题目的特性是这里面的数组会自动变长，
 * 因此一定会使用到泛型类的ArrayList，用泛型类的前提就是每个元素都必须是对象才可以操作，
 * 又因为ArrayList<E>实现了List<E>接口，所以最后看到的函数返回类型才是List<List<Integer>>
 *
 * public int[][] pascalTriangle(int numRows) {...}
 */
public class E118_Pascal_Triangle {
    public static void main(String[] args) {
        List<List<Integer>> result = pascalTriangle(2000);
        //print2D(result);
    }

    // 改善为从ArrayList尾部追加1在逆序叠加，这样可以避免ArrayList元素频繁右移，大约节省10毫秒。
    static List<List<Integer>> pascalTriangle2(int nums) {
        List<List<Integer>> result = new ArrayList<>(nums);
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < nums; i++) {
            row.add(1);
            for (int j = row.size() - 2; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
            result.add(new ArrayList<>(row));
        }
        return result;
    }

    // 巧妙在于：没有用传统的“第i+1行的中间元素由第i行斜对角两相邻元素相加而成“规律
    // 而是使用：每一行元素都等于上一行元素在开头插入元素1后依次叠加而成。
    // 内循环j从1开始，可以自动包含nums = 1或2不需要叠加的情况。
    // 缺点是每次都要从头插入元素，需要把所有其他元素都右移一遍。
    static List<List<Integer>> pascalTriangle(int nums) {
        List<List<Integer>> result = new ArrayList<>(nums);
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < nums; i++) {
            row.add(0, 1);
            for (int j = 1; j < row.size() - 1; j++)
                row.set(j, row.get(j) + row.get(j + 1));
            result.add(new ArrayList<>(row));
        }
        return result;
    }

    static void print2D(List<List<Integer>> a) {
        for (List<Integer> row : a) {
            for (Integer x : row) {
                System.out.print(x + ",");
            }
            System.out.println("");
        }
    }
}
