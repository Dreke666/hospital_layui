<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx }/css/list.css" media="all"/>
    <link rel="stylesheet" href="${ctx }/css/font_eolqem241z66flxr.css"
          media="all"/>
    <link rel="stylesheet" href="${ctx }/css/main.css" media="all"/>
    <script>
        var ctx = "${ctx}";
    </script>

    <style>
        .layui-table-cell {
            height: 100%;
            max-width: 100%;
        }
    </style>


</head>
<body class="childrenBody">

<blockquote class="layui-elem-quote news_search">
    <form class="layui-form">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" id="username" value="" placeholder="请输入用户用户名"
                       class="layui-input search_input " style="margin-left: 10px">
            </div>
            <div class="layui-input-inline">
                <input type="text" id="realname" value="" placeholder="请输入用户真实姓名"
                       class="layui-input search_input " style="margin-left: 10px">
            </div>
            <div class="layui-input-inline" style="margin-left: 10px">
                <select name="gender" class="" id="gender">
                    <option value="-1">请选择性别</option>
                    <option value="1">男</option>
                    <option value="0">女</option>
                    <option value="2">保密</option>
                </select>
            </div>

            <div class="layui-input-inline" style="margin-left: 10px">
                <select name="levelId" class="" id="levelId">
                    <option value="-1">请选择用户级别</option>
                    <option value="1">普通</option>
                    <option value="2">VIP</option>
                </select>
            </div>

            <div style="margin-left: 10px">
                <div class="layui-inline">
                    <input type="text" id="create_time_s" class="layui-input"
                           name="create_time_s" placeholder="注册时间(开始)" value="">
                </div>
                -
                <div class="layui-inline">
                    <input type="text" id="create_time_e" class="layui-input"
                           name="create_time_e" placeholder="注册时间(结束)" value="">
                </div>
                <a style="margin-left: 10px" class="layui-btn search_btn" lay-submit="" data-type="search"
                   lay-filter="search">查询</a>
            </div>
        </div>
    </form>
</blockquote>


<div class="layui-form">
    <table class="layui-table layui-table-cell" id="userList" lay-filter="userList"></table>
</div>
<script type="text/html" id="sexTpl">
    {{#  if(d.gender==1){                                 }}
    <span style="color: #0000FF">男</span>
    {{#   }  else if(d.gender==0){                        }}
    <span style="color: #FF5722">女</span>
    {{#   }else {                                      }}
    <span style="color: #1AA094">保密</span>
    {{#      }                                          }}
</script>


<script type="text/html" id="levelIdTpl">
    {{#  if(d.levelId==1){                                 }}
    <span style="color: #0000FF">普通</span>
    {{#   }  else if(d.levelId==2){                        }}
    <span style="color: #FF5722">VIP</span>
    {{#   }else {                                      }}
    <span style="color: #1AA094">保密</span>
    {{#      }                                          }}
</script>

<script type="text/html" id="statusTpl">
    <input type="checkbox" name="status" value="{{d.user_id}}"
           lay-skin="switch" lay-text="正常|禁用"
           lay-filter="statusSwitch" {{ d.status== 1 ? "checked" : '' }}>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-sm" lay-event="edit">
        <i class="layui-icon">&#xe642;</i>
    </a>
    <a class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">
        <i class="layui-icon">&#xe640;</i>
    </a>
</script>

<script type="text/html" id="imgtmp">
    <img src="../../hospital_img/{{d.photo_path}}"/>
</script>

<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/admin/userList.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>

</body>


</html>