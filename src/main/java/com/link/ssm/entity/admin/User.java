package com.link.ssm.entity.admin;

import org.springframework.stereotype.Component;

/*
 * 用户实体类
 */
@Component
public class User {
	private Long id;//用户id，自增
	private String username;//用户名
	private String password;//登陆密码
	private Long roleId; //所属角色id
	private String photo;//头像地址
	private int sex;//0代表位置，1为男，2为女
	private Integer age;//年龄；
	private String address;//家庭住址

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
