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
 * <Tags>
 * - 使用Arrays.sort(T array, Comparator<T> comparator)进行自定义规则的对象数组排序。
 * - 使用匿名类或Lambda表达式
 *
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
    static boolean canAttendAllMeetings4(Interval[] a) {
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

    // Counting Sort解法: Get Max -> Get Histo -> Get Accumulative Histo -> Sorted -> Check Validity
    // Time - o(n), space - o(n)
    static boolean canAttendAllMeetings(Interval[] a) {
        int max = 0;
        for (Interval i : a)
            max = Math.max(max, i.start);
        int[] b = new int[max + 1];
        for (Interval i : a)
            b[i.start]++;
        for (int i = 1; i < b.length; i++)
            b[i] += b[i - 1];
        Interval[] c = new Interval[a.length];
        for (int i = a.length - 1; i >= 0; i--) {
            c[--b[a[i].start]] = a[i];
        }
        for (int i = 1; i < c.length; i++)
            if (c[i].start < c[i - 1].end) return false;
        return true;
    }

    // Insertion Sort解法：
    // Time - o(n^2), Space - o(1)
    static boolean canAttendAllMeetings2(Interval[] a) {
        int i, j;
        Interval current;
        for (i = 0; i < a.length; i++) {
            current = a[i];
            for (j = i - 1; j >= 0 && a[j].start >= current.start; j--)
                a[j + 1] = a[j];
            a[j + 1] = current;
        }
        for (i = 1; i < a.length; i++)
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
        Arrays.sort(a, (Interval i1, Interval i2) -> { return i1.start - i2.start;});
        for (int i = 1; i < a.length; i++)
            if (a[i].start <= a[i - 1].end) return false;
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
