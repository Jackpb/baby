package com.babyangel.modules.hospital.service.impl;

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

import com.babyangel.modules.hospital.dao.DiseaseDao;
import com.babyangel.modules.hospital.entity.DiseaseEntity;
import com.babyangel.modules.hospital.service.DiseaseService;


@Service("diseaseService")
public class DiseaseServiceImpl extends ServiceImpl<DiseaseDao, DiseaseEntity> implements DiseaseService {
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiseaseEntity> page = this.selectPage(
                new Query<DiseaseEntity>(params).getPage(),
                new EntityWrapper<DiseaseEntity>()
        );

        //显示科室信息
        for(DiseaseEntity DiseaseEntity : page.getRecords()){
            if(DiseaseEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(DiseaseEntity.getDeptId());
                if(null!=sysDeptEntity){
                    DiseaseEntity.setDeptName(sysDeptEntity.getName());
                }
            }
        }

        return new PageUtils(page);
    }

}
