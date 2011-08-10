package org.kerflyn.javafp;

import com.google.common.base.Function;

import java.util.Iterator;

public class Iterables2 {

    private Iterables2() {
        throw new UnsupportedOperationException();
    }

    public static Iterable<Integer> allIntegersFrom(final int start) {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return Iterators2.allIntegersFrom(start);
            }
        };
    }

    public static <T, R> R foldLeft(Iterable<T> elements, R init, Aggregator<R, T> aggregator) {
        return Iterators2.foldLeft(elements.iterator(), init, aggregator);
    }

    public static <T, R> Iterable<R> construct(final Iterable<Function<T, R>> functions, final T value) {
        return new Iterable<R>() {
            @Override
            public Iterator<R> iterator() {
                return Iterators2.construct(functions.iterator(), value);
            }
        };
    }

}
