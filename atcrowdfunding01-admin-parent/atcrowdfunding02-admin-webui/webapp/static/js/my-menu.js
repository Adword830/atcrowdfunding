function generateTree() {
    // 1.发送ajax请求查询数据
    $.ajax({
        url:"menu/get/whole/tree.json",
        type:"post",
        dataType:"json",
        success:function(response){
            var result = response.result;
            if(result=="SUCCESS"){
                var setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom,
                    },
                    data: {
                        key: {
                            url: "zpj"
                        }
                    }
                };
                var zNodes=response.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if(result=="FAILED"){
                layer.msg(response.message);
            }

        },
        error:function (response) {
            layer.msg("错误代码："+response.status+"错误消息："+response.statusText)
        }
    })
}

function myAddDiyDom(treeId, treeNode) {
   // 构建出每一个节点的id
    var demoId=treeNode.tId+"_ico";

    $("#"+demoId).removeClass().addClass(treeNode.icon);

}

function myAddHoverDom(treeId, treeNode) {
    // 为生成的span标签构建一个id
    var btn=treeNode.tId+"_btnGroup";

    //构建增加的btn
    var addBtn='<a id="'+treeNode.id+'" class="btn btn-info dropdown-toggle btn-xs addBtn" style="margin-left:10px;padding-top:0px;" href="#" title="添加权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    var removeBtn='<a id="'+treeNode.id+'" class="btn btn-info dropdown-toggle btn-xs removeBtn" style="margin-left:10px;padding-top:0px;" href="#" title="删除权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    var editBtn='<a id="'+treeNode.id+'" class="btn btn-info dropdown-toggle btn-xs editBtn" style="margin-left:10px;padding-top:0px;" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    // 储存要添加的按钮
    var btnHtml="";
    if(treeNode.level==0){
        btnHtml=addBtn;
    }
    if(treeNode.level==1){
        btnHtml=editBtn+"  "+removeBtn+"  "+addBtn;
        // 当前节点存在子节点
        if(treeNode.children.length>0){
            btnHtml=editBtn+"  "+addBtn;
        }
    }
    if(treeNode.level==2){
        btnHtml=editBtn+"  "+removeBtn+"  "+addBtn;
    }
    // 判断是否有多个标签生成
    if($("#"+btn).length>0){
        return;
    }
    // 找到那个要生成的a标签
    var anchor=treeNode.tId+"_a";
    // 生成对应的标签
    $("#"+anchor).after("<span id='"+btn+"'>"+btnHtml+"</span>")




}

function myRemoveHoverDom(treeId, treeNode){
    // 找到要删除的标签
    var btn=treeNode.tId+"_btnGroup";
    // 进行删除
    $("#"+btn).remove();

}