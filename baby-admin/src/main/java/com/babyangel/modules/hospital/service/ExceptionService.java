package com.babyangel.modules.hospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.ExceptionEntity;

import java.util.Map;

/**
 * 意外管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 20:19:56
 */
public interface ExceptionService extends IService<ExceptionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

