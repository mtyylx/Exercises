package com.leetcode.math;

/**
 * Created by Michael on 2017/10/24.
 *
 * Find the total area covered by two rectilinear rectangles in a 2D plane.
 * Each rectangle is defined by its <bottom left> corner and <top right> corner as shown in the figure.
 * Assume that the total area is never beyond the maximum possible value of int.
 * 已知两个矩形左下角和右上角顶点坐标，求两个矩形的相交面积。
 *
 * <Tags>
 * - Math: 最大最小法 (Max of Min, Min of Max. 求线段重叠部分的二维版本而已)
 * - Bounding Box Calculation in Computer Vision.
 *
 */

public class M223_Rectangle_Area {
    public static void main(String[] args) {
        System.out.println(union_area(-2, -2, 2, 2, 3, 3, 4, 4));
        System.out.println(intersection_area(-2, -2, 2, 2, 3, 3, 4, 4));
        System.out.println(intersection_area2(-2, -2, 4, 4, 3, 3, 1, 1));
        System.out.println(intersection_area3(-2, 2, 2, -2, 3, 4, 4, 3));
        System.out.println(hasIntersections(0, 0, 2, 2, 1, 0, 3, 3));
    }


    // 难点：刚上手时会感觉参数列表中的每个值的相对位置都是不定的，因此完全无法思考。
    /** 思想1：把不定的简化为固定的。 */
    // 虽然这个问题可以有很多变体，但是万变不离其宗。
    // - 这里已经明确给定的矩形顶点就是左下和右上，等效于直接告诉你每个矩形的 xmin, ymin, xmax, ymax 都是什么，直接用就行
    // - 假如不告诉你矩形的这两个顶点是“左下右上”还是“左上右下”，那么只需要额外判断每个矩形的 xmin, ymin, xmax, ymax
    // - 假如告诉你的不是两个顶点坐标，而是一个顶点（例如左下顶点）的坐标以及矩形的长和宽，那么依然可以轻松得到 xmin, ymin, xmax, ymax

    /** 思想2：降维。*/
    // 在处理高维空间的坐标时，不同维度的值总会相互干扰，使你的思考困难重重，这时候就可以考虑是否能够一次只考虑一个维度了。
    // 在二维空间内不能直观思考的问题，可以分别压缩为两个一维空间的问题解决。
    //        ┌──────────────┐ (C, D)
    //        │	   Rec 1     │
    //        │         ┌────┼──────┐ (G, H)
    // (A, B) └─────────┼────┘	    │
    //                  │   Rec 2   │
    //           (E, F) └───────────┘
    //
    // X轴
    //       A ─────────┼─────┤ C              矩形1
    //                E ├─────┼─────── G       矩形2
    //                                                    相交最右点一定是两个线段最大值中小的那个
    //                                                    相交最左点一定是两个线段最小值中大的那个
    // Y轴
    //            B ├───┼──────── D            矩形1
    //       F ─────┼───┤ H                    矩形2

    /** 容易疏忽的点：没有先判断是否相交，再计算面积 */
    // 导致用最小最大法算出来的负长度可以想乘得到正面积。e.g. (-2, -2) (2, 2) vs (3, 3) (4, 4)

    /** 交集面积：最大最小法。*/
    static int intersection_area(int A, int B, int C, int D, int E, int F, int G, int H) {
        // 首先判断是否相交，不相交则直接返回0
        if (A >= G || C <= E || B >= H || D <= F) return 0;
        // 在确保相交的前提下，进一步用最小最大法求相交面积
        int dx = Math.min(C, G) - Math.max(A, E);
        int dy = Math.min(D, H) - Math.max(B, F);
        return dx * dy;
    }

    /** 并集面积：两个矩形面积相加，再减去交集面积。*/
    static int union_area(int A, int B, int C, int D, int E, int F, int G, int H) {
        int area1 = (C - A) * (D - B);
        int area2 = (G - E) * (H - F);
        int intersection = 0;
        if (!(A >= G || C <= E || B >= H || D <= F)) {
            int dx = Math.min(C, G) - Math.max(A, E);
            int dy = Math.min(D, H) - Math.max(B, F);
            intersection = dx * dy;
        }
        return area1 + area2 - intersection;
    }

    /** 扩展题1：给定两个矩形的左下右上的两个对角线顶点，判断是否存在相交区域。 */
    // 注意这里的表达式只能判断不相交，如果想要直接表达相交，则需要两个条件的交集才可以，没法像判断不相交那样写法简洁。
    //  A >= G     E ───── G     A ───── C
    //  C <= E     A ───── C     E ───── G
    //  B >= H     F ───── H     B ───── D
    //  D <= F     B ───── D     F ───── H
    static boolean hasIntersections(int A, int B, int C, int D, int E, int F, int G, int H) {
        return !(A >= G || C <= E || B >= H || D <= F);
    }

    /** 扩展题2：给定两个矩形的左下角顶点坐标，以及长和宽，判断是否存在相交区域。 */
    // 由于左下角坐标一定是矩形的最小值，因此只需要分别在两个维度上加上 w 和 h 就可以得到最大值了，之后就都一样了。
    static int intersection_area2(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        if (x1 >= x2 + w2 || x1 + w1 <= x2 || y1 >= y2 + h2 || y1 + h1 <= y2) return 0;
        int dx = Math.min(x1 + w1, x2 + w2) - Math.max(x1, x2);
        int dy = Math.min(y1 + h1, y2 + h2) - Math.max(y1, y2);
        return dx * dy;
    }

    /** 扩展题3：给定两个矩形的对角线顶点，但不保证给的两个点是左下右上还是左上右下，判断是否存在相交区域。 */
    // 还是同样的思路，确定到底 xmin, xmax, ymin, ymax 都是谁，后面就都一样了。
    static int intersection_area3(int A, int B, int C, int D, int E, int F, int G, int H) {
        int xmin1 = Math.min(A, C);
        int xmax1 = Math.max(A, C);
        int ymin1 = Math.min(B, D);
        int ymax1 = Math.max(B, D);

        int xmin2 = Math.min(E, G);
        int xmax2 = Math.max(E, G);
        int ymin2 = Math.min(F, H);
        int ymax2 = Math.max(F, H);

        if (xmin1 >= xmax2 || xmax1 <= xmin2 || ymin1 >= ymax2 || ymax1 <= ymin2) return 0;
        int dx = Math.min(xmax1, xmax2) - Math.max(xmin1, xmin2);
        int dy = Math.min(ymax1, ymax2) - Math.max(ymin1, ymin2);
        return dx * dy;
    }
}
