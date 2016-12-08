package com.leetcode.regex;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Created by LYuan on 2016/10/21.
 * Scan and analyze java source files, and provide basic statistics of the source code.
 * Including: total lines, total source code lines, total comments lines, avg/min/max lines per files.
 *
 * 当前运行结果：（两个月不间断的努力劳动果实，平均每天150行。还是很有成就感滴！）
 * 1. 总行数：8965,
 * 2. 代码行数：5506,
 * 3. 注释行数：3459,
 * 4. 单文件最大行数：288,
 * 5. 单文件最小行数：22,
 * 6. 文件平均行数：74.
 * 7. 总字符数：266402.
 *
 */

public class Code_Analyzer {
    /** 测试：用户只需要给出要扫描的根目录绝对地址，然后run()就行了。 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give me the root folder you want to scan: ");
        String root = scanner.nextLine();
        Code_Analyzer analyzer = new Code_Analyzer(root);
        analyzer.run();
    }

    private ArrayList<Path> filePath;               // 根目录下存在的所有文件列表。
    private HashMap<Path, Statistics> fileStat;     // 以文件的Path对象为键，存储该文件内容的统计信息。
    private String root;                            // 用户提供的根目录绝对地址。
    private int file_count = 0;                         // 扫描文件个数。

    /** Constructor */
    Code_Analyzer(String search_path) {
        filePath = new ArrayList<>();
        fileStat = new HashMap<>();
        root = search_path;
    }

    /** Wrapper方法，负责将几个操作串联起来（扫描分析加统计数据） */
    public void run() {
        getAllFiles();
        scan();
        getAllStat();
    }

    /** 汇总fileStat的所有统计信息，将结果呈现给用户。 */
    private void getAllStat() {
        int total = 0;
        int comments = 0;
        int code = 0;
        int characters = 0;
        int max = 0;
        int min = Integer.MAX_VALUE;
        int avg = 0;
        for (Path p : filePath) {
            Statistics stat = fileStat.get(p);
            total += stat.total;
            comments += stat.comments;
            code += stat.code;
            characters += stat.characters;
            max = Math.max(stat.total, max);
            min = Math.min(stat.total, min);
        }
        avg = total / filePath.size();
        System.out.println("来自" + file_count + "个文件的代码统计信息：");
        System.out.println("1. 总行数：" + total + ", ");
        System.out.println("2. 代码行数：" + code + ", ");
        System.out.println("3. 注释行数：" + comments + ", ");
        System.out.println("4. 单文件最大行数：" + max + ", ");
        System.out.println("5. 单文件最小行数：" + min + ", ");
        System.out.println("6. 文件平均行数：" + avg + ",");
        System.out.println("7. 总字符数：" + characters + ".");

    }

    /** 使用Files.readAllLines()直接读取小文件的所有内容。并同时交给parse()进行解析和统计。 */
    private void scan() {
        for (Path path : filePath) {
            try {
                file_count++;
                List<String> content = Files.readAllLines(path);
                for (String line : content) {
                    parse(path, line);
                }
            } catch (IOException x) {
                System.err.print(x);
            }
        }
    }

    /** 解析每一行的内容，根据内容类别统计至fileStat哈希表中所属文件的统计信息中。 */
    private void parse(Path p, String s) {
        if (!fileStat.containsKey(p)) fileStat.put(p, new Statistics());
        Statistics stat = fileStat.get(p);

        // 匹配空行：如果该行长度为0或只有空白字符，则判定为空行，不进行统计。
        if (s.length() == 0 || s.equals("") || s.matches("\\s*")) return;

        // 匹配注释：如果该行的起始非空白字符是//, /**, */, *，则判定为注释
        if (s.matches("\\s*(//|/\\*\\*|\\*/|\\*).*")) {
            stat.comments++;
            stat.total++;
        }

        // 匹配代码
        else {
            stat.code++;
            stat.total++;
        }

        // 统计字数，去掉首尾空格。
        String cut = s.trim();
        stat.characters += cut.length();
    }

    /** 从目录堆栈dirPath取path，放到newDirectoryStream中读取这个path下的所有内容，依次判断是目录还是文件，
     *  如果是目录，就继续压入目录堆栈dirPath，
     *  如果是文件，就加入文件列表filePath。
     *  循环直至目录堆栈清空。 */
    private void getAllFiles() {
        Path dir = Paths.get(root);

        Deque<Path> dirPath = new ArrayDeque<>();
        dirPath.push(dir);
        while (!dirPath.isEmpty()) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath.pop())) {
                for (Path file : stream) {
                    boolean isDir = Files.isDirectory(file);
                    if (isDir) dirPath.push(file.toAbsolutePath());
                    else {
                        filePath.add(file.toAbsolutePath());
                        // System.out.println(file.toAbsolutePath().toString());
                    }
                }
            } catch (IOException | DirectoryIteratorException x) {
                System.err.println(x);
            }
        }
    }
}

/** 一个专门用来存储每个文件内容统计信息的数据结构。 */
class Statistics {
    public int total;
    public int code;
    public int comments;
    public int characters;
    Statistics() {
        total = 0;
        code = 0;
        comments = 0;
        characters = 0;
    }
}
