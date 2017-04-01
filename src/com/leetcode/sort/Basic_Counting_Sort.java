package com.leetcode.sort;

import java.util.Arrays;

/**
 * Created by Michael on 2016/9/29.
 *
 * Basic Algorithm: Counting Sort.
 * 适用条件：待排序数组是整型数组，或可以按整型类型的Key对数组进行排序。
 * 特点：因为根本思路在于Value-as-Index，所以对于数组的元素取值非常挑剔：
 * 1. 数组元素取值最好均匀分布在一个区间内，
 * 2. 取值必须是整数，
 * 3. 取值范围不宜过大，取值范围不能超过整型最大值
 *
 * Time - O(n)
 * Space - O(MAX_VALUE - MIN_VALUE + 1),
 * 取决于待排序数组中最大值和最小值元素之差。适合于方差小的数组，方差过大会使性能严重下降，甚至无法排序。
 *
 */
public class Basic_Counting_Sort {
    public static void main(String[] args) {
        int[] a = {-5, -3, -5, -3, 1, 1, -2};
        System.out.println(Arrays.toString(CountingSort2(a)));

        // Bulk Test
        SortUtility.VerifySortAlgorithm("counting");
        SortUtility.TestPerformance("counting", 100000);
    }

    /** Counting Sort的核心思路：<Value-As—Index> */
    // 计数排序的<最大软肋>是对未排序数组的元素取值范围有要求。
    // 不管是下面的解法1还是解法2，都需要依靠Value-As-Index的方式，而Java的数组Index类型限制了Value必须也得是整型，
    // 这就导致了Counting Sort有一定的局限性，不能对<小数数组>进行排序，不能对<取值范围过大的数组>进行排序。
    // (虽然对于元素有负数的数组严格来说也不行，但是可以用offset平移整个数组元素至正数，所以依然可以用Counting Sort)
    // 对于小数数组，以及取值范围过大的数组，具有十分类似思路的Bucket Sort可以解决问题。详见Bucket Sort.
    // 其实Bucket Sort的解决方案是缩放，和Counting Sort中使用的负数平移十分类似，但Bucket和Counting的核心区别在于：
    // Counting Sort的Count数组每个元素里只能存放<相同元素>的<个数>，但是，
    // Bucket Sort的每个Bucket里却可以放一个子区间内的<不同元素>的<实体>，这使得Bucket Sort可以用缩放来处理任何取值范围内的任何元素。
    // 当Bucket Sort的每个Bucket都放的是相同元素时(或每个桶长度都为1)，Bucket Sort就退化成为了Counting Sort
    // 当Bucket Sort只有一个Bucket的时候，Bucket Sort就退化成为了Insertion Sort
    // 当Bucket Sort只有两个Bucket的时候，Bucket Sort就退化成了Merge Sort

    /** 为什么有解法1和解法2，以及什么情况下该用哪个？ */
    // 解法一只统计原数组的元素出现频率，然后顺序遍历频率表<直接生成>数组，不涉及把原数组元素拷贝至新数组的过程。
    // 解法二则统计的是累计频率分布的表，然后逆序遍历原数组，把原数组的元素<拷贝至新数组的合适位置>。
    // 可以看到，第一个解法更为直觉，第二个解法更麻烦些，那为什么还需要第二个解法呢？
    // 这源自于对特殊数组的排序需求，现实中，想要把计数排序用在其他排序中作为子过程，或者要排序的是数组元素对应的Key值，
    // 那么就需要即按照Key的出现频率统计，最后又需要操作原数组元素排序，而不是仅将Key自己排序好就够了。第二个解法的拷贝特性满足了这个需求。

    /** 解法1：针对<纯整型数组>进行计数排序 */
    // 基本思想：Value-as-Index，将原数组的元素值作为新数组的索引，原数组元素值出现的个数作为新数组的元素值。
    // 得到频率分布数组（即count数组）后，遍历该数组的同时，复写原数组，count数组的每个元素值是多少，就写多少个元素在原数组里。
    // 虽然计数排序不属于比较排序，因此不存在比较排序时间复杂度O(nlogn)的下限，理论上可以比任何比较排序的速度都要快。
    // 但是，只要其元素取值的最大值和最小值差值很大，即使未排序数组的元素个数很少，其排序速度依然将会非常慢（比较类排序则完全不会有这种问题）
    // 特别的，计数排序对于取值范围超过Integer.MAX_VALUE的数组是无法排序的。
    // 例如一个只有两个元素的数组[2147483647, -2147483648]的取值范围是4294967296，而由于Java数组的索引值必须是整型，因此索引会整型溢出，索引值将会是负值
    static void CountingSort(int[] a) {
        if (a == null || a.length < 2) return;
        int max = a[0];
        int min = a[0];
        for (int x : a) {
            if (x > max) max = x;
            else if (x < min) min = x;
        }
        int[] count = new int[max - min + 1];
        for (int x : a) {
            count[x - min]++;
        }

        // 写为双for循环更简洁
        int idx = 0;
        for (int i = 0; i < count.length; i++)
            for (int j = 0; j < count[i]; j++)
                a[idx++] = i + min;

//        // 写为for-while循环
//        int current = 0;
//        for (int i = 0; i < count.length; i++) {
//            while (count[i] > 0) {
//                a[current] = i + min;
//                current++;
//                count[i]--;
//            }
//        }
    }

    /** 解法2：针对<数组元素并非整型但每个元素都对应一个整型类型的Key>，根据这个整型的Key值进行计数排序（官方解法）*/
    // 基本思想：Value-as-index + Cumulative Histogram，后者是这个解法的独特之处。解法1只用到了一般的Histogram (Frequency Table).
    // 使用两个辅助数组，b数组代表原数组a元素值的分布情况，由a数组和b数组得到排序后的新数组c。
    // 和第一个解法一样使用offset构造b数组，以处理a数组元素为负值的情况，
    // 和第一个写法的区别在于：
    // 1. b数组（也就是计数数组）需要额外的叠加处理，使得b数组每个元素的含义转变为“a数组中小于等于b数组该索引值的元素个数”
    // 2. 最关键的部分：经b数组的辅助，把a数组的元素拷贝至c数组. 即c[--b[a[i] - min]] = a[i]这个看上去很绕的赋值语句。
    // 这里用逆序扫描a数组，而不是正序扫描，是为了让该排序是稳定的（即排序完成的c数组会保持a数组中相等元素的相对位置）
    // 具体原理是，由于b[a[i] - min]表示a数组中值小于等于当前元素a[i]的元素个数，所以需要做两件事：
    // 第一件事：因为要把a[i]元素放入c数组了，因此b数组中小于等于a[i]的元素个数应该减一，即--b[a[i] - min]
    // 第二件事：把a[i]放入c数组中的合适位置，这个合适位置等于个数减一，又因为第一步已经把个数减一了，所以直接就是。
    // 例如a = [5, 1, 1, 4]，b = [2, 2, 2, 3, 4]，逆序扫描a数组，首先是a[i] = 4
    // 查询b数组发现，b[4 - 1] = 3，表示a数组中小于等于a[i] = 4的元素个数为3，那么这个a[i]元素就应该放在c[2]的位置（索引等于个数减一）
    // 所以有c = [0, 0, 4, 0]，b = [2, 2, 2, 2, 4]
    // 同时也可以看到，虽然b数组的长度为a数组最大最小值之差加一，但是在逆序扫描a数组的过程中，b数组中并不是每个元素都会被访问到，
    // 准确的说，b数组中只有发生个数增量的那些元素会被访问到，比如b[0], b[3], b[4]，其中b[0]会被多次访问到，因为a数组中有多个偏移后为0的元素
    static int[] CountingSort2(int[] a) {
        if (a == null || a.length < 2) return a;
        int max = a[0];
        int min = a[0];
        for (int x : a) {
            if (x > max) max = x;
            else if (x < min) min = x;
        }
        int[] b = new int[max - min + 1];
        for (int x : a) {
            b[x - min]++;
        }
        for (int i = 1; i < b.length; i++) {
            b[i] += b[i - 1];
        }
        int[] c = new int[a.length];
        // 最关键的部分
        for (int i = a.length - 1; i >= 0; i--) {
            c[--b[a[i] - min]] = a[i];
        }
        return c;
    }

    public void sort(int[] a) {

    }

}
