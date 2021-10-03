package ru.job4j.concurrent.mergesort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *  Реализовать параллельный поиск индекса в массиве объектов.
 *  В целях оптимизации, если размер массива не больше 10, использовать обычный линейный поиск.
 */

public class ParallelFind extends RecursiveTask<Integer> {

    private final int[] array;
    private final int from;
    private final int to;
    private final int el;

    public ParallelFind(int[] array, int from, int to, int el) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.el = el;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int i = from; i <= array.length - 1; i++) {
                if (array[i] == el) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        // создаем задачи для поиска
        ParallelFind leftFind = new ParallelFind(array, from, mid, el);
        ParallelFind rightFind = new ParallelFind(array, mid + 1, to, el);
        leftFind.fork();
        rightFind.fork();
        // объединяем полученные результаты
        int e1 = leftFind.join();
        int e2 = rightFind.join();
        return Math.max(e1, e2);
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int[] data = new int[]{1, 2, 3, -1, 0};
        System.out.println(forkJoinPool.invoke(new ParallelFind(data, 0, data.length, 3)));
    }
}
