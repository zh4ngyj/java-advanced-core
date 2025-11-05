package com.zh4ngyj.advanced.c_core_technology.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示 JVM 内存溢出 (OOM)
 * <p>
 * 运行配置 (VM Options): -Xmx20m -Xms20m
 * -Xmx20m: 设置堆最大内存为 20MB
 * -Xms20m: 设置堆初始内存为 20MB
 */
public class OutOfMemoryDemo {

    // 内部类，用于占用内存
    static class MemoryObject {
        // 每个对象大约占用 1MB 内存
        private byte[] data = new byte[1024 * 1024];
    }

    public static void main(String[] args) {
        List<MemoryObject> objects = new ArrayList<>();
        System.out.println("Starting to allocate memory...");
        try {
            while (true) {
                objects.add(new MemoryObject());
                System.out.println("Allocated " + objects.size() + " MB.");
            }
        } catch (OutOfMemoryError e) {
            System.err.println("Caught OutOfMemoryError!");
            System.err.println("Total allocated objects: " + objects.size());
            e.printStackTrace();
        } finally {
            System.out.println("Program finished.");
        }
    }
}