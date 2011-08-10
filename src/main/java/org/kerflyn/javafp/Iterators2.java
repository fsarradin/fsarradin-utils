package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

public class Iterators2 {

    private Iterators2() {
        throw new UnsupportedOperationException();
    }

    public static <T, R> R foldLeft(Iterator<T> iterator, R init, Aggregator<R, T> aggregator) {
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

    public static <T, R> Iterator<R> construct(final Iterator<Function<T, R>> functions, final T value) {
        return new AbstractIterator<R>() {
            @Override
            protected R computeNext() {
                if (functions.hasNext()) {
                    return functions.next().apply(value);
                }
                return endOfData();
            }
        };
    }
}
