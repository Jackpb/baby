package com.babyangel.modules.publicity.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.publicity.entity.PublicityEntity;

import java.util.Map;

/**
 * 宣教内容管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 15:08:58
 */
public interface PublicityService extends IService<PublicityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

