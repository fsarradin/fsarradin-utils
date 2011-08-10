package javafp;

import com.google.common.base.Function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static javafp.FpFunctions.add;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Integer> ints = asList(1, 2, 3, 4);

        int value = FpFunctions.<Iterator<Integer>, Integer>
                _do().apply(Arrays.<Function>asList(add()).iterator())
                .apply(ints.iterator());

        System.out.println(value);
    }
}
