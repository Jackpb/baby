package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.hospital.entity.DiseaseEntity;
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

import com.babyangel.modules.hospital.dao.ExceptionDao;
import com.babyangel.modules.hospital.entity.ExceptionEntity;
import com.babyangel.modules.hospital.service.ExceptionService;


@Service("exceptionService")
public class ExceptionServiceImpl extends ServiceImpl<ExceptionDao, ExceptionEntity> implements ExceptionService {
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExceptionEntity> page = this.selectPage(
                new Query<ExceptionEntity>(params).getPage(),
                new EntityWrapper<ExceptionEntity>()
        );

        //显示科室信息
        for(com.babyangel.modules.hospital.entity.ExceptionEntity exceptionEntity : page.getRecords()){
            if(exceptionEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(exceptionEntity.getDeptId());
                if(null!=sysDeptEntity){
                    exceptionEntity.setDeptName(sysDeptEntity.getName());
                }
            }
        }
        return new PageUtils(page);
    }

}
