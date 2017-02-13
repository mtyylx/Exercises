package com.leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/27.
 *
 * Basic Algorithm: Merge Sort
 *
 * <For Array>
 * - Time: o(n * log n) 划分的过程是二分法，因此为logn，合并的过程为n，因此整体时间复杂度是n＊logn.
 * - Space: o(n)        对于数组，归并排序的合并部分操作如果想要在o(n)时间内完成，必须用额外空间。
 *
 * <For Linked List>
 * - Time: o(n * log n)
 * - Space: o(1)        对于链表，归并排序的合并部分操作可以在o(1)n内完成。
 *
 * <Tags>
 * - 二分法：将问题规模分解，使得时间复杂度从 n * n 降至 n * logn.
 * - 递归：利用逆序递归对数组进行分解
 * - Sentinel卫兵：同步扫描两个序列时经常用到
 *
 */
public class Basic_Merge_Sort {
    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 5, 2, 4, 1, 6};
        MergeSort_Iterative(arr);
        System.out.println(Arrays.toString(arr));

        int[] arr2 = {9, 8, 7, 5, 2, 4, 1, 6};
        MergeSort_Recursive(arr2);
        System.out.println(Arrays.toString(arr2));
    }

    // 解法1和解法2分别使用递归和迭代的方式对数组进行分解。
    // 解法1和解法2使用相同的合并方式对分解的数组区间进行合并。

    /** 解法2：<分解过程>用迭代实现，<合并过程>同样需要额外空间。Time - o(nlogn), Space - o(n). */
    // 与解法1共享合并过程的算法，唯一区别在于分解的过程是直接访问的，省略了递归方法不断除二分解将数组分段的过程。
    // Bottom-Up Iterative：直接指定最小比较区段就是1，1比完了比2，2比完了比4，一直比到数组长度N
    /** 循环指针含义 */
    // 记住内外循环的目的是为了构造正确的left, mid, right值给merge方法使用，而不是直接当场扫描并合并
    // 外循环i表示每次划分的<左右分区各自宽度>，应为1, 2, 4, 8, 16...，因此有：i *= 2
    // 内循环j表示每次划分的<左分区起始位置>，j自增间隔等于两个i（即左右区间长度之和），因此有：j += 2 * i
    // 在i=1时应为0, 2, 4, 6, 8...
    // 在i=2时应为0, 4, 8, 12, 16...
    // 在i=3时应为0, 8, 16, 24, 32...
    // 在i=4时应为0, 16, 32, 48, 64...
    /** merge调用方式 */
    // left = j; 因为j就表示做分区起始位置
    // mid = j + i - 1; 因为起始位置j加上分区宽度i就是中点，减一就是左分区的终止位置
    // right = j + 2 * i - 1; 因为起始位置加上左右分区的宽度2*i就是总长度，减一就是右分区的终止位置
    // 还要考虑到对于长度不是2的幂的数组，需要避免mid和right越界，因此在使用前需要和a.length-1进行比较，取小的那个。
    /** 举例 */
    // {9, 8, 7, 4, 5}
    // Merge(0, 0, 1): 9 8 -> 8 9
    // Merge(2, 2, 3): 7 4 -> 4 7
    // Merge(4, 4, 4): 5, do nothing. 如果没有Math.min保护，原本应是(4, 4, 5), right会越界。
    // Merge(0, 1, 3): 8 9 4 7 -> 4 7 8 9
    // Merge(4, 4, 4): 5, do nothing. 如果没有Math.min保护，原本应是(4, 5, 7), mid和right都会越界。
    // Merge(0, 3, 4): 4 7 8 9 5 -> 4 5 7 8 9
    // {4, 5, 7, 8, 9}
    static void MergeSort_Iterative(int[] a) {
        for (int i = 1; i < a.length; i *= 2)
            for (int j = 0; j < a.length; j += 2 * i)
                mergeX(a, j, Math.min(j + i - 1, a.length - 1), Math.min(j + 2 * i - 1, a.length - 1));
    }
    // 完全相同的合并过程
    static void mergeX(int[] a, int left, int mid, int right) {
        List<Integer> list = new ArrayList<>(right - left + 1);
        int i = left;
        int j = mid + 1;
        while (i <= mid || j <= right) {
            int x = i <= mid ? a[i] : Integer.MAX_VALUE;
            int y = j <= right ? a[j] : Integer.MAX_VALUE;
            list.add(Math.min(x, y));
            if (x < y) i++;
            else j++;
        }
        int idx = left;
        for (int element : list) {
            a[idx++] = element;
        }
    }

    /** 解法1：<分解过程>用递归实现，<合并过程>需要使用额外空间。Time - o(nlogn), Space - o(n). */
    // Top-down Recursive: 逆序递归，即先递归至终点，再在返回的过程中进行Conquer
    // 用三层结构实现：
    // 1. 最外层：Wrapper
    // 2. 中间层：将数组按二分法进行划分至最小单位（逆序递归中的“先递归”）
    // 3. 最内层：将划分完的数组区间按顺序进行合并（逆序递归中的“后干活”）
    // 从整体可以看到实际上这里递归的唯一目的就是对数组区间进行了划分，划分完了之后就可以依次合并排序了。
    // 使用ArrayList实现merge功能，双指针同时扫描两段，并存入ArrayList，最后由ArrayList覆盖当前区间
    // 使用卫兵：用极大值保护先扫完的部分，直至左右两部分都扫完。
    // 左中右left / mid / right三个指针索引都是inclusive的，因此在调用的时候应该调用MergeSort(a, 0, a.length - 1)
    // 算法导论里介绍的方法是先把左右两段元素备份至一个临时数组，然后用双指针扫描更新原数组，
    // 下面的解法则是直接双指针扫描数组两段，存入临时数组，然后顺序扫描更新现有数组，本质上是一样的，不过感觉我的方法写起来更简单些
    static void MergeSort_Recursive(int[] a) {                              // 最外层：Wrapper
        split(a, 0, a.length - 1);
    }

    static void split(int[] a, int left, int right) {                       // 中间层：二分法划分数组
        if (left < right) {
            int mid = (left + right) / 2;
            split(a, left, mid);
            split(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    static void merge(int[] a, int left, int mid, int right) {              // 最内层：对已划分区间进行合并
        List<Integer> list = new ArrayList<>(right - left + 1);
        int i = left;
        int j = mid + 1;
        while (i <= mid || j <= right) {
            int x = i <= mid ? a[i] : Integer.MAX_VALUE;                    // 使用卫兵
            int y = j <= right ? a[j] : Integer.MAX_VALUE;                  // 使用卫兵
            list.add(Math.min(x, y));
            if (x < y) i++;
            else j++;
        }
        int idx = left;
        for (int element : list) {                                          // 拷贝回原数组
            a[idx++] = element;
        }
    }

    /** 扩展问题：对于数组如何做到o(1)空间复杂度的合并过程？ */
    // 上面的两种解法又是递归又是迭代，看上去很不同，但是实际上用的是相同的合并算法(merge方法)，而该合并算法的空间复杂度是o(n)。
    // 那么问题就来了，如何做到o(1)空间复杂度的合并呢？
    // 可以看到其时间复杂度已经最优，即左右部分的所有元素扫描完就合并完成了，不可能比这个复杂度更低了，因为你必须扫描每个元素，不可能不扫描人家就自动排好。
    // 但是其空间复杂度是o(n + m)，依然需要额外的空间才能完成，是否可以优化至o(1)即Constant Space呢。这是个非常有意思的问题。
    // 因此进一步的问题是：是否可以In-place的进行数组合并呢？
    // 下面就提供一个In-place的数组合并解法，不过需要注意的是，为了做到In-place Merge，我们不得不提高了时间复杂度至o(n * m)
    // 这就是典型的<Time / Space Trade-off>，要不然用时间复杂度换空间复杂度，要不然用空间换时间，你终归需要付出才有回报。
    // 那么新问题是：是否可以Constant Space且Linear Time的进行数组合并呢？
    // 答案是肯定存在，只不过达到这个标准的解法通常都太过复杂，以至于一般人不可能在面试时临时创造出来。

    /** 首先解决：对<两个独立数组>进行In-place排序：Time - o(n^2), Space - o(1) */
    // 其本质是插入排序。
    // 将问题抽象为对两个独立的数组进行merge，使得merge完成后a连接b也是已排序的
    // a = {1, 5, 9, 10, 15, 20}
    // b = {2, 3, 8, 13}
    // 核心思路在于需要在比对两个数组的同时，确保两个数组剩余未扫描部分的元素依然是有序的。
    // 为了确保这一点，需要将两者比对的落选者插入到对方数组的合适部分。所以实际上就是插入排序的过程。
    static void merge_inplace(int[] a, int[] b) {
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

    /** 然后解决：对<一个数组指定区间内的左右两部分>进行In-place排序：Time - o(n^2), Space - o(1) */
    // 在merge2()方法的基础上修改，得到了可以用与Merge Sort的原位merge方法
    // 需要注意j和x的下限应该相应的改为mid + 1和left。
    static void merge_inplace2(int[] a, int left, int mid, int right) {
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
}
