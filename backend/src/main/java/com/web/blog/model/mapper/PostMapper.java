package com.web.blog.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.blog.model.ImgDto;
import com.web.blog.model.PostDto;
import com.web.blog.model.PostLikeDto;
import com.web.blog.model.PostParameterDto;

@Mapper
public interface PostMapper {

	public int getTotalCount(PostParameterDto postParameterDto) throws SQLException;
	public int getCount() throws SQLException;
	
	public PostDto getOne(int postNo) throws SQLException;
	public List<PostDto> getList(PostParameterDto postParameterDto) throws SQLException;
	public List<PostDto> getTempList(PostParameterDto postParameterDto) throws SQLException;
	public int write(PostDto postDto) throws SQLException;
	public int writeTemp(PostDto postDto) throws SQLException;
	public int modify(PostDto postDto) throws SQLException;
	public int delete(int postNo) throws SQLException;
	
	public int likeCount(int postNo) throws SQLException;
	public int likeCheck(Map<String, Object> map) throws SQLException;
	public PostLikeDto likeInfo(Map<String, Object> map) throws SQLException;
	public int insertLike(Map<String, Object> map) throws SQLException;
	public int like(Map<String, Object> map) throws SQLException;
	public int unlike(Map<String, Object> map) throws SQLException;
	public int updateLike(Map<String, Object> map) throws SQLException;

	public int likeCntUp(int postNo) throws SQLException;
	public int likeCntDown(int postNo) throws SQLException;
	
	public int getLastPostNo(String email) throws SQLException;
	public int uploadFile(ImgDto img) throws SQLException;
	public List<ImgDto> getFiles(int postNo) throws SQLException;
	public ImgDto getFileInfo(int picNo) throws SQLException;
	public int deleteImgs(List<String> unmodified) throws SQLException;
	public int deleteImg(int picNo) throws SQLException;
	public int getCountFiles(int postNo) throws SQLException;
	
	public int writeTemptoDB(PostDto postDto) throws SQLException;
}
