#include "../common.h"
using namespace std;

/** 
 * You are given an n x n 2D matrix representing an image.
 * Rotate the image by 90 degrees (clockwise).
 * 
 * Note:
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. 
 * DO NOT allocate another 2D matrix and do the rotation.
 * 
 * Example 1:
 * 
 * Given input matrix = 
 * [
 *  [1,2,3],
 *  [4,5,6],
 *  [7,8,9]
 * ],
 * 
 * rotate the input matrix in-place such that it becomes:
 * 
 * [
 *   [7,4,1],
 *   [8,5,2],
 *   [9,6,3]
 * ]
 * 
 * 基本思想
 *      - 原位交换元素：temp 或者 ^ XOR
 *      - 矩阵顺时针旋转的特性：行反转，转置
 *      - 矩阵逆时针旋转的特性：列反转，转置
 * 
 */

// 行反转
// 第 i 行与第 N - i - 1 行数据交换，i取值范围为 0 to N/2
void reverse_row(vector<vector<int>>& matrix) {
    int rows = matrix.size();
    for (int i = 0; i < rows / 2; i++) {
        for (int j = 0; j < matrix[0].size(); j++) {
            matrix[i][j]            = matrix[i][j] ^ matrix[rows - i - 1][j];
            matrix[rows - i - 1][j] = matrix[i][j] ^ matrix[rows - i - 1][j];
            matrix[i][j]            = matrix[i][j] ^ matrix[rows - i - 1][j];
        }
    }
}

// 转置
void rotate(vector<vector<int>>& matrix) {
    reverse_row(matrix);
    for (int i = 0; i < matrix.size(); i++) {
        for (int j = i + 1; j < matrix[0].size(); j++) {        // 上对角区域 (i, j) vs (j, i)
            matrix[i][j] = matrix[i][j] ^ matrix[j][i];
            matrix[j][i] = matrix[i][j] ^ matrix[j][i];
            matrix[i][j] = matrix[i][j] ^ matrix[j][i];
        }
    }
}

int main() {

    vector<vector<int>> mat = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    print2D(mat);
    rotate(mat);
    print2D(mat);
}