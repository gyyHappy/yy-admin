package com.gyy.common.constants;

/**
 * 常量类
 * @author GYY
 * @version 1.0
 * @date 2020/4/27 14:22
 */
public class Constant {

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_NAME="jwt-user-name-key";

    /**
     * 权限key
     */
    public static final String JWT_PERMISSIONS_KEY="jwt-permissions-key_";


    /**
     * 角色key
     */
    public static final String JWT_ROLES_KEY="jwt-roles-key_";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST="jwt-refresh-token-blacklist_";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST="jwt-access-token-blacklist_";

    /**
     * access_token 用户信息修改后标记 key
     */
    public static final String JWT_ACCESS_TOKEN_UPDATE="jwt-access-token-update_";

    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN="authorization";
    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN="refresh_token";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY="account-lock-key_";

    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY="deleted-user-key_";

    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY="jwt-refresh-key_";
    /**
     * 标记新的access_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION="jwt-refresh-identification_";

    /**
     * 部门编码key
     */
    public static final String DEPT_CODE_KEY="dept-code-key_";

    /**
     * 用户权鉴缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY="shiro-cache:com.gyy.common.shiro.CustomRealm.authorizationCache:";

    /**
     * 超级管理员
     */
    public static final String ADMIN_ACCOUNT = "admin";

    /**
     * 目录
     */
    public static final Integer CATALOG = 1;

    /**
     * 菜单
     */
    public static final Integer MENU = 2;

    /**
     * 按钮
     */
    public static final Integer BUTTON = 3;

    /**
     * 当前页码
     */
    public static final String PAGE = "page";

    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";

    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";

    /**
     * 排序方式
     */
    public static final String ORDER = "order";

    /**
     *  升序
     */
    public static final String ASC = "asc";
}
