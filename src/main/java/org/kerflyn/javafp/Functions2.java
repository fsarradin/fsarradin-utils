package org.kerflyn.javafp;

import com.google.common.base.Function;

public class Functions2 {

    private Functions2() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param function
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @return ((T1, T2) -> R) -> T1 -> T2 -> R
     */
    public static <T1, T2, R> Function<T1, Function<T2, R>> curryfy(final Function2<T1, T2, R> function) {
        return new Function<T1, Function<T2, R>>() {
            @Override
            public Function<T2, R> apply(final T1 arg1) {
                return new Function<T2, R>() {
                    @Override
                    public R apply(T2 arg2) {
                        return function.apply(arg1, arg2);
                    }
                };
            }
        };
    }

    /**
     *
     * @param function
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @return ((T1, T2) -> R) -> (T2, T1) -> R
     */
    public static <T1, T2, R> Function2<T2, T1, R> flip(final Function2<T1, T2, R> function) {
        return new Function2<T2, T1, R>() {
            @Override
            public R apply(T2 arg1, T1 arg2) {
                return function.apply(arg2, arg1);
            }
        };
    }

    /**
     *
     * @param function
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @return (T1 -> T2 -> R) -> T2 -> T1 -> R
     */
    public static <T1, T2, R> Function<T2, Function<T1, R>> flip(final Function<T1, Function<T2, R>> function) {
        return new Function<T2, Function<T1, R>>() {
            @Override
            public Function<T1, R> apply(final T2 arg2) {
                return new Function<T1, R>() {
                    @Override
                    public R apply(T1 arg1) {
                        return function.apply(arg1).apply(arg2);
                    }
                };
            }
        };
    }
}
