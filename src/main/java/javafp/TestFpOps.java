package javafp;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;

import java.util.*;

import static java.util.Arrays.asList;
import static javafp.Iterators2.reduce;
import static javafp.Tuple2.Tuple2;

public class TestFpOps {

    public static class Address {
        public String city;
        public String country;

        public Address(String city, String country) {
            this.city = city;
            this.country = country;
        }

        @Override
        public String toString() {
            return "Address(" + city + ", " + country + ")";
        }
    }

    public static class Person {
        public int someId;
        public String name;
        public Address address;

        public Person(int someId, String name, Address address) {
            this.someId = someId;
            this.name = name;
            this.address = address;
        }

        @Override
        public String toString() {
            return "Person(" + name + ", " + address + ")";
        }
    }

    public static Person Person(int someId, String name, String city, String country) {
        return new Person(someId, name, new Address(city, country));
    }

    public static void main(String[] args) {
        List<Person> persons = asList(
                Person(1, "A", "Paris", "France"),
                Person(2, "B", "Nantes", "France"),
                Person(3, "C", "London", "United Kingdom"),
                Person(4, "D", "Frankfurt-am-Main", "Deutschland"),
                Person(5, "E", "San Francisco", "United States of America"),
                Person(6, "F", "London", "United Kingdom"),
                Person(7, "F", "New York", "United States of America")
        );

        List<Person> result;

//        result = getPersonsWithMaxCountryName_ip(persons);
        result = getPersonsWithMaxCountryName_fp(persons);

        System.out.println(result);
    }

    private static List<Person> getPersonsWithMaxCountryName_fp(List<Person> persons) {
        List<Person> emptyList = Lists.newArrayList();
        Tuple2<List<Person>, Integer> init = Tuple2(emptyList, 0);
        return reduce(toPersonAndLength(persons), init, new MaxOf<Person>())._0;
    }

    private static List<Person> getPersonsWithMaxCountryName_ip(List<Person> persons) {
        List<Person> result = Lists.newArrayList();
        int maxLen = 0;
        for (Person person : persons) {
            int len = person.address.country.length();
            if (len > maxLen) {
                result.clear();
                result.add(person);
                maxLen = len;
            } else if (len == maxLen) {
                result.add(person);
            }
        }
        return result;
    }

    public static class MaxOf<T> implements Aggregator<Tuple2<List<T>, Integer>, Tuple2<T, Integer>> {
        @Override
        public Tuple2<List<T>, Integer> apply(Tuple2<List<T>, Integer> acc, Tuple2<T, Integer> input) {
            int maxlen = acc._1;
            int len = input._1;
            if (len > maxlen) {
                return Tuple2((List<T>) Lists.newArrayList(input._0), len);
            } else if (len == maxlen) {
                acc._0.add(input._0);
                return acc;
            }
            return acc;
        }
    }

    private static Iterator<Tuple2<Person, Integer>> toPersonAndLength(final List<Person> persons) {
        return new AbstractIterator<Tuple2<Person, Integer>>() {
            private Iterator<Person> iterator = persons.iterator();

            @Override
            protected Tuple2<Person, Integer> computeNext() {
                if (!iterator.hasNext())
                    return endOfData();
                Person person = iterator.next();
                return Tuple2(person, person.address.country.length());
            }
        };
    }

}
