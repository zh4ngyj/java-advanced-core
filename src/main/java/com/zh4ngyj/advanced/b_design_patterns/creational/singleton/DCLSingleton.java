package com.zh4ngyj.advanced.b_design_patterns.creational.singleton;

/**
 * 双重检查锁 (Double-Checked Locking) 实现单例模式
 * <p>
 * 优点：线程安全，且在多线程情况下能保持高性能。
 * <p>
 * 注意：必须配合 volatile 关键字，防止指令重排。
 */
public class DCLSingleton {

    // 1. volatile 关键字确保 instance 在多线程环境下的可见性和有序性
    // 如果没有 volatile，可能会因为指令重排，导致其他线程拿到一个未完全初始化的对象
    private static volatile DCLSingleton instance;

    // 2. 私有化构造函数，防止外部直接创建实例
    private DCLSingleton() {
        System.out.println("DCLSingleton instance created.");
    }

    /**
     * 3. 提供全局访问点
     * 第一次检查：避免不必要的同步开销。
     * 第二次检查：在同步块内确保只有一个实例被创建。
     */
    public static DCLSingleton getInstance() {
        // 第一次检查：如果实例已经存在，直接返回，避免进入同步块
        if (instance == null) {
            // 同步块：确保只有一个线程能创建实例
            synchronized (DCLSingleton.class) {
                // 第二次检查：防止多个线程同时通过第一次检查后，重复创建实例
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from DCLSingleton!");
    }
}