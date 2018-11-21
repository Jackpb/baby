package com.babyangel.modules.monitor.service.impl;

import com.babyangel.common.utils.DateUtils;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.monitor.dao.SysUserOnlineDao;
import com.babyangel.modules.monitor.entity.SysUserOnlineEntity;
import com.babyangel.modules.monitor.service.SysUserOnlineService;


@Service("sysUserOnlineService")
public class SysUserOnlineServiceImpl extends ServiceImpl<SysUserOnlineDao, SysUserOnlineEntity> implements SysUserOnlineService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysUserOnlineEntity> page = this.selectPage(
                new Query<SysUserOnlineEntity>(params).getPage(),
                new EntityWrapper<SysUserOnlineEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public SysUserOnlineEntity selectOnlineBySessionId(String sessionId) {
        Wrapper<SysUserOnlineEntity> wrapper = Condition.create().eq("sessionId",sessionId);
        return this.selectOne(wrapper);
    }

    @Override
    public List<SysUserOnlineEntity> selectOnlineByExpired(Date expiredDate) {

//        String lastAccessTime = DateUtils.format(expiredDate,"yyyy-MM-dd HH:mm:ss");
        Wrapper<SysUserOnlineEntity> wrapper = Condition.create().lt("last_access_time",expiredDate);
        return this.selectList(wrapper);
    }

    @Override
    public void deletedBatchSessionIds(Collection<String> collection) {
        Wrapper<SysUserOnlineEntity> wrapper = Condition.create().in("sessionId",collection);
        this.delete(wrapper);
    }

    @Override
    public void deletedBySessionId(String sessionId) {
        Wrapper<SysUserOnlineEntity> wrapper = Condition.create().eq("sessionId",sessionId);
        this.delete(wrapper);
    }

}
