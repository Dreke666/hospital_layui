
layui.use(['form','jquery','table','laydate'],function () {
    var layer=layui.layer;
    var $=layui.jquery;
    var form=layui.form;
    var table=layui.table;
    var laydate=layui.laydate;  //时间选择控件
    var nowTime=new Date().valueOf();//valueOf() 方法返回 Date 对象的原始值 如：1576645321921
    var max=null;


    table.render({
        id:'myReservationList',
        elem:'#myReservationList',
        url:ctx+ "/reservation/getAllMyReservationList",
        limit:10,
        limits:[10,20,30,40],
        cols:[[
            {field:'res_id',title:'预约编号',align:'center',width:80},
            {field:'photo_path',title:'医生头像',align:'center',width:110,templet:'#imgtmp'},
            {field:'realname',title:'医生姓名',align:'center',width:110,templet: '<div>{{d.doctor.realname}}</div>'},
            {field:'rank',title:'级别',align:'center',width:120,templet:'#rankTpl'},
            {field:'order_time',title:'预约时间',align:'center',width:160,
                templet:'<div>{{ formatTime(d.order_time,"yyyy-MM-dd hh:mm:ss")}}</div>'},
            {title:'取消预约',align:'center',width:180,toolbar:"#barDemo"}  //注意！！！！

        ]],
        page:true,
        loading:true

    });





    table.on('tool(myReservationList)',function (obj) {
        var data=obj.data;
        if(obj.event==='delete')
        {
            layer.confirm('确定要取消'+data.doctor.realname+'预约么？',function (index) {
                $.ajax({
                    url:ctx+"/reservation/cancelRes",
                    type:"POST",
                    data:{"res_id":data.res_id},
                    success:function (d) {
                        if(d.code==0)
                        {
                            layer.msg("取消预约成功",{icon:1});
                            table.reload('myReservationList'); //重新加载表格

                        }
                        else
                        {
                            layer.msg("权限不足，删除失败",{icon:5});
                        }
                    },
                    error:function () {
                        layer.msg("取消预约失败，检查sql及输出",{icon:5});
                    }
                })


                layer.close(index);
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
