package com.zh4ngyj.advanced.d_new_features.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamApiDemo {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Stream", "API", "is", "powerful", "and", "elegant");

        // 示例：找出长度大于3的单词，转换为大写，并用逗号连接
        String result = words.stream()                  // 1. 创建流
                .filter(word -> word.length() > 3)      // 2. 中间操作：过滤
                .map(String::toUpperCase)               // 3. 中间操作：转换
                .sorted()                               // 4. 中间操作：排序
                .collect(Collectors.joining(", ")); // 5. 终端操作：收集结果

        System.out.println("Original list: " + words);
        System.out.println("Processed string: " + result);
    }
}