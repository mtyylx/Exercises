/*
 * 
 * An image is represented by a 2-D array of integers, each integer
 * representing the pixel value of the image (from 0 to 65535).
 * 
 * Given a coordinate (sr, sc) representing the starting pixel (row and column)
 * of the flood fill, and a pixel value newColor, "flood fill" the image.
 * 
 * To perform a "flood fill", consider the starting pixel, plus any pixels
 * connected 4-directionally to the starting pixel of the same color as the
 * starting pixel, plus any pixels connected 4-directionally to those pixels
 * (also with the same color as the starting pixel), and so on.  Replace the
 * color of all of the aforementioned pixels with the newColor.
 * 
 * At the end, return the modified image.
 * 
 * Example 1:
 * 
 * Input: 
 * 
 *      1 1 1        2 2 2
 *      1 1 0   ->   2 2 0
 *      1 0 1        2 0 2
 * 
 * image = [[1,1,1],[1,1,0],[1,0,1]]
 * sr = 1, sc = 1, newColor = 2
 * Output: [[2,2,2],[2,2,0],[2,0,1]]
 * Explanation: 
 * From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected 
 * by a path of the same color as the starting pixel are colored with the new color.
 * Note the bottom corner is not colored 2, because it is not 4-directionally connected
 * to the starting pixel.
 * 
 * Note:
 * The length of image and image[0] will be in the range [1, 50].
 * The given starting pixel will satisfy 0  and 0 .
 * The value of each color in image[i][j] and newColor will be an integer in
 * [0, 65535].
 * 
 * Similar Problems
 * - E463 Island Perimeter：连通域边长
 * - E733 Flood Fill：连通域换色
 * - M200 Number of Islands：连通域个数
 * - M695 Max Area of Islands：连通域面积
 * 
 * 基本思想
 *      - DFS / BFS
 *      - 始终确保入栈和入队元素合法：节点 inbound 且取值等于 srcColor
 *      - 避免重复访问：避免 srcColor 与 newColor 一样，先更新颜色后访问相邻颜色
 * 
 * 特点
 *      - 第一眼看上去应该用 BFS，但是实际上 BFS 和 DFS 的效果完全一样。
 * 
 */

#include <vector>
#include <queue>
#include <stack>
#include <iostream>
using namespace std;
class Solution {
public:
    // BFS + Queue
    vector<vector<int>> floodFill_BFS(vector<vector<int>>& image, int sr, int sc, int newColor) {
        int srcColor = image[sr][sc];
        int rows = image.size();
        int cols = image[0].size();
        vector<vector<int>> result(image);   // 拷贝输入矩阵到输出矩阵
        if (srcColor == newColor)            // 需要边界条件：如果原值与目标值一样，应该立即返回，否则下面会陷入死循环
            return result;
        queue<pair<int, int>> queue;         // 用 std::pair 作为数据容器。但需要特别注意，这里 pair 的 first 是 y 而不是 x，因为数组是先定义行再定义列，导致列在行维度的外面。
        queue.push(make_pair(sr, sc));       // 循环开始前压入种子
        while(!queue.empty()) {
            int size = queue.size();            // BFS 务必要记录<当前队内元素数量>，每个循环清理同一层深度的所有节点。
            for (int i = 0; i < size; i++) {
                pair<int, int> pt = queue.front();
                queue.pop();
                result[pt.first][pt.second] = newColor;
                // down ↓
                if (pt.first + 1 < rows && result[pt.first + 1][pt.second] == srcColor) queue.push(make_pair(pt.first + 1, pt.second));
                // up ↑
                if (pt.first - 1 >= 0 && result[pt.first - 1][pt.second] == srcColor) queue.push(make_pair(pt.first - 1, pt.second));
                // right →
                if (pt.second + 1 < cols && result[pt.first][pt.second + 1] == srcColor) queue.push(make_pair(pt.first, pt.second + 1));
                // left ←
                if (pt.second - 1 >= 0 && result[pt.first][pt.second - 1] == srcColor) queue.push(make_pair(pt.first, pt.second - 1));
            }
        }
        return result;
    }

    // DFS + Stack
    vector<vector<int>> floodFill_DFS(vector<vector<int>>& image, int sr, int sc, int newColor) {

        int srcColor = image[sr][sc];
        int rows = image.size();
        int cols = image[0].size();
        vector<vector<int>> result(image);
        if (srcColor == newColor)            // 需要边界条件：如果原值与目标值一样，应该立即返回，否则下面会陷入死循环
            return result;
        stack<pair<int, int>> stack;
        stack.push(make_pair(sr, sc));
        while (!stack.empty()) {
            pair<int, int> pt = stack.top();
            stack.pop();
            result[pt.first][pt.second] = newColor;
            // down ↓
            if (pt.first + 1 < rows && result[pt.first + 1][pt.second] == srcColor) stack.push(make_pair(pt.first + 1, pt.second));
            // up ↑
            if (pt.first - 1 >= 0 && result[pt.first - 1][pt.second] == srcColor) stack.push(make_pair(pt.first - 1, pt.second));
            // right →
            if (pt.second + 1 < cols && result[pt.first][pt.second + 1] == srcColor) stack.push(make_pair(pt.first, pt.second + 1));
            // left ←
            if (pt.second - 1 >= 0 && result[pt.first][pt.second - 1] == srcColor) stack.push(make_pair(pt.first, pt.second - 1));
        }
        return result;
    }

    // 用偏移量简化代码（本质一样，需要花更多时间写，并不推荐）
    vector<vector<int>> floodFill_DFS2(vector<vector<int>>& image, int sr, int sc, int newColor) {
        int srcColor = image[sr][sc];
        int rows = image.size();
        int cols = image[0].size();
        vector<vector<int>> result(image);
        if (srcColor == newColor) 
            return result;
        stack<pair<int, int>> stack;
        stack.push({sr, sc});
        vector<pair<int, int>> offset = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};    // 偏移量：上，下，左，右
        while (!stack.empty()) {
            pair<int, int> pt = stack.top();
            stack.pop();
            result[pt.first][pt.second] = newColor;     // 先更新颜色
            for (auto s : offset) {                     // 后遍历 4 方向
                int r = pt.first + s.first;
                int c = pt.second + s.second;
                if (r < 0 || c < 0 || r >= rows || c >= cols || result[r][c] != srcColor)   // 剔除越界或颜色不对的方向
                    continue;
                stack.push({r, c});     // 压入合法元素
            }
        }
        return result;
    }

};

int main () {
    std::vector<int> row0 = {0, 0, 0};
    std::vector<int> row1 = {0, 1, 1};
    std::vector<std::vector<int>> mat = {row0, row1};
    Solution s;
    std::vector<std::vector<int>> result = s.floodFill_DFS(mat, 1, 1, 1);
    std::vector<std::vector<int>> result2 = s.floodFill_BFS(mat, 1, 1, 1);
}

