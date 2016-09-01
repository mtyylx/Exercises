package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/9/1.
 * Design a logger system that receive stream of messages along with its timestamps,
 * each message should be printed if and only if it is not printed in the last 10 seconds.
 * Given a message and a timestamp (in seconds granularity),
 * return true if the message should be printed in the given timestamp, otherwise returns false.
 * It is possible that several messages arrive roughly at the same time.
 *
 * Example:
 * Logger logger = new Logger();
 * // logging string "foo" at timestamp 1
 * logger.shouldPrintMessage(1, "foo"); returns true;
 * // logging string "bar" at timestamp 2
 * logger.shouldPrintMessage(2,"bar"); returns true;
 * // logging string "foo" at timestamp 3
 * logger.shouldPrintMessage(3,"foo"); returns false;
 * // logging string "bar" at timestamp 8
 * logger.shouldPrintMessage(8,"bar"); returns false;
 * // logging string "foo" at timestamp 10
 * logger.shouldPrintMessage(10,"foo"); returns false;
 * // logging string "foo" at timestamp 11
 * logger.shouldPrintMessage(11,"foo"); returns true;
 *
 * public boolean shouldPrintLog(int timestamp, String message) {...}
 */

public class E359_Logger_Rate_Limiter {
    public static void main(String[] args) {
        Logger logger = new Logger();
        System.out.println(logger.shouldPrintLog(1, "foo")); //returns true;
        System.out.println(logger.shouldPrintLog(2,"bar")); //returns true;
        System.out.println(logger.shouldPrintLog(3,"foo")); //returns false;
        System.out.println(logger.shouldPrintLog(8,"bar")); //returns false;
        System.out.println(logger.shouldPrintLog(10,"foo")); //returns false;
        System.out.println(logger.shouldPrintLog(11,"foo")); //returns true;
    }
}

// 使用哈希表解法最直观
// 思路是：打印出来的消息才更新哈希表中对应键的时间戳，没打印的则不更新时间戳
class Logger {
    private Map<String, Integer> map;

    public Logger() {
        this.map = new HashMap<>();
    }

    public boolean shouldPrintLog(int timestamp, String message) {
        // 如果没有出现该消息，或者出现了该消息但是时间戳只差大于10秒，那么就可以打印并更新时间戳
        // 注意这里或运算的设计，A||B中，只有A为false时才会执行B，所以可以把B的前提写在!A中。
        if (!map.containsKey(message) || timestamp - map.get(message) >= 10) {
            map.put(message, timestamp);
            return true;
        }
        else
            return false;
    }
}
