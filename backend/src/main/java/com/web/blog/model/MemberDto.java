package com.web.blog.model;

public class MemberDto {
	private String email;
	private String name;
	private String pwd;
	private String role;
	private String joinDate;
	private String introduce;
	private String profileImg;
	private String profileImgName;

	public MemberDto() {
		super();
	}

	public MemberDto(String email, String name, String pwd, String role, String joinDate, String introduce,
			String profileImg, String profileImgName) {
		super();
		this.email = email;
		this.name = name;
		this.pwd = pwd;
		this.role = role;
		this.joinDate = joinDate;
		this.introduce = introduce;
		this.profileImg = profileImg;
		this.profileImgName = profileImgName;
	}

	public String getProfileImgName() {
		return profileImgName;
	}

	public void setProfileImgName(String profileImgName) {
		this.profileImgName = profileImgName;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "MemberDto [email=" + email + ", name=" + name + ", pwd=" + pwd + ", role=" + role + ", joinDate="
				+ joinDate + ", introduce=" + introduce + ", profileImg=" + profileImg + ", profileImgName="
				+ profileImgName + "]";
	}

}
