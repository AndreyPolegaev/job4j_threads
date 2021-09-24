package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.HashSet;
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
    private final Map<Integer, User> users;

    public UserStorage(Map<Integer, User> users) {
        this.users = copy(users);
    }

    private synchronized Map<Integer, User> copy(Map<Integer, User> users) {
        return new HashMap<>(users);
    }

    @Override
    public synchronized boolean add(User user) {
       return users.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public synchronized boolean update(User user) {
      return users.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
       return users.remove(user.getId(), user);
    }

    public synchronized User findById(int id) {
        return users.get(id);
    }

    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = users.get(fromId);
        User to = users.get(toId);
        if (from != null && to != null) {
            if (from.getAmount() < amount) {
                throw new IllegalArgumentException("недостаточно средств");
            }
            from.setAmount(from.getAmount() - amount);
            to.setAmount((to.getAmount() + amount));
        }
    }
}
