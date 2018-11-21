package com.babyangel.modules.hospital.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.babyangel.modules.hospital.entity.BannerTypeEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.hospital.entity.PublicityTypeEntity;
import com.babyangel.modules.hospital.service.PublicityTypeService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 宣教类型管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 17:52:55
 */
@RestController
@RequestMapping("hospital/publicitytype")
public class PublicityTypeController {
    @Autowired
    private PublicityTypeService publicityTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:publicitytype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = publicityTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:publicitytype:info")
    public R info(@PathVariable("id") Integer id){
        PublicityTypeEntity publicityType = publicityTypeService.selectById(id);

        return R.ok().put("publicityType", publicityType);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:publicitytype:save")
    public R save(@RequestBody PublicityTypeEntity publicityType){
        publicityType.setCreateTime(new Date());
        ValidatorUtils.validateEntity(publicityType, AddGroup.class);
        publicityTypeService.insert(publicityType);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:publicitytype:update")
    public R update(@RequestBody PublicityTypeEntity publicityType){
        ValidatorUtils.validateEntity(publicityType, UpdateGroup.class);
        publicityTypeService.updateAllColumnById(publicityType);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:publicitytype:delete")
    public R delete(@RequestBody Integer[] ids){
        publicityTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/publicityTypeList")
    public R getByPublicityTypeId(){
        List<PublicityTypeEntity> publicityTypes = publicityTypeService.selectByPublicityTypeId();
        return R.ok().put("publicityTypes", publicityTypes);
    }

}
