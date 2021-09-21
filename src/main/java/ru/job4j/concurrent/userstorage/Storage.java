package ru.job4j.concurrent.userstorage;

public interface Storage {

    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    void transfer(int fromId, int toId, int amount);
}
