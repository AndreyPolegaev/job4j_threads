package ru.job4j.concurrent.conchashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Разные версии");
            }
            v.setVersion();
            v.setName(model.getName());
            return v;
        });
        return memory.containsKey(model.getId());
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base getBase(int index) {
        return memory.get(index);
    }
}
