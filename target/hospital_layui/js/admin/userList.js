
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
            var status=$('#status option:selected');
            var create_time_e=$('#create_time_e');
            var create_time_s=$('#create_time_s');
            var levelId=$('#levelId');

            table.reload('userList',{
                page:{curr:1},
                where:{
                    realname:realname.val(),
                    username:username.val(),
                    gender:gender.val(),
                    status:status.val(),
                    levelId:levelId.val(),
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

            if ($.trim(value) == '')
            {
                var curDate = new Date();
                date = {//如果为空 就是没有选择 就用当前时间 加一个月
                    'date': curDate.getDate(),
                    'month': curDate.getMonth() + 1,
                    'year': curDate.getFullYear()
                };

                create_time_s.config.max = date;
                create_time_s.config.max.month = date.month - 1;
            }
        }
    });


    table.render({
        id:'userList',
        elem:'#userList',
        url:ctx+ "/admin/getAllUserList",
        limit:10,
        limits:[10,20,30,40],
        cols:[[
            {field:'user_id',title:'用户编号',align:'center',width:80},
            {field:'photo_path',title:'用户头像',align:'center',width:110,templet: "#imgtmp"},
            {field:'username',title:'用户用户名',align:'center',width:110},
            {field:'realname',title:'用户真实姓名',align:'center',width:110},
            {field:'gender',title:'性别',align:'center',templet:'#sexTpl',width:60},
            {field:'phone',title:'电话',align:'center',width:120},
            {field:'email',title:'E-mail',align:'center',width:180},
            {field:'address',title:'家庭住址',align:'center',width:180},
            {field:'levelId',title:'级别',align:'center',width:120,templet:'#levelIdTpl'},
            {field:'create_time',title:'注册时间',align:'center',width:160,
                templet:'<div>{{ formatTime(d.createTime,"yyyy-MM-dd hh:mm:ss")}}</div>'},
            {title:'操作',align:'center',width:180,toolbar:"#barDemo"}


        ]],
        page:true,
        loading:true

    });
    form.on('switch(statusSwitch)',function () {
        var status=this.checked ? 1 : 0;
        var id=this.value;  //看jsp页面中的代码
        $.ajax({
            url:ctx+'/admin/updateUserStatusById',
            type:"get",
            data:{
                status:status,
                user_id:id
            },
            success:function (d) {
                if(d.code==0)
                {
                    layer.msg("更新用户状态成功");
                }
                esle
                {
                    layer.msg("权限不足",{icon:5});
                }
            },
            error:function () {
                layer.msg("操作失败，检查sql及输出",{icon:5});
            }
        });
    });




    table.on('tool(userList)',function (obj) {
        var data=obj.data;
        if(obj.event==='delete')
        {
            layer.confirm('确定要删除'+data.realname+'么？',function (index) {
                $.ajax({
                    url:ctx+"/admin/deleteUserById",
                    type:"POST",
                    data:{"user_id":data.user_id},
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
                title:"修改用户信息",
                area:['450px','600px'],
                content:ctx+"/admin/editUser/"+data.user_id //controller中只是跳转jsp 所以这里不用success判断
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
