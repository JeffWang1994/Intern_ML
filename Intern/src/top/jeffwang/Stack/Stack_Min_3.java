package top.jeffwang.Stack;

/**
 * 3. 如何用O(1)的时间复杂度来求栈中最小元素
 * 在实现时，使用两个栈结构，一个栈用来存储数据，另一个栈用来存最小元素。
 * 如果当前入栈的元素比原来栈中的最小值还小，则把这个值压入保存最小元素的栈中。
 * 在出栈时，如果当前出栈的元素恰好为当前栈中的最小值。保存最小值的栈顶元素也出栈。使得最小值变成其入栈之前的那个最小值。
 */
public class Stack_Min_3 {
    Stack<Integer> elem;
    Stack<Integer> min;

    public Stack_Min_3(){
        elem = new Stack<>();
        min = new Stack<>();
    }

    public void push(int data){
        elem.push(data);
        if(min.isEmpty()){
            min.push(data);
        }else{
            if(data<min.peek()){
                min.push(data);
            }
        }
    }

    public int pop(){
        int topData = elem.peek();
        elem.pop();
        if(topData==this.min()){
            min.pop();
        }
        return topData;
    }

    public int min(){
        if(min.isEmpty()){
            return Integer.MAX_VALUE;
        }
        return min.peek();
    }
}
