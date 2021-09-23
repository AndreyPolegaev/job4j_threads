package ru.job4j.concurrent.producerconsimer;

import org.junit.Test;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenTwoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> sbq.offer(new Random().nextInt(5)));
        Thread consumer = new Thread(sbq::poll);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(sbq.getSize(), is(0));
    }
}