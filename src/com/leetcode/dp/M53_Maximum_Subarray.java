package com.leetcode.dp;

/**
 * Created by Michael on 2016/10/29.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
 *
 * For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
 * the contiguous subarray [4,-1,2,1] has the largest sum = 6.
 * click to show more practice.
 * If you have figured out the O(n) solution,
 * try coding another solution using the divide and conquer approach, which is more subtle.
 *
 * Function Signature:
 * public int maxSubarray(int[] a) {...}
 */
public class M53_Maximum_Subarray {
    public static void main(String[] args) {
        System.out.println(maxSubarray(new int[] {-32,-54,-36,62,20,76}));
        System.out.println(maxSubarray2(new int[] {-32,-54,-36,62,20,76}));
        System.out.println(maxSubarray3(new int[] {-32,-54,-36,62,20,76}));
    }

    /** DP解法, Iterative，Time - o(n), Space - o(1) */
    // 相比于穷举遍历法，到底DP解法节省了哪些冗余的运算？
    // 核心思路在于将问题以递推角度考虑，问题转换成为：
    /** 假设对于长度为n的数组，我们已经知道了它的最大子数组之和，此时如果我们在数组尾部添加一个元素，那么新数组的最大子数组该如何计算。*/
    // 上面这个问题本身描述的就是状态转移的过程：从数组n到数组n+1的最大子数组解。
    // 一开始思路卡住的原因是试图用HouseRobber的思路把每一阶段都分成两种状态来分别计算，状态转移时则会取用上一阶段的两种状态组合。
    // 但是实际上，这里只关心以当前元素所谓结尾的最大子数组maxInclude以及整体的最大子数组max这两个东西，并不关心maxNotInclude。
    // 所以试图把max分解为maxInclude和maxNotInclude就会误入歧途。
    // 由于每向数组添加一个元素，最大子数组的变化就会有下面几种可能：
    // Case 1：新添加的元素单独成为了最大子数组。maxInclude = a[i], max = maxInclude
    // Case 2：原来的最大子数组添加上当前元素，成为新的最大子数组。maxInclude = maxInclude(old) + a[i], max = maxInclude
    // Case 3：最大子数组未更新。max = max(old)
    // 将上面三种情况综合处理，就变成了：
    // 首先确定maxInclude(new)会是a[i],还是maxInclude(old) + a[i]
    // 再确定全局最大max是max(old),还是maxInclude(new)
    // 迭代即得。
    static int maxSubarray2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int maxInclude = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            maxInclude = Math.max(maxInclude + a[i], a[i]);     // 而不是Math.max(maxInclude, maxInclude + a[i])，因为这样得出来的值不一定Include a[i].
            max = Math.max(max, maxInclude);
        }
        return max;
    }

    /** 分治法，Recursive，类似Binary Search，逆序递归，先分解问题再处理问题，Time - o(nlogn)，Space - o(n) */
    static int maxSubarray3(int[] a) {
        return divideAndConquer(a, 0, a.length - 1);
    }

    // Divide：首先分解问题规模，递归处理左右子块，直至子块长度为1，返回子块的值本身（作为找到的最大子数组值返回）
    // Conquer：然后将当前左右分区合并处理，处理方法是从分界点分别向首尾扫描，记录子数组不断扩张过程中达到的最大值（因为有可能先变大后变小）
    // 最后判断，是左分区返回的结果最大(leftOnlyMax)，还是右分区返回的结果最大(rightOnlyMax)，还是穿过左右分区的区域的最大值(crossMax)最大。
    // 这时候返回的结果又会在递归上升之后被上层递归视作左右分区之一的最大值结果。循环往复。
    // ---Left Part--- | ---Right Part---
    // ****@@@@******* | **@@@@@*********
    // **********@@@@@@@@@@@@************
    // 示意图：@代表属于最大子数组范围的元素，*表示不属于的元素，可以看到每个递归最后要解决的问题就是上面三段中的@@@@值谁最大。
    static int divideAndConquer(int[] a, int left, int right) {
        if (left == right) return a[left];
        int mid = left + (right - left) / 2;
        int leftOnlyMax = divideAndConquer(a, left, mid);
        int rightOnlyMax = divideAndConquer(a, mid + 1, right);
        int leftPartialMax = a[mid];
        int rightPartialMax = a[mid + 1];
        int sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += a[i];
            if (sum > leftPartialMax) leftPartialMax = sum;
        }
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += a[i];
            if (sum > rightPartialMax) rightPartialMax = sum;
        }
        int crossMax = leftPartialMax + rightPartialMax;
        // Determine which is the max: leftOnly, rightOnly, crossMax
        return Math.max(Math.max(leftOnlyMax, rightOnlyMax), crossMax);
    }

    /** Naive穷举遍历法, Time - o(n^2), Space - o(1) */
    static int maxSubarray(int[] a) {
        if (a == null || a.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            max = Math.max(max, a[i]);
            int sum = a[i];
            for (int j = i + 1; j < a.length; j++) {
                sum += a[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
