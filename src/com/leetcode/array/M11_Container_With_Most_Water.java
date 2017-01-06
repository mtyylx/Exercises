package com.leetcode.array;

/**
 * Created by Michael on 2016/12/14.
 *
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 *
 * Note: You may not 倾斜 the container.
 *
 * Function Signature:
 * public int maxContainer(int[] a) {...}
 *
 * <Tags>
 * - Two Pointers: 左右指针首尾包围。[left → → → ... ← ← ← right]
 * - Early Exit
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
    // 方案#3则是收缩矩形，在这个过程中，我们找到了一种可靠的趋势！

    // 由于木桶效应，任意时刻的矩形面积只由短边决定。即如果左指针a[i] > 右指针a[j]，那么决定矩形面积的是右指针。
    // 此时如果我们收缩左指针，则不管下一个高度是什么，矩形面积一定会减小，原因如下：
    // Case #1 - a[i'] 比 a[i] 更高：没用，因为矩形高度依然由左指针决定，因此高度不变，但因为宽度收缩，所以整体面积会减小。
    // Case #2 - a[i'] 比 a[i] 更矮：如果a[i']依然大于a[j]，那么等同于Case#1，
    // 如果a[i']小于a[j]，那么矩形高度将会由这个更小的高度决定，再加上宽度收缩，因此整体面积同样会减小，而且减小的更多。
    // 综上所述，移动两个指针中高的那个不会让面积增大，但移动短的那个却不一定不增大。
    // 因此我们就有理由不去移动高指针了。我们找到了一种可靠的变化趋势，可以用于精简解空间的搜索，也就不再是穷举法了。
    //
    //      Case #1          Case #2
    //
    //       *             *
    //    *  *             *        *
    //    *  *     *       *     *  *
    //    *  *  *  *       *  *  *  *
    //    *  *  *  *       *  *  *  *
    //    i  i'    j       i  i'    j
    // 接下来再讨论收缩指针B在什么情况下会有可能更新最大面积记录。
    // 由于矩形宽度是不断收缩的，因此如果收缩指针B的下一个位置比当前位置高度还低，那么整体面积一定会缩小，没有必要更新max
    // 只有下一位置比当前位置高度升高，才有比较的意义。因此加入early exit逻辑。
    static int maxContainer2(int[] a) {
        int i = 0;
        int j = a.length - 1;
        int max = Math.min(a[i], a[j]) * j;
        while (i < j) {
            if (a[i] > a[j]) {
                j--;
                if (a[j] < a[j + 1]) continue;          // 变矮就跳过更新max
            }
            else {
                i++;
                if (a[i] < a[i - 1]) continue;          // 变矮就跳过更新max
            }
            max = Math.max(max, Math.min(a[i], a[j]) * (j - i));  // 尝试更新max
        }
        return max;
    }

    // 简化写法，在内循环里寻找比现在高度大的停下来。
    static int maxContainer3(int[] a) {
        int left = 0;
        int right = a.length - 1;
        int max = 0;
        while (left < right) {
            int min = Math.min(a[left], a[right]);
            max = Math.max(max, min * (right - left));
            while (a[left] <= min && left < right) left++;
            while (a[right] <= min && left < right) right--;
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
