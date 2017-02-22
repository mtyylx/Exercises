package com.leetcode.sort;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/9/27.
 *
 * Basic Algorithm: Heap Sort
 *
 * <Core Mechanism>
 * Max Heapify + Extract Root Node + Maintain Max Heap.
 *
 * - Time - o(n * log n)
 * - Space - o(1)
 *
 * <Tags>
 * - Heap Data Structure: MaxHeapify
 * - Sentinel卫兵：保护可能的出现非法值
 *
 */
public class Basic_Heap_Sort extends SortMethod {
    public static void main(String[] args) {
        int[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort(a);
        System.out.println(Arrays.toString(a));

        int[] b = {0, 0, 0, 1};
        HeapSortx(b);
        System.out.println(Arrays.toString(b));

        // Bulk Test
//        SortUtility.VerifySortAlgorithm("heap");
    }

    /** 数组迭代解法：<自底向上>最大堆化 + 提取树根并继续<自底向上>最大堆化。Time - o(n * logn), Space - o(1) */
    /** 关键点1：堆排序的<堆>指的是什么 */
    // 首先要明确“堆”的概念：堆这个名词具有双重含义。在数据结构中，堆指的是一类特殊的树。在程序内存中，堆指的是一块专门放对象的动态内存空间。
    // 这里堆排序的“堆”指的是前者，一类特殊的树。
    // 其特殊的地方在于这个树的所有节点都符合相同的父子大小关系，要不然父节点大于所有子节点（最大堆），要不然小于所有子节点（最小堆）
    // 堆其实和BST有相似之处，堆中节点的大小顺序是<按照上下划分>的，而BST中节点的大小顺序是<按照左右划分>的。
    // 最大堆只能保证节点在自己做树根的子树上的所有层最大，但不保证节点的值比自己之下所有层的值都大。
    // 例如节点4在其子树上的确是最大的，但是节点4（位于level1）并没有比level2上的所有节点都大。
    //             8        level 0
    //           /   \
    //          4     7     level 1
    //         / \   / \
    //        3   1  6  5   level 2
    /** 关键点2：如何将二叉树变为最大堆 */
    // 由于二叉树的自相似性，只需要<自底向上>的对每个节点引领的树进行最大堆化，最后得到的整体一定就是最大堆。
    // 又由于只有那些有子节点的节点才需要最大堆化，因此实际的扫描过程只需要从最后一个非叶子节点开始即可。
    // 需要特别注意的是，如果我们只是简单的逆序扫描 + 交换父子节点，那么最后得到的不一定是最大堆，但是却可以保证树根一定是最大值。
    // 对于堆排序本身来说，其实这就已经足够了。（本解法就是这么做的）
    // 但是如果要求必须要严格的最大堆化一个数组或树，那么在逆序扫描和交换父子节点的同时，还要递归的对发生修改的子节点分支再次进行最大堆化。
    // 实例：     原始树           <仅逆序扫描 + 交换本层父子节点>      <逆序扫描 + 递归交换各层父子节点>
    //             1                       7                              7
    //           /   \                   /   \                          /   \
    //          2     3                 5     1 ← 不满足最大堆           5     6 ← 满足最大堆
    //         / \   / \               / \   / \                      / \   / \
    //        4   5  6  7             4   2  6  3                    4   2  1  3
    //
    /** 关键点3：堆映射至数组时，节点的索引值之间有什么特性 */
    // 1. 通过父节点索引定位子节点
    // 父节点索引值为 i
    // 左儿子索引为 i * 2 + 1
    // 右儿子索引为 i * 2 + 2
    // 2. 定位最后一个有子节点的节点
    // 如果数组有效长度为 N，则最后一个有子节点的节点索引为 N / 2 - 1.
    // 例如 {8, 4, 7, 3}中，节点4是最后一个有子节点的，其索引为 4 / 2 - 1 = 1.
    //            8
    //           / \
    //          4   7
    //         /
    //        3
    // 3. 经典的三元素比较交换问题，由于右儿子不一定存在，因此可以使用卫兵保护，将当前节点与比自己大的子节点交换。
    // 为了做到优雅的比较和交换，可以考虑使用元素的索引而不是元素值。
    /** 关键点4：最大堆化之后，如何进行排序 */
    // 由于最大堆的特性，即使最大堆化完成之后，我们唯一能确定的就是这时候整个树的树根一定是全局的最大值，其他节点我们都不确定。
    // 因此我们只需要把树根保存下来，让剩下的节点再进行最大堆化，每次确定一个最大值，直至所有节点都排序完成。
    // 由于这里将最大堆映射到的数组进行操作，因此需要注意动态缩小数组的有效范围。
    static void HeapSort(int[] a) {
        if (a == null || a.length < 2) return;
        maxHeapify(a, a.length);
        for (int range = a.length - 1; range > 0; range--) {
            swap(a, 0, range);
            maxHeapify(a, range);
        }
    }

    // 注意这里的最大堆化方法并不能得到严格意义上的最大堆，但可以确保树根节点一定是全局最大值（该性质已经足够完成排序工作了）
    static void maxHeapify(int[] a, int range) {
        for (int i = range / 2 - 1; i >= 0; i--) {                                          // 从最后一个有子节点的元素开始，自下而上进行最大堆化
            int lChild = i * 2 + 1;                                                         // 左儿子（一定存在）
            int rChild = i * 2 + 2;                                                         // 右儿子（不一定存在）
            int max = Math.max(a[lChild], rChild < range ? a[rChild] : Integer.MIN_VALUE);  // 确定两者的最大值（使用卫兵保护右儿子）
            if (a[i] < max) swap(a, i, max == a[lChild] ? lChild : rChild);                 // 如果有交换的必要，就将父节点与最大子节点交换
        }
    }

    static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public void sort(int[] a) {
        HeapSortx(a);
    }

    /** 解法1的变体：<自底向上>最大堆化 + 抽取根节点并<自顶向下>维持最大堆。 */
    // 想要将任意数组最大堆化的操作上，只有自底向上（对于数组是逆序扫描）才能保证获得最大堆。
    // 但是对于一个只替换了树根的“准最大堆”，我们可以采用自顶向下（对于数组是正序扫描）就可以确保让它转变成为一个真正的最大堆。
    // 这是因为如果只替换了树根，那么我们只需要让树根的这个节点交换至树枝上的合适节点即可，而这将只影响一个节点，因此只要不断递归的更新发生交换的分支即可。
    static void HeapSortx(int[] a) {
        if (a == null || a.length < 2) return;
        maxHeapify_Iterative_BottomUp(a, a.length);
        for (int i = a.length - 1; i > 0; i--) {
            swap(a, 0, i);
            maxHeapify_Iterative_BottomUp(a, i);
        }
    }

    // 正序递归将准最大堆更新为真正的最大堆
    private static void maxHeapify_Recursive_TopDown(int[] a, int root, int len) {
        int left = root * 2 + 1;
        int right = root * 2 + 2;
        int max = root;
        if (left < len && a[left] > a[max]) max = left;
        if (right < len && a[right] > a[max]) max = right;
        if (max != root) {
            swap(a, root, max);
            maxHeapify_Recursive_TopDown(a, max, len);              // 只对发生交换的分支进行进一步递归，直至交换停止。
        }
    }

    private static void maxHeapify_Iterative_BottomUp(int[] a, int len) {
        for (int i = len / 2 - 1; i >= 0; i--) {
            int root = i;
            while (root <= len / 2 - 1) {   // 至多扫描至最大非叶子节点索引
                int left = root * 2 + 1;
                int right = root * 2 + 2;
                int max = root;
                if (left < len && a[left] > a[max]) max = left;
                if (right < len && a[right] > a[max]) max = right;
                if (max == root) break;         // 如果无需交换父节点和子节点，也就不需要下降了
                swap(a, root, max);
                root = max;
            }
        }
    }

    // 在复习算法的时候我发现一个特别有意思的现象，就是之前学会了这个算法之后，往往已经忘记了最初接触时候的那些最根本的疑问，
    // 比如在Heap Sort代码的注释里可以看到我当时事无巨细的分析了很多细枝末节的问题，却并没有强调这个算法最独特的地方就是他用了Heap，heap到底是什么等等
    // 结果就是，我现在再看之前写的算法注释的时候，发现完全get不到上面写的点，因为我压根就不记得到底Heap是个什么东西了，
    // 以为就是一个类似于Queue的东西，和Java内存的Heap和Stack搞混了。这也说明其实我之前学的时候就没有真正的领悟到最核心的东西。

    // 时间复杂度计算和最坏情况性能推导
    // 堆排序比较蛋疼的的一点在于如果数组是已排序的，那么他还是会傻傻的把已经升序的数组先修改成最大堆，然后再修改回来，简直是南辕北辙。
    // 时间复杂度之堆排序的第一步：将数组最大堆化，时间复杂度为o(n)。
    // 例如四层15个数进行最大堆化：最坏情况一共7个数需要下降，这7个数中，4个下降1层，2个下降2层，1个下降3层，总下降数11.
    // 例如五层31个数进行最大堆化：最坏情况一共15个数需要下降，这15个数中，8个下降1层，4个下降2层，2个下降3层，1个下降4层，总下降数26.
    // 时间复杂度之堆排序的第二步：不断取根节点，时间复杂度为o(nlogn)
    // 因为要提取n次根节点，每次从根节点最大堆化是logn（从主定理证明）


}
