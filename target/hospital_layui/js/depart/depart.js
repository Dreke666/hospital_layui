

layui.config({
    base:"/js/"
}).use(['form','layer','jquery','laypage','table','laytpl'],function () {
    var form=layui.form,table=layui.table;
    layer=parent.layer===undefined ? layui.layer:parent.layer,
        laypage=layui.laypage;
    $=layui.jquery;

    //表格数据
    table.render({
        id: 'departList',//设定容器唯一 id。id 是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引，
        elem: '#departList',//指定原始 table 容器的选择器或 DOM，方法渲染方式必填
        url:ctx+'/depart/getDepartList', //数据接口 默认会自动传递两个参数：?page=1&limit=30 page 代表当前页码、limit 代//表
        // 每页数据量  该参数可通过 添加一个request参数自定义 下面有limit：10 所以发送的是 1 10
        cellMinWidth:80,
        // toolbar:true, //开启表格头部工具栏区域，
        // toolbar: true 仅开启工具栏，开启后 有打印输出功能
        //但是 导出excel功能不完善 所以不开启了
        limit:10, //每页显示的条数
        limits:[10,20,30,40], //每页条数的选择项
        cols:[[ //设置表头 值是一个二维数组
            {field:'depart_id',title:'科室类型ID',sort:true,width:80,align:'center'}, //允许排序
            {field:'depart_name',title:'类型名称',align:'center'},
            {title:'操作',toolbar:'#barEdit', align:'center'}
        ]],
        page:true //开启分页
    });

    //监听工具条
    table.on('tool(test)',function (obj) { //test 是 table 原始容器的属性 lay-filter="对应的值
        var data=obj.data ;//data获得当前行数据
        if(obj.event==='del')
        {
            layer.confirm('真的删除吗？',function (index) {
                $.ajax({
                    url:ctx+"/depart/delDepartById/"+data.depart_id, // 发送的是/admin/delAdminById/7
                    type:"get",
                    success:function (d) {
                        if(d.code==0) //删除成功
                        {
                            layer.msg(d.msg,{icon:5});
                            table.reload('departList',{}) //表格重载 这里的表格重载是指对表格重新进行渲染
                            //数据请求和基础参数 和之前的一样 所以这里采用了{}
                        }
                        else
                        {
                            layer.msg(d.msg,{icon:5});
                        }
                    }
                })
                layer.close(index); //删除上面的确认窗口index是function的参数
            });
        }
        else if(obj.event==='edit')
        {
            layer.open({ //发送请求 controller返回一个jsp页面 加载在这个open的弹出窗口中
                type:2,
                title:"编辑科室信息",
                area:['350px','300px'],
                content:ctx+"/depart/editDepart/"+data.depart_id  // 发送的是/admin/editAdmin/4
            })
        }
    });
    //添加角色 通过选择样式 选择

    //添加科室
    $(".departAdd_btn").click(function(){
        var index = top.layer.open({     //这里不能用layui.layer.open 否则不是在最上层 会被挡住一部分
            title : "添加科室类别",
            type : 2,
            content : ctx+"/depart/addDepart",
            area: ['330px', '260px'],
            maxmin: true  //可以最大化 原来 没有这个 不能显示在最外层

        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
    })

});


