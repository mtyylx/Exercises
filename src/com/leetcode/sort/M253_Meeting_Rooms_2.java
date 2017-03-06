package com.leetcode.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

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
        if (a == null || a.length == 0)
            return 0;

        // Sort the intervals by start time
        Arrays.sort(a, (x, y) -> (x.start - y.start));

        // Use a min heap to track the minimum end time of merged intervals
        PriorityQueue<Interval> heap = new PriorityQueue<>(a.length, (x, y) -> (x.end - y.end));

        // start with the first meeting, put it to a meeting room
        heap.offer(a[0]);

        for (int i = 1; i < a.length; i++) {
            // get the meeting room that finishes earliest
            Interval interval = heap.poll();

            if (a[i].start >= interval.end) {
                // if the current meeting starts right after
                // there's no need for a new room, merge the interval
                interval.end = a[i].end;
            } else {
                // otherwise, this meeting needs a new room
                heap.offer(a[i]);
            }

            // don't forget to put the meeting room back
            heap.offer(interval);
        }

        return heap.size();
    }
}
