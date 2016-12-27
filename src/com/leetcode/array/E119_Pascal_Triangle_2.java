package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
 *
 * <系列问题>
 * - E118 Pascal Triangle : 给定行号，求解整个杨辉三角。
 * - E119 Pascal Triangle2: 给定行号，求解杨辉三角的这一行。
 *
 * <Tags>
 * - Reverse Scan
 * - DP: Memoization
 * - Integer[] to ArrayList<Integer> using Arrays.asList()
 *
 */
public class E119_Pascal_Triangle_2 {
    public static void main(String[] args) {
        System.out.println(pascalTriangle(4).toString());
        System.out.println(pascalTriangle2(4).toString());
        System.out.println(pascalTriangle3(4).toString());
    }

    /** 解法1：与E118相同的解法。反向扫描不断更新扩容数组。Time - o(n^2), Space - o(n) */
    // 外循环扩容，内循环更新一次一整行。一共更新n次。
    static List<Integer> pascalTriangle(int n) {
        List<Integer> row = new ArrayList<>(n + 1);     // 避免容量扩容
        for (int i = 0; i <= n; i++) {
            row.add(1);
            for (int j = row.size() - 2; j > 0; j--)
                row.set(j, row.get(j) + row.get(j - 1));
        }
        return row;
    }

    /** 解法2：很巧妙的使用Integer[]直接生成全0数组，仅首元素为1，省去了添加1的过程。最后转为ArrayList返回。*/
    //  [1 0 0 0 0]
    //    \|        j = 1 to 1
    //  [1 1 0 0 0]
    //    \|\|      j = 2 to 1
    //  [1 2 1 0 0]
    //    \|\|\|    j = 3 to 1
    //  [1 3 3 1 0]
    //    \|\|\|\|  j = 4 to 1
    //  [1 4 6 4 1]
    // 可以看到，尾部的1等效于每次循环的时候追加在尾部的1，一开始补的0会逐渐的被填满。
    // 注意这里使用的是Integer[]数组，而不是int[]数组。因为Arrays.asList只接受对象数组。
    static List<Integer> pascalTriangle2(int n) {
        Integer[] row = new Integer[n + 1];
        Arrays.fill(row, 0);                        // 填上全0
        row[0] = 1;                                     // 种子值1，等效于每个循环开始在尾部添加1
        for (int i = 1; i <= n; i++)                    // 外循环用于控制更新次数
            for (int j = i; j > 0; j--)                 // 内循环的起始点不再从整个数组的尾部开始，而是从有1的位置开始往回扫
                row[j] += row[j - 1];                   // 相比于上面频繁get和set的写法，这里无疑更为简洁易读。
        return Arrays.asList(row);
    }

    /** 解法3：直接使用杨辉三角的公式（不是递推公式，而是直接计算得到）获得第n行的元素。Time - o(n), Space - o(n) */
    // C(n,i) = C(n, i-1) * (n - (i - 1)) / i
    // [1  4          6          4          1         ]
    //  1, 1 * 4 / 1, 4 * 3 / 2, 6 * 2 / 3, 4 * 1 / 4.
    static List<Integer> pascalTriangle3(int n) {
        Integer[] row = new Integer[n + 1];
        Arrays.fill(row, 0);
        row[0] = 1;
        for (int i = 1; i <= n; i++) {
            row[i] = (int) ((long) row[i - 1] * (n - i + 1) / i);
        }
        return Arrays.asList(row);
    }

}
