# 设计模式: 装饰器模式 (Decorator)

装饰器模式是一种结构型模式，它允许向一个现有的对象动态地添加新的功能，同时又不改变其结构。这种方式比继承更加灵活。

## 核心思想
装饰器模式就像“包装”，在保持原有对象接口不变的前提下，为其增加一层或多层额外的功能。

## 角色
1.  **组件 (Component)**: 定义一个对象的接口，可以给这些对象动态地添加职责。
2.  **具体组件 (Concrete Component)**: 实现组件接口，是被装饰的原始对象。
3.  **装饰器 (Decorator)**: 继承或实现组件接口，并持有一个组件对象的引用。它定义了与组件一致的接口，并将请求转发给被持有的组件对象。
4.  **具体装饰器 (Concrete Decorator)**: 负责向组件添加新的职责。

## 与继承的区别
- **继承**: 是静态的，在编译时就确定了。子类继承了父类的所有功能，无法在运行时动态增删。
- **装饰器模式**: 是动态的，可以在运行时为一个对象添加或移除功能。它通过组合而非继承来实现，耦合度更低。

## 经典应用
`java.io` 包是装饰器模式的经典应用。
- `InputStream` 是抽象组件 (Component)。
- `FileInputStream` 是具体组件 (Concrete Component)。
- `FilterInputStream` 是抽象装饰器 (Decorator)。
- `BufferedInputStream`, `DataInputStream` 等都是具体装饰器 (Concrete Decorator)。

```java
// 示例：通过层层包装，为 FileInputStream 增加缓冲和数据读取功能
InputStream in = new DataInputStream(
                    new BufferedInputStream(
                        new FileInputStream("file.txt")
                    )
                 );
```
在这个例子中，`FileInputStream` 被 `BufferedInputStream` 装饰，增加了缓冲功能；然后又被 `DataInputStream` 装饰，增加了读取基本数据类型的能力。每一层都只关心自己的职责，并委托下一层完成核心工作。