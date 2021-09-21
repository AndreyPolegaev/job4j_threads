package ru.job4j.concurrent.singlelocklist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>, Cloneable {

    @GuardedBy("this")
    private List<T> list;

    public SingleLockList(List<T> list) throws CloneNotSupportedException {
        this.list = (List<T>) clone();
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        this.list = (List<T>) super.clone();
        return list;
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    private synchronized List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}
