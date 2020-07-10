package com.gyy.exception.handler;

import com.gyy.exception.BusinessException;
import com.gyy.exception.code.BaseResponseCode;
import com.gyy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理
 * @author GYY
 * @version 1.0
 * @date 2020/7/7 23:48
 */
@RestControllerAdvice
@Slf4j
public class
RestExceptionHandler {

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

    /**
     * 参数异常 处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> R<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }
    private <T> R<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}",msgs[i]);
            i++;
        }
        return R.getR(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), msgs[0]);
    }

    /**
     * 权限异常 处理
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R unauthorizedException(UnauthorizedException e){
        log.error("UnauthorizedException,{},{}",e.getLocalizedMessage(),e);
        return R.getR(BaseResponseCode.NOT_PERMISSION);
    }
}
