package ru.job4j.concurrent.userstorage;

import org.junit.Test;

import java.util.HashMap;

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
        UserStorage userStorage = new UserStorage(new HashMap<>());
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
    public void whenAdd() throws InterruptedException {
        UserStorage userStorage = new UserStorage(new HashMap<>());
        assertTrue(userStorage.add(new User(1, 500)));
        assertFalse(userStorage.add(new User(1, 500)));
        assertTrue(userStorage.add(new User(2, 700)));
        assertThat(userStorage.findById(1), is(new User(1, 500)));
        assertThat(userStorage.findById(2), is(new User(2, 700)));
    }

    @Test
    public void whenUpdate() throws InterruptedException {
        UserStorage userStorage = new UserStorage(new HashMap<>());
        userStorage.add(new User(1, 500));
        userStorage.add(new User(2, 700));
        userStorage.update(new User(1, 600));
        assertThat(userStorage.findById(1), is(new User(1, 600)));
        assertThat(userStorage.findById(2), is(new User(2, 700)));
    }

    @Test
    public void whenDelete1() throws InterruptedException {
        UserStorage userStorage = new UserStorage(new HashMap<>());
        userStorage.add(new User(1, 500));
        userStorage.add(new User(2, 700));
        assertTrue(userStorage.delete(new User(2, 500)));
        assertFalse(userStorage.delete(new User(2, 500)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLowAmount() throws InterruptedException {
        UserStorage userStorage = new UserStorage(new HashMap<>());
        userStorage.add(new User(1, 500));
        userStorage.add(new User(2, 700));
        userStorage.transfer(1, 2, 600);
    }

    @Test
    public void whenThreeThreads() throws InterruptedException {
        UserStorage userStorage = new UserStorage(new HashMap<>());

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
        UserStorage userStorage = new UserStorage(new HashMap<>());

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