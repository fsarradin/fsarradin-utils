package org.kerflyn.javafp.integ;

import com.google.common.base.Function;
import com.google.common.collect.AbstractIterator;
import org.junit.Test;
import org.kerflyn.javafp.Tuple2;

import java.util.Iterator;

import static com.google.common.collect.Iterators.limit;
import static com.google.common.collect.Iterators.transform;
import static org.fest.assertions.Assertions.assertThat;
import static org.kerflyn.javafp.Iterators2.iterate;

public class FibonacciTest {

    @Test
    public void shouldGetFibonacciNumbersAsInfiniteStream() {
        Iterator<Integer> fibIterator = new AbstractIterator<Integer>() {
            int a = 1;
            int b = 0;
            @Override
            protected Integer computeNext() {
                Integer result = b;
                b = a + b;
                a = result;
                return result;
            }
        };

        assertThat(limit(fibIterator, 8)).containsOnly(0, 1, 1, 2, 3, 5, 8, 13);
    }

    @Test
    public void shouldGetFibonacciNumbersWithIterate() {
        Function<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> nextFib = new Function<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<Integer, Integer> apply(Tuple2<Integer, Integer> input) {
                return Tuple2.Tuple2(input._1, input._1 + input._0);
            }
        };

        Function<Tuple2<Integer, Integer>, Integer> getFirst = new Function<Tuple2<Integer, Integer>, Integer>() {
            @Override
            public Integer apply(Tuple2<Integer, Integer> input) {
                return input._0;
            }
        };

        Iterator<Integer> fibIterator = transform(iterate(nextFib, Tuple2.Tuple2(0, 1)), getFirst);

        assertThat(limit(fibIterator, 8)).containsOnly(0, 1, 1, 2, 3, 5, 8, 13);
    }

}
