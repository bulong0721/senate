package com.jlt.framework.data;

public class PageRequest extends AbstractPageRequest {

    private static final long serialVersionUID = -4541509938956089562L;

    private final Sort sort;

    @Deprecated
    public PageRequest(int page, int size) {
        this(page, size, Sort.unsorted());
    }

    @Deprecated
    public PageRequest(int page, int size, Sort.Direction direction, String... properties) {
        this(page, size, Sort.by(direction, properties));
    }

    @Deprecated
    public PageRequest(int page, int size, Sort sort) {

        super(page, size);

        this.sort = sort;
    }

    public static PageRequest of(int page, int site) {
        return of(page, site, Sort.unsorted());
    }

    public static PageRequest of(int page, int site, Sort sort) {
        return new PageRequest(page, site, sort);
    }

    public static PageRequest of(int page, int size, Sort.Direction direction, String... properties) {
        return of(page, size, Sort.by(direction, properties));
    }

    public Sort getSort() {
        return sort;
    }

    public Pageable next() {
        return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
    }

    public PageRequest previous() {
        return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
    }

    public Pageable first() {
        return new PageRequest(0, getPageSize(), getSort());
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageRequest)) {
            return false;
        }

        PageRequest that = (PageRequest) obj;

        return super.equals(that) && this.sort.equals(that.sort);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + sort.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(), sort);
    }
}
