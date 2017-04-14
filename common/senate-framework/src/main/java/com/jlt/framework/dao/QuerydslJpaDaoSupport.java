package com.jlt.framework.dao;

import com.jlt.framework.dao.querydsl.QSort;
import com.jlt.framework.dao.querydsl.Querydsl;
import com.jlt.framework.data.Page;
import com.jlt.framework.data.PageUtil;
import com.jlt.framework.data.Pageable;
import com.jlt.framework.data.Sort;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Martin.Xu on 2017/4/13.
 */
@Repository("queryDslDao")
@Transactional
public class QuerydslJpaDaoSupport<K, E> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected final EntityPath<E> entityPath;
    private Querydsl querydsl;

    public QuerydslJpaDaoSupport(EntityPath<E> entityPath) {
        this.entityPath = entityPath;
        PathBuilder<E> builder = new PathBuilder<E>(entityPath.getType(), entityPath.getMetadata());

    }

    protected Querydsl getQuerydsl() {
        if (null == querydsl) {
            synchronized (this) {
                querydsl = new Querydsl(getEntityManager());
            }
        }
        return querydsl;
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void clear() {
        getEntityManager().clear();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public E findOne(Predicate predicate) {
        return createQuery(predicate).select(entityPath).fetchOne();
    }

    public List<E> findAll(Predicate predicate) {
        return createQuery(predicate).select(entityPath).fetch();
    }

    public List<E> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return executeSorted(createQuery(predicate).select(entityPath), orders);
    }

    public List<E> findAll(Predicate predicate, Sort sort) {
        Assert.notNull(sort, "Sort must not be null!");
        return executeSorted(createQuery(predicate).select(entityPath), sort);
    }

    public List<E> findAll(OrderSpecifier<?>... orders) {
        Assert.notNull(orders, "Order specifiers must not be null!");
        return executeSorted(createQuery().select(entityPath), orders);
    }

    public Page<E> findAll(Predicate predicate, Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null!");
        final JPQLQuery<?> countQuery = createCountQuery(predicate);
        JPQLQuery<E> query = getQuerydsl().applyPagination(pageable, createQuery(predicate).select(entityPath));

        return PageUtil.getPage(query.fetch(), pageable, () -> countQuery.fetchCount());
    }

    public long count(Predicate predicate) {
        return createQuery(predicate).fetchCount();
    }

    public boolean exists(Predicate predicate) {
        return createQuery(predicate).fetchCount() > 0;
    }

    protected JPQLQuery<?> createQuery(Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = getQuerydsl().createQuery(entityPath).where(predicate);
        return query;
    }

    protected JPQLQuery<?> createCountQuery(Predicate predicate) {
        return getQuerydsl().createQuery(entityPath).where(predicate);
    }

    private List<E> executeSorted(JPQLQuery<E> query, OrderSpecifier<?>... orders) {
        return executeSorted(query, new QSort(orders));
    }

    private List<E> executeSorted(JPQLQuery<E> query, Sort sort) {
        return getQuerydsl().applySorting(sort, query).fetch();
    }

    public <T> void update(T entity) {
        if (!getEntityManager().contains(entity)) {
            getEntityManager().merge(entity);
            //throw new PersistenceException("Updated entity must be attached");
        }
        //TODO: http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/
    }

    public <T> void save(T entity) {
        getEntityManager().persist(entity);
    }

    public <T> void delete(T entity) {
        if (!getEntityManager().contains(entity)) {
            getEntityManager().merge(entity);
            //throw new PersistenceException("Failed to delete a detached entity");
        }
        getEntityManager().remove(entity);
    }

    public <T> T refresh(T entity) {
        getEntityManager().refresh(entity);

        return entity;
    }

    public E getEntity(Class<? extends E> clazz, K id) {
        return getEntityManager().find(clazz, id);
    }

}
