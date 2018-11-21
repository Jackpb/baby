package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
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

import com.babyangel.modules.hospital.dao.TitlesDao;
import com.babyangel.modules.hospital.entity.TitlesEntity;
import com.babyangel.modules.hospital.service.TitlesService;


@Service("titlesService")
public class TitlesServiceImpl extends ServiceImpl<TitlesDao, TitlesEntity> implements TitlesService {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TitlesEntity> page = this.selectPage(
                new Query<TitlesEntity>(params).getPage(),
                new EntityWrapper<TitlesEntity>()
        );

        //显示部门信息
        //显示部门信息
        for(TitlesEntity titlesEntity : page.getRecords()){
            if(titlesEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(titlesEntity.getDeptId());
                if(null!=sysDeptEntity){
                    titlesEntity.setDeptName(sysDeptEntity.getName());
                }
            }
        }
        return new PageUtils(page);
    }
    @Override
    public List<TitlesEntity> selectByDeptId(int deptId){
        Wrapper<TitlesEntity> wrapper = new EntityWrapper<TitlesEntity>().eq("dept_id",deptId);
        return this.baseMapper.selectList(wrapper);
    }

}
