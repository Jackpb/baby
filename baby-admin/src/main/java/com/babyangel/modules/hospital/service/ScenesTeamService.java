package com.babyangel.modules.hospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.ScenesTeamEntity;

import java.util.Map;

/**
 * 幕后团队管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 16:06:30
 */
public interface ScenesTeamService extends IService<ScenesTeamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

