#include "../common.h"
using namespace std;
/**
 * 杨氏矩阵的搜索
 * 
 * Write an efficient algorithm that searches for a value in an m x n matrix. 
 * This matrix has the following properties:
 * Integers in each row are sorted in ascending from left to right.
 * Integers in each column are sorted in ascending from top to bottom.
 * 
 * Example: Consider the following matrix:
 * [
 *  [1,   4,  7, 11, 15],
 *  [2,   5,  8, 12, 19],
 *  [3,   6,  9, 16, 22],
 *  [10, 13, 14, 17, 24],
 *  [18, 21, 23, 26, 30]
 * ]
 * 
 * Given target = 5, return true.
 * Given target = 20, return false.
 * 
 * 基本技巧
 *    - 矩阵的 Walk：上下左右四方位移动
 *    - 搜索起点：左下角或右上角
 *    - BST 特性
 * 
 * 思路分析
 *    - 由于行列都已排序，因此搜索是二维空间的二分
 *    - 难点：通常会陷入绞尽脑汁想要 / 2 的思维定式里，但是其实这条路并不好走
 *    - Tips：不一定非要 / 2 一步实现 O(logn) 的复杂度，有时候 O(n) 的 ++/-- 更好实现。
 *    - Tips：矩阵的正反对角线很重要，但是这里并没有用到
 * 
 */

// Time ~ O(m + n)
// 从二叉树的角度来看这个矩阵，会发现，右上角顶点和左下角顶点都可以视为是一个 BST 的 root 节点
// 对于右上角顶点，其左侧的元素一定小于它，其下方的元素一定大于它，同时这两个相邻的元素也符合这一特性，
// 这个一个小于一个大于的特性符合<选边>的特点，实际搜索的过程是一个小海龟走折线（zig-zag）的过程。
bool searchMatrix(vector<vector<int>>& matrix, int target) {
    if (matrix.empty() || matrix[0].empty()) return false;
    int m = matrix.size();
    int n = matrix[0].size();
    int r = 0;
    int c = n - 1;
    while (r < m && c >= 0) {
        cout << "current coordinate = " << r << ", " << c << endl;
        if (matrix[r][c] == target) return true;
        else if (matrix[r][c] < target) r++;
        else if (matrix[r][c] > target) c--;
    }
    return false;
}

// 如果走火入魔非要用二分，对于二维矩阵，任何一个中点会将这个矩阵划分为 4 块
// 而杨氏矩阵本身的排序特性决定了你一次二分仅仅只能排除这四块中的一块，剩下的三块都有可能存在 candidate
// 所以这种方法最后的复杂度其实还会比 O(m+n) 更高。具体分析见讨论版。

int main() {
  vector<vector<int>> matrix = {{1,   4,  7, 11, 15}, 
                                {2,   5,  8, 12, 19}, 
                                {3,   6,  9, 16, 22}, 
                                {10, 13, 14, 17, 24}, 
                                {18, 21, 23, 26, 30}};
  searchMatrix(matrix, 18);
}
