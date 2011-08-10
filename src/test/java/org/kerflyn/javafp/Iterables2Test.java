package org.kerflyn.javafp;

import com.google.common.base.Function;
import org.junit.Test;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Iterables.transform;
import static org.fest.assertions.Assertions.assertThat;
import static org.kerflyn.javafp.Iterables2.allIntegersFrom;
import static org.kerflyn.javafp.Iterables2.reduce;

public class Iterables2Test {

    @Test(timeout = 5000)
    public void shouldTransformInfiniteStreamLazily() {
        Function<Integer, Integer> squareFunction = new Function<Integer, Integer>() {
            @Override public Integer apply(Integer value) { return value * value; }
        };

        Iterable<Integer> integers = transform(allIntegersFrom(1), squareFunction);
        assertThat(limit(integers, 5)).containsOnly(1, 4, 9, 16, 25);
    }

    @Test(timeout = 5000)
    public void shouldLimitAnInfiniteStream() {
        Aggregator<Integer, Integer> multiplyAggregator = new Aggregator<Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x * y;
            }
        };

        Iterable<Integer> integers = limit(allIntegersFrom(1), 5);
        int result = reduce(integers, 1, multiplyAggregator);
        assertThat(result).isEqualTo(120);
    }

}
