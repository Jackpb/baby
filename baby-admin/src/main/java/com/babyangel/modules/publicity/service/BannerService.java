package com.babyangel.modules.publicity.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.publicity.entity.BannerEntity;

import java.util.Map;

/**
 * banner管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 14:33:16
 */
public interface BannerService extends IService<BannerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

