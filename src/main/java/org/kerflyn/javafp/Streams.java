package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterators;

import java.util.Iterator;

public class Streams {

    private static final Stream<?> EMPTY_STREAM = new Stream<Object>(null, null) {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<Object> iterator() {
            return Iterators.emptyIterator();
        }
    };

    public static <T> Stream<T> emptyStream() {
        return (Stream) EMPTY_STREAM;
    }

    public static <T> Stream<T> from(Iterable<T> iterable) {
        return from(iterable.iterator());
    }

    public static <T> Stream<T> from(final Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return emptyStream();
        }
        return new Stream<T>(iterator.next(), new Supplier<Stream<T>>() {
            @Override
            public Stream<T> get() {
                return from(iterator);
            }
        });
    }

    public static <T1, T2, R> Stream<R> zip(final Stream<T1> stream1, final Stream<T2> stream2, final Function<Tuple2<T1, T2>, R> function) {
        if (stream1.isEmpty() || stream2.isEmpty()) {
            return emptyStream();
        }
        return new Stream<R>(function.apply(Tuple2.Tuple2(stream1.head(), stream2.head())),
                new Supplier<Stream<R>>() {
                    @Override
                    public Stream<R> get() {
                        return zip(stream1.tail(), stream2.tail(), function);
                    }
                });
    }
}
