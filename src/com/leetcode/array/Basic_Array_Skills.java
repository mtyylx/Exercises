package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/5.
 *
 * Summarize the basic skills for manipulating array and ArrayList
 *
 */
public class Basic_Array_Skills {
    public static void main(String[] args) {
//        basicUsage();
//        testGenericArray();
//        System.out.println(int2str(1234567));
//        System.out.println(str2int("12345"));
        testAsList();
    }

    static void basicUsage () {
        // 基础类型的数组，初始化后每个元素都是类型默认值（0，False，'\u0000'）
        int[] intArray = new int[10];
        print(intArray[9]);
        intArray[9] = 99;
        print(intArray[9]);

        // 引用类型的数组，初始化后每个元素的值都是null
        String[] strArray = new String[10];
        print(strArray[9]);
        strArray[9] = "abc";
        print(strArray[9]);

        // 面向接口编程：定义接口类型的数组变量，让它指向实现接口的对象数组。
        // List is an <interface>. It can hold a reference to different types of <List implementations> (i.e. ArrayList).
        List[] listArray = new ArrayList[10];
        print(listArray[9]);            // 可以访问，因为这是一个数组。数组初始化以后所有元素立即可用。
        listArray[9] = new ArrayList<>();

        // 定义ArrayList对象，list中存的每一个元素都是Object，向list中add的任何类型数据都会被装箱为Object
        List list = new ArrayList(10);  // 10只是capacity，size依然是0
        // list.get(0); <-- 报错，数组越界（因为list对象目前size = 0）
        list.add(999);                  // 可以存储任何类型数据，但是存在装箱拆箱的性能损耗。
        list.add("abc");
        // int x = list.get(1); <-- 报错，容易因为类型匹配错误而导致的InvalidCastException
    }

    static void testGenericArray() {
        // 定义泛型ArrayList对象，listGeneric中存的每个元素类型都是指定的类型，不再需要担心装箱拆箱
        List<Integer> listGeneric = new ArrayList<>(10);
        listGeneric.add(9);
        // listGeneric.add("abc"); <-- 显示编译错误，避免错误隐藏在运行时

        // Java不允许定义泛型类的数组对象。例如new List<String>[10]
        // List<String>[] a = new List<String>[10]; <-- 显示"generic array creation"，意思是你试图定义Java禁止的泛型数组。

        // 但Java允许定义泛型类的数组变量，并让它指向一个非泛型类（即数组成员全是Object类型的）数组。
        // 例如下面这行代码中的List<String>[] b就是一个泛型类数组变量
        // 之所以编译时会提示Warning，是因为这么赋值不安全。
        // 由于数组对象每个元素都是Object类，而数组变量却是Object类的子类，因此不论是先cast（b）还是直接使用（c）都有ClassCastException的可能。
        List<String>[] b = (List<String>[]) new ArrayList[10];
        List<String>[] c = new ArrayList[10];
    }

    private static <T> void print(T info) {
        System.out.println(info);
    }

    /** int -> String: 除10提取法 */
    // 从低位向高位依次提取每一位的数值。%10用于提取当前最低一位，/10用于砍掉当前最低一位。
    static String int2str(int x) {
        StringBuilder sb = new StringBuilder();
        while (x > 0) {
            int digit = x % 10;
            x = x / 10;
            sb.insert(0, digit);
        }
        return sb.toString();
    }

    /** String -> int: 乘10叠加法 */
    // 从低位向高位扫描，上次的sum乘10加上当前位的数值。
    static int str2int(String x) {
        int sum = 0;
        for (int i = 0; i < x.length(); i++) {
            int val = x.charAt(i) - '0';
            sum = sum * 10 + val;
        }
        return sum;
    }

    static void testAsList() {
        /** 使用asList创建List<>对象的正确和错误方式 */
        // Wrong: 这样等效于尝试创建List<int[]>，但是List只能存对象，所以非法。
        int[] a = {1, 2, 3, 4, 5};
        // List<Integer> l1 = Arrays.asList(a);
        // List<Integer> l2 = Arrays.asList(new int[] {1, 2, 3, 4, 5});

        // Correct：对于基础类型数组，需要先创建包装类Integer的数组再转化为List<Integer>。
        Integer[] b = {1, 2, 3, 4, 5};
        List<Integer> int1 = Arrays.asList(b);
        List<Integer> int2 = Arrays.asList(new Integer[] {1, 2, 3, 4, 5});

        // Correct：或者直接列出数组所有元素，不用大括号
        List<Integer> int3 = Arrays.asList(1, 2, 3, 4, 5);

        // Correct：对于对象数组则直接从String[]转为List<String>，没有任何问题。
        String[] c = {"adf", "guu", "wer"};
        List<String> str = Arrays.asList(c);

        /** 修改asList创建的List对象的正确和错误方式 */
        // Wrong: 任何试图删除或添加元素的操作都会抛出UnsupportedOperationException，虽然你操作的是List，但实际上他只是一个array。
        // int3.add(9); (UnsupportedOperationException)
        // int3.remove(0); (UnsupportedOperationException)
        // str.add("shit"); (UnsupportedOperationException)

        // Correct：任何操作都是对原数组的操作。因此修改List也会同时修改原数组。
        System.out.println(c[0]);
        str.set(0, "shit");
        System.out.println(c[0]);

        // 可以摆脱上面一切烦恼的解决方法：创建一个真正的List。
        Integer[] d = {1, 2, 3, 4, 5};
        List<Integer> real = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> real2 = new ArrayList<>(Arrays.asList(d));
        real.add(9);        // 合法，因为real是一个全功能的list
        real.remove(0);     // 合法
        real2.set(0, 1000);
        System.out.println(d[0] == 1); // 即使修改list也不会影响原数组了。两者之间没有任何关系。
    }

}
