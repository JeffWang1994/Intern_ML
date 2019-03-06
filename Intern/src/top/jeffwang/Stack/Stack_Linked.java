package top.jeffwang.Stack;

class Node2<E>{
    Node2<E> next = null;
    E data;
    public Node2(E data){
        this.data = data;
    }
}

public class Stack_Linked<E> {
    Node2<E> top = null;
    public boolean isEmpty(){
        return top == null;
    }

    public void push(E data){
        Node2<E> newNode = new Node2<E>(data);
        newNode.next = top;
        top = newNode;
    }

    public E pop(){
        if(this.isEmpty())
            return null;
        E data = top.data;
        top = top.next;
        return data;
    }

    public E peek(){
        if(isEmpty()){
            return null;
        }
        return top.data;
    }
}
