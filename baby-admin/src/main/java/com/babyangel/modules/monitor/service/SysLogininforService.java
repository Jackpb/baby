package com.babyangel.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.monitor.entity.SysLogininforEntity;

import java.util.Map;

/**
 * 系统访问记录
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 09:44:48
 */
public interface SysLogininforService extends IService<SysLogininforEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

