package com.babyangel.modules.publicity.service.impl;

import com.babyangel.modules.hospital.entity.BannerTypeEntity;
import com.babyangel.modules.hospital.service.BannerTypeService;
import com.babyangel.modules.sys.entity.SysDeptEntity;
import com.babyangel.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.publicity.dao.BannerDao;
import com.babyangel.modules.publicity.entity.BannerEntity;
import com.babyangel.modules.publicity.service.BannerService;


@Service("bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements BannerService {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private BannerTypeService bannerTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BannerEntity> page = this.selectPage(
                new Query<BannerEntity>(params).getPage(),
                new EntityWrapper<BannerEntity>()
        );

        //显示部门信息
        for(BannerEntity bannerEntity : page.getRecords()){
            if(bannerEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(bannerEntity.getDeptId());
                if(null!=sysDeptEntity){
                    bannerEntity.setDeptName(sysDeptEntity.getName());
                }
            }
        //显示类型信息
            if(bannerEntity.getTypeId()!=null){
                BannerTypeEntity bannerTypeEntity = bannerTypeService.selectById(bannerEntity.getTypeId());
                if(null!=bannerTypeEntity){
                    bannerEntity.setTypeName(bannerTypeEntity.getName());
                }
            }
        }
        return new PageUtils(page);
    }

}
