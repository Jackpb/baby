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
 * 素材管理
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-10-14 23:16:39
 */
@TableName("tb_material")
public class MaterialEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
    @NotBlank(message="名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 类型：image，video，audio，application
	 */
	private String type;
	/**
	 * 文件地址
	 */
    @NotBlank(message="文件地址不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String url;
	/**
	 * 状态，0：素材，1：成品
	 */
	private Integer state;
	/**
	 * 上传时间
	 */
	private Date createTime;
	/**
	 * 所属部门
	 */
    @NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;
	/**
	 * 使用说明
	 */
	private String comment;

    /**
     * 部门名称
     */
    @TableField(exist=false)//表示不是数据库字段，仅用于显示
    private String deptName;

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
	 * 设置：状态，0：素材，1：成品
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：状态，0：素材，1：成品
	 */
	public Integer getState() {
		return state;
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
	 * 设置：使用说明
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * 获取：使用说明
	 */
	public String getComment() {
		return comment;
	}

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
