package com.babyangel.modules.hospital.service;

import com.babyangel.modules.publicity.entity.BannerEntity;
import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.BannerTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * Banner类型管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 17:11:45
 */
public interface BannerTypeService extends IService<BannerTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<BannerTypeEntity> selectByBannerTypeId();
}

