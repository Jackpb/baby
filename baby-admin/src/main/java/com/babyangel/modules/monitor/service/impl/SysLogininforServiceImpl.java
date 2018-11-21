package com.babyangel.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.monitor.dao.SysLogininforDao;
import com.babyangel.modules.monitor.entity.SysLogininforEntity;
import com.babyangel.modules.monitor.service.SysLogininforService;


@Service("sysLogininforService")
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforDao, SysLogininforEntity> implements SysLogininforService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysLogininforEntity> page = this.selectPage(
                new Query<SysLogininforEntity>(params).getPage(),
                new EntityWrapper<SysLogininforEntity>()
        );

        return new PageUtils(page);
    }

}
