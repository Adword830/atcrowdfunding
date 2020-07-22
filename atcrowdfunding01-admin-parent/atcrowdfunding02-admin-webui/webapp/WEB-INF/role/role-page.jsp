<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/18
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/include/include-head.jsp"%>
    <link href="static/ztree/zTreeStyle.css" rel="stylesheet">
    <script type="application/javascript" src="static/jquery/jquery.pagination.js"></script>
    <script src="static/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="application/javascript" src="static/js/my-role.js"></script>
    <script type="application/javascript" >
        $(function () {
            window.pageNum="1";
            window.pageSize="5";
            window.keWord="";
            generatePage();
            $("#search_btn").click(function(){
                window.keWord=$("#keyWord").val();
                generatePage();
                return false;
            })
            // 新增按钮触发
            $("#saveBtn").click(function () {
                // 显示模态框
                $('#addModal').modal('show')

            })
            // 确认添加
            $("#addBtn").click(function () {
                // 获取ajax需要的参数
                var roleName=$("#addModal [name=roleName]").val();
                // 进行ajax请求后台进行新增
                $.ajax({
                    url:"role/do/add.json",
                    data:{"roleName":roleName},
                    type:"post",
                    dataType:"JSON",
                    success:function (response) {
                        // 请求成功
                        if(response.result=="SUCCESS"){
                            // 添加成功后跳转到最后一页
                            window.pageNum=999999;
                            // 使用该方法进行页面上的数据生成和分页导航栏的生成
                            generatePage();
                            layer.msg("添加成功");
                        }

                    },
                    error:function (response) {
                        // 请求失败
                        layer.msg("错误代码"+response.status+"消息提示"+response.statusText);

                    },

                })
                // 请求失败或者成功都关闭模态框
                $('#addModal').modal('hide')
                // 清除模态框里面上一次的数据
                $("#addModal [name=roleName]").val("");
            });

            //  点击新增按钮
           $("#rolePage").on("click",".pencilBtn",function () {
               // 获取当前修改的角色名称
               var name=$(this).parent().prev().text();
               // 获取到当前需要修改得id由于当前不在同一个函数中设置为全局变量
               window.id=$(this).prop("id")
               console.log($(this).prop("id"))
               // 进行表单回显
               $("#editModal [name=roleName]").val(name);
                // 显示修改模态框
               $('#editModal').modal('show');
           })
           // 确认修改按钮进行更新
           $("#editBtn").click(function () {
               // 获取到当前修改的信息
                var name=$("#editModal [name=roleName]").val();
                // 发送ajax请求
               $.ajax({
                   url:"role/do/edit.json",
                   data:{
                       "id":window.id,
                       "name":name,
                   },
                   type:"post",
                   dataType:"json",
                   success:function (response) {
                        if(response.result=="SUCCESS"){
                            layer.msg("修改成功")
                            // 再重新对body的内容进行生成
                            generatePage();
                        }
                        if(response.result=="FAILED"){
                            layer.msg("修改成功"+response.message)
                        }
                   },
                   error:function (response) {
                        layer.msg("错误代码"+response.status+"错误消息"+response.statusText)
                   }
               });
                // 关闭模态框
               $('#editModal').modal('hide');
               // 清除模态框上一次的内容
               var name=$("#editModal [name=roleName]").val("");
           })
            // 点击删除
            $("#rolePage").on("click",".removeBtn",function () {
                // 获取到要删除的id
                var roleId=this.id;
                // 获取到要删除的名字
                var roleName=$(this).parent().prev().text();
                // 构建数组对象
                var roleArray=[{roleName:roleName,roleId:roleId}]
                // 传入信息进行显示
                getRoleArray(roleArray)
            })
            // 点击确认删除按钮
            $("#removeBtn").click(function () {
                // 获取到要删除的id信息
                var idArray=JSON.stringify(window.roleId)
                // 发送ajax请求进行删除
                $.ajax({
                    url:"role/do/remove.json",
                    contentType:"application/json;charset=utf-8",
                    data:idArray,
                    dataType:"json",
                    type:"post",
                    success:function (response) {
                        if(response.result=="SUCCESS"){
                            layer.msg("删除成功")
                            // 再重新对body的内容进行生成
                            generatePage();
                        }
                        if(response.result=="FAILED"){
                            layer.msg("删除失败"+response.message)
                        }
                    },
                    error:function (response) {
                        layer.msg("出错啦"+response.status+"错误消息"+response.statusText)
                    }

                });

                // 关闭模态框
                $('#removeModal').modal('hide');
            });
            // 批量删除的信息收集
            $("#batchBtn").click(function () {

                var roleArray=[];
                // 使用each循环遍历
                $(".itemBox:checked").each(function () {
                    // 获取到要删除的id
                    var roleId=$(this).parent().prev().text();
                    // 获取到要删除的名字
                    var roleName=$(this).parent().next().text();
                    // 构建数组对象
                    roleArray.push({roleId:roleId,roleName:roleName})
                })
                if(roleArray.length==0){
                    layer.msg("请至少选择一项进行删除");
                    return;
                }
                // 传入信息进行显示
                getRoleArray(roleArray);
            })
           // 全选全不选功能
            $("#checkBox").click(function () {
                var checkedStatus=this.checked;
                $(".itemBox").prop("checked",checkedStatus)
            })
            $("#rolePage").on("click",".itemBox",function () {
                // 获取当前被选中的checkeBox的数量
                var checkBox_checked=$(".itemBox:checked").length;
                // 获取所有的checkBox数量
                var checkBox=$(".itemBox").length;

                $("#checkBox").prop("checked",checkBox_checked==checkBox);
            })
            //点击权限分配按钮
            $("#rolePage").on("click",".checkBtn",function () {
                window.roleId=this.id;
                // 弹出模态框
                $("#assignModal").modal("show")
                //
                fillAssignRoleAuthTree();
            })
            // 点击保存角色权限更新的按钮
            $("#assignBtn").click(function () {
                var treeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                // 获取到当前被选中的ztree节点
                var checkedNodes = treeObj.getCheckedNodes();
                // 生命新的数组
                var authIdArray=[];
                for(var i=0;i<checkedNodes.length;i++){
                    var authId=checkedNodes[i].id;
                    authIdArray.push(authId);
                }
                // 获取到当前要分配权限的角色
                var roleId=window.roleId;

                // 重新构建一个json对象统一类型
                var requestBody={
                    roleId:[roleId],
                    authId:authIdArray
                }
                requestBody=JSON.stringify(requestBody);

                // 发送ajax请求进行保存
                $.ajax({
                    url:"assign/role/auth/save.json",
                    type:"post",
                    data:requestBody,
                    contentType: "application/json;charset=utf-8",
                    dataType:"json",
                    success:function (response) {
                        if(response.result=="SUCCESS"){
                            layer.msg("操作成功")
                        }
                        if(response.result=="FAILED"){
                            layer.msg("操作失败"+response.message)
                        }
                    },
                    error:function (response) {
                        layer.msg(response.status+"  "+response.statusText)
                    }
                });
                $("#assignModal").modal("hide");

            })

        })

    </script>
</head>
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
                    <form class="form-inline" role="form"  method="post" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" id="keyWord" type="text" name="keyWord" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="search_btn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="batchBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="saveBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkBox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePage">

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
<%@include file="/WEB-INF/modal/modal-add-role.jsp"%>
<%@include file="/WEB-INF/modal/modal-edit-role.jsp"%>
<%@include file="/WEB-INF/modal/modal-remove-role.jsp"%>
<%@include file="/WEB-INF/modal/modal-role-assign-auth.jsp"%>
</body>
</html>
