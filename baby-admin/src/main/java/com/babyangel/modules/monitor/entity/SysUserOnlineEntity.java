package com.babyangel.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户记录
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 19:03:05
 */
@TableName("sys_user_online")
public class SysUserOnlineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户会话id
	 */
	private String sessionid;
	/**
	 * 登录账号
	 */
	private String loginName;
	/**
	 * 部门名称
	 */
	private String deptName;
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
	 * 在线状态on_line在线off_line离线
	 */
	private OnlineSession.OnlineStatus status = OnlineSession.OnlineStatus.on_line;
	/**
	 * session创建时间
	 */
	private Date startTimestamp;
	/**
	 * session最后访问时间
	 */
	private Date lastAccessTime;
	/**
	 * 超时时间，单位为分钟
	 */
	private long expireTime;

	/** 备份的当前用户会话 */
    @TableField(exist = false)
	private OnlineSession session;
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
	 * 设置：用户会话id
	 */
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	/**
	 * 获取：用户会话id
	 */
	public String getSessionid() {
		return sessionid;
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
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptName() {
		return deptName;
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
	 * 设置：在线状态on_line在线off_line离线
	 */
	public void setStatus(OnlineSession.OnlineStatus status) {
		this.status = status;
	}
	/**
	 * 获取：在线状态on_line在线off_line离线
	 */
	public OnlineSession.OnlineStatus getStatus() {
		return status;
	}
	/**
	 * 设置：session创建时间
	 */
	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
	/**
	 * 获取：session创建时间
	 */
	public Date getStartTimestamp() {
		return startTimestamp;
	}
	/**
	 * 设置：session最后访问时间
	 */
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	/**
	 * 获取：session最后访问时间
	 */
	public Date getLastAccessTime() {
		return lastAccessTime;
	}
	/**
	 * 设置：超时时间，单位为分钟
	 */
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：超时时间，单位为分钟
	 */
	public long getExpireTime() {
		return expireTime;
	}

	public OnlineSession getSession() {
		return session;
	}

	public void setSession(OnlineSession session) {
		this.session = session;
	}
}
