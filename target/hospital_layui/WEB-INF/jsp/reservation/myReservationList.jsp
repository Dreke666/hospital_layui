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
    <table class="layui-table layui-table-cell"  id="myReservationList" lay-filter="myReservationList"></table>
</div>



<script type="text/html" id="rankTpl">
    {{#  if(d.doctor.rank==0){                                 }}
    <span style="color: #0000FF">行政主任</span>
    {{#   }  else if(d.doctor.rank==1){                        }}
    <span style="color: #FF5722">主任医师</span>
    {{#   }  else if(d.doctor.rank==2){                        }}
    <span style="color: #FF5722">副主任医师</span>
    {{#   }  else if(d.doctor.rank==3){                        }}
    <span style="color: #FF5722">主治医师</span>
    {{#   }  else if(d.doctor.rank==4){                        }}
    <span style="color: #FF5722">副主治医师</span>
    {{#   }else {                                      }}
    <span style="color: #1AA094">实习</span>
    {{#      }                                          }}
</script>



<script type="text/html" id="barDemo">

    <a class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">
        <i class="layui-icon">&#xe640;</i>
    </a>
</script>

<script type="text/html" id="imgtmp">
    <img src="../../hospital_img/{{d.doctor.photo_path}}" />
</script>

<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
<script type="text/javascript" src="${ctx }/js/reservation/myReservationList.js"></script>
<script type="text/javascript" src="${ctx }/js/pubilc.js"></script>

</body>




</html>