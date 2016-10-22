package com.leetcode.math;

/**
 * Created by Michael on 2016/10/22.
 * You are playing the following Nim Game with your friend: There is a heap of stones on the table,
 * each time one of you take turns to <remove 1 to 3 stones>.
 * The one who <removes the last stone> will be the winner. You will take the first turn to remove the stones.
 * Both of you are very clever and have optimal strategies for the game.
 * Write a function to determine whether you can win the game given the number of stones in the heap.
 *
 * For example, if there are 4 stones in the heap, then you will never win the game:
 * no matter 1, 2, or 3 stones you remove, the last stone will always be removed by your friend.
 *
 * Function Signature:
 * public boolean winGame(int n) {...}
 */
public class E292_Nim_Game {
    public static void main(String[] args) {
        System.out.println(winGame(4));
    }

    // 穷举找规律法，同时也有数学归纳法的身影。
    // 对于1，2，3，win
    // 对于4，lose
    // 对于5，6，7，可以分别通过出1，2，3使得对方面对4，所以对方会输，我会赢。
    // 对于8，相当于对方得到了5，6，7，所以对方会赢。
    // 对于9，10，11，可以分别通过出1，2，3使得对方面对8，同样因为8对方会输，我会赢。
    // 所以任何结果都可以简化成为1，2，3赢，4输，这两个情况。所以取模即可。
    static boolean winGame(int n) {
        return n % 4 == 0;
    }
}
