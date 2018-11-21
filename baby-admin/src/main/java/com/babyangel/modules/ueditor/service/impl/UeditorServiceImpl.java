package com.babyangel.modules.ueditor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.common.utils.Query;

import com.babyangel.modules.ueditor.dao.UeditorDao;
import com.babyangel.modules.ueditor.entity.UeditorEntity;
import com.babyangel.modules.ueditor.service.UeditorService;


@Service("ueditorService")
public class UeditorServiceImpl extends ServiceImpl<UeditorDao, UeditorEntity> implements UeditorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UeditorEntity> page = this.selectPage(
                new Query<UeditorEntity>(params).getPage(),
                new EntityWrapper<UeditorEntity>()
        );

        return new PageUtils(page);
    }

}
