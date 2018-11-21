package com.babyangel.modules.hospital.entity;

import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 幕后团队管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 16:06:30
 */
@TableName("tb_scenes_team")
public class ScenesTeamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 团队名称
	 */
	@NotBlank(message="团队不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 团队介绍
	 */
	@NotBlank(message="介绍不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String introduce;

	/**
	 * 设置：编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：团队名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：团队名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：团队介绍
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	/**
	 * 获取：团队介绍
	 */
	public String getIntroduce() {
		return introduce;
	}
}
