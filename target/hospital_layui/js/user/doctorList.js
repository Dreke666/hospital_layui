
layui.use(['form','jquery','table','laydate'],function () {
    var layer=layui.layer;
    var $=layui.jquery;
    var form=layui.form;
    var table=layui.table;
    var laydate=layui.laydate;  //时间选择控件
    var nowTime=new Date().valueOf();//valueOf() 方法返回 Date 对象的原始值 如：1576645321921
    var max=null;
    active ={  //定义一个数组 点击查询的时候会用到
        search:function () {  //实际上就是点击查询调用的函数

            var realname=$('#realname');
            var gender=$('#gender option:selected');
            var rank=$('#rank option:selected');
            var depart_id=$('#depart_id option:selected');



            table.reload('doctorList',{
                page:{curr:1},
                where:{
                    realname:realname.val(),

                    gender:gender.val(),
                    rank:rank.val(),
                    depart_id:depart_id.val(),

                }
            });
        }
    };
    $(".search_btn").click(function () {
        var type=$(this).data('type');//jsp 查询 按钮中  data-type="search" 所以这里var type=search
        active[type] ? active[type].call(this) : ''; //查看active中有没有 search数组 有的话就调用其函数
    });


    table.render({
        id:'doctorList',
        elem:'#doctorList',
        url:ctx+ "/admin/getAllDoctorList",
        limit:10,
        limits:[10,20,30,40],
        cols:[[
            {field:'doctor_id',title:'医生编号',align:'center',width:80},
            {field:'photo_path',title:'用户头像',align:'center',width:110,templet: "#imgtmp"},
            {field:'realname',title:'用户真实姓名',align:'center',width:110},
            {field:'gender',title:'性别',align:'center',templet:'#sexTpl',width:60},
            {field:'rank',title:'级别',align:'center',width:120,templet:'#rankTpl'},
            {field:'depart_id',title:'所属科室',align:'center',width:120,templet: '<div>{{d.depart.depart_name}}</div>'},

            {title:'点击预约',align:'center',width:180,toolbar:"#barDemo"}
        ]],
        page:true,
        loading:true

    });





    table.on('tool(doctorList)',function (obj) {
        var data=obj.data;
        if(obj.event==='res')
        {

            var editIndex=top.layer.open({
                type:2,
                title:"添加预约时间信息",
                area:['350px','500px'],
                content:ctx+"/reservation/chooseResDate/"+data.doctor_id
            });

        }
        else if(obj.event=='resCancel') //取消预约
        {
            layer.confirm('确定要取消预约'+data.realname+'医生么？',function (index) {
                $.ajax({
                    url:ctx+"/reservation/cancelRes",
                    type:"POST",
                    data:{"doctor_id":data.doctor_id},
                    success:function (d) {
                        if(d.code==0)
                        {
                            layer.msg("取消成功成功",{icon:1});

                        }
                        else
                        {
                            layer.msg(d.msg,{icon:5});
                        }
                    },
                    error:function () {
                        layer.msg("取消失败，检查sql及输出",{icon:5});
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
