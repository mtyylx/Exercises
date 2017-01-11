package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/1.
 * Write an algorithm to determine if a number is "happy".
 * A happy number is a number defined by the following process: Starting with any positive integer,
 * replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay),
 * or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy numbers.
 *
 * Example: 19 is a happy number
 *
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 *
 * Function Signature:
 * public boolean isHappy(int a) {...}
 *
 * <Tags>
 * - Loop Detection: HashSet判重
 * - Loop Detection: Two Pointers （快慢指针，快指针是慢指针前进速度的两倍）
 * - Math
 *
 * */
public class E202_Happy_Number {
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            if (isHappy3(i)) System.out.println(i);
        }
    }

    /** 解法3：双指针（快慢指针）循环检测（即Floyd循环检测算法）。 Time - o(1), Space - o(1) */
    // 比解法2的空间复杂度低。
    // 慢指针走一步，快指针走两步，对于一个存在循环的数组，快慢指针一定会相会。
    // 这时候只要再检查下是不是1就可判断是否happy了。
    static boolean isHappy3(int a) {
        int slow = a;
        int fast = a;
        while (true) {
            slow = calculate(slow);
            fast = calculate(fast);     // 快指针永远是慢指针前进速度的两倍。
            fast = calculate(fast);
            if (slow == fast) break;
        }
        return slow == 1;
    }

    static int calculate(int a) {
        int temp = 0;
        while (a > 0) {
            temp += (a % 10) * (a % 10);
            a /= 10;
        }
        return temp;
    }

    /** 解法2：HashSet判重（判循环），会占用额外空间，但不会太多。Time - o(1), Space - o(x). */
    // 要看透这道题目的本质：其实是循环检测而已。而循环的本质特征就是重复。因此HashSet一出无人能敌。
    // 相对于猜测较多、不太板上钉钉的解法1，使用HashSet判重可以说是万无一失的。
    static boolean isHappy2(int a) {
        Set<Integer> set = new HashSet<>();
        while (set.add(a)) {
            int temp = 0;
            while (a > 0) {
                temp += (a % 10) * (a % 10);
                a /= 10;
            }
            a = temp;
        }
        return a == 1;
    }

    /** 解法1：找规律。Time - o(1). */
    // 由于其实只有两种走向：一种是循环最后到1，一种是无限循环。
    // 那么第二种情况下这个“无限循环”具体是什么特性呢？是周期循环？还是和无理数一样没有周期的无限进行下去？
    // 这时候我们可以穷举1到9的运算结果，看看能不能从结果中总结出什么规律来。
    // 首先，我们发现，这种运算的是单向的、决定性的，一环套一环，而且只要在运算的“路径”上遇到同一个值，后面的所有运算就都一样了。
    // 而且我们还发现，数字4具有一个很特殊的循环，经过10次运算可以回到自己。这也就是“无限循环”的根源所在了。
    // 虽然我们并没有证据，但是我们可以猜测，无限循环是完全由4的这个循环造成的。
    // 只要运算结果出现4，就一定不是Happy Number。剩下的一定都是Happy Number。（虽然这样看上去证据并不充分，但对于这道题恰好是这样）

    // 死胡同：4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4
    // 可以看到只有1和7成功避免进入死胡同，其他的都阵亡了。
    // 1 - 1
    // 2 - 进入死胡同 - <4> - 16 - 37 - 58 - 89 - 145 - 42 - 20 - 4
    // 3 - 9 - 81 - 65 - 61 - 进入死胡同 - <37> - 58 - 89 - 145 - 42 - 20 - 4
    // 进入死胡同 - <4> - 16 - 37 - 58 - 89 - 145 - 42 - 20 - 4
    // 5 - 25 - 39 - 85 - 进入死胡同 - <89> - 145 - 42 - 20 - 4
    // 6 - 36 - 45 - 41 - 17 - 50 - 25 - 39 - 85 - 进入死胡同 - <89> - 145 - 42 - 20 - 4
    // 7 - 49 - 97 - 130 - 10 - 1
    // 8 - 64 - 52 - 29 - 85 - 进入死胡同 - <89> - 145 - 42 - 20 - 4
    // 9 - 81 - 65 - 61 - 进入死胡同 - <37> - 58 - 89 - 145 - 42 - 20 - 4
    static boolean isHappy(int a) {
        if (a <= 0) return false;
        int sum = 0;
        while (true) {
            while (a > 0) {
                sum += (a % 10) * (a % 10);
                a /= 10;
            }
            if (sum == 1) return true;
            if (sum == 4) return false;
            a = sum;
            sum = 0;
        }
    }
}
