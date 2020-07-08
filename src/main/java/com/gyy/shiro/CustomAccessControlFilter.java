package com.gyy.shiro;

import com.alibaba.fastjson.JSON;
import com.gyy.constants.Constant;
import com.gyy.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义过滤器
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 10:13
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {
    /**
     * 当isAccessAllowed返回false的时候，会进入onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        try {
            log.info("接口请求方式{}",request.getMethod());
            log.info("接口请求地址",request.getRequestURI());
            String token=request.getHeader(Constant.ACCESS_TOKEN);
            if(StringUtils.isEmpty(token)){
                throw new BusinessException(4010001,"用户凭证已失效请重新登录认证");
            }
            CustomUsernamePasswordToken customUsernamePasswordToken=new CustomUsernamePasswordToken(token);
            getSubject(servletRequest,servletResponse).login(customUsernamePasswordToken);
        } catch (BusinessException e) {
            customResponse(e.getCode(),e.getMessage(),servletResponse);
            return false;
        } catch (AuthenticationException e) {
            if(e.getCause() instanceof BusinessException){
                BusinessException businessException= (BusinessException) e.getCause();
                customResponse(businessException.getCode(),businessException.getMessage(),servletResponse);
            }else {
                customResponse(4000001,"用户认证失败",servletResponse);
            }
            return false;
        }catch (Exception e){
            customResponse(5000001,"系统异常",servletResponse);
        }
        return true;
    }

    private void customResponse(int code,String msg,ServletResponse response){
        try {
            Map<String,Object> result=new HashMap<>();
            result.put("code",code);
            result.put("msg",msg);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
            response.setCharacterEncoding("UTF-8");
            String json= JSON.toJSONString(result);
            OutputStream out=response.getOutputStream();
            out.write(json.getBytes());
            out.flush();
        } catch (IOException e) {
            log.error("customResponse 相应异常{}",e);
        }
    }
}
