package ru.job4j.concurrent.userstorage;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    private class ThreadStorage extends Thread {

        private final Storage userStorage;

        private ThreadStorage(final Storage userStorage) {
            this.userStorage = userStorage;
        }

        @Override
        public void run() {
            userStorage.add(new User(1, 100));
            userStorage.add(new User(2, 200));
            userStorage.transfer(1, 2, 50);
        }
    }

    @Test
    public void whenTwoThreads() throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        ThreadStorage first = new ThreadStorage(userStorage);
        ThreadStorage second = new ThreadStorage(userStorage);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(userStorage.findById(1).getAmount(), is(0));
        assertThat(userStorage.findById(2).getAmount(), is(300));
    }

    @Test
    public void whenThreeThreads() throws InterruptedException {
        UserStorage userStorage = new UserStorage();

        Thread first = new Thread(
                () -> userStorage.add(new User(1, 500)));

        Thread second = new Thread(
                () -> userStorage.add(new User(2, 300)));

        Thread third = new Thread(
                () -> {
                    userStorage.update(new User(1, 1000));
                    userStorage.transfer(2, 1, 300);
                });

        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(userStorage.findById(1).getAmount(), is(1300));
        assertThat(userStorage.findById(2).getAmount(), is(0));
    }

    @Test
    public void whenDelete() throws InterruptedException {
        UserStorage userStorage = new UserStorage();

        Thread first = new Thread(
                () -> userStorage.add(new User(1, 500)));

        Thread second = new Thread(
                () -> userStorage.add(new User(2, 300)));

        Thread third = new Thread(
                () -> userStorage.delete(new User(2, 300)));

        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertNull(userStorage.findById(2));
    }
}