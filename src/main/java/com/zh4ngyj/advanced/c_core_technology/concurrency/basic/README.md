# 模块: 并发基础 (Concurrency Basics)

本模块涵盖了 Java 并发编程最基础、最核心的同步机制：`synchronized` 和 `volatile` 关键字。

## 1. `synchronized` 关键字
`synchronized` 是 Java 中用于实现原子性和可见性的内置锁机制。

### a. 用法
1.  **修饰实例方法**: 锁是当前实例对象 (`this`)。
2.  **修饰静态方法**: 锁是当前类的 `Class` 对象。
3.  **修饰代码块**: 锁是括号中指定的对象。

### b. 底层原理
- **Monitor (管程)**: 每个 Java 对象都可以作为一个锁，这个锁被称为内置锁或监视器锁 (Monitor)。
- **字节码层面**:
    - **同步代码块**: 通过 `monitorenter` 和 `monitorexit` 两个字节码指令实现。
    - **同步方法**: 通过方法flags中的 `ACC_SYNCHRONIZED` 标志位实现。
- **锁升级 (JDK 1.6+ 优化)**:
  为了提高性能，`synchronized` 锁会经历一个从低到高的升级过程，而不是一开始就是重量级的。
    1.  **偏向锁 (Biased Locking)**: 锁会偏向于第一个获取它的线程。在此线程后续的访问中，无需再进行同步操作。
    2.  **轻量级锁 (Lightweight Locking)**: 当有另一个线程竞争锁时，偏向锁会升级为轻量级锁。线程通过自旋 (CAS) 的方式尝试获取锁，避免了阻塞和唤醒的开销。
    3.  **重量级锁 (Heavyweight Locking)**: 如果自旋一定次数后仍未获取到锁，或者有更多线程竞争，锁会膨胀为重量级锁。此时，未获取到锁的线程会被阻塞，并交由操作系统调度。

## 2. `volatile` 关键字
`volatile` 是一个轻量级的同步机制，主要用于保证共享变量的**可见性**和**有序性**。

### a. 可见性 (Visibility)
- **原因**: JMM (Java Memory Model) 规定，每个线程有自己的工作内存。线程对变量的修改可能先保存在工作内存中，对其他线程不可见。
- **`volatile` 的作用**:
    1.  当一个线程修改 `volatile` 变量时，会强制将修改后的值立即写回主内存。
    2.  当一个线程读取 `volatile` 变量时，会强制从主内存中重新加载最新的值，而不是使用工作内存中的缓存。

### b. 有序性 (Ordering)
- **原因**: 编译器和处理器为了优化性能，可能会对指令进行重排序。
- **`volatile` 的作用**:
    - **禁止指令重排序**: `volatile` 通过插入内存屏障 (Memory Barrier) 来防止其前后的指令被重排序，确保了代码的执行顺序。
    - 这在 DCL (Double-Checked Locking) 单例模式等特定场景下至关重要。

### `volatile` vs. `synchronized`
- **原子性**: `volatile` **不保证**复合操作的原子性（如 `i++`）。`synchronized` 保证其代码块内的操作是原子的。
- **性能**: `volatile` 是非阻塞的，开销比 `synchronized` 小。
- **使用场景**:
    - `volatile` 适用于“一写多读”且写操作不依赖于当前值的场景。
    - `synchronized` 适用于需要保证原子性的复杂操作场景。