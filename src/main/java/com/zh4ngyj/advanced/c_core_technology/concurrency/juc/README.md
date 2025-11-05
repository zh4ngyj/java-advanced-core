# 模块: JUC (java.util.concurrent)

`java.util.concurrent` (JUC) 包是 Java 并发编程的核心，提供了大量高级的并发工具和框架。

## 1. `Lock` 接口
`Lock` 提供了比 `synchronized` 更强大、更灵活的锁机制。

- **`ReentrantLock` (可重入锁)**:
    - **特性**: 可重入、可中断、可设置公平/非公平。
    - **公平锁 vs. 非公平锁**:
        - **公平锁**: 线程按照请求锁的顺序获取锁。
        - **非公平锁** (默认): 允许“插队”，新请求的线程可能比队列中等待的线程先获取锁，吞吐量更高。
    - **与 `synchronized` 的区别**:
        - `ReentrantLock` 需要手动加锁 (`lock()`) 和释放锁 (`unlock()`)，通常在 `finally` 块中释放。
        - `ReentrantLock` 提供更多高级功能，如尝试获取锁 (`tryLock`)、可中断获取锁等。

## 2. AQS (`AbstractQueuedSynchronizer`)
AQS 是 JUC 中大多数同步器（如 `ReentrantLock`, `Semaphore`, `CountDownLatch`）的实现基础。

- **核心思想**:
    - 维护一个 `volatile int state` 变量作为同步状态，以及一个 FIFO 的线程等待队列。
    - 通过 `CAS` (Compare-And-Swap) 原子地修改 `state` 变量。
    - 定义了两种资源共享模式：
        - **独占模式 (Exclusive)**: 只有一个线程能持有锁，如 `ReentrantLock`。
        - **共享模式 (Shared)**: 多个线程可以同时持有锁，如 `Semaphore`, `CountDownLatch`。
- **原理**: 想要获取锁的线程如果失败，会被封装成一个节点加入等待队列并挂起。当锁被释放时，会唤醒队列中的后继节点。

## 3. 并发工具类

- **`CountDownLatch` (倒计时门闩)**:
    - **作用**: 让一个或多个线程等待其他一组线程完成操作后再继续执行。
    - **原理**: 内部有一个计数器，`await()` 方法会阻塞，直到计数器通过 `countDown()` 方法减到 0。计数器不能重置。

- **`CyclicBarrier` (循环屏障)**:
    - **作用**: 让一组线程互相等待，直到所有线程都到达一个公共的屏障点，然后所有线程再一起继续执行。
    - **与 `CountDownLatch` 的区别**:
        - `CountDownLatch` 是一次性的，`CyclicBarrier` 可以通过 `reset()` 方法重用。
        - `CountDownLatch` 是一个或多个线程等其他线程，`CyclicBarrier` 是线程互相等待。

- **`Semaphore` (信号量)**:
    - **作用**: 控制同时访问某个特定资源的线程数量。
    - **原理**: 维护一个许可（permits）集。线程通过 `acquire()` 获取许可，通过 `release()` 释放许可。如果许可数量为 0，`acquire()` 会阻塞。
    - **应用**: 可以用于实现流量控制或资源池。

## 4. `Atomic` 原子类
- **作用**: 提供了一系列原子操作的类，位于 `java.util.concurrent.atomic` 包下。
- **核心原理**: **CAS (Compare-And-Swap)**，这是一种乐观锁机制。
    - **CAS 操作包含三个操作数**: 内存位置 V、预期原值 A、新值 B。如果内存位置 V 的值与预期原值 A 相匹配，那么处理器会自动将该位置的值更新为新值 B。否则，不做任何操作。
    - **ABA 问题**: CAS 只能检查值是否被改变，但无法知道是否被改回过。可以通过 `AtomicStampedReference` 解决。