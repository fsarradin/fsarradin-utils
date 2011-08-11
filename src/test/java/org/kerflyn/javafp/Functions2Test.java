package org.kerflyn.javafp;

import com.google.common.base.Function;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class Functions2Test {

    @Test
    public void shouldCurryfy() {
        Function2<Integer, Integer, Integer> add2int = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer value1, Integer value2) {
                return value1 + value2;
            }
        };

        Function<Integer, Function<Integer, Integer>> result = Functions2.curryfy(add2int);

        assertThat(result.apply(4).apply(6)).isEqualTo(add2int.apply(4, 6));
    }

    @Test
    public void shouldFlipArguments() {
        Function2<Integer, Boolean, Integer> incrOnFlag = new Function2<Integer, Boolean, Integer>() {
            @Override
            public Integer apply(Integer value, Boolean shouldIncr) {
                return shouldIncr ? (value + 1) : value;
            }
        };

        Function2<Boolean, Integer, Integer> result = Functions2.flip(incrOnFlag);

        assertThat(result.apply(true, 1)).isEqualTo(incrOnFlag.apply(1, true));
    }

    @Test
    public void shouldFlipArgumentsOfCurryfiedFunction() {
        Function<Integer, Function<Boolean, Integer>> incrOnFlag = Functions2.curryfy(new Function2<Integer, Boolean, Integer>() {
            @Override
            public Integer apply(Integer value, Boolean shouldIncr) {
                return shouldIncr ? (value + 1) : value;
            }
        });

        Function<Boolean, Function<Integer, Integer>> result = Functions2.flip(incrOnFlag);

        assertThat(result.apply(true).apply(1)).isEqualTo(incrOnFlag.apply(1).apply(true));
    }

}
