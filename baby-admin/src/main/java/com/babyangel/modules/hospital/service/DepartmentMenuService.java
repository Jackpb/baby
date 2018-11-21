package com.babyangel.modules.hospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.DepartmentMenuEntity;

import java.util.Map;

/**
 * 
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-14 09:26:27
 */
public interface DepartmentMenuService extends IService<DepartmentMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

