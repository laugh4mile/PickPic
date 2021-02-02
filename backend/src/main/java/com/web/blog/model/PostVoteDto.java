package com.web.blog.model;

public class PostVoteDto {
	private String vNo;
	private String picNo;
	private String email;
	private String postNo;

	public String getvNo() {
		return vNo;
	}

	public void setvNo(String vNo) {
		this.vNo = vNo;
	}

	public String getPicNo() {
		return picNo;
	}

	public void setPicNo(String picNo) {
		this.picNo = picNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	@Override
	public String toString() {
		return "PostVoteDto [vNo=" + vNo + ", picNo=" + picNo + ", email=" + email + ", postNo=" + postNo + "]";
	}

}
