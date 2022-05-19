
layui.use([ 'form','layer','upload','jquery','table','laydate'], function() {
    var layer = layui.layer, $ = layui.jquery, form = layui.form, table = layui.table, laydate = layui.laydate;
    var upload=layui.upload;

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        ,url:ctx+ "/user/image"
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code >0)
            {
                return layer.msg('上传失败');
            }
            //上传成功
            else
            {
                $("#photo_path").val(res.data.src); //提交时注册的时候用 写入数据库
                // layer.msg('上传成功'+photo_path);
                layer.msg('上传成功');
            }
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });




    form.on("submit(updateDoctor)",function (data) {
        var index=top.layer.msg("数据提交中，请稍候",{icon:16,time:false,shade:0.2});
        var msg;
        $.ajax({
            type:"post",
            async:false,
            url:ctx+"/doctor/updateDoctor",
            data:data.field,  //
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
                // layer.closeAll();
            }
        });
        setTimeout(function () {
            top.layer.close(index);
            top.layer.msg(msg);
            layer.closeAll("iframe");
            setTimeout(function () {  //添加一个 延时 否则接着刷新 看不到提示的信息
                //刷新父页面
                parent.location.reload();
            },1000);
        },2000);
        return false;

    });


});








