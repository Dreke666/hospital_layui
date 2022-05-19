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

    <div class="layui-form-item">
        <label class="layui-form-label">活动标题</label>
        <div class="layui-input-block">
            <input type="text" id="title" class="layui-input userName" lay-verify="required"
                   placeholder="请输入活动标题" name="title" value="">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">活动内容</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入活动内容" class="layui-textarea linksDesc" lay-verify="required"
                      name="content"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addPromotion">立即提交</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/promotion/addPromotion.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>
</body>
</html>