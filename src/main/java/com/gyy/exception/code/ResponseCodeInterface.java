package com.gyy.exception.code;

/**
 * @author GYY
 * @version 1.0
 * @date 2020/7/4 21:27
 */
public interface ResponseCodeInterface {

    /**
     * 获取异常码
     */
    int getCode();

    /**
     * 获取异常信息
     */
    String getMsg();
}
