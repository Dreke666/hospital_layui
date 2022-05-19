




var $;
layui.config({
    base:"/js/"
}).use(['form','layer','jquery'],function () {
    var form=layui.form,
        layer=parent.layer===undefined?layui.layer:parent.layer,
        laypage=layui.layPage;
    $=layui.jquery;
    form.on("submit(editDepart)",function (data) {
        //弹出loading
        var index=top.layer.msg('数据提交中,请稍候',{icon:16,time:false,shade:0.8});
        setTimeout(function () {
            $.ajax({
                type: "POST",
                async:false,
                url:ctx+"/depart/updateDepart",
                data:$("#arf").serialize()
            });
            top.layer.close(index);
            top.layer.msg("科室修改成功", { shift: -1, time: 2000 },function () {
                parent.location.reload();  //shift: -1表示提示信息不抖动 2秒后调用回调函数
            });

        },2000);

        return false;
    })
    //角色名称唯一性校验
    $("#depart_name").blur(function () {  //失去焦点 调用函数
        //layer.msg($("#roleName").val());
        $.ajax({
            type:"get",
            url:ctx+"/depart/checkDepartName/"+$("#depart_name").val(),

            success:function (data) {
                if(data.code!=0)
                {
                    top.layer.msg(data.msg);
                    $("#depart_name").val("");
                    $("#depart_name").focus();
                }
            }
        });
    });
});















