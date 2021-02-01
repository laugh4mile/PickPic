package com.web.blog.model.service;

import java.util.List;
import java.util.Map;

import com.web.blog.model.CommentDto;
import com.web.blog.model.CommentLikeDto;
import com.web.blog.model.CommentParameterDto;

public interface CommentService {
//	public List<CommentDto> commentListService() throws Exception;
	/* 하나의 글 정보 */
	public CommentDto getOne(int commentNo) throws Exception;
	public List<CommentDto> commentList(int postNo) throws Exception;
	public List<CommentDto> infiniteScrollDown(int postNo, int pg) throws Exception;
	public boolean commentWrite(CommentDto dto) throws Exception;
	public boolean commentUpdate(CommentDto dto) throws Exception;
	public boolean commentDelete(int commentNo) throws Exception;
	
	
	/* 해당 댓글의 좋아요 수 */
	public int likeCount(int commentNo) throws Exception;
	/* 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws Exception;
	/* 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public CommentLikeDto likeInfo(Map<String, Object> map) throws Exception;
	/* 게시글에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;
	/* 좋아요 체크 */
	public int like(Map<String, Object> map) throws Exception;
	/* 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/* 게시글의 좋아요 수 갱신 */
	public int likeCntUp(int commentNo) throws Exception;	
	public int likeCntDown(int commentNo) throws Exception;
}
