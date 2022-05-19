<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" href="${ctx }/layui/css/layui.css" media="all" />
    <script>
        <%-- js全局变量  js文件中都可以直接应用这个变量--%>
        var ctx="${ctx}";
    </script>
    <script type="text/css">
        .layui-form-item .layui-inline {
            width: 33.333%;
            float: left;
            margin-right: 0;
        }

        @media ( max-width :1240px) {
            .layui-form-item .layui-inline {
                width: 100%;
                float: none;
            }
        }
    </script>
</head>
<body class="childrenBody">
<form class="layui-form" style="width: 80%;" id="aaf">

    <input type="hidden" id="photo_path"  name="photo_path" value=""/>
    <div class="layui-form-item">
        <label class="layui-form-label">用户类型</label>
        <div class="layui-input-block">
            <select name="level" id="level"  lay-verify="required">
                <option value="2">患者</option>
                <option value="3">医生</option>
            </select>
        </div>
    </div>


    <div class="layui-upload">
        <label for="username" class="layui-form-label">
            <span class="x-red">*</span>头像</label>
        <div class="layui-input-inline">
            <button type="button" class="layui-btn" id="test1">上传头像</button>
            <div class="layui-upload-list">
                <img class="layui-upload-img" id="demo1" width="100px" height="100px">
                <p id="demoText"></p>
            </div>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">出生日期</label>
        <div class="layui-input-inline">
            <input type="text" id="birthday" name="birthday" class="layui-input"
                   lay-verify="required" placeholder="请输入出生日期">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" id="username" class="layui-input userName"
                   lay-verify="required" placeholder="请输入用户名" name="username" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">真实姓名</label>
        <div class="layui-input-block">
            <input type="text" id="realname" class="layui-input userName"
                   lay-verify="required" placeholder="请输入真实姓名" name="realname" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">家庭住址</label>
        <div class="layui-input-block">
            <input type="text" id="address" class="layui-input userName"
                   lay-verify="required" placeholder="请输入家庭住址" name="address" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="password" id="password" class="layui-input userName"
                   lay-verify="pass" placeholder="请输入密码" name="password" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-block">
            <input type="password"  class="layui-input userName"
                   lay-verify="repass" placeholder="请再次输入密码"  value="">
        </div>
    </div>
    <!--上面的lay-verify 值 pass repass在layui.all中都有定义 可以看看具体验证的格式 如几位密码 之类-->



    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <input type="radio" name="gender" value="1" title="男" checked>
            <input type="radio" name="gender" value="0" title="女">
            <input type="radio" name="gender" value="2" title="保密">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-block">
            <input type="text" id="phone" class="layui-input userName"
                   lay-verify="phone" placeholder="请输入手机号" name="phone" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block">
            <input type="text" id="email" class="layui-input userName"
                   lay-verify="email" placeholder="请输入邮箱" name="email" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addNewUser">立即提交</button>
        </div>
    </div>
</form>

<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/register/register.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>
</body>
</html>