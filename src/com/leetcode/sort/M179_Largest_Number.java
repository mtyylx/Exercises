package com.leetcode.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Michael on 2016/10/7.
 * Given a list of non negative integers, arrange them such that they form the largest number.
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * Note: The result may be very large, so you need to return a string instead of an integer.
 *
 * Function Signature:
 * public String largestNumber(int[] a) {...}
 *
 * <Tags>
 * - 基于比较的排序的本质
 * - 避免整型溢出：转换为字符串比较问题。
 * - 字符串比较：比较每个字符的ASCII值的大小（Lexicographic Order）
 * - int2String类型转换：String.valueOf(int x)
 * - 使用匿名类、Lambda表达式。
 *
 */
public class M179_Largest_Number {
    public static void main(String[] args) {
        int[] a = {99, 88, 77, 66, 5, 50};

        System.out.println(largestNumber(a));       // slow
        System.out.println(largestNumber2(a));      // fast
        System.out.println(largestNumber3(a));      // fast
        System.out.println(largestNumberx(a));      // fast
    }

    /** 解法3：Lambda表达式。更为简洁。 */
    static String largestNumber3(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++)                          // 全部转换为字符串数组操作，以避免重复转换
            s[i] = String.valueOf(a[i]);

        Arrays.sort(s, (String x, String y) -> {                    // 使用Lambda表达式隐式重写compare方法（实现Comparator接口）
            return (y + x).compareTo(x + y);
        });

        if(s[0].charAt(0) == '0') return "0";                       // 需要考虑数组全为0的特例，特殊处理
        StringBuilder sb = new StringBuilder();
        for (String i : s) sb.append(i);
        return sb.toString();
    }

    /** 解法2：匿名类 + 重写Arrays.sort所使用的compare方法，自定义比较逻辑. */
    // 由于仅仅修改了元素大小的概念，排序算法用的还是库函数，因此可以确保性能最优（用的应该是快速排序的变体）
    // 关键点1：对比机制的自定义
    // 关键点2：排序算法
    // 关键点3：特例边界情况处理
    static String largestNumber2(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++)                          // 全部转换为字符串数组操作，以避免重复转换
            s[i] = String.valueOf(a[i]);
        // Create a new Object of a Anonymous Class that implements the "Comparator" Interface.
        Comparator<String> comparator = new Comparator<String>() {  // 定义并实例化一个实现Comparator接口的匿名类的对象，该匿名类赋予了compare方法新的逻辑：组合数大才算大
            @Override
            public int compare(String x, String y) {
                return (y + x).compareTo(x + y);        // 内部再调用String类实现的compareTo方法，比较两个字符串字典排序顺序
            }
        };

        Arrays.sort(s, comparator);                                 // 将重写compare方法的类对象传入Arrays.sort方法
        if(s[0].charAt(0) == '0') return "0";                       // 需要考虑数组全为0的特例，特殊处理
        StringBuilder sb = new StringBuilder();
        for (String i : s) sb.append(i);
        return sb.toString();
    }
    // 简化写法
    static String largestNumberx(int[] a) {
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++)                          // 全部转换为字符串数组操作，以避免重复转换
            s[i] = String.valueOf(a[i]);

        Arrays.sort(s, new Comparator<String>() {                   // 注意这里的语法是匿名类的语法：new Comparator()并不是要实例化接口
            @Override                                               // 而是定义了一个实现接口的类，并在大括号中重写了接口的抽象方法compare
            public int compare(String x, String y) {
                return (y + x).compareTo(x + y);
            }
        });

        if(s[0].charAt(0) == '0') return "0";                       // 需要考虑数组全为0的特例，特殊处理
        StringBuilder sb = new StringBuilder();
        for (String i : s) sb.append(i);
        return sb.toString();
    }


    /** 解法1：插入排序 + 字符串比较。Time - O(n^2), Space - O(n) */
    // 其实这里使用什么排序算法都可以。这里只是用插入排序实现，可以像解法2一样使用性能更好的排序算法。
    // 这个问题的特点在于要以字符串相连的方式比较组合数的大小。
    // <关键点1> 相连后的新数可能会超过整型范围，此时如何比较大小 -> 使用字符ASCII值比较字典顺序。
    // 这里既可以使用String类库直接实现的来自Comparable接口的compareTo方法来比较大小，也可以自己动手写一个同样逻辑的方法。
    // <关键点2> 相比于普通按元素数值大小排序的算法，这里需要自定义“数值大小”这个概念。
    // 普通的比较排序算法只需要按照两个元素值的大小来排序。例如 9 50 59 排序为 9 50 59
    // 但是在这道题中，我们如果还按照上面的逻辑，将无法得到最大的组合数，因为升序排列的元素组合在一起并不一定是最小或最大的。
    // 考虑这两个相悖的例子：10 50 组合最大值应该是5010。 9 10 组合最大值应该是910。
    // 于是我们的排序原则需要变成：a和b谁在先组合在一起的值更大，就让他排在前面。例如 9 50 59 应该排序为 9 59 50 才对。
    // <容易疏忽的点> 当数组元素并非全0时，无需担心是否需要清理格式，因为此时非0值一定会被放在组合数的最前面，此时的0元素都是非常有用的。
    // 但如果数组元素是全0时，那么此时组合数将是一个形如"00000...000"的无效字符串，不符合数值的格式，因此需要单独处理为数值0本身。
    static String largestNumber(int[] a) {
        for (int i = 1; i < a.length; i++) {                    // 标准的插入排序双指针解法
            int j = i - 1;
            int temp = a[i];
            for (; j >= 0 && larger(a[j], temp); j--)           // 只有当前已排序元素比temp大（两者组合起来应该让已排序元素在前面） 才继续
                a[j + 1] = a[j];
            a[j + 1] = temp;
        }
        if (a[a.length - 1] == 0) return "0";                   // 需要特殊处理全部元素都是0的情况，因为此时会组合出来一个全是0的结果，不符合数值格式
        StringBuilder sb = new StringBuilder();
        for (int i = a.length - 1; i >= 0; i--)                 // 逆序依次相连所有元素的文本
            sb.append(String.valueOf(a[i]));
        return sb.toString();
    }

    static boolean larger(int a, int b) {                       // 自定义两个整型元素的大小判断逻辑，相当于重写的“大于号”本身的含义
        String x = String.valueOf(a);
        String y = String.valueOf(b);
        return strcmp(x + y, y + x);                       // 不再直接比较元素值本身的大小关系，而是按照那种组合方式的顺序更大来判断
    }

    static boolean strcmp(String a, String b) {                 // 实现了String对象具有的compareTo()方法，当a>=b时返回true
        if (a.length() > b.length()) return true;
        if (a.length() < b.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) > b.charAt(i)) return true;
            if (a.charAt(i) < b.charAt(i)) return false;
        }
        return true;
    }
}
