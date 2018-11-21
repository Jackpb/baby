package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.babyangel.modules.hospital.entity.TitlesEntity;
import com.babyangel.modules.hospital.service.MedicalTeamService;
import com.babyangel.modules.hospital.service.TitlesService;
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

import com.babyangel.modules.hospital.dao.DoctorDao;
import com.babyangel.modules.hospital.entity.DoctorEntity;
import com.babyangel.modules.hospital.service.DoctorService;


@Service("doctorService")
public class DoctorServiceImpl extends ServiceImpl<DoctorDao, DoctorEntity> implements DoctorService {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private MedicalTeamService medicalTeamService;

    @Autowired
    private TitlesService titlesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DoctorEntity> page = this.selectPage(
                new Query<DoctorEntity>(params).getPage(),
                new EntityWrapper<DoctorEntity>()
        );

        //显示科室信息
        for(com.babyangel.modules.hospital.entity.DoctorEntity doctorEntity : page.getRecords()){
            if(doctorEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(doctorEntity.getDeptId());
                if(null!=sysDeptEntity){
                    doctorEntity.setDeptName(sysDeptEntity.getName());
                }
            }

            if(doctorEntity.getTeamId()!=null){
                MedicalTeamEntity medicalTeamEntity = medicalTeamService.selectById(doctorEntity.getTeamId());
                if(null!=medicalTeamEntity){
                    doctorEntity.setTeamName(medicalTeamEntity.getName());
                }
            }

            if(doctorEntity.getTitleId()!=null){
                TitlesEntity titlesEntity = titlesService.selectById(doctorEntity.getTitleId());
                if(null!=titlesEntity){
                    doctorEntity.setTitlesName(titlesEntity.getName());
                }
            }

        }

        return new PageUtils(page);
    }

}
