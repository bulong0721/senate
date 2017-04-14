package com.jlt.senate.admin.service.impl;

import com.jlt.senate.admin.domain.AdminUser;
import com.jlt.senate.admin.dao.AdminUserDao;
import com.jlt.senate.admin.service.AdminUserService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.jlt.framework.service.GenericEntityServiceImpl;

@Service
public class AdminUserServiceImpl extends GenericEntityServiceImpl<Long, AdminUser> implements AdminUserService {

    protected final AdminUserDao dao;

    @Autowired
    public AdminUserServiceImpl(AdminUserDao dao) {
        super(dao);
        this.dao = dao;
    }

}

