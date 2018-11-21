package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.DiseaseEntity;
import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.babyangel.modules.hospital.entity.NurseEntity;
import com.babyangel.modules.hospital.service.MedicalTeamService;
import com.babyangel.modules.hospital.service.NurseService;
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

import com.babyangel.modules.hospital.dao.BedDao;
import com.babyangel.modules.hospital.entity.BedEntity;
import com.babyangel.modules.hospital.service.BedService;


@Service("bedService")
public class BedServiceImpl extends ServiceImpl<BedDao, BedEntity> implements BedService {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private NurseService nurseService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BedEntity> page = this.selectPage(
                new Query<BedEntity>(params).getPage(),
                new EntityWrapper<BedEntity>()
        );

        //显示科室信息
        for(com.babyangel.modules.hospital.entity.BedEntity bedEntity : page.getRecords()){
            if(bedEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(bedEntity.getDeptId());
                if(null!=sysDeptEntity){
                    bedEntity.setDeptName(sysDeptEntity.getName());
                }
            }

            if(bedEntity.getNurseId()!=null){
               NurseEntity nurseEntity = nurseService.selectById(bedEntity.getNurseId());
                if(null!=nurseEntity){
                    bedEntity.setNursesName(nurseEntity.getName());
                }
            }

        }

        return new PageUtils(page);
    }

}
