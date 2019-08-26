#include "../common.h"
#include <algorithm>
#include <climits>
using namespace std;

/**
 * Created by Michael on 2019/8/26.
 *  
 * Implement next permutation, 
 * which rearranges numbers into the lexicographically next greater permutation of numbers.
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
 * The replacement must be in-place and use only constant extra memory.
 * Here are some examples. 
 * Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
 * 
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 * 
 * 基本问题
 *      - 拿到题目，第一个问题就是，如何定义下一个排列的顺序，所谓的 Lexicographically 实际是按什么排列的？
 *      - 输入数组是否有序？无序
 *      - 输入数组元素是否无重复？可以有重复
 *      - Permutation 虽然解是循环出现的，但是最后一个状态向第一个状态的变化不再是渐变，而是突变。这个需要特殊处理。
 *      - 从函数的接口能够观察到其实是原位运算，也就暗示了我们其实要做 swap 和 reverse 来实现
 *      - 如何高效的分析问题：举例。这个题目的主要难点在于如果不自己列一遍3位或者4位的所有全排列，你完全想象不到从一个状态到下一个状态可能出现哪些奇葩的变化特点，所以必须要多举例，而且善于从例子中提炼规律，这个过程确实很考验人。
 * 
 * 基本思想
 *      - 大智若愚：这道题属于真的没有什么捷径的题，需要踏踏实实的观察相邻 permutation 的特性，找规律，用代码实现出来。
 *      - 虽然这道题被归为 backtracking，但是实际上用 backtracking 来解这个的复杂度是 O(n!)，而完全靠分析相邻排列的变化规律，不使用任何特殊 technique 就可以做出来，复杂度只有 O(n)
 *      - element swap 
 *      - element reverse
 *      - reverse scan of array
 *      - exploit sorted array: 虽然整个数组无序，但是可以利用有序的部分，快速确定相对大小元素的索引位置。比如升序的部分，最大的元素一定位于最后。
 * 
 */

void mySwap(vector<int> a, int i, int j) {
    a[i] = a[i] ^ a[j];
    a[j] = a[i] ^ a[j];
    a[i] = a[i] ^ a[j];
}

void myReverse(vector<int>& nums) {
    for (int i = 0; i < nums.size() / 2; i++) {
        mySwap(nums, i, nums.size() - i - 1);
    }
}

// 第一步：反向扫描数组，找到第一个不满足降序排列的元素，记为 pivot。
// 第二步：如果数组严格降序，就反转整个数组，返回结果，其复杂度是 O(n)（并不需要排序，因为排序是 O(nlogn)）
// 第三步：在 pivot 之后的元素中，选择比 pivot 大的最小元素，与 pivot 交换。（注意：这里用到了部分已排序数组的性质！利用规律！）
void nextPermutation(vector<int>& nums) {
    if (nums.size() < 2) return;            // 空数组和单个元素的数组永远只有一个排列方式，就是自己。
    int i = nums.size() - 2;                // 确保 i 能够停在第一个不满足降序条件的元素上（而不是它旁边的）
    while (i >= 0) {
        if (nums[i] < nums[i + 1]) break;   // 找到第一个不满足降序排列的数组就立即退出，i 停留在这个元素的位置上不再动了
        i--;
    }
    if (i < 0) {                            // i < 0 说明始终没有 break，说明数组是严格降序排列的，也就是整个排列中的最后一个状态，此时需要反转整个数组，获得严格升序的数组就回到第一状态了。
        reverse(nums.begin(), nums.end());
        return;
    }
    // 到此位置，一定存在 pivot
    int pivot = nums[i];
    for (int j = nums.size() - 1; j > i; j--) {     // 逆序扫描，由于后面部分的元素一定是降序排列，因此最小的元素一定出现在最后。
        if (nums[j] > pivot) {                      // 只要元素大于 pivot 就是满足最小且大于 pivot 的值，直接 swap 即可结束。
            swap(nums[i], nums[j]);
            break;
        }
    }
    reverse(nums.begin() + i + 1, nums.end());      // 反转 pivot 之后区间的元素  [2, 1, 4, 3] ---swap---> [2, 3, 4, 1] ---reverse---> [2, 3, 1, 4]
}

/**
 *      示例分析
 * 
 *             大于 pivot 且最小的元素                               反转 pivot 之后的区域
 *             ↓
 *    1, 4, 3, 2               ------------>        2,  |  4, 3, 1    ---------->    2, 1, 3, 4
 *    ↑
 *    第一个不满足降序的元素 pivot
 * 
 */


int main() {
    vector<int> a = {2, 3, 1, 4};
    for (int i = 0; i < 16; i++) {
        nextPermutation(a); print(a);
    }
}