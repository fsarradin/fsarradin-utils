package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Iterables.transform;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.kerflyn.javafp.Iterables2.allIntegersFrom;
import static org.kerflyn.javafp.Iterables2.foldLeft;
import static org.kerflyn.javafp.Tuple2.Tuple2;
import static org.kerflyn.javafp.Tuple3.Tuple3;

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
        int result = foldLeft(integers, 1, multiplyAggregator);
        assertThat(result).isEqualTo(120);
    }

    @Test
    public void shouldConstructIterable() {
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

        Iterable<Integer> ints = Iterables2.construct(functions, 5);

        assertThat(ints).containsOnly(4, 5, 6);
    }

    @Test
    public void shouldZip2Iterators() {
        List<Integer> values = Arrays.asList(2, 4, 6);
        Iterable<Tuple2<Integer, Integer>> result = Iterables2.zip(values, Iterables2.allIntegersFrom(1));
        assertThat(result).containsOnly(Tuple2(2, 1), Tuple2(4, 2), Tuple2(6, 3));
    }

    @Test
    public void shouldZip3Iterators() {
        List<Integer> values = Arrays.asList(2, 4, 6);
        Iterable<Tuple3<Integer, Integer, Integer>> result = Iterables2.zip(
                values,
                Iterables2.allIntegersFrom(1),
                Iterables.<Integer>cycle(42));
        assertThat(result).containsOnly(Tuple3(2, 1, 42), Tuple3(4, 2, 42), Tuple3(6, 3, 42));
    }

    @Test
    public void shouldZipBasedOnFunction() {
        List<Integer> values = Arrays.asList(1, 2, 3);
        final Function<Tuple2<Integer, Integer>, Integer> ADD = new Function<Tuple2<Integer, Integer>, Integer>() {
            @Override
            public Integer apply(Tuple2<Integer, Integer> tuple) {
                return tuple._0 + tuple._1;
            }
        };

        Iterable<Integer> result = Iterables2.zip(values, Iterables.cycle(1), ADD);
        assertThat(result).containsOnly(2, 3, 4);
    }

}
