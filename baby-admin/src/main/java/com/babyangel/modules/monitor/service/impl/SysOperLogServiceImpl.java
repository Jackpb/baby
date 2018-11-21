package com.babyangel.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.monitor.dao.SysOperLogDao;
import com.babyangel.modules.monitor.entity.SysOperLogEntity;
import com.babyangel.modules.monitor.service.SysOperLogService;


@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLogEntity> implements SysOperLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysOperLogEntity> page = this.selectPage(
                new Query<SysOperLogEntity>(params).getPage(),
                new EntityWrapper<SysOperLogEntity>()
        );

        return new PageUtils(page);
    }

}
