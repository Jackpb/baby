package com.babyangel.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.monitor.entity.SysUserOnlineEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在线用户记录
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 19:03:05
 */
public interface SysUserOnlineService extends IService<SysUserOnlineEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SysUserOnlineEntity selectOnlineBySessionId(String sessionId);

    List<SysUserOnlineEntity> selectOnlineByExpired(Date expiredDate);

    void deletedBatchSessionIds(Collection<String> collection);

    void deletedBySessionId(String sessionId);
}

