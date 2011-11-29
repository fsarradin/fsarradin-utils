package org.kerflyn.javafp;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;

public class StreamsTest {

    @Test
    public void shouldGetStreamFromIterable() {
        Stream<Integer> result = Streams.from(Arrays.asList(1, 2, 3));
        assertThat(result).containsOnly(1, 2, 3);
    }

    @Test
    public void shouldGetStreamFromIterator() {
        Stream<Integer> result = Streams.from(Arrays.asList(1, 2, 3).iterator());
        assertThat(result).containsOnly(1, 2, 3);
    }

    @Test
    public void shouldZipStreams() {
        Stream<Integer> stream1 = Streams.from(Arrays.asList(1, 2, 3).iterator());
        Stream<Integer> stream2 = Streams.from(Iterables.cycle(1));
        Stream<Integer> result = Streams.zip(stream1, stream2, new Function<Tuple2<Integer, Integer>, Integer>() {
            @Override
            public Integer apply(Tuple2<Integer, Integer> input) {
                return input._0 + input._1;
            }
        });

        assertThat(result).containsOnly(2, 3, 4);
    }

}
