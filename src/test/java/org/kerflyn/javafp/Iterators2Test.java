package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Iterators.limit;
import static com.google.common.collect.Iterators.transform;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.kerflyn.javafp.Iterators2.allIntegersFrom;
import static org.kerflyn.javafp.Iterators2.foldLeft;
import static org.kerflyn.javafp.Tuple2.Tuple2;
import static org.kerflyn.javafp.Tuple3.Tuple3;

public class Iterators2Test {

    @Test(timeout = 5000)
    public void shouldTransformInfiniteStreamLazily() {
        Function<Integer, Integer> squareFunction = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
                return value * value;
            }
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
                    @Override
                    public Integer apply(Integer value) {
                        return value - 1;
                    }
                },
                new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer value) {
                        return value;
                    }
                },
                new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer value) {
                        return value + 1;
                    }
                }
        );

        Iterator<Integer> ints = Iterators2.construct(functions.iterator(), 5);

        assertThat(ints).containsOnly(4, 5, 6);
    }

    @Test
    public void shouldZip2Iterators() {
        List<Integer> values = Arrays.asList(2, 4, 6);
        Iterator<Tuple2<Integer, Integer>> result = Iterators2.zip(values.iterator(), Iterators2.allIntegersFrom(1));
        assertThat(result).containsOnly(Tuple2(2, 1), Tuple2(4, 2), Tuple2(6, 3));
    }

    @Test
    public void shouldZip3Iterators() {
        List<Integer> values = Arrays.asList(2, 4, 6);
        Iterator<Tuple3<Integer, Integer, Integer>> result = Iterators2.zip(
                values.iterator(),
                Iterators2.allIntegersFrom(1),
                Iterators.<Integer>cycle(42));
        assertThat(result).containsOnly(Tuple3(2, 1, 42), Tuple3(4, 2, 42), Tuple3(6, 3, 42));
    }

}
