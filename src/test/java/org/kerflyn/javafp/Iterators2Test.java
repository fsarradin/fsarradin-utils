package org.kerflyn.javafp;

import com.google.common.base.Function;
import org.junit.Test;

import java.util.Iterator;

import static com.google.common.collect.Iterators.limit;
import static com.google.common.collect.Iterators.transform;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.kerflyn.javafp.Iterators2.allIntegersFrom;
import static org.kerflyn.javafp.Iterators2.foldLeft;

public class Iterators2Test {

    @Test(timeout = 5000)
    public void shouldTransformInfiniteStreamLazily() {
        Function<Integer, Integer> squareFunction = new Function<Integer, Integer>() {
            @Override public Integer apply(Integer value) { return value * value; }
        };

        Iterator<Integer> integers = transform(allIntegersFrom(1), squareFunction);
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

        Iterator<Integer> integers = limit(allIntegersFrom(1), 5);
        int result = foldLeft(integers, 1, multiplyAggregator);
        assertThat(result).isEqualTo(120);
    }

    @Test
    public void shouldConstructIterator() {
        Iterable<Function<Integer, Integer>> functions = asList(
                new Function<Integer, Integer>() {
                    @Override public Integer apply(Integer value) {
                        return value - 1;
                    }
                },
                new Function<Integer, Integer>() {
                    @Override public Integer apply(Integer value) {
                        return value;
                    }
                },
                new Function<Integer, Integer>() {
                    @Override public Integer apply(Integer value) {
                        return value + 1;
                    }
                }
        );

        Iterator<Integer> ints = Iterators2.construct(functions.iterator(), 5);

        assertThat(ints).containsOnly(4, 5, 6);
    }

}
