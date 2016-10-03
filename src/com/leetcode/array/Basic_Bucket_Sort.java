package com.leetcode.array;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Michael on 2016/10/2.
 *
 * Basic Algorithm: Bucket Sort, aka. Bin Sort
 * 适用条件：数组元素值均匀分布在一个区间内 (Uniformly distributed)
 * Time - o(n), worse o(n^2)
 * Space - o(n)
 *
 */
public class Basic_Bucket_Sort {
    public static void main(String[] args) {
        float[] a = {1, 7, -88, 44, 22, 77};
        BucketSort2(a, 100);
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
    // 桶的个数越多，每个bucket里面装的元素个数就会越少，当桶的个数多到使得每个桶里面只有相同值元素的时候，其实此时的Bucket Sort就是Counting Sort（只不过实现方式更复杂些）
    // 桶的个数越少，每个bucket里面装的元素个数就会越多，当只有1个桶的时候，此时的Bucket Sort就等效于其内部对每个桶排序的那个排序算法。
    // 比如只有一个桶，且桶内部使用插入排序，则桶排序的性能就是插入排序的性能。这就是为什么说Bucket Sort的Worse Time - o(n^2)
    //
    /** 难点2：如何计算每个元素应该放在哪个桶里 */
    // 对于[0, max]取值范围的数组，计算元素所属桶的索引号 = 元素值 / 区间长度 = 元素值 / (取值范围 / 桶个数) = 元素值 * 桶个数 / 取值范围
    // 对于取值范围为实数的数组，桶索引号计算需要偏移，即: (a[i] - min) * n / (max - min + 1)
    // 特别的，如果使用链表来实现桶，可以将第二和第三步合并为一步，无需插入排序，加入桶中的合适位置仅需要o(1)
    // 由于这里的桶必须具备动态扩容的功能（因为一般并不知道取值分布情况），因此不可避免的需要使用ArrayList或者LinkedList来实现桶。

    // 支持对包含小数或负数的数组排序，bucket使用List<List<Float>>实现
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

    // 与上面的解法几乎完全一样，只是bucket使用泛型数组实现（被Java所禁止的一种方式），且插入排序自己用LinkedList在放入桶的同时就完成。（第二步和第三步结合在一起）
    static void BucketSort2(float[] a, int n) {

        // 1. 初始化桶
        // 根据Effective Java里面介绍的方法避免Generic Array Creation编译错误
        // 我需要的是一个每个元素都是指向一个独特ArrayList的数组，但是Java并不允许直接定义这种数组，
        // 因此需要先定义一个装满Object类型元素的数组（因为这么做并不违反Java的规定）
        // Java只禁止定义泛型类的数组，但是并不禁止你定义泛型类的数组引用变量，因此我们再定义一个我们想要的泛型类数组引用变量，
        // 然后加强制类型转换，让这个引用变量指向这个原本是Object类型的数组，就曲线的完成了“定义泛型类数组”的目的。
        // 可以看到，其实在这里使用泛型数组没有任何好处，因为即使定义泛型数组，还是需要把每个元素都初始化一遍。
        // 一般非用数组而不用List的情况是因为数组一旦定义好长度那么所有元素就都已经初始化完毕了，比List初始化完之后长度为0要方便很多
        // 但是要知道这仅限于数组元素是基础类型，对于装满了list引用变量的数组，依然需要和List<List<>>一样遍历所有元素以初始化。
        @SuppressWarnings("unchecked")
        List<Float>[] bucketlist = (List<Float>[]) new LinkedList[n];
        for (int i = 0; i < n; i++)
            bucketlist[i] = new LinkedList<>();

        // 1. 确定取值范围
        float max = a[0];
        float min = a[0];
        for (float x : a) {
            max = Math.max(x, max);
            min = Math.min(x, min);
        }

        // 2 & 3. 将元素放入合适的桶，放入桶的时候就确保有序的插入
        for (float x : a) {
            int bucketID = (int) ((x - min) * n / (max - min + 1));
            int i = 0;
            while (bucketlist[bucketID].size() != 0 && bucketlist[bucketID].get(i) < x) i++;    // 插入位置是有序的
            bucketlist[bucketID].add(i, x);
        }

        // 4. 合并所有的非空桶
        int idx = 0;
        for (List<Float> bucket : bucketlist) {
            for (float x : bucket) {
                a[idx++] = x;
            }
        }
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
