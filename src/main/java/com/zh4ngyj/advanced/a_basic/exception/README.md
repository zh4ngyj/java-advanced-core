## 模块: Java 异常处理机制

Java 的异常处理机制是一种强大的错误处理方式，它使得程序的主要逻辑和错误处理逻辑可以分离开来。

### `Throwable` 类层次结构

所有异常和错误的根类是 `java.lang.Throwable`。

```
      java.lang.Throwable
        /             \
java.lang.Error   java.lang.Exception
                       /           \
      Checked Exception     Unchecked Exception (RuntimeException)
```

#### 1. `Error`
- **定义**: 表示严重的、程序无法处理的系统级错误，例如 `OutOfMemoryError`、`StackOverflowError`。
- **特点**: `Error` 通常是不可恢复的，应用程序不应该（也无法）捕获和处理它们。

#### 2. `Exception`
- **定义**: 表示程序本身可以处理的异常情况。它分为两类：Checked Exception 和 Unchecked Exception。

##### a. `Checked Exception` (受检异常)
- **定义**: 除了 `RuntimeException` 及其子类之外的所有 `Exception`。
- **特点**:
    - **强制处理**: Java 编译器会强制要求你必须处理（通过 `try-catch` 捕获）或声明抛出（通过 `throws` 关键字）这类异常。
    - **场景**: 通常表示可恢复的、由外部因素引起的异常，如 `IOException`、`SQLException`。

##### b. `Unchecked Exception` (非受检异常)
- **定义**: `RuntimeException` 及其所有子类。
- **特点**:
    - **非强制处理**: 编译器不强制要求处理。
    - **场景**: 通常表示程序中的逻辑错误，如 `NullPointerException`、`IllegalArgumentException`、`IndexOutOfBoundsException`。这些异常本应在编码阶段通过逻辑判断来避免。

---

### 核心关键字

- **`try`**: 包含可能抛出异常的代码块。
- **`catch`**: 捕获并处理 `try` 块中抛出的特定类型的异常。可以有多个 `catch` 块。
- **`finally`**: 无论是否发生异常，`finally` 块中的代码总会被执行（除了JVM退出的情况）。主要用于资源释放。
- **`throw`**: 手动抛出一个异常对象。
- **`throws`**: 在方法签名中声明该方法可能抛出的异常类型，将处理责任交给调用者。

---

### `try-with-resources` (Java 7+)

- **定义**: 一种语法糖，用于自动关闭实现了 `java.lang.AutoCloseable` 或 `java.io.Closeable` 接口的资源。
- **优点**:
    - **代码简洁**: 无需手动编写 `finally` 块来关闭资源。
    - **避免资源泄漏**: 编译器会自动生成关闭资源的代码，比手动管理更安全。
- **示例**:
  ```java
  try (FileInputStream fis = new FileInputStream("file.txt")) {
      // ... use the resource ...
  } catch (IOException e) {
      // ... handle exception ...
  }
  // fis is automatically closed here
  ```

### 释放资源
“报错了”并不会自动帮你把所有资源都释放掉，只会做一件事：回收 Java 堆里的对象内存。而很多我们说的“资源”根本不在堆里，所以需要自己关。

在 Java 里常说要“释放资源”，通常指：
- 文件句柄（FileInputStream, RandomAccessFile…）
- 网络连接（Socket, HTTP 连接）
- 数据库连接、Statement、ResultSet
- 线程池、定时任务
- 锁、信号量等并发资源
这些资源通常对应的是 操作系统资源 / 外部系统资源，不只是内存。

GC 只管 Java 堆内存：
- 某个对象变成不可达时，GC 只会把它占用的堆内存回收
- 它持有的操作系统资源（文件句柄、socket 等），GC 不负责帮你关闭

很多 I/O 类内部确实会在 finalize() 或其它机制里尝试关闭，但：
- 不可靠：什么时候 GC、什么时候触发，完全不确定
- 可能太晚：文件句柄/连接数耗尽前 GC 没发生，程序直接出错

**资源不释放会存在上面问题？**
1.文件流不关：
- 打开太多文件：抛 Too many open files
- 文件一直被占用：别的程序/线程删不了、改不了

2.数据库连接不关:
- 连接池被用光：后续每个请求都卡住或报“无法获取连接”
- 数据库端资源泄漏

3.Socket 不关:
- 文件描述符泄漏
- 客户端/服务器都认为连接还在，长期占用资源

4.线程池不关：
- 程序想退出时退出不了，因为后台线程还在运行
- 持续占用 CPU / 内存

这些问题跟有没有异常无关：不管正常结束还是报错，只要你不关，就会泄漏。