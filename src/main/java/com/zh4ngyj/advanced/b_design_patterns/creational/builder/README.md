# 设计模式: 建造者模式 (Builder)

建造者模式是一种创建型模式，旨在将一个复杂对象的构建过程与其表示分离，使得同样的构建过程可以创建不同的表示。

## 核心思想
当一个对象的构造函数参数过多，或者对象的创建过程比较复杂时，使用建造者模式可以使代码更具可读性和可维护性。

## 为什么使用建造者模式？
- **解决构造函数参数过多的问题**: 避免了重叠构造函数（Telescoping Constructor）的混乱。
- **提高代码可读性**: 通过链式调用 (`.setter().setter()`)，可以清晰地看出设置了哪些属性。
- **支持创建不可变对象**: 可以在 `build()` 方法中完成所有属性的设置，然后返回一个不可变的对象。
- **灵活性**: 构建过程是分步的，可以灵活控制对象的创建细节。

## 角色
1.  **产品 (Product)**: 要创建的复杂对象。
2.  **抽象建造者 (Abstract Builder)**: 定义创建产品各个部分的抽象接口。
3.  **具体建造者 (Concrete Builder)**: 实现抽象建造者接口，负责构建和组装产品的各个部分。
4.  **指挥者 (Director)** (可选): 负责按一定顺序调用建造者的方法来构建产品。在现代流式API的实现中，这个角色通常被省略，由客户端代码直接调用。

## 链式调用 (Fluent Interface) 实现
现代最常用的方式是使用静态内部类 `Builder` 来实现链式调用。

```java
// 示例
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    // ...

    public static class Builder {
        // ...
        public Builder servingSize(int val) {
            servingSize = val;
            return this; // 返回 this 实现链式调用
        }
        // ...
        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        // ...
    }
}

// 使用
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
    .calories(100)
    .sodium(35)
    .build();
```
这种方式简洁、易读，并且是线程安全的（如果产品对象是不可变的）。