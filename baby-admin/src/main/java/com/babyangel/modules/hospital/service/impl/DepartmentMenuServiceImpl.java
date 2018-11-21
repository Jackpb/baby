package com.babyangel.modules.hospital.service.impl;

import com.babyangel.modules.publicity.entity.BannerEntity;
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

import com.babyangel.modules.hospital.dao.DepartmentMenuDao;
import com.babyangel.modules.hospital.entity.DepartmentMenuEntity;
import com.babyangel.modules.hospital.service.DepartmentMenuService;


@Service("departmentMenuService")
public class DepartmentMenuServiceImpl extends ServiceImpl<DepartmentMenuDao, DepartmentMenuEntity> implements DepartmentMenuService {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private PublicityTypeService publicityTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DepartmentMenuEntity> page = this.selectPage(
                new Query<DepartmentMenuEntity>(params).getPage(),
                new EntityWrapper<DepartmentMenuEntity>()
        );

        //显示部门信息
        //显示部门信息
        for(DepartmentMenuEntity departmentMenuEntity : page.getRecords()){
            if(departmentMenuEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(departmentMenuEntity.getDeptId());
                if(null!=sysDeptEntity){
                    departmentMenuEntity.setDeptName(sysDeptEntity.getName());
                }
            }

            if(departmentMenuEntity.getPublicityId()!=null){
                PublicityTypeEntity publicityTypeEntity = publicityTypeService.selectById(departmentMenuEntity.getPublicityId());
                if(null!=publicityTypeEntity){
                    departmentMenuEntity.setPublicityTypeName(publicityTypeEntity.getName());
                }
            }
        }
        return new PageUtils(page);
    }

}
