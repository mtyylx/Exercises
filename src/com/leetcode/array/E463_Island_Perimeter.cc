#include <vector>
#include <iostream>
using namespace std;

/**
 * Created by Michael on 2019/8/17.
 * 
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water.
 * Grid cells are connected horizontally/vertically (not diagonally). 
 * The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells).
 * The island doesn't have "lakes" (water inside that isn't connected to the water around the island). 
 * One cell is a square with side length 1. 
 * The grid is rectangular, width and height don't exceed 100. 
 * Determine the perimeter of the island.
 * Example:
 * Input:
 * [[0,1,0,0],
 * [1,1,1,0],
 * [0,1,0,0],
 * [1,1,0,0]]
 * 
 * Output: 16
 * 
 * 基本思想
 *      - 二维矩阵投影至一维，独立处理
 *      - 二维矩阵双循环遍历
 *      - 只关注每个元素与后面相邻元素的关系
 * 
 * Similar Problems
 * - E463 Island Perimeter：连通域边长
 * - E733 Flood Fill：连通域换色
 * - M200 Number of Islands：连通域个数
 * - M695 Max Area of Islands：连通域面积
 * 
 */

int islandPerimeter(vector<vector<int>>& grid) {
    // 基本规律：两个相邻的矩形构成的周长等于 4 × 2 - 2，也就是说，相邻一次、周长减两条边。
    // 找到规律后，只要我们知道矩阵有多少个 1 元素，他们之间有多少个相邻边，就可以知道总周长了。周长公式 = 矩形数 * 4 - 相邻数 * 2
    int count = 0, neighbor = 0;
    int r = grid.size();
    int c = grid[0].size();
    for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
            if (grid[i][j] == 1) {
                count++;
                if (i + 1 < r && grid[i + 1][j] == 1) neighbor++;       // 纵向与后面元素相邻
                if (j + 1 < c && grid[i][j + 1] == 1) neighbor++;       // 横向与后面元素相邻
            }
        }
    }
    return count * 4 - neighbor * 2;
}

int main() {
    vector<vector<int>> mat = {{0, 1, 0, 0},
                               {1, 1, 1, 0},
                               {0, 1, 0, 0},
                               {1, 1, 0, 0}};
    std::cout << "Perimeter = " << islandPerimeter(mat) << std::endl;
}
