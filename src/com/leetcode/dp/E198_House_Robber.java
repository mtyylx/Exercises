package com.leetcode.dp;

/**
 * Created by LYuan on 2016/10/27.
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected,
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Function Signature:
 * public int houseRobber(int[] a) {...}
 */
public class E198_House_Robber {
    public static void main(String[] args) {
        System.out.println(houseRobber2(new int[] {3, 7, 5, 2, 4, 6, 1, 8}));
    }

    // 首先，不能一上来就天真的认为只要按照隔一个的方式计算所有偶数个元素之和以及所有奇数个元素之和选最大的就是答案了。
    // 这么算没有考虑这种情况：[50, 0, 0, 100] -> 显然单独计算奇数序号元素和以及偶数序号元素都不是最大值，而是150，隔两个元素。

    /** DP解法，Memoization，BottomUp，Iterative：Time - o(n), Space - o(1) */
    // 一开始分析问题时，能想到的是，决策当前房间是否打劫的决策，本身依赖对于之前所有房间的决策。
    // 但是，实际上，由于我们只关心最大值，所以到当前房间为止的房间范围内，最大值应该是rob和skip两者中的最大值。
    // Case 1. 打劫至当前房间的最大值：rob
    // Case 2. 跳过至当前房间的最大值：skip
    // Case 1：如果打劫当前房间，那么为了让rob达到最大值，只能依赖上个房间提供的最大值，又因为相邻房间不能都打劫，因此准确的说是只能依赖上个房间不打劫时的最大值。
    // 所以整体来看，rob的值就等于prevSkip + 当前房间值
    // Case 2：如果跳过当前房间，那么为了让skip达到最大值，同样只能依赖上个房间提供的最大值，此时由于当前房间跳过，因此上个房间既可以跳过，也可以打劫。
    // 所以整体来看，skip的值就等于prevSkip和prevRob两者之间的最大值。
    // 到此为止，我们确定的相邻两个房间的状态转移关系，并可以看到，到当前房间位置的范围内的最大值，只跟上一个房间的范围最大值有关。
    //          [3  7  5  2  4  6  1  8]
    // prevRob   0  3  7  8  9 12 15 13
    // prevSkip  0  0  3  7  8  9 12 15
    // rob       3  7  8  9 12 15 13 23
    // skip      0  3  7  8  9 12 15 15
    static int houseRobber(int[] a) {
        int prevRob = 0;
        int prevSkip = 0;
        int rob = 0;
        int skip = 0;
        for (int x : a) {
            rob = prevSkip + x;
            skip = Math.max(prevRob, prevSkip);
            prevRob = rob;
            prevSkip = skip;
        }
        return Math.max(rob, skip);
    }

    /** DP解法，Memoization，BottomUp，Iterative：Time - o(n), Space - o(n) */
    // 使用一个单独的数组，数组的每个元素表示打劫至对应房间范围内能达到的最大值。
    // 但是如上面的分析可以知道，其实每个房间能达到的最大值只需要和上一个房间相关即可，无需前面所有房间的信息。因此这个解法的空间复杂度有冗余。
    static int houseRobber2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int[] state = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            if      (i == 0) state[i] = a[i];
            else if (i == 1) state[i] = Math.max(state[i - 1], a[i]);
            else             state[i] = Math.max(state[i - 2] + a[i], a[i - 1]);
        }
        return state[a.length - 1];
    }
}
