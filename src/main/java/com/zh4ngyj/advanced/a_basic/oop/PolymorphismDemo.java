package com.zh4ngyj.advanced.a_basic.oop;

/**
 * 演示多态、封装和继承
 */
public class PolymorphismDemo {

    // 1. 封装 (Encapsulation): Animal 将属性 name 设为 private，通过 public 方法访问
    static abstract class Animal {
        private String name;

        public Animal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        // 抽象方法，由子类实现
        abstract void makeSound();
    }

    // 2. 继承 (Inheritance): Dog 和 Cat 继承自 Animal
    static class Dog extends Animal {
        public Dog(String name) {
            super(name);
        }

        @Override
        void makeSound() {
            System.out.println(getName() + " says: Woof!");
        }

        void fetch() {
            System.out.println(getName() + " is fetching the ball.");
        }
    }

    static class Cat extends Animal {
        public Cat(String name) {
            super(name);
        }

        @Override
        void makeSound() {
            System.out.println(getName() + " says: Meow!");
        }
    }

    public static void main(String[] args) {
        // 3. 多态 (Polymorphism): 父类引用指向子类对象
        Animal myDog = new Dog("Buddy");
        Animal myCat = new Cat("Mitty");

        myDog.makeSound(); // 运行时调用 Dog 的 makeSound
        myCat.makeSound(); // 运行时调用 Cat 的 makeSound

        // instanceof 用于检查对象的真实类型，并可以安全地进行类型转换
        if (myDog instanceof Dog) {
            ((Dog) myDog).fetch();
        }
    }
}