package com.babyangel.modules.hospital.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.hospital.entity.DiseaseEntity;
import com.babyangel.modules.hospital.service.DiseaseService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 病种管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 18:22:50
 */
@RestController
@RequestMapping("hospital/disease")
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:disease:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = diseaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:disease:info")
    public R info(@PathVariable("id") Integer id){
        DiseaseEntity disease = diseaseService.selectById(id);

        return R.ok().put("disease", disease);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:disease:save")
    public R save(@RequestBody DiseaseEntity disease){
        disease.setCreateTime(new Date());
        ValidatorUtils.validateEntity(disease, AddGroup.class);
        diseaseService.insert(disease);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:disease:update")
    public R update(@RequestBody DiseaseEntity disease){
        ValidatorUtils.validateEntity(disease, UpdateGroup.class);
        diseaseService.updateAllColumnById(disease);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:disease:delete")
    public R delete(@RequestBody Integer[] ids){
        diseaseService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
