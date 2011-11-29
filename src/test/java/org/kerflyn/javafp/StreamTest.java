package org.kerflyn.javafp;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Iterator;

import static com.google.common.collect.Iterators.limit;
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

}
