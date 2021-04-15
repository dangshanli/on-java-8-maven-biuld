package container;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<E> {
    private Deque<E> deque = new ArrayDeque<>();
    //入栈
    public void push(E e) { deque.push(e); }
    //查看
    public E peek() { return deque.peek(); }
    //弹出
    public E pop() { return deque.pop(); }

    public boolean isEmpty() { return deque.isEmpty(); }

    @Override
    public String toString() { return deque.toString(); }
}
