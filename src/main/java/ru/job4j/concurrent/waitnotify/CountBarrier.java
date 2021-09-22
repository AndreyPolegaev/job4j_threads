package ru.job4j.concurrent.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * класс блокирует выполнение по условию счетчика.
 */

@ThreadSafe
public class CountBarrier {

    private final Object monitor = this;

    /**
     * total содержит количество вызовов метода count().
     */
    private final int total;

    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод count изменяет состояние программы. нужно вызывать метод notifyAll.
     */
    public void count() {
        synchronized (monitor) {
            if (count < total) {
                count++;
            }
            monitor.notifyAll();
        }
    }

    /**
     * Нити, которые выполняют метод await, могут начать работу если поле count >= total.
     * Если оно не равно, то нужно перевести нить в состояние wait.
     */
    public void await() {
        synchronized (monitor) {
            count();
            while (count < total) {
                try {
                    System.out.println(Thread.currentThread().getName() + " started");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
