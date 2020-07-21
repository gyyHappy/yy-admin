package com.gyy.common.exception.code;

/**
 * Base异常响应
 * @author GYY
 * @version 1.0
 * @date 2020/7/4 21:30
 */
public enum BaseResponseCode implements ResponseCodeInterface{
    /**
     * 这个要和前端约定好
     * code=0：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     * code=4010001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     * code=4010002：(凭证过期) 客户端请求刷新凭证接口
     * code=4030001：没有权限禁止访问
     * code=400xxxx：系统主动抛出的业务异常
     * code=5000001：系统异常
     *
     */
    SUCCESS(0,"操作成功"),
    SYSTEM_ERROR(5000001,"系统异常请稍后再试"),
    DATA_ERROR(4000001,"传入数据异常"),
    METHOD_IDENTITY_ERROR(4000002,"数据校验异常"),
    ACCOUNT_ERROR(4000003,"该账号不存在"),
    ACCOUNT_LOCK(4010001,"该账号被锁定"),
    ACCOUNT_PASSWORD_ERROR(4000004,"用户名密码不匹配"),
    TOKEN_ERROR(4010001,"用户未登录，请重新登录"),
    TOKEN_NOT_NULL(4010001,"token 不能为空"),
    TOKEN_UPDATE(4010001,"角色权限信息改变,请重新登录"),
    SHIRO_AUTHENTICATION_ERROR(4010001,"用户认证异常"),
    ACCOUNT_HAS_DELETED_ERROR(4000001,"该账号已被删除，请联系系统管理员"),
    ACCOUNT_DELETED_ADMIN_ERROR(4000001,"不能删除管理员账号"),
    ACCOUNT_DELETED_ERROR(4000001,"不能删除当前操作账号"),
    TOKEN_PAST_DUE(4010001,"token 异常,请重新登录"),
    NOT_PERMISSION(4030001,"没有权限访问该资源"),
    ILLEGAL_PARAMETER(5000002,"非法参数"),
    ROLE_ERROR(4000005,"新增用户所选角色，不是本人创建"),
    MENU_ERROR(4000006,"新增角色的权限，已超出你的权限范围")
    ;

    int code;

    String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
