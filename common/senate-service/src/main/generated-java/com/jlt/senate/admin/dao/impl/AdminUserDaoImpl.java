package com.jlt.senate.admin.dao.impl;

import com.jlt.senate.admin.dao.AdminUserDao;
import com.jlt.senate.admin.domain.AdminUser;
import com.jlt.senate.admin.domain.QAdminUser;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import com.jlt.framework.dao.GenericEntityDaoImpl;

@Repository
public class AdminUserDaoImpl extends GenericEntityDaoImpl<Long, AdminUser> implements AdminUserDao {

    protected final QAdminUser path = QAdminUser.adminUser;

    public AdminUserDaoImpl() {
        super(QAdminUser.adminUser);
    }
}

