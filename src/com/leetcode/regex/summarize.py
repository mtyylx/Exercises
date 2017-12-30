#!/usr/bin/python
# coding=UTF-8

import os
import sys


# 用法示例：统计leetcode目录下所有 .java 为后缀的文件的字数统计
# 可以省略后缀参数，默认后缀为 .md
# >>> python summarize.py D:\JavaWorkSpace\Leetcode\src\com\leetcode .java 


def get_char_statistics(line):
    chinese_count = 0
    ascii_count = 0
    for c in line:
        if u'\u4e00' <= c <= u'\u9fff' or u'\u3000' <= c <= u'\u303f': #CJK字符范围以及CJK标点符号范围
            chinese_count += 1
        else:
            ascii_count += 1
    return chinese_count, ascii_count
	
	
def get_file_statistics(path, extension_whitelist, strict_mode=True):
    extension = os.path.splitext(path)[1]
    char_count = [0, 0]
    line_count = 0
    file_count = 0
    if extension in extension_whitelist:
        file_count = 1
        with open(path, mode='rb') as f:
            for line in f:
                content = line.decode('utf-8')
                if strict_mode:
                    content = content.strip() # 严格模式：不计算每行的首尾空白字符
                chinese_count, ascii_count = get_char_statistics(content)
                char_count[0] += chinese_count
                char_count[1] += ascii_count
                line_count += 1
    return char_count, line_count, file_count

# get_file_statistics('D:\\a\\ML\\工具\\0.Anaconda.md', ['.md'])


def get_folder_statistics(root, ext, log=False):
    file_result = 0
    char_result = [0, 0]
    line_result = 0
    for dirName, subdirList, fileList in os.walk(root):
        if log:
            print('Found directory: %s' % dirName)
        for filename in fileList:
            fullpath = os.path.join(dirName, filename)
            char_count, line_count, file_count = get_file_statistics(fullpath, ext)
            char_result[0] += char_count[0]
            char_result[1] += char_count[1]
            line_result += line_count
            file_result += file_count
            if log:
                print('\t%s' % fullpath)
    print('目录 <%s> 的文本统计信息:\n' % root)
    print('1. 总文件个数 =', file_result)
    print('2. 总文本行数 =', line_result)
    print('3. 总字符数 =', char_result[0] + char_result[1])
    print('4. 中文字符 =', char_result[0])
    print('5. 英文字符 =', char_result[1])
            
# get_folder_statistics(basedir, ['.md', '.txt'])

if len(sys.argv) == 2:
	get_folder_statistics(sys.argv[1], '.md')
elif len(sys.argv) == 3:
 	get_folder_statistics(sys.argv[1], sys.argv[2])