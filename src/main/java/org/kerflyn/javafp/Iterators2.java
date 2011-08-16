package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;

import javax.swing.border.EtchedBorder;
import java.util.Iterator;

import static com.google.common.collect.Iterators.concat;
import static com.google.common.collect.Iterators.emptyIterator;
import static com.google.common.collect.Iterators.singletonIterator;

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

    public static <T0, T1> Iterator<Tuple2<T0, T1>> zip(final Iterator<T0> iterator0,
                                                        final Iterator<T1> iterator1) {
        return new AbstractIterator<Tuple2<T0, T1>>() {
            @Override
            protected Tuple2<T0, T1> computeNext() {
                if (!(iterator0.hasNext() && iterator1.hasNext())) {
                    return endOfData();
                }
                return Tuple2.Tuple2(iterator0.next(), iterator1.next());
            }
        };
    }

    public static <T0, T1, T2> Iterator<Tuple3<T0, T1, T2>> zip(final Iterator<T0> iterator0,
                                                                final Iterator<T1> iterator1,
                                                                final Iterator<T2> iterator2) {
        return new AbstractIterator<Tuple3<T0, T1, T2>>() {
            @Override
            protected Tuple3<T0, T1, T2> computeNext() {
                if (!(iterator0.hasNext() && iterator1.hasNext() && iterator2.hasNext())) {
                    return endOfData();
                }
                return Tuple3.Tuple3(iterator0.next(), iterator1.next(), iterator2.next());
            }
        };
    }

    public static <T> Iterator<T> limitWhen(final Iterator<T> iterator, final Predicate<T> predicate) {
        return new AbstractIterator<T>() {
            @Override
            protected T computeNext() {
                if (!iterator.hasNext()) {
                    return endOfData();
                }
                T value = iterator.next();
                if (predicate.apply(value)) {
                    return endOfData();
                }
                return value;
            }
        };
    }

    public static <T> Iterator<T> skipWhile(final Iterator<T> iterator, final Predicate<T> predicate) {
        T value = iterator.next();
        while (iterator.hasNext() && predicate.apply(value)) {
            value = iterator.next();
        }
        if (predicate.apply(value)) {
            return emptyIterator();
        }
        return concat(singletonIterator(value), iterator);
    }
}
