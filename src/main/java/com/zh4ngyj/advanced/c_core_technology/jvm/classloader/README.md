# 模块: JVM 类加载机制 (Classloader)

JVM 的类加载机制负责将 `.class` 文件中的字节码加载到内存中，并转换为 `java.lang.Class` 对象。

## 1. 类加载的生命周期

一个类的生命周期包括：**加载 (Loading)**、**验证 (Verification)**、**准备 (Preparation)**、**解析 (Resolution)**、**初始化 (Initialization)**、**使用 (Using)** 和 **卸载 (Unloading)**。其中，验证、准备、解析三个部分统称为**连接 (Linking)**。

1.  **加载**: 通过一个类的全限定名来获取定义此类的二进制字节流，并将其转换为方法区中的运行时数据结构，然后在内存中生成一个代表这个类的 `java.lang.Class` 对象。
2.  **验证**: 确保 Class 文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
3.  **准备**: 为类变量（`static` 修饰的变量）分配内存并设置类变量初始值（零值，如 `int` 为 0，`boolean` 为 `false` 等）。
4.  **解析**: 将常量池内的符号引用替换为直接引用的过程。
5.  **初始化**: 执行类构造器 `<clinit>()` 方法的过程。此方法由编译器自动收集所有类变量的赋值动作和静态语句块 (`static{}`) 中的语句合并产生。

## 2. 类加载器 (Class Loader)

JVM 使用**双亲委派模型 (Parents Delegation Model)** 来组织类加载器。

### a. 类加载器种类
1.  **启动类加载器 (Bootstrap ClassLoader)**:
    - C++ 实现，是虚拟机自身的一部分。
    - 负责加载 `<JAVA_HOME>\lib` 目录下的核心类库（如 `rt.jar`）。
2.  **扩展类加载器 (Extension ClassLoader)**:
    - Java 实现，`sun.misc.Launcher$ExtClassLoader`。
    - 负责加载 `<JAVA_HOME>\lib\ext` 目录下的扩展类库。
3.  **应用程序类加载器 (Application ClassLoader)**:
    - Java 实现，`sun.misc.Launcher$AppClassLoader`。也称为系统类加载器。
    - 负责加载用户类路径 (Classpath) 上所指定的类库。是程序中默认的类加载器。
4.  **自定义类加载器 (Custom ClassLoader)**:
    - 用户通过继承 `java.lang.ClassLoader` 实现的自定义加载器，可以实现动态加载、代码加密等功能。

### b. 双亲委派模型
- **工作过程**:
    1. 如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器去完成。
    2. 每一个层次的类加载器都是如此，因此所有的加载请求最终都应该传送到顶层的启动类加载器中。
    3. 只有当父加载器反馈自己无法完成这个加载请求（它的搜索范围中没有找到所需的类）时，子加载器才会尝试自己去加载。
- **优点**:
    - **避免重复加载**: 保证一个类在各种类加载器环境中都是同一个类。
    - **安全性**: 防止核心 API 库被篡改。例如，用户不能通过自定义一个 `java.lang.String` 类来替代系统中的 `String` 类。