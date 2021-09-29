package ru.job4j.concurrent.nosharedresources;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());

    }

    public List<User> findAll() {
        return List.copyOf(users.values());
    }
}
