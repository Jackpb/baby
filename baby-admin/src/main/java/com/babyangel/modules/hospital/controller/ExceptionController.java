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

import com.babyangel.modules.hospital.entity.ExceptionEntity;
import com.babyangel.modules.hospital.service.ExceptionService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 意外管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 20:19:56
 */
@RestController
@RequestMapping("hospital/exception")
public class ExceptionController {
    @Autowired
    private ExceptionService exceptionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:exception:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = exceptionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:exception:info")
    public R info(@PathVariable("id") Integer id){
        ExceptionEntity exception = exceptionService.selectById(id);

        return R.ok().put("exception", exception);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:exception:save")
    public R save(@RequestBody ExceptionEntity exception){
        exception.setCreateTime(new Date());
        ValidatorUtils.validateEntity(exception, AddGroup.class);
        exceptionService.insert(exception);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:exception:update")
    public R update(@RequestBody ExceptionEntity exception){
        ValidatorUtils.validateEntity(exception, UpdateGroup.class);
        exceptionService.updateAllColumnById(exception);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:exception:delete")
    public R delete(@RequestBody Integer[] ids){
        exceptionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
