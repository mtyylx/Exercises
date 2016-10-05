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
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class E252_Meeting_Rooms {
    public static void main(String[] args) {
        Interval[] intervals = new Interval[] {new Interval(5, 8), new Interval(6, 8)};
        System.out.println(canAttendAllMeetings4(intervals));
    }

    // 此题的关键在于要按照start成员变量值对interval对象排序，再依次判断每个interval对象的end成员变量值是否交叉。
    // 思路1：可以直接尝试分别用Counting Sort和Insertion Sort来排序。
    // 思路2：直接修改Interval类，使其实现Comparable接口以直接使用Arrays.sort方法
    // 思路3：由于可以确保每个interval都是有效的，因此可以对interval数组的所有start和end单独排序，无需按interval对象分析了。
    static boolean canAttendAllMeetings4(Interval[] intervals) {
        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i].start;
            end[i] = intervals[i].end;
        }
        Arrays.sort(start);
        Arrays.sort(end);
        for (int i = 1; i < intervals.length; i++)
            if (start[i] < end[i - 1]) return false;
        return true;
    }

    // Counting Sort解法: Get Max -> Get Histo -> Get Accumulative Histo -> Sorted -> Check Validity
    // Time - o(n), space - o(n)
    static boolean canAttendAllMeetings(Interval[] intervals) {
        int max = 0;
        for (Interval i : intervals)
            max = Math.max(max, i.start);
        int[] b = new int[max + 1];
        for (Interval i : intervals)
            b[i.start]++;
        for (int i = 1; i < b.length; i++)
            b[i] += b[i - 1];
        Interval[] c = new Interval[intervals.length];
        for (int i = intervals.length - 1; i >= 0; i--) {
            c[--b[intervals[i].start]] = intervals[i];
        }
        for (int i = 1; i < c.length; i++)
            if (c[i].start < c[i - 1].end) return false;
        return true;
    }

    // Insertion Sort解法：
    // Time - o(n^2), Space - o(1)
    static boolean canAttendAllMeetings2(Interval[] intervals) {
        int i, j;
        Interval current;
        for (i = 0; i < intervals.length; i++) {
            current = intervals[i];
            for (j = i - 1; j >= 0 && intervals[j].start >= current.start; j--)
                intervals[j + 1] = intervals[j];
            intervals[j + 1] = current;
        }
        for (i = 1; i < intervals.length; i++)
            if (intervals[i].start < intervals[i - 1].end) return false;
        return true;
    }

    // 直接使用Arrays.sort方法比较任何的Interval对象
    static boolean canAttendAllMeetings3(Interval[] intervals) {
        Arrays.sort(intervals);
        for (int i = 1; i < intervals.length; i++)
            if (intervals[i].start < intervals[i - 1].end) return false;
        return true;
    }
}

class Interval implements Comparable<Interval> {
    int start;
    int end;
    Interval() {
        start = 0;
        end = 0;
    }
    Interval(int s, int e) {
        start = s;
        end = e;
    }

    // 这是我加的偷懒方法，当然实际题目中是不允许你修改Interval类本身的。
    // 只要声明Interval类实现了Comparable规范，并提供compareTo方法（其实就是比较两个Interval对象的start成员变量值）
    // 就可以使用Arrays.sort方法对任意的Interval数组进行排序了，是不是爽到飞起？！
    @Override
    public int compareTo(Interval o) {
        return start - o.start;
    }
}
