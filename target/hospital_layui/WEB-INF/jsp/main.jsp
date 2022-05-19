<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx }/css/style.css">
    <link rel="stylesheet" href="${ctx }/layui/css/layui.css" media="all">
    <script>
        var ctx = "${ctx}";
    </script>

    <style type="text/css">
        .lb1 {
            position: absolute;
            width: 100%;
            height: 100%;
        }
        #test1{
            margin-top: 3%;
            align-content: center;
        }
        .b{
            margin-top: 44%;
        }
        #title{
            font-family: KaiTi;
        }
    </style>
</head>


<br>
<br>
<h1 align="center" id="title">欢迎登录在线预约挂号系统</h1>

<body onload="showtime()" background="">

    <div class="layui-carousel" id="test1">
        <div carousel-item>
            <div><img src="${ctx}/image/banner01.jpg" class="lb1"></div>
            <div><img src="${ctx}/image/banner02.jpg" class="lb1"></div>
            <div><img src="${ctx}/image/banner03.jpg" class="lb1"></div>
            <div><img src="${ctx}/image/banner04.jpg" class="lb1"></div>

        </div>
    </div>

    <script src="${ctx }/layui/layui.js"></script>
    <script>
        layui.use('carousel', function(){
            var carousel = layui.carousel;
            //建造实例
            carousel.render({
                elem: '#test1'
                ,width: '98%' //设置容器宽度
                ,height:'450px'
                ,arrow: 'always'//始终显示箭头
                //,anim: 'updown' //切换动画方式
            });
        });
    </script>
    <div class="b">
        <div id="clock" style="color:powderblue"></div>
    </div>


    <script language=JavaScript>
        function showtime() {
            var today;
            var hour;
            var second;
            var minute;
            var year;
            var month;
            var date;
            var strDate;
            today = new Date();
            var n_day = today.getDay();
            switch (n_day) {
                case 0: {
                    strDate = "星期日"
                }
                    break;
                case 1: {
                    strDate = "星期一"
                }
                    break;
                case 2: {
                    strDate = "星期二"
                }
                    break;
                case 3: {
                    strDate = "星期三"
                }
                    break;
                case 4: {
                    strDate = "星期四"
                }
                    break;
                case 5: {
                    strDate = "星期五"
                }
                    break;
                case 6: {
                    strDate = "星期六"
                }
                    break;
                case 7: {
                    strDate = "星期日"
                }
                    break;
            }
            year = today.getYear() - 100 + 2000;
            month = today.getMonth() + 1;
            date = today.getDate();
            hour = today.getHours();
            minute = today.getMinutes();
            second = today.getSeconds();
            if (month < 10)
                month = "0" + month;
            if (date < 10)
                date = "0" + date;
            if (hour < 10)
                hour = "0" + hour;
            if (minute < 10)
                minute = "0" + minute;
            if (second < 10)
                second = "0" + second;
            document.getElementById('clock').innerHTML = year + "年" + month
                + "月" + date + "日 " + strDate + " " + hour + ":" + minute
                + ":" + second;
            setTimeout("showtime();", 1000); //1秒调用一次showTime()函数
        }
    </script>

</body>
</html>