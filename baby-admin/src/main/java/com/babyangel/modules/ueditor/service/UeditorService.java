package com.babyangel.modules.ueditor.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.ueditor.entity.UeditorEntity;

import java.util.Map;

/**
 * ueditor素材管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-15 15:34:34
 */
public interface UeditorService extends IService<UeditorEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

