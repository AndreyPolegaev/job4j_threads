package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает потокобезопасное хранилище.
 * @Author Andrey Polegaev
 * @version 1.0
 */

@ThreadSafe
public class UserStorage implements Storage {

    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();

    @Override
    public synchronized boolean add(User user) {
        if (!users.contains(user)) {
            users.add(user);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean update(User user) {
        User find = findById(user.getId());
        if (find != null) {
            users.set(users.indexOf(user), user);
            return true;

        }
        return false;
    }

    @Override
    public synchronized boolean delete(User user) {
       return users.remove(user);
    }

    public synchronized User findById(int id) {
        for (var temp : users) {
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }

    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = findById(fromId);
        User to = findById(toId);
        if (from != null && to != null) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount((to.getAmount() + amount));
        }
    }
}
