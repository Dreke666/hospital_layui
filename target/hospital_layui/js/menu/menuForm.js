var $;
var $form;
var form;
layui.config({
    base : ctx+'/js/' // 这里加上 ctx   当设定为localhost:8080/+项目名的时候访问时 ctx就是localhost:8080/+项目名
    // 必须是 /js/ 两个/ / 都有 否则 js下的iconPicker加载不成功
}).use(['form','layer','jquery','laydate','iconPicker'],function(){
    var layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,laydate = layui.laydate,iconPicker = layui.iconPicker;
    $ = layui.jquery;
    form = layui.form;

    iconPicker.render({
        elem: '#iconPicker',
        type: 'fontClass',
        // 是否开启搜索：true/false
        search: true,
        // 是否开启分页
        page: true,
        // 每页显示数量，默认12
        limit: 12,
        // 点击回调
        click: function (data) {
            console.log(data);
        }
    });

    iconPicker.checkIcon('iconPicker', 'layui-icon-rate-solid');

    $("#sorting").blur(function () {

        var menuLevel=parseInt($("#menuLevel").val()); //parseInt将内容转换成数字
        var sort=parseInt($("#sorting").val());


        switch (menuLevel) {
            case 1:
                if(sort>99)
                {
                    layer.confirm('一级菜单排序1-99',function (index) {
                            layer.close(index);
                            $("#sorting").val('');
                        },
                        function (index) {
                            layer.close(index);
                            $("#sorting").val('');
                        });

                }
                break;
            case 2:
                if(sort>999||sort<111)
                {
                    layer.confirm('二级菜单排序111-999',function (index) {
                            layer.close(index);
                            $("#sorting").val('');
                        },
                        function (index) {
                            layer.close(index);
                            $("#sorting").val('');
                        });
                }
                break;
            case 3:
                    if(sort>9999||sort<1111 )
                    {
                        layer.confirm('三级菜单排序1111-9999',function (index) {
                            layer.close(index);
                            $("#sorting").val('');
                        },
                            function (index) {
                                layer.close(index);
                                $("#sorting").val('');
                            });
                    }
                    break;
            default:break;
        }

    });

    form.on("submit(menuForm)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var msg="发生错误！",flag=false;
        $.ajax({
            type: "post",
            url: ctx+"/admin/menuForm",
            data:data.field,
            dataType:"json",
            async:false,
            success:function(d){
                msg=d.msg;
            },
            error:function() {
                flag=true;
                top.layer.close(index);
                $("#menuF")[0].reset();
                layer.msg("发生错误，请检查输入！"); }
        });
        setTimeout(function(){
            if(!flag){
                top.layer.close(index);
                top.layer.msg(msg);
                layer.closeAll("iframe");

                setTimeout(function () {
                    //刷新父页面
                    top.location.reload();
                },1000);

            }
        },2000);
        return false;
    })
})