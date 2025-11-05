# 模块: Java 异常处理机制

Java 的异常处理机制是一种强大的错误处理方式，它使得程序的主要逻辑和错误处理逻辑可以分离开来。

## `Throwable` 类层次结构

所有异常和错误的根类是 `java.lang.Throwable`。

```
      java.lang.Throwable
        /             \
java.lang.Error   java.lang.Exception
                       /           \
      Checked Exception     Unchecked Exception (RuntimeException)
```

### 1. `Error`
- **定义**: 表示严重的、程序无法处理的系统级错误，例如 `OutOfMemoryError`、`StackOverflowError`。
- **特点**: `Error` 通常是不可恢复的，应用程序不应该（也无法）捕获和处理它们。

### 2. `Exception`
- **定义**: 表示程序本身可以处理的异常情况。它分为两类：Checked Exception 和 Unchecked Exception。

#### a. `Checked Exception` (受检异常)
- **定义**: 除了 `RuntimeException` 及其子类之外的所有 `Exception`。
- **特点**:
    - **强制处理**: Java 编译器会强制要求你必须处理（通过 `try-catch` 捕获）或声明抛出（通过 `throws` 关键字）这类异常。
    - **场景**: 通常表示可恢复的、由外部因素引起的异常，如 `IOException`、`SQLException`。

#### b. `Unchecked Exception` (非受检异常)
- **定义**: `RuntimeException` 及其所有子类。
- **特点**:
    - **非强制处理**: 编译器不强制要求处理。
    - **场景**: 通常表示程序中的逻辑错误，如 `NullPointerException`、`IllegalArgumentException`、`IndexOutOfBoundsException`。这些异常本应在编码阶段通过逻辑判断来避免。

---

## 核心关键字

- **`try`**: 包含可能抛出异常的代码块。
- **`catch`**: 捕获并处理 `try` 块中抛出的特定类型的异常。可以有多个 `catch` 块。
- **`finally`**: 无论是否发生异常，`finally` 块中的代码总会被执行（除了JVM退出的情况）。主要用于资源释放。
- **`throw`**: 手动抛出一个异常对象。
- **`throws`**: 在方法签名中声明该方法可能抛出的异常类型，将处理责任交给调用者。

---

## `try-with-resources` (Java 7+)

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