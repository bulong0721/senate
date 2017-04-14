package com.jlt.framework.dao.querydsl;

import com.jlt.framework.data.Pageable;
import com.jlt.framework.data.Sort;
import com.jlt.framework.data.Sort.Order;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Martin.Xu on 2017/4/13.
 */
public class Querydsl {
    private final EntityManager em;

    public Querydsl(EntityManager em) {
        this.em = em;
    }

    public <T> AbstractJPAQuery<T, JPAQuery<T>> createQuery() {
        return new JPAQuery<T>(em);
    }

    /**
     * Creates the {@link JPQLQuery} instance based on the configured {@link EntityManager}.
     *
     * @return
     */
    public AbstractJPAQuery<Object, JPAQuery<Object>> createQuery(EntityPath<?>... paths) {
        return createQuery().from(paths);
    }

    public <T> JPQLQuery<T> applyPagination(Pageable pageable, JPQLQuery<T> query) {
        if (pageable.isUnpaged()) {
            return query;
        }

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
        return applySorting(pageable.getSort(), query);
    }

    public <T> JPQLQuery<T> applySorting(Sort sort, JPQLQuery<T> query) {
        if (sort == null) {
            return query;
        }
        if (sort instanceof QSort) {
            return addOrderByFrom((QSort) sort, query);
        }

        return addOrderByFrom(sort, query);
    }

    private <T> JPQLQuery<T> addOrderByFrom(QSort qsort, JPQLQuery<T> query) {

        List<OrderSpecifier<?>> orderSpecifiers = qsort.getOrderSpecifiers();
        return query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]));
    }

    private <T> JPQLQuery<T> addOrderByFrom(Sort sort, JPQLQuery<T> query) {
        Assert.notNull(sort, "Sort must not be null!");
        Assert.notNull(query, "Query must not be null!");
        for (Order order : sort) {
            query.orderBy(toOrderSpecifier(order));
        }
        return query;
    }

    private OrderSpecifier<?> toOrderSpecifier(Order order) {
        return new OrderSpecifier(
                order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                buildOrderPropertyPathFrom(order), toQueryDslNullHandling(order.getNullHandling()));
    }

    private OrderSpecifier.NullHandling toQueryDslNullHandling(Sort.NullHandling nullHandling) {
        Assert.notNull(nullHandling, "NullHandling must not be null!");
        switch (nullHandling) {
            case NULLS_FIRST:
                return OrderSpecifier.NullHandling.NullsFirst;
            case NULLS_LAST:
                return OrderSpecifier.NullHandling.NullsLast;
            case NATIVE:
            default:
                return OrderSpecifier.NullHandling.Default;
        }
    }

    /**
     * Creates an {@link Expression} for the given {@link Order} property.
     *
     * @param order must not be {@literal null}.
     * @return
     */
    private Expression<?> buildOrderPropertyPathFrom(Order order) {
        //TODO
        Assert.notNull(order, "Order must not be null!");
        return Expressions.stringPath(order.getProperty());
    }
}
