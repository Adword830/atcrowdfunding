package com.houpu.crowd.mvc.interceptors;

import com.houpu.crowd.entity.Admin;
import com.houpu.crowd.exception.AccessPermissionsException;
import com.houpu.crowd.util.constant.CrowdConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptors extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取session
        HttpSession session = request.getSession();
        // 2.查看session中是否存在用户
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_NAME);
        if(admin == null){
            // 3.抛出当前您没有访问权限的异常
            throw new AccessPermissionsException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        // 4.当前用户存在放行
        return true;
    }
}
