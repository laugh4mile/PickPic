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
	/** 댓글 호출 */
	public CommentDto getOne(int commentNo) throws Exception;

	/** 댓글 목록 호출 */
	public List<CommentDto> commentList(int postNo) throws Exception;

	/** 댓글 목록 무한 스크롤링 */
	public List<CommentDto> infiniteScrollDown(int postNo, int pg) throws Exception;

	/** 댓글 작성 */
	public boolean commentWrite(CommentDto dto) throws Exception;

	/** 댓글 수정 */
	public boolean commentUpdate(CommentDto dto) throws Exception;

	/** 댓글 삭제 */
	public boolean commentDelete(int commentNo) throws Exception;

	/** 댓글 like 개수 */
	public int likeCount(int commentNo) throws SQLException;

	/** 댓글 like 유무 */
	public int likeCheck(Map<String, Object> map) throws SQLException;

	/** 댓글 like 유무*/
	public CommentLikeDto likeInfo(Map<String, Object> map) throws SQLException;

	/** 댓글 like 입력 */
	public int insertLike(Map<String, Object> map) throws SQLException;

	/** 댓글 like 입력*/
	public int like(Map<String, Object> map) throws SQLException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public int unlike(Map<String, Object> map) throws SQLException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public int likeCntUp(int commentNo) throws SQLException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public int likeCntDown(int commentNo) throws SQLException;
}
