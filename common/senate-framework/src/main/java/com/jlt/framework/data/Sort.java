package com.jlt.framework.data;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Sort implements Iterable<Sort.Order>, Serializable {

    private static final long serialVersionUID = 5737186511678863905L;

    private static final Sort UNSORTED = Sort.by(new Order[0]);

    public static final Direction DEFAULT_DIRECTION = Direction.ASC;

    private final List<Order> orders;

    @Deprecated
    public Sort(Order... orders) {
        this(Arrays.asList(orders));
    }

    @Deprecated
    public Sort(List<Order> orders) {

        Assert.notNull(orders, "Orders must not be null!");

        this.orders = Collections.unmodifiableList(orders);
    }

    @Deprecated
    public Sort(String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    public Sort(Direction direction, String... properties) {
        this(direction, properties == null ? new ArrayList<>() : Arrays.asList(properties));
    }

    public Sort(Direction direction, List<String> properties) {

        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }

        this.orders = new ArrayList<>(properties.size());

        for (String property : properties) {
            this.orders.add(new Order(direction, property));
        }
    }

    public static Sort by(String... properties) {

        Assert.notNull(properties, "Properties must not be null!");

        return properties.length == 0 ? Sort.unsorted() : new Sort(properties);
    }

    public static Sort by(List<Order> orders) {

        Assert.notNull(orders, "Orders must not be null!");

        return orders.isEmpty() ? Sort.unsorted() : new Sort(orders);
    }

    public static Sort by(Order... orders) {

        Assert.notNull(orders, "Orders must not be null!");

        return new Sort(orders);
    }

    public static Sort by(Direction direction, String... properties) {

        Assert.notNull(direction, "Direction must not be null!");
        Assert.notNull(properties, "Properties must not be null!");
        Assert.isTrue(properties.length > 0, "At least one property must be given!");

        return Sort.by(Arrays.stream(properties)//
                .map(it -> new Order(direction, it))//
                .collect(Collectors.toList()));
    }

    public static Sort unsorted() {
        return UNSORTED;
    }

    public Sort descending() {
        return withDirection(Direction.DESC);
    }

    public Sort ascending() {
        return withDirection(Direction.ASC);
    }

    public boolean isSorted() {
        return !orders.isEmpty();
    }

    public boolean isUnsorted() {
        return !isSorted();
    }

    public Sort and(Sort sort) {

        if (sort == null) {
            return this;
        }

        ArrayList<Order> these = new ArrayList<>(this.orders);

        for (Order order : sort) {
            these.add(order);
        }

        return Sort.by(these);
    }

    public Order getOrderFor(String property) {

        for (Order order : this) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }

        return null;
    }

    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Sort)) {
            return false;
        }

        Sort that = (Sort) obj;

        return this.orders.equals(that.orders);
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + orders.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return orders.isEmpty() ? "UNSORTED" : StringUtils.collectionToCommaDelimitedString(orders);
    }

    private Sort withDirection(Direction direction) {

        return Sort.by(orders.stream().map(it -> new Order(direction, it.getProperty())).collect(Collectors.toList()));
    }

    public static enum Direction {

        ASC, DESC;

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }

        public static Direction fromString(String value) {

            try {
                return Direction.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
            }
        }

        public static Optional<Direction> fromOptionalString(String value) {

            try {
                return Optional.of(fromString(value));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }

    public static enum NullHandling {

        /**
         * Lets the data store decide what to do with nulls.
         */
        NATIVE,

        /**
         * A hint to the used data store to order entries with null values before non null entries.
         */
        NULLS_FIRST,

        /**
         * A hint to the used data store to order entries with null values after non null entries.
         */
        NULLS_LAST;
    }

    public static class Order implements Serializable {

        private static final long serialVersionUID = 1522511010900108987L;
        private static final boolean DEFAULT_IGNORE_CASE = false;

        private final Direction direction;
        private final String property;
        private final boolean ignoreCase;
        private final NullHandling nullHandling;

        public Order(Direction direction, String property) {
            this(direction, property, DEFAULT_IGNORE_CASE, null);
        }

        public Order(Direction direction, String property, NullHandling nullHandlingHint) {
            this(direction, property, DEFAULT_IGNORE_CASE, nullHandlingHint);
        }

        public Order(String property) {
            this(DEFAULT_DIRECTION, property);
        }

        public static Order by(String property) {
            return new Order(property);
        }

        private Order(Direction direction, String property, boolean ignoreCase, NullHandling nullHandling) {

            if (!StringUtils.hasText(property)) {
                throw new IllegalArgumentException("Property must not null or empty!");
            }

            this.direction = direction == null ? DEFAULT_DIRECTION : direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullHandling = nullHandling == null ? NullHandling.NATIVE : nullHandling;
        }

        public Direction getDirection() {
            return direction;
        }

        public String getProperty() {
            return property;
        }

        public boolean isAscending() {
            return this.direction.isAscending();
        }

        public boolean isDescending() {
            return this.direction.isDescending();
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public Order with(Direction direction) {
            return new Order(direction, this.property, this.ignoreCase, this.nullHandling);
        }

        public Order withProperty(String property) {
            return new Order(this.direction, property, this.ignoreCase, this.nullHandling);
        }

        public Sort withProperties(String... properties) {
            return Sort.by(this.direction, properties);
        }

        public Order ignoreCase() {
            return new Order(direction, property, true, nullHandling);
        }

        public Order with(NullHandling nullHandling) {
            return new Order(direction, this.property, ignoreCase, nullHandling);
        }

        public Order nullsFirst() {
            return with(NullHandling.NULLS_FIRST);
        }

        public Order nullsLast() {
            return with(NullHandling.NULLS_LAST);
        }

        public Order nullsNative() {
            return with(NullHandling.NATIVE);
        }

        public NullHandling getNullHandling() {
            return nullHandling;
        }

        @Override
        public int hashCode() {

            int result = 17;

            result = 31 * result + direction.hashCode();
            result = 31 * result + property.hashCode();
            result = 31 * result + (ignoreCase ? 1 : 0);
            result = 31 * result + nullHandling.hashCode();

            return result;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (!(obj instanceof Order)) {
                return false;
            }

            Order that = (Order) obj;

            return this.direction.equals(that.direction) && this.property.equals(that.property)
                    && this.ignoreCase == that.ignoreCase && this.nullHandling.equals(that.nullHandling);
        }

        @Override
        public String toString() {

            String result = String.format("%s: %s", property, direction);

            if (!NullHandling.NATIVE.equals(nullHandling)) {
                result += ", " + nullHandling;
            }

            if (ignoreCase) {
                result += ", ignoring case";
            }

            return result;
        }
    }
}
