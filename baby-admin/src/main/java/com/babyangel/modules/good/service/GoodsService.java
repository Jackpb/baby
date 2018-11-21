package com.babyangel.modules.good.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.good.entity.GoodsEntity;

import java.util.Map;

/**
 * 商品管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-02 21:43:31
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

