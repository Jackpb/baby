package com.babyangel.modules.publicity.service.impl;

import com.babyangel.common.utils.Constant;
import com.babyangel.modules.sys.entity.SysDeptEntity;
import com.babyangel.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.publicity.dao.DptMenuDao;
import com.babyangel.modules.publicity.entity.DptMenuEntity;
import com.babyangel.modules.publicity.service.DptMenuService;


@Service("dptMenuService")
public class DptMenuServiceImpl extends ServiceImpl<DptMenuDao, DptMenuEntity> implements DptMenuService {
    @Autowired
    private SysDeptService sysDeptService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DptMenuEntity> page = this.selectPage(
                new Query<DptMenuEntity>(params).getPage(),
                new EntityWrapper<DptMenuEntity>()
        );
        //显示部门信息
        //显示部门信息
        for(DptMenuEntity dptMenuEntity : page.getRecords()){
            if(dptMenuEntity.getDeptId()!=null){
                SysDeptEntity sysDeptEntity = sysDeptService.selectById(dptMenuEntity.getDeptId());
                if(null!=sysDeptEntity){
                    dptMenuEntity.setDeptName(sysDeptEntity.getName());
                }
            }
            //父菜单名称
            if(dptMenuEntity.getPid()!=null){
                DptMenuEntity dptMenuEntityTwo = this.selectById(dptMenuEntity.getPid());
                if(null!=dptMenuEntityTwo){
                    dptMenuEntity.setPname(dptMenuEntity.getName());
                }
            }
        }
        return new PageUtils(page);
    }

    @Override
    public DptMenuEntity selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public List<DptMenuEntity> queryList(HashMap<String, Object> params) {
        //1、只能获取菜单及其下属菜单
        List<DptMenuEntity> menuList =
                this.selectList(new EntityWrapper<DptMenuEntity>().eq("status",1)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));
        //2、设置菜单的父亲名称
        for(DptMenuEntity menuEntity : menuList){
            DptMenuEntity parentMenuEntity =  this.selectById(menuEntity.getPid());
            if(parentMenuEntity != null){
                menuEntity.setPname(parentMenuEntity.getName());
            }
        }
        return menuList;
    }

}
