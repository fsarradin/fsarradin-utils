package org.kerflyn.javafp;

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

    public static <T, R> R reduce(Iterable<T> elements, R init, Aggregator<R, T> aggregator) {
        return Iterators2.reduce(elements.iterator(), init, aggregator);
    }

}
