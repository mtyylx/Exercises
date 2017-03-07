package com.leetcode.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Michael on 2016/10/5.
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * determine if a person could attend all meetings.
 *
 * For example, Given [[0, 30],[5, 10],[15, 20]], return false.
 *
 * Function Signature:
 * public boolean hasNoConflict(Interval[] a) {...}
 *
 * <区间集合 系列问题>
 * E252 Meeting Rooms  : 给定一个时间区间集合，判断是否存在区间交叉的情况。
 * M56  Merge Intervals: 给定一个时间区间集合，合并所有存在交叉的区间并返回。
 * M253 Meeting Rooms 2: 给定一个时间区间集合，判断最多需要多少个并行的线程可以无冲突执行所有时间区间。
 *
 * <Tags>
 * - 使用Arrays.sort(T array, Comparator<T> comparator)进行自定义规则的对象数组排序。
 * - 使用匿名类或Lambda表达式
 *
 */
public class E252_Meeting_Rooms {
    public static void main(String[] args) {
        Interval[] intervals = {new Interval(15, 20), new Interval(20, 25), new Interval(5, 10)};
        Interval[] intervals2 = {new Interval(15, 25), new Interval(20, 25), new Interval(5, 10)};
        System.out.println(hasNoConflict(intervals));
        System.out.println(hasNoConflict2(intervals));
        System.out.println(hasNoConflict3(intervals));
        System.out.println(hasNoConflict4(intervals));
        System.out.println(hasNoConflict5(intervals));
        System.out.println(hasNoConflict(intervals2));
        System.out.println(hasNoConflict2(intervals2));
        System.out.println(hasNoConflict3(intervals2));
        System.out.println(hasNoConflict4(intervals2));
        System.out.println(hasNoConflict5(intervals2));
    }

    // 此题的关键在于先按start的大小对数组对象排序，再顺序扫描数组的每两个相邻的Interval对象的start和end是否交叉，如果交叉就说明时间冲突。

    /** 解法5：将start和end分别拷贝至新的数组进行排序。 */
    // 注意这么做的前提是给出的每一个Interval对象的start都是小于end的。
    static boolean hasNoConflict5(Interval[] a) {
        int[] start = new int[a.length];
        int[] end = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            start[i] = a[i].start;
            end[i] = a[i].end;
        }
        Arrays.sort(start);
        Arrays.sort(end);
        for (int i = 1; i < a.length; i++)
            if (start[i] < end[i - 1]) return false;
        return true;
    }

    /** 解法4：转化为一般数组排序问题（对Interval对象的start成员变量值进行计数排序。Time - o(n), Space - o(n). */
    // Get Max -> Get Histo -> Get Accumulative Histo -> Sorted -> Check Validity
    static boolean hasNoConflict4(Interval[] a) {
        int max = 0;
        for (Interval i : a)
            max = Math.max(max, i.start);
        int[] b = new int[max + 1];
        for (Interval i : a)
            b[i.start]++;
        for (int i = 1; i < b.length; i++)
            b[i] += b[i - 1];
        Interval[] c = new Interval[a.length];
        for (int i = a.length - 1; i >= 0; i--)
            c[--b[a[i].start]] = a[i];
        for (int i = 1; i < c.length; i++)
            if (c[i].start < c[i - 1].end) return false;
        return true;
    }

    /** 解法3：转化为一般数组排序问题（对Interval对象的start成员变量值进行插入排序）。Time - o(n^2), Space - o(1). */
    static boolean hasNoConflict3(Interval[] a) {
        for (int i = 0; i < a.length; i++) {
            Interval current = a[i];
            int j;
            for (j = i - 1; j >= 0 && a[j].start >= current.start; j--)
                a[j + 1] = a[j];
            a[j + 1] = current;
        }
        for (int i = 1; i < a.length; i++)
            if (a[i].start < a[i - 1].end) return false;
        return true;
    }

    /** 解法2：直接在Interval类内实现Comparable接口，重写compare方法。更为简洁。 */
    static boolean hasNoConflict2(Interval[] a) {
        Arrays.sort(a);
        for (int i = 1; i < a.length; i++)
            if (a[i].start < a[i - 1].end) return false;
        return true;
    }

    /** 解法1：使用Lambda表达式重写compare方法，只比较每个Interval对象的start成员属性值。 */
    static boolean hasNoConflict(Interval[] a) {
        Arrays.sort(a, (Interval i1, Interval i2) -> { return i1.start - i2.start; });
        for (int i = 1; i < a.length; i++)
            if (a[i].start < a[i - 1].end) return false;
        return true;
    }
}

class Interval implements Comparable<Interval> {
    int start;
    int end;
    Interval(int s, int e) {
        start = s;
        end = e;
    }

    // 让Interval类实现Comparable接口，并重写compare方法，使其只比较两个Interval对象的start成员变量值）
    // 就可以使用Arrays.sort()方法对任意的Interval数组进行排序了，是不是爽到飞起？！
    @Override
    public int compareTo(Interval anotherInterval) {
        return this.start - anotherInterval.start;
    }
}
