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
 * <Tags>
 * - Binary Search
 * - Two Pointers: [left → → → ... ← ← ← right]
 *
 */
public class Basic_Binary_Search {
    public static void main(String[] args) {
        int[] a = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        System.out.println(binarySearchIterative(a, 19));
        System.out.println(binarySearchRecursive(a, 2));

        int[] b = {3, 4, 4, 5, 6, 7, 7, 7, 8, 9};
        System.out.println("Testing InsertPos: ");
        System.out.println(getFirstInsertPos(b, 4));
        System.out.println(getLastInsertPos(b, 4));
        System.out.println(getFirstInsertPos(b, 3));
        System.out.println(getLastInsertPos(b, 3));
        System.out.println(getFirstInsertPos(b, 1));
        System.out.println(getLastInsertPos(b, 1));
        System.out.println(getFirstInsertPos(b, 12));
        System.out.println(getLastInsertPos(b, 12));

        System.out.println(binarySearchIterative2(new int[] {1, 2, 4, 8}, 0));
    }


    /** 问题发散：寻找target在数组的首次出现位置。如果target不在数组中，则给出最佳插入位置。 */
    // 插入位置范围：0 to len （默认插入后右侧元素会全部右移并拓展）
    static int getFirstInsertPos(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (a[mid] >= target) j = mid - 1;      // 尽可能左移右指针，以获得最左侧target
            else i = mid + 1;
        }
        return i;   // 返回左指针
    }

    /** 问题发散：寻找target在数组的最后一次出现位置。如果target不在数组中，则给出最佳插入位置。 */
    // 插入位置范围：-1 to len - 1 （默认插入后左侧元素会全部左移并拓展）
    static int getLastInsertPos(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (a[mid] <= target) i = mid + 1;      // 尽可能右移左指针，以获得最右侧target
            else j = mid - 1;
        }
        return j;   // 返回右指针
    }


    // Example: 证明left刚好处于待插入位置。
    // 以下四种情况涵盖了left/right指针的所有可能情况，可以看到left最终的位置都能确保是正确的插入位置。
    // target = 0 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [0, 1, 4, 7]
    //             ↑   ↑   ↑      ↑            ↑ ↑
    //             l   m   r    l,m,r          r l
    //
    // target = 8 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [1, 4, 7, 8]
    //             ↑   ↑   ↑              ↑              ↑ ↑
    //             l   m   r            l,m,r            r l
    //
    // target = 3 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [1, 3, 4, 7]
    //             ↑   ↑   ↑      ↑              ↑   ↑
    //             l   m   r    l,m,r            r   l
    //
    // target = 6 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [1, 4, 6, 7]
    //             ↑   ↑   ↑              ↑          ↑   ↑
    //             l   m   r            l,m,r        r   l

    /**
     * <递归解法>
     * 要点1：递归终止情况分两种：
     *       #正常终止：target在数组中找到，即 target == a[mid]
     *       #异常终止：target并不在数组中，即 left > right。此时返回的left含义是：保证数组有序的条件下，插入target的最佳位置。
     * 要点2：选边时，要不然是(mid - 1)，要不然就是(mid + 1)。
     * 要点3：求分界点时，应避免整形溢出。e.g. "left + (right - left) / 2" instead of "(left + right) / 2"
     */
    static int binarySearchRecursive(int[] a, int target) {
        return binarySearch(a, target, 0, a.length - 1);
    }

    static int binarySearch(int[] a, int target, int left, int right) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            if      (target < a[mid]) return binarySearch(a, target, left, mid - 1);
            else if (target > a[mid]) return binarySearch(a, target, mid + 1, right);
            else return mid;
        }
        return left;
    }

    /**
     * <迭代解法>
     * 双指针从首尾开始，首先将target与中间元素比较，
     * 根据相对大小关系，将双指针的其中一个更新为中间元素的左相邻或右相邻元素，
     * 循环直至两个指针<交错>。
     *
     * 该算法设计的一个小的困难在于i和j的更新需要middle+/-1，而不仅仅是更新为middle
     * 因为当i和j越来越接近的直至相邻的时候，如果不加减1就会陷入死循环，永远等不到i=j的时候
     *
     * 易疏忽点1：求中点的加法有可能会整型溢出，所以最好使用先减后加的方式。
     * 易疏忽点2：while循环条件应包含左右指针重合的情况，只有左右指针位置交错才退出。
     * 易疏忽点3：即使没有找到，target的理想位置也就刚好等于退出while循环后的left指针位置。
     */

    static int binarySearchIterative(int[] a, int target) {
        if (a == null) return -1;
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if      (target > a[mid]) left = mid + 1;
            else if (target < a[mid]) right = mid - 1;
            else return mid;
        }
        return left;
    }

    /** 另一种写法，循环终止条件为左右指针重合（而不是上面解法的指针交错），另外右指针的移动是mid而不是mid-1. */
    // 两种解法在target存在于数组中时没有差别，但是这种解法在target大于数组所有元素时的最佳插入位置不对。
    // [1 2 4 8] target = 10, 该解法返回的插入位置是3，但实际应该是4，即插入在元素8之后。
    // 所以此解法不适用于M35，不过却对M153有奇效。
    static int binarySearchIterative2(int[] a, int target) {
        if (a == null) return -1;
        int i = 0;
        int j = a.length - 1;
        int mid;
        while (i < j) {
            mid = i + (j - i) / 2;
            if      (target > a[mid]) i = mid + 1;
            else if (target < a[mid]) j = mid;
            else return mid;
        }
        return i;
    }
}
