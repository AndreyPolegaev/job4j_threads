package ru.job4j.concurrent.producerconsimer;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest3 {

    @Test
    public void whenSeveralThreads() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread[] threads = new Thread[15];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    queue.offer(10);
                    System.out.println(Thread.currentThread().getName() + " задача добавлена");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread[] threadConsumer = new Thread[5];

        for (int i = 0; i < threadConsumer.length; i++) {
            threadConsumer[i] = new Thread(() -> {
                try {
                    while ((!queue.isEmpty()) || !Thread.currentThread().isInterrupted()) {
                        System.out.println(queue.poll());
                        System.out.println(Thread.currentThread().getName() + " Задача снята");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (var temp : threads) {
            temp.start();
        }
        for (var temp : threadConsumer) {
            temp.start();
            temp.interrupt();
            temp.join();
        }
    }
}