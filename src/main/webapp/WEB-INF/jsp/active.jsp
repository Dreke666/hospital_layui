<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>信息提示页面</title>
    <link rel="stylesheet" href="${ctx }/layui/css/layui.css" media="all" />
    <script>
        var ctx = "${ctx}";
    </script>
</head>

<body style="text-align: center;">

<div>
    <blockquote class="layui-elem-quote layui-quote-nm">${msg }</blockquote>
</div>

</body>
</html>
