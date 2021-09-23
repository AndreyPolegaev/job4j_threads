package ru.job4j.concurrent.conchashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        memory.computeIfPresent(model.getId(), (K, V) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Разные версии");
            }

            V.setVersion();
            V.setName(model.getName());
            return V;
        });
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
