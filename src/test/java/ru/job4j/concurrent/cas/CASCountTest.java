package ru.job4j.concurrent.cas;

import org.junit.Test;
import java.util.stream.IntStream;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenTwoThreadsIncreaseCount() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(() -> IntStream.range(0, 5).forEach(el -> casCount.increment()));
        Thread second = new Thread(() -> IntStream.range(0, 5).forEach(el -> casCount.increment()));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get(), is(10));
    }
}