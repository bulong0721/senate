package com.jlt.senate.web.controller;

import com.jlt.framework.ajax.AjaxPageableResponse;
import com.jlt.framework.ajax.AjaxResponse;
import com.jlt.framework.exception.ServiceException;
import com.jlt.senate.admin.domain.AdminUser;
import com.jlt.senate.admin.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
        List<AdminUser> userList = userService.findAll();
        resp.setList(userList);
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/user/save")
    public AjaxResponse saveUser(@RequestBody AdminUser user) throws ServiceException {
        userService.save(user);
        return AjaxResponse.success();
    }
}
