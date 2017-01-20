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
 * logger.shouldPrintMessage(1, "foo");     // returns true;
 *
 * // logging string "bar" at timestamp 2
 * logger.shouldPrintMessage(2,"bar");      // returns true;
 *
 * // logging string "foo" at timestamp 3
 * logger.shouldPrintMessage(3,"foo");      // returns false;
 *
 * // logging string "bar" at timestamp 8
 * logger.shouldPrintMessage(8,"bar");      // returns false;
 *
 * // logging string "foo" at timestamp 10
 * logger.shouldPrintMessage(10,"foo");     // returns false;
 *
 * // logging string "foo" at timestamp 11
 * logger.shouldPrintMessage(11,"foo");     // returns true;
 *
 * public boolean shouldPrint(int timestamp, String message) {...}
 *
 * <Tags>
 * - HashMap: Key → 消息, Value → 时间戳
 *
 */

public class E359_Logger_Rate_Limiter {
    public static void main(String[] args) {
        LogLimiter2 logger2 = new LogLimiter2();
        System.out.println(logger2.shouldPrint("foo", 1)); //returns true;
        System.out.println(logger2.shouldPrint("bar", 2)); //returns true;
        System.out.println(logger2.shouldPrint("foo", 3)); //returns false;
        System.out.println(logger2.shouldPrint("bar", 8)); //returns false;
        System.out.println(logger2.shouldPrint("foo", 10)); //returns false;
        System.out.println(logger2.shouldPrint("foo", 11)); //returns true;

        LogLimiter logger = new LogLimiter();
        System.out.println(logger.shouldPrint("foo", 1)); //returns true;
        System.out.println(logger.shouldPrint("bar", 2)); //returns true;
        System.out.println(logger.shouldPrint("foo", 3)); //returns false;
        System.out.println(logger.shouldPrint("bar", 8)); //returns false;
        System.out.println(logger.shouldPrint("foo", 10)); //returns false;
        System.out.println(logger.shouldPrint("foo", 11)); //returns true;

    }
}

/** 解法2：同样是HashMap，简化了下逻辑。 */
// 思路是：打印出来的消息才更新哈希表中对应键的时间戳，没打印的则不更新时间戳
class LogLimiter2 {
    private Map<String, Integer> map;

    public LogLimiter2() {
        this.map = new HashMap<>();
    }

    public boolean shouldPrint(String message, int timestamp) {
        // 如果没有出现该消息，或者出现了该消息但是时间戳之差大于等于10秒，那么就可以打印，并同时更新时间戳。
        // 注意这里或运算的设计，A||B中，只有A为false时才会执行B，所以可以把B的前提写在!A中。
        if (!map.containsKey(message) || timestamp - map.get(message) >= 10) {
            map.put(message, timestamp);
            return true;
        }
        else
            return false;
    }
}

/** 解法1：HashMap. */
// 关键在于只有时间戳之差超过了10，才可以打印，打印才可以导致时间戳更新，才可以导致继续打印。
class LogLimiter {
    private Map<String, Integer> map;
    public LogLimiter() {
        map = new HashMap<>();
    }

    public boolean shouldPrint(String message, int timeStamp) {
        if (map.containsKey(message)) {                             // 消息出现过
            if (timeStamp - map.get(message) < 10) return false;    // 消息出现过且是在10秒钟内出现的，不打印
            map.put(message, timeStamp);                            // 否则打印，并更新时间戳
            return true;
        }
        else {                                                      // 消息未出现过
            map.put(message, timeStamp);                            // 肯定打印，并存储消息时间戳
            return true;
        }
    }
}
