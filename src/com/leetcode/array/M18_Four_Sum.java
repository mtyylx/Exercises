package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 2016/10/24.
 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?
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
 * <K-Sum系列问题>
 *    E1 2-Sum: 给定一个整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *   M15 3-Sum: 给定一个整形数组a和目标值0，求解所有相加等于0的三元组，不可重复。（解个数随机）
 *   M18 4-Sum: 给定一个整型数组a和目标值k，求解所有相加等于0的四元组，不可重复。（解个数随机）
 *  M167 2-Sum Sorted: 给定一个已排序的整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *  E170 2-Sum Data Structure: 给定一系列整型数值和目标值，提供添加元素和寻找内部数据库中是否存在和等于目标值的两个元素的功能。
 *   M16 3-Sum Closest: 给定一个整型数组a和目标值k，求解距离k最近的一个三元组之和。（有且仅有一个解）
 *  M259 3-Sum Smaller: 给定一个整型数组a和目标值k，求解和小于target的三元组个数，可以重复。
 *
 * <Tags>
 * - Sort + Two Pointers: [left → → → ... ← ← ← right]
 * - Backtracking
 *
 */
public class M18_Four_Sum {
    public static void main(String[] args) {
        List<List<Integer>> result = fourSum(new int[] {-1, 0, 1, 2, -1, -4}, 0);
        List<List<Integer>> result2 = fourSum2(new int[] {-1, 0, 1, 2, -1, -4}, 0);
        List<List<Integer>> result3 = kSum(new int[] {-1, 0, 1, 2, -1, -4}, 0, 4);
        List<List<Integer>> result4 = kSum2(new int[] {-1, 0, 1, 2, -1, -4}, 0, 4);
        List<List<Integer>> result5 = kSum_Trim(new int[] {-1, 0, 1, 2, -1, -4}, 0, 4);
    }

    /** 解法1：Sort + Two Pointers，迭代写法。Time - o(n^3) */
    // 2Sum扩展版：4Sum用for循环i降至3Sum，3Sum用for循环j降至2Sum，2Sum用left和right得到答案。
    static List<List<Integer>> fourSum(int[] a, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length < 4) return result;
        Arrays.sort(a);
        for (int i = 0; i < a.length - 3; i++) {                // 指针i：固定第一成员
            if (i > 0 && a[i] == a[i - 1]) continue;            // 跳过重复
            for (int j = i + 1; j < a.length - 2; j++) {        // 指针j：固定第二成员
                if (j > i + 1 && a[j] == a[j - 1]) continue;    // 跳过重复
                int left = j + 1;                               // 双指针固定第三和第四个成员
                int right = a.length - 1;
                int k = target - a[i] - a[j];
                while (left < right) {
                    if      (a[left] + a[right] < k) left++;
                    else if (a[left] + a[right] > k) right--;
                    else {
                        result.add(new ArrayList<>(Arrays.asList(a[i], a[j], a[left], a[right])));
                        left++; right--;                                              // while循环手动移动指针
                        while (left < right && a[left] == a[left - 1]) left++;        // 跳过重复
                        while (left < right && a[right] == a[right + 1]) right--;     // 跳过重复
                    }
                }
            }
        }
        return result;
    }

    /** 解法2：<Sort + 剪枝法>。虽然代码更长，但是运行速度超过了解法1，因为节省了很多无用功。通用的递归解法见后面。 */
    // 剪枝0：如果当前元素等于左侧相邻元素，则应该跳过当前元素扫描。（解法1只用了这一种剪枝策略）
    // 剪枝1：如果数组最大元素的4倍都比target小，则一定无解。
    // 剪枝2：如果数组最小元素的4倍都比target大，则一定无解。
    // 剪枝3：如果当前元素加数组最大元素的3倍都比target小，则当前元素做为四元组第一成员一定无解。
    // 剪枝4：如果当前元素的4倍都比target大，则当前元素及其之后的所有元素做为四元组第一成员一定无解。
    // 剪枝5：如果当前元素的4倍等于target，且当前元素向右3个位置的元素都等于当前元素，则找到一个解，否则无解。
    static List<List<Integer>> fourSum2(int[] a, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length < 4) return result;
        Arrays.sort(a);
        int max = a[a.length - 1];
        if (a[0] * 4 > target || max * 4 < target) return result;   // 剪枝1 + 剪枝2
        for (int i = 0; i < a.length - 3; i++) {
            if (i > 0 && a[i] == a[i - 1]) continue;                // 剪枝0
            if (a[i] + max * 3 < target) continue;                  // 剪枝3
            if (a[i] * 4 > target) break;                           // 剪枝4
            if (a[i] * 4 == target) {
                if (a[i + 3] == a[i]) result.add(new ArrayList<>(Arrays.asList(a[i], a[i], a[i], a[i])));
                break;                                              // 剪枝5
            }
            threeSum(a, target - a[i], a[i], i + 1, result);
        }
        return result;
    }

    static void threeSum(int[] a, int target, int e1, int start, List<List<Integer>> result) {
        int max = a[a.length - 1];
        if (a[start] * 3 > target || max * 3 < target) return;
        for (int i = start; i < a.length - 2; i++) {
            if (i > start && a[i] == a[i - 1]) continue;
            if (a[i] + max * 2 < target) continue;
            if (a[i] * 3 > target) break;
            if (a[i] * 3 == target) {
                if (a[i + 2] == a[i]) result.add(new ArrayList<>(Arrays.asList(e1, a[i], a[i], a[i])));
                break;
            }
            twoSum(a, target - a[i], e1, a[i], i + 1, result);
        }
    }

    static void twoSum(int[] a, int target, int e1, int e2, int start, List<List<Integer>> result) {
        int max = a[a.length - 1];
        if (a[start] * 2 > target || max * 2 < target) return;
        int left = start;
        int right = a.length - 1;
        while (left < right) {
            if      (a[left] + a[right] < target) left++;
            else if (a[left] + a[right] > target) right--;
            else {
                result.add(new ArrayList<>(Arrays.asList(e1, e2, a[left], a[right])));
                left++;
                right--;
                while (left < right && a[left] == a[left - 1]) left++;
                while (left < right && a[right] == a[right + 1]) right--;
            }
        }
    }


    /** 问题发散：推广至<K-Sum>情况下，该如何去解决？ */

    /** 解法1：<Backtracking + Two Pointers>, 递归写法。Time - o(n^(k-1)). */
    // 递归方法的签名设计：传递当前搜索起点start，传递当前剩余成员个数k，传递解集，传递当前路径（以便随时回退）
    // 需要考虑k的边界情况：k超过数组长度则一定无解，k为0也无解，k为1则问题退化为判断数组中是否有target元素的问题，也不考虑。
    static List<List<Integer>> kSum2(int[] a, int target, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || k > a.length || k < 2) return result;
        Arrays.sort(a);
        kSum_Recursive(a, target, 0, k, result, new ArrayList<>());
        return result;
    }

    static void kSum_Recursive(int[] a, int target, int start, int k, List<List<Integer>> result, List<Integer> path) {
        if (k == 2) {                                                   // Two-Sum情况
            int left = start;
            int right = a.length - 1;
            while (left < right) {
                if      (a[left] + a[right] > target) right--;
                else if (a[left] + a[right] < target) left++;
                else {
                    result.add(new ArrayList<>(path));                                      // 拷贝新path
                    result.get(result.size() - 1).addAll(Arrays.asList(a[left], a[right])); // 直接对副本path添加最后两个成员
                    left++; right--;
                    while (left < right && a[left] == a[left - 1]) left++;
                    while (left < right && a[right] == a[right + 1]) right--;
                }
            }
        }
        else {                                                          // k-Sum情况
            for (int i = start; i < a.length - k + 1; i++) {            // i的搜索区间应该从start至a.length减去剩下k-1个元素个数
                if (i > start && a[i] == a[i - 1]) continue;
                path.add(a[i]);
                kSum_Recursive(a, target - a[i], i + 1, k - 1, result, path);
                path.remove(path.size() - 1);                      // backtracking的典型回退方式
            }
        }
    }

    /** 解法2：基于上面解法2的 K Sum推广。<Sort + 剪枝>，递归写法。
     *  绝对是我最近的登峰造极之作！！！灭哈哈哈哈哈！！！
     *  */
    // 传递List<Integer> path而不是单独元素。
    static List<List<Integer>> kSum_Trim(int[] a, int target, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length < k || k < 2) return result;
        Arrays.sort(a);
        kSum_Trim(a, target, k, 0, result, new ArrayList<>());
        return result;
    }

    static void kSum_Trim(int[] a, int target, int k, int start, List<List<Integer>> result, List<Integer> path) {
        int max = a[a.length - 1];
        if (a[start] * k > target || max * k < target) return;                              // 剪枝1 + 剪枝2
        if (k == 2) {
            int left = start;
            int right = a.length - 1;
            while (left < right) {
                if      (a[left] + a[right] < target) left++;
                else if (a[left] + a[right] > target) right--;
                else {
                    result.add(new ArrayList<>(path));
                    result.get(result.size() - 1).addAll(Arrays.asList(a[left], a[right]));
                    left++; right--;
                    while (left < right && a[left] == a[left - 1]) left++;
                    while (left < right && a[right] == a[right + 1]) right--;
                }
            }
        }
        else {
            for (int i = start; i < a.length - k + 1; i++) {
                if (i > start && a[i] == a[i - 1]) continue;                                // 剪枝0
                if (a[i] + max * (k - 1) < target) continue;                                // 剪枝3
                if (a[i] * k > target) break;                                               // 剪枝4
                if (a[i] * k == target) {                                                   // 剪枝5
                    if (a[i + k - 1] == a[i]) {
                        result.add(new ArrayList<>(path));
                        List<Integer> temp = new ArrayList<>();
                        for (int x = 0; x < k; x++) temp.add(a[i]);
                        result.get(result.size() - 1).addAll(temp);
                    }
                    break;
                }
                path.add(a[i]);
                kSum_Trim(a, target - a[i], k - 1, i + 1, result, path);
                path.remove(path.size() - 1);                                         // Backtracking方式
            }
        }
    }


    /** 早期解法：同样是递归写法，只不过是在每次递归结束时把返回的解集全添加上当前成员，思路稍显复杂。 */
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
    static List<List<Integer>> kSum(int[] a, int target, int k) {
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

