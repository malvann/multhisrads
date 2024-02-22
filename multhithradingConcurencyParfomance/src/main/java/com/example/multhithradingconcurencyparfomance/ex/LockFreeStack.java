package com.example.multhithradingconcurencyparfomance.ex;

import com.example.multhithradingconcurencyparfomance.util.Node;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockFreeStack<T> {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T val) {
        Node<T> newNode = new Node<>(val, null);
        while (true) {
            Node<T> oldHead = head.get();
            newNode.setNext(oldHead);
            if (head.compareAndSet(oldHead, newNode)) break;
            LockSupport.parkNanos(1);
        }
        counter.incrementAndGet();
    }

    public T pop() {
        Node<T> oldHead = head.get();
        Node<T> newHead;
        while (oldHead != null) {
            newHead = oldHead.getNext();
            if (head.compareAndSet(oldHead, newHead)) break;

            LockSupport.parkNanos(1);
            oldHead = head.get();
        }
        counter.incrementAndGet();
        return oldHead == null ? null : oldHead.getVal();
    }

    public int getCounter() {
        return counter.get();
    }
}
