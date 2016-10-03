package com.leetcode.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michael on 2016/10/2.
 *
 * Basic Algorithm: Bucket Sort, aka. Bin Sort
 * 适用条件：数组元素值均匀分布在一个区间内 (Uniformly distributed)
 */
public class Basic_Bucket_Sort {
    public static void main(String[] args) {
        float[] a = {-6, 4, -3, 2, -7, 4, 1, 9};
        BucketSort(a, 8);
        System.out.println(Arrays.toString(a));
        bulkTest();
    }


    // Bucket Sort的核心思路
    // 第一步：确定桶个数，确定原数组取值范围，初始化所有桶
    // 第二步：将原数组的每个元素放入所属的桶中
    // 第三步：每个桶内使用插入排序
    // 第四步：将所有的桶内元素顺序连结，得到已排序数组。
    //
    /** 难点1：到底应该用多少个桶来排序 */
    // 桶的个数是有上限的，桶的个数不能大于待排序数组的长度，因为这样会大概率导致每个桶的取值区间小于1
    //
    /** 难点2：如何计算每个元素应该放在哪个桶里 */
    // 对于[0, max]取值范围的数组，计算元素所属桶的索引号 = 元素值 / 区间长度 = 元素值 / (取值范围 / 桶个数) = 元素值 * 桶个数 / 取值范围
    // 对于取值范围为实数的数组，桶索引号计算需要偏移，即: (a[i] - min) * n / (max - min + 1)
    // 特别的，如果使用链表来实现桶，可以将第二和第三步合并为一步，无需插入排序，加入桶中的合适位置仅需要o(1)
    // 由于这里的桶必须具备动态扩容的功能（因为一般并不知道取值分布情况），因此不可避免的需要使用ArrayList或者LinkedList来实现桶。


    static void BucketSort(float[] a, int bucketCount) {
        // 1. 初始化桶
        List<List<Float>> bucketlist = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++)
            bucketlist.add(new ArrayList<>());

        // 1. 确定取值范围
        float max = a[0];
        float min = a[0];
        for (int i = 0; i < a.length; i++) {
            max = Math.max(max, a[i]);
            min = Math.min(min, a[i]);
        }

        // 2. 将元素放入所属区间对应的桶
        for (int i = 0; i < a.length; i++) {
            int bucketID = (int)((a[i] - min) * bucketCount / (max - min + 1));
            bucketlist.get(bucketID).add(a[i]);
        }

        // 3 & 4. 将每个bucket内的元素使用别的排序方式进行排序，并连结后输出
        int idx = 0;
        for (int i = 0; i < bucketCount; i++) {
            Collections.sort(bucketlist.get(i));
            for (int j = 0; j < bucketlist.get(i).size(); j++) {
                a[idx++] = bucketlist.get(i).get(j);
            }
        }
    }

    // 支持对包含小数或负数的数组排序，使用了泛型数组定义（被Java所禁止的一种方式）
    static void BucketSort2(float[] a, int n) {
        // 根据Effective Java里面介绍的方法避免Generic Array Creation编译错误
        // 我需要的是一个每个元素都是指向一个独特ArrayList的数组，但是Java并不允许直接定义这种数组，
        // 因此需要先定义一个装满Object类型元素的数组（因为这么做并不违反Java的规定）
        // Java只禁止定义泛型类的数组，但是并不禁止你定义泛型类的数组引用变量，因此我们再定义一个我们想要的泛型类数组引用变量，
        // 然后加强制类型转换，让这个引用变量指向这个原本是Object类型的数组，就曲线的完成了“定义泛型类数组”的目的。
        @SuppressWarnings("unchecked")
        List<Float>[] bucketlist = (List<Float>[]) new ArrayList[n];

        float max = a[0];
        float min = a[0];
        for (float x : a) {
            max = Math.max(x, max);
            min = Math.min(x, min);
        }

        for (int i = 0; i < n; i++)
            bucketlist[i] = new ArrayList<>();

        for (float x : a) {
            int bucketID = (int) ((x - min) * n / (max - min + 1));
            bucketlist[bucketID].add(x);
        }

        int idx = 0;
        for (List<Float> bucket : bucketlist) {
            Collections.sort(bucket);
            for (float x : bucket) {
                a[idx++] = x;
            }
        }
    }

    // [88, 99, 43, 41, 54, 1, 12, 10]
    // Length = 8; max = 99, min = 1, range = 99
    // Bucket Size = range / length = 99 / 8 = 12
    // Determine which bucket does a element belong = (value - min) * 8 / 99 = [7, 7, 3, 3, 4, 0, 0, 0, 0]
    static void BucketSort2(int[] a) {
        if (a == null || a.length < 2) return;
        int max = a[0];
        int min = a[0];
        for (int x : a) {
            if (x > max) max = x;
            if (x < min) min = x;
        }
//        List[] list = new List[max - min + 1];
//        for (int x : a) {
//            list[x].add()
//        }
    }

    public static void bulkTest() {
        for (int j = 2; j < 50; j += 2) {
            for (int i = 1; i < 50; i++) {
                float[] x = randGen(i, j);
                System.out.println("Original: " + Arrays.toString(x));
                BucketSort(x, x.length);
                if (!isSorted(x)) {
                    System.out.println("Failed at: " + Arrays.toString(x));
                    return;
                }
                else System.out.println("Passed at: " + Arrays.toString(x));
            }
        }
        System.out.println("Passed.");
    }

    public static boolean isSorted(float[] a) {
        if (a == null || a.length < 2) return true;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }

    private static float[] randGen(int len, int range) {
        float[] a = new float[len];
        for (int i = 0; i < a.length; i++)
            a[i] = (float) (range * Math.random());
        return a;
    }

}
