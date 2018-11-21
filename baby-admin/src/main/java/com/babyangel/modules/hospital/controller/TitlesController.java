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

import com.babyangel.modules.hospital.entity.TitlesEntity;
import com.babyangel.modules.hospital.service.TitlesService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 职称管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 11:47:49
 */
@RestController
@RequestMapping("hospital/titles")
public class TitlesController {
    @Autowired
    private TitlesService titlesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:titles:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = titlesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:titles:info")
    public R info(@PathVariable("id") Integer id){
        TitlesEntity titles = titlesService.selectById(id);

        return R.ok().put("titles", titles);
    }

    /**
     * 保存
     */
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:titles:save")
    public R save(@RequestBody TitlesEntity titles){
        titles.setCreateTime(new Date());
        ValidatorUtils.validateEntity(titles, AddGroup.class);
        titlesService.insert(titles);

        return R.ok();
    }
    /**
     * 信息
     */
    @RequestMapping("/dept/{id}")
    public R getByDeptId(@PathVariable("id") Integer deptId){
        List<TitlesEntity> titles = titlesService.selectByDeptId(deptId);

        return R.ok().put("titles", titles);
    }


    /**
     * 修改
     */
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:titles:update")
    public R update(@RequestBody TitlesEntity titles){
        ValidatorUtils.validateEntity(titles, UpdateGroup.class);
        titlesService.updateAllColumnById(titles);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:titles:delete")
    public R delete(@RequestBody Integer[] ids){
        titlesService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
