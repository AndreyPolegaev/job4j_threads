package ru.job4j.concurrent.conchashmap;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return Objects.nonNull(memory.computeIfPresent(model.getId(), (key, oldValue) -> {
            if (oldValue.getVersion() != model.getVersion()) {
                throw new OptimisticException("Разные версии Base");
            }
            oldValue.setVersion(oldValue.getVersion() + 1);
            oldValue.setName(model.getName());
            return oldValue;
        }));
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base getBase(int index) {
        return memory.get(index);
    }
}
