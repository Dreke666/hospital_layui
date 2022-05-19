<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx }/css/zTreeStyle/zTreeStyle.css"  media="all" />
    <script type="text/javascript" src="${ctx }/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.ztree.all.js"></script>
    <script>
        var ctx = "${ctx}";
    </script>
    <style type="text/css">
        .layui-form-item .layui-inline{ width:33.333%; float:left; margin-right:0; }
        @media(max-width:1240px){
            .layui-form-item .layui-inline{ width:100%; float:none; }
        }
    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width: 80%;" id="arf">
    <!--提交请求时 携带的参数 猜测 m是代表这个角色有哪些权限 （菜单） -->
    <input type="hidden" id="depart_id" name="depart_id" value="${depart.depart_id}" />
    <div class="layui-form-item">
        <label class="layui-form-label">科室名称</label>
        <div class="layui-input-block">
            <input type="text" id="depart_name" class="layui-input userName" lay-verify="required"
                   placeholder="请输入类别名称" name="depart_name" value="${depart.depart_name}">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="editDepart">立即提交</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/depart/editDepart.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>
</body>
</html>