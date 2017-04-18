package com.jlt.framework.service;

import com.jlt.framework.GenericEntity;
import com.jlt.framework.data.Page;
import com.jlt.framework.data.Pageable;
import com.jlt.framework.data.Sort;
import com.jlt.framework.exception.ServiceException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import java.io.Serializable;
import java.util.List;

public interface GenericEntityService<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>> extends TransactionalAspectAware {

    void save(E entity) throws ServiceException;

    void update(E entity) throws ServiceException;

    void create(E entity) throws ServiceException;

    void delete(E entity) throws ServiceException;

    E refresh(E entity);

    E findById(K id);

    List<E> list();

    E getEntity(Class<? extends E> clazz, K id);

    void flush();

    void clear();

    E findOne(Predicate predicate);

    List<E> findAll(Predicate predicate);

    List<E> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    List<E> findAll(Predicate predicate, Sort sort);

    List<E> findAll(OrderSpecifier<?>... orders);

    Page<E> findAll(Predicate predicate, Pageable pageable);

    long count(Predicate predicate);

    boolean exists(Predicate predicate);

}
