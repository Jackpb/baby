package com.babyangel.modules.hospital.service;

import com.babyangel.modules.hospital.entity.BannerTypeEntity;
import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.PublicityTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 宣教类型管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 17:52:55
 */
public interface PublicityTypeService extends IService<PublicityTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<PublicityTypeEntity> selectByPublicityTypeId();
}

