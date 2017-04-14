package com.jlt.framework.data;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@FunctionalInterface
public interface Streamable<T> extends Iterable<T> {

    static <T> Streamable<T> empty() {
        return Collections::emptyIterator;
    }

    @SafeVarargs
    static <T> Streamable<T> of(T... t) {
        return () -> Arrays.asList(t).iterator();
    }

    static <T> Streamable<T> of(Iterable<T> iterable) {

        Assert.notNull(iterable, "Iterable must not be null!");

        return iterable::iterator;
    }

    static <T> Streamable<T> of(Supplier<? extends Stream<T>> supplier) {
        return null;
    }

    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default <R> Streamable<R> map(Function<? super T, ? extends R> mapper) {

        Assert.notNull(mapper, "Mapping function must not be null!");

        return Streamable.of(() -> stream().map(mapper));
    }

    default <R> Streamable<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {

        Assert.notNull(mapper, "Mapping function must not be null!");

        return Streamable.of(() -> stream().flatMap(mapper));
    }

    default Streamable<T> filter(Predicate<? super T> predicate) {

        Assert.notNull(predicate, "Filter predicate must not be null!");

        return Streamable.of(() -> stream().filter(predicate));
    }
}
