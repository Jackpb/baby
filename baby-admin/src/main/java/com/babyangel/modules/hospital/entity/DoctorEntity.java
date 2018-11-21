package com.babyangel.modules.hospital.entity;

import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 医生管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 20:19:56
 */
@TableName("tb_doctor")
public class DoctorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	@NotBlank(message="姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 简介
	 */
	private String introduce;
	/**
	 * 点赞数
	 */
	private Integer clickRate;
	/**
	 * 浏览量
	 */
	private Integer viewRate;
	/**
	 * 状态：0禁用，1启用
	 */
	private Integer status;
	/**
	 * 登录用户ID
	 */
	private Integer userId;
	/**
	 * 所属科室
	 */
	@NotNull(message="科室不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer deptId;
	/**
	 * 所属团队
	 */
	@NotNull(message="团队不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer teamId;
	/**
	 * 职称
	 */
	@NotNull(message="职称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer titleId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 科室名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String deptName;

	/**
	 * 团队名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String teamName;

	/**
	 * 职称名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String titlesName;

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
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：简介
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	/**
	 * 获取：简介
	 */
	public String getIntroduce() {
		return introduce;
	}
	/**
	 * 设置：点赞数
	 */
	public void setClickRate(Integer clickRate) {
		this.clickRate = clickRate;
	}
	/**
	 * 获取：点赞数
	 */
	public Integer getClickRate() {
		return clickRate;
	}
	/**
	 * 设置：浏览量
	 */
	public void setViewRate(Integer viewRate) {
		this.viewRate = viewRate;
	}
	/**
	 * 获取：浏览量
	 */
	public Integer getViewRate() {
		return viewRate;
	}
	/**
	 * 设置：状态：0禁用，1启用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0禁用，1启用
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：登录用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：登录用户ID
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：所属部门
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属部门
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：所属团队
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	/**
	 * 获取：所属团队
	 */
	public Integer getTeamId() {
		return teamId;
	}
	/**
	 * 设置：职称
	 */
	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}
	/**
	 * 获取：职称
	 */
	public Integer getTitleId() {
		return titleId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 获取：科室名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：科室名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTitlesName() {
		return titlesName;
	}

	public void setTitlesName(String titlesName) {
		this.titlesName = titlesName;
	}
}
