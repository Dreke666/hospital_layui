
package com.zhang.hospital.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class User {
	
	private int user_id;
	
	private String username;
	private String photo_path;
	private String password;
	
	private String gender;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	//@DateTimeFormat(pattern = "yyyy-MM-dd") 很重要 必须加上 不然jsp中
	// 数据类型和这里绑定不了 ajax请求进入不了controller

	
	private String phone;
	
	private String email;
	
	private String address;
	
	private String note;
	
	private int status;
	private String realname;
	private int levelId;


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	private Long roleId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
