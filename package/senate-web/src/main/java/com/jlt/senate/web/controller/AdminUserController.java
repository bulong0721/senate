package com.jlt.senate.web.controller;

import com.jlt.framework.ajax.AjaxPageableResponse;
import com.jlt.senate.admin.domain.AdminUser;
import com.jlt.senate.admin.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin.Xu on 2017/4/11.
 */
@Controller
public class AdminUserController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService userService;

    @ResponseBody
    @RequestMapping(value = "/user/paging")
    public AjaxPageableResponse pageUsers(Model model, HttpServletRequest request) {
        AjaxPageableResponse resp = new AjaxPageableResponse();
        List<AdminUser> userList = userService.list();
        for (AdminUser user : userList) {
            Map entry = new HashMap();
            entry.put("id", user.getId());
            entry.put("login", user.getLogin());
            entry.put("name", user.getName());
            entry.put("email", user.getEmail());
            entry.put("phoneNumber", user.getPhoneNumber());
            entry.put("activeStatusFlag", user.getActiveStatusFlag());
            entry.put("lastLogin", user.getLastLogin());
            entry.put("loginIP", user.getLoginIP());
            entry.put("gender", user.getGender());
            resp.addDataEntry(entry);
        }
        return resp;
    }
}
