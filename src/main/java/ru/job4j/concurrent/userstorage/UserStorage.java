package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс описывает потокобезопасное хранилище.
 *
 * @version 1.0
 * @Author Andrey Polegaev
 */

@ThreadSafe
public class UserStorage implements Storage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != user;
    }

    @Override
    public synchronized boolean update(User user) {
        User u = users.get(user.getId());
        if (u != null) {
            u.setAmount(user.getAmount());
            users.put(user.getId(), u);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    public synchronized User findById(int id) {
        User u = users.get(id);
       if (u != null) {
           return u;
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
