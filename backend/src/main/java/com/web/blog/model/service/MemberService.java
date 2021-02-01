package com.web.blog.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.MemberDto;

public interface MemberService {

	// 관리자
	public List<MemberDto> getAllMember();

	/** id, pwd와 일치하는 MemberDto를 반환 */
	public MemberDto login(MemberDto dto) throws Exception;

	/** 인자로 MemberDto를 생성하고, 생성 여부를 반환 */
	public void join(MemberDto dto) throws Exception;

	/** 인자로 받은 MemberDto를 삭제하고, 성공 여부를 반환 */
	public boolean delete(String id) throws Exception;

	/** 인자로 받은 MemberDto를 수정하고, 성공 여부를 반환 */
	public boolean updateIntro(MemberDto memberDto) throws Exception;

	public boolean updatePwd(MemberDto memberDto) throws Exception;

	public MemberDto findUserInfo(String email) throws Exception;

	public void saveImg(MemberDto memberDto) throws IOException, SQLException;

	public void uploadFile(MultipartFile file) throws IOException;

	public String getFilePath(String email);
	
	public void deleteImg(String email);
}
