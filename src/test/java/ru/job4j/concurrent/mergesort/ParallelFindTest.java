package ru.job4j.concurrent.mergesort;

import org.junit.Test;
import java.util.concurrent.ForkJoinPool;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
public class ParallelFindTest {

    @Test
    public void whenFindIndex() {
        int[] data = new int[]{1, 2, 3, -1, 0};
        ParallelFind parallelFind = new ParallelFind(data, 0, data.length, 3);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int expectedIndex = forkJoinPool.invoke(parallelFind);
        assertThat(expectedIndex, is(2));
    }

    @Test
    public void whenFindIndex2() {
        int[] data = new int[]{1, 2, 3, -1, 0};
        ParallelFind parallelFind = new ParallelFind(data, 0, data.length, 0);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int expectedIndex = forkJoinPool.invoke(parallelFind);
        assertThat(expectedIndex, is(4));
    }

    @Test
    public void whenIndexNotFound() {
        int[] data = new int[]{1, 2, 3, -1, 0};
        ParallelFind parallelFind = new ParallelFind(data, 0, data.length, 10);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int expectedIndex = forkJoinPool.invoke(parallelFind);
        assertThat(expectedIndex, is(-1));
    }

    @Test
    public void whenIndexFound() {
        int[] data = new int[]{
                1, 2, 3, -1, 0, 7, 4, 6, 1, -3, -5, -1, 0, 12, 10, 54, 234, -123, 54, -654,
                0, 17, 14, 16, 1, -32, -54, -134, 0, 124, 105, 546, 2347, -1233, 54, 333
        };
        ParallelFind parallelFind = new ParallelFind(data, 0, data.length, -654);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int expectedIndex = forkJoinPool.invoke(parallelFind);
        assertThat(expectedIndex, is(19));
    }
}