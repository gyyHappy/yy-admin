package com.gyy.exception;

import com.gyy.exception.code.ResponseCodeInterface;

/**
 * 自定义异常
 * @author GYY
 * @version 1.0
 * @date 2020/4/24 21:55
 */
public class BusinessException extends RuntimeException {

    /**
     * 提示编码
     */
    private final int code;

    /**
     * 后端提示语句
     */
    private final String msg;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ResponseCodeInterface responseCodeInterface) {
        this(responseCodeInterface.getCode(),responseCodeInterface.getMsg());
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
