package com.babyangel.modules.hospital.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.hospital.entity.BannerTypeEntity;
import com.babyangel.modules.hospital.service.BannerTypeService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * Banner类型管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 17:11:45
 */
@RestController
@RequestMapping("hospital/bannertype")
public class BannerTypeController {
    @Autowired
    private BannerTypeService bannerTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:bannertype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bannerTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:bannertype:info")
    public R info(@PathVariable("id") Integer id){
        BannerTypeEntity bannerType = bannerTypeService.selectById(id);

        return R.ok().put("bannerType", bannerType);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:bannertype:save")
    public R save(@RequestBody BannerTypeEntity bannerType){
        bannerType.setCreateTime(new Date());
        ValidatorUtils.validateEntity(bannerType, AddGroup.class);
        bannerTypeService.insert(bannerType);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:bannertype:update")
    public R update(@RequestBody BannerTypeEntity bannerType){
        ValidatorUtils.validateEntity(bannerType, UpdateGroup.class);
        bannerTypeService.updateAllColumnById(bannerType);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:bannertype:delete")
    public R delete(@RequestBody Integer[] ids){
        bannerTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/bannerTypeList")
    public R getByBannerTypeId(){
        List<BannerTypeEntity> bannerTypes = bannerTypeService.selectByBannerTypeId();
        return R.ok().put("bannerTypes", bannerTypes);
    }

}
