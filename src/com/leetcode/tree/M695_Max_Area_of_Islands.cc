#include "../common.h"
#include <stack>
using namespace std;
/**
 * 
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's 
 * (representing land) connected 4-directionally (horizontal or vertical.) 
 * You may assume all four edges of the grid are surrounded by water.
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 * Example 1:
 * 
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,1,1,0,1,0,0,0,0,0,0,0,0],
 * [0,1,0,0,1,1,0,0,1,0,1,0,0],
 * [0,1,0,0,1,1,0,0,1,1,1,0,0],
 * [0,0,0,0,0,0,0,0,0,0,1,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * Given the above grid, return 6. 
 * Note the answer is not 11, because the island must be connected 4-directionally.
 * 
 * Example 2:
 * [[0,0,0,0,0,0,0,0]]
 * Given the above grid, return 0.
 * Note: The length of each dimension in the given grid does not exceed 50.
 * 
 * 基本思想
 *      - 连通域 Flood Fill 换色
 *      - DFS / BFS
 *      - 双循环遍历矩阵
 * 
 * 
 * Similar Problems
 * - E463 Island Perimeter：连通域边长
 * - E733 Flood Fill：连通域换色
 * - M200 Number of Islands：连通域个数
 * - M695 Max Area of Islands：连通域面积
 * 
 */

// 带 counting 功能的 flood fill，返回连通图所含的元素个数。
// 特别需要注意的是<避免重复访问>！由于一般的 flood fill 只需要换色，即使出现重复访问也没关系，但是counting就不同了。
// 元素重复访问的原因在于动态更新元素后栈内的原先记录已经部分换过色，不需要探索了。
int floodFill(vector<vector<int>>& grid, pair<int, int> p, int source, int target) {
    int count = 0;
    stack<pair<int, int>> stack;
    stack.push(p);
    while (!stack.empty()) {
        pair<int, int> c = stack.top();
        stack.pop();
        if (grid[c.first][c.second] != source) continue;       // 需要加这句话，确保出栈元素目前仍然有效（因为元素可能在入栈到出栈的这段时间内已经发生换色）
        grid[c.first][c.second] = target;
        count++;
        if (c.first + 1 < grid.size()     && grid[c.first + 1][c.second] == source) stack.push({c.first + 1, c.second});
        if (c.first - 1 >= 0              && grid[c.first - 1][c.second] == source) stack.push({c.first - 1, c.second});
        if (c.second + 1 < grid[0].size() && grid[c.first][c.second + 1] == source) stack.push({c.first, c.second + 1});
        if (c.second - 1 >= 0             && grid[c.first][c.second - 1] == source) stack.push({c.first, c.second - 1});
    }
    return count;
}


int maxAreaOfIsland(vector<vector<int>>& grid) {
    int count = 0;
    for (int r = 0; r < grid.size(); r++) {
        for (int c = 0; c < grid[0].size(); c++) {
            if (grid[r][c] == 1) {
                int curr = floodFill(grid, {r, c}, 1, 0);
                count = max(count, curr);
            }
        }
    }
    return count;        
}

int main() {
    vector<vector<int>> mat = {{1, 1, 0, 0, 0},
                               {1, 1, 0, 0, 0},
                               {0, 0, 1, 1, 1},
                               {0, 0, 1, 1, 1}};

    std::cout << "Max Island Area = " << maxAreaOfIsland(mat) << std::endl;
}