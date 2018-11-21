package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.hospital.dao.BannerTypeDao;
import com.babyangel.modules.hospital.entity.BannerTypeEntity;
import com.babyangel.modules.hospital.service.BannerTypeService;


@Service("bannerTypeService")
public class BannerTypeServiceImpl extends ServiceImpl<BannerTypeDao, BannerTypeEntity> implements BannerTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BannerTypeEntity> page = this.selectPage(
                new Query<BannerTypeEntity>(params).getPage(),
                new EntityWrapper<BannerTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BannerTypeEntity> selectByBannerTypeId(){
        Wrapper<BannerTypeEntity> wrapper = new EntityWrapper<BannerTypeEntity>();
        return this.baseMapper.selectList(wrapper);
    }

}
