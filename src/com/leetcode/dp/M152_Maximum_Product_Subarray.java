package com.leetcode.dp;

/**
 * Created by Michael on 2016/11/1.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.
 *
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6.
 *
 * Function Signature:
 * public int maxProduct(int[] a) {...}
 */
public class M152_Maximum_Product_Subarray {
    public static void main(String[] args) {
        System.out.println(maxProduct(new int[] {2, -3, -4, -5}));
    }

    /** DP解法，Memoization，Iterative，通过记录最大最小值来做状态转移，Time - o(n)，Space - o(1) */
    // 如果a < b，那么一定有a + x < b + x
    // 如果a < b，那么并没有a * x < b * x，反例：-2 * -5 > 2 * -5
    // 乘法和加法最大的不同就在于屌丝(即上个局部最小值)也可以瞬间逆袭(即成为新的局部最大/最小值)。
    // 所以相对于Max Sum Subarray来说，Max Product Subarray的状态转移会更具有跳跃性。
    // 为了避免上一轮的最小负值在这一轮摇身一变成为最大正值但却没有被记录下来的问题，就需要我们不仅跟踪最大值，也跟踪最小值。
    // 于是问题转换成为：已知一个数组的子数组最大积和最小积，如果再向这个数组添加一个元素，那么求新数组的最大积和最小积。
    // 局部最大值只有三个可能：
    // 1. 原数组的最大积prevMaxLocal乘新元素
    // 2. 原数组的最小积prevMinLocal乘新元素（屌丝逆袭）
    // 3. 新元素本身（可以作为新的Subarray起点）
    // 为了使状态转移能够继续下去，必须同时记录当前轮的局部最小值minLocal，同样是上面的三种可能。
    // 注意这里的maxLocal和minLocal都是特指以当前元素作为结尾的最大和最小Subarray的积。
    static int maxProduct(int[] a) {
        if (a == null || a.length == 0) return 0;
        int prevMaxLocal = a[0];
        int prevMinLocal = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            int maxLocal = Math.max(Math.max(prevMaxLocal * a[i], prevMinLocal * a[i]), a[i]);  // 状态转移
            int minLocal = Math.min(Math.min(prevMaxLocal * a[i], prevMinLocal * a[i]), a[i]);  // 状态转移
            max = Math.max(max, maxLocal);
            prevMaxLocal = maxLocal;        // 需要中转变量，因为求maxLocal和minLocal时会修改自己。
            prevMinLocal = minLocal;
        }
        return max;
    }

    // 通过提前判断当前轮的a[i]值是正还是负来直接确定maxLocal和minLocal. 等效于上面的解法。只是用交换元素避免了双重判定极值的过程。
    static int maxProduct2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int x = a[0];
        int min = x;
        int max = x;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < 0) {
                int temp = min;
                min = max;
                max = temp;
            }
            min = Math.min(a[i], a[i] * min);
            max = Math.max(a[i], a[i] * max);
            x = Math.max(max, x);
        }
        return x;
    }
}
