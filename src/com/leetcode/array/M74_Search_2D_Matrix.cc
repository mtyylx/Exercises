#include "../common.h"
using namespace std;
/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. 
 * This matrix has the following properties:
 *      - Integers in each row are sorted from left to right.
 *      - The first integer of each row is greater than the last integer of the previous row.
 * 
 * Example 1: Input: matrix = 
 * [
 *      [1,   3,  5,  7],
 *      [10, 11, 16, 20],
 *      [23, 30, 34, 50]
 * ]
 * 
 * target = 3
 * Output: true
 * 
 * 相似问题
 *      - M74 Search 2D Matrix: 每行已排序，且每一行严格小于下一行。
 *      - M240 Search 2D Matrix 2: 每行已排序，每列已排序，但是行与行、列与列之间没有必然的排序关系。
 * 
 * 基本思路
 *      - 虽然是二维矩阵，但是由于行与行之间有严格的大小关系，因此完全可以将矩阵整体视为一维向量。
 *      - 相比标准的数组二分查找，需要额外做的是一维编号到二维矩阵坐标的翻译
 * 
 */

// 把矩阵视为一维数组，左右指针的索引值都是 flatten 之后的值，仅对 mid 指针的索引翻译为二维矩阵的坐标
// 还是除法+求余的技巧，注意要除以的是 c，也就是列的个数。
bool searchMatrix(vector<vector<int>>& matrix, int target) {
    if (matrix.empty() || matrix[0].empty()) return false;
    int r = matrix.size();
    int c = matrix[0].size();
    int i = 0;
    int j = r * c - 1;
    while (i <= j) {
        int mid = i + (j - i) / 2;
        int x = mid / c;
        int y = mid % c;
        printf("(%d, %d) %d\n", x, y, mid);
        if (matrix[x][y] == target) return true;
        else if (matrix[x][y] < target) i = mid + 1;
        else if (matrix[x][y] > target) j = mid - 1;
    }
    return false;
}

int main () {
  vector<vector<int>> matrix = {{1,   2,   3,  4,  5}, 
                                {8,   9,  10, 12, 13}, 
                                {16,  17, 18, 19, 20}};
  searchMatrix(matrix, 18);
}