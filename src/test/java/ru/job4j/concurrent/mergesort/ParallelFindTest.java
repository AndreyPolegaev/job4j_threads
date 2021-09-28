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
}