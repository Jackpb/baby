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

import com.babyangel.modules.monitor.entity.SysLogininforEntity;
import com.babyangel.modules.monitor.service.SysLogininforService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.R;



/**
 * 系统访问记录
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 09:44:48
 */
@RestController
@RequestMapping("monitor/syslogininfor")
public class SysLogininforController {
    @Autowired
    private SysLogininforService sysLogininforService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("monitor:syslogininfor:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysLogininforService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{infoId}")
    @RequiresPermissions("monitor:syslogininfor:info")
    public R info(@PathVariable("infoId") Integer infoId){
        SysLogininforEntity sysLogininfor = sysLogininforService.selectById(infoId);

        return R.ok().put("sysLogininfor", sysLogininfor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("monitor:syslogininfor:save")
    public R save(@RequestBody SysLogininforEntity sysLogininfor){
        sysLogininforService.insert(sysLogininfor);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("monitor:syslogininfor:update")
    public R update(@RequestBody SysLogininforEntity sysLogininfor){
        ValidatorUtils.validateEntity(sysLogininfor);
        sysLogininforService.updateAllColumnById(sysLogininfor);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("monitor:syslogininfor:delete")
    public R delete(@RequestBody Integer[] infoIds){
        sysLogininforService.deleteBatchIds(Arrays.asList(infoIds));

        return R.ok();
    }

}
