package ru.job4j.concurrent.producerconsimer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> extends Thread {

    private final int limit;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == limit) {
                    System.out.println(Thread.currentThread().getName() + " ждет в методе offer()");
                    this.wait();
            }
            queue.offer(value);
            System.out.println(value + " размер очереди = " + queue.size());
            this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " ждет в методе poll()");
                    this.wait();
            }
            System.out.println("элемент снят, размер очереди = " + queue.size());
            this.notifyAll();
            return queue.poll();
        }
    }

    public int getSize() {
        synchronized (this) {
            return queue.size();
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return queue.isEmpty();
        }
    }
}


