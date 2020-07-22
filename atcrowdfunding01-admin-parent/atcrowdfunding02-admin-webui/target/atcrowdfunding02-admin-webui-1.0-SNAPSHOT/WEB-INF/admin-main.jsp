<%--
  Created by IntelliJ IDEA.
  User: hopu
  Date: 2020/6/12
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <security:authentication property="principal.password"/><br/>
            Principal：<security:authentication property="principal.class.name"/><br/> 访 问 SecurityAdmin 对 象 的 属 性 ：
            <security:authentication property="principal.admin.loginAcct"/><br/> 访 问 SecurityAdmin 对 象 的 属 性 ：
            <security:authentication property="principal.admin.userPswd"/><br/> 访 问 SecurityAdmin 对 象 的 属 性 ：
            <security:authentication property="principal.admin.userName"/><br/> 访 问 SecurityAdmin 对 象 的 属 性 ：
            <security:authentication property="principal.admin.email"/><br/> 访 问 SecurityAdmin 对 象 的 属 性 ：
            <security:authentication property="principal.admin.createTime"/><br/>
            <div class="row placeholders">
                <security:authorize access="hasRole('经理')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img data-src="holder.js/200x200/auto/sky" class="img-responsive"
                             alt="Generic placeholder thumbnail">
                        <h4>Label</h4>
                        <span class="text-muted">Something else</span>
                    </div>
                </security:authorize>
                <security:authorize access="hasAuthority('uesr:get')">
                    <div class="col-xs-6 col-sm-3 placeholder">
                        <img data-src="holder.js/200x200/auto/vine" class="img-responsive"
                             alt="Generic placeholder thumbnail">
                        <h4>Label</h4>
                        <span class="text-muted">Something else</span>
                    </div>
                </security:authorize>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/sky" class="img-responsive"
                         alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/vine" class="img-responsive"
                         alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
