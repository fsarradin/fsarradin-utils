package org.kerflyn.javafp;

import org.junit.Test;

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

}
