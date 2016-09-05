package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYuan on 2016/9/5.
 *
 * Summarize the basic skills for manipulating array and ArrayList
 *
 */
public class Basic_Array_Skills {
    public static void main(String[] args) {
        test1();
    }

    static void test1 () {
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

        // 定义接口类型的数组变量，指向实现接口的类对象作为元素的数组。
        List[] listArray = new ArrayList[10];
        print(listArray[9]);            // 可以访问，因为这是一个数组。数组初始化以后所有元素立即可用。
        listArray[9] = new ArrayList<>();

        // 定义ArrayList对象，list中存的每一个元素都是Object，向list中add的任何类型数据都会被装箱为Object
        List list = new ArrayList(10);  // 10只是capacity，size依然是0
//        list.get(0);                    // 报错，数组越界（因为list对象目前size = 0）
        list.add(0);                    // 可以存储任何类型数据，但是存在装箱拆箱的耗费
        list.add("abc");
        list.add(new ArrayList(10));

        // 定义泛型ArrayList对象，listGeneric中存的每个元素类型都是指定的类型，不再需要担心装箱拆箱
        List<Integer> listGeneric = new ArrayList<>(10);
        listGeneric.add(9);
//        listGeneric.add("abc");     // 直接可看到编译错误，避免错误隐藏在运行时

        // Java不允许定义泛型类的数组对象。例如new List<String>[10]
        // 下面这行代码编译会报错：”generic array creation“
//        List<String>[] a = new List<String>[10];

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


}
