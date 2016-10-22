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
 */
public class E278_First_Bad_Version {
    public static void main(String[] args) {
        System.out.println(VersionControl.firstBadVersion(9));
    }
}

class VersionControl {

    /** 二分查找解法，迭代写法，区别只是不再是比大小，而是直接判定是或者不是。 */
    // 需要小心求中点的时候应该用i + (j - i)/2 而不是 (i + j)/2，避免整型溢出。
    static int firstBadVersion(int n) {
        int left = 0;
        int right = n - 1;
        int i;
        while (left <= right) {
            i = left + (right - left) / 2;
            if (isBadVersion(i)) right = i - 1;
            else left = i + 1;
        }
        return left;    // 不管什么情况，left指针的位置都刚好是第一个返回true的元素。
    }

    /** 二分查找的递归解法 */
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
