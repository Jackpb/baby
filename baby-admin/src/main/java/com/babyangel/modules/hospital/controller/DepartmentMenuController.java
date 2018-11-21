package com.babyangel.modules.hospital.controller;

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

import com.babyangel.modules.hospital.entity.DepartmentMenuEntity;
import com.babyangel.modules.hospital.service.DepartmentMenuService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-14 09:26:27
 */
@RestController
@RequestMapping("hospital/departmentmenu")
public class DepartmentMenuController {
    @Autowired
    private DepartmentMenuService departmentMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hospital:departmentmenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = departmentMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hospital:departmentmenu:info")
    public R info(@PathVariable("id") Integer id){
        DepartmentMenuEntity departmentMenu = departmentMenuService.selectById(id);

        return R.ok().put("departmentMenu", departmentMenu);
    }

    /**
     * 保存
     */
    @Log(title = "宣教管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("hospital:departmentmenu:save")
    public R save(@RequestBody DepartmentMenuEntity departmentMenu){
        departmentMenu.setCreateTime(new Date());
        ValidatorUtils.validateEntity(departmentMenu, AddGroup.class);
        departmentMenuService.insert(departmentMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "宣教管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("hospital:departmentmenu:update")
    public R update(@RequestBody DepartmentMenuEntity departmentMenu){
        ValidatorUtils.validateEntity(departmentMenu, AddGroup.class);
        departmentMenuService.updateAllColumnById(departmentMenu);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "宣教管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("hospital:departmentmenu:delete")
    public R delete(@RequestBody Integer[] ids){
        departmentMenuService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
