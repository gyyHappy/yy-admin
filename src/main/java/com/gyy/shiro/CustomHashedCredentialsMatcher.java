package com.gyy.shiro;

import com.gyy.constants.Constant;
import com.gyy.exception.BusinessException;
import com.gyy.exception.code.BaseResponseCode;
import com.gyy.utils.JwtTokenUtil;
import com.gyy.utils.RedisUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 自定义校验器
 *
 * @author GYY
 * @version 1.0
 * @date 2020/7/6 10:27
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        String accessToken = (String) customUsernamePasswordToken.getPrincipal();
        String userId = JwtTokenUtil.getUserId(accessToken);
        /*
         * 判断用户是否被锁定
         */
        if (redisUtils.hasKey(Constant.ACCOUNT_LOCK_KEY + userId)) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK);
        }
        /*
         * 判断用户是否被删除
         */
        if (redisUtils.hasKey(Constant.DELETED_USER_KEY + userId)) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_HAS_DELETED_ERROR);
        }

        /*
         * 判断token 是否主动登出
         */
        if (redisUtils.hasKey(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken)) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        /*
         * 判断token是否通过校验
         */
        if (!JwtTokenUtil.validateToken(accessToken)) {
            throw new BusinessException(BaseResponseCode.TOKEN_PAST_DUE);
        }
        /*
         * 判断这个登录用户是否要主动去刷新
         *
         * 如果 key=Constant.JWT_REFRESH_KEY+userId大于accessToken说明是在 accessToken不是重新生成的
         * 这样就要判断它是否刷新过了/或者是否是新生成的token
         */
//        if(redisUtils.hasKey(Constant.JWT_REFRESH_KEY+userId)&&redisUtils.getExpire(Constant.JWT_REFRESH_KEY+userId, TimeUnit.MILLISECONDS)>JwtTokenUtil.getRemainingTime(accessToken)){
//            /**
//             * 是否存在刷新的标识
//             */
//            if(!redisUtils.hasKey(Constant.JWT_REFRESH_IDENTIFICATION+accessToken)){
//                throw new BusinessException(BaseResponseCode.TOKEN_PAST_DUE);
//            }
//        }
        if (redisUtils.hasKey(Constant.JWT_REFRESH_KEY + userId)) {
            /*
             * 通过剩余的过期时间比较如果token的剩余过期时间大与标记key的剩余过期时间
             * 就说明这个token是在这个标记key之后生成的
             */
            if (redisUtils.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) > JwtTokenUtil.getRemainingTime(accessToken)) {
                throw new BusinessException(BaseResponseCode.TOKEN_PAST_DUE);
            }
        }
        return true;
    }
}
