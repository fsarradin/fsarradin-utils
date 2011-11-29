package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class StreamTest {

    public Stream<Integer> integers(final int n) {
        return new Stream<Integer>(n, new Supplier<Stream<Integer>>() {
            @Override
            public Stream<Integer> get() {
                return integers(n + 1);
            }
        });
    }

    @Test
    public void shouldReturnSeriesOfIntegers() {
        Stream<Integer> result = integers(1);
        assertThat(Iterables.limit(result, 5)).containsOnly(1, 2, 3, 4, 5);
    }

    public Stream<Integer> fibs1(final int a, final int b) {
        return new Stream<Integer>(a, new Supplier<Stream<Integer>>() {
            @Override
            public Stream<Integer> get() {
                return fibs1(b, a + b);
            }
        });
    }

    @Test
    public void shouldReturnFibonacciSeries1() {
        Stream<Integer> result = fibs1(1, 1);
        assertThat(Iterables.limit(result, 8)).containsOnly(1, 1, 2, 3, 5, 8, 13, 21);
    }

    public Stream<Integer> fibs2() {
        return new Stream<Integer>(1, new Supplier<Stream<Integer>>() {
            @Override
            public Stream<Integer> get() {
                return new Stream<Integer>(1, new Supplier<Stream<Integer>>() {
                    @Override
                    public Stream<Integer> get() {
                        final Stream<Integer> fibStream = fibs2();
                        return Streams.zip(fibStream, fibStream.tail(), new Function<Tuple2<Integer, Integer>, Integer>() {
                            @Override
                            public Integer apply(Tuple2<Integer, Integer> tuple) {
                                return tuple._0 + tuple._1;
                            }
                        });
                    }
                });
            }
        });
    }

    @Test
    public void shouldReturnFibonacciSeries2() {
        assertThat(Iterables.limit(fibs2(), 8)).containsOnly(1, 1, 2, 3, 5, 8, 13, 21);
        // FIXME: risk of StackOverflowException here
//        assertThat(Iterables.limit(fibs2(), 29)).isNotEmpty();
    }

}
