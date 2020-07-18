package com.gyy.modules.sys.service;

import com.gyy.common.utils.PageUtils;
import com.gyy.modules.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyy.modules.sys.form.SysLoginForm;
import com.gyy.modules.sys.vo.resp.LoginRespVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GYY
 * @since 2020-07-06
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 登录，并生成token
     */
    LoginRespVO login(SysLoginForm form);

    /**
     * 获取用户详情接口
     */
    SysUserEntity queryById(String id);

    /**
     * 分页查询用户列表
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 删除用户
     */
    void deleteBatch(String[] userIds);

    /**
     * 新增用户
     */
    void saveUser(SysUserEntity user);

    /**
     * 修改用户信息
     */
    void update(SysUserEntity user, String accessToken);

    /**
     * 修改密码
     */
    boolean updatePassword(String password, String newPassword, String userId);

}
