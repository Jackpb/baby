package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.babyangel.modules.hospital.entity.TitlesEntity;
import com.babyangel.modules.hospital.service.MedicalTeamService;
import com.babyangel.modules.hospital.service.TitlesService;
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

import com.babyangel.modules.hospital.dao.NurseDao;
import com.babyangel.modules.hospital.entity.NurseEntity;
import com.babyangel.modules.hospital.service.NurseService;


@Service("nurseService")
public class NurseServiceImpl extends ServiceImpl<NurseDao, NurseEntity> implements NurseService {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private MedicalTeamService medicalTeamService;

    @Autowired
    private TitlesService titlesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<NurseEntity> page = this.selectPage(
                new Query<NurseEntity>(params).getPage(),
                new EntityWrapper<NurseEntity>()
        );

        //显示科室信息
        for(com.babyangel.modules.hospital.entity.NurseEntity nurseEntity : page.getRecords()){
            if(nurseEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(nurseEntity.getDeptId());
                if(null!=sysDeptEntity){
                    nurseEntity.setDeptName(sysDeptEntity.getName());
                }
            }

            if(nurseEntity.getTeamId()!=null){
                MedicalTeamEntity medicalTeamEntity = medicalTeamService.selectById(nurseEntity.getTeamId());
                if(null!=medicalTeamEntity){
                    nurseEntity.setTeamName(medicalTeamEntity.getName());
                }
            }

            if(nurseEntity.getTitleId()!=null){
                TitlesEntity titlesEntity = titlesService.selectById(nurseEntity.getTitleId());
                if(null!=titlesEntity){
                    nurseEntity.setTitlesName(titlesEntity.getName());
                }
            }
        }

        return new PageUtils(page);
    }

    @Override
    public List<NurseEntity> selectByDeptId(int deptId){
        Wrapper<NurseEntity> wrapper = new EntityWrapper<NurseEntity>().eq("dept_id",deptId);
        return this.baseMapper.selectList(wrapper);
    }

}
