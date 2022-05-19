
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
            var username=$('#username');
            var realname=$('#realname');
            var gender=$('#gender option:selected');
            var rank=$('#rank option:selected');
            var depart_id=$('#depart_id option:selected');
            var create_time_e=$('#create_time_e');
            var create_time_s=$('#create_time_s');


            table.reload('doctorList',{
                page:{curr:1},
                where:{
                    realname:realname.val(),
                    username:username.val(),
                    gender:gender.val(),
                    rank:rank.val(),
                    depart_id:depart_id.val(),
                    //下面四个也是表格加载的时候的查询条件 在最底层的sql语句中做了 判断
                    create_time_s:create_time_s.val(),
                    create_time_e:create_time_e.val(),
                }
            });
        }
    };
    $(".search_btn").click(function () {
        var type=$(this).data('type');//jsp 查询 按钮中  data-type="search" 所以这里var type=search
        active[type] ? active[type].call(this) : ''; //查看active中有没有 search数组 有的话就调用其函数
    });


//下面四个时间选择器 改的很简单就是 后面的不能比前面的小就行了
    var create_time_s=laydate.render({
        elem:'#create_time_s',//指定元素 选择后赋值
        type:'datetime',//可选择：年、月、日、时、分、秒
        max:  nowTime, //最大选择 当前时间
        done:function (value,date) {  //控件选择完毕后的回调
            // value 得到日期生成的值，如：2017-08-18
            //date 得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            //  endMax=createTimeE.config.max; //不知何用
            create_time_e.config.min=date; //结束时间最小为 开始时间
            create_time_e.config.min.month=date.month-1; //如果没有那么 createTimeE只能从下一个月开始选择
        }
    });
    var create_time_e=laydate.render({
        elem:'#create_time_e',
        type:'datetime',
        max:nowTime,
        done:function (value,date) {

            if ($.trim(value) == '') //trim是去掉value两端的空格 //如果我们 点击清空以后 创造当前时间 不然createTimeS无法选择
            {
                var curDate = new Date();
                date = {//如果为空 就是没有选择 就用当前时间 加一个月
                    'date': curDate.getDate(),
                    'month': curDate.getMonth() + 1,
                    'year': curDate.getFullYear()
                };

                create_time_s.config.max = date; //如果先选定了结束时间 那么开始时间最大就是结束时间
                create_time_s.config.max.month = date.month - 1;
            }
        }
    });


    table.render({
        id:'doctorList',
        elem:'#doctorList',
        url:ctx+ "/admin/getAllDoctorList",
        limit:10,
        limits:[10,20,30,40],
        cols:[[
            {field:'doctor_id',title:'医生编号',align:'center',width:80},
            {field:'photo_path',title:'医生头像',align:'center',width:110,templet: "#imgtmp"},
            {field:'username',title:'医生用户名',align:'center',width:110},
            {field:'realname',title:'医生真实姓名',align:'center',width:110},
            {field:'gender',title:'性别',align:'center',templet:'#sexTpl',width:60},
            {field:'phone',title:'电话',align:'center',width:120},
            {field:'email',title:'E-mail',align:'center',width:180},
            {field:'rank',title:'级别',align:'center',width:120,templet:'#rankTpl'},
            {field:'depart_id',title:'所属科室',align:'center',width:120,templet: '<div>{{d.depart.depart_name}}</div>'},
            {field:'create_time',title:'注册时间',align:'center',width:160,
                templet:'<div>{{ formatTime(d.createTime,"yyyy-MM-dd hh:mm:ss")}}</div>'},
            {title:'操作',align:'center',width:180,toolbar:"#barDemo"}  //注意！！！！
            // 宽度设置大一点 否则 删除图标显示不出来 然后会出现下拉符号 显示
            // 删除符号 但此时点击就不会有效果了 所以windth设大一点 都显示出来
        ]],
        page:true,
        loading:true

    });





    table.on('tool(doctorList)',function (obj) {
        var data=obj.data;
        if(obj.event==='delete')
        {
            layer.confirm('确定要删除'+data.realname+'么？',function (index) {
                $.ajax({
                    url:ctx+"/admin/deleteDoctorById",
                    type:"POST",
                    data:{"doctor_id":data.doctor_id},
                    success:function (d) {
                        if(d.code==0)
                        {
                            layer.msg("删除成功",{icon:1});
                            obj.del();//下面没有重新加载table
                            // 这里删除了不会自动刷新 但页面中 这一项没有了
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
                title:"修改医生信息",
                area:['450px','600px'],
                content:ctx+"/admin/editDoctor/"+data.doctor_id //controller中只是跳转jsp 所以这里不用success判断
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
