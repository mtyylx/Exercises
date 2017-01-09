package com.leetcode.array;

import java.util.*;

/**
 * Created by Michael on 2016/12/31.
 * Given an array of integers, find out whether there are two distinct indices i and j in the array
 * such that the difference between nums[i] and nums[j] is at most t
 * and the difference between i and j is at most k.
 *
 * Function Signature:
 * public boolean containsDuplicate(int[] a, int k , int t) {...}
 *
 * <系列问题>
 * E217 Contains Duplicate 1: 给定一个数组，如果相等元素存在就返回true，                           ，否则返回false。
 * E219 Contains Duplicate 2: 给定一个数组，如果相等元素的索引值之差等于k就返回true                  ，否则返回false。
 * M220 Contains Duplicate 3: 给定一个数组，如果元素的索引值之差小于等于k且元素值之差小于等于t就返回true，否则返回false。
 *
 * <Tags>
 * - Bucket Sort
 * - TreeSet: 平衡二叉搜索树, 能以o(logN)执行绝大多数操作。ceiling() / floor()
 * - Sliding Window
 * 注：尚未完全消化。解法过于牛逼。
 *
 */

public class M220_Contains_Duplicate_3 {
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[] {1, 4, 2, 8, 5, 7}, 2, 1));
        System.out.println(containsDuplicate2(new int[] {1, 4, 2, 8, 5, 7}, 2, 1));
        System.out.println(containsDuplicate3(new int[] {1, 4, 2, 8, 5, 7}, 2, 1));
    }

    /** 解法3：Bucket Sort，将元素放置于宽度为valDiff的桶中。Time - o(n), Space - o(min(n, l)) */
    // 使用HashMap构造一堆桶，使用桶的编号作为key，指向桶的容器对象。
    // 按理说一般的BucketSort都需要Map<Integer, List<Integer>>，后者应该是一个装数的容器
    // 但是由于这里我们的目的不是全排序，而是尽快的找到符合条件的“几乎重复数”，因此不会在一个容器中出现多于一个数的情况，出现了我们也就可以退出了
    // 这里大规模使用long的原因也是因为运算可能会整型溢出。
    static boolean containsDuplicate3(int[] a, int idxDiff, int valDiff) {
        if (valDiff < 0) return false;              // 距离应该恒正
        Map<Long, Long> map = new HashMap<>();      // 构造一堆桶，每个桶具有一个编号
        long width = (long) valDiff + 1;
        for (int i = 0; i < a.length; i++) {
            long id = getBucket(a[i], width);       // 计算当前元素所属的桶编号
            if (map.containsKey(id)) return true;                                                       // 同一个桶出现了第二个元素
            if (map.containsKey(id + 1) && Math.abs(map.get(id + 1) - a[i]) < width) return true;   // 右侧相邻桶的元素值符合要求
            if (map.containsKey(id - 1) && Math.abs(map.get(id - 1) - a[i]) < width) return true;   // 左侧相邻桶的元素值符合要求
            map.put(id, (long) a[i]);
            if (i >= idxDiff) map.remove(getBucket((long) a[i - idxDiff], width));      // 滑动窗随时删除元素
        }
        return false;
    }

    // 对正数和负数元素所属的桶的取值宽度进行偏置
    // 如果是正数，那么取值范围就是: [0, width] [width + 1, 2 * width] [2 * width + 1, 3 * width】
    // 如果是负数，那么取值范围需要偏置一位，从-1开始: [-width - 1, -1] [-2 * width - 1, - width]...
    static long getBucket(long x, long width) {
        if (x >= 0) return x / width;
        else return (x + 1) / width - 1;
    }


    /** 解法2：TreeSet构建平衡二叉搜索树，所有操作都是o(logn). Time - o(n * log(min(n, k))), Space - o(min(n, k)). */
    // TreeSet的特性在于可以提供o(logN)的插入、搜索和删除操作。
    // 如果试图只用一般的数组拷贝一份已排序然后不断反查的话，由于每个操作都是o(n)，因此其实速度和解法1是一样的。
    // [1, 4, 2, 8, 5, 7] 存入TreeSet之后会变成 [1, 2, 4, 5, 7, 8]（这一点上和Arrays.sort一样，但之后操作快于一般数组）
    // set.ceiling(x)能以o(logN)查找set中从右侧最接近x的值：set.ceiling(6) = 7, set.ceiling(9) = null
    // set.floor(x)能以o(logN)查找set中从左侧最接近x的值：set.floor(3) = 2, set.floor(0) = null
    // 使用long来装数据的原因在于加减运算可能会溢出，
    // 使用Long盛放ceiling和floor运算的结果因为有可能会返回null，而null只能赋给引用类型。
    static boolean containsDuplicate2(int[] a, int idxDiff, int valDiff) {
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < a.length; i++) {
            Long next = set.ceiling((long) a[i]);                       // 查询大于等于a[i]的最小元素（即从右侧最接近a[i]的元素）
            if (next != null && next - a[i] <= valDiff) return true;    // 如果能查到，且next与a[i]的距离小于等于valDiff就说明找到
            Long prev = set.floor((long) a[i]);                         // 查询小于等于a[i]的最大元素（即从左侧最接近a[i]的元素）
            if (prev != null && a[i] - prev <= valDiff) return true;    // 如果能查到，且a[i]与prev的距离小于等于valDiff就说明找到
            set.add((long) a[i]);                                       // 这时候才添加元素进入set中
            if (i >= idxDiff) set.remove((long) a[i - idxDiff]);    // 控制滑动窗口宽度，一旦扫描元素超过窗口宽度，就需要删除前面的一个元素
        }
        return false;
    }


    /** 解法1：线性滑动窗（双指针实现）遍历。Time - o(n * min(n, k)), Space - o(1). */
    // 维持一个宽度为k的滑动窗，每个新加入的元素都要与滑动窗内的所有元素进行比较，看差值是否小于等于t
    // 初始状态滑动窗宽度为0，以双指针作为滑动窗的起点left和终点right。随着滑动窗宽度不断增长，直到宽度为k的时候开始移动左侧指针。
    static boolean containsDuplicate(int[] a, int idxDiff, int valDiff) {
        if (idxDiff < 0 || valDiff < 0) return false;
        int left = 0;
        for (int right = 0; right < a.length; right++) {
            if (right > idxDiff) left++;                  // 滑动窗增长到宽度k以后，才开始移动起点指针，以保持滑动窗的宽度恒定。
            for (int i = left; i < right; i++) {    // 每个新加入滑动窗的元素a[right]都要与窗内的其他所有元素比较
                long diff = a[right] - a[i];        // 由于差值可能会大于整型取值区间，因此要用long，再用Math.abs()，否则取绝对值会整型溢出。
                if (Math.abs(diff) <= valDiff) return true;
            }
        }
        return false;
    }
}
