package com.gyy.modules.sys.service;

import com.gyy.common.utils.PageUtils;
import com.gyy.modules.sys.entity.SysLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author GYY
 * @since 2020-07-15
 */
public interface SysLogService extends IService<SysLogEntity> {


    PageUtils queryPage(Map<String, Object> params);
}
