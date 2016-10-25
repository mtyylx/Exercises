package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 2016/10/24.
 * Given an array S of n integers, are there elements a, b, c,
 * and d in S such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 * The solution set must not contain duplicate quadruplets.
 *
 * For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.
 * A solution set is:
 * [
 *      [-1,  0, 0, 1],
 *      [-2, -1, 1, 2],
 *      [-2,  0, 0, 2]
 * ]
 *
 * Function Signature:
 * public List<List<Integer>> fourSum(int[] a, int target) {...}
 *
 */
public class M18_Four_Sum {
    public static void main(String[] args) {
        List<List<Integer>> result = fourSum(new int[] {0, 0, 0, 0}, 0);
        List<List<Integer>> ksum = kSum(new int[] {-1,0,1,2,-1,-4}, 4, 0);
    }

    /** 2Sum扩展版 */
    // 4Sum用for循环i降至3Sum，3Sum用for循环j降至2Sum，2Sum用left和right得到答案。
    static List<List<Integer>> fourSum(int[] a, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length < 4) return result;
        Arrays.sort(a);
        for (int i = 0; i < a.length - 3; i++) {                // 遍历扫描指针i，固定第一个值
            if (i > 0 && a[i] == a[i - 1]) continue;
            for (int j = i + 1; j < a.length - 2; j++) {        // 遍历扫描指针j，固定第二个值
                if (j > i + 1 && a[j] == a[j - 1]) continue;
                int realtarget = target - a[i] - a[j];
                int left = j + 1;
                int right = a.length - 1;
                while (left < right) {                          // 剩下的两个值用2Sum的双指针解法即可o(n)搞定。
                    int sum = a[left] + a[right];
                    if (sum < realtarget) left++;
                    else if (sum > realtarget) right--;
                    else {
                        result.add(Arrays.asList(a[i], a[j], a[left], a[right]));
                        left++;
                        right--;
                        while (left < right && a[left] == a[left - 1]) left++;
                        while (left < right && a[right] == a[right + 1]) right--;
                    }
                }
            }
        }
        return result;
    }

    /** K-Sum 通用解法，递归形式 */
    // 通过前面的2Sum,3Sum,4Sum，我们已经可以感受到推广到K-Sum是什么形式。
    // 本质上，只要K超过了2，就只能使用完全遍历，所以解决K-Sum问题，就是将K-Sum转化为2Sum再用2Sum的双指针解法解决的过程。
    // 2Sum的时间复杂度为o(n), 3Sum的时间复杂度为o(n^2)，4Sum的时间复杂度为o(n^3)，因此K-Sum的时间复杂度就是o(n^(k-1)).
    // 可以看到，双指针的解法让所有这类问题的计算复杂度都减小了一个数量级。还是很可观的。
    /** 难点1：需要给出哪些参数？*/
    // 原数组作为入参是肯定的，另外每次递归进入的时候，K都会下降一级，且target会更新，因此K和target也要作为入参
    // 不仅如此，由于下一层的遍历必须要在上一层遍历位置的右侧开始，因此还要传入本层遍历的起始索引位置，start。
    /** 难点2：递归写法使得每条结果的元素内容都分布在递归的不同层，如何存储？*/
    // 首先，2Sum搜索时，有可能会找到多条不重复的结果，但这些结果集合都是不完全的，需要在递归返回的时候添加完整
    // 因此递归返回至上层时，每个上层for循环的当前元素需要添加到所有返回的结果集合条目中。
    // 如果底层返回的结果集合prev_result_list不是空的，说明有解，那么就需要把for循环的当前元素a[i]加到所有底层的结果集合中，
    // 再把这些集合全加入当前整体的结果集合current_result_list中。
    // 需要注意current_result_list包括for循环所有元素的产生的解集合内容，而prev_result_list则只包含某一个元素对应的解集合。
    // 这就是为什么每个递归方法需要在最外面定义一个current结果集合，然后在for循环里又定义一个prev结果集合的原因。
    /** 难点3：如何避免重复结果？ */
    // 这个相对简单，还是沿用只要当前值等于上一个元素值就条跳过的原则。
    /** 难点4：使用Arrays.asList()要注意什么？*/
    // 平常我们为了直接得到一个填满元素的List，用Arrays.asList()即可
    // 但是这里不行。因为将这个List添加进结果集合中事情并没完，这个list之后还会被遍历到，并且添加新的元素。这时候Arrays.asList的马脚就露出来了。
    // 因为Arrays.asList创建的并不是真正的list，而只是一个有着list外衣的实际是array的东东。
    // 因此，一旦不注意在这个假list上使用add,remove这类数组并不支持的操作就会报错。
    // 所以，珍爱生命，用new ArrayList<>(Arrays.asList())而不是Arrays.asList()
    // 前者是货真价实的list，后者只会让你不知道怎么死的。
    static List<List<Integer>> kSum(int[] a, int k, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length < k) return result;
        Arrays.sort(a);
        result = kSum_Recursive(a, k, 0, target);
        return result;
    }

    static List<List<Integer>> kSum_Recursive(int[] a, int k, int start, int target) {
        List<List<Integer>> current_result_list = new ArrayList<>();
        // 2Sum 分支：用双指针找到结果，结束递归。
        if (k == 2) {
            int left = start;
            int right = a.length - 1;
            while (left < right) {
                int sum = a[left] + a[right];
                if      (sum < target) left++;
                else if (sum > target) right--;
                else {
                    // while循环中有可能找到多个不重复的解，需要全都加入当前递归层的结果集合中。
                    List<Integer> partial_result = new ArrayList<>();
                    partial_result.add(a[left]);
                    partial_result.add(a[right]);
                    current_result_list.add(partial_result);
                    // 正确简写：current_result_list.add(new ArrayList<>(Arrays.asList(a[left], a[right])));
                    // 错误简写：current_result_list.add(Arrays.asList(a[left], a[right]));
                    left++;
                    right--;
                    while (left < right && a[left] == a[left - 1]) left++;          // Avoid duplicate
                    while (left < right && a[right] == a[right + 1]) right--;       // Avoid duplicate
                }
            }
        }
        // K-Sum 分支：对当前层的每个元素(start至a.length-k+1)使用递归。
        else {
            for (int i = start; i < a.length - k + 1; i++) {
                if (i > start && a[i] == a[i - 1]) continue;                        // Avoid duplicate
                List<List<Integer>> prev_result_list = kSum_Recursive(a, k - 1, i + 1, target - a[i]);
                // prev是current的子集，两者必须分开写。详细分析见上。
                if (prev_result_list != null) {
                    for (List<Integer> prev_result : prev_result_list)
                        prev_result.add(a[i]);                      // 如果使用上面的错误简写，会导致这步挂掉，并提示臣妾做不到。
                    current_result_list.addAll(prev_result_list);
                }
            }
        }
        return current_result_list;
    }
}

