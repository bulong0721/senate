package com.jlt.framework;

import com.alibaba.fastjson.annotation.JSONType;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Martin on 2016/4/8.
 */
@MappedSuperclass
public abstract class GenericEntity<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>>
        implements Serializable, Comparable<E> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected K id;
    @Version
    protected int version;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date created = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modified = new Date();

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Transient
    public boolean isNew() {
        return getId() == null;
    }

    @PrePersist
    @PreUpdate
    protected void updateModifiedDate() {
        modified = new Date();
    }

    public int compareTo(E o) {
        if (this == o) {
            return 0;
        }
        return this.getId().compareTo(o.getId());
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        // In list operations with lazy loaded entities, you may get a proxy with the correct id but a different class
        else if (obj instanceof GenericEntity) {
            GenericEntity entity = (GenericEntity) obj;

            if (getId() == null || entity.getId() == null) {
                return false;
            }
            if (entity.getId().equals(getId())) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("entity.");
        builder.append(Hibernate.getClass(this).getSimpleName());
        builder.append("<");
        builder.append(getId());
        builder.append("-");
        builder.append(super.toString());
        builder.append(">");

        return builder.toString();
    }

    public String toShortString() {
        return getClass().getSimpleName() + ": [id=" + getId() + "]";
    }

    public String toUniqueString() {
        return (getId() == null ? "" : getId().toString());
    }
}
