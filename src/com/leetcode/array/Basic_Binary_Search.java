package com.leetcode.array;

/**
 * Created by Michael on 2016/8/26.
 *
 * Basic Algorithm - Binary Search:
 * For a given sorted array, return the index of the target value.
 *
 * Function Signature:
 * public int binarySearch(int[] a, int target) {...}
 *
 */
public class Basic_Binary_Search {
    public static void main(String[] args) {
        int[] a = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        System.out.println(binarySearchIterative(a, 19));
        System.out.println(binarySearchRecursive(a, 2));
    }

    /**
     * <递归解法>
     * 把while循环改写为if判断
     * 因为不像迭代循环体每个循环都能自动隐式知道首尾索引值，
     * 所以递归解法需要在反复调用自己的时候同时传递进首尾索引值，这就需要修改函数接口，
     * 因此只能单独定义一个递归函数，再在外面包一层，提供初始条件[0, a.length - 1].
     */
    static int binarySearchRecursive(int[] a, int target) {
        return binarySearch(a, target, 0, a.length - 1);
    }

    static int binarySearch(int[] a, int target, int start, int stop) {
        if (start <= stop) {
            int middle = (start + stop) / 2;
            if      (target < a[middle]) return binarySearch(a, target, start, middle - 1);
            else if (target > a[middle]) return binarySearch(a, target, middle + 1, stop);
            else return middle;
        }
        else return -1;
    }

    /**
     * <迭代解法>
     * 双指针从首尾开始，首先将target与中间元素比较，
     * 根据相对大小关系，将双指针的其中一个更新为中间元素的左相邻或右相邻元素，
     * 循环直至两个指针重叠。
     *
     * 该算法设计的一个小的困难在于i和j的更新需要middle+/-1，而不仅仅是更新为middle
     * 因为当i和j越来越接近的直至相邻的时候，如果不加减1就会陷入死循环，永远等不到i=j的时候
     *
     *  易疏忽点1：求中点的加法有可能会整型溢出，所以最好使用先减后加的方式。
     *  易疏忽点2：while循环条件应包含左右指针重合的情况，只有左右指针位置交错才退出。
     *  易疏忽点3：即使没有找到，target的理想位置也就刚好等于退出while循环后的left指针位置。
     */
    // Example: 证明left刚好处于待插入位置。
    // target = -1. [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4]     应插入至left所在位置
    //               ↑     ↑     ↑    ↑  ↑             ↑              ↑ ↑
    //               l     m     r   l,m r           l,m,r            r l
    //
    // target = 5.  [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4]     应插入至left所在位置
    //               ↑     ↑     ↑             ↑  ↑                ↑                ↑ ↑
    //               l     m     r            l,m r              l,m,r    move l    r l
    //
    // target = 7.  [0, 2, 4, 6, 8], [0, 2, 4, 6, 8], [0, 2, 4, 6, 8], [0, 2, 4, 6, 8]     应插入至left所在位置
    //               ↑     ↑     ↑             ↑  ↑                ↑              ↑ ↑
    //               l     m     r            l,m r              l,m,r   move r   r l
    static int binarySearchIterative(int[] a, int target) {
        if (a == null) return -1;
        int i = 0;
        int j = a.length - 1;
        int middle;
        while (i <= j) {
            middle = i + (j - i) / 2;
            if      (target > a[middle]) i = middle + 1;
            else if (target < a[middle]) j = middle - 1;
            else return middle;
        }
        return i;
    }

    // 下面的解法只是改变了while循环的条件（i<=j 变成 i<j）以及区段划分j = middle，
    // 但是不如上面解法的地方在于，如果没有查找到的话它不能正确的返回插入位置。所以不建议。
    static int binarySearchIterative2(int[] a, int target) {
        if (a == null) return -1;
        int i = 0;
        int j = a.length - 1;
        int middle;
        while (i < j) {
            middle = i + (j - i) / 2;
            if      (target > a[middle]) i = middle + 1;
            else if (target < a[middle]) j = middle;
            else return middle;
        }
        return i;
    }
}
