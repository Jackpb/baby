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
 * 
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-14 09:26:27
 */
@TableName("tb_department_menu")
public class DepartmentMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 菜单名称
	 */
	@NotBlank(message="名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 菜单：0禁用，1启用
	 */
	private Integer menuIs;
	/**
	 * 菜单logo
	 */
	private String logo;
	/**
	 * 图片链接
	 */
	private String picUrl;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 状态：0禁用，1启用
	 */
	private Integer status;
	/**
	 * 所属部门
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer deptId;
	/**
	 * 宣教类型
	 */
	@NotNull(message="宣教类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer publicityId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 父id
	 */
	private Integer pid;
	/**
	 * 部门名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String deptName;

	/**
	 * 类型名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String publicityTypeName;


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
	 * 设置：菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：菜单名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：是否菜单
	 */
	public void setMenuIs(Integer menuIs) {
		this.menuIs = menuIs;
	}
	/**
	 * 获取：是否菜单
	 */
	public Integer getMenuIs() {
		return menuIs;
	}
	/**
	 * 设置：菜单logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：菜单logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：图片链接
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取：图片链接
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
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
	 * 设置：宣教类型
	 */
	public void setPublicityId(Integer publicityId) {
		this.publicityId = publicityId;
	}
	/**
	 * 获取：宣教类型
	 */
	public Integer getPublicityId() {
		return publicityId;
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
	 * 设置：父id
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父id
	 */
	public Integer getPid() {
		return pid;
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
	public String getPublicityTypeName() {
		return publicityTypeName;
	}
	/**
	 * 设置：类型名称
	 */
	public void setPublicityTypeName(String publicityTypeName) {
		this.publicityTypeName = publicityTypeName;
	}
}
