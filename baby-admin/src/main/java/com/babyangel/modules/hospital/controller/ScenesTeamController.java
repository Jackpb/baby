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

import com.babyangel.modules.hospital.entity.ScenesTeamEntity;
import com.babyangel.modules.hospital.service.ScenesTeamService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 幕后团队管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 16:06:30
 */
@RestController
@RequestMapping("hospital/scenesteam")
public class ScenesTeamController {
    @Autowired
    private ScenesTeamService scenesTeamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:scenesteam:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = scenesTeamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:scenesteam:info")
    public R info(@PathVariable("id") Integer id){
        ScenesTeamEntity scenesTeam = scenesTeamService.selectById(id);

        return R.ok().put("scenesTeam", scenesTeam);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:scenesteam:save")
    public R save(@RequestBody ScenesTeamEntity scenesTeam){
        ValidatorUtils.validateEntity(scenesTeam, AddGroup.class);
        scenesTeamService.insert(scenesTeam);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:scenesteam:update")
    public R update(@RequestBody ScenesTeamEntity scenesTeam){
        ValidatorUtils.validateEntity(scenesTeam, UpdateGroup.class);
        scenesTeamService.updateAllColumnById(scenesTeam);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:scenesteam:delete")
    public R delete(@RequestBody Integer[] ids){
        scenesTeamService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
