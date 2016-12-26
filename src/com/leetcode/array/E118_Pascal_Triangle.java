package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
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
 *
 * <系列问题>
 * - E118 Pascal Triangle : 给定行号，求解整个杨辉三角。
 * - E119 Pascal Triangle2: 给定行号，求解杨辉三角的这一行。
 *
 * <Tags>
 * - Min/Max Sentinel
 * - Reverse Scan
 *
 */
public class E118_Pascal_Triangle {
    public static void main(String[] args) {
        print2D(pascalTriangle(20));
        print2D(pascalTriangle2(20));
    }

    /** 更巧妙地解法：使用同一个List，不断在这个list上修改，并将复制版本加入result。逆序扫描。 */
    // 计算第i行时，先把第(i-1)行尾部添上一个1，然后逆序相邻叠加。很巧妙。
    // [1 2 1]
    // [1 2 1 1]
    //     \|
    // [1 2 3 1]
    //   \|
    // [1 3 3 1]
    static List<List<Integer>> pascalTriangle2(int nums) {
        List<List<Integer>> result = new ArrayList<>(nums);
        List<Integer> row = new ArrayList<>();              // 无需处理n = 0和n = 1的情况，自动搞定。
        for (int i = 0; i < nums; i++) {
            row.add(1);                                     // 在上一行的基础上尾部追加一个1.
            for (int j = row.size() - 2; j > 0; j--)        // 逆序扫描，否则先改完的会影响后改的元素
                row.set(j, row.get(j) + row.get(j - 1));
            result.add(new ArrayList<>(row));               // 拷贝新实例，切忌直接加入result
        }
        return result;
    }

    /** 最初解法：每行都由上一行结果相邻叠加而成，使用极大极小值做卫兵避免越界。 */
    // 针对每一行的元素都是一个倒直角的构造，可以分解为如下面所示：
    // [1 2 1]   0 1   1 2   2 1   1 0
    //            \|    \|    \|    \|
    // [1 3 3 1]   1     3     3     1
    // 其中首尾的0是虚拟出来的，用卫兵实现。
    static List<List<Integer>> pascalTriangle(int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (n == 0) return result;
        if (n > 0) result.add(new ArrayList<>(Arrays.asList(1)));   // 专门处理第0行
        for (int i = 1; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                int l = (j > 0) ? result.get(i - 1).get(j - 1) : 0; // 卫兵，虚拟的0
                int r = (j < i) ? result.get(i - 1).get(j) : 0;     // 卫兵，虚拟的0
                row.add(l + r);
            }
            result.add(row);
        }
        return result;
    }

    static void print2D(List<List<Integer>> a) {
        for (List<Integer> row : a) {
            for (Integer x : row) System.out.print(x + ",");
            System.out.println("");
        }
    }
}
