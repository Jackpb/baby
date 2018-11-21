package com.babyangel.modules.publicity.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.publicity.entity.DptMenuEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科室菜单管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 17:40:34
 */
public interface DptMenuService extends IService<DptMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    DptMenuEntity selectById(Long id);

    List<DptMenuEntity> queryList(HashMap<String, Object> stringObjectHashMap);

}

