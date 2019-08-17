#include "../common.h"
#include <stack>
/**
 * Created by Michael on 2019/8/17.
 * 
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
 * You may assume all four edges of the grid are all surrounded by water.
 * 
 * Example 1:
 * Input:
 *      11110
 *      11010
 *      11000
 *      00000
 * Output: 1
 * 
 * Example 2:
 * Input:
 *      11000
 *      11000
 *      00100
 *      00011
 * Output: 3
 *
 * 基本思想：
 *      - 连通域 DFS/BFS 换色：Sink every island!
 *      - 双循环遍历矩阵
 * 
 * Similar Problems
 * - E463 Island Perimeter：连通域边长
 * - E733 Flood Fill：连通域换色
 * - M200 Number of Islands：连通域个数
 * - M695 Max Area of Islands：连通域面积
 * 
 */

using namespace std;

// 很有意思：负责对指定起点开始的岛屿进行<沉没操作>
void floodFill(vector<vector<char>>& grid, pair<int, int> p, char source, char target) {
    stack<pair<int, int>> stack;
    stack.push(p);
    while (!stack.empty()) {
        pair<int, int> c = stack.top();
        stack.pop();
        grid[c.first][c.second] = target;
        if (c.first + 1 < grid.size()     && grid[c.first + 1][c.second] == source) stack.push({c.first + 1, c.second});
        if (c.first - 1 >= 0              && grid[c.first - 1][c.second] == source) stack.push({c.first - 1, c.second});
        if (c.second + 1 < grid[0].size() && grid[c.first][c.second + 1] == source) stack.push({c.first, c.second + 1});
        if (c.second - 1 >= 0             && grid[c.first][c.second - 1] == source) stack.push({c.first, c.second - 1});
    }
}

// 正常双循环遍历矩阵，遇到一个岛屿就沉掉整个岛屿。
// 并不需要每次重新遍历，因为 floodfill 操作是四方向延伸的，只会沉没，不会浮起来新岛屿。
int numberOfIslands(vector<vector<char>>& grid) {
    int count = 0;
    for (int r = 0; r < grid.size(); r++) {
        for (int c = 0; c < grid[0].size(); c++) {
            if (grid[r][c] == '1') {
                count++;
                floodFill(grid, {r, c}, '1', '0');
            }
        }
    }
    return count;
}


int main() {
    vector<vector<char>> mat = {{'1', '1', '0', '0', '0'},
                                {'1', '1', '0', '0', '0'},
                                {'0', '0', '1', '0', '0'},
                                {'0', '0', '0', '1', '1'},
                                {'0', '0', '0', '1', '1'}};
    std::cout << "Number of island = " << numberOfIslands(mat) << std::endl;
}