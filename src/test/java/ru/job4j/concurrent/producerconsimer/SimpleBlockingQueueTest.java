package ru.job4j.concurrent.producerconsimer;

import org.junit.Test;
import java.util.Random;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenTwoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            try {
                sbq.offer(new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                sbq.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(sbq.getSize(), is(0));
    }
}