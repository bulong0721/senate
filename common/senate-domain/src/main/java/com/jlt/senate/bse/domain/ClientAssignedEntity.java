package com.jlt.senate.bse.domain;

import com.jlt.framework.GenericEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class ClientAssignedEntity<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>> extends GenericEntity<K, E> {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return a description of this entity
     */
    public String toShortString() {
        return super.toShortString() + "[client=" + client.getNumber() + "]";
    }

}
