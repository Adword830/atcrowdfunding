<%--
  Created by IntelliJ IDEA.
  User: hopu
  Date: 2020/6/27
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="/WEB-INF/include/include-head.jsp" %>
<link href="static/ztree/zTreeStyle.css" rel="stylesheet">
<script src="static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="static/js/my-menu.js"></script>
<script>
    $(function () {
        generateTree();
        // 点击添加按钮弹出模态框
        $("#treeDemo").on("click",".addBtn",function(){
            // 弹出模态框
            $("#menuAddModal").modal("show");
            // 获取到当前所点击分支的id作为添加节点的pid
            window.pid=this.id;

            return false;
        })
        // 点击模态框的保存按钮
        $("#menuSaveBtn").click(function () {
            // 获取节点的名字
            var name=$("#menuAddModal [name=name]").val();
            // 获取节点的url地址
            var url=$("#menuAddModal [name=url]").val();
            // 获取图标的url
            var icon=$("#menuAddModal [name=icon]:checked").val();
            // 发送ajax请求进行数据保存
            $.ajax({
                url:"menu/save.json",
                data:{
                    pid:window.pid,
                    name:name,
                    url:url,
                    icon:icon,
                },
                type:"post",
                dataType:"json",
                success:function (response) {
                    var result=response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功")
                        // 调用方法重新构建树形结构
                        generateTree();

                    }
                    if(result=="FAILED"){
                        layer.msg(response.message);
                    }
                },
                error:function (response) {
                    layer.msg(response.status+"  "+response.statusText)
                }
            });
            // 关闭模态框
            $("#menuAddModal").modal("hide");
            // 重置之前模态框里面的数据    jQuery中直接调用click()方法不写回调函数相当于点击当前按钮
            $("#menuResetBtn").click();
        })
        // 点击修改按钮弹出模态框
        $("#treeDemo").on("click",".editBtn",function(){
            // 弹出模态框的方法
            $("#menuEditModal").modal("show");
            // 获取到id的值设置为全局变量
            window.id=this.id;
            // 通过ztree的方法获取到相关信息
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
             var key="id";
            var value=window.id;
            var treeNode=zTreeObj.getNodeByParam(key,value,null);
            // 获取到当前菜单的名字
            $("#menuEditModal [name=name]").val(treeNode.name);
            // 获取到当前节点的url地址
            $("#menuEditModal [name=url]").val(treeNode.url);
            // 给单选按钮加上选中状态
            $("#menuEditModal [name=icon]").val([treeNode.icon]);

            return false;

        })
        // 点击模态框里面的修改按钮发送ajax请求
        $("#menuEditBtn").click(function(){
                // 获取到当前菜单的名字
                var name=$("#menuEditModal [name=name]").val();
                // 获取到当前节点的url地址
                var url=$("#menuEditModal [name=url]").val();
                // 获取到修改得图标url
                var icon=$("#menuEditBtn [name=icon]").val();
                // 发送ajax请求进行修改
                $.ajax({
                    url:"menu/edit.json",
                    data:{
                        id:window.id,
                        name:name,
                        url:url,
                        icon:icon,
                    },
                    type:"post",
                    dataType:"json",
                    success:function (response) {
                        var result=response.result;
                        if(result=="SUCCESS"){
                            layer.msg("操作成功")
                            // 调用方法重新构建树形结构
                            generateTree();

                        }
                        if(result=="FAILED"){
                            layer.msg(response.message);
                        }
                    },
                    error:function (response) {
                        layer.msg(response.status+"  "+response.statusText)
                    }
                })
            $("#menuAddModal").modal("hide");

            })
        // 点击删除弹出模态框
        $("#treeDemo").on("click",".removeBtn",function(){
            // 获取到id的值设置为全局变量
            window.id=this.id;
            // 通过ztree的方法获取到相关信息
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key="id";
            var value=window.id;
            var treeNode=zTreeObj.getNodeByParam(key,value,null);
            $("#removeNodeSpan").html("<i class='"+treeNode.icon+"'>"+treeNode.name+"</i>");
            //弹出删除提示模态框
            $("#menuConfirmModal").modal("show");
            return false;
        })
        // 用户点击确认删除
        $("#confirmBtn").click(function () {
            //发送ajax请求
            $.ajax({
                url:"menu/remove.json",
                data:{
                    id:window.id
                },
                type:"post",
                datatype:"json",
                success:function (response) {
                    var result=response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功")
                        // 调用方法重新构建树形结构
                        generateTree();

                    }
                    if(result=="FAILED"){
                        layer.msg(response.message);
                    }
                },
                error:function (response) {
                    layer.msg(response.status+"  "+response.statusText)
                }
            })
            $("#menuConfirmModal").modal("hide");
        })
    })
</script>
<body>

<%@include file="/WEB-INF/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<%@include file="/WEB-INF/modal/modal-menu-add.jsp"%>
<%@include file="/WEB-INF/modal/modal-menu-edit.jsp"%>
<%@include file="/WEB-INF/modal/modal-menu-confirm.jsp"%>
</html>

