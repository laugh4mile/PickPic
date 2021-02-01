package com.web.blog.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.CommentDto;
import com.web.blog.model.CommentLikeDto;
import com.web.blog.model.CommentParameterDto;
import com.web.blog.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{

	
	@Autowired
	CommentMapper dao;
	
	@Override
	public CommentDto getOne(int commentNo) throws Exception {
		return dao.getOne(commentNo);
	}
	
	@Override
	public List<CommentDto> commentList(int postNo) throws Exception {
		return dao.commentList(postNo);
	}

	@Override
	public List<CommentDto> infiniteScrollDown(int postNo, int pg) throws Exception {
		return dao.infiniteScrollDown(postNo, pg);
	}

	@Override
	public boolean commentWrite(CommentDto dto) throws Exception {
		return dao.commentWrite(dto);
	}

	@Override
	public boolean commentUpdate(CommentDto dto) throws Exception {
		return dao.commentUpdate(dto);
	}

	@Override
	public boolean commentDelete(int commentNo) throws Exception {
		return dao.commentDelete(commentNo);
	}

	@Override
	public int likeCount(int commentNo) throws Exception {
		return dao.likeCount(commentNo);
	}

	@Override
	public int likeCheck(Map<String, Object> map) throws Exception {
		return dao.likeCheck(map);
	}

	@Override
	public CommentLikeDto likeInfo(Map<String, Object> map) throws Exception {
		return dao.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		return dao.insertLike(map);
	}

	@Override
	public int like(Map<String, Object> map) throws Exception {
		return dao.like(map);
	}

	@Override
	public int unlike(Map<String, Object> map) throws Exception {
		return dao.unlike(map);
	}

	@Override
	public int likeCntUp(int commentNo) throws Exception {
		return dao.likeCntUp(commentNo);
	}

	@Override
	public int likeCntDown(int commentNo) throws Exception {
		return dao.likeCntDown(commentNo);
	}



	
}