package com.babyangel.modules.publicity.entity;

import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 科室菜单管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 17:40:34
 */
@TableName("tb_dpt_menu")
public class DptMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long pid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 种类：0菜单，1内容
	 */
	private Integer type;
	/**
	 * 图标
	 */
	private String logo;
	/**
	 * 可打开的连接地址
	 */
	private String url;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 状态：1启用，0禁用
	 */
	private Integer status;
	/**
	 * 所属部门
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 部门名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String deptName;
	/**
	 * 父菜单名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String pname;
	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;
	@TableField(exist=false)
	private List<?> list;
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
	 * 设置：父菜单ID，一级菜单为0
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父菜单ID，一级菜单为0
	 */
	public Long getPid() {
		return pid;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：种类：0菜单，1内容
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：种类：0菜单，1内容
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：图标
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：图标
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：可打开的连接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：可打开的连接地址
	 */
	public String getUrl() {
		return url;
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
	 * 设置：状态：1启用，0禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态：1启用，0禁用
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：所属部门
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属部门
	 */
	public Long getDeptId() {
		return deptId;
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
	 * 获取：父菜单名称
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * 设置：父菜单名称
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
