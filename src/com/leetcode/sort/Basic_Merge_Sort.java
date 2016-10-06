package com.leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/27.
 *
 * Basic Algorithm: Merge Sort
 * Time: o(n * log n) 划分的过程是二分法，因此为logn，合并的过程为n，因此整体时间复杂度是n＊logn.
 * Space: o(n) 因为简单的merge的过程需要临时数组，至于o(1)解法也是可以有的，只不过太过复杂。详细分析见下。
 *
 */
public class Basic_Merge_Sort {
    public static void main(String[] args) {
        int[] a = {0, 2, 1, 0};
        MergeSort_Recursive(a);
        //MergeSort_Iterative(a);
        System.out.println(Arrays.toString(a));
        bulkTest();
    }

    /** 迭代解法 - 直接合并 */
    // 直接合并 - BottomUp Iterative：相当于省略了递归方法不断除二分解将数组分段的过程，
    // 直接指定最小比较区段就是1，1比完了比2，2比完了比3，一直比到数组长度N
    // 外循环控制区段长度，内循环用区段长度扫描并按区段长度merge整个数组
    // 难点: 在于如何处理长度不是2的幂的数组的merge
    // 将i设计为merge两个区段各自的长度，而不是总长度，
    // 然后将j跳跃的间隔设计为2倍的i，可以确保mid的值不会越界，再用Math.min确保right的值也不越界
    // len = 9:
    // 0 1 | 2 3 | 4 5 | 6 7 | 8
    // 0 1 2 3 | 4 5 6 7 | 8
    // 0 1 2 3 4 5 6 7 | 8
    // len = 6:
    // 0 1 | 2 3 | 4 5
    // 0 1 2 3 | 4 5
    static void MergeSort_Iterative(int[] a) {
        for (int i = 1; i < a.length; i *= 2)
            for (int j = 0; j < a.length - i; j += 2 * i)
                merge3(a, j, j + i - 1, Math.min(j + 2 * i - 1, a.length - 1));
    }

    /** 递归解法 - 先分解再合并 */
    static void MergeSort_Recursive(int[] a) {
        MergeSortRecursive(a, 0, a.length - 1);
    }

    // 先分解再合并 - Topdown Recursive: 逆序递归，即先递归至终点，再在返回的过程中进行Conquer
    static void MergeSortRecursive(int[] a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            MergeSortRecursive(a, left, mid);
            MergeSortRecursive(a, mid + 1, right);
            merge3(a, left, mid, right);
        }
    }

    // 使用ArrayList实现merge功能，双指针同时扫描两段，并存入ArrayList，最后由ArrayList覆盖当前区间
    // 用极大值保护先扫描入库完成的那个部分，直至左右两部分都扫完。
    // 算法导论里介绍的方法是先把左右两段元素备份至一个临时数组，然后用双指针扫描更新原数组，
    // 下面的解法则是直接双指针扫描数组两段，存入临时数组，然后顺序扫描更新现有数组，本质上是一样的，不过感觉我的方法写起来更简单些
    // 需要注意的一点是，这里的left / mid / right索引都是inclusive的，因此在调用的时候应该调用MergeSort(a, 0, a.length - 1)
    static void merge(int[] a, int left, int mid, int right) {
        List<Integer> list = new ArrayList<>(right - left + 1);
        int i = left;
        int j = mid + 1;
        while (i <= mid || j <= right) {
            int x, y;
            if (i <= mid) x = a[i];
            else x = Integer.MAX_VALUE;
            if (j <= right) y = a[j];
            else y = Integer.MAX_VALUE;
            list.add(Math.min(x, y));
            if (x <= y) i++;
            else j++;
        }
        int idx = left;
        for (int element : list) {
            a[idx++] = element;
        }
    }

    /** In-place Merge 非常有意思的研究 */
    // 单独对上面的merge方法进行复杂度分析：Time - o(n + m), Space - o(n + m), 这里n表示左半部长度，m表示右半部长度。
    // 可以看到，上面的merge方法的时间复杂度已经最优（即左右半部所有元素扫描完就合并完成了，不可能比这个复杂度更低了，因为你必须扫描每个元素，不可能不扫描人家就自动排好。）
    // 但是，上面merge方法的空间的复杂度是o(n + m)，依然需要额外的空间才能完成，是否可以优化至o(1)即Constant Space呢。这是个非常有意思的问题。
    // 即问题是：是否可以In-place的进行数组合并呢？
    // 下面就提供一个In-place的数组合并解法，不过需要注意的是，为了做到In-place Merge，我们不得不提高了时间复杂度至o(n * m)...
    // 哈哈哈哈...这就是典型的<Time / Space Trade-off>，要不用时间复杂度换空间复杂度，要不然反过来。
    // 那么新问题是：是否可以Constant Space且Linear Time的进行数组合并呢？
    // 其实这个问题到现在还在研究中，可以肯定的是的确存在这种解法，只不过达到这个标准的解法通常都太过复杂，以至于一般人不可能在面试时临时创造出来。

    /** 上面问题的变体：原位的将两个独立的数组进行整体排序：Time - o(n^2), Space - o(1) */
    // 将问题抽象为对两个独立的数组进行merge，使得merge完成后a连接b也是已排序的
    // a = {1, 5, 9, 10, 15, 20}
    // b = {2, 3, 8, 13}
    // 核心思路在于需要在比对两个数组的同时，确保两个数组剩余未扫描部分的元素依然是有序的。
    // 为了确保这一点，需要将两者比对的落选者插入到对方数组的合适部分。所以实际上就是插入排序的过程。
    static void merge2(int[] a, int[] b) {
        int i = a.length - 1;
        int j = b.length - 1;
        while (j >= 0) {
            if (a[i] > b[j]) {
                int x, temp = b[j];
                b[j] = a[i];
                for (x = i - 1; x >= 0 && a[x] > temp; x--)
                    a[x + 1] = a[x];
                a[x + 1] = temp;
            }
            j--;
        }
    }

    /** In-place的Merge方法：对给定区间的数组左右两部分进行Merge，Time - o(n^2), Space - o(1)的Merge方法实现 */
    // 在merge2()方法的基础上修改，得到了可以用与Merge Sort的原位merge方法
    // 需要注意j和x的下限应该相应的改为mid + 1和left。
    static void merge3(int[] a, int left, int mid, int right) {
        int i = mid;
        int j = right;
        while (j >= mid + 1) {
            if (a[i] > a[j]) {
                int x, temp = a[j];
                a[j] = a[i];
                for (x = i - 1; x >= left && a[x] > temp; x--)
                    a[x + 1] = a[x];
                a[x + 1] = temp;
            }
            j--;
        }
    }

    public static void bulkTest() {
        for (int j = 2; j < 1000; j += 2) {
            for (int i = 1; i < 100; i++) {
                int[] x = randGen(i, j);
                System.out.println("Original: " + Arrays.toString(x));
                MergeSort_Iterative(x);
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
