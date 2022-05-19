
var $;
layui.config({
    base:ctx+'/js/' // 这里加上 ctx   当设定为localhost:8080/+项目名的时候访问时 ctx就是localhost:8080/+项目名
}).use(['form','layer','jquery'],function () {
    var form=layui.form,
        layer=parent.layer===undefined?layui.layer:parent.layer,
        laypage=layui.layPage;
    $=layui.jquery;
    form.on("submit(editRole)",function (data) {
        var treeObj=$.fn.zTree.getZTreeObj("xtree1");
        var list=treeObj.getCheckedNodes(true);//获取输入框被勾选的节点集合
        //菜单id
        var mStr="";
        for(var i=0;i<list.length;i++)
        {
            mStr+=list[i].menuId+","; //"1,2,3,4" 这种格式
        }
        //去除字符串尾
        mStr=mStr.substring(0,mStr.length-1);
        var m=$("#m");
        m.val(mStr);//将字符串 写入隐藏的携带参数
        //弹出loading
        var index=top.layer.msg('数据提交中,请稍候',{icon:16,time:false,shade:0.8});
        setTimeout(function () {
            $.ajax({
                type: "POST",
                async:false,
                url:ctx+"/admin/updateRole",
                data:$("#arf").serialize()
            });
            top.layer.close(index);
            top.layer.msg("角色修改成功");
            layer.closeAll("iframe");
            //parent.location.reload();
            setTimeout(function () {  //添加一个 延时 否则接着刷新 看不到提示的信息
                //刷新父页面
                parent.location.reload();
            },1000);
        },2000);
        return false;
    })
    //角色名称唯一性校验
    $("#roleName").blur(function () {  //失去焦点 调用函数
        //layer.msg($("#roleName").val());
        $.ajax({
            type:"get",
            url:ctx+"/admin/checkRoleName/"+$("#roleName").val()+"/"+$("#roleId").val(),

            success:function (data) {
                if(data.code!=0)
                {
                    top.layer.msg(data.msg);
                    $("#roleName").val("");
                    $("#roleName").focus();
                }
            }
        });
    });
});


var roleId=$("#roleId").val(); //loadMenuTree中ajax函数用到的参数
//这个menu是权限树的配置参数
var menu ={
    setting: {
        view:{
            showIcon: false, //不显示图标
        },
        data:{
            simpleData: {
                enable:true,
                idKey: "menuId",
                pIdKey: "parentId",
            },
            key:{
                name:"name",
            }
        },
        check:{
            enable:true //显示单选框或者复选框
        }
    },
    //获取菜单数据的ajax请求
    loadMenuTree:function () {
        $.ajax({
            type:"post",
            url:ctx+"/admin/xtreedata",
            data:{roleId:roleId}, //这个应该也是jsp种隐藏的请求参数 代表当前选中的角色 roleId
            dataType:"json",
            success:function (data) {
                $.fn.zTree.init($("#xtree1"),menu.setting,data); //初始化权限树
                // 执行后 权限树就可以显示出来 同时属于本角色的菜单处于选中状态
            }
        })
    }

};
$().ready(function (data) {
    menu.loadMenuTree(); //js一加载就调用此函数
});

function checkNode(e) {
    var zTree=$.fn.zTree.getZTreeObj("xtree1");
    type=e.data.type;
    nodes=zTree.getSelectedNodes();
    if(type.indexOf("All")<0&&nodes.length==0)
    {
        alert("请先选择一个节点");
    }
    if(type=="checkAllTrue")
    {
        zTree.checkAllNodes(true);
    }
    else if(type=="checkAllFalse")
    {
        zTree.checkAllNodes(false);
    }
}
$("#checkAllTrue").bind("click",{type:"checkAllTrue"},checkNode);
$("#checkAllFalse").bind("click",{type:"checkAllFalse"},checkNode);















