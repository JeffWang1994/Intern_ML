package top.jeffwang.Stack;

import java.util.Arrays;

/**
 * 用单链表实现栈
 * @param <E>
 */
public class Stack<E> {
    private Object[] stack;
    private int size;

    public Stack(){
        stack = new Object[10];
    }

    //判断堆栈是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    public E peek(){
        if(isEmpty()){
            return null;
        }
        return (E) stack[size-1];
    }

    public E pop(){
        E e = peek();
        stack[size-1] = null;
        size--;
        return e;
    }

    public E push(E item){
        ensureCapacity(size+1);
        stack[size++] = item;
        return item;
    }

    public void ensureCapacity(int size){
        int len = stack.length;
        if (size>len){
            int newLen = 10;
            stack = Arrays.copyOf(stack, newLen);
        }
    }

    public static void main(String[] args){
        Stack<Integer> s = new Stack<Integer>();
        s.push(1);
        s.push(2);
        System.out.println("栈中元素个数: "+s.size);
        System.out.println("栈顶元素为: "+s.pop());
        System.out.println("栈顶元素为: "+s.pop());
    }
}
