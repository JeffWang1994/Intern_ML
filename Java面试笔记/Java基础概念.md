# Java面试笔记——基础概念篇
## 1. Java集合框架是什么？说出一些集合框架的优点？
Vector，Stack，HashTable和Array

## 2. 集合框架中的泛型有什么优点？
所有集合接口和实现都大量地使用泛型。<br>
泛型允许我们为集合提供一个可以容纳的对象类型。<br>
但是如果你提供了其他对象类型，会报错ClassCastException。

## 3. Java集合框架的基础接口有哪些？
1. Collection为集合层级的根接口。一个集合代表一组对象，这些对象即为它的元素。Java平台不提供这个接口的任何直接实现。
2. Set是一个不能包含重复元素的集合。这个接口对数学集合抽象进行建模，被用来代表集合。
3. List是一个有序集合，可以包含重复元素。通过它的索引可以访问任何元素。List更像长度动态变化的数组。
4. Map是一个key映射到value的对象。每个key最多只能映射一个value。

## 4. Iterator是什么？
Iterator为迭代器，Iterator接口提供了遍历任何Collection的接口。

## 5. 在Java

## 1. Hibernate中SessionFactory和Session是线程安全的吗？这两个线程是否能够共享同一个Session？
## 2. 请介绍一下bean的生命周期