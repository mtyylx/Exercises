package com.leetcode.backtracking;

/**
 * Created by LYuan on 2016/11/16.
 *
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * For example,
 * Given board =
 * [
 *  ['A','B','C','E'],
 *  ['S','F','C','S'],
 *  ['A','D','E','E']
 * ]
 *
 * word = "ABCCED", -> returns true,
 * word = "SEE", -> returns true,
 * word = "ABCB", -> returns false.
 *
 * Function Signature:
 * public boolean wordSearch(char[][] map, String word) {...}
 */
public class M79_Word_Search {
    public static void main(String[] args) {
        char[] row1 = new char[]{'A', 'A', 'A', 'A'};
        char[] row2 = new char[]{'A', 'A', 'A', 'A'};
        char[] row3 = new char[]{'A', 'A', 'A', 'A'};
        char[][] map = new char[][] {row1, row2, row3};
        System.out.println(wordSearch(map, "AAAAAAAAAAAA"));
    }

    /** 回溯法，无效路径剪枝，time - o((nm)^2), space - o(nm), 会修改原矩阵 */
    /** 从树的角度，也可以算是DFS，因为遍历每个点的最深路径，一旦所有路径都宣告失败，就换个点重新开始。 */
    // 针对map上的每一个点，如果当前点match，就递归扩展至其4个可能方向，排除已访问的所有点，排除越界。
    // 如果想要不修改原矩阵，那么就需要额外定义一个和原矩阵一样尺寸的矩阵，将已经访问的节点标记为true，并且在递归结束后最后恢复为false。
    // 如果想要用迭代形式写法，就需要额外定义一个栈来存储这些状态信息。
    static boolean wordSearch(char[][] map, String word) {
        if (word == null || word.length() == 0) return true;            // early exit.
        if (word.length() > map.length * map[0].length) return false;   // early exit.
        // 遍历整个map，以每个元素作为起点进行尝试，只要找到一个有效的match路径就返回。
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                if (wordSearch_Recursive(map, word, i, j, 0))           // early exit when valid path is found.
                    return true;
        return false;
    }

    static boolean wordSearch_Recursive(char[][] map, String word, int row, int col, int idx) {
        if (idx == word.length()) return true;                  // word扫描完毕，说明找到了有效路径，成功。
        if (row < 0 || row >= map.length) return false;         // 越界，失败
        if (col < 0 || col >= map[0].length) return false;      // 越界，失败
        if (map[row][col] != word.charAt(idx)) return false;    // 不match，失败
        // 能走到这，说明这条路起码到现在还是有效的，因此有必要继续。
        char temp = map[row][col];      // 为了避免回环，因此把所有匹配成功的元素都缓存，并临时修改为一个不可能再被匹配到的值。
        map[row][col] = ' ';
        if (wordSearch_Recursive(map, word, row + 1, col, idx + 1)) return true;    // 递归四个可能方向。也可以写成一长串或语句，执行逻辑一样。
        if (wordSearch_Recursive(map, word, row, col + 1, idx + 1)) return true;
        if (wordSearch_Recursive(map, word, row - 1, col, idx + 1)) return true;
        if (wordSearch_Recursive(map, word, row, col - 1, idx + 1)) return true;
        map[row][col] = temp;           // 能走到这，说明当前这条路往下发展的所有4个方向都是无效的，因此无需继续，需要恢复当前节点值，以供其他起点的尝试。
        return false;
    }
}
