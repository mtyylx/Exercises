package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2017/1/4.
 *
 * Given a sorted array of integers, find the starting and ending position of a given target value.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * If the target is not found in the array, return [-1, -1].
 *
 * For example, Given [5, 7, 7, 8, 8, 10] and target value 8, return [3, 4].
 *
 * Function Signature:
 * public int[] searchRange(int[] a, int target) {...}
 *
 * <系列问题>
 * M35  Search Insert Position: 给定一个已排序数组a和一个目标值k，求k在a中出现的位置值。如果k不在a中，求将k插入a中后让a依然保持有序的位置值。
 * M34  Search In Range:        给定一个已排序数组a和一个目标值，求k在a中出现的起始和终止位置值。如果k不在a中，则返回[-1, -1]
 * E278 First Bad Version:      给定一个取值范围1至n（即有序）和一个判定函数，求让判定函数第一次返回true的位置索引。
 * E374 Guess Number:           给定一个取值范围1至n（即有序）和一个判定函数，求让判定函数返回0的位置索引。
 *
 * <Tags>
 * - Binary Search: Divide and Conquer
 * - Two Pointers: [left → → → ... ← ← ← right]
 * - Sorted Array: X + 1元素的最佳插入位置就是X元素的最后一个出现位置加一。
 *
 */
public class M34_Search_For_Range {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 6, 6, 8, 9};
        System.out.println(Arrays.toString(searchRange(a, 6)));
        System.out.println(Arrays.toString(searchRange2(a, 6)));
        System.out.println(Arrays.toString(searchRange3(a, 6)));
    }

    /** 解法3：对 a[mid] == target 的情况进行递归，同时完成起始和终止位置检测。递归写法。 */
    // 与解法1和2的不同点就在于处理 a[mid] == target 这上面。
    // 因为只要我们找到了 a[mid] == target，就说明有必要继续寻找在mid左侧和右侧分别寻找起始和终止位置，
    // 如果target有多个，那么就可以拓展左右指针的位置。如果target只有一个，那么返回的起始和终止坐标都是mid。
    // 分别寻找的过程就是递归，如果新的（左/右）区间内再也找不到target，就说明已经找到头了，相应的左/右极限位置不会再拓展。
    // 示例：
    // [1, 2, 3, 3, 4, 6,    6,    6, 8]
    //                       ↑
    //                      mid
    // [1, 2, 3, 3, 4, 6, 6] | [6, 6, 8]
    //        求左极限位置       求右极限位置
    // 不断递归直至区域找不到目标值。
    static int[] searchRange3(int[] a, int target) {
        return helper(a, 0, a.length - 1, target, new int[] {-1, -1});
    }

    static int[] helper(int[] a, int i, int j, int target, int[] res){
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if      (a[mid] > target) j = mid - 1;
            else if (a[mid] < target) i = mid + 1;
            else {
                res[0] = (res[0] == -1) ? mid : Math.min(mid, res[0]);  // 更小时才更新结果
                res[1] = (res[1] == -1) ? mid : Math.max(mid, res[1]);  // 更大时才更新结果
                res = helper(a, i, mid - 1, target, res);     // 对左侧区域继续搜索左极限位置
                res = helper(a, mid + 1, j, target, res);     // 对右侧区域继续搜索右极限位置
                return res;
            }
        }
        return res;
    }


    /** 解法2：两次 Binary Search，解法1的简化升级版，基于插入位置，只需要写一个Binary Search方法，然后调用两次即可。 */
    // 解法1完全专注于寻找target这个值本身的起点和终点，但是真的必须如此么？
    // 解法2改变了以target为中心的思路，将问题转变为求解target的起点、以及(target+1)的起点。因为(target+1)的起点不就是target的终点加一么？！
    // 虽然(target+1)并不一定恰好出现在数组中，但对于已排序数组，其实不管(target+1)出不出现，我们都可以求它的最佳插入位置，即getInsertPos方法。
    // 另外，为了进一步简化运算量，我们在搜索(target+1)的插入位置时，其实无需完整扫描数组，而是只需要从target的发现位置开始。
    static int[] searchRange2(int[] a, int target) {
        int x = getInsertPos(a, target, 0);
        if (x >= a.length || a[x] != target) return new int[] {-1, -1};     // Early exit
        int y = getInsertPos(a, target + 1, x);                       // 从x所在位置开始接着扫描，避免因为x是最后一个元素而越界
        return new int[] {x, y - 1};
    }

    static int getInsertPos(int[] a, int target, int start) {
        int i = start;
        int j = a.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (a[mid] >= target) j = mid - 1;      // 尽可能靠左插入
            else i = mid + 1;
        }
        return i;
    }


    /** 解法1：双 Binary Search，将 a[mid] == target的情况分别归至左右，以检测起始和终止位置。Time - o(logn). */
    // 总结下来，有两点要求：一个是用两次二分查找确定起始和终止点，一个是如果没有要返回-1,-1
    // 主要的难点在于左右两个指针的索引选用上。这里需要对标准的Binary Search的代码进行略微调整。
    /** [确定起始点]: 和标准Binary Search基本一样，但取消了a[mid] == target的情况，右指针移动的概率更大。
     * 只要 a[mid] <  target，就右移左指针
     * 只要 a[mid] >= target，就左移右指针
     * 这个逻辑导致循环<更倾向于将右指针左移>，循环终止时右指针一定在第一个target值的左边，而且左指针则刚好指向第一个target值。*/
    // 示例：
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //  ↑        ↑           ↑
    //  i       mid          j    mid < target, thus i = mid + 1
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //              ↑  ↑     ↑
    //              i mid    j    mid >=target, thus j = mid - 1
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //              ↑
    //           i,mid,j          mid >=target, thus j = mid - 1
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //           ↑  ↑
    //           j  i             循环终止，i刚好就是target的起始位置
    // 对于边界情况，如果target不在数组内，那么i表示的位置就是它的有序插入位置，取值范围为[0, len]，
    //    [1, 2, 3, 4, 6, 6, 8, 9]
    //     ↑                      ↑        插入位置范围[0, len]，默认插入后右侧元素右移。
    //  target < 1             target > 9
    // 因此只需要检查返回的索引值是否在数组范围内（右边界），且元素是否是target就能判断是否存在了。
    /** [确定终止点]: 与确定起始点的区别在于不等号，左指针移动的概率更大。
     * 只要 a[mid] <= target，就右移左指针
     * 只要 a[mid] >  target，就左移右指针
     * 这个逻辑导致循环<更倾向于将左指针右移>，循环终止时左指针一定在最后一个target值的右边，而且右指针则刚好指向最后一个target值。*/
    // 示例：
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //  ↑        ↑           ↑
    //  i       mid          j    mid <= target, thus i = mid + 1
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //              ↑  ↑     ↑
    //              i mid    j    mid <= target, thus i = mid + 1
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //                    ↑  ↑
    //                 i,mid j    mid > target, thus j = mid - 1
    // [1, 2, 3, 4, 6, 6, 8, 9]
    //                 ↑  ↑
    //                 j  i       循环终止，j刚好就是target的起始位置
    // 对于边界情况，如果target不在数组内，那么j表示的位置就是它的有序插入位置，取值范围为[-1, len - 1]，如下所示：
    //    [1, 2, 3, 4, 6, 6, 8, 9]
    //   ↑                      ↑          插入位置范围[-1, len - 1]，默认插入后左侧元素左移。
    // target < 1             target > 9
    // 因此同样只需要检查返回的索引值是否在数组范围内（左边界），且元素是否是target就能判断是否存在了。
    static int[] searchRange(int[] a, int target) {
        return new int[] {searchStart(a, target), searchEnd(a, target)};
    }

    static int searchStart(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (a[mid] < target) i = mid + 1;
            else j = mid - 1;                               // 倾向于将j左移
        }
        return (i < a.length && a[i] == target) ? i : -1;   // 最后i是插入位置，需要检查是否存在
    }

    static int searchEnd(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (a[mid] <= target) i = mid + 1;              // 倾向于将i右移
            else j = mid - 1;
        }
        return (j >= 0 && a[j] == target) ? j : -1;         // 最后j是插入位置，需要检查是否存在
    }
}
