<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/1
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: hopu
  Date: 2020/6/12
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="UTF-8">
<%@include file="/WEB-INF/include/include-head.jsp"%>
<script>
    $(function(){
        $("#rightBtn").click(function(){
            $("select:eq(0)>option:selected").appendTo($("select:eq(1)"))
        });
        $("#leftBtn").click(function(){
            $("select:eq(1)>option:selected").appendTo($("select:eq(0)"))
        });
        $("#saveBtn").click(function () {
            // 提交之前让已分配角色的下拉列表的数值进行全选
            $("select:eq(1)>option").prop("selected","selected");
        })
    })
</script>
<body>

<%@include file="/WEB-INF/include/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp"%>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.html">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline" action="assign/do/role/assign.html">
                        <input name="adminId" type="hidden" value="${param.adminId}">
                        <input name="keyWord" type="hidden" value="${param.keyWord}">
                        <input name="pageNum" type="hidden" value="${param.pageNum}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select  class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${unAssignRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="rightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="leftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select name="roleId" class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${assignRoleList}" var="role">
                                    <option  value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="saveBtn" class="btn btn-lg btn-success btn-block" style="margin: 20px auto 20px auto;width: 150px" > 保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

