package com.babyangel.modules.hospital.service;

import com.babyangel.modules.hospital.entity.MedicalTeamEntity;
import com.baomidou.mybatisplus.service.IService;
import com.babyangel.common.utils.PageUtils;
import com.babyangel.modules.hospital.entity.NurseEntity;

import java.util.List;
import java.util.Map;

/**
 * 护士管理
 *
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 20:19:56
 */
public interface NurseService extends IService<NurseEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<NurseEntity> selectByDeptId(int deptId);
}

