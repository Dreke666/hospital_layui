
var $,tab;



layui.config(
    {base:ctx+'/js/' // 这里加上 ctx   当设定为localhost:8080/+项目名的时候访问时 ctx就是localhost:8080/+项目名

}).use(['bodyTab','form','element','layer','jquery'],function () {
    $=layui.jquery;
    var element=layui.element;
    var layer=layui.layer;
    var form=layui.form;


    var url;
    var logOutUrl;


    var usertype=$('#usertype').val();
    switch (parseInt(usertype)) {

        case 1:
            url="/admin/getMenus";
            logOutUrl=ctx+"/admin/logOut";
            break;
        case 2:
            url="/user/getMenus";
            logOutUrl=ctx+"/user/logOut";
            break;
        case 3:
            url="/doctor/getMenus";
            logOutUrl=ctx+"/doctor/logOut";
            break;
    }

    document.getElementById("logOut").href = logOutUrl;




    tab=layui.bodyTab({
        openTabNum: "50",//最大可打开的窗口数量
        url: ctx + url
    });
    //点击退出
    $(".signOut").click(function () {
        window.sessionStorage.removeItem("menu");
        menu=[];
        window.sessionStorage.removeItem("curmenu");
    })
    //隐藏左侧导航
    $(".hideMenu").click(function () {
        $(".layui-layout-admin").toggleClass("showMenu"); //隐藏 显示相互翻转
        tab.tabMove();//渲染顶部窗口
    })
    //渲染左侧菜单
    tab.render();
    // 添加新的标签窗口
    $("body").on("click", ".layui-nav .layui-nav-item a", function() {

        if ($(this).siblings().length == 0) {
            addTab($(this));//添加新的标签窗口
            $('body').removeClass('site-mobile'); // 移动端点击菜单关闭菜单层
        }
        //猜测是 展开这个菜单 如一共三级 点击的是第二级 就把第三级展现出来
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    });
    function addTab(_this){
        tab.tabAdd(_this);
    }
    if(window.sessionStorage.getItem("menu") != null)
    {
        menu = JSON.parse(window.sessionStorage.getItem("menu"));
        curmenu = window.sessionStorage.getItem("curmenu");
        var openTitle = '';
        for(var i=0;i<menu.length;i++)
        {
            openTitle = '';
            if(menu[i].icon)
            {
                if(menu[i].icon.indexOf("icon-") != -1)
                {
                    //openTitle += '<i class="iconfont '+menu[i].icon+'"></i>';
                    openTitle += '<i class="layui-icon  '+menu[i].icon+'"></i>';
                }
                else {
                    openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
                }
            }
            openTitle += '<cite>'+menu[i].title+'</cite>';
            openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';
            element.tabAdd("bodyTab",{
                title : openTitle,
                content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
                id : menu[i].layId
            })
            //定位到刷新前的窗口
            if(curmenu != "undefined"){
                if(curmenu == '' || curmenu == "null"){  //定位到后台首页
                    element.tabChange("bodyTab",'');
                }else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
                    element.tabChange("bodyTab",menu[i].layId);
                }
            }else{
                element.tabChange("bodyTab",menu[menu.length-1].layId);
            }
        }
        //渲染顶部窗口
        tab.tabMove();
    }
    //点击刷新当前
    $(".refresh").on("click",function(){
        if($(this).hasClass("refreshThis")){
            $(this).removeClass("refreshThis");
            $(".clildFrame .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload(true);
            setTimeout(function(){
                $(".refresh").addClass("refreshThis");
            },2000)
        }else{
            layer.msg("您点击的速度超过了服务器的响应速度，还是等两秒再刷新吧！");
        }
    });


    $(".closePageOther").on("click",function(){
        if($("#top_tabs li").length>2 && $("#top_tabs li.layui-this cite").text()!="登录首页"){
            var menu = JSON.parse(window.sessionStorage.getItem("menu"));
            $("#top_tabs li").each(function(){
                if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                    element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                    //此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
                    for(var i=0;i<menu.length;i++){
                        if($("#top_tabs li.layui-this cite").text() == menu[i].title){
                            menu.splice(0,menu.length,menu[i]);
                            window.sessionStorage.setItem("menu",JSON.stringify(menu));
                        }
                    }
                }
            })
        }else if($("#top_tabs li.layui-this cite").text()=="登录首页" && $("#top_tabs li").length>1){
            $("#top_tabs li").each(function(){
                if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                    element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                    window.sessionStorage.removeItem("menu");
                    menu = [];
                    window.sessionStorage.removeItem("curmenu");
                }
            })
        }else{
            layer.msg("没有可以关闭的窗口了!");
        }
        //渲染顶部窗口
        tab.tabMove();
    });

    //关闭全部窗口 只留下 登录首页
    $(".closePageAll").on("click",function(){
        if($("#top_tabs li").length > 1){
            $("#top_tabs li").each(function(){
                if($(this).attr("lay-id") != ''){
                    element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
                    window.sessionStorage.removeItem("menu");
                    menu = [];
                    window.sessionStorage.removeItem("curmenu");
                }
            })
        }else{
            layer.msg("没有可以关闭的窗口了!");
        }
        //渲染顶部窗口
        tab.tabMove();
    })

});





