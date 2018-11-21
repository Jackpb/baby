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

import com.babyangel.modules.hospital.entity.DoctorEntity;
import com.babyangel.modules.hospital.service.DoctorService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 医生管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 20:19:56
 */
@RestController
@RequestMapping("hospital/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:doctor:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = doctorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:doctor:info")
    public R info(@PathVariable("id") Integer id){
        DoctorEntity doctor = doctorService.selectById(id);

        return R.ok().put("doctor", doctor);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:doctor:save")
    public R save(@RequestBody DoctorEntity doctor){
        doctor.setCreateTime(new Date());
        ValidatorUtils.validateEntity(doctor, AddGroup.class);
        doctorService.insert(doctor);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:doctor:update")
    public R update(@RequestBody DoctorEntity doctor){
        ValidatorUtils.validateEntity(doctor, UpdateGroup.class);
        doctorService.updateAllColumnById(doctor);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:doctor:delete")
    public R delete(@RequestBody Integer[] ids){
        doctorService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
