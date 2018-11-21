package com.babyangel.modules.hospital.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.hospital.dao.ScenesTeamDao;
import com.babyangel.modules.hospital.entity.ScenesTeamEntity;
import com.babyangel.modules.hospital.service.ScenesTeamService;


@Service("scenesTeamService")
public class ScenesTeamServiceImpl extends ServiceImpl<ScenesTeamDao, ScenesTeamEntity> implements ScenesTeamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ScenesTeamEntity> page = this.selectPage(
                new Query<ScenesTeamEntity>(params).getPage(),
                new EntityWrapper<ScenesTeamEntity>()
        );

        return new PageUtils(page);
    }

}
