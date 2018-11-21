package com.babyangel.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录
 * 
 * @author babyangel
 * @email babyangel@gmail.com
 * @date 2018-09-22 08:21:56
 */
@TableName("sys_oper_log")
public class SysOperLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志主键
	 */
	@TableId
	private Integer operId;
	/**
	 * 模块标题
	 */
	private String title;
	/**
	 * 业务类型（0其它 1新增 2修改 3删除）
	 */
	private Integer businessType;
	/**
	 * 方法名称
	 */
	private String method;
	/**
	 * 操作类别（0其它 1后台用户 2手机端用户）
	 */
	private Integer operatorType;
	/**
	 * 操作人员
	 */
	private String operName;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 请求URL
	 */
	private String operUrl;
	/**
	 * 主机地址
	 */
	private String operIp;
	/**
	 * 操作地点
	 */
	private String operLocation;
	/**
	 * 请求参数
	 */
	private String operParam;
	/**
	 * 操作状态（0正常 1异常）
	 */
	private Integer status;
	/**
	 * 错误消息
	 */
	private String errorMsg;
	/**
	 * 操作时间
	 */
	private Date operTime;

	/**
	 * 设置：日志主键
	 */
	public void setOperId(Integer operId) {
		this.operId = operId;
	}
	/**
	 * 获取：日志主键
	 */
	public Integer getOperId() {
		return operId;
	}
	/**
	 * 设置：模块标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：模块标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：业务类型（0其它 1新增 2修改 3删除）
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	/**
	 * 获取：业务类型（0其它 1新增 2修改 3删除）
	 */
	public Integer getBusinessType() {
		return businessType;
	}
	/**
	 * 设置：方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：方法名称
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：操作类别（0其它 1后台用户 2手机端用户）
	 */
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	/**
	 * 获取：操作类别（0其它 1后台用户 2手机端用户）
	 */
	public Integer getOperatorType() {
		return operatorType;
	}
	/**
	 * 设置：操作人员
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}
	/**
	 * 获取：操作人员
	 */
	public String getOperName() {
		return operName;
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
	 * 设置：请求URL
	 */
	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}
	/**
	 * 获取：请求URL
	 */
	public String getOperUrl() {
		return operUrl;
	}
	/**
	 * 设置：主机地址
	 */
	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}
	/**
	 * 获取：主机地址
	 */
	public String getOperIp() {
		return operIp;
	}
	/**
	 * 设置：操作地点
	 */
	public void setOperLocation(String operLocation) {
		this.operLocation = operLocation;
	}
	/**
	 * 获取：操作地点
	 */
	public String getOperLocation() {
		return operLocation;
	}
	/**
	 * 设置：请求参数
	 */
	public void setOperParam(String operParam) {
		this.operParam = operParam;
	}
	/**
	 * 获取：请求参数
	 */
	public String getOperParam() {
		return operParam;
	}
	/**
	 * 设置：操作状态（0正常 1异常）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：操作状态（0正常 1异常）
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：错误消息
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * 获取：错误消息
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * 设置：操作时间
	 */
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	/**
	 * 获取：操作时间
	 */
	public Date getOperTime() {
		return operTime;
	}
}
