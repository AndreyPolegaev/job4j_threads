package ru.job4j.concurrent.waitnotify;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        CountBarrier cb = new CountBarrier(3);
        Thread first = new Thread(cb::await);
        Thread second = new Thread(cb::await);
        Thread third = new Thread(cb::await);
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
    }
}
