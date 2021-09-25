package ru.job4j.concurrent.threadpool;

import ru.job4j.concurrent.producerconsimer.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * Инициализация пула должна быть по количеству ядер в системе.
 * int size = Runtime.getRuntime().availableProcessors()
 * Количество нитей всегда одинаковое и равно size.
 * <p>
 * В каждую нить передается блокирующая очередь tasks. В методе run мы должны получить задачу из очереди tasks.
 * tasks - это блокирующая очередь.
 * work(Runnable job) - этот метод должен добавлять задачи в блокирующую очередь tasks.
 * shutdown() - этот метод завершит все запущенные задачи.
 *
 * @Author Andrey Polegaev
 * v. 1
 */

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    private int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {

                while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }


    /**
     * добавляет задачи в блокирующую очередь tasks.
     */
    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * В каждую нить передается блокирующая очередь tasks.
     * В методе run мы должны получить задачу из очереди tasks.
     */
    public void shutdown() {
        threads.forEach(el -> {
            el.interrupt();
            try {
                el.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.work(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " задача выполнена");
                }
            });
        }
        threadPool.shutdown();
    }
}
