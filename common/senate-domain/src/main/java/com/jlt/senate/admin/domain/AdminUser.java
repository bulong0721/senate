package com.jlt.senate.admin.domain;

import com.jlt.framework.GenericEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Martin on 2016/4/11.
 */
@Entity
@Table(name = "C_ADMIN_USER")
@DynamicUpdate
public class AdminUser extends GenericEntity<Long, AdminUser> {

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "LOGIN", length = 40, nullable = false, unique = true)
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 40)
    private String password;

    @Column(name = "PHONE_NUMBER", length = 20)
    protected String phoneNumber;

    @NotEmpty
    @Column(name = "ADMIN_EMAIL", length = 60)
    protected String email;

    @Column(name = "ACTIVE_STATUS_FLAG")
    protected Boolean activeStatusFlag = Boolean.TRUE;

    @Column(name = "GENDER")
    protected int gender = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOGIN_ACCESS")
    protected Date lastLogin;

    @Column(name = "LOGIN_IP", length = 20)
    protected String loginIP;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActiveStatusFlag() {
        return activeStatusFlag;
    }

    public void setActiveStatusFlag(Boolean activeStatusFlag) {
        this.activeStatusFlag = activeStatusFlag;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
