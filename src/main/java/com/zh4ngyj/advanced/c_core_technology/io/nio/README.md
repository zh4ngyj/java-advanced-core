# I/O 模型: NIO (Non-blocking I/O)

NIO (Non-blocking I/O 或 New I/O)，即同步非阻塞 I/O，是 Java 1.4 中引入的一套新的 I/O API，旨在解决 BIO 模型的性能瓶颈。

## 核心思想
NIO 的核心是**I/O 多路复用 (I/O Multiplexing)**，它允许一个单独的线程来监视和处理多个 Channel（连接）上的 I/O 事件。

## 三大核心组件

### 1. `Channel` (通道)
- **定义**: `Channel` 类似于传统的 `Stream`，是数据源和目标之间的连接。但 `Channel` 是双向的，既可以读也可以写。
- **与 Stream 的区别**: `Stream` 是单向的，且是阻塞的；`Channel` 是双向的，可以配置为非阻塞模式。
- **常见类型**: `SocketChannel`, `ServerSocketChannel`, `FileChannel`。

### 2. `Buffer` (缓冲区)
- **定义**: `Buffer` 是一块内存区域，NIO 中所有的数据读写都通过 `Buffer` 进行。
- **核心属性**:
    - `capacity`: 缓冲区总容量，一旦设定不可改变。
    - `position`: 当前读/写的位置。
    - `limit`: 可读/写的界限。
    - `mark`: 一个备忘位置。
- **工作流程**:
    1.  向 `Buffer` 写数据时，`position` 后移。
    2.  写完后，调用 `flip()` 方法，将 `limit` 设为当前 `position`，`position` 归 0。这标志着 `Buffer` 从写模式切换到读模式。
    3.  从 `Buffer` 读数据时，`position` 后移。
    4.  读完后，调用 `clear()` 或 `compact()` 方法，重置 `Buffer` 以便再次写入。

### 3. `Selector` (选择器)
- **定义**: `Selector` 是 NIO 实现 I/O 多路复用的核心。它允许一个单线程管理多个 `Channel`。
- **工作流程**:
    1.  将多个 `Channel` 注册到一个 `Selector` 上，并指定关心该 `Channel` 上的哪些事件（如 `OP_READ`, `OP_WRITE`, `OP_CONNECT`, `OP_ACCEPT`）。
    2.  调用 `selector.select()` 方法，该方法会阻塞，直到至少有一个已注册的 `Channel` 发生了你所关心的事件。
    3.  `select()` 方法返回后，可以通过 `selector.selectedKeys()` 获取所有已就绪事件的集合 (`SelectionKey`)。
    4.  遍历 `SelectionKey` 集合，根据事件类型进行相应的 I/O 操作。

## 优缺点

### 优点
- **高并发支持**: 单个线程可以处理成百上千个连接，极大地提高了服务器的并发能力。
- **资源高效**: 只有在连接真正有 I/O 事件时才会进行处理，避免了 BIO 中大量线程因等待而阻塞造成的资源浪费。

### 缺点
- **编程复杂**: 相比 BIO，NIO 的编程模型更复杂，需要理解 `Channel`, `Buffer`, `Selector` 之间的协作关系。

NIO 是构建高性能网络服务（如 Netty, Mina 等框架）的基础。