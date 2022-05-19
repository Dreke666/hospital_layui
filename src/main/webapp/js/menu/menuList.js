layui.use(['element','layer','form','upload','treeGrid','jquery'],function () {
    var treeGrid=layui.treeGrid;//treeGrid 树形表格 是layui第三方控件
    var form=layui.form;
    var treeTable=treeGrid.render({
        id:'treeTable',
        elem:'#treeTable',
        url:ctx+"/admin/menuData",
        cellMinWidth:100,
        treeId:'menuId',//树形id字段名称
        treeUpId:'parentId',//树形父Id字段名称
        treeShowName:'name',//树形形式显示的字段
        cols:[[
            {
                field:'menuId',
                title:'菜单ID',
                type:'checkbox',
                unresize:true
            },
            {
                field:'name',
                title:'菜单名'
            },
            {
                field:'icon',
                title:'图标',
                templet:'#iconTpl'
            },
            {
                field:'href',
                title:'链接'
            },
            {
                field:'perms',
                title:'权限标识'
            },
            {
                field:'sorting',
                title:'排序',
                event:'sorting',
                style:'cursor:pointer;'
            },
        ]],
        page:false
    });

    //监听单元格编辑
    treeGrid.on('tool(treeTable)',function (obj) {
        var data=obj.data;
        if(obj.event=='sorting')
        {
            var msg='',flag=false;
            layer.prompt(
                { //弹出框 有确定取消按钮那种
                formType:2,
                title:'修改ID为['+data.menuId+'] 的排序',
                value:data.sorting
                },function (value,index) { //弹出框 点击确定后 调用function函数
                    layer.close(index);
                    //这里一般是发送修改的Ajax请求
                    if(data.sorting!=value)
                    {
                        $.ajax({
                            type:"POST",
                            url:ctx+"/admin/updMenuSortingById",
                            async:false,
                            data:{'menuId':data.menuId,'sorting':value},
                            success:function (d) {
                                if(d.code==0)
                                {
                                    msg="修改成功";
                                    flag=true;
                                }
                                else
                                    {
                                        if(d.code==null||d.code=='')
                                        {
                                            msg="权限不足，联系超管！";
                                        }
                                        else
                                        {
                                            msg=d.msg;
                                        }
                                        value=data.sorting; //修改失败的话 value设为原来的序号
                                    }
                            },
                            error:function (jqXHR, textStatus, errorThrown) {
                                layer.alert("获取数据失败! 先检查sql 及 Tomcat Localhost Log 的输出");
                            }
                        });
                        if(flag)
                        {
                            layer.msg(msg,{icon:1});
                        }
                        else
                        {
                            layer.msg(msg,{icon:5});
                        }
                    }
                    //同步更新新表格和缓存对应的值
                    treeGrid.reload("treeTable",{});
                });
        }
    });

    $("#addMenu").click(function () {
        var checkStatus=treeGrid.checkStatus('treeTable');
        var data=checkStatus.data;
        var a=0;
        var menuTemp;
        var menuLevel=1; //代表添加的是几级菜单

        if(data.length>1)
        {
            layer.msg("只能选择一个！",{icon:5});
            return;
        }
        if(data!='')
        {
           a=data[0].menuId;
           menuTemp=data[0].sorting;
           if(menuTemp>1&&menuTemp<99)
           {
               menuLevel=2; //添加的是二级菜单
           }
           else if(menuTemp>110&&menuTemp<999)
           {
               menuLevel=3;//添加的是三级菜单
           }
           else if(menuTemp>1110)
           {
               menuLevel=4;//添加的是4级菜单  这里不做判断了 后台判断吧
           }

        }
        if(a==undefined||a!=1)
        {
            if(a==undefined)
            {
                a=0;//顶级菜单 parentId为0
                menuLevel=1; //添加的是一级菜单
            }
            //添加菜单
            layer.open({
                type:2,
                title:"添加菜单",
                area:['470px','420px'],
                content:ctx+"/admin/toSaveMenu/"+a+"/"+menuLevel,
                end:function () {
                    location.reload(); //菜单管理界面 重新加载
                }
            })
        }
        else //a==1 表示 登录首页 此菜单不允许添加子菜单
        {
            layer.msg("此菜单不允许操作！",{icon:5});
            return ;
        }
    })

    $("#editMenu").click(function () {
        var checkStatus=treeGrid.checkStatus('treeTable'),data=checkStatus.data,
            a='';
        if(data.length>1)
        {
            layer.msg("只能选择一个",{icon:5});
            return;
        }
        if(data=='')
        {
            layer.msg("请选择一个菜单",{icon:5});
            return;
        }
        if(data!='')
        {
            a=data[0].menuId;
        }
        if(a==1)
        {
            layer.msg("不允许操作此菜单",{icon:5});
            return;
        }
        layer.open({
            type:2,
            title:"编辑菜单",
            area:['470px','420px'],
            content: ctx+"/admin/toEditMenu/"+a,
            end:function () {
                top.location.reload(); //菜单管理界面 重新加载
            }
        })
    })

    $("#delMenu").click(function () {
        var checkStatus=treeGrid.checkStatus('treeTable');
        var data=checkStatus.data;
        var a='';
        if(data.length>1)
        {
            layer.msg("只能选择一个！",{icon:5});
            return;
        }
        if(data!='')
        {
            a=data[0].menuId;
        }
        if(a=='')
        {
            layer.msg("请选择要操作的菜单！",{icon:5});
            return;
        }
        if(a==1) //后台首页不允许操作
        {
            layer.msg("不允许删除！",{icon:5});
            return;
        }
        layer.confirm('真的删除吗？',function (index) {
            $.ajax({
                url:ctx+"/admin/delMenuById/"+a,
                type:"post",
                success:function (d) {
                    if(d.code==0)
                    {
                        layer.msg("删除成功",{icon:1});
                        setTimeout(function () {
                            treeGrid.reload("treeTable",{})
                        },1000);
                    }
                    else
                    {
                        layer.msg(d.msg,{icon:5});
                    }

                }
            })
            layer.close(index);
        });

    })
});












