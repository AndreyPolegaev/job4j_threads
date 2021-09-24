package ru.job4j.concurrent.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * механизм оптимистичной блокировки и позволяющий изменить значение number только в том случае,
 * если оно равно ожидаемому значению (next)
 */

public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int number;
        do {
            number = count.get();
        } while (!count.compareAndSet(number++, number));

    }

    public int get() {
        return count.get();
    }
}
