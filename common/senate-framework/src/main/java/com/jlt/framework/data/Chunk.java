package com.jlt.framework.data;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class Chunk<T> implements Slice<T>, Serializable {
    private final List<T> content = new ArrayList<>();
    private final Pageable pageable;

    public Chunk(List<T> content, Pageable pageable) {

        Assert.notNull(content, "Content must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");

        this.content.addAll(content);
        this.pageable = pageable;
    }

    public int getNumber() {
        return pageable.isPaged() ? pageable.getPageNumber() : 0;
    }

    public int getSize() {
        return pageable.isPaged() ? pageable.getPageSize() : 0;
    }

    public int getNumberOfElements() {
        return content.size();
    }

    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    public boolean isFirst() {
        return !hasPrevious();
    }

    public boolean isLast() {
        return !hasNext();
    }

    public Pageable nextPageable() {
        return hasNext() ? pageable.next() : Pageable.unpaged();
    }

    public Pageable previousPageable() {
        return hasPrevious() ? pageable.previousOrFirst() : Pageable.unpaged();
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }

    public Iterator<T> iterator() {
        return content.iterator();
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        Assert.notNull(converter, "Converter must not be null!");

        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Chunk<?>)) {
            return false;
        }

        Chunk<?> that = (Chunk<?>) obj;

        boolean contentEqual = this.content.equals(that.content);
        boolean pageableEqual = this.pageable.equals(that.pageable);

        return contentEqual && pageableEqual;
    }

    @Override
    public int hashCode() {

        int result = 17;

        result += 31 * pageable.hashCode();
        result += 31 * content.hashCode();

        return result;
    }
}
