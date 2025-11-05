## Java 高级核心技术

---

### 1. 扎实的 Java 基础，精通面向对象编程（OOP）思想和常用设计模式

这部分考察的是你代码设计和抽象的能力。

#### **Java 基础 (扎实)**
*   **关键字原理：**
    *   `static`: 静态绑定、类加载时机、与实例变量/方法的区别。
    *   `final`: 修饰变量（不可变性）、方法（不可覆盖）、类（不可继承）的底层原理。
    *   `volatile`: 保证内存可见性和防止指令重排，与 `synchronized` 的区别。
    *   `transient`: 在序列化中的作用，为什么需要它。
*   **异常处理机制：**
    *   `Exception` 和 `Error` 的区别，`Checked Exception` 和 `Unchecked Exception` 的区别及使用场景。
    *   `try-with-resources` 的原理和优势。
    *   最佳实践：应该捕获还是抛出？如何设计自定义异常？
*   **泛型：**
    *   类型擦除（Type Erasure）的原理。
    *   `? extends T` 和 `? super T` (PECS - Producer Extends, Consumer Super) 的使用场景和区别。
    *   泛型方法和泛型类的设计。
*   **反射（Reflection）：**
    *   核心类：`Class`, `Constructor`, `Method`, `Field`。
    *   应用场景：框架（如 Spring IoC）、动态代理。
    *   缺点：性能开销、安全性问题。

#### **面向对象编程 (OOP) (精通)**
*   **三大特性：**
    *   **封装 (Encapsulation):** 不仅仅是 `private` + `getter/setter`，而是如何隐藏内部实现，提供稳定的接口。
    *   **继承 (Inheritance):** 优缺点是什么？何时用组合（Composition）替代继承？
    *   **多态 (Polymorphism):** 编译时多态（重载）和运行时多态（重写）的实现原理。
*   **SOLID 原则：** 这是高级工程师必备的设计思想。
    *   **S (单一职责原则):** 如何界定一个类的职责。
    *   **O (开闭原则):** 如何通过抽象和扩展来应对需求变化，而不是修改已有代码。
    *   **L (里氏替换原则):** 子类如何做到不破坏父类的行为约定。
    *   **I (接口隔离原则):** 设计小而专的接口。
    *   **D (依赖倒置原则):** 面向接口编程，而非面向实现编程，与控制反转（IoC）/依赖注入（DI）的关系。

#### **常用设计模式 (精通)**
*   **创建型模式：**
    *   **单例模式 (Singleton):** 熟练掌握懒汉、饿汉、双重检查锁（DCL）、静态内部类、枚举等实现方式，并能分析其线程安全性。
    *   **工厂模式 (Factory):** 简单工厂、工厂方法、抽象工厂的区别和适用场景。
    *   **建造者模式 (Builder):** 如何优雅地构建复杂对象，与链式调用的关系。
*   **结构型模式：**
    *   **代理模式 (Proxy):** 静态代理和动态代理（JDK Proxy, CGLIB）的原理及应用（如 Spring AOP）。
    *   **装饰器模式 (Decorator):** 与继承的区别，在 `java.io` 包中的应用。
    *   **适配器模式 (Adapter):** 如何兼容不兼容的接口。
*   **行为型模式：**
    *   **策略模式 (Strategy):** 如何优雅地切换算法或行为，与 `if-else` 的对比。
    *   **观察者模式 (Observer):** GUI、事件监听、消息队列中的应用。
    *   **模板方法模式 (Template Method):** 固定算法骨架，延迟实现细节。

---

### 2. 深入理解 Java 核心技术

这部分考察的是你对 Java 底层和并发处理的能力，是高级岗位的核心竞争力。

#### **集合框架 (深入理解)**
*   **核心接口：** `Collection`, `List`, `Set`, `Map` 的继承体系和设计思想。
*   **主要实现类源码分析：**
    *   `ArrayList` vs `LinkedList`: 底层数据结构、增删改查的时间复杂度、适用场景。`ArrayList` 动态扩容机制。
    *   `HashMap` (重点): 底层数据结构（数组+链表/红黑树）、`put`/`get` 过程、哈希冲突解决方法、`loadFactor`（负载因子）和 `threshold`（扩容阈值）、1.7 和 1.8 版本的区别（头插法 vs 尾插法、引入红黑树）。
    *   `ConcurrentHashMap` (重点): 如何实现分段锁（1.7）和 CAS+`synchronized`（1.8）来保证线程安全和高性能。
    *   `HashSet` 和 `HashMap` 的关系。

#### **多线程并发编程 (深入理解)**
*   **JMM (Java Memory Model):**
    *   **三大特性：** 原子性、可见性、有序性。
    *   `happens-before` 原则：理解其规则，用于判断数据竞争和线程安全问题。
*   **线程与线程池：**
    *   `Thread` 状态转换。
    *   `synchronized` 关键字：锁升级过程（偏向锁 -> 轻量级锁 -> 重量级锁）。
    *   `volatile` 关键字：内存可见性和防止指令重排。
    *   `Lock` 接口：`ReentrantLock` 的公平/非公平锁、可重入性、与 `synchronized` 的区别。
    *   `ThreadPoolExecutor` (重点): 核心参数（`corePoolSize`, `maximumPoolSize`, `workQueue` 等）的含义、线程池工作流程、拒绝策略。
*   **并发工具类 (JUC - `java.util.concurrent`):**
    *   `Atomic` 原子类：CAS（Compare-And-Swap）无锁编程思想。
    *   `CountDownLatch`, `CyclicBarrier`, `Semaphore`: 信号量机制，用于线程协作。
    *   `AQS (AbstractQueuedSynchronizer)`: JUC 的核心，理解其独占模式和共享模式的设计思想。

#### **JVM (深入理解)**
*   **内存模型 (Runtime Data Areas):**
    *   堆（Heap）、栈（VM Stack）、方法区（Method Area）、程序计数器（PC Register）。
    *   各区域的作用、生命周期、可能出现的 OOM（`OutOfMemoryError`）和 SOF（`StackOverflowError`）。
    *   JDK 1.8 后方法区的变化（元空间 Metaspace）。
*   **垃圾回收机制 (GC):**
    *   **判断对象存活：** 引用计数法、可达性分析算法。
    *   **GC 算法：** 标记-清除、标记-复制、标记-整理。
    *   **分代回收模型：** 新生代（Eden, S0, S1）、老年代。
    *   **GC 收集器 (重点):** Serial, Parallel, CMS, G1，ZGC。理解它们的工作原理、优缺点和适用场景。
    *   **JVM 调优：** 常用 JVM 参数（-Xmx, -Xms, -Xmn等）、如何使用工具（如 VisualVM, JConsole）分析 GC 日志和堆转储文件（Heap Dump）来定位内存泄漏等问题。

#### **I/O (NIO/BIO)**
*   **BIO (Blocking I/O):** 阻塞模型，理解其“一对一”连接的瓶颈。
*   **NIO (Non-blocking I/O):**
    *   **三大核心组件：** `Channel` (通道), `Buffer` (缓冲区), `Selector` (选择器)。
    *   理解其如何通过一个线程管理多个连接，实现 I/O 多路复用。
    *   **零拷贝 (Zero-Copy):** `mmap`, `sendfile` 等技术的原理和优势。
*   **AIO (Asynchronous I/O):** 异步非阻塞，与 NIO 的区别。

---

### 3. 熟练使用 Java 8/11/17/21 的新特性

这部分考察你是否跟进技术发展，能否编写更现代化、更简洁高效的代码。

#### **Java 8 (核心必会)**
*   **Lambda 表达式：**
    *   语法和原理（函数式接口 `@FunctionalInterface`）。
    *   如何在代码中替代匿名内部类。
*   **Stream API (重点):**
    *   中间操作（`filter`, `map`, `sorted`）和终端操作（`collect`, `forEach`, `reduce`）。
    *   `parallelStream()` 的原理（背后是 Fork/Join 框架），以及使用时的注意事项（线程安全问题）。
    *   熟练使用 `Collectors` 工具类进行数据分组、聚合等复杂操作。
*   **Optional:** 优雅地处理 `null` 值，避免 `NullPointerException`。
*   **新的日期时间 API (`java.time`):** `LocalDate`, `LocalTime`, `LocalDateTime` 的使用。

#### **Java 11/17/21 (加分项)**
*   **Java 11:**
    *   `var` 局部变量类型推断：简化代码。
    *   HTTP Client (Standard): 标准化的异步 HTTP 客户端。
*   **Java 17 (LTS 版本):**
    *   `sealed` 类（密封类）：限制类的继承。
    *   `record` 类（记录类）：创建不可变数据对象的简洁语法。
*   **Java 21 (最新 LTS 版本):**
    *   **虚拟线程 (Virtual Threads):** Project Loom 的成果，极大地简化了高并发应用的编写。理解它与平台线程的区别。
    *   **序列化集合 (Sequenced Collections):** 统一集合的遍历顺序。
    *   **Switch 模式匹配** 和 **记录模式 (Record Patterns)**。

### 总结

高级职位不仅仅是你“知道”这些技术，而是：
1.  **深度：** 你能讲清楚底层原理吗？（如 `HashMap` 的实现、`synchronized` 的锁升级）
2.  **广度：** 你能横向对比不同技术的优劣吗？（如 `ArrayList` vs `LinkedList`，`synchronized` vs `ReentrantLock`）
3.  **实践：** 你在项目中是如何应用这些技术的？解决了什么问题？（如 JVM 调优解决 OOM 问题，使用线程池优化性能）

建议你在复习时，每个知识点都按照“是什么 -> 为什么 -> 怎么用 -> 有什么优缺点”的思路去深入学习和总结。祝你一切顺利！


### 目录设计

~~~
java-advanced-mastery/
├── .gitignore               # Git 忽略文件配置
├── pom.xml                  # Maven 项目配置文件
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── zh4ngyj/
    │   │           └── advanced/
    │   │               ├── a_basic/              # 1. Java 基础
    │   │               │   ├── oop/              #    - 面向对象演示 (封装、继承、多态)
    │   │               │   ├── keyword/          #    - 关键字 (static, final, volatile)
    │   │               │   └── exception/        #    - 异常处理
    │   │               │
    │   │               ├── b_design_patterns/    # 2. 设计模式 (23种)
    │   │               │   ├── creational/       #    - 创建型 (单例, 工厂, 建造者)
    │   │               │   │   ├── singleton/
    │   │               │   │   ├── factory/
    │   │               │   │   └── builder/
    │   │               │   ├── structural/       #    - 结构型 (代理, 装饰器, 适配器)
    │   │               │   │   ├── proxy/
    │   │               │   │   ├── decorator/
    │   │               │   │   └── adapter/
    │   │               │   └── behavioral/       #    - 行为型 (策略, 观察者, 模板方法)
    │   │               │       ├── strategy/
    │   │               │       ├── observer/
    │   │               │       └── template/
    │   │               │
    │   │               ├── c_core_technology/    # 3. Java 核心技术
    │   │               │   ├── collections/      #    - 集合框架
    │   │               │   │   ├── list/         #      (ArrayList vs LinkedList)
    │   │               │   │   └── map/          #      (HashMap 源码分析, ConcurrentHashMap)
    │   │               │   ├── concurrency/      #    - 多线程并发
    │   │               │   │   ├── basic/        #      (synchronized, volatile)
    │   │               │   │   ├── threadpool/   #      (ThreadPoolExecutor 详解)
    │   │               │   │   ├── juc/          #      (AQS, Lock, CountDownLatch等)
    │   │               │   │   └── jmm/          #      (JMM 模型演示, happens-before)
    │   │               │   ├── jvm/              #    - JVM
    │   │               │   │   ├── memory/       #      (内存溢出演示 OOM, SOF)
    │   │               │   │   ├── gc/           #      (GC 算法及收集器演示代码)
    │   │               │   │   └── classloader/  #      (类加载机制)
    │   │               │   └── io/               #    - I/O
    │   │               │       ├── bio/          #      (阻塞IO模型)
    │   │               │       ├── nio/          #      (非阻塞IO模型)
    │   │               │       └── zero_copy/    #      (零拷贝)
    │   │               │
    │   │               └── d_new_features/       # 4. Java 新特性
    │   │                   ├── java8/            #    - Java 8 (Lambda, Stream, Optional)
    │   │                   ├── java11/           #    - Java 11 (var, HTTP Client)
    │   │                   ├── java17/           #    - Java 17 (sealed, record)
    │   │                   └── java21/           #    - Java 21 (虚拟线程, 序列化集合)
    │   │
    │   └── resources/
    │       # (配置文件等)
    │
    └── test/
        └── java/
            └── com/
                └── zh4ngyj/
                    └── advanced/
                        # (对应每个模块的单元测试)
~~~