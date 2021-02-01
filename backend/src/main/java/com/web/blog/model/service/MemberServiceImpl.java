package com.web.blog.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

import com.web.blog.model.MemberDto;
import com.web.blog.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MemberMapper dao;

	private final String IMAGE_DIR = "c:\\SSAFY\\uploaded\\";

	@Transactional
	public void saveImg(MemberDto dto) throws IOException, SQLException {
		dao.setProfile(dto);
	}

	@Override
	public void deleteImg(String email) {
		dao.deleteImg(email);
	}

	@Override
	public void uploadFile(MultipartFile file) throws IOException {
		String originName = file.getOriginalFilename();
		System.out.println("on " + originName);
		File dest = new File(IMAGE_DIR + originName);
		System.out.println("fileD " + dest);
		System.out.println(file);
		file.transferTo(dest);
		System.out.println("fin");
	}

	@Override
	public String getFilePath(String email) {
		return dao.getFilePath(email);
	}

	@Override
	public List<MemberDto> getAllMember() {
		return dao.getAllMember();
	}

	@Override
	public MemberDto login(MemberDto dto) throws SQLException {
		String encodedPassword = dao.findPwd(dto.getEmail());
		System.out.println("rowPassword : " + dto.getPwd());
		System.out.println("encodedPassword : " + encodedPassword);
		System.out.println("matches : " + passwordEncoder.matches(dto.getPwd(), encodedPassword));
		if(passwordEncoder.matches(dto.getPwd(), encodedPassword)) {
			dto.setPwd(dto.getPwd());
			MemberDto answer = dao.login(dto);
			System.out.println("answer : " + answer);
			return answer;
		}else {
			return null;
		}
	}

	@Override
	public void join(MemberDto dto) throws Exception {
		String encodePassword = passwordEncoder.encode(dto.getPwd());
		dto.setPwd(encodePassword);
		dao.join(dto);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return dao.delete(id);
	}

	@Override
	public boolean updateIntro(MemberDto memberDto) throws Exception {
		if (memberDto == null)
			return false;

		return dao.updateIntro(memberDto);
	}

	@Override
	public boolean updatePwd(MemberDto memberDto) throws Exception {
		if (memberDto == null)
			return false;

		return dao.updatePwd(memberDto);
	}

	@Override
	public MemberDto findUserInfo(String email) throws Exception {
		return dao.findUserInfo(email);
	}
}
