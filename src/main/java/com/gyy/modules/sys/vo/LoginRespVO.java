package com.gyy.modules.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录返回信息
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 11:27
 */
@Data
public class LoginRespVO {

    @ApiModelProperty(value = "业务访问token")
    private String accessToken;

    @ApiModelProperty(value = "业务token刷新")
    private String refreshToken;

    @ApiModelProperty(value = "用户id")
    private String userId;
}
