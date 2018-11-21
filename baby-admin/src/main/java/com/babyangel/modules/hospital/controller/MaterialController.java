package com.babyangel.modules.hospital.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.babyangel.common.exception.RRException;
import com.babyangel.common.utils.StringUtils;
import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.babyangel.modules.oss.cloud.OSSFactory;
import com.babyangel.modules.oss.entity.SysOssEntity;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.hospital.entity.MaterialEntity;
import com.babyangel.modules.hospital.service.MaterialService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;
import org.springframework.web.multipart.MultipartFile;

/**
 * 素材管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-14 23:16:39
 */
@RestController
@RequestMapping("hospital/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:material:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = materialService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:material:info")
    public R info(@PathVariable("id") Long id){
        MaterialEntity material = materialService.selectById(id);

        return R.ok().put("material", material);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:material:save")
    public R save(@RequestBody MaterialEntity material){
        material.setCreateTime(new Date());
        if(StringUtils.isEmpty(material.getUrl())) {
            ValidatorUtils.validateEntity(material, AddGroup.class);
            materialService.insert(material);
        }else{

            Wrapper<MaterialEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("url",material.getUrl());
            MaterialEntity material1 = materialService.selectOne(wrapper);
            material.setType(material1.getType());
            material.setId(material1.getId());

            ValidatorUtils.validateEntity(material, UpdateGroup.class);
            materialService.updateAllColumnById(material);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:material:update")
    public R update(@RequestBody MaterialEntity material){
        ValidatorUtils.validateEntity(material, UpdateGroup.class);
        materialService.updateAllColumnById(material);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:material:delete")
    public R delete(@RequestBody Long[] ids){
        materialService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
}
