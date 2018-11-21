package com.babyangel.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统访问记录
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 09:44:48
 */
@TableName("sys_logininfor")
public class SysLogininforEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 访问ID
	 */
	@TableId
	private Integer infoId;
	/**
	 * 登录账号
	 */
	private String loginName;
	/**
	 * 登录IP地址
	 */
	private String ipaddr;
	/**
	 * 登录地点
	 */
	private String loginLocation;
	/**
	 * 浏览器类型
	 */
	private String browser;
	/**
	 * 操作系统
	 */
	private String os;
	/**
	 * 登录状态（0成功 1失败）
	 */
	private String status;
	/**
	 * 提示消息
	 */
	private String msg;
	/**
	 * 访问时间
	 */
	private Date loginTime;

	/**
	 * 设置：访问ID
	 */
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	/**
	 * 获取：访问ID
	 */
	public Integer getInfoId() {
		return infoId;
	}
	/**
	 * 设置：登录账号
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 获取：登录账号
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 设置：登录IP地址
	 */
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	/**
	 * 获取：登录IP地址
	 */
	public String getIpaddr() {
		return ipaddr;
	}
	/**
	 * 设置：登录地点
	 */
	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}
	/**
	 * 获取：登录地点
	 */
	public String getLoginLocation() {
		return loginLocation;
	}
	/**
	 * 设置：浏览器类型
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * 获取：浏览器类型
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * 设置：操作系统
	 */
	public void setOs(String os) {
		this.os = os;
	}
	/**
	 * 获取：操作系统
	 */
	public String getOs() {
		return os;
	}
	/**
	 * 设置：登录状态（0成功 1失败）
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：登录状态（0成功 1失败）
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：提示消息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 获取：提示消息
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置：访问时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 获取：访问时间
	 */
	public Date getLoginTime() {
		return loginTime;
	}
}
