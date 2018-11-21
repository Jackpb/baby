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
 * 宣教内容管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-22 15:08:58
 */
@TableName("tb_publicity")
public class PublicityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 宣教名称
	 */
	private String name;
	/**
	 * logo
	 */
	private String logo;
	/**
	 * 宣教内容
	 */
	private String content;
	/**
	 * 宣教视频或音频
	 */
	private String mediaUrl;
	/**
	 * 宣教时间
	 */
	@NotNull(message="时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Date occurTime;
	/**
	 * 点击率
	 */
	private Integer clickRate;
	/**
	 * 启用状态：1启用，0禁用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 所属科室ID
	 */
	private Long deptId;
	/**
	 * 所属类型ID
	 */
	private Long typeId;

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
	 * 设置：宣教名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：宣教名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：宣教内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：宣教内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：宣教视频或音频
	 */
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	/**
	 * 获取：宣教视频或音频
	 */
	public String getMediaUrl() {
		return mediaUrl;
	}
	/**
	 * 设置：宣教时间
	 */
	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}
	/**
	 * 获取：宣教时间
	 */
	public Date getOccurTime() {
		return occurTime;
	}
	/**
	 * 设置：点击率
	 */
	public void setClickRate(Integer clickRate) {
		this.clickRate = clickRate;
	}
	/**
	 * 获取：点击率
	 */
	public Integer getClickRate() {
		return clickRate;
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
