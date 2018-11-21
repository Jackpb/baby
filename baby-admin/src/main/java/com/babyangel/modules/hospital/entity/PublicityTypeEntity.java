package com.babyangel.modules.hospital.entity;

import com.babyangel.common.validator.group.AddGroup;
import com.babyangel.common.validator.group.UpdateGroup;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 宣教类型管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-05 17:52:55
 */
@TableName("tb_publicity_type")
public class PublicityTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 宣教类型名称
	 */
	@NotBlank(message="宣教类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
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
	 * 设置：宣教类型名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：宣教类型名称
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
}
