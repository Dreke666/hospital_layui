package com.zhang.hospital.util;


import java.io.Serializable;

public class ResultUtil implements Serializable {
    @Override
    public String toString() {
        return "ResultUtil{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    //这几个属性是与layui交互数据的时候 固定的读这几个属性
    //比如下面count属性 就是 layui table模块的中需要的一共多少页
    //layui官网的table模块 文档
    //      "code": res.status, //解析接口状态
    //      "msg": res.message, //解析提示文本
    //      "count": res.total, //解析数据长度
    //      "data": res.data.item //解析数据列表
    private Integer code;
    private String msg = "";
    private long count = 0L;
    private Object data;

    public ResultUtil() {
        super();
    }

    public ResultUtil(Integer code) {
        super();
        this.code = code;
    }

    public ResultUtil(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long l) {
        this.count = l;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultUtil ok() {
        return new ResultUtil(0);
    }

    public static ResultUtil ok(Object list) {
        ResultUtil result = new ResultUtil();
        result.setCode(0);
        result.setData(list);
        return result;
    }

    public static ResultUtil ok(String msg) {
        ResultUtil result = new ResultUtil();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    public static ResultUtil error() {
        return new ResultUtil(500, "没有此权限，请联系超管！");
    }

    public static ResultUtil error(String str) {
        return new ResultUtil(500, str);
    }
}

