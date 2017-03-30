package com.leetcode.sort;

import java.util.*;

/**
 * Created by Michael on 2016/9/28.
 *
 * Basic Algorithm: Quick Sort
 *
 * <Core Mechanism>
 * Recursive Partition (Split + Reorder). No need to merge.
 *
 * <Performance>
 * Time - o(n * log n), worst o(n^2)
 * Space - o(logn)
 *
 * 快排的空间复杂度始终是o(logn)，这是因为快排必须要使用额外的空间存储每次分区的分界点位置。
 * 如果使用递归实现，则分界点位置是隐式的存储在函数堆栈上
 * 如果使用迭代实现，则分界点位置必须使用栈自己存储起来，因此不管使用递归还是迭代，快排都需要o(logn)额外空间，无法做到o(1)。
 *
 * <Tags>
 * - Two Pointers: 双指针首尾包围扫描。[left → → → ... | ... ← ← ← right ]
 *
 */
public class Basic_Quick_Sort extends SortMethod {
    public static void main(String[] args) {
        /** Recursive */
        // Pivot = Mid-left
        int[] a = {3, 4, 1, 0, 2, 5, 7, 6};
        QuickSort_Std(a);
        System.out.println(Arrays.toString(a));

        // Pivot = Right-most
        int[] b = {3, 4, 1, 0, 2, 5, 7, 6};
        QuickSort_Std2(b);
        System.out.println(Arrays.toString(b));

        // Pivot = Randomized
        int[] c = {3, 4, 1, 0, 2, 5, 7, 6};
        QuickSort_Std3(c);
        System.out.println(Arrays.toString(c));

        // Compact Style
        int[] d = {3, 4, 1, 0, 2, 5, 7, 6};
        QuickSort_Std4(d);
        System.out.println(Arrays.toString(d));

        /** Iterative */
        int[] e = {3, 4, 1, 0, 2, 5, 7, 6};
        QuickSort_Iterative(e);
        System.out.println(Arrays.toString(e));

        /** Bulk Test */
        SortUtility.VerifySortAlgorithm("quick");
        SortUtility.TestPerformance("quick", 100000);       // 7ms per 10,0000 elements
    }

    /** <Quick Sort> vs <Merge Sort> */
    // 1. 虽然两者都会递归分解数组，但是Merge Sort是先分解再排序（逆序递归），而Quick Sort则是边排序边分解（正序递归/尾递归）
    // 2. 虽然两者都会用分解来减小问题规模，但是Merge Sort的分解操作本身并不会修改元素的顺序，而Quick Sort的分解过程本身就是对数组元素进行粗略排序的过程。
    // 3. 虽然两者都会使用额外空间，但是Merge Sort是非原位修改，必须借助额外空间才能完成合并操作，而Quick Sort是原位修改，分区完成之后无需合并。

    /** 快排的直觉思路 Intuitive Explanation：选取基准值构造虚拟分界点 */
    // 按照基准值（pivot）对数组进行划分，而且划分的同时还顺便调整那些明显位置不合适的元素，把他们放到相对于基准值来说合适的位置。
    // 这样每次划分之后，都可以确保整个区间按照基准值已经有序，直至最后划分的区间长度小于2。

    /** pivot位置的选择会影响分区的边界选择 */
    // 如果pivot选择靠左（例如区间第一个元素 / 区间中间偏左元素），那么分区点要选left指针停止位置，并且left属于右侧分区的起始位置。
    // pivot = a[start] 或 pivot = a[start + (end - start) / 2]
    // 如果pivot选择靠右（例如区间最后一个元素 / 区间中间偏右元素），那么分区点要选right指针停止位置，并且right属于左侧分区的终止位置。
    // pivot = a[end] 或 pivot = (end - start) % 2 == 0 ? start + (end - start) / 2 : start + (end - start) / 2 + 1

    /** 示例分析 */
    // [3, 4, 1, 0, 2, 5, 7, 6]   select pivot = 0
    //  ↓                    ↓
    // [3, 4, 1, 0, 2, 5, 7, 6]   记住，左指针会停在第一个 >= pivot的地方 (misplaced element)
    //  ↑l       ↑r                    右指针会停在第一个 <= pivot的地方 (misplaced element)
    //
    // [0, 4, 1, 3, 2, 5, 7, 6]
    //  ↑r ↑l                      这里右指针最后会因为碰到pivot自己而停止下来，不会越界，很巧妙（因为分界点左边一定有小于pivot的元素，能够让右指针停下来）
    //     ↑split                  反之亦然，分界点右边一定有大于pivot的元素，能够让左指针停下来。
    //
    // [0] [4, 1, 3, 2, 5, 7, 6]
    // -----↓-----------------↓--------------------
    //     [4, 1, 3, 2, 5, 7, 6]  select pivot = 2
    //      ↑l       ↑r
    //
    //     [2, 1, 3, 4, 5, 7, 6]
    //         ↑r ↑l
    //            ↑split
    //
    //     [2, 1] [3, 4, 5, 7, 6]
    // -----↓--↓---↓-----------↓-------------------
    //     [2, 1]  ↓           ↓   select pivot = 2
    //      ↑l ↑r  ↓           ↓
    //             ↓           ↓
    //     [1, 2]  ↓           ↓
    //      ↑r ↑l  ↓           ↓
    // ------------↓-----------↓-------------------
    //            [3, 4, 5, 7, 6]  select pivot = 5
    //                   ↑l,r
    //
    //            [3, 4, 5, 7, 6]
    //                ↑r    ↑l
    //                      ↑split
    //
    //            [3, 4, 5] [7, 6]
    // ------------↓-----↓---↓--↓------------------
    //            [3, 4, 5]  ↓  ↓  select pivot = 4
    //                ↑l,r   ↓  ↓
    //            [3, 4] [5] ↓  ↓
    //            [3] [4]    ↓  ↓
    // ----------------------↓--↓------------------
    //                      [7, 6] select pivot = 7
    //                       ↑l ↑r
    //
    //                      [6, 7]
    //                       ↑r ↑l
    // [0] [1] [2] [3] [4] [5] [6] [7]

    /** 快排标准递归写法1：pivot在<中间（偏左）>的位置，
     *  以<左指针>停止位置作为分界点<右侧>的起始边界。注意pivot不能选最后一个元素，否则有可能越界。 */
    static void QuickSort_Std(int[] a) {
        divide(a, 0, a.length - 1);
    }

    // 虽然也是divide，但是这里是先划分左右分区并同时排序之后，再递归各个分区。而Merge Sort是先分区，直到分到不能再分时，在合并的时候再排序。
    static void divide(int[] a, int start, int end) {
        if (start >= end) return;                           // 递归终止条件：当前区间长度小于2（起点start和终点end已经重合或者交叉）
        int pivot = a[start + (end - start) / 2];           // 选择pivot值：取数组中间位置元素（有些类似Merge Sort，不过即使改成pivot = a[start]一样work）
        int split = partition(a, start, end, pivot);        // 按照所选的pivot对当前区间[start, end]进行划分，划分之后的左半部全小于pivot，右半部全大于等于pivot，获得分区点
        divide(a, start, split - 1);                   // 对左右分区递归的进一步划分并排序，直至区间长度小于2
        divide(a, split, end);
    }

    static int partition(int[] a, int left, int right, int pivot) {
        while (left <= right) {                             // 交换的终止条件：左右指针交叉
            while (a[left] < pivot) left++;                 // 左指针会停在第一个 >= pivot的元素 (因为这是一个misplaced element)
            while (a[right] > pivot) right--;               // 右指针会停在第一个 <= pivot的元素 (因为这是一个misplaced element)
            if (left <= right) swap(a, left++, right--);    // 如果此时左右指针还未交叉，就交换这两个元素（一口气让两个元素进入相对合适的位置）
        }
        return left;        // 很关键：最后返回的分界点是left，且left所指元素本身应该属于右半部份。即分区应为[start ~ split - 1], [split ~ end]
    }

    static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    /** 快排标准递归写法2：pivot在<区间最右>的位置（right-most）
     *  以<右指针>停止位置作为分界点<左侧>的终止边界。注意pivot不能选第一个元素，否则可能会越界。 */
    static void QuickSort_Std2(int[] a) {
        divide2(a, 0, a.length - 1);
    }

    static void divide2(int[] a, int start, int end) {
        if (start >= end) return;                           // 递归终止条件：当前区间长度小于2（起点start和终点end已经重合或者交叉）
        int pivot = a[end];                                 // 选择pivot值：选择偏右，因此分界点也应该返回右指针
        int split = partition2(a, start, end, pivot);       // 按照所选的pivot对当前区间[start, end]进行划分，划分之后的左半部全小于pivot，右半部全大于等于pivot，获得分区点
        divide2(a, start, split);                           // 对左右分区递归的进一步划分并排序，直至区间长度小于2
        divide2(a, split + 1, end);
    }

    static int partition2(int[] a, int left, int right, int pivot) {
        while (left <= right) {                             // 交换的终止条件：左右指针交叉
            while (a[left] < pivot) left++;                 // 左指针会停在第一个 >= pivot的元素 (因为这是一个misplaced element)
            while (a[right] > pivot) right--;               // 右指针会停在第一个 <= pivot的元素 (因为这是一个misplaced element)
            if (left <= right) swap(a, left++, right--);    // 如果此时左右指针还未交叉，就交换这两个元素（一口气让两个元素进入相对合适的位置）
        }
        return right;        // 很关键：由于基准偏右，因此分界点返回right，且是左分区的终止位置。即分区应为[start ~ split], [split + 1 ~ end]
    }


    /** 快排标准写法3：pivot<随机选取>
     *  本质上就是写法1加上了随机pivot而已：首先选择随机的元素，然后把它与第一个元素交换，就可以确保pivot处于区间偏左的位置，满足写法1的要求。*/
    // 随机数对象在外面生成，这样每次调用nextInt才是随机的。
    static Random rand = new Random();
    static void QuickSort_Std3(int[] a) {
        divide3(a, 0, a.length - 1);
    }

    static void divide3(int[] a, int start, int end) {
        if (start >= end) return;
        int randi = rand.nextInt(end - start + 1) + start;      // 选取区间内的任意元素
        swap(a, start, randi);                                        // 将该元素与首元素交换
        int pivot = a[start];                                         // 按照老套路选择第一个元素作为pivot
        int split = partition3(a, start, end, pivot);
        divide3(a, start, split - 1);
        divide3(a, split, end);
    }

    static int partition3(int[] a, int left, int right, int pivot) {
        while (left <= right) {                             // 交换的终止条件：左右指针交叉
            while (a[left] < pivot) left++;                 // 左指针会停在第一个 >= pivot的元素 (因为这是一个misplaced element)
            while (a[right] > pivot) right--;               // 右指针会停在第一个 <= pivot的元素 (因为这是一个misplaced element)
            if (left <= right) swap(a, left++, right--);    // 如果此时左右指针还未交叉，就交换这两个元素（一口气让两个元素进入相对合适的位置）
        }
        return left;        // 很关键：最后返回的分界点是left，且left所指元素本身应该属于右半部份。即分区应为[start ~ split - 1], [split ~ end]
    }



    /** 快排标准写法4：使用一个方法完成 */
    // 为什么没有看到对i和j的越界保护却可以做到永远不会越界：每次交换完的值隐式的确保了i和j一定会在越界前停下来。
    // j每次负责把小于等于pivot的值移动到前面，而被移动的这个值本身，可以确保如果j再次遇到这个值时，依然会停下来，这时i和j一定已经交叉，因此会直接结束。
    // 举例：
    // [2, 1] -> [1, 2]  pivot = 2，交换以后1可以确保j一定不会越过这个元素，因为j只有在遇到大于等于pivot的值是才会移动，而
    // ↑i  ↑j    ↑j  ↑i             同样的，一开始被i交换到后面去的元素2，也可以确保i再次遇到它的时候，依然会停下来等待交换或退出判定。

    static void QuickSort_Std4(int[] a) {
        Partition(a, 0, a.length - 1);
    }

    static void Partition(int[] a, int left, int right) {
        if (left >= right) return;      // 递归终止条件
        int pivot = a[left];
        int i = left;
        int j = right;
        while (true) {                  // 为什么是无限循环而不直接用i < j做终止条件，因为需要让最后退出时的指针有一个能永远确保分区正确。
            while (a[i] < pivot) i++;   // 为什么是小于而不是小于等于，为了把pivot放在右侧分区。经过了这个while，i的位置一定是一个大于等于pivot的元素。
            while (a[j] > pivot) j--;   // 为什么i和j永远不会越界：因为每次通过交换纠正首尾两个数的值可以充当sentinel以确保i和j在越界前停下来。
            if (i < j) {                // 只有i在j前才交换，如果已经交叉就直接结束，说明分区点找到了。
                swap(a, i, j);
                i++;                    // 交换完必须手动将两个指针相向移动一步，否则将可能死循环。
                j--;                    // 即使移动完之后交叉了，下个循环的双while依然会确保让指针移动到正确的边界位置。
            }
            else break;     // 如果i已经在j后，就直接终止。
        }
        Partition(a, left, j);          // 为什么用j而不是i：因为不论任何情况，j最后停止的位置，一定是最后一个小于pivot的位置
        Partition(a, j + 1, right); // 也就是说j的位置就是左侧分区的最后一个元素，因此可以安全的划分为left~j，以及j+1~right区间
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
                if (left < right) swap(a, left++, right--);
                else break;
            }
            stack.push(start);
            stack.push(right);
            stack.push(right + 1);
            stack.push(end);
        }
    }


    public void sort(int[] a) {
        QuickSort_Std(a);
    }

    /** 早期算法总结 */
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
}


