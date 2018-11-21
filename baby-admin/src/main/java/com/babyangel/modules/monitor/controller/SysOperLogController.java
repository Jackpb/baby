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

import com.babyangel.modules.monitor.entity.SysOperLogEntity;
import com.babyangel.modules.monitor.service.SysOperLogService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;



/**
 * 操作日志记录
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 08:21:56
 */
@RestController
@RequestMapping("monitor/sysoperlog")
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("monitor:sysoperlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysOperLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{operId}")
    @RequiresPermissions("monitor:sysoperlog:info")
    public R info(@PathVariable("operId") Integer operId){
        SysOperLogEntity sysOperLog = sysOperLogService.selectById(operId);

        return R.ok().put("sysOperLog", sysOperLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("monitor:sysoperlog:save")
    public R save(@RequestBody SysOperLogEntity sysOperLog){
        sysOperLogService.insert(sysOperLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("monitor:sysoperlog:update")
    public R update(@RequestBody SysOperLogEntity sysOperLog){
        ValidatorUtils.validateEntity(sysOperLog);
        sysOperLogService.updateAllColumnById(sysOperLog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("monitor:sysoperlog:delete")
    public R delete(@RequestBody Integer[] operIds){
        sysOperLogService.deleteBatchIds(Arrays.asList(operIds));

        return R.ok();
    }

}
