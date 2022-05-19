
var $;
layui.config({
    base:"/js/"
}).use(['form','layer','jquery'],function () {
    var form=layui.form,
        layer=parent.layer===undefined?layui.layer:parent.layer,
        laypage=layui.layPage;
    $=layui.jquery;
    form.on("submit(addPromotion)",function (data) {
        //弹出loading
        var index=top.layer.msg('数据提交中,请稍候',{icon:16,time:false,shade:0.8});
        setTimeout(function () {
            $.ajax({
                type: "POST",
                async:false,
                url:ctx+"/promotion/insPromotion",
                data:$("#arf").serialize()

            });
            top.layer.close(index);
            top.layer.msg("医院公告添加成功", { shift: -1, time: 2000 },function () {
                parent.location.reload();  //shift: -1表示提示信息不抖动 2秒后调用回调函数
            });

        },2000);
        return false;
    })
});















