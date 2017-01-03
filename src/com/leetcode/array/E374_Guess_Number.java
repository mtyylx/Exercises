package com.leetcode.array;

/**
 * Created by Michael on 2017/1/3.
 *
 * We are playing the Guess Game. The game is as follows:
 * I pick a number from 1 to n. You have to guess which number I picked.
 * Every time you guess wrong, I'll tell you whether the number is higher or lower.
 * You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):
 * -1 : My number is lower
 * 1 : My number is higher
 * 0 : Congrats! You got it!
 *
 * Example: n = 10, I pick 6. Return 6.
 *
 * Function Signature:
 * public int guessNumber(int n) {...}
 *
 * <Tags>
 * - Binary Search:  ( ... | ... )
 * - Ternary Search: ( ... | ... | ... )
 *
 */
public class E374_Guess_Number{
    public static void main(String[] args) {
        E374_Guess_Number obj = new E374_Guess_Number(6);
        System.out.println(obj.guessNumber(10));
        System.out.println(obj.guessNumber2(10));
    }

    private int picked;
    public E374_Guess_Number (int picked) {
        this.picked = picked;
    }

    // 这里的My number指的是对方picked的number
    public int guess(int x) {
        if      (picked > x) return 1;
        else if (picked < x) return -1;
        else return 0;
    }

    /** 标准Binary Search: Time - o(log2n), Space - o(1) */
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if      (guess(mid) < 0) right = mid - 1;
            else if (guess(mid) > 0) left = mid + 1;
            else return mid;
        }
        return left;
    }

    /** Ternary Search: Time - o(log3n), Space - o(1) */
    // 虽然平均时间复杂度上Ternary要优于Binary Search，
    // 但是在最坏情况下，Ternary Search的比较次数多于Binary Search.
    // 这就是是为什么Binary Search最常用，而不是分成N段
    // i ... mid1 ... mid2 ...  j
    // 第一部分 | 第二部分 | 第三部分
    public int guessNumber2(int n) {
        int i = 1;
        int j = n;
        while (i <= j) {
            int mid1 = i + (j - i) / 3;         // 三分之一中点
            int mid2 = j - (j - i) / 3;         // 三分之二中点
            if (guess(mid1) == 0) return mid1;
            if (guess(mid2) == 0) return mid2;
            if      (guess(mid1) < 0) j = mid1 - 1;     // 落在第一部分
            else if (guess(mid2) > 0) i = mid2 + 1;     // 落在第三部分
            else { i = mid1 + 1; j = mid2 - 1; }        // 落在第二部分
        }
        return -1;
    }
}

