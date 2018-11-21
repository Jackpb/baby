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

import com.babyangel.modules.hospital.entity.NurseEntity;
import com.babyangel.modules.hospital.service.NurseService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 护士管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 20:19:56
 */
@RestController
@RequestMapping("hospital/nurse")
public class NurseController {
    @Autowired
    private NurseService nurseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:nurse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = nurseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:nurse:info")
    public R info(@PathVariable("id") Integer id){
        NurseEntity nurse = nurseService.selectById(id);

        return R.ok().put("nurse", nurse);
    }

    /**
     * 信息
     */
    @RequestMapping("/dept/{id}")
    public R getByDeptId(@PathVariable("id") Integer deptId){
        List<NurseEntity> nurses = nurseService.selectByDeptId(deptId);

        return R.ok().put("nurses", nurses);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:nurse:save")
    public R save(@RequestBody NurseEntity nurse){
        nurse.setCreateTime(new Date());
        ValidatorUtils.validateEntity(nurse, AddGroup.class);
        nurseService.insert(nurse);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:nurse:update")
    public R update(@RequestBody NurseEntity nurse){
        ValidatorUtils.validateEntity(nurse, UpdateGroup.class);
        nurseService.updateAllColumnById(nurse);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:nurse:delete")
    public R delete(@RequestBody Integer[] ids){
        nurseService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
