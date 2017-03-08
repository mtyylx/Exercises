package com.leetcode.string;

import java.io.*;
import java.util.*;

/**
 * Created by Michael on 2017/3/5.
 *
 * Practise Question From Google Kickstart 2017 Code Jam.
 *
 */
public class Google_Country_Leader {
    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\JavaWorkSpace\\Leetcode\\src\\com\\leetcode\\A-small-practice.in"))) {
            String s;
            while ((s = reader.readLine()) != null)      // null means EOF
                test.add(s);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String[] a = new String[test.size()];
        for (int i = 0; i < a.length; i++) a[i] = test.get(i);
        countryLeader(a);
    }

    static void countryLeader(String[] input) {
        if (input == null || input.length < 1 || input[0].equals("0")) return;
        int id = 1;
        for (int i = 1; i < input.length; i++) {
            int len = Integer.valueOf(input[i]);
            String leader = getLeader(input, i + 1, i + len);
            System.out.println("Case #" + (id++) + ": " + leader);
            i += len;
        }
    }

    // start and end are inclusive.
    static String getLeader(String[] input, int start, int end) {
        List<String> candidates = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = start; i <= end; i++) {
            int count = getUniqueLetterCount(input[i]);
            max = Math.max(max, count);
            map.put(input[i], count);
        }
        for (String s : map.keySet())
            if (map.get(s) == max) candidates.add(s);
        if (candidates.size() == 1) return candidates.get(0);
        candidates.sort(Comparator.naturalOrder());
        return candidates.get(0);
    }

    static int getUniqueLetterCount(String s) {
        Set<Character> set = new HashSet<>();
        for (Character c : s.toCharArray()) set.add(c);
        return set.size();
    }
}
