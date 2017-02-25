package com.leetcode.sort;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import java.util.*;

/**
 * Created by Michael on 2016/9/28.
 *
 * Basic Algorithm: Quick Sort
 * Time - o(n * log n), worst o(n^2)
 * Space - o(logn)
 * 快排的空间复杂度始终是o(logn)，这是因为快排必须要使用额外的空间存储每次分区的分界点位置。
 * 如果使用递归实现，这个分界点位置被隐式的使用在函数堆栈上，
 * 如果使用迭代实现，则分界点位置必须使用栈自己存储起来，因此不管使用递归还是迭代，快排都需要o(logn)额外空间，无法做到o(1)。
 */
public class Basic_Quick_Sort extends SortMethod {
    public static void main(String[] args) {
        int[] a = {1, 0, 1};
        QuickSort_Iterative(a);
        QuickSort_Randomized(a);
        System.out.println(Arrays.toString(a));

        // Bulk Test
        SortUtility.VerifySortAlgorithm("quick");
        SortUtility.TestPerformance("heap", 100000);
    }

    // 基本思路是Top-down，不断的分解问题，在分解的过程中原位的对元素顺序进行调换，分解完成时（即递归到头时），整个数组已经完全有序，任务也就完成了，
    // 写成递归的话，是正序递归。先选基准元素，然后双指针同时首尾扫描，互换位置不对的元素，
    // 直至双指针相遇，这时候start至end区域就被划分成了两个部分，左侧的全小于pivot，右侧的全大于pivot，再对划分后的元素进行继续划分，直至待分区数组长度为1。
    /** 难点1：如何交换左右分区中的不合群元素 */
    // 一开始的思维定势是，既想原位修改，又认为一定要让pivot元素本身处于左右分解点上，但是发现怎么交换或插入元素代码都不好写
    // 解决方法：直接把pivot的值缓存，然后使用while一直扫描至XXX的方式首尾先后定位要交换的元素，即：
    // 1. while (a[left] < pivot) left++; 这句话运行完之后，left一定停在了不小于pivot的元素
    // 2. while (a[right] > pivot) right--; 这句话运行完之后，right一定也停在了不大于pivot的元素
    // 3. Swap (a[left], a[right]) 交换这两个元素
    // 这样可以确保left指针左侧区间[start, left - 1]的所有元素都应该小于pivot，而且指针会停止在移动过程中遇到的第一个不小于pivot的元素（也包括pivot自身）
    // 并同时确保right指针右侧区间[right + 1, end]的所有元素都应该大于等于pivot，而且指针会停止在移动过程中遇到的第一个不大于pivot的元素
    // 这里有一个细节，由于pivot默认取在[start, end]的左端点，因此left一开始一定会停留在pivot上（因为pivot并不小于pivot），并且pivot一定最后会被交换至right右侧区间，
    // 这也就是为什么说right右侧区间一定都是大于或等于pivot的元素。如果反过来pivot取在整个区间的右端点，那么就变成left左侧区间全是小于或等于pivot的元素了。
    /** 难点2：如何控制递归结束，避免出现死循环或堆栈溢出 */
    // 快排如果写成一个递归方法的话，只需要在一开始判断start是否小于end即可 {if (start >= end) return; }，确保即将排序的数组长度大于2才进行分段。
    // 快排如果写成两个递归方法（一个负责分区，一个负责递归），由于分区方法必须返回一个分区点位置，即使发现应该终止递归，也无法做到同时让左右两段分区都结束(因为返回值不可能既小于start又大于end)
    // 不能像一个递归方法那样直接终止递归，因此需要把终止递归的条件写在递归方法里，即获得分区点位置后，判断start和end与分区点的位置是否合理，再进行递归。
    /** 难点3：如何处理left和right相遇或交叉情况的收尾工作。*/
    // 这是我折腾了一天都没有找到明确规律的，主要问题在于出错的可能性种类繁多，光靠自己想很难都考虑到，于是我的对策就是生成大量的测试队列并检验
    // 为了确保我的算法rock solid，我不得不单独写了一个判定字符串是否排序成功的方法，以及一个生成任意长度任意值范围的数组生成方法。
    // 然后我就发现，pivot选在数组开始还是结尾，可以决定快排分区后分区点应该选right还是left。如果选的不对，就会出错。
    // 如果pivot = a[start]，则区间应该是[start, right] 和 [right + 1, end]
    // 如果pivot = a[end]，则区间应该是[start, left - 1] 和 [left, end]

    /** 递归写法1：pivot选在首个元素，使用right作为分区点 */
    static void QuickSort1(int[] a, int start, int end) {
        if (start >= end) return;       // 必须的递归终止条件，只要分区长度为1或更小，就不做相向扫描和进一步的递归。
        int left = start;
        int right = end;
        int pivot = a[start];           // 选择打头元素作为基准，双指针相向扫描完成后，pivot一定在右侧分区
        while (true) {
            while (a[left] < pivot) left++;     // left指针的左边[start, left - 1]应该都小于pivot，指针会停止在遇到的第一个不小于pivot的元素
            while (a[right] > pivot) right--;   // right指针的右边[right + 1, end]应该都大于pivot，指针会停止在遇到的第一个不大于pivot的元素
            if (left < right) {                 // 然后交换这两个元素
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
            else break;
        }
        System.out.println("start = " + start + ", left = " + left + ", right = " + right + ", end = " + end);
        QuickSort1(a, start, right);        // 用right作为分区点，是因为相向扫描完成时，right一定可以确保停在最靠右的一个不大于pivot的位置
        QuickSort1(a, right + 1, end);
    }

    /** 递归写法2：pivot选在尾部元素，使用left作为分区点 */
    static void QuickSort2(int[] a, int start, int end) {
        if (start >= end) return;
        int left = start;
        int right = end;
        int pivot = a[end];
        while (true) {
            while (a[left] < pivot) left++;
            while (a[right] > pivot) right--;
            if (left < right) {
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
            else break;
        }
        QuickSort2(a, start, left - 1);
        QuickSort2(a, left, end);
    }

    /** 递归写法3：与写法1一样，唯一区别在于把递归写成了两个方法，pivot选在首个元素，使用right作为分区点 */
    static int Partition1(int[] a, int start, int end) {
        int left = start;
        int right = end;
        int pivot = a[start];
        while (true) {
            while (a[left] < pivot) left++;
            while (a[right] > pivot) right--;
            if (left < right) {
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
            else break;
        }
        return right;
    }

    static void QuickSort3(int[] a, int start, int end) {
        if (a == null || a.length < 2) return;
        int mid = Partition1(a, start, end);
        if (start < mid) QuickSort3(a, start, mid);     // 递归终止条件不能忘
        if (mid + 1 < end) QuickSort3(a, mid + 1, end); // 递归终止条件不能忘
    }

     /** 递归写法4：与写法2一样，唯一区别在于把递归写成了两个方法，pivot选在尾部元素，使用left作为分区点 */
    static int Partition2(int[] a, int start, int end) {
        int left = start;
        int right = end;
        int pivot = a[end];
        while (true) {
            while (a[left] < pivot) left++;
            while (a[right] > pivot) right--;
            if (left < right) {
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
            else break;
        }
        return left;
    }

    static void QuickSort4(int[] a, int start, int end) {
        if (a == null || a.length < 2) return;
        int mid = Partition2(a, start, end);
        if (start < mid - 1) QuickSort4(a, start, mid - 1);
        if (mid < end) QuickSort4(a, mid, end);
    }

    /** 迭代解法：使用栈 */
    // 由于递归本质上就是使用了Java提供的函数栈来存放临时的分区信息，因此迭代法可以通过自己定义一个栈实现同样的功能，往里面压入待处理的分区指针
    // 使用万能的Deque和实现ArrayDeque来作stack
    // 最外部的循环开始前，献给stack里面压入种子，也就是整个数组的起始和终止位置，设置循环终止条件是stack为空（也就是所有的分段任务都完成）
    // 外循环体的内容就是：先从stack领任务，拿到需要分段的数组区间，然后分区，分区完后把两个分区的起始和终止位置再压入栈内。
    // 在网上搜索了半天，没有找到既不使用递归，又不使用栈的解法，so this is it.
    static void QuickSort_Iterative(int[] a) {
        int start, end, left, right, pivot;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        stack.push(a.length - 1);
        while (!stack.isEmpty()) {
            right = end = stack.pop();
            left = start = stack.pop();
            if (start >= end) continue; // 注意不能写成break，遇到区间长度为1应该跳过继续而不是直接终止整个排序。
            pivot = a[start];
            while (true) {
                while (a[left] < pivot) left++;
                while (a[right] > pivot) right--;
                if (left < right) {
                    int temp = a[left];
                    a[left] = a[right];
                    a[right] = temp;
                    left++;
                    right--;
                }
                else break;
            }
            stack.push(start);
            stack.push(right);
            stack.push(right + 1);
            stack.push(end);
        }
    }

    /** Randomized Pivot QuickSort */
    // 区别在于需要先随机选一个元素，这个元素的值作为pivot，为了避免越界和死循环，需要把这个元素和首元素交换，就转化为与上面解法相同的场景了。
    // 随机数对象在方法之外生成，避免每次起始一样。
    static Random rand = new Random();
    static void QuickSort_Randomized(int[] a) {
        QuickSort5(a, 0, a.length - 1);
    }
    static void QuickSort5(int[] a, int left, int right) {
        if (left >= right) return;
        int idx = left + rand.nextInt(right - left + 1);      // nextInt(x)生成的是0至x-1的范围值，不包括x本身。
        int pivot = a[idx];         // 既要保存pivot的值，也要保存pivot的索引本身。这样交换元素后pivot的值依然是未交换前的值。
        swap(a, idx, left);
        int i = left;
        int j = right;
        while (true) {
            while (a[i] < pivot) i++;
            while (a[j] > pivot) j--;
            if (i >= j) break;
            swap(a, i++, j--);      // swap之后记得移动双指针。
        }
        QuickSort5(a, left, j);
        QuickSort5(a, j + 1, right);
    }

    static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /** Quick Sort递归解法细节分析：为什么要这么写，而不是那么写。 */
    // 为什么没有看到对i和j的越界保护却可以做到永远不会越界：每次交换完的值隐式的确保了i和j一定会在越界前停下来。
    // j每次负责把小于等于pivot的值移动到前面，而被移动的这个值本身，可以确保如果j再次遇到这个值时，依然会停下来，这时i和j一定已经交叉，因此会直接结束。
    // 举例：
    // [2, 1] -> [1, 2]  pivot = 2，交换以后1可以确保j一定不会越过这个元素，因为j只有在遇到大于等于pivot的值是才会移动，而
    // ↑i  ↑j    ↑j  ↑i             同样的，一开始被i交换到后面去的元素2，也可以确保i再次遇到它的时候，依然会停下来等待交换或退出判定。

    static void Partition(int[] a, int left, int right) {
        // 递归终止条件：只要分区长度为1或更小，就停止相向扫描和进一步的递归。否则将会递归函数栈溢出。
        if (left >= right) return;
        int pivot = a[left];
        int i = left;
        int j = right;
        while (true) {              // 为什么是无限循环而不直接用i < j做终止条件，因为需要让最后退出时的指针有一个能永远确保分区正确。
            while (a[i] < pivot) i++;   // 为什么是小于而不是小于等于，为了把pivot放在右侧分区。经过了这个while，i的位置一定是一个大于等于pivot的元素。
            while (a[j] > pivot) j--;   // 为什么i和j永远不会越界：因为每次通过交换纠正首尾两个数的值可以充当sentinel以确保i和j在越界前停下来。
            if (i < j) {            // 只有i在j前才交换，如果已经交叉就直接结束，说明分区点找到了。
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;        // 交换完必须手动将两个指针相向移动一步，否则将可能死循环。
                j--;        // 即使移动完之后交叉了，下个循环的双while依然会确保让指针移动到正确的边界位置。
            }
            else break;     // 如果i已经在j后，就直接终止。
        }
        Partition(a, left, j);          // 为什么用j而不是i：因为不论任何情况，j最后停止的位置，一定是最后一个小于pivot的位置
        Partition(a, j + 1, right);     // 也就是说j的位置就是左侧分区的最后一个元素，因此可以安全的划分为left~j，以及j+1~right区间
    }

    public void sort(int[] a) {
        QuickSort_Iterative(a);
    }
}


