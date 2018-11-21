/**
 * Copyright 2018 BabyAngel365
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.babyangel.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.babyangel.modules.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
    /**
     * 查询部门的所有角色ID
     */
    List<SysRoleEntity> queryAllRole(Long deptId);
}
