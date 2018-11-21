package com.babyangel.modules.hospital.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.babyangel.modules.hospital.service.MedicalTeamService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 医护团队管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 19:24:25
 */
@RestController
@RequestMapping("hospital/medicalteam")
public class MedicalTeamController {
    @Autowired
    private MedicalTeamService medicalTeamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:medicalteam:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = medicalTeamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:medicalteam:info")
    public R info(@PathVariable("id") Integer id){
        MedicalTeamEntity medicalTeam = medicalTeamService.selectById(id);

        return R.ok().put("medicalTeam", medicalTeam);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:medicalteam:save")
    public R save(@RequestBody MedicalTeamEntity medicalTeam){
        medicalTeam.setCreateTime(new Date());
        ValidatorUtils.validateEntity(medicalTeam, AddGroup.class);
        medicalTeamService.insert(medicalTeam);

        return R.ok();
    }
    /**
     * 信息
     */
    @RequestMapping("/dept/{id}")
    public R getByDeptId(@PathVariable("id") Integer deptId){
        List<MedicalTeamEntity> medicalTeams = medicalTeamService.selectByDeptId(deptId);

        return R.ok().put("medicalTeams", medicalTeams);
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:medicalteam:update")
    public R update(@RequestBody MedicalTeamEntity medicalTeam){
        ValidatorUtils.validateEntity(medicalTeam, UpdateGroup.class);
        medicalTeamService.updateAllColumnById(medicalTeam);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:medicalteam:delete")
    public R delete(@RequestBody Integer[] ids){
        medicalTeamService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
