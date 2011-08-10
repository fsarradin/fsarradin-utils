package org.kerflyn.javafp;

import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

public class Iterators2 {

    private Iterators2() {
        throw new UnsupportedOperationException();
    }

    public static <T, R> R reduce(Iterator<T> iterator, R init, Aggregator<R, T> aggregator) {
        R result = init;
        while (iterator.hasNext()) {
            result = aggregator.apply(result, iterator.next());
        }

        return result;
    }

    public static Iterator<Integer> allIntegersFrom(final int start) {
        return new AbstractIterator<Integer>() {
            public int value = start;
            @Override protected Integer computeNext() { return value++; }
        };
    }
}
