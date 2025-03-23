package com.student;

public class Module {
	private String moduleCode;
	private Integer departmentId;
	private String title;
	private Long regFee;
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getRegFee() {
		return regFee;
	}
	public void setRegFee(Long regFee) {
		this.regFee = regFee;
	}
	@Override
	public String toString() {
		return title;
	}
}
