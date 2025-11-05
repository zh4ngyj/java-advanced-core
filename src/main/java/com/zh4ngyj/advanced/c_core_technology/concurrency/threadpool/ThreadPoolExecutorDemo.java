package com.zh4ngyj.advanced.c_core_technology.concurrency.threadpool;

import java.util.concurrent.*;

/**
 * 演示如何手动创建和配置 ThreadPoolExecutor
 * <p>
 * 阿里 Java 开发规范强制要求不使用 Executors 创建线程池，而是手动创建。
 * 这是因为 Executors 创建的线程池存在资源耗尽的风险（如 FixedThreadPool 的队列无限大）。
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        // 核心参数
        int corePoolSize = 2;       // 核心线程数
        int maximumPoolSize = 4;    // 最大线程数
        long keepAliveTime = 60;    // 空闲线程存活时间
        TimeUnit unit = TimeUnit.SECONDS; // 时间单位
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2); // 工作队列，容量为2
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy(); // 拒绝策略

        // 手动创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                Executors.defaultThreadFactory(), // 默认线程工厂
                handler
        );

        // 提交任务，观察线程池行为
        for (int i = 1; i <= 7; i++) {
            final int taskId = i;
            try {
                executor.submit(() -> {
                    System.out.println("Executing task " + taskId + " with thread " + Thread.currentThread().getName());
                    try {
                        // 模拟任务执行
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                System.out.println("Submitted task " + taskId);
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " was rejected.");
            }
        }

        executor.shutdown();
    }
}