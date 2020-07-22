<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/17
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="/WEB-INF/include/include-head.jsp"%>
<script type="application/javascript" src="static/jquery/jquery.pagination.js"></script>
<script>
    $(function(){
        initPagination();

        $(".btn-danger").click(function () {
            var name=$(this).parent().parent().find("td").eq(3).text();
            var flag=confirm("是否删除"+name+"的数据");
            if(flag){
                return true;
            }
            return false;
        })
    })
    // 的到总记录数
    var totalRecord=${requestScope.pageInfo.total}
    var properties={
        num_edge_entries: 1, // 边缘页数
        num_display_entries: 5, // 主体页数
        callback: pageselectCallback,// 回调函数
        items_per_page:${requestScope.pageInfo.pageSize},//每页显示几项
        current_page:${requestScope.pageInfo.pageNum-1},// 由于pagination中的页码是从第0页开始算pageInfo是从第1页开始算所以-1
        prev_text:"上一页",
        next_text:"下一页"
    }
    // 初始化分页插件
    function  initPagination() {
        $("#Pagination").pagination(totalRecord,properties);
    }
    // 回调函数是指pagination会自动调用这个函数参数值会自动传入
    function pageselectCallback(pageIndex,jquery){
        // 1.得到用户点击的页码是
        var pageNum=pageIndex+1;
        // 2.进行相关页面的查询
        window.location.href="admin/get/page.html?pageNum="+pageNum+"&keyWord=${param.keyWord}";

        // 3.由于每一个点击的超链接
        return false;
    }
</script>
<body>

<%@include file="/WEB-INF/include/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
            <%@include file="/WEB-INF/include/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" action="admin/get/page.html" method="post" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="keyWord" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='admin/to/add.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:if test="${empty requestScope.pageInfo.list}">
                                    <tr>
                                        <td colspan="6">抱歉，未查询到您所需要的数据</td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty requestScope.pageInfo.list}">
                                    <c:forEach var="admin" items="${requestScope.pageInfo.list}">
                                        <tr>
                                            <td>${admin.id}</td>
                                            <td><input type="checkbox"></td>
                                            <td>${admin.loginAcct}</td>
                                            <td>${admin.userName}</td>
                                            <td>${admin.email}</td>
                                            <td>
                                                <a href="assign/to/assign/role/page.html?pageNum=${requestScope.pageInfo.pageNum}&keyWord=${param.keyWord}&adminId=${admin.id}" type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                                <a href="admin/to/edit/page.html?pageNum=${requestScope.pageInfo.pageNum}&keyWord=${param.keyWord}&adminId=${admin.id}"  class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                                <a href="admin/do/remove/${admin.id}/${pageInfo.pageNum}/${param.keyWord}.html" type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

