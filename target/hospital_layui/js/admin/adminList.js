
layui.config({
    base:ctx+'/js/' // 这里加上 ctx   当设定为localhost:8080/+项目名的时候访问时 ctx就是localhost:8080/+项目名
}).use(['form','layer','jquery','laypage','table','laytpl'],function () {
    var form=layui.form,table=layui.table;
    layer=parent.layer===undefined ? layui.layer:parent.layer,
    laypage=layui.laypage;
    $=layui.jquery;
    //表格数据
    table.render({
            id: 'adminList',//设定容器唯一 id。id 是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引，
            elem: '#adminList',//指定原始 table 容器的选择器或 DOM，方法渲染方式必填
            url:ctx+'/admin/getAdminList', //数据接口 默认会自动传递两个参数：?page=1&limit=30 page 代表当前页码、limit 代//表
       // 每页数据量  该参数可通过 添加一个request参数自定义 下面有limit：10 所以发送的是 1 10
            cellMinWidth:80,
           // toolbar:true, //开启表格头部工具栏区域，
        // toolbar: true 仅开启工具栏，开启后 有打印输出功能
        //但是 导出excel功能不完善 所以不开启了
            limit:10, //每页显示的条数
            limits:[10,20,30,40], //每页条数的选择项
            cols:[[ //设置表头 值是一个二维数组
                {field:'id',title:'ID',sort:true,width:80,align:'center'}, //允许排序
                {field:'username',title:'用户名',align:'center'},
                {field:'nickname',title:'昵称',align:'center'},
                {field:'email',title:'邮箱',align:'center'},
                {field:'sex',title:'性别',templet:'#sexTpl', align:'center'},//templet 自定义列模板 #sexTpl是绑定的选择器
                {field:'phone',title:'联系方式',align:'center'},
                {field:'roleName',title:'角色',align:'center'},
                {title:'操作',toolbar:'#barEdit', align:'center'}
            ]],
            page:true //开启分页
        });
        //监听工具条
        table.on('tool(test)',function (obj) { //test 是 table 原始容器的属性 lay-filter="对应的值
            var data=obj.data, adminId=$("#adminId").val();//data获得当前行数据
            if(obj.event==='del')
            {
                if(data.id==adminId)
                {
                    layer.msg("不允许删除自己!",{icon:5});
                    return ;
                }
                layer.confirm('真的删除吗？',function (index) {
                    $.ajax({
                        url:ctx+"/admin/delAdminById/"+data.id, // 发送的是/admin/delAdminById/7
                        type:"get",
                        success:function (d) {
                                if(d.code==0) //删除成功
                                {
                                    table.reload('adminList',{}) //表格重载 这里的表格重载是指对表格重新进行渲染
                                    //数据请求和基础参数 和之前的一样 所以这里采用了{}
                                }
                                else
                                {
                                    layer.msg("权限不足,联系超管！",{icon:5});
                                }
                        }
                    })
                    layer.close(index); //删除上面的确认窗口index是function的参数
                });
            }
            else if(obj.event==='edit')
            {
                if(data.id=='1')
                {
                    layer.msg("不允许编辑此用户！",{icon:5});
                    return;
                }
                if(data.id==adminId)
                {
                    layer.msg("不允许编辑自己！",{icon:5});
                    return;
                }
                layer.open({ //发送请求 controller返回一个jsp页面 加载在这个open的弹出窗口中
                    type:2,
                    title:"编辑角色",
                    area:['380px','560px'],
                    content:ctx+"/admin/editAdmin/"+data.id  // 发送的是/admin/editAdmin/4
                })
            }
            });
        //添加角色 通过选择样式 选择

    //添加角色
    $(".adminAdd_btn").click(function(){
        var index = layer.open({     //这里不能用layui.layer.open 否则不是在最上层 会被挡住一部分
            title : "添加管理员",
            type : 2,
            content : ctx+"/admin/addAdmin",
            area: ['450px', '570px'],
            //maxmin: true  //可以最大化 原来 没有这个 不能显示在最外层 现在去掉也行了 不懂
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
    })
});

