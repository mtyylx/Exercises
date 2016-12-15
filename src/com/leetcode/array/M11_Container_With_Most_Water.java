package com.leetcode.array;

/**
 * Created by Michael on 2016/12/14.
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * Note: You may not 倾斜 the container.
 *
 * Function Signature:
 * public int maxContainer(int[] a) {...}
 *
 */
public class M11_Container_With_Most_Water {
    public static void main(String[] args) {
        System.out.println(maxContainer(new int[] {4, 5, 2, 3}));
        System.out.println(maxContainer2(new int[] {4, 5, 2, 3}));
        System.out.println(maxContainer3(new int[] {4, 5, 2, 3}));
    }

    /** 解法2：双指针首尾相向扫描。优化解空间扫描，找到一种可靠的变化趋势。Time - o(n) */
    // 这个问题的难点在于如何让两个指针运动的同时找到一种可靠的变化趋势。
    // 双指针常见的几种运动方式有：
    // #1. 快慢指针：即先后平行向同一个方向运动
    // #2. 中心扩散：即从序列的中间某一点分别向左右运动（排序中常用到）
    // #3. 首尾包围：即从序列的首尾相向运动直至汇合。
    // 针对这个问题，make sense的是第三种。

    // 方案#1和#2都是扩张矩形，在这个过程中，不管选择移动哪一边，都无法确定面积会一定增大或一定减小，也就是说，“找不到一种可靠的变化趋势”。
    // 既然找不到一种趋势，我们也就没有移动A指针而不移动B指针的根据，那么在这种情况下，为了避免错过最优解，我们只能尝试所有可能，即退化成为穷举法。
    // 方案#3则是收缩矩形，在这个过程中，我们找到了一种可靠的趋势：
    // 指针A在左，指针B在右，如果指针A所在高度 > 指针B所在高度，那么当前矩形面积由指针B所决定，
    // 此时如果我们收缩指针A，则不管下一个高度是什么，矩形面积一定会减小。
    // Case #1 如果下一个高度增加，但矩形高度依然由指针B决定，因此高度不变，但因为宽度收缩，所以整体面积会减小。
    // Case #2 如果下一个高度减小至比指针B高度还小，矩形高度将会由这个更小的高度决定，再加上宽度收缩，因此整体面积同样会减小，而且减小的更多。
    // 因此我们就有理由不去移动指针A了。我们找到了一种可靠的变化趋势，可以用于精简解空间的搜索，也就不再是穷举法了。
    // A高于B，无论什么情况移动A至A'都会导致面积减小。
    //      Case #1          Case #2
    //
    //       *             *
    //    *  *             *        *
    //    *  *     *       *     *  *
    //    *  *  *  *       *  *  *  *
    //    *  *  *  *       *  *  *  *
    //    A  A'    B       A  A'    B
    // 接下来再讨论收缩指针B在什么情况下会有可能更新最大面积记录。
    // 由于矩形宽度是不断收缩的，因此如果收缩指针B的下一个位置比当前位置高度还低，那么整体面积一定会缩小，没有必要更新max
    // 只有下一位置比当前位置高度升高，才有比较的意义。因此加入early exit逻辑。
    static int maxContainer2(int[] a) {
        int left = 0;
        int right = a.length - 1;
        int max = Math.min(a[left], a[right]) * right;
        while (left < right) {
            if (a[left] > a[right]) {
                right--;
                if (a[right] < a[right + 1]) continue;      // 变矮就跳过更新max
            }
            else {
                left++;
                if (a[left] < a[left - 1]) continue;        // 变矮就跳过更新max
            }
            max = Math.max(max, Math.min(a[left], a[right]) * (right - left));  // 尝试更新max
        }
        return max;
    }

    // 另一种写法：记录高度最大值，只有遇到新高度才进入下一循环。
    static int maxContainer3(int[] a) {
        int left = 0;
        int right = a.length - 1;
        int max = 0;
        int lMax = a[left];
        int rMax = a[right];
        while (left < right) {
            max = Math.max(max, Math.min(a[left], a[right]) * (right - left));
            if (a[left] < a[right]) {
                while (left < right && a[left] < lMax) left++;
                lMax = a[left++];
            }
            else {
                while (left < right && a[right] < rMax) right--;
                rMax = a[right--];
            }
        }
        return max;
    }


    /** 解法1：最朴素解法。双指针遍历。Time - o(n^2) */
    static int maxContainer(int[] a) {
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                max = Math.max(max, (j - i) * Math.min(a[i], a[j]));
            }
        }
        return max;
    }
}
