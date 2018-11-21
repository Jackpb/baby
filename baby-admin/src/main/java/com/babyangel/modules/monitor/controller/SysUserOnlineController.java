package com.babyangel.modules.monitor.controller;

import java.util.Arrays;
import java.util.Map;

import com.babyangel.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.babyangel.modules.monitor.entity.SysUserOnlineEntity;
import com.babyangel.modules.monitor.service.SysUserOnlineService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;



/**
 * 在线用户记录
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 19:03:05
 */
@RestController
@RequestMapping("monitor/sysuseronline")
public class SysUserOnlineController {
    @Autowired
    private SysUserOnlineService sysUserOnlineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("monitor:sysuseronline:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserOnlineService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("monitor:sysuseronline:info")
    public R info(@PathVariable("id") Long id){
        SysUserOnlineEntity sysUserOnline = sysUserOnlineService.selectById(id);

        return R.ok().put("sysUserOnline", sysUserOnline);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("monitor:sysuseronline:save")
    public R save(@RequestBody SysUserOnlineEntity sysUserOnline){
        sysUserOnlineService.insert(sysUserOnline);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("monitor:sysuseronline:update")
    public R update(@RequestBody SysUserOnlineEntity sysUserOnline){
        ValidatorUtils.validateEntity(sysUserOnline);
        sysUserOnlineService.updateAllColumnById(sysUserOnline);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("monitor:sysuseronline:delete")
    public R delete(@RequestBody Long[] ids){
        sysUserOnlineService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
