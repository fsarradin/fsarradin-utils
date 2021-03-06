package org.kerflyn.javafp.integ;

import com.google.common.base.Function;
import org.junit.Test;
import org.kerflyn.javafp.Aggregator;
import org.kerflyn.javafp.Tuple3;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Iterables.cycle;
import static com.google.common.collect.Iterables.transform;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;
import static org.kerflyn.javafp.Iterables2.*;

/**
 * FP implementation of the net present value with streams.
 *
 * <ol>
 * <li>get for each cash flow the rate and period number into a 3-uple (zip)</li>
 * <li>calculate the discount for each 3-uple (transform)</li>
 * <li>sum each discount (foldLeft)</li>
 * </ol>
 */
public class NpvTest {

    public final Function<Tuple3<Double,Double,Integer>,Double> DISCOUNT = new Function<Tuple3<Double, Double, Integer>, Double>() {
        @Override
        public Double apply(Tuple3<Double, Double, Integer> input) {
            return input._0 / Math.pow(1. + input._1, input._2);
        }
    };

    public final Aggregator<Double, Double> SUM_OF_DOUBLES = new Aggregator<Double, Double>() {
        @Override
        public Double apply(Double arg1, Double arg2) {
            return arg1 + arg2;
        }
    };

    public double npv(List<Double> cashflows, double rate) {
        Iterable<Tuple3<Double, Double, Integer>> zippedParams = zip(cashflows, cycle(rate), allIntegersFrom(1));
        Iterable<Double> discountedFlows = transform(zippedParams, DISCOUNT);
        return foldLeft(discountedFlows, 0., SUM_OF_DOUBLES);
    }

    @Test
    public void shouldGetNetPresentValue() {
        List<Double> cashflows = Arrays.asList(100000., -100., -100., -1000.);
        double rate = .04;

        Double result = npv(cashflows, rate);

        assertThat(result).isEqualTo(95117.68, delta(1e-2));
    }



}
