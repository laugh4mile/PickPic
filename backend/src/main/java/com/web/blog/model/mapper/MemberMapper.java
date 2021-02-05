package com.web.blog.model.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.blog.model.MemberDto;

@Mapper
public interface MemberMapper {
	/** id, pwd와 일치하는 MemberDto를 반환 */
	public List<MemberDto> getAllMember();

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public MemberDto login(MemberDto dto) throws SQLException;

	/** 인자로 MemberDto를 생성하고, 생성 여부를 반환 */
	public void join(MemberDto dto) throws Exception;

	/** 인자로 받은 MemberDto를 삭제하고, 성공 여부를 반환 */
	public boolean delete(String id) throws SQLException;

	/** 인자로 받은 MemberDto를 수정하고, 성공 여부를 반환 */
	public boolean updateIntro(MemberDto memberDto) throws SQLException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public boolean updatePwd(MemberDto memberDto) throws SQLException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public String findPwd(String email);

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public MemberDto findUserInfo(String email) throws SQLException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public void setProfile(MemberDto memberDto) throws IOException;

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public String getFilePath(String email);

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public void deleteImg(String email);

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public String emailCheck(String email);

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public String nameCheck(String name);
}
