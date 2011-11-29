package org.kerflyn.javafp;

import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

public class Stream<T> implements Iterable<T> {
    private T value;
    private Supplier<Stream<T>> supplier;

    public Stream(T value, Supplier<Stream<T>> supplier) {
        this.value = value;
        this.supplier = supplier;
    }

    public T head() {
        return value;
    }

    public Stream<T> tail() {
        return supplier.get();
    }

    @Override
    public Iterator<T> iterator() {
        return new AbstractIterator<T>() {
            Stream<T> stream = Stream.this;

            @Override
            protected T computeNext() {
                T current = stream.head();
                stream = stream.tail();
                return current;
            }
        };
    }
}
