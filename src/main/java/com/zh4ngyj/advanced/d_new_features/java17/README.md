# 模块: Java 17 (LTS) 新特性

Java 17 是继 Java 11 之后的又一个长期支持 (LTS) 版本，它带来了许多重要的语言增强和性能改进。

## 1. `sealed` 类和接口 (密封类)
- **定义**: `sealed` 关键字用于限制一个类或接口可以被哪些其他的类或接口继承或实现。
- **目的**: 提供了对类层次结构的更精细的控制，使得开发者可以明确地定义一个超类的所有可能的子类。这对于领域建模和库的设计非常有用。
- **语法**:
    - 使用 `sealed` 关键字修饰父类/接口。
    - 使用 `permits` 关键字列出所有允许的直接子类/实现。
    - 子类必须是 `final`, `sealed`, 或 `non-sealed` 之一。
        - `final`: 不能再被继承。
        - `sealed`: 继续限制其子类的范围。
        - `non-sealed`: 开放继承，任何类都可以继承它。

```java
public abstract sealed class Shape 
    permits Circle, Rectangle, Square { ... }

public final class Circle extends Shape { ... }
public non-sealed class Rectangle extends Shape { ... }
public final class Square extends Shape { ... }
```

## 2. `record` 类 (记录类)
- **定义**: `record` 是一种特殊的类，用于创建**不可变的**、纯粹的数据载体对象。
- **目的**: 极大地减少了创建 DTOs (Data Transfer Objects) 或 POJOs 时的样板代码。
- **编译器自动生成**:
    - `private final` 的字段。
    - 所有字段的 `getter` 方法（方法名与字段名相同，如 `x()`）。
    - `equals()`, `hashCode()`, `toString()` 的标准实现。
    - 一个接收所有字段的公共构造函数。
- **语法**: `public record Point(int x, int y) {}`
- 这一行代码就等同于传统方式下几十行的 POJO 代码。

## 3. Switch 模式匹配 (Pattern Matching for switch) - 预览特性
- **增强 `switch` 语句**: 允许在 `case` 标签中使用类型模式，而不仅仅是常量。
- **优点**:
    - **代码更简洁**: 无需繁琐的 `if-else if-else` 和 `instanceof` 链。
    - **更安全**: 可以在 `case` 块中直接使用匹配到的类型的变量，无需强制类型转换。
- **示例**:
```java
Object obj = ...;
switch (obj) {
    case Integer i -> System.out.println("It is an integer: " + i);
    case String s -> System.out.println("It is a string: " + s);
    case null -> System.out.println("It is null");
    default -> System.out.println("It is something else");
}
```

## 4. 增强的伪随机数生成器
- 引入了新的接口 `RandomGenerator` 和多个实现，提供了更现代、更灵活的伪随机数生成 API。

Java 17 巩固并扩展了 Java 的现代化特性，使得代码更加简洁、安全和富有表现力。