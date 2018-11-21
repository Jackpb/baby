package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.DiseaseEntity;
import com.babyangel.modules.sys.entity.SysDeptEntity;
import com.babyangel.modules.sys.service.SysDeptService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.hospital.dao.MedicalTeamDao;
import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.babyangel.modules.hospital.service.MedicalTeamService;


@Service("medicalTeamService")
public class MedicalTeamServiceImpl extends ServiceImpl<MedicalTeamDao, MedicalTeamEntity> implements MedicalTeamService {
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MedicalTeamEntity> page = this.selectPage(
                new Query<MedicalTeamEntity>(params).getPage(),
                new EntityWrapper<MedicalTeamEntity>()
        );

        //显示科室信息
        for(com.babyangel.modules.hospital.entity.MedicalTeamEntity medicalTeamEntity : page.getRecords()){
            if(medicalTeamEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(medicalTeamEntity.getDeptId());
                if(null!=sysDeptEntity){
                    medicalTeamEntity.setDeptName(sysDeptEntity.getName());
                }
            }
        }

        return new PageUtils(page);
    }
    @Override
    public List<MedicalTeamEntity> selectByDeptId(int deptId){
        Wrapper<MedicalTeamEntity> wrapper = new EntityWrapper<MedicalTeamEntity>().eq("dept_id",deptId);
        return this.baseMapper.selectList(wrapper);
    }

}
