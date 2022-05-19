<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--这个就是运行后第一次打开的页面 用作 登录界面 放在了WEB-INF外面-->
<%@ include file="/WEB-INF/page/include/taglib.jsp" %>
<%--/WEB-INF/page/include/taglib.jsp--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all">

    <link rel="stylesheet" href="${ctx}/css/style.css">

    <script>
        var ctx = "${ctx}";  //因为下面引入的login.js中会用到ctx变量
    </script>

    <title>登录</title>

   <style type="text/css">

     #bg_img {
         position: absolute;
         width: 100%;
         height: 100%;
     }

     #promotion{
         float: left;
         position: absolute;
         left: 25%;
         top: 27%;
         background: url(${ctx}/image/1.png);
         font-family: KaiTi;
         font-size: 450%;
         align-content: center;
     }
   </style>
</head>

<body>
    <img src="image/back_061.jpg" id="bg_img">

    <a href="/promotion/promotionList" id="promotion">点击查看医院公告</a>

    <div class="login-main">

        <header class="layui-elip">登 录</header>

        <form class="layui-form" lay-filter="login">

            <div class="layui-input-inline">
                <select name="level" id="level"  lay-verify="required">
                    <option value="0" >请选择用户类型</option>
                    <option value="1">管理员</option>
                    <option value="2">患者</option>
                    <option value="3">医生</option>
                </select>
            </div>


        <div class="layui-input-inline">
            <input id="username" type="text" placeholder="用户名" class="layui-input">
        </div>
        <div class="layui-input-inline">
            <input id="password" type="password" placeholder="密码" class="layui-input">
        </div>
        <div class="layui-input-inline" style="display: flex;">
            <input type="text" id="code" placeholder="验证码" class="layui-input">
            <canvas id="canvas" width="100%" height="35"></canvas>
        </div>
        <div class="layui-btn layui-btn-radius" style="margin-left: 5px"><%--layui-input-inline--%>
            <button id="loginBt" class="layui-btn">登录</button>

        </div>
        <button type="button"  id="registerBtn" class="layui-btn layui-btn-radius layui-btn-warm" style="margin-left: 60px;width: 35%;" >注册</button>
        </form>

    </div>

    <script type="text/javascript" src="${ctx }/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx }/js/login.js"></script>
    <script type="text/javascript" src="${ctx }/js/pubilc.js"></script>
</body>

</html>






