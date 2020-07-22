<%--
  Created by IntelliJ IDEA.
  User: hopu
  Date: 2020/6/8
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--http://localhost:8080/atcrowdfunding02_admin_webui_war/test.html-->
<html>
  <head>
    <title>测试</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script  src="static/jquery/jquery-3.1.0.min.js"></script>
      <script src="static/layer/layer.js"></script>
    <script>
      $(function(){
        var student={
            name:"zpj",
            age:"19",
            sex:"男",
            address:{
              province:"湖南省",
              city:"常德市",
              county:"桃源县",
            },
          subjectList:[
              {
                name:"javaSE",
                score:"100",
                map:{"k1":"v1"},
              },
              {
                name:"ssm",
                score:"99",
                map:{"k2":"v2"},
              }
            ]
        }
        var responseBody = JSON.stringify(student);
        $("#ajax_test").click(function () {
          $.ajax({
            "url":"test/ajax.json",
            "data":responseBody,
            "contentType":"application/json;charset=UTF-8",
            "type":"post",
            "dataType":"JSON",
            "success":function(response){
              alert(response)
            },
            "error":function (response) {
              alert(response)
            }
          })
        })
        $("#btn").click(function () {
            layer.msg("这是layer的弹框")
        })
      })
    </script>
  </head>
  <body>
      <a  href="test.html">SSM环境测试</a>

      <button id="ajax_test">1 5 6</button>

      <a href="admin/do/login/page.html">登录页面</a>

      <button id="btn">弹框</button>
  </body>
</html>
