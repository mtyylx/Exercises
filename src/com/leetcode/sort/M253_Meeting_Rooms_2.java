package com.leetcode.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Michael on 2017/3/3.
 *
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 *
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]], return 2.
 *
 * Function Signature:
 * public int roomsRequired(Interval[] a) {...}
 *
 */
public class M253_Meeting_Rooms_2 {
    public static void main(String[] args) {
        System.out.println(roomsRequired(new Interval[] {new Interval(0, 30), new Interval(5, 10), new Interval(15, 20)}));
    }

    static int roomsRequired(Interval[] a) {
        if (a == null || a.length < 1) return 0;
        int count = 0;
        Arrays.sort(a, Comparator.comparingInt(i -> i.start));
        int fast = 0;
        int slow = 0;
        while (slow < a.length) {
            int end = a[slow].end;
            int i = 0;
            while (fast < a.length && a[fast].start <= end) {
                end = Math.max(end, a[fast++].end);
                i++;
            }
            slow = fast;
            count = Math.max(count, i);
        }
        return count;
    }
}
