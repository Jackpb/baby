package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.BannerTypeEntity;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.hospital.dao.PublicityTypeDao;
import com.babyangel.modules.hospital.entity.PublicityTypeEntity;
import com.babyangel.modules.hospital.service.PublicityTypeService;


@Service("publicityTypeService")
public class PublicityTypeServiceImpl extends ServiceImpl<PublicityTypeDao, PublicityTypeEntity> implements PublicityTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PublicityTypeEntity> page = this.selectPage(
                new Query<PublicityTypeEntity>(params).getPage(),
                new EntityWrapper<PublicityTypeEntity>()
        );
        return new PageUtils(page);
    }
    @Override
    public List<PublicityTypeEntity> selectByPublicityTypeId(){
        Wrapper<PublicityTypeEntity> wrapper = new EntityWrapper<PublicityTypeEntity>();
        return this.baseMapper.selectList(wrapper);
    }

}
