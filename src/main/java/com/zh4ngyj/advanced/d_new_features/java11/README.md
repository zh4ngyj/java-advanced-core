# 模块: Java 11 (LTS) 新特性

Java 11 是继 Java 8 之后的第一个长期支持 (LTS) 版本，它包含了从 Java 9, 10 引入的诸多特性，并进行了一些重要更新。

## 1. 局部变量类型推断 (`var`)
- **定义**: `var` 关键字允许在声明局部变量时，让编译器根据右侧的表达式自动推断变量的类型。
- **目的**: 减少样板代码，使代码更简洁、易读。
- **使用范围**:
    - 只能用于局部变量（方法内、循环中）。
    - 不能用于方法参数、构造函数参数、方法返回类型或字段。
    - 声明时必须立即初始化。
- **示例**:
  ```java
  // 传统方式
  Map<String, List<Integer>> userScores = new HashMap<>();

  // 使用 var
  var userScores = new HashMap<String, List<Integer>>(); 
  ```
- **Lambda中的应用**: Java 11 允许在 Lambda 表达式的参数上使用 `var`，从而可以对参数添加注解。
  ```java
  // (@NonNull var x, @Nullable var y) -> x.process(y)
  ```

## 2. 标准化的 HTTP Client
- **背景**: Java 9 引入了一个新的孵化阶段的 HTTP Client API，在 Java 11 中，该 API 被正式纳入标准库 `java.net.http`。它用于取代老旧、设计笨重的 `HttpURLConnection`。
- **核心类**:
    - `HttpClient`: 用于发送请求和接收响应的客户端。
    - `HttpRequest`: 代表一个 HTTP 请求。
    - `HttpResponse`: 代表一个 HTTP 响应。
- **特性**:
    - **支持 HTTP/1.1 和 HTTP/2**。
    - **支持同步和异步编程模型**:
        - 同步: `client.send(...)`
        - 异步: `client.sendAsync(...)`，返回一个 `CompletableFuture`。
    - **流式 API**: 使用建造者模式创建请求，易于使用。
    - **支持 WebSocket**。
- **示例**:
  ```java
  HttpClient client = HttpClient.newHttpClient();
  HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://example.com"))
        .build();
  
  // 异步请求
  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println)
        .join();
  ```

## 3. 字符串 API 增强
`String` 类增加了一些实用的新方法：
- `isBlank()`: 判断字符串是否为空白（空字符串或只包含空白字符）。
- `lines()`: 将字符串按行分隔符分割成一个 Stream。
- `strip()`, `stripLeading()`, `stripTrailing()`: 去除首尾空白字符（与 `trim()` 不同，它能识别所有 Unicode 空白字符）。
- `repeat(int)`: 将字符串重复指定次数。

## 4. `Files` API 增强
- `Files.writeString(Path, CharSequence, ...)`: 直接将字符串写入文件。
- `Files.readString(Path, ...)`: 直接从文件读取内容到字符串。

Java 11 作为一个 LTS 版本，其稳定性和实用性得到了广泛认可，是企业从 Java 8 升级的重要选择。