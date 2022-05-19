
var $;
var $form;
var form;
layui.config({
    base:ctx+'/js/' // 这里加上 ctx   当设定为localhost:8080/+项目名的时候访问时 ctx就是localhost:8080/+项目名
}).use(['form','layer','laydate','jquery','upload'],function () {
    var layer=parent.layer===undefined?layui.layer:parent.layer;
    var laydate=layui.laydate;
    $=layui.jquery;

    form=layui.form;


    laydate.render({
        elem:'#order_time',
        trigger: 'focus',
        theme: '#23b7e5',
        type: 'datetime'
    });



    form.on("submit(addDate)",function (data) {
        var index=top.layer.msg('数据提交中，请稍候。',{icon:16,time:false,shade:0.8});
        var msg;

        $.ajax({
            type: "post",
            url:ctx+"/reservation/addRes",
            data:data.field,
            dataType:"json",
            success:function (d) {
                if(d.code==0)
                {
                    msg="预约成功!";
                }
                else
                {
                    msg=d.msg;
                }
            },
            error:function (jqXHR, textStatus, errorThrown) {
                layer.alert("获取数据失败! 先检查sql 及 Tomcat Localhost Log 的输出");
            }


        });

        setTimeout(function () {
            top.layer.msg(msg);
            top.layer.close(index);
            layer.closeAll("iframe");
            setTimeout(function () {
                //刷新父页面
                top.location.reload();
            },1000);
        },2000);  //在点击提交后2秒执行 setTimeout中的function函数

        return false;
    })
});


