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
 * 床位管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 18:22:50
 */
@TableName("tb_bed")
public class BedEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Integer id;
	/**
	 * 床位代码
	 */
	@NotBlank(message="床位不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String code;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 责任护士
	 */
	@NotNull(message="责任护士不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer nurseId;
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
	 * 责任护士
	 */
	@TableField(exist=false)//表示不是数据库字段，仅用于显示
	private String nursesName;


	/**
	 * 设置：ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：床位代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：床位代码
	 */
	public String getCode() {
		return code;
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
	 * 设置：责任护士
	 */
	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
	}
	/**
	 * 获取：责任护士
	 */
	public Integer getNurseId() {
		return nurseId;
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

	public String getNursesName() {
		return nursesName;
	}

	public void setNursesName(String nursesName) {
		this.nursesName = nursesName;
	}
}
