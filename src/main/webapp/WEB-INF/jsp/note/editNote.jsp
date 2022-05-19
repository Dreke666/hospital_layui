<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>编辑管理员</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx }/layui/css/layui.css" media="all" />
    <script>
        var ctx="${ctx}";
    </script>
    <!-- @meida 表示如果文档宽度小于 1240 像素 那么.layui-form-item .layui-inline的宽度等样式改变-->
    <style type="text/css">
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
    </style>
</head>
<body class="childrenBody">
<form class="layui-form" style="width: 80%;">

    <input type="hidden" id="note_id" name="note_id" value="${note.note_id}" />

    <div class="layui-form-item">
        <label class="layui-form-label ">患者姓名</label>
        <div class="layui-input-block">
            <input type="text" id="realname" class="layui-input"
                   name="realname" value="${note.realname}">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
                <input type="text" name="gender" class="layui-input"
                       value="${note.gender}">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">创建日期</label>
        <div class="layui-input-block">
            <input type="text" id="create_time" class="layui-input" autocomplete="off"
                   name="create_time" lay-verify="datetime" placeholder="yyyy-MM-dd"
                   value="<fmt:formatDate value="${note.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
            >
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label ">诊断结论</label>
        <div class="layui-input-block">
            <input type="text" id="note_title" class="layui-input"
                   name="note_title" lay-verify="required"
                   placeholder="请输入诊断结论"  value="${note.note_title}">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">诊断详情</label>
        <div class="layui-input-block">
            <textarea placeholder="请填写详细诊断内容" class="layui-textarea linksDesc" lay-verify="required"
                      name="note_content" >${note.note_content}</textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="updateNote">立即保存</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/note/editNote.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>
</body>
</html>