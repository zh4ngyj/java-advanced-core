# 模块: JVM 内存模型 (Runtime Data Areas)

理解JVM内存模型是进行JVM调优和解决内存相关问题（如OOM）的基础。

JVM在执行Java程序时，会把它所管理的内存划分为若干个不同的数据区域。

---

## JVM 运行时数据区

### 线程私有区域 (Thread-Private)
生命周期与线程相同。

1.  **程序计数器 (Program Counter Register)**
    - **作用**: 当前线程所执行的字节码的行号指示器。
    - **特点**:
        - 唯一一个在Java虚拟机规范中没有规定任何 `OutOfMemoryError` 情况的区域。
        - 如果线程正在执行的是一个Java方法，这个计数器记录的是正在执行的虚拟机字节码指令的地址；如果正在执行的是本地（Native）方法，这个计数器值则为空（Undefined）。

2.  **Java 虚拟机栈 (Java Virtual Machine Stack)**
    - **作用**: 存储局部变量表、操作数栈、动态链接、方法出口等信息。每个方法在执行时都会创建一个栈帧（Stack Frame）用于存储这些数据。
    - **异常**:
        - `StackOverflowError`: 如果线程请求的栈深度大于虚拟机所允许的深度。
        - `OutOfMemoryError`: 如果虚拟机栈可以动态扩展，但在扩展时无法申请到足够的内存。

3.  **本地方法栈 (Native Method Stack)**
    - **作用**: 与虚拟机栈类似，但它为虚拟机使用到的本地（Native）方法服务。

---

### 线程共享区域 (Thread-Shared)
随虚拟机的启动而创建。

4.  **Java 堆 (Java Heap)**
    - **作用**: 存放对象实例和数组。是垃圾收集器管理的主要区域。
    - **特点**:
        - 所有线程共享，是JVM内存中最大的一块。
        - 可以在物理上不连续，但逻辑上要连续。
        - 也是产生 `OutOfMemoryError` 的主要区域。
    - **分代**: 通常分为新生代（Young Generation）和老年代（Old Generation）以便于进行垃圾回收。

5.  **方法区 (Method Area)**
    - **作用**: 存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。
    - **别名**: 也被称为“永久代”（Permanent Generation）在HotSpot虚拟机（JDK 7及以前）的实现中。
    - **演进**:
        - **JDK 7**: 将字符串常量池和静态变量从永久代移到了Java堆中。
        - **JDK 8**: 废除了永久代，引入了**元空间 (Metaspace)**，它使用的是本地内存（Native Memory）而不是JVM内存。
    - **运行时常量池 (Runtime Constant Pool)**: 方法区的一部分，用于存放编译期生成的各种字面量和符号引用。

---

## 常见内存问题
- **`OutOfMemoryError: Java heap space`**: 堆内存不足，通常是由于创建了大量的对象且无法被GC回收。
    - **示例**: `OutOfMemoryDemo.java`
- **`StackOverflowError`**: 栈内存溢出，通常是由于无限递归调用导致栈深度过大。
    - **示例**: `StackOverflowDemo.java`

本包中的示例代码旨在通过实践重现这些经典的内存问题，加深理解。