package com.babyangel.modules.publicity.controller;

import java.util.*;

import com.babyangel.common.validator.ValidatorUtils;
import com.babyangel.common.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.publicity.entity.DptMenuEntity;
import com.babyangel.modules.publicity.service.DptMenuService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;

import com.babyangel.common.annotation.Log;
import com.babyangel.common.enums.BusinessType;

/**
 * 科室菜单管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 17:40:34
 */
@RestController
@RequestMapping("publicity/dptmenu")
public class DptMenuController {
    @Autowired
    private DptMenuService dptMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("publicity:dptmenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dptMenuService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("publicity:dptmenu:list")
    public R select(@RequestParam Map<String, Object> params){
        List<DptMenuEntity> page = dptMenuService.queryList(new HashMap<String, Object>());
        //添加一级部门

        DptMenuEntity root = new DptMenuEntity();
        root.setDeptId(0L);
        root.setName("一级菜单");
        root.setPid(-1L);
        root.setOpen(true);
        page.add(root);

        return R.ok().put("menuList", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("publicity:dptmenu:info")
    public R info(@PathVariable("id") Long id){
        DptMenuEntity dptMenu = dptMenuService.selectById(id);

        return R.ok().put("dptMenu", dptMenu);
    }

    /**
     * 保存
     */
    @Log(title = "宣教管理", businessType = BusinessType.INSERT)
    @RequestMapping("/save")
    @RequiresPermissions("publicity:dptmenu:save")
    public R save(@RequestBody DptMenuEntity dptMenu){
        dptMenu.setCreateTime(new Date());
        ValidatorUtils.validateEntity(dptMenu, AddGroup.class);
        dptMenuService.insert(dptMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @Log(title = "宣教管理", businessType = BusinessType.UPDATE)
    @RequestMapping("/update")
    @RequiresPermissions("publicity:dptmenu:update")
    public R update(@RequestBody DptMenuEntity dptMenu){
        ValidatorUtils.validateEntity(dptMenu, AddGroup.class);
        dptMenuService.updateAllColumnById(dptMenu);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @Log(title = "宣教管理", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    @RequiresPermissions("publicity:dptmenu:delete")
    public R delete(@RequestBody Long[] ids){
        dptMenuService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
