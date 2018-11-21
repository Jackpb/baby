package com.babyangel.modules.publicity.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.common.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.publicity.entity.PublicityEntity;
import com.babyangel.modules.publicity.service.PublicityService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 宣教内容管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 15:08:58
 */
@RestController
@RequestMapping("publicity/publicity")
public class PublicityController {
    @Autowired
    private PublicityService publicityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("publicity:publicity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = publicityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("publicity:publicity:info")
    public R info(@PathVariable("id") Long id){
        PublicityEntity publicity = publicityService.selectById(id);

        return R.ok().put("publicity", publicity);
    }

    /**
     * 保存
     */
    @Log(title = "宣教管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("publicity:publicity:save")
    public R save(@RequestBody PublicityEntity publicity){
        publicity.setCreateTime(new Date());
        ValidatorUtils.validateEntity(publicity, AddGroup.class);
        publicityService.insert(publicity);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "宣教管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("publicity:publicity:update")
    public R update(@RequestBody PublicityEntity publicity){
        ValidatorUtils.validateEntity(publicity, AddGroup.class);
        publicityService.updateAllColumnById(publicity);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "宣教管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("publicity:publicity:delete")
    public R delete(@RequestBody Long[] ids){
        publicityService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
