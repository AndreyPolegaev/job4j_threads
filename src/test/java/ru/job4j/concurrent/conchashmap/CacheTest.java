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

    @Test
    public void whenSuccessUpdate() {
        Cache cache = new Cache();
        Base model1 = new Base(0, 1);
        model1.setName("name");
        cache.add(model1);
        Base user1 = cache.getBase(0);
        user1.setName("newName");
        assertTrue(cache.update(user1));
    }


    @Test(expected = OptimisticException.class)
    public void whenError() {
        Cache cache = new Cache();
        Base model1 = new Base(0, 1);
        model1.setName("name");
        cache.add(model1);
        Base user1 = cache.getBase(0);
        user1.setName("newName");
        Base user2 = new Base(0, 1);
        assertTrue(cache.update(user1));
        assertFalse(cache.update(user2));
    }

    @Test
    public void whenUpdateFailed() {
        Cache cache = new Cache();
        Base model1 = new Base(0, 1);
        cache.add(model1);
        Base model2 = new Base(5, 1);
        assertFalse(cache.update(model2));
    }

    @Test(expected = OptimisticException.class)
    public void whenException() {
        Cache cache = new Cache();
        Base model1 = new Base(0, 1);
        cache.add(model1);
        cache.update(model1);
        Base model2 = new Base(0, 1);
        model2.setName("...");
        cache.update(model2);
    }
}