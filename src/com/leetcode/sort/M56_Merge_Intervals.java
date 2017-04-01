package com.leetcode.sort;

import java.util.*;

/**
 * Created by Michael on 2017/3/2.
 * Given a collection of intervals, merge all overlapping intervals.
 *
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18], return [1,6],[8,10],[15,18].
 *
 * Function Signature:
 * public List<Interval> mergeInterval(List<Interval> a) {...}
 *
 * <区间集合 系列问题>
 * E252 Meeting Rooms  : 给定一个时间区间集合，判断是否存在区间交叉的情况。
 * M56  Merge Intervals: 给定一个时间区间集合，合并所有存在交叉的区间并返回。
 * M253 Meeting Rooms 2: 给定一个时间区间集合，判断最多需要多少个并行的线程可以无冲突执行所有时间区间。
 *
 * <Tags>
 * - 使用匿名类、Lambda表达式自定义排序逻辑。
 * - Two Pointers: 快慢指针同向扫描。[ slow -> ... fast -> -> ... ]
 *
 */
public class M56_Merge_Intervals {
    public static void main(String[] args) {
        List<Interval> list = new ArrayList<>(Arrays.asList(new Interval(0, 3), new Interval(1, 2)));
        List<Interval> result = mergeIntervals(list);
        List<Interval> list2 = new ArrayList<>(Arrays.asList(new Interval(0, 3), new Interval(1, 2)));
        List<Interval> result2 = mergeIntervals2(list2);
    }

    /** 解法1：Lambda表达式重写compare准则 + 双指针（快慢指针）扫描合并。Time - O(nlogn), Space - O(1). */
    // 首先一上来肯定按照Interval的start属性对Interval对象进行排序。
    // 具体的写法很灵活：
    // 方法1：可以直接让Interval实现Comparator接口的compare方法。
    // 方法2：直接用匿名类重写compare方法
    // 方法3：直接用Lambda表达式 Collections.sort(a, (Interval i1, Interval i2) -> i1.start - i2.start);
    // 方法4：用Lambda表达式 + Comparator.compareInt(i -> i.start)，意思是告诉大家我比较的是对象的start这个属性。
    // 另外对于指定Comparator的情况，对ArrayList进行排序可以有两种写法：Collections.sort(a, comparator) 或 listObj.sort(comparator)
    // 然后就在于如何谨慎的用双指针处理各种边界情况。
    // 例如 {0, 5} {1, 2} {3, 4}，
    // 0------------5
    //   1--2 3--4
    // 可以看到后面两个区间其实是第一个区间的完全子集。end的值需要向变大这个方向更新。
    // 保持快指针移动的条件并不是相邻节点的起始点重合，而是快指针节点是否完全脱离了当前的{start, end}区间，脱离了才说明是分离区间。
    static List<Interval> mergeIntervals(List<Interval> a) {
        if (a == null || a.size() < 2) return a;
        a.sort(Comparator.comparingInt(i -> i.start));          // 定义一个只比较整型数值的comparator，关注的是每个元素的start属性
        List<Interval> result = new ArrayList<>();
        int slow = 0, fast = 0;                                 // 快慢指针初始状态是重合的
        while (slow < a.size()) {                               // 注意这里的循环终止条件是慢指针扫描到头，而不是快指针，这么做的原因是保证扫描完整
            int start = a.get(slow).start;                      // 珍惜当下：首先缓存慢指针所指对象的属性，以备不时之需。
            int end = a.get(slow).end;
            while (fast < a.size() && a.get(fast).start <= end) // 只要节点的起点在取值范围内，就可以融合
                end = Math.max(end, a.get(fast++).end);         // 融合注意让end保持最大（因为有可能后者是前者的完全子集）
            result.add(new Interval(start, end));
            slow = fast;                                        // 让快慢指针再次重合，供下个循环使用
        }
        return result;
    }
    // 另一种写法，用iterator遍历ArrayList
    static List<Interval> mergeIntervalsx(List<Interval> a) {
        if (a == null || a.size() < 2) return a;
        a.sort(Comparator.comparingInt(i -> i.start));
        List<Interval> result = new ArrayList<>();
        int start = a.get(0).start;
        int end = a.get(0).end;
        for (Interval item : a) {
            if (item.start <= end)                      // 使用于item是第一个对象的时候，以及存在交集的区间之间
                end = Math.max(end, item.end);
            else {
                result.add(new Interval(start, end));   // 说明当前item和缓存的item没有交集，需要将缓存item入库
                start = item.start;
                end = item.end;
            }
        }
        result.add(new Interval(start, end));           // 处理最后一个缓存item（与一般处理方法不同，这里最后一段区间一定需要单独处理）
        return result;
    }


    /** 解法2：分别对两个属性排序 + 双指针（快慢指针）合并区间。用空间换时间。Time - O(nlogn), Space - O(n). */
    // 分别排序的好处是能将完全子集进行解耦。
    //  原区间            解耦区间
    //  0, 5             0, 2
    //  1, 2       →     1, 4
    //  3, 4             3, 5
    //  |----------|    |----|
    //    |--|             |----|
    //         |--|           |----|
    // 虽然解耦会完全失去原有的区间含义，但是实际上这时候我们已经不关心这个大区间内部是如何分段重合的，我们的任务只是尽可能的合并区间。
    static List<Interval> mergeIntervals2(List<Interval> a) {
        if (a == null || a.size() < 2) return a;
        int[] start = new int[a.size()];
        int[] end = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            start[i] = a.get(i).start;
            end[i] = a.get(i).end;
        }
        Arrays.sort(start);
        Arrays.sort(end);
        List<Interval> result = new ArrayList<>();

        for (int fast = 0, slow = 0; fast < start.length; fast++) {             // 慢指针停留在最近的待合并区间，快指针遍历整个数组
            if (fast == start.length - 1 || start[fast + 1] > end[fast]) {      // 可以合并时合并
                result.add(new Interval(start[slow], end[fast]));
                slow = fast + 1;
            }
        }
        return result;
    }

}
