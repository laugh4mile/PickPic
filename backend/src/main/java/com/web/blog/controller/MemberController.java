package com.web.blog.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import com.web.blog.model.MemberDto;
import com.web.blog.model.service.JwtService;
import com.web.blog.model.service.MemberService;
import com.web.blog.model.service.S3FileUploadService;
import com.web.blog.util.FileUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = { "*" })
public class MemberController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	MemberService memberService;

	@Autowired
	private FileUtil fileService;

	@Autowired
	private S3FileUploadService s3FileUploadService;

	@ApiOperation(value = "사용자의 이미지를 저장한다")
	@PostMapping("/upload")
	public ResponseEntity<Void> updateUserPicture(@RequestParam String email, @RequestParam MultipartFile profileImg) {
		try {
			MemberDto dto = new MemberDto();
			String url = s3FileUploadService.upload(profileImg);
			dto.setEmail(email);
			System.out.println(url);
			dto.setProfileImg(url);
			memberService.saveImg(dto);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("io");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql");
		}

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "사용자의 이미지를 가져온다")
	@GetMapping("/download")
	public ResponseEntity<Resource> getProfileImg(@RequestParam String email, HttpServletRequest request) {
		String path = memberService.getFilePath(email);
		Resource resource = fileService.loadFileAsResource(path);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping
	public ResponseEntity<Boolean> regist(@RequestBody Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;
		MemberDto dto = new MemberDto();
		dto.setEmail(map.get("email"));
		dto.setPwd(map.get("pwd"));
		dto.setName(map.get("name"));
		dto.setIntroduce(map.get("introduce"));
		System.out.println(dto);
		boolean flag = true;

		try {
			memberService.join(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getInfo(@RequestParam String email, HttpServletRequest req)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
//		System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")).get("user"));
		try {
			// 사용자에게 전달할 정보이다.
			MemberDto info = memberService.findUserInfo(email);
//			resultMap.put("status", true);
			resultMap.put("info", info);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
//			logger.error("정보조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PutMapping("/intro")
	public ResponseEntity<Boolean> modifyIntro(@RequestBody Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;

		MemberDto dto = new MemberDto();
		dto.setEmail(map.get("email"));
		dto.setIntroduce(map.get("introduce"));
		System.out.println(dto);
		boolean flag = false;

		try {
			flag = memberService.updateIntro(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@PutMapping("/pwd")
	public ResponseEntity<Boolean> modifyPwd(@RequestBody Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;

		MemberDto dto = new MemberDto();
		dto.setEmail(map.get("email"));
		dto.setPwd(map.get("pwd"));
		System.out.println(dto);
		boolean flag = false;

		try {
			flag = memberService.updatePwd(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@DeleteMapping
	public ResponseEntity<Boolean> delete(@RequestBody Map<String, String> map) {
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println(map);
		boolean flag = false;

		try {
			flag = memberService.delete(map.get("email"));
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}
}
