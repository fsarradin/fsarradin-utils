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

    public static <T0, T1> Iterable<Tuple2<T0, T1>> zip(final Iterable<T0> iterable0,
                                                        final Iterable<T1> iterable1) {
        return new Iterable<Tuple2<T0, T1>>() {
            @Override
            public Iterator<Tuple2<T0, T1>> iterator() {
                return Iterators2.zip(iterable0.iterator(), iterable1.iterator());
            }
        };
    }

    public static <T0, T1, T2> Iterable<Tuple3<T0, T1, T2>> zip(final Iterable<T0> iterable0,
                                                                final Iterable<T1> iterable1,
                                                                final Iterable<T2> iterable2) {
        return new Iterable<Tuple3<T0, T1, T2>>() {
            @Override
            public Iterator<Tuple3<T0, T1, T2>> iterator() {
                return Iterators2.zip(iterable0.iterator(), iterable1.iterator(), iterable2.iterator());
            }
        };
    }
}
