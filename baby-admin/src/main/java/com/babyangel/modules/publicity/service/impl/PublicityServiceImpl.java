package com.babyangel.modules.publicity.service.impl;

import com.babyangel.modules.hospital.entity.PublicityTypeEntity;
import com.babyangel.modules.hospital.service.PublicityTypeService;
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

import com.babyangel.modules.publicity.dao.PublicityDao;
import com.babyangel.modules.publicity.entity.PublicityEntity;
import com.babyangel.modules.publicity.service.PublicityService;


@Service("publicityService")
public class PublicityServiceImpl extends ServiceImpl<PublicityDao, PublicityEntity> implements PublicityService {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private PublicityTypeService publicityTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PublicityEntity> page = this.selectPage(
                new Query<PublicityEntity>(params).getPage(),
                new EntityWrapper<PublicityEntity>()
        );

        //显示部门信息
        for(PublicityEntity publicityEntity : page.getRecords()){
            if(publicityEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(publicityEntity.getDeptId());
                if(null!=sysDeptEntity){
                    publicityEntity.setDeptName(sysDeptEntity.getName());
                }
            }
            //显示类型信息
            if(publicityEntity.getTypeId()!=null){
                PublicityTypeEntity publicityTypeEntity = publicityTypeService.selectById(publicityEntity.getTypeId());
                if(null!=publicityTypeEntity){
                    publicityEntity.setTypeName(publicityTypeEntity.getName());
                }
            }
        }
        return new PageUtils(page);
    }

}
