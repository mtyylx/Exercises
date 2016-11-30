package com.leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/11/30.
 * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 *
 * Example 1:
 * Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
 *
 * Example 2:
 * Given the list [1,[4,[6]]], return 27. (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
 *
 * Function Signature:
 * public int nestSum(List<NestedInteger> nestedList) {...}
 */
public class E339_NestedList_Weight_Sum {
    public static void main(String[] args) {
        System.out.println(depthSum(new ArrayList<>()));
    }

    /** DFS解法1：定义成员变量，每一层递归都可以更新该变量。递归终止条件是当前对象是单值。 */
    private static int sum = 0;
    static int depthSum(List<NestedInteger> nestedList) {
        for (NestedInteger ni : nestedList)
            dfs(ni, 1);
        return sum;
    }

    private static void dfs(NestedInteger element, int depth) {
        if (element.isInteger())
            sum += depth * element.getInteger();
        else
            for (NestedInteger ni : element.getList())
                dfs(ni, depth + 1);
    }

    /** DFS解法2：不使用成员变量，每一层递归累加传递，递归终止条件是最后一层深度for遍历结束。 */
    static int depthSum2(List<NestedInteger> nestedList) {
        return dfs2(nestedList, 1);
    }

    private static int dfs2(List<NestedInteger> element, int depth) {
        int sum = 0;
        for (NestedInteger ni : element) {
            if (ni.isInteger()) sum += depth * ni.getInteger();
            else                sum += dfs2(ni.getList(), depth + 1);
        }
        return sum;
    }

}

class NestedInteger {
    public boolean isInteger() {
        return true;
    }

    public int getInteger() {
        return 0;
    }

    public List<NestedInteger> getList() {
        return new ArrayList<>();
    }
}
