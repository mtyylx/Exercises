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
 * - Heap Data Structure
 *      - <Max Heap>: father > all siblings
 *      - <Min Heap>: father < all siblings
 * - Sentinel卫兵：保护可能出现的非法值
 * - 剪枝：将排序问题限制在树的某个分支上，本质上是一种剪枝的操作，因此极大的降低了复杂度。
 *
 */
public class Basic_Heap_Sort extends SortMethod {
    public static void main(String[] args) {
        // Recursive
        int[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort1(a);
        System.out.println(Arrays.toString(a));

        // Iterative
        int[] b = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort2(b);
        System.out.println(Arrays.toString(b));

        // Bulk Test
        SortUtility.VerifySortAlgorithm("heap");
        SortUtility.TestPerformance("heap", 100000);        // 10ms per 10,0000 elements

        // Incorrect Heap Sort: 5000ms per 10,0000 elements.
        int[] c = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSortX(c);
        System.out.println(Arrays.toString(c));
    }


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
    // 需要特别注意的是，如果我们只是简单的逆序扫描 + 交换父子节点，是无法保证得到最大堆的，只能保证树根一定是最大值。这是非常容易疏忽的地方。
    // 因此为了确保发生交换之后，子树依然是最大堆，需要在逆序扫描和交换父子节点的同时递归的对发生修改的子节点分支再次进行最大堆化。
    // 递归的终止条件是不再发生父子交换或者父节点已经是叶子节点。
    // 实例：     原始树           <逆序扫描 + 仅交换本层父子节点>      <逆序扫描 + 递归交换各层父子节点>
    //             1                       7                              7
    //           /   \                   /   \                          /   \
    //          2     3                 5     1 ← 不满足最大堆           5     6 ← 满足最大堆
    //         / \   / \               / \   / \                      / \   / \
    //        4   5  6  7             4   2  6  3                    4   2  1  3
    //
    /** 关键点3：建堆时，父子节点的索引值之间有什么特性 */
    // 1. 通过父节点索引定位子节点
    // 父节点索引值为 i
    // 左儿子索引为 i * 2 + 1
    // 右儿子索引为 i * 2 + 2
    // 2. 定位最后一个非叶子节点
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
    // 在最初的建堆阶段，我们没有选择，必须自底向上（对于数组是逆序扫描）从最后一个非叶子节点扫描才能保证获得最大堆。
    // 在最大堆化之后，由于我们每次只替换树根，此时的树只差一点就是最大堆了，即使需要交换，也仅仅涉及到整个树上的某一条路径（很关键）
    // 其实这就是堆排序的效率高的原因：通过构造树状结构，我们将问题限制在了树的某一条路径上，而不再是整棵树，因此复杂度立刻就下来了（和回溯法有神似）
    // 另外需要注意在交换树根的同时，缩减数组的有效长度。
    //          7                   3                  6
    //        /   \    交换根节点   /   \   更新路径    /   \
    //       5     6      ->     5     6    ->      5     3    -> 循环往复
    //      / \   / \           / \   /            / \   /
    //     4  2   1  3         4  2   1           4  2   1
    // 从树的遍历角度分析
    // 第一阶段建堆时：自底向上
    // 第二阶段提取根节点：自顶向下


    /** 数组堆排序标准<递归>解法：建堆 + 提取根节点维持最大堆性质。Time - o(nlogn), Space - o(logn) */
    // 正序递归，不过由于使用递归，因此需要额外的空间，空间复杂度与堆的高度相关。
    static void HeapSort1(int[] a) {
        for (int i = a.length / 2 - 1; i >= 0; i--)                     // 建堆：从最后一个非叶子节点开始，逆序扫描至树根
            maxHeapify_Recursive(a, i, a.length);
        for (int i = a.length - 1; i > 0; i--) {                        // 抽取根节点：从最后一个节点开始，抽取（交换）节点
            swap(a, 0, i);
            maxHeapify_Recursive(a, 0, i);                          // 维持最大堆性质：只处理发生交换的分支，并收缩数组有效长度
        }
    }

    static void maxHeapify_Recursive(int[] a, int root, int len) {
        if (root * 2 + 1 >= len) return;                                // <递归终止条件1>：root节点没有左节点（即root本身是叶子节点）
        int left = root * 2 + 1;
        int right = root * 2 + 2;
        int validRight = (right < len) ? a[right] : Integer.MIN_VALUE;  // 用卫兵保护root没有右节点的情况
        int larger = (a[left] >= validRight) ? left : right;            // 确定较大子节点的索引值（右节点只有存在才可能比左节点大）
        if (a[root] >= a[larger]) return;                               // <递归终止条件2>：root节点比两个子节点都大，没有交换和继续下探的必要
        swap(a, root, larger);                                          // root与较大的子节点交换
        maxHeapify_Recursive(a, larger, len);                           // 正序递归下探该子分支，确保该分支保持最大堆性质
    }



    /** 数组堆排序标准<迭代>解法：建堆 + 提取根节点维持最大堆性质。Time - o(nlogn), Space - o(1) */
    // 相比于解法1，具有更低的空间复杂度。实际使用中推荐这种解法。
    static void HeapSort2(int[] a) {
        for (int i = a.length / 2 - 1; i >= 0; i--)
            maxHeapify_Iterative(a, i, a.length);
        for (int i = a.length - 1; i > 0; i--) {
            swap(a, 0, i);
            maxHeapify_Iterative(a, 0, i);
        }
    }

    static void maxHeapify_Iterative(int[] a, int root, int len) {
        while (root * 2 + 1 < len) {                                        // 循环条件：当前节点不是叶子节点
            int left = root * 2 + 1;
            int right = root * 2 + 2;
            int validRight = (right < len) ? a[right] : Integer.MIN_VALUE;
            int larger = (a[left] >= validRight) ? left : right;
            if (a[root] >= a[larger]) break;                                // 没有交换的必要，无需下探任何子分支，循环结束
            swap(a, root, larger);
            root = larger;                                                  // 有交换的必要，需要下探发生交换的子分支，进入下一轮循环
        }
    }

    static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    /** 错误解法：虽然可以正确排序，但是构造的是“准最大堆”，并不是严格的最大堆。因此时间复杂度是o(n^2)而不是o(nlogn) */
    // 问题有两个。
    // 问题1：建堆方法中，在发生父子交换后，没有递归的检查交换的子节点分支是否最大堆化。这导致构造出的树并不是严格的最大堆。
    // 问题2：在提取根节点的时候，没有采取只更新发生交换的分支的原则，而是每次都从头逆序扫描一遍。存在双for循环。堆排序的最大优势被抹杀了。
    static void HeapSortX(int[] a) {
        if (a == null || a.length < 2) return;
        maxHeapify(a, a.length);
        for (int range = a.length - 1; range > 0; range--) {
            swap(a, 0, range);
            maxHeapify(a, range);           // 这是一个双for循环，因此时间复杂度回到了o(n^2)
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

    public void sort(int[] a) {
        HeapSort2(a);
    }
}
