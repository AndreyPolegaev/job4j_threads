package ru.job4j.concurrent.conchashmap;

import org.junit.Test;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model1 = new Base(0, 1);
        assertTrue(cache.add(model1));
        assertFalse(cache.add(model1));
    }

    @Test(expected = OptimisticException.class)
    public void whenException() {
        Cache cache = new Cache();
        Base model1 = new Base(0, 1);
        cache.add(model1);
        Base model2 = new Base(0, 1);
        model2.setName("...");
        cache.update(model2);
    }
}