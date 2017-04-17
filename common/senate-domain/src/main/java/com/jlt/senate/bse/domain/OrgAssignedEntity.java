package com.jlt.senate.bse.domain;

import com.jlt.framework.GenericEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class OrgAssignedEntity<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>> extends GenericEntity<K, E> {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id")
    private Org org;

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    /**
     * @return a description of this entity
     */
    public String toShortString() {
        return super.toShortString() + "[org=" + org.getNumber() + "]";
    }

}
