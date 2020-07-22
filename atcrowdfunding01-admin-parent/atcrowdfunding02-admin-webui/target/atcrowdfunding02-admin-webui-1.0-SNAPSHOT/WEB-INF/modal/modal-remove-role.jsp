<%--
  Created by IntelliJ IDEA.
  User: hopu
  Date: 2020/6/24
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: hopu
  Date: 2020/6/23
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="removeModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网消息提示框</h4>
            </div>
            <div class="modal-body">
                <label>确认删除以下的信息：</label>
                <div id="roleIdDiv" style="text-align: center"></div>
            </div>
            <div class="modal-footer">
                <button id="removeBtn" type="button" class="btn btn-success">确认删除</button>
            </div>
        </div>
    </div>
</div>

