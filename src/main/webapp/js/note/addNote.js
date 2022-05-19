var $;
var $form;
var form;
layui.config({
    base:ctx+'/js/' // 这里加上 ctx   当设定为localhost:8080/+项目名的时候访问时 ctx就是localhost:8080/+项目名
}).use(['form','layer','jquery'],function () {
    var layer=parent.layer===undefined?layui.layer:parent.layer;
    $=layui.jquery;
    form=layui.form;


    form.on("submit(addNote)",function (data) {
        //弹出loading
        var index=top.layer.msg('数据提交中，请稍候',{icon:6,time:false,shade:0.2});
        var msg;
        $.ajax({
            type:"post",
            url:ctx+"/note/insNote",
            data:data.field,  //当前容器的全部表单字段，名值对形式：{name: value}
            dataType:"json",
            success:function (d) {
                if(d.code==0)
                {
                    msg="病历添加成功";
                }
                else
                {
                    msg=d.msg;
                }
            }
        });
        setTimeout(function () {
            // top.layer.msg(msg);

            top.layer.msg("病历添加成功", { shift: -1, time: 2000 },function () {
                parent.location.reload();  //shift: -1表示提示信息不抖动 2秒后调用回调函数
            });
        },2000);  //在点击提交后2秒执行 setTimeout中的function函数
        return false;

    });
});