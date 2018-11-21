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

import com.babyangel.modules.publicity.entity.BannerEntity;
import com.babyangel.modules.publicity.service.BannerService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * banner管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 14:33:16
 */
@RestController
@RequestMapping("publicity/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("publicity:banner:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bannerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("publicity:banner:info")
    public R info(@PathVariable("id") Long id){
        BannerEntity banner = bannerService.selectById(id);

        return R.ok().put("banner", banner);
    }

    /**
     * 保存
     */
    @Log(title = "宣教管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("publicity:banner:save")
    public R save(@RequestBody BannerEntity banner){
        banner.setCreateTime(new Date());
        ValidatorUtils.validateEntity(banner, AddGroup.class);
        bannerService.insert(banner);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "宣教管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("publicity:banner:update")
    public R update(@RequestBody BannerEntity banner){
        ValidatorUtils.validateEntity(banner, AddGroup.class);
        bannerService.updateAllColumnById(banner);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "宣教管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("publicity:banner:delete")
    public R delete(@RequestBody Long[] ids){
        bannerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
