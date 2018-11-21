package com.babyangel.modules.hospital.controller;

import java.util.Arrays;
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

import com.babyangel.modules.hospital.entity.BedEntity;
import com.babyangel.modules.hospital.service.BedService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 床位管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 18:22:50
 */
@RestController
@RequestMapping("hospital/bed")
public class BedController {
    @Autowired
    private BedService bedService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:bed:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bedService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:bed:info")
    public R info(@PathVariable("id") Integer id){
        BedEntity bed = bedService.selectById(id);

        return R.ok().put("bed", bed);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:bed:save")
    public R save(@RequestBody BedEntity bed){
        ValidatorUtils.validateEntity(bed, AddGroup.class);
        bedService.insert(bed);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:bed:update")
    public R update(@RequestBody BedEntity bed){
        ValidatorUtils.validateEntity(bed, UpdateGroup.class);
        bedService.updateAllColumnById(bed);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:bed:delete")
    public R delete(@RequestBody Integer[] ids){
        bedService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
