package com.jlt.senate.bse.domain;

import com.jlt.framework.GenericEntity;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bse_org")
public class Org extends ClientAssignedEntity<Long, Org> {
    private static final long serialVersionUID = 1L;
    @Column(nullable = false, unique = true)
    private String name = null;
    @Column(nullable = false, unique = true, name = "org_nr", length = 20)
    private String number = null;
    @Column(name = "org_code", length = 20)
    private String code = "";
    @Email
    @Column(length = 20)
    private String email = "";
    @Column(length = 20)
    private String phone = "";
    @Column(length = 20)
    private String fax = "";

    public Org() {
        super();
    }

    public Org(long id) {
        super();
        setId(id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public String toUniqueString() {
        return getNumber();
    }

    @Transient
    public boolean isSystemClient() {
        if (getId() == 0)
            return true;
        else
            return false;
    }
}
