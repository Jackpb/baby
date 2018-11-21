package com.babyangel.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.monitor.entity.SysOperLogEntity;

import java.util.Map;

/**
 * 操作日志记录
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 08:21:56
 */
public interface SysOperLogService extends IService<SysOperLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

