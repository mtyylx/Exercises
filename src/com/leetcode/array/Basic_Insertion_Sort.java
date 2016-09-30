package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/9/13.
 *
 * Basic Algorithm: Insertion Sort
 * Time - o(n^2)
 * Space - o(1)
 */
public class Basic_Insertion_Sort {
    public static void main(String[] args) {
        int[] a = {2, 1, 2};
        InsertionSort(a);
        System.out.println(Arrays.toString(a));
        bulkTest();
    }

    /** 基本思路：将未排序元素依次插入已排序区间的合适位置。用平移。*/
    // 作为对比，选择排序的基本思路是：选出未排序区间的最大或最小元素放在已排序区间的头或尾。用交换。
    // 难点1：指针该指向谁，内外循环该如何设计。
    // 外循环指针扫描未排序元素。
    // 内循环指针扫描已排序元素。
    // 内外循环的扫描方向相反，互相远离。
    // 如果是顺序扫描，起始状态应该是外循环指针指向第0个元素，内循环指针指向不存在的第-1个元素。这样代码可以隐式处理长度为1的数组排序。
    // 每个外循环首先缓存当前的未排序元素（其实就是未排序区间的第一个元素），然后用内循环将该元素插入已排序区间的合适位置。
    // sorted, sorted, sorted, unsorted, unsorted, unsorted, ...
    //           <---- ^^in^^ | ^^out^^ ---->
    // 这里的插入实际上利用了已排序数组的性质：已排序数组的最后一个元素一定是区间内最大的元素，
    // 因此，如果这个区间内最大的元素a[j]已经小于了缓存的未排序元素current，未排序元素一定大于已排序区间的所有元素，因此根本不用插入。
    // 另外，在内循环指针逆向扫描的过程中只要发现已排序元素a[j]不再小于未排序元素current，就说明已排序元素的右侧就是该插入的位置，
    // 因此上述的情况下都需要立即退出内循环，把缓存的未排序元素插入当前已排序元素的右侧即可。
    // 上述情况可以写在for循环的循环条件中：j >= 0 && a[j] > current
    // 也可以用if和break实现：if (a[j] > current) { a[j + 1] = a[j]; } else break;

    // 标准写法
    // 注意内循环指针需要在外部定义，因为退出内循环后该指针还有用，它指示了该插入元素的位置。
    static void InsertionSort(int[] a) {
        int i, j, current;
        for (i = 0; i < a.length; i++){
            current = a[i];
            for (j = i - 1; j >= 0 && a[j] > current; j--) a[j + 1] = a[j];
            a[j + 1] = current;     // 在当前位置的右邻侧(j+1)插入未排序元素
        }
    }

    // 稍微修改一下写法
    static void InsertionSort2(int[] a) {
        int i, j, current;
        for (i = 0; i < a.length; i++) {
            current = a[i];
            for (j = i - 1; j >= 0; j--) {
                if (a[j] > current) a[j + 1] = a[j];    // 写法改成if-else-break
                else break;
            }
            a[j + 1] = current;
        }
    }

    // 反过来写
    // 逆序扫描，从数组尾部开始逆序扫描未排序数组并插入已排序数组，形如：
    // unsorted, unsorted, unsorted, sorted, sorted, sorted, ...
    //               <---- ^^out^^ | ^^in^^ ---->
    static void InsertionSort3(int[] a) {
        int i, j, current;
        for (i = a.length - 1; i >= 0; i--) {
            current = a[i];
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < current) a[j - 1] = a[j];
                else break;
            }
            a[j - 1] = current;     // 在当前已排序元素的左邻侧插入未排序元素。
        }
    }

    public static void bulkTest() {
        for (int j = 2; j < 100; j += 2) {
            for (int i = 1; i < 100; i++) {
                int[] x = randGen(i, j);
                System.out.println("Original: " + Arrays.toString(x));
                InsertionSort3(x);
                if (!isSorted(x)) {
                    System.out.println("Failed at: " + Arrays.toString(x));
                    return;
                }
                else System.out.println("Passed at: " + Arrays.toString(x));
            }
        }
        System.out.println("Passed.");
    }

    public static boolean isSorted(int[] a) {
        if (a == null || a.length < 2) return true;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }

    private static int[] randGen(int len, int range) {
        int[] a = new int[len];
        for (int i = 0; i < a.length; i++)
            a[i] = (int) (range * Math.random());
        return a;
    }

}
