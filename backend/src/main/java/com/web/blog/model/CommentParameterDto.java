package com.web.blog.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PostParameterDto : 게시판 파라미터 정보", description = "게시판의 글을 얻기위한 부가적인 파라미터정보.")
public class CommentParameterDto {
	@ApiModelProperty(value = "현재 페이지 번호", example = "1")
	private int pg;
	@ApiModelProperty(value = "페이지당 글갯수", example = "10")
	private int spp;
	@ApiModelProperty(value = "페이지의 시작 글번호", example = "2")
	private int start;
	
	public CommentParameterDto() {
		super();
	}

	public CommentParameterDto(int pg, int spp, int start) {
		super();
		this.pg = pg;
		this.spp = spp;
		this.start = start;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		this.spp = spp;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "CommentParameterDto [pg=" + pg + ", spp=" + spp + ", start=" + start + "]";
	}

	
}
