package org.kerflyn.javafp.internal;

import com.google.common.base.Function;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

import static com.google.common.collect.Iterators.skip;

public class FpFunctions {
        public static <T> Function<Iterator<T>, T> head() {
            return new Function<Iterator<T>, T>() {
                @Override
                public T apply(Iterator<T> iterator) {
                    return iterator.next();
                }
            };
        }

        public static <T> Function<Iterator<T>, Iterator<T>> tail() {
            return new Function<Iterator<T>, Iterator<T>>() {
                @Override
                public Iterator<T> apply(Iterator<T> iterator) {
                    skip(iterator, 1);
                    return iterator;
                }
            };
        }

        public static <A, B> Function<Function<A, B>, Function<Iterator<A>, Iterator<B>>> map() {
            return new Function<Function<A, B>, Function<Iterator<A>, Iterator<B>>>() {
                @Override
                public Function<Iterator<A>, Iterator<B>> apply(final Function<A, B> f) {
                    return new Function<Iterator<A>, Iterator<B>>() {
                        @Override
                        public Iterator<B> apply(final Iterator<A> iterator) {
                            return new AbstractIterator<B>() {
                                @Override
                                protected B computeNext() {
                                    if (iterator.hasNext()) {
                                        return f.apply(iterator.next());
                                    }
                                    return endOfData();
                                }
                            };
                        }
                    };
                }
            };
        }

        public static Function<Iterator<Integer>, Integer> add() {
            return new Function<Iterator<Integer>, Integer>() {
                public Integer apply(Iterator<Integer> iterator) {
                    Integer result = 0;
                    while (iterator.hasNext()) {
                        result += iterator.next();
                    }
                    return result;
                }
            };
        }

        public static <A, B> Function<Iterator<Function>, Function<A, B>> _do() {
            return new Function<Iterator<Function>, Function<A, B>>() {
                @Override
                public Function<A, B> apply(final Iterator<Function> functions)  {
                    return new Function<A, B>() {
                        @Override
                        @SuppressWarnings("unchecked")
                        public B apply(A input) {
                            if (!functions.hasNext()) {
                                return null;
                            }
                            Object result = functions.next().apply(input);
                            while (functions.hasNext()) {
                                result = functions.next().apply(result);
                            }
                            return (B) result;
                        }
                    };
                }
            };
        }

        public static Function<Integer, Iterator<Integer>> range() {
            return new Function<Integer, Iterator<Integer>>() {
                @Override
                public Iterator<Integer> apply(final Integer limit) {
                    return new AbstractIterator<Integer>() {
                        public Integer current = 1;
                        @Override
                        protected Integer computeNext() {
                            if (current <= limit) {
                                Integer old = current;
                                current++;
                                return old;
                            }
                            return endOfData();
                        }
                    };
                }
            };
        }
    }
