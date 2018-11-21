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

import com.babyangel.modules.hospital.dao.MaterialDao;
import com.babyangel.modules.hospital.entity.MaterialEntity;
import com.babyangel.modules.hospital.service.MaterialService;


@Service("materialService")
public class MaterialServiceImpl extends ServiceImpl<MaterialDao, MaterialEntity> implements MaterialService {
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MaterialEntity> page = this.selectPage(
                new Query<MaterialEntity>(params).getPage(),
                new EntityWrapper<MaterialEntity>()
        );

        //显示部门信息
        for(MaterialEntity titlesEntity : page.getRecords()){
            if(titlesEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(titlesEntity.getDeptId());
                if(null!=sysDeptEntity){
                    titlesEntity.setDeptName(sysDeptEntity.getName());
                }
            }
        }

        return new PageUtils(page);
    }

}
