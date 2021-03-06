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
 * 职称管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-03 22:03:39
 */
@TableName("tb_titles")
public class TitlesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 职称名称
	 */
	@NotBlank(message="职称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 部门名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String deptName;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 所属医院
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer deptId;

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
	 * 设置：职称名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：职称名称
	 */
	public String getName() {
		return name;
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
	 * 设置：所属医院
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属医院
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 获取：所属部门
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：所属部门
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
