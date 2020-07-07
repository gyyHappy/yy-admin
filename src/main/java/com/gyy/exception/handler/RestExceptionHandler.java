package com.gyy.exception.handler;

import com.gyy.exception.BusinessException;
import com.gyy.exception.code.BaseResponseCode;
import com.gyy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 * @author GYY
 * @version 1.0
 * @date 2020/7/7 23:48
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * Exception 处理
     */
    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e){
        log.error("Exception,{},{}",e.getLocalizedMessage(),e);
        return new R(BaseResponseCode.SYSTEM_ERROR);
    }

    /**
     * BusinessException 处理
     */
    @ExceptionHandler(value = BusinessException.class)
    public R businessException(BusinessException e){
        log.error("businessException,{},{}",e.getLocalizedMessage(),e);
        return R.getR(e.getCode(),e.getMsg());
    }
}
