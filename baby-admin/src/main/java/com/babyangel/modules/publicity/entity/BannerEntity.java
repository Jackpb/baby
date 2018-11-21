package com.babyangel.modules.publicity.entity;

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
 * banner管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 14:33:16
 */
@TableName("tb_banner")
public class BannerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * banner名称
	 */
	@NotBlank(message="banner名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 链接
	 */
	private String mediaUrl;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 启用状态：1启用，0禁用
	 */
	private Integer status;
	/**
	 * 所属类型ID
	 */
	@NotNull(message="banner类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long typeId;
	/**
	 * 所属科室ID
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;

	/**
	 * 部门名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String deptName;

	/**
	 * 类型名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String typeName;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：banner名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：banner名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：链接
	 */
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	/**
	 * 获取：链接
	 */
	public String getMediaUrl() {
		return mediaUrl;
	}
	/**
	 * 设置：显示顺序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：显示顺序
	 */
	public Integer getSort() {
		return sort;
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
	 * 设置：启用状态：1启用，0禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：启用状态：1启用，0禁用
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：所属类型ID
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取：所属类型ID
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * 设置：所属科室ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属科室ID
	 */
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
