package com.leetcode.array;

/**
 * Created by Michael on 2016/10/22.
 * You are a product manager and currently leading a team to develop a new product.
 * Unfortunately, the latest version of your product fails the quality check.
 * Since each version is developed based on the previous version, all the versions after a bad version are also bad.
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
 * which causes all the following ones to be bad.
 * You are given an API bool isBadVersion(version) which will return whether version is bad.
 * Implement a function to find the first bad version. You should minimize the number of calls to the API.
 *
 * Function Signature:
 * public int firstBadVersion(int n) {...}
 *
 * <系列问题>
 * M35  Search Insert Position: 给定一个已排序数组a和一个目标值k，求k在a中出现的位置值。如果k不在a中，求将k插入a中后让a依然保持有序的位置值。
 * M34  Search In Range:        给定一个已排序数组a和一个目标值，求k在a中出现的起始和终止位置值。如果k不在a中，则返回[-1, -1]
 * E278 First Bad Version:      给定一个取值范围1至n（即有序）和一个判定函数，求让判定函数第一次返回true的位置索引。
 * E374 Guess Number:           给定一个取值范围1至n（即有序）和一个判定函数，求让判定函数返回0的位置索引。
 *
 * <Tags>
 * - Binary Search
 *
 */
public class E278_First_Bad_Version {
    public static void main(String[] args) {
        System.out.println(VersionControl.firstBadVersion(9));
        System.out.println(VersionControl.firstBadVersion2(9));
    }
}

class VersionControl {

    /** 解法1：Binary Search, Iterative. */
    // 注意1: 求中点时避免整型溢出。
    // 注意2: 要让左右指针交错再结束循环，此时left指针的位置一定是待插入位置（第一个错误版本）
    static int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    /** 解法2：Binary Search, Recursive. */
    static int firstBadVersion2(int n) {
        return divide(1, n);
    }

    static int divide(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) return divide(left, mid);
            else return divide(mid + 1, right);
        }
        else return left;
    }

    // 由题目内部提供，这里并未实现。
    static boolean isBadVersion(int x) {
        return false;
    }
}
