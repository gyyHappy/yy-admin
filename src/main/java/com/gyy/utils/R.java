package com.gyy.utils;

import com.gyy.exception.code.BaseResponseCode;
import com.gyy.exception.code.ResponseCodeInterface;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回
 *
 * @author GYY
 * @version 1.0
 * @date 2020/7/7 9:34
 */
@Data
public class R<T> implements Serializable {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 构造函数
     */
    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public R(ResponseCodeInterface responseCodeInterface) {
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
        this.data = null;
    }

    public R(ResponseCodeInterface responseCodeInterface, T data) {
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
        this.data = data;
    }

    public R(T data) {
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
        this.data = data;
    }

    public R() {
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
        this.data = null;
    }

    /**
     * 响应成功封装
     */
    public static <T> R ok() {
        return new <T>R();
    }

    public static <T> R ok(T data) {
        return new <T>R(data);
    }

    /**
     * 自定义返回封装
     */
    public static <T> R getR(int code, String msg, T data) {
        return new <T>R(code, msg, data);
    }

    public static <T> R getR(BaseResponseCode baseResponseCode) {
        return new <T>R(baseResponseCode);
    }
}
