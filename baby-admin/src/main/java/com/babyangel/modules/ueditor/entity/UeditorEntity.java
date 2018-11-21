package com.babyangel.modules.ueditor.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * ueditor素材管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-15 15:34:34
 */
@TableName("tb_ueditor")
public class UeditorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 类型：image，video，audio，application
	 */
	private String type;
	/**
	 * 文件地址
	 */
	private String url;
	/**
	 * 上传时间
	 */
	private Date createTime;

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
	 * 设置：类型：image，video，audio，application
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型：image，video，audio，application
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：文件地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：文件地址
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：上传时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：上传时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
