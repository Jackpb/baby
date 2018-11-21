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
 * 病种管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 18:22:50
 */
@TableName("tb_disease")
public class DiseaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 病种名称
	 */
	@NotBlank(message="病种不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 所属科室
	 */
	@NotNull(message="科室不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer deptId;
	/**
	 * 科室名称
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String deptName;

	/**
	 * 创建时间
	 */
	private Date createTime;

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
	 * 设置：病种名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：病种名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：所属科室
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属科室
	 */
	public Integer getDeptId() {
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
}
