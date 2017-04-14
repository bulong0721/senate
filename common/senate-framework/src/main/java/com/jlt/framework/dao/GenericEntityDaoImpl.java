package com.jlt.framework.dao;

import com.jlt.framework.GenericEntity;
import com.jlt.framework.util.GenericEntityUtil;
import com.querydsl.core.types.EntityPath;

import java.io.Serializable;

/**
 * @param <K> entity type
 */
public abstract class GenericEntityDaoImpl<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>>
        extends QuerydslJpaDaoSupport<K, E>
        implements GenericEntityDao<K, E> {

    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public GenericEntityDaoImpl(EntityPath<E> entityPath) {
        super(entityPath);
        this.entityClass = (Class<E>) GenericEntityUtil.getEntityClass(getClass());
    }

    protected final Class<E> getEntityClass() {
        return entityClass;
    }

    public E getById(K id) {
        return super.getEntity(getEntityClass(), id);
    }

}
