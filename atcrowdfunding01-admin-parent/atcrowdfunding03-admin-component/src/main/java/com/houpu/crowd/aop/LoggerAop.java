package com.houpu.crowd.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 管理web层的日志
 */
@Component
@Aspect
public class LoggerAop {

    private final Logger logger=LoggerFactory.getLogger(LoggerAop.class);
    //* *..*ServiceImpl.*(..)
    //
    @Pointcut("execution(* com.houpu.crowd..*.*(..))")
    public void log(){

    }

      @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            // 获取request对象
            HttpServletRequest request = requestAttributes.getRequest();
            // 1.获取url
            String url=request.getRequestURL().toString();
            // 2.获取ip
            String ip=request.getRemoteAddr();
            // 3.获取方法名
            String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
            // 4.获取参数
            Object args[]=joinPoint.getArgs();
            // 5.构造参数
            ResultLog resultLog=new ResultLog(url,ip,classMethod,args);
            // 6.打印日志
            logger.info("resultLog : {}",resultLog);
        }
    }
    @After("log()")
    public void doAfter(){
        //logger.info("-------after----------");
    }


    @AfterReturning(returning = "result",pointcut = "log()")
    public  void doAfterRuturn(Object result){
        // 1.打印方法传入的返回值
        logger.info("result : {}",result);
    }


}
    class ResultLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ResultLog() {
        }

        public ResultLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ResultLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
