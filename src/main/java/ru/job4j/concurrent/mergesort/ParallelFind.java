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
    private final int el;

    public ParallelFind(int[] array, int el) {
        this.array = array;
        this.el = el;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == el) {
                    return i;
                }
            }
        }

        // создаем задачи для поиска
        ParallelFind leftFind = new ParallelFind(Arrays.copyOfRange(array, 0, array.length / 2), el);
        ParallelFind rightFind = new ParallelFind(Arrays.copyOfRange(array, array.length + 1, array.length), el);

        leftFind.fork();
        rightFind.fork();
        // объединяем полученные результаты
        leftFind.join();
        rightFind.join();
        return el;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(new ParallelFind(new int[]{1, 2, 3, -1, 0}, -1)));
    }
}
