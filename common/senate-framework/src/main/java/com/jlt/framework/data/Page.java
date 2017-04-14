package com.jlt.framework.data;

import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public interface Page<T> extends Slice<T> {

	int getTotalPages();

	long getTotalElements();

	<U> Page<U> map(Function<? super T, ? extends U> converter);
}
