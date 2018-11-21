package com.babyangel.modules.hospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.MaterialEntity;

import java.util.Map;

/**
 * 素材管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-14 23:16:39
 */
public interface MaterialService extends IService<MaterialEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

