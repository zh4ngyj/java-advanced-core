package com.zh4ngyj.advanced.d_new_features.java21;

import java.time.Duration;

/**
 * 演示 Java 21 的虚拟线程 (Virtual Threads)
 * <p>
 * 编译和运行需要 JDK 21+，并开启预览特性。
 * 在 pom.xml 中需要配置 <compilerArgs>--enable-preview</compilerArgs>
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting virtual threads demo...");

        // 创建并启动 100,000 个虚拟线程
        int taskCount = 100_000;

        // 使用 try-with-resources 确保 executor 被关闭
        // try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        //     for (int i = 0; i < taskCount; i++) {
        //         final int taskId = i;
        //         executor.submit(() -> {
        //             // 虚拟线程非常轻量，适合执行 I/O 密集型任务
        //             System.out.println("Executing task " + taskId + " on thread " + Thread.currentThread());
        //             try {
        //                 Thread.sleep(Duration.ofSeconds(1));
        //             } catch (InterruptedException e) {
        //                 // Handle exception
        //             }
        //         });
        //     }
        // } // executor.close() 会等待所有任务完成

        // 更简单的启动方式
        Thread.startVirtualThread(() -> {
            System.out.println("Hello from a virtual thread: " + Thread.currentThread());
        });

        Thread.sleep(Duration.ofSeconds(2));
        System.out.println("Demo finished.");
    }
}