# 模块: Map 集合框架

本模块深入探讨 `java.util.Map` 接口的几个关键实现，特别是 `HashMap` 和 `ConcurrentHashMap` 的底层原理，这是 Java 面试中的重中之重。

## `HashMap` (重点)

`HashMap` 是基于哈希表的 `Map` 接口实现，它允许 `null` 键和 `null` 值，并且不保证映射的顺序。

### JDK 1.7
- **底层数据结构**: 数组 + 链表。
- **`put` 过程**:
    1. 计算 key 的 `hashCode()`。
    2. 通过哈希扰动函数和位运算 (`& (length-1)`) 确定在数组中的索引。
    3. 如果该位置为 `null`，直接插入。
    4. 如果该位置不为 `null`（发生哈希冲突），则遍历链表。
    5. 如果链表中已有相同的 key，则替换 value。
    6. 如果没有相同的 key，则使用**头插法**将新节点插入链表头部。
- **扩容问题**: 在多线程环境下，头插法可能导致链表形成环形结构，在 `get` 时造成死循环。

### JDK 1.8
- **底层数据结构**: 数组 + 链表 / 红黑树。
- **改进**:
    1. **引入红黑树**: 当链表长度超过阈值 `TREEIFY_THRESHOLD` (默认为 8) 且数组长度大于 `MIN_TREEIFY_CAPACITY` (默认为 64) 时，链表会转换为红黑树，以提高查询效率 (O(log n))。
    2. **尾插法**: 扩容时使用尾插法，解决了 JDK 1.7 中头插法导致的死循环问题。
    3. **哈希扰动简化**: 哈希计算方式有所简化，但仍然保留了高位参与运算的思想，以减少哈希冲突。
- **`resize()` (扩容)**:
    - 创建一个容量为原先两倍的新数组。
    - 遍历旧数组，将每个桶（bucket）中的元素重新计算索引并放入新数组。
    - 对于链表上的节点，它们的索引在新数组中要么在原位置，要么在 `原位置 + oldCapacity` 的位置，这是一个巧妙的优化。

## `ConcurrentHashMap` (重点)

`ConcurrentHashMap` 是 `HashMap` 的线程安全版本，提供了比 `Hashtable` 和 `Collections.synchronizedMap` 好得多的并发性能。

### JDK 1.7
- **底层数据结构**: `Segment` 数组 + `HashEntry` 数组 + 链表。
- **锁机制**: **分段锁 (Segment Lock)**。
    - `ConcurrentHashMap` 由多个 `Segment` 组成，每个 `Segment` 都是一个小的、独立的 `HashMap`。
    - 每个 `Segment` 内部持有一把锁 (`ReentrantLock`)。
    - 当对数据进行写操作时，只需要锁定数据所在的 `Segment`，其他 `Segment` 不受影响，从而允许多个线程并行地访问不同的 `Segment`。
- **并发度 (concurrencyLevel)**: 默认是 16，即有 16 个 `Segment`。

### JDK 1.8
- **底层数据结构**: 与 `HashMap` 1.8 类似，数组 + 链表 / 红黑树。
- **锁机制**: 放弃了分段锁，改用 `CAS` + `synchronized`。
    - **`put` 过程**:
        1. 如果数组的某个位置是 `null`，则使用 `CAS` 操作尝试插入新节点。
        2. 如果 `CAS` 失败（说明有其他线程正在操作），则进入下一轮循环。
        3. 如果数组位置不为 `null`，则使用 `synchronized` 锁定该位置的**头节点** (链表头或红黑树根)。
- **优点**:
    - **锁粒度更细**: 从锁住一个 `Segment` 变为锁住一个桶的头节点，并发性能更高。
    - **内存结构更简单**: 放弃了 `Segment` 结构，更接近 `HashMap`。