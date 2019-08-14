#include <iostream>
#include <vector>


// 基本前提：数组已排序，且为升序排列。
// 循环终止条件：左右指针位置出现交错（左右互换）
int binary_search_lowerbound(std::vector<int>& a, int target) {
    if (a.size() == 0) return 0;
    int left = 0;
    int right = a.size() - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        // if (a[mid] == target) return mid;
        if (a[mid] >= target) right = mid - 1;      // 即使遇到中点元素就是target，也会继续搜索。
        else if (a[mid] < target) left = mid + 1;
    }
    return left;
}

// 标准的二分查找，但是无法正确的返回最小插入位置。
int binary_search_standard(std::vector<int>& a, int target) {
    if (a.size() == 0) return 0;
    int left = 0;
    int right = a.size() - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (a[mid] == target) return mid;           // return immediately, may not be the lower bound idx.
        else if (a[mid] > target) right = mid - 1;
        else if (a[mid] < target) left = mid + 1;
    }
    return left;
}

int main() {

    std::vector<int> data = {1, 1, 2, 3, 4, 4, 7};
    std::cout << "First no less than target insert pos = " << binary_search_lowerbound(data, 1) << std::endl;
    std::cout << "First no less than target insert pos = " << binary_search_standard(data, 1) << std::endl;

}