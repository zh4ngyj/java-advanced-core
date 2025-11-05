# 模块: Java 21 (LTS) 新特性

Java 21 是当前的长期支持 (LTS) 版本，它带来了革命性的并发模型和一系列强大的语言特性，是 Java 发展的一个重要里程碑。

## 1. 虚拟线程 (Virtual Threads) - JEP 444
- **背景**: 这是 Project Loom 的核心成果，旨在极大地简化高吞吐量并发应用的编写、维护和观察。
- **定义**: 虚拟线程是由 JVM 管理的、极其轻量的线程。它们运行在传统的平台线程 (Platform Threads) 之上，但一个平台线程可以分时运行成千上万个虚拟线程。
- **核心优势**:
    - **数量庞大**: 可以轻松创建数百万个虚拟线程。
    - **阻塞不昂贵**: 当虚拟线程执行 I/O 等阻塞操作时，它不会占用平台线程，而是被 JVM 挂起，平台线程可以去执行其他虚拟线程。这使得“每个请求一个线程”的传统编程模型在高并发场景下重新变得可行。
- **创建方式**:
  ```java
  // 直接启动
  Thread.startVirtualThread(() -> { ... });

  // 使用 ExecutorService
  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.submit(() -> { ... });
  }
  ```
- **影响**: 彻底改变了 Java 高并发编程的范式，使得开发者可以用简单的同步阻塞代码写出高性能的异步程序。

## 2. 序列化集合 (Sequenced Collections) - JEP 431
- **背景**: Java 集合框架长期以来缺乏一个统一的、定义了明确遍历顺序的集合类型。
- **新接口**:
    - `SequencedCollection<E>`: 定义了 `addFirst()`, `addLast()`, `getFirst()`, `getLast()` 等方法。`List` 和 `Deque` 现在都继承了它。
    - `SequencedSet<E>`: 一个有序且元素唯一的 `Set`，`LinkedHashSet` 和 `SortedSet` 继承了它。
    - `SequencedMap<K, V>`: 一个有序的 `Map`，`LinkedHashMap` 和 `SortedMap` 继承了它。
- **目的**: 提供了统一、易用的 API 来访问集合的首尾元素，并支持逆序遍历 (`reversed()`)。

## 3. 记录模式 (Record Patterns) - JEP 440
- **定义**: 这是模式匹配的进一步增强，允许解构 `record` 对象。
- **应用**:
    - `instanceof` 检查:
      ```java
      // if (obj instanceof Point(int x, int y)) { ... }
      // 可以直接在 if 块中使用 x 和 y
      ```
    - `switch` 语句:
      ```java
      // case Point(int x, int y) -> ...
      ```
- **优点**: 使得从数据对象中提取组件变得极其简洁和安全。

## 4. Switch 模式匹配 (Pattern Matching for switch) - JEP 441
- **正式 finalized**: 在 Java 17 作为预览特性引入，Java 21 中正式定稿。
- **增强**:
    - 支持对 `null` 的 `case` 处理。
    - 支持 `when` 子句，为 `case` 标签添加更复杂的判断条件。
      ```java
      // case String s when s.length() > 5 -> ...
      ```

Java 21 的这些特性，特别是虚拟线程，标志着 Java 在云原生和高并发时代保持强大竞争力的决心。