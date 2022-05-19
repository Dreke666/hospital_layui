<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx }/css/list.css" media="all" />
    <link rel="stylesheet" href="${ctx }/css/font_eolqem241z66flxr.css"
          media="all" />
    <link rel="stylesheet" href="${ctx }/css/main.css" media="all" />
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
<body class="childrenBody"> <!--这个保证内容不会铺满父窗口但是必须要加入main.css-->

<blockquote class="layui-elem-quote news_search">

</blockquote>


<div class="layui-form">
    <table class="layui-table layui-table-cell"  id="doctorResDoneList" lay-filter="doctorResDoneList"></table>
</div>



<script type="text/html" id="levelId">
    {{#  if(d.user.levelId==1){                                 }}
    <span style="color: #0000FF">普通</span>
    {{#   }  else if(d.user.levelId==2){                        }}
    <span style="color: #FF5722">VIP</span>
    {{#   }else {                                      }}
    <span style="color: #1AA094">保密</span>
    {{#      }                                          }}
</script>

<script type="text/html" id="sexTpl">
    {{#  if(d.user.gender==1){                                 }}
    <span style="color: #0000FF">男</span>
    {{#   }  else if(d.user.gender==0){                        }}
    <span style="color: #FF5722">女</span>
    {{#   }else {                                      }}
    <span style="color: #1AA094">保密</span>
    {{#      }                                          }}
</script>

<script type="text/html" id="barDemo">

    <a class="layui-btn layui-btn-sm layui-btn-danger" lay-event="note">
        <i class="layui-icon">&#xe642;</i>
    </a>
</script>

<script type="text/html" id="imgtmp">
    <img src="../../hospital_img/{{d.user.photo_path}}" />
</script>

<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/reservation/doctorResDoneList.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>

</body>




</html>