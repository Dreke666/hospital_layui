

layui.config({
    base:"/js/"
}).use(['form','layer','jquery','laypage','table','laytpl'],function () {
    var form=layui.form,table=layui.table;
    layer=parent.layer===undefined ? layui.layer:parent.layer,
        laypage=layui.laypage;
    $=layui.jquery;

    //表格数据
    table.render({
        id: 'noteList',//设定容器唯一 id。id 是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引，
        elem: '#noteList',//指定原始 table 容器的选择器或 DOM，方法渲染方式必填
        url:ctx+'/note/getNoteList', //数据接口 默认会自动传递两个参数：?page=1&limit=30 page 代表当前页码、limit 代//表
        // 每页数据量  该参数可通过 添加一个request参数自定义 下面有limit：10 所以发送的是 1 10

        cellMinWidth:80,

        limit:10, //每页显示的条数
        limits:[10,20,30,40], //每页条数的选择项
        cols:[[ //设置表头 值是一个二维数组
            {field:'note_id',title:'病历编号',width:250,align:'center'}, //允许排序
            {field:'note_title',title:'病历标题',align:'center',width:150},
            {field:'gender',title:'性别',align:'center',width:60},
            {field:'realname',title:'患者姓名',align:'center',width:120,templet: '<div>{{d.realname}}</div>'},
            {field:'create_time',title:'病历创建时间',sort:true,align:'center',templet:'<div>{{ formatTime(d.create_time,"yyyy-MM-dd hh:mm:ss")}}</div>'},
            {title:'操作',align:'center',width:180,toolbar:"#barDemo"}
        ]],
        page:true //开启分页
    });


    table.on('tool(noteList)',function (obj) {
        var data=obj.data;
        if(obj.event==='delete')
        {
            layer.confirm('确定要删除'+data.realname+'病历么？',function (index) {
                $.ajax({
                    url:ctx+"/note/deleteNoteById",
                    type:"POST",
                    data:{"note_id":data.note_id},
                    success:function (d) {
                        if(d.code==0)
                        {
                            layer.msg("删除成功",{icon:1});
                            obj.del();
                        }
                        else
                        {
                            layer.msg("权限不足，删除失败",{icon:5});
                        }
                    },
                    error:function () {
                        layer.msg("删除失败，检查sql及输出",{icon:5});
                    }
                })
                layer.close(index);
            });
        }
        else if(obj.event=='edit')
        {
            var editIndex=top.layer.open({
                type:2,
                title:"修改病历信息",
                area:['450px','600px'],
                content:ctx+"/note/editNote/"+data.note_id
            });
        }
    })


});


// 格式化时间
function formatTime(datetime, fmt) {
    if (datetime == null || datetime == 0) {
        return "";
    }
    if (parseInt(datetime) == datetime) {
        if (datetime.length == 10) {
            datetime = parseInt(datetime) * 1000;
        } else if (datetime.length == 13) {
            datetime = parseInt(datetime);
        }
    }
    datetime = new Date(datetime);
    var o = {
        "M+" : datetime.getMonth() + 1, // 月份
        "d+" : datetime.getDate(), // 日
        "h+" : datetime.getHours(), // 小时
        "m+" : datetime.getMinutes(), // 分
        "s+" : datetime.getSeconds(), // 秒
        "q+" : Math.floor((datetime.getMonth() + 3) / 3), // 季度
        "S" : datetime.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1,
                (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                    .substr(("" + o[k]).length)));
    return fmt;
}
