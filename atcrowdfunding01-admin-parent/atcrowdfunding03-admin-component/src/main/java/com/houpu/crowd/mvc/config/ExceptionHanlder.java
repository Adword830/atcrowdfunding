package com.houpu.crowd.mvc.config;

import com.google.gson.Gson;
import com.houpu.crowd.exception.AccessPermissionsException;
import com.houpu.crowd.exception.LoginAccectAlreadyInUseException;
import com.houpu.crowd.exception.LoginAccectAlreadyInUseUpdateException;
import com.houpu.crowd.exception.LoginFailedException;
import com.houpu.crowd.util.CrowdUtil;
import com.houpu.crowd.util.ResultEntity;
import com.houpu.crowd.util.constant.CrowdConstant;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHanlder {
    /**
     * 处理登录失败的异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName="admin-login";

        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response,viewName );

        return modelAndView;
    }

    /**
     * 处理您当前没有访问权限的异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(AccessPermissionsException.class)
    public ModelAndView resolveAccessPermissionsException(AccessPermissionsException exception, HttpServletRequest request,
                                                          HttpServletResponse response) throws IOException {
        String viewName="admin-login";

        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response,viewName);

        return modelAndView;
    }
    /**
     * 测试异常处理器，处理空指针异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request,
    HttpServletResponse response) throws IOException {
        String viewName="system-error";
        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response, viewName);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView resolveRuntimeException(Exception exception, HttpServletRequest request,
                                                HttpServletResponse response)throws IOException{
        String viewName="system-error";
        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response, viewName);
        return modelAndView;
    }

    /**
     * 处理数据库唯一约束的异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(LoginAccectAlreadyInUseException.class)
    public ModelAndView resolveLoginAccectAlreadyInUseException(LoginAccectAlreadyInUseException exception, HttpServletRequest request,
                                                HttpServletResponse response)throws IOException{
        String viewName="admin-add";
        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response, viewName);
        return modelAndView;
    }
    @ExceptionHandler(LoginAccectAlreadyInUseUpdateException.class)
    public ModelAndView resolveLoginAccectAlreadyInUseUpdateException(LoginAccectAlreadyInUseUpdateException exception, HttpServletRequest request,
                                                                HttpServletResponse response)throws IOException{
        String viewName="system-error";
        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response, viewName);
        return modelAndView;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView resolveAccessDeniedException(AccessDeniedException exception, HttpServletRequest request,
                                                                      HttpServletResponse response)throws IOException{
        String viewName="system-error";
        ModelAndView modelAndView = resolveModelAndViewException(exception, request, response, viewName);
        return modelAndView;
    }
    /**
     * 异常处理的通用方法
     * @param exception 要处理的异常
     * @param request   当前的请求对向
     * @param response  当前的响应对象
     * @param viewName  异常处理完成跳转的页面
     * @return
     * @throws IOException
     */
    private ModelAndView resolveModelAndViewException(Exception exception,HttpServletRequest request,
    HttpServletResponse response,String viewName) throws IOException {
        response.setContentType("text/json; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Boolean type = CrowdUtil.judgeRequestType(request);
        // 1.判断是普通请求还是ajax请求
        if(type){
            // 2.是ajax请求
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            // 3.将对象转为json字符串
            Gson gson=new Gson();
            String json = gson.toJson(failed);
            // 4.把创建好的json字符串用原生的response对象返回给浏览器
            PrintWriter writer = response.getWriter();
            System.out.println(json);
            writer.write(json);
            return null;

        }
        // 5.是普通请求
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        // 6.去到异常页面
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
