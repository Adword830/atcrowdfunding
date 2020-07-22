// 声明方法构建出来角色权限分配的模态框
function fillAssignRoleAuthTree() {
    // 发送ajax请求
    var ajaxReturn = $.ajax({
        url: "assign/role/auth.json",
        type: "post",
        dataType: "json",
        async: false
    })

    if (ajaxReturn.status != 200) {
        layer.msg("错误状态码" + ajaxReturn.status + " " + "错误消息" + ajaxReturn.statusText);
        return;
    }
    // 获取到所有的权限
    var zNodes = ajaxReturn.responseJSON.data;

    // 配置ztree需要的参数
    var setting = {
        check: {
            // 把每一个节点设置为checkbox的形式
            enable: true,
        },
        data: {
            simpleData: {
                // 设置当前ztree的pid为
                pIdKey: "categoryId",
                // 开启简单的json
                enable: true,
            },
            key: {
                // 设置当前ztree要显示的信息的名字
                name: "title",
            },

        }
    };
    // 显示树形结构
    $.fn.zTree.init($("#authTreeDemo"), setting, zNodes);
    // 获取ztreeObj
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    // 调用方法让树形结构为展开状态
    zTreeObj.expandAll(true);
    // 把当前角色分配的权限查询出来
    ajaxReturn = $.ajax({
        url: "assign/role/auth/role/id.json",
        data: {
            roleId: window.roleId
        },
        type: "post",
        dataType: "json",
        async: false

    });

    if (ajaxReturn.status != 200) {
        layer.msg("错误状态码" + ajaxReturn.status + " " + "错误消息" + ajaxReturn.statusText);
        return;
    }
    // 获取到当前角色的权限
    var authIdList = ajaxReturn.responseJSON.data;
    // 循环遍历当前获取到的权限id数组
    for (var i = 0; i < authIdList.length; i++) {
        var key = "id";
        var value = authIdList[i];

        // 获取到要勾选的nodes
        var treeNode = zTreeObj.getNodesByParam(key, value);
        // checked 设置为 true 表示节点勾选
        var checked = true;
        // checkTypeFlag 设置为 false，表示不“联动”，不联动是为了避免把不该勾选的勾 选上
        var checkTypeFlag = false;
        // 执行

        //zTreeObj.checkAllNodes(true);
        zTreeObj.checkNode(treeNode[0], checked, checkTypeFlag,false);
    }


}


function getRoleArray(roleArray) {
    // 开启模态框
    $('#removeModal').modal('show');
    // 清除之前数据
    $("#roleIdDiv").empty();

    //声明一个空的数组来接收
    window.roleId = []
    // 使用foreach方法遍历数组
    roleArray.forEach(function (item, index) {
        // 将id存到全局变量的数组中
        window.roleId.push(item.roleId)
        // 将要删除的名字存入模态框
        $("#roleIdDiv").append(item.roleName + "</br>");
    });
    /*for(var i=0;i<roleArray.length;i++){
        var role=roleArray[i]

    }*/
}

function generatePage() {
    // 1.获取数据
    var pageInfo = getPageInfoRemote();
    // 把之前的选中状态清除
    $("#checkBox").prop("checked", false);
    // 2.填充页面
    fillTableBody(pageInfo)
}

// 获取数据pageInfo
function getPageInfoRemote() {
    var pageInfo = null;
    // 1.使用同步的ajax请求获取数据
    var resultAjax = $.ajax({
        url: "role/get/page.json",
        type: "post",
        data: {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyWord: window.keWord,
        },
        async: false,
        dataType: "json",
    })
    // 2.获取ajax函数的返回值
    var statusCode = resultAjax.status;
    // 3.判断ajxa响应的状态码是否是200
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return;
    }
    // 判断返回的json是否是
    if (resultAjax.responseJSON.result == 'FAILED') {
        layer.msg(resultAjax.responseJSON.message )
        return;
    }
    // 4.请求成功返回pageInfo对象
    pageInfo = resultAjax.responseJSON.data;
    return pageInfo;

}

// 填充toby
function fillTableBody(pageInfo) {
    // 清除上一次ajax请求获取的数据
    $("#rolePage").empty()


    // 这里是没有查询到数据的时候不显示导航条
    $("#Pagination").empty();
    // 1.判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePage").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }
    // 2.使用for循环来构建
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];

        var id = role.id;

        var name = role.name;
        // 构建数据
        var numberTd = "<td>" + id + "</td>"
        var checkboxTd = "<td><input class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + name + "</td>";
        // 构建btn
        var checkBtn = "<button id='" + id + "' type='button' class='btn btn-success btn-xs checkBtn' ><i class='glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='" + id + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button  id='" + id + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePage").append(tr);

        generateNavigator(pageInfo);

    }
}

// 声明分页导航栏
function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;

    var properties = {
        num_edge_entries: 1, // 边缘页数
        num_display_entries: 5, // 主体页数
        callback: pageselectCallback,// 回调函数
        items_per_page: pageInfo.pageSize,//每页显示几项
        current_page: pageInfo.pageNum - 1,// 由于pagination中的页码是从第0页开始算pageInfo是从第1页开始算所以-1
        prev_text: "上一页",
        next_text: "下一页"
    }
    $("#Pagination").pagination(totalRecord, properties);
}

// 回调函数
function pageselectCallback(pageIndex, jquery) {
    window.pageNum = pageIndex + 1;
    // 调用主函数
    generatePage();

    // 取消默认行为
    return false;
}