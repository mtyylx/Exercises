package com.leetcode.array;

/**
 * Created by LYuan on 2016/10/21.
 * Write a function that takes an unsigned integer and returns the number of '1' bits it has.
 * You need to treat n as an unsigned value.
 * Also known as the <Hamming weight>.
 *
 * For example,
 * the 32-bit integer '11' has binary representation 00 00000 00000 00000 00000 00000 01011,
 * so the function should return 3.
 *
 * Function Signature:
 * public int hammingWeight(int n) {...}
 *
 * <系列问题>
 * - E191 Hamming Weight  : 给定一个无符号数，返回该数值二进制形式1bit的个数。
 * - E461 Hamming Distance: 给定两个数值，返回两个数值二进制形式不同的比特个数。（本质上就是求两个数值异或值的Hamming Weight）
 *
 * <Tags>
 * - Bit Manipulation: XOR with ^ operator.
 * - Bit Manipulation: Bit Mask with & operator.
 * - Bit Manipulation: x ^ (x - 1) to cleanup the lowest 1 bit.
 *
 */
public class E191_Hamming_Weight {
    public static void main(String[] args) {
        int a = 193;
        System.out.println(hammingWeight(a));
        System.out.println(hammingWeight2(a));
        System.out.println(hammingWeight3(a));

        System.out.println(~(-1));
        System.out.println(~(0));
        System.out.println(~Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE>>1);
    }

    // 到底Java中的整型数值对应的二进制比特是什么样的：(移位运算">>"和"<<"可以有效的展示一个数值真实的二进制信息)
    // 1 << 1，== 2，即2^1                 0000 0000 0000 0000, 0000 0000 0000 0010
    // 1 << 2，== 4，即2^2                 0000 0000 0000 0000, 0000 0000 0000 0100
    // 1 << 30，== 1073741824，即2^30      0100 0000 0000 0000, 0000 0000 0000 0000
    // 1 << 31，== -2147483648，即2^31     1000 0000 0000 0000, 0000 0000 0000 0000    (Java整型溢出了)
    // 2147483647，即2^31-1，整型最大值      0111 1111 1111 1111, 1111 1111 1111 1111
    // 可以看到，其实在1 << 31的时候就出现了整型溢出，如果我们要找到对应的无符号数，和对应实际得到的值，将会有以下这种对应关系：
    // <理论中的无符号数>    <实际看到的值>    <对应的二进制比特>
    // 2147483647         2147483647      0111 1111 1111 1111, 1111 1111 1111 1111
    // 2147483648         -2147483648     1000 0000 0000 0000, 0000 0000 0000 0000  (Overflow Started)
    // 2147483649         -2147483647     1000 0000 0000 0000, 0000 0000 0000 0001
    // ...                ...             ...
    // 4294967294         -2              1111 1111 1111 1111, 1111 1111 1111 1110
    // 4294967295         -1              1111 1111 1111 1111, 1111 1111 1111 1111

    /** 解法1：Bitmask + 逻辑右移。 */
    // Mask始终是1（即一个只有最低位为1的二进制数）
    // n不断被右移直至全0。注意这里使用的是逻辑右移，而不是算数右移，因为这里n是无符号数。
    // 逻辑右移 >>> 最高位强制补0
    // 算数右移 >> 最高位保留当前值的符号
    static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;     // 检查最低位是1还是0
            n >>>= 1;           // 右移一位
        }
        return count;
    }

    /** 解法2：移动Bitmask的解法。 */
    // 由于Java里面的并没有无符号数的整型，但是这里又非要让你把一个int（分正负）看作一个无符号整型，
    // 相当于修改取值范围从：-2147483648(-2^31) to 2147483647(2^31-1) 变成了 0 to 4294967295(2^32-1)
    // 因此如果只是用常规的循环除2法计算余数为1的个数，是无法处理超过Integer.MAX_VALUE的情况的。
    // 这时候使用bitmask就会格外简化了问题。
    // 只需要构造一个只有1位是1的bitmask，然后将数与这个bitmask求与运算，即可知道是否这一位是1，而完全不用关心这时候这个值溢出成了什么。
    // 00000000001011
    // 00000000000001  <- bitmask不断向左平移。
    static int hammingWeight2(int n) {
        int mask = 1;
        int count = 0;
        while (mask != 0) {
            if ((mask & n) == mask) count++;
            mask = mask << 1;
        }
        return count;
    }

    /** 解法3：利用 n & (n - 1)比特运算的特性：可以让n的最低位的1变成0. */
    // 运算次数会比上面的解法还要少。
    //      10101  <- n
    //   &  10100  <- (n-1)
    //      10100  n最低位的1被清理
    // 不断使用这个性质，让n的所有为1的位都清理为0，即n变成0的时候返回count即可。
    static int hammingWeight3(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }
}

