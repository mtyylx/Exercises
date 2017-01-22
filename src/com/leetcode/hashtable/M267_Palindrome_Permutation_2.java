package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by Michael on 2017/1/22.
 * Given a string s, return all the palindromic permutations (without duplicates) of it.
 * Return an empty list if no palindromic permutation could be form.
 * 生成的所有字符串的字符必须与原字符串完全一致。
 *
 * For example:
 * Given s = "aabb", return ["abba", "baab"].
 * Given s = "abc", return [].
 *
 * Hint:
 * 1. If a palindromic permutation exists, we just need to generate the first half of the string.
 * 2. To generate all distinct permutations of a (half of) string, use a similar approach from: Permutations II.
 *
 * Function Signature:
 * public List<String> getAllPalindromes(String s) {...}
 *
 * <Palindrome系列问题>
 * E9   Palindrome Number
 * E125 Palindrome String
 * E234 Palindrome LinkedList
 * E266 Palindrome Permutation
 * M267 Palindrome Permutation II
 * E409 Longest Palindrome
 *
 * <Tags>
 * - HashMap：Key → 字符，Value → 出现次数。
 * - Value-As-Index: 模拟HashMap统计字符频率分布。
 * - Backtracking: 路径增删 / 记录索引而不是内容 / 跳过同层相等元素
 *
 */
public class M267_Palindrome_Permutation_2 {
    public static void main(String[] args) {
        System.out.println(getAllPalindromes("abcabc").toString());
        System.out.println(getAllPalindromes2("aab").toString());
    }

    /** 解法2：HashMap统计词频 + Backtracking。简化解法1的逻辑。 */
    // 起初想把逻辑设计成一旦哈希表中的字符对应出现次数为0就删除该KVP，
    // 但是这么做会导致用foreach遍历Key时出现ConcurrentModification，只能通过新建一个拷贝的HashMap来workaround，不是一个好方法。
    // 现在改成先检查key值，只要为0就跳过。由于我们foreach的时keySet，而修改的是Values，因此就巧妙的避开了上面的问题
    static List<String> getAllPalindromes2(String s) {
        List<String> result = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
        char odd = 0;
        int count = 0;
        for (char c : map.keySet()) {
            if (map.get(c) % 2 != 0) { odd = c; count++; }  // 取奇数次字符的值本身，并记录是否有多于1个奇数次字符
            map.put(c, map.get(c) / 2);                     // 对于只出现1次的字符，会自动降为0
        }
        if (count > 1) return result;
        getPermutations2(map, result, new StringBuilder(), s.length() / 2, odd);    // 不管字符串是奇是偶，达标长度一定是除2
        return result;
    }

    // 同样是Backtracking，相比于解法1：
    // 区别1：这里使用的不是构造好的List<Character>，而是HashMap<Character, Integer>，这样的好处是不用手动跳过同一层的相同元素了，HashMap自动把相同元素归类了。
    // 区别2：把生成镜像的功能也集成进来，所以整体比解法1更短。
    // map记录可用字符集，会被实时修改
    // result用作结果集合，所有递归层次共享
    // path用来记录当前走过的路径（做出的选择）
    // len作为递归终止条件，即字符串的一半长度，例如源字符串长度为3，那么path长到1就可以返回，如果长度为4，那么path长到2就可以返回
    // odd作为返回时构造镜像字符串中心点（仅当字符串拥有奇数次字符时才起作用）
    private static void getPermutations2(Map<Character, Integer> map, List<String> result, StringBuilder path, int len, char odd) {
        if (path.length() == len) {
            StringBuilder sb = new StringBuilder(path);
            if (odd != 0) sb.append(odd);
            for (int i = path.length() - 1; i >= 0; i--)   // 直接生成完整的镜像字符串
                sb.append(sb.charAt(i));
            result.add(sb.toString());
            return;
        }
        for (char c : map.keySet()) {
            if (map.get(c) == 0) continue;                  // 次数用尽，直接跳过
            map.put(c, map.get(c) - 1);
            path.append(c);
            getPermutations2(map, result, path, len, odd);
            map.put(c, map.get(c) + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }


    /** 解法1：Value-As-Index统计字符频率分布 + Backtracking. Time - o(n), Space - o(n). */
    // 完全将问题转化为：给定一个字符串，求这个字符串的所有不重复的排列组合。
    // 需要几个环节：
    // 1. 首先判断字符串是否符合Palindrome构造要求：奇数次字符至多1个。
    // 2. 如果有奇数次字符，需要记忆这个字符是什么，并将该字符的1个从当前字符串刨除，以供最后放在字符串正中间。
    // 3. 删除字符串相同字符的一半，构造待排列组合数组。
    // 4. 对待排列组合数组使用Backtracking，跳过重复的连续字符，构造排列组合。
    // 5. 新构造出的字符串每个都翻转相连，返回字符串集合。
    // [ababababccc] -> c + [aabbc]*2 -> c + [aabbc/aabcb/aacbb/...]*2 -> [aabbcccbbaa/aabcbcbcbaa/...]
    static List<String> getAllPalindromes(String s) {
        List<String> result = new ArrayList<>();
        // Step #1. 检查是否满足Palindrome要求
        int[] map = new int[128];
        for (char c : s.toCharArray()) map[c]++;
        char odd = 0;
        int count = 0;                              // 判定奇数次字符的个数
        for (int i = 0; i < map.length; i++) {
            if (map[i] % 2 != 0) { odd = (char) map[i]; count++; }
            map[i] /= 2;
        }
        if (count > 1) return result;
        // Step #2. 构造半个字符串
        List<Character> pool = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            if (map[i] == 0) continue;
            for (int j = 0; j < map[i]; j++)
                pool.add((char) i);
        }
        // Step #3. 根据半个字符串构造其排列组合
        getPermutations(pool, result, new ArrayList<>());
        // Step #4. 根据奇偶情况构造排列组合的完整结果。
        for (int i = 0; i < result.size(); i++) {
            String half = result.get(i);
            int len = half.length();
            StringBuilder sb = new StringBuilder(half);
            if (odd != 0) sb.append(odd);               // 如果有奇数字符，先添加至中间位置
            for (int j = len - 1; j >= 0; j--)          // 反向扫描生成最终的镜像字符串
                sb.append(half.charAt(j));
            result.set(i, sb.toString());               // 将镜像字符串替换half字符串至result集合中
        }
        return result;
    }


    /** 使用Backtracking
     * 关键点1：<选过的位置不能再选>。通过让<当前路径Path>记录元素的<索引>，而不是元素的<内容>来实现。
     * 关键点2：<同一层深度的相等元素跳过>，以避免记录重复的path，尽快剪枝。*/
    // 例如数据源是[a a b]，那么因为不是求子集，因此path必须增加至与数据源长度完全相同才算完毕
    // 第一层可选[a,a,b]，那么我们来分析下去重的原理：
    // 第一层选定第一个a，则第二层可选的就是[a,b]。第二层选第二个a那么第三层只能选b，第二层选b的话那么第三层只能选a，因此结果有[a,a,b]和[a,b,a]
    // 从这个过程我们可以看到，我们需要实现“选过了就不能再选的功能”，而这通过记录元素内容到path上是无法保证的，因为可能有相同元素在不同位置上
    // 因此我们就需要记录位置，而不是内容，并且每次选择元素的时候，都确保是已经走过路径中还没选过的元素，即!path.contains(i).
    // 第一层选第二个a，那么实际的过程和选第一个a是完全一样的，结果重复，没必要继续，应该根本就不进去以节省计算。
    private static void getPermutations(List<Character> source, List<String> result, List<Integer> path) {
        if (path.size() == source.size()) {                 // 如果路径长度已经饱满，就输出为字符串
            StringBuilder sb = new StringBuilder();
            for (int x : path) sb.append(source.get(x));
            result.add(sb.toString());
            return;
        }
        for (int i = 0; i < source.size(); i++) {
            if (path.contains(i)) continue;                 // 每次都检查是否已经使用过，所以这个for循环实际是o(n^2)
            path.add(i);                                    // 路径增删法：添加的是source的索引值
            getPermutations(source, result, path);
            path.remove(path.size() - 1);
            while (i < source.size() - 1 && source.get(i) == source.get(i + 1)) i++;    // 同一级深度跳过相同元素，去重
        }
    }


}
