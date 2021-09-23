package ru.job4j.concurrent.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * механизм оптимистичной блокировки и позволяющий изменить значение value только в том случае,
 * если оно равно ожидаемому значению
 */

public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int number;
        int next;
        do {
            number = count.get();
            next = number++;
        } while (!count.compareAndSet(next, number));
        count.set(number);
    }

    public int get() {
        return count.get();
    }
}
