package com.web.blog.model.mapper;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.blog.model.CommentDto;
import com.web.blog.model.CommentLikeDto;
import com.web.blog.model.CommentParameterDto;

@Mapper
public interface CommentMapper {
	public CommentDto getOne(int commentNo) throws Exception;
	public List<CommentDto> commentList(int postNo) throws Exception;
	public List<CommentDto> infiniteScrollDown(int postNo, int pg) throws Exception;
	public boolean commentWrite(CommentDto dto) throws Exception;
	public boolean commentUpdate(CommentDto dto) throws Exception;
	public boolean commentDelete(int commentNo) throws Exception;
	
	
	public int likeCount(int commentNo) throws SQLException;
	public int likeCheck(Map<String, Object> map) throws SQLException;
	public CommentLikeDto likeInfo(Map<String, Object> map) throws SQLException;
	public int insertLike(Map<String, Object> map) throws SQLException;
	public int like(Map<String, Object> map) throws SQLException;
	public int unlike(Map<String, Object> map) throws SQLException;

	public int likeCntUp(int commentNo) throws SQLException;
	public int likeCntDown(int commentNo) throws SQLException;
}
