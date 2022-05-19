

layui.config({
    base:"/js/"
}).use(['form','layer','jquery','laypage','table','laytpl'],function () {
    var form=layui.form,table=layui.table;
    layer=parent.layer===undefined ? layui.layer:parent.layer,
        laypage=layui.laypage;
    $=layui.jquery;

    //表格数据
    table.render({
        id: 'promotionList',//设定容器唯一 id。id 是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引，
        elem: '#promotionList',//指定原始 table 容器的选择器或 DOM，方法渲染方式必填
        url:ctx+'/promotion/getPromotionList', //数据接口 默认会自动传递两个参数：?page=1&limit=30 page 代表当前页码、limit 代//表
        // 每页数据量  该参数可通过 添加一个request参数自定义 下面有limit：10 所以发送的是 1 10

        cellMinWidth:80,

        limit:10, //每页显示的条数
        limits:[10,20,30,40], //每页条数的选择项
        cols:[[ //设置表头 值是一个二维数组
            {field:'title',title:'公告标题',width:250,align:'center'}, //允许排序
            {field:'content',title:'公告内容',align:'center',width:550},
            {field:'pub_date',title:'发布时间',sort:true,align:'center',templet:'<div>{{ formatTime(d.pub_date,"yyyy-MM-dd hh:mm:ss")}}</div>'},

        ]],
        page:true //开启分页
    });


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
