package com.babyangel.modules.hospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.BedEntity;

import java.util.Map;

/**
 * 床位管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 18:22:50
 */
public interface BedService extends IService<BedEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

