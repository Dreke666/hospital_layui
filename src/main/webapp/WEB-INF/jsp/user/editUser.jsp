<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css">
    <script>
        var ctx = "${ctx}";
    </script>
</head>
<body>
<form class="layui-form" style="width: 80%;">

    <input type="hidden" name="user_id" value="${hospital_user.user_id}" id="user_id"/>
    <input type="hidden" id="photo_path"  name="photo_path" value=""/>

    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" id="username" class="layui-input"
                   name="username" value="${hospital_user.username}">
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
        <label class="layui-form-label">真实姓名</label>
        <div class="layui-input-block">
            <input type="text" id="realname" class="layui-input userName"
                   lay-verify="required" placeholder="请输入真实姓名" name="realname"
                   value="${hospital_user.realname}">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block">
            <input type="text" name="email" id="email" id="email" class="layui-input"
                   lay-verify="email" placeholder="请输入邮箱" value="${hospital_user.email}">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">级别</label>
        <div class="layui-input-block">
            <c:if test="${hospital_user.levelId==1}">
                <input type="radio" name="levelId" value="1" title="普通" checked>
                <input type="radio" name="levelId" value="2" title="VIP">
            </c:if>
            <c:if test="${hospital_user.levelId==2}">
                <input type="radio" name="levelId" value="1" title="普通" >
                <input type="radio" name="levelId" value="2" title="VIP" checked>
            </c:if>
        </div>
    </div>



    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <c:if test="${hospital_user.gender==0}">
                <input type="radio" name="gender" value="1" title="男">
                <input type="radio" name="gender" value="0" title="女" checked>
                <input type="radio" name="gender" value="2" title="保密">
            </c:if>
            <c:if test="${hospital_user.gender==1}">
                <input type="radio" name="gender" value="1" title="男" checked>
                <input type="radio" name="gender" value="0" title="女" >
                <input type="radio" name="gender" value="2" title="保密">
            </c:if>
            <c:if test="${hospital_user.gender==2}">
                <input type="radio" name="gender" value="1" title="男" >
                <input type="radio" name="gender" value="0" title="女" >
                <input type="radio" name="gender" value="2" title="保密" checked>
            </c:if>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-block">
            <input type="text" name="phone" class="layui-input"
                   lay-verify="phone" placeholder="请输入手机号" value="${hospital_user.phone}">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="updateUser">立即保存</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/user/editUser.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>
</body>
</html>















