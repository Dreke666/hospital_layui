
layui.use([ 'form','layer','upload','jquery','table','laydate'], function() {
    var layer = layui.layer, $ = layui.jquery, form = layui.form, table = layui.table, laydate = layui.laydate;
    var upload=layui.upload;

    form.on("submit(updateDoctor)",function (data) {
        var index=top.layer.msg("数据提交中，请稍候",{icon:16,time:false,shade:0.2});
        // var index1=parent.layer.getFrameIndex(window.name); //这是获得的 学生列表 这个窗口？
        var msg;
        // var flag=false;
        $.ajax({
            type:"post",
            async:false,
            url:ctx+"/doctor/updateDoctor",
            data:data.field,
            dataType:"json",
            success:function (d) {
                if(d.code==0)
                {
                    msg="用户更新成功";
                    // flag=true;
                }
                else
                {
                    msg=d.msg;
                }
            } ,
            error:function () {
                layer.msg("错误，请检查sql及输出",{icon:2});
            }
        });
        setTimeout(function () {

            top.layer.msg("用户更新成功", { shift: -1, time: 2000 },function () {
                parent.location.reload();  //shift: -1表示提示信息不抖动 2秒后调用回调函数
            });
        },2000);  //在点击提交后2秒执行 setTimeout中的function函数
        return false;
    });
});






