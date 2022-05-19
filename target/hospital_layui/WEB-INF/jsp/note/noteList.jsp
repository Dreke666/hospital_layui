<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx }/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${ctx }/css/font_eolqem241z66flxr.css"
          media="all" />
    <link rel="stylesheet" href="${ctx }/css/list.css" media="all" />
    <link rel="stylesheet" href="${ctx }/css/main.css" media="all" />

    <script>
        var ctx = "${ctx}";
    </script>
    <style type="text/css">
        .layui-table-cell {
            font-size:14px;
            padding:0 5px;
            height: 100%;
            max-width: 100%;
            overflow:visible;
            text-overflow:inherit;
            white-space:normal;
            word-break: break-all;
        }
    </style>

</head>
<body class="childrenBody">


<table class="layui-table layui-table-cell" id="noteList" lay-filter="noteList"></table>  <!--加载管理员列表数据-->

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-sm" lay-event="edit">
        <i class="layui-icon">&#xe642;</i>
    </a>
    <a class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">
        <i class="layui-icon">&#xe640;</i>
    </a>
</script>


<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/note/noteList.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>



</body>
</html>