package com.babyangel.modules.hospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.TitlesEntity;

import java.util.List;
import java.util.Map;

/**
 * 职称管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 11:47:49
 */
public interface TitlesService extends IService<TitlesEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<TitlesEntity> selectByDeptId(int deptId);
}

